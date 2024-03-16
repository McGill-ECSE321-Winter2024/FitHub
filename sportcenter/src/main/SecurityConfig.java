package ca.mcgill.ecse321.sportcenter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.ecse321.sportcenter.model.AccountPrincipal;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.CustomAuthenticationManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security annotations
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AccountService customUserDetailsService;

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    public SecurityConfig(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationManager);
    }
    
    public SecurityLoggingConfig(LoggingSystem loggingSystem) {
        loggingSystem.setLogLevel("org.springframework.security", LogLevel.DEBUG);
    }

}
