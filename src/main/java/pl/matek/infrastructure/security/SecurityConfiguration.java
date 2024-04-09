package pl.matek.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            UserDetailsService userDetailService
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                                .requestMatchers("/logout", "/login", "/error", "/registerCustomer/**", "/registerOwner/**").permitAll()
                                .requestMatchers("/owner/**").hasAnyAuthority("OWNER")
                                .requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER")
                                .requestMatchers("/").hasAnyAuthority("OWNER", "CUSTOMER")
                                .requestMatchers("/api/**").hasAnyAuthority("REST")
                )
                .formLogin(form -> form
                        .usernameParameter("username")
                        .successHandler(new AuthenticationSuccessHandler() {
                                            @Override
                                            public void onAuthenticationSuccess(
                                                    HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException {
                                                Optional<String> firstRole = authentication.getAuthorities().stream()
                                                        .map(Object::toString)
                                                        .findFirst();
                                                String role = "/error";
                                                if (firstRole.isPresent()) {
                                                    role = switch (firstRole.get()) {
                                                        case "OWNER" -> "/owner";
                                                        case "CUSTOMER" -> "/customer";
                                                        default -> "/error";
                                                    };
                                                }
                                                response.sendRedirect(request.getContextPath() + role);
                                            }
                                        }
                        )
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout(form -> form
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();
        return http.build();
    }

}
