package ca.gbc.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig
{
    private final String[] noauthResources ={
            "/swagger-ui",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/api-docs/**",
            "/aggregate/**"
    };

    //configures security filter chain
    //configure all http requests
    //also configure jwt token through oauth2
    //@param httpsecurity to htttpsecurity customise
    //@return the configured securityfilterchain
    //@throws exception if error occurs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Initialising security filter chain .....");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //diable csrf protection
//                .authorizeHttpRequests(authorize ->authorize //permit all
//                        .anyRequest().permitAll())
                .authorizeHttpRequests(authorize ->authorize //all request require authentication
                        .requestMatchers(noauthResources)
                        .permitAll()
                        .anyRequest().authenticated())
                //setup oauth to configure jwt token
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();

    }
}
