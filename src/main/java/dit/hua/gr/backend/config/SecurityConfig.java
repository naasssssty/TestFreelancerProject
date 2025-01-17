package dit.hua.gr.backend.config;

import dit.hua.gr.backend.filter.JwtAuthenticationFilter;
import dit.hua.gr.backend.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll() // Δημόσια πρόσβαση για login και register
                        .requestMatchers("/api/projects", "/api/projects/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENT") // Δημιουργία έργου και προβολή έργου από CLIENT και ADMIN
                        .requestMatchers("/api/projects/freelancer/**").hasAuthority("ROLE_FREELANCER") // Πρόσβαση μόνο για Freelancer να δει τα έργα που έχουν ανατεθεί
                        .requestMatchers("/api/users").hasAuthority("ROLE_ADMIN") // Μόνο ο ADMIN μπορεί να δει όλους τους χρήστες
                        .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Μόνο ο ADMIN μπορεί να διαχειριστεί χρήστες
                        .anyRequest().authenticated() // Όλες οι άλλες ενέργειες απαιτούν αυθεντικοποίηση
                )
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless για το JWT
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Προσθήκη του φίλτρου JWT
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
