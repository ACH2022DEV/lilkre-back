package com.pfa.lilkre.config;

import com.pfa.lilkre.entities.ERole;
import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.entities.RoleEntity;
import com.pfa.lilkre.model.Image;
import com.pfa.lilkre.model.Personne;
import com.pfa.lilkre.repository.RoleRepository;
import com.pfa.lilkre.security.jwt.JwtUtils;
import com.pfa.lilkre.security.payload.response.JwtResponse;
import com.pfa.lilkre.services.intf.IPersonneService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/token")
public class TokenStore {
    @Autowired
    JwtUtils jwtUtils;
    @Value("${ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecommerce.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${mySecret.password}")
    private String password;
    @Autowired
    RoleRepository RoleRepository;
    @Autowired
    IPersonneService personneService;
    private PasswordEncoder passwordEncoder;
    String imageUrl;
    String username;
    String prenom;
    Long Id;
    byte[] bytes;
    public final Map<String, String> cache = new HashMap<String, String>();

    public String generateToken(Authentication authentication) throws IOException {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
        System.out.println("getName " + userDetails.getName());
        PersonneEntity personneEntity = new PersonneEntity();
        Personne personne = new Personne();
        String email = (String) userDetails.getAttributes().get("email");
        String username = "";
        String user;
        //ajouter l'image de l'utilsateur à la base de données
        imageUrl = (String) userDetails.getAttributes().get("picture");
        if (!Objects.equals(imageUrl, "")) {
            bytes = convertImageUrlToBytesArray(imageUrl);
        }
        username = (String) userDetails.getAttributes().get("given_name");
        prenom = (String) userDetails.getAttributes().get("family_name");
        if (personneService.ifEmailExist(email)) {
            System.out.println("email  existe.");
            personneEntity = personneService.getUserByMail(email);
            username = personneEntity.getUsername();
            Id = personneEntity.getId();
        } else {
            System.out.println("email not existe.");
            personne = createUser(email);
            Id = personne.getId();
            System.out.println("personne" + personne);

        }
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .claim("Id", Id)
                .claim("email", userDetails.getAttributes().get("email"))
                .claim("roles", roles.get(0))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        //ajouter les autres attributes en cache
        cache.put("token", jwtToken);
        cache.put("email", email);
        cache.put("username", username);
        cache.put("Id", String.valueOf(Id));
        cache.put("roles", roles.get(0));
        String Mytoken = cache.get("token");
        System.out.println("jwt " + Mytoken);
        return jwtToken;

    }

    @GetMapping("/")
    public ResponseEntity<?> getToken() {
        String token = cache.get("token");
        Long Id = Long.valueOf(cache.get("Id"));
        String email = cache.get("email");
        String username = cache.get("username");
        String Roles = cache.get("roles");
        //afficher les autres attributes en cache
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("id", Id);
        jsonResponse.put("username", username);
        jsonResponse.put("email", email);
        jsonResponse.put("roles", Roles);
        jsonResponse.put("accessToken", token);
        jsonResponse.put("tokenType", "Bearer");
        System.out.println("token" + token);
        List<String> roles = Arrays.asList("ROLE_USER");
        return ResponseEntity.ok(
                new JwtResponse(token, Id, username, email, roles));
    }

    @GetMapping("/deleteToken")
    public void deleteToken() {
        cache.remove("token");

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

    private Personne createUser(String email) {
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
        user.setNom(username);
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

}
