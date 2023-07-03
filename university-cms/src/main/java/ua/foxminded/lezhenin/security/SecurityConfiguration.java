package ua.foxminded.lezhenin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan("ua.foxminded.lezhenin")
public class SecurityConfiguration {
	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(requests -> requests
				.requestMatchers("/", "/index.html").permitAll()
				.requestMatchers("/register/**").permitAll()
				.requestMatchers("/profile.html").authenticated()
				.requestMatchers("/webjars/**").permitAll()
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/student/**").hasAnyAuthority("STUDENT", "ADMIN")
				.requestMatchers("/teacher/**").hasAnyAuthority("TEACHER", "ADMIN")
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/profile")
						.permitAll())
				.logout(logout -> logout.permitAll())
			    .rememberMe().tokenValiditySeconds(2592000);

		return http.build();
	}

}
