


package com.pfa.lilkre.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
//import javafx.print.Collation;

import com.pfa.lilkre.entities.ERole;
import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.entities.RoleEntity;
import com.pfa.lilkre.entities.dto.TokenDto;
import com.pfa.lilkre.model.Image;
import com.pfa.lilkre.model.Personne;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.repository.RoleRepository;
import com.pfa.lilkre.security.jwt.AuthTokenFilter;
import com.pfa.lilkre.security.jwt.JwtUtils;
import com.pfa.lilkre.security.payload.request.LoginRequest;
import com.pfa.lilkre.security.payload.response.JwtResponse;
import com.pfa.lilkre.security.services.UserDetailsServiceImpl;
import com.pfa.lilkre.services.intf.IPersonneService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.net.*;

// http://localhost:8080
@RestController
@RequestMapping("/social")
@CrossOrigin(origins = "*")
//http://localhost:8080/social
public class SocialController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    IPersonneService personneService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    AuthController authController;
    private String email;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;
    @Value("${ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecommerce.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Autowired
    RoleRepository RoleRepository;


    @Autowired
    public SocialController(
            PasswordEncoder passwordEncoder) {
       /* this.userService = userService;
        this.roleService = roleService;
        this.tokenService = tokenService;*/
        this.passwordEncoder = passwordEncoder;

    }

    String name;
    String imageUrl;
    String username;
    String prenom;
    byte[] bytes;
    Long Id;

    //http://localhost:8080/social/google
    @PostMapping(value = "/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String token, HttpServletResponse response, HttpServletRequest request) throws Exception {
       // System.out.println("token" + token);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(token);
        // Get the value associated with the key "token"
        String tokenValue = (String) jsonObject.get("token");
        // System.out.println("tokenValue" + tokenValue);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport, factory)
                        .setAudience(Collections.singleton(idClient));
        //  System.out.println("ver.getJsonFactory()" + ver.getJsonFactory());
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), tokenValue);
        // System.out.println("googleIdToken" + googleIdToken);
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        // System.out.println("payload" + payload);
        //ajouter l'image de l'utilsateur à la base de données
        imageUrl = payload.get("picture").toString();
        bytes = convertImageUrlToBytesArray(imageUrl);
        //System.out.println("Base64 image: " + bytes);
        email = payload.getEmail();
        name = payload.get("given_name").toString();
        username = payload.get("given_name").toString();
        Object familyNameObj = payload.get("family_name");
        var role_User = "";
        if (familyNameObj != null) {
            prenom = familyNameObj.toString();
        } else {
            prenom = "";
        }
        // System.out.println("name" + name);
        // System.out.println("email" + email);
        Personne personne = new Personne();
        PersonneEntity personneEntity = new PersonneEntity();
        String user;
        LoginRequest loginRequest = new LoginRequest();
        if (personneService.ifEmailExist(email)) {
            //    System.out.println("email  existe.");
            personneEntity = personneService.getUserByMail(email);
            username = personneEntity.getUsername();
            role_User = personneEntity.getRole().getName().toString();
            Id = personneEntity.getId();
        } else {
            //  System.out.println("email not existe.");
            personne = createUser(email, username, bytes, prenom, name);
            role_User = personne.getRole().getName().toString();
            Id = personne.getId();
        }
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        List<String> roles = Arrays.asList(role_User);
        List<Map<String, String>> rolesList = new ArrayList<>();
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("authority", roles.get(0));
        rolesList.add(roleMap);
        //tester le role


        //fin méthode
        System.out.println("roleMap " + roleMap);
        //test


        // Conversion des rôles en une liste de GrantedAuthority

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .claim("Id", Id)
                .claim("email", email)
                .claim("roles", rolesList)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        //méthode pour annuler la connexion de l'autre client
        //SecurityContextHolder.getContext().setAuthentication(jwtToken);
        //  System.out.println("jwtToken " + jwtToken);
        return ResponseEntity.ok(
                new JwtResponse(jwtToken, Id, username, email, roles));
    }


    public static byte[] convertImageUrlToBytesArray(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    private Personne createUser(String email, String username, byte[] bytes, String prenom, String name) {
        Personne user = new Personne();
        String uniqueUsername = username;
        if (personneService.ifUsernameExist(username)) {
            //  System.out.println("im here");
            // Le nom d'utilisateur existe déjà, ajouter un suffixe numérique
            int suffix = 1;
            while (personneService.ifUsernameExist(username + suffix)) {
                suffix++;
            }
            uniqueUsername = username + suffix;
            System.out.println("Nom d'utilisateur existant. Nom d'utilisateur unique généré: " + uniqueUsername);
        }
        user.setUsername(uniqueUsername);
        user.setNom(name);
        user.setPrenom(prenom);
        user.setEmail(email);
        Image images = new Image();
        System.out.println("bytes généré: " + bytes);
        images.setPicbyte(bytes);
        String imageName = "newImage";
        images.setName(imageName);
        images.setType("image/jpeg");
        List<Image> imageUrls = new ArrayList<>();
        imageUrls.add(images);
        user.setImages(imageUrls);
        RoleEntity role = new RoleEntity();
        role.setName(ERole.ROLE_USER);
        //System.out.println("ROLE_USER "+  RoleRepository.findByName(ERole.ROLE_USER));
        RoleEntity roleUser = RoleRepository.findByName(ERole.ROLE_USER).get();
        user.setRole(roleUser);
        user.setPassword(passwordEncoder.encode(password));
        return personneService.save(user);

    }


    //http://localhost:8080/social/facebook
    @PostMapping("/facebook")
    public void loginWithFacebook(@RequestBody TokenDto tokenDto) throws Exception {
        Facebook facebook = new FacebookTemplate(tokenDto.getToken());
        String[] data = {"email"};
        org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, data);

        email = user.getEmail();
        //ajouter la logique (aprés avoir l'accées à facebook https !!!!!!!!!)
    }


    @PostMapping("/microsoft")
    public ResponseEntity<?> loginWithMicrosoft(@RequestBody Map<String, Object> tokenDto) throws Exception {
        // System.out.println("tokenDto "+ tokenDto);
        String email = (String) ((Map<String, Object>) tokenDto.get("token")).get("email");
        String firstName = (String) ((Map<String, Object>) tokenDto.get("token")).get("firstName");
        String lastName = (String) ((Map<String, Object>) tokenDto.get("token")).get("lastName");
        // Extraire les informations pertinentes du token
        Personne personne = new Personne();
        PersonneEntity personneEntity = new PersonneEntity();
        imageUrl = "https://img.freepik.com/vecteurs-premium/photo-profil-avatar-homme-illustration-vectorielle_268834-538.jpg";
        bytes = convertImageUrlToBytesArray(imageUrl);
        var role_User = "";
        if (personneService.ifEmailExist(email)) {
            //  System.out.println("email  existe.");
            personneEntity = personneService.getUserByMail(email);
            firstName = personneEntity.getUsername();
            role_User = personneEntity.getRole().getName().toString();
            Id = personneEntity.getId();
        } else {
            //  System.out.println("email not existe.");
            personne = createUser(email, firstName, bytes, lastName, firstName);
            role_User = personne.getRole().getName().toString();
            Id = personne.getId();
        }
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        List<String> roles = Arrays.asList(role_User);
        List<Map<String, String>> rolesList = new ArrayList<>();
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("authority", roles.get(0));
        rolesList.add(roleMap);
        System.out.println("roleMap " + roleMap);
        String jwtToken = Jwts.builder()
                .setSubject(firstName)
                .claim("Id", Id)
                .claim("email", email)
                .claim("roles", rolesList)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        //   System.out.println("jwtToken " + jwtToken);
        return ResponseEntity.ok(
                new JwtResponse(jwtToken, Id, firstName, email, roles));
    }




}




