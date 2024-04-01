package ca.mcgill.ecse321.sportcenter;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
            .csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/login**").permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/public/**").permitAll()
				.requestMatchers("/error**").permitAll()
				.anyRequest().authenticated()
			)
			.securityContext((securityContext) -> securityContext
				.securityContextRepository(new DelegatingSecurityContextRepository(
					new RequestAttributeSecurityContextRepository(),
					new HttpSessionSecurityContextRepository()
				))
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(formLogin -> formLogin
				.loginPage("http://127.0.0.1:8087/#/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/login-success")
				.failureUrl("/login-failure")
				.permitAll()
            )	
			.logout((logout) ->
                 logout.deleteCookies("JSESSIONID")
                     .invalidateHttpSession(false)
                     .logoutUrl("/logout")
                     .logoutSuccessUrl("/logout-success"))
					 
			;

		return http.build();
	}	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8087"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}