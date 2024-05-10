package com.pfa.lilkre.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfa.lilkre.entities.ERole;
import com.pfa.lilkre.security.jwt.AuthEntryPointJwt;
import com.pfa.lilkre.security.jwt.AuthTokenFilter;
import com.pfa.lilkre.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
//importation  pour auth2.0

//fin de l'importation
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private final ObjectMapper mapper;
    private final TokenStore tokenStore;


    public WebSecurityConfig(ObjectMapper mapper, TokenStore tokenStore
    ) {
        this.mapper = mapper;
        this.tokenStore = tokenStore;

    }

    private final String gerantRole = ERole.ROLE_GERANT.name();
    private final String userRole = ERole.ROLE_USER.name();
    private final String adminRole = ERole.ROLE_ADMIN.name();

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //autorization de créer client à revoir
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/**/swagger-*/**").permitAll()
                .antMatchers("/**/api-docs/**").permitAll()
                .antMatchers("/agence/**").permitAll()
                .antMatchers("/employe/**")
                .hasAnyAuthority(gerantRole, adminRole)
                .antMatchers("/client/**")
                .permitAll().antMatchers("/compte/**")
                .hasAnyAuthority(userRole, adminRole)
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/article/**").permitAll()
                .antMatchers("/avis/**").permitAll()
                //juste pour le moment =>annuler les accées aux urls
                .antMatchers("/payment/**").permitAll()
                .antMatchers("/Email/**").permitAll()
                .antMatchers("/SMS/**").permitAll()
                .antMatchers("/social/login/oauth2/code/**").permitAll()
                .antMatchers("/token/**").permitAll()
                //juste pour le moment
                .antMatchers("/panier/**").permitAll()
                .antMatchers("/Envies/**").permitAll()
                // fin : juste pour le moment =>annuler les accées aux urls
                .antMatchers("/social/**")

                .permitAll().anyRequest().authenticated();
        //AJOUTER la configuration de l'auth2

        /*http.oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository( new InMemoryRequestRepository() )
                .and()
                .successHandler( this::successHandler );

                http.exceptionHandling()
                .authenticationEntryPoint( this::authenticationEntryPoint )
               .and().logout(cust -> cust.addLogoutHandler( this::logout ).logoutSuccessHandler( this::onLogoutSuccess ));*/

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        /* http.cors().configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.applyPermitDefaultValues();
            corsConfig.setAllowCredentials(true);
            corsConfig.addAllowedOrigin("http://localhost:8080/e-commerce-back/api/v1/*");// Allow credentials
            corsConfig.addAllowedMethod(HttpMethod.PUT); // Add allowed methods if needed
            corsConfig.addAllowedMethod(HttpMethod.DELETE);
            corsConfig.addAllowedMethod(HttpMethod.GET);
            corsConfig.addAllowedMethod(HttpMethod.POST);
            return corsConfig;
        });*/
    }


    //@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(new ObjectMapper());
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.addAll(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(converter);
    }

    //les méthodes liées à auth2
    public OAuth2AuthorizationRequest logout(HttpServletRequest request, HttpServletResponse response,
                                             Authentication authentication) {
        final Map<String, OAuth2AuthorizationRequest> cache = new HashMap<>();
        String state = request.getParameter("state");
        cache.clear();
        //return authorizationRequest;
        System.out.println("Auth token is - " + request.getHeader("Authorization"));
        return null;
    }

    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                         Authentication authentication) throws IOException, ServletException {
        // this code is just sending the 200 ok response and preventing redirect
        response.getWriter().write("authenticated");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void successHandler(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication) throws IOException {
        /*if(authentication.getPrincipal() instanceof DefaultOAuth2User){
            DefaultOAuth2User userDetails= ( DefaultOAuth2User )authentication.getPrincipal();
            System.out.println("userDetails "+userDetails);
        }*/
        String token = tokenStore.generateToken(authentication);

        System.out.println("the token is " + token);

        response.getWriter().write(
                mapper.writeValueAsString(Collections.singletonMap("accessToken", token))

        );

    }

    private void authenticationEntryPoint(HttpServletRequest request, HttpServletResponse response,
                                          AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(Collections.singletonMap("error", "Unauthenticated")));
    }

}
