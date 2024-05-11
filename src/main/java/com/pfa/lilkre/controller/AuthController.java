package com.pfa.lilkre.controller;

import com.pfa.lilkre.controller.EmailAndSMSPackage.SendEmailController;
import com.pfa.lilkre.entities.ConfirmationCodeEntity;
import com.pfa.lilkre.entities.ERole;
import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.entities.RoleEntity;
import com.pfa.lilkre.entities.dto.Code_Info;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.repository.RoleRepository;
import com.pfa.lilkre.security.jwt.JwtUtils;
import com.pfa.lilkre.security.payload.request.LoginRequest;
import com.pfa.lilkre.security.payload.request.SignupRequest;
import com.pfa.lilkre.security.payload.response.JwtResponse;
import com.pfa.lilkre.security.payload.response.MessageResponse;
import com.pfa.lilkre.security.services.UserDetailsImpl;
import com.pfa.lilkre.services.intf.IConfirmationCodeService;
import com.pfa.lilkre.services.intf.IPersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonneRepository userRepository;
    @Autowired
    IConfirmationCodeService iConfirmationCodeService;
    @Autowired
    SendEmailController sendEmailController;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IPersonneService iPersonneService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest
                                              // , HttpServletResponse response
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jwt for auth  " + jwt);
// Créez un cookie HTTP-only contenant le JWT
      /* Cookie cookie = new Cookie("CookiesData", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Assurez-vous que votre application utilise HTTPS
        cookie.setMaxAge((int) jwtUtils.getJwtExpirationInMs());
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        System.out.println("response"+ response);*/
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));

    }

    //ajouter une méthode pour vérifier l'existance de username et email
    @PostMapping(value = "/sendCodeConfirmation", consumes = "application/json")
    public ResponseEntity<?> sendCodeConfirmation(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        //sending the code
        ConfirmationCodeEntity confirmationCode = new ConfirmationCodeEntity();
        Random random = new Random();
        // Générer un nombre aléatoire entre 100000 et 999999 (inclus)
        int randomCode = random.nextInt(900000) + 100000;
        confirmationCode.setCode(String.valueOf(randomCode));
        confirmationCode.setEmail(signUpRequest.getEmail());
        confirmationCode.setCreatedAt(LocalDateTime.now());
        confirmationCode.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        iConfirmationCodeService.saveConfirmationCode(confirmationCode);
        Code_Info code_info = new Code_Info();
        code_info.setCode(String.valueOf(randomCode));
        code_info.setNomDestinateur(signUpRequest.getUsername());
        code_info.setEmail(signUpRequest.getEmail());
        sendEmailController.senEmail(code_info);
        return null;
    }

    //fin methode
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, @RequestParam String code) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        PersonneEntity user = PersonneEntity.builder()
                .nom(signUpRequest.getNom())
                .prenom(signUpRequest.getPrenom())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();


        RoleEntity LOCATAIRERole = roleRepository.findByName(ERole.ROLE_LOCATAIRE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(LOCATAIRERole);
        //mettre la vérification ici  (si le client

        Optional<ConfirmationCodeEntity> codeByEmail = iConfirmationCodeService.getCodeByEmail(signUpRequest.getEmail());
        if (codeByEmail.isPresent()) {
            //System.out.println("codeByEmail "+codeByEmail.get().getCode());
            // System.out.println("code "+ code);
            if (Objects.equals(code, codeByEmail.get().getCode())) {
                //activer l'utilisateur
                user.setActif(true);
                userRepository.save(user);
                // System.out.println("user saved");
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Votre code n'est pas correcte!"));
            }
        } else {
            System.out.println("An error appears.");
        }


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    //ajoutter le méthode de véifier l'existance de compte d'utilisateur par son mail ou username ou N.téléphone

    @PostMapping(value = "/verifeCompte")
    public ResponseEntity<?> verifierCompte(@RequestParam String identifiant) {
        // Vérification de l'existence du compte par téléphone, email ou username
        boolean telephoneExist = iPersonneService.ifTelephoneExist(identifiant);
        boolean emailExist = iPersonneService.ifEmailExist(identifiant);
        boolean usernameExist = iPersonneService.ifUsernameExist(identifiant);
        // Affichage des résultats pour débogage
        System.out.println("Telephone exist: " + telephoneExist);
        System.out.println("Email exist: " + emailExist);
        System.out.println("Username exist: " + usernameExist);
        // Vérification si au moins un compte existe
        if (telephoneExist || emailExist || usernameExist) {
            System.out.println("Le compte existe !");
            //  return  ResponseEntity.ok("Le compte existe!");
            return ResponseEntity.ok().body("{\"message\": \"Le compte existe\"}");
        } else {
            System.out.println("Le compte n'existe pas.");
            //return  ResponseEntity.ok("Le compte n'existe pas");
            return ResponseEntity.ok().body("{\"message\": \"Le compte n'existe pas\"}");

        }
    }

    //méthode pour envoyer un code pour un récupérer son compte
    @PostMapping(value = "/sendCodeForReciveCompte")
    public ResponseEntity<?> sendCodeForReciveCompte(@RequestParam String identifiant) {
        // System.out.println("identifiant "+ identifiant);
        Optional<PersonneEntity> personne = Optional.ofNullable(iPersonneService.getUserByTel(identifiant));
        // System.out.println("findpersonne "+ personne);
        if (personne.isEmpty()) {
            personne = Optional.ofNullable(iPersonneService.getUserByMail(identifiant));
            //System.out.println("getUserByMail "+ personne);
        }
        if (personne.isEmpty()) {
            personne = iPersonneService.getUserByUsername(identifiant);
            //System.out.println("getUserByUsername "+ personne);
        }
        if (personne.isPresent()) {
            //sending the code
            ConfirmationCodeEntity confirmationCode = new ConfirmationCodeEntity();
            Random random = new Random();
            String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder codeBuilder = new StringBuilder();
// Générer un code alphanumérique de 6 caractères
            for (int i = 0; i < 6; i++) {
                // Générer un index aléatoire dans la plage des caractères autorisés
                int randomIndex = random.nextInt(allowedChars.length());
                // Ajouter le caractère correspondant à l'index aléatoire au code
                codeBuilder.append(allowedChars.charAt(randomIndex));
            }
            System.out.println("codeBuilder: " + codeBuilder);
            confirmationCode.setCode(codeBuilder.toString());
            confirmationCode.setEmail(personne.get().getEmail());
            confirmationCode.setCreatedAt(LocalDateTime.now());
            confirmationCode.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            iConfirmationCodeService.saveConfirmationCode(confirmationCode);
            Code_Info code_info = new Code_Info();
            code_info.setCode(codeBuilder.toString());
            code_info.setNomDestinateur(personne.get().getUsername());
            code_info.setEmail(personne.get().getEmail());
            sendEmailController.senEmail(code_info);
            return ResponseEntity.ok().body("{\"message\": \"Email envoyé avec succès!\"}");
        } else {
            return ResponseEntity.ok().body("{\"message\": \"Aucun compte trouvé avec cet identifiant.\"}");
        }
    }

    //méthode pour vérifier le code confirmation de la modification de password
    @PostMapping(value = "/VerifierCodeConfirmationOfPassword")
    public ResponseEntity<?> VerifierCodeConfirmationOfPassword(@RequestParam String identifiant, @RequestParam String code) {
        // System.out.println("identifiant "+ identifiant);
        Optional<PersonneEntity> personne = Optional.ofNullable(iPersonneService.getUserByTel(identifiant));
        // System.out.println("findpersonne "+ personne);
        if (personne.isEmpty()) {
            personne = Optional.ofNullable(iPersonneService.getUserByMail(identifiant));
            //System.out.println("getUserByMail "+ personne);
        }
        if (personne.isEmpty()) {
            personne = iPersonneService.getUserByUsername(identifiant);
            //System.out.println("getUserByUsername "+ personne);
        }
        if (personne.isPresent()) {
            Optional<ConfirmationCodeEntity> codeByEmail = iConfirmationCodeService.getCodeByEmail(personne.get().getEmail());
            if (codeByEmail.isPresent()) {
                if (Objects.equals(code, codeByEmail.get().getCode())) {
                    return ResponseEntity.ok().body("{\"message\": \"Le code est correct\"}");
                } else {
                    return ResponseEntity.ok().body("{\"message\": \"Le code n'est pas correct\"}");
                }
            } else {
                return ResponseEntity.ok().body("{\"message\": \"Aucun code de confirmation trouvé pour cet email\"}");
            }
        } else {
            return ResponseEntity.ok().body("{\"message\": \"Un erreur s'est produire!!!\"}");
        }
    }

    //ajouter une méthode pour modifier le mot de passe
    @PostMapping(value = "/ModifierPassword")
    public ResponseEntity<?> ModifierPassword(@RequestParam String identifiant, @RequestParam String code, @RequestParam String NewPassword) {
        // System.out.println("identifiant "+ identifiant);
        Optional<PersonneEntity> personne = Optional.ofNullable(iPersonneService.getUserByTel(identifiant));
        // System.out.println("findpersonne "+ personne);
        if (personne.isEmpty()) {
            personne = Optional.ofNullable(iPersonneService.getUserByMail(identifiant));
            //System.out.println("getUserByMail "+ personne);
        }
        if (personne.isEmpty()) {
            personne = iPersonneService.getUserByUsername(identifiant);
            //System.out.println("getUserByUsername "+ personne);
        }
        if (personne.isPresent()) {
            Optional<ConfirmationCodeEntity> codeByEmail = iConfirmationCodeService.getCodeByEmail(personne.get().getEmail());
            if (codeByEmail.isPresent()) {
                if (Objects.equals(code, codeByEmail.get().getCode())) {
                    personne.get().setPassword(encoder.encode(NewPassword));
                    iPersonneService.savePersonneEntity(personne.get());
                    return ResponseEntity.ok().body("{\"message\": \"mot de passe modifiée\"}");
                } else {
                    return ResponseEntity.ok().body("{\"message\": \"mot de passe non modifiée\"}");
                }
            } else {
                return ResponseEntity.ok().body("{\"message\": \"Aucun code de confirmation trouvé pour cet email\"}");
            }
        } else {
            return ResponseEntity.ok().body("{\"message\": \"Un erreur s'est produire!!!\"}");
        }
    }

}
