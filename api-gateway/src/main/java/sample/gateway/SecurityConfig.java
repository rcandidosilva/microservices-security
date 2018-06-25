package sample.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSpringHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll();
		// Enabling CORS configuration
		http.cors().configurationSource(corsConfigurationSource());
		// Enabling CSRF configuration
		http.csrf().ignoringAntMatchers("/uaa/**").csrfTokenRepository(csrfTokenRepository())
				.requireCsrfProtectionMatcher(new RequestMatcher() {
			private final HashSet<String> allowedMethods = new HashSet<>(Arrays.asList("HEAD", "TRACE", "OPTIONS"));
			@Override
			public boolean matches(HttpServletRequest request) {
				return !this.allowedMethods.contains(request.getMethod());
			}
		});
		// CSRF tokens handling
		http.addFilterAfter(new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
											FilterChain filterChain) throws ServletException, IOException {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				if (token != null) {
					response.setHeader("X-CSRF-HEADER", token.getHeaderName());
					response.setHeader("X-CSRF-PARAM", token.getParameterName());
					response.setHeader("X-CSRF-TOKEN" , token.getToken());
					response.setHeader("Access-Control-Expose-Headers" , "X-SESSION-TOKEN, X-CSRF-HEADER, X-CSRF-PARAM, X-CSRF-TOKEN");
				}
				filterChain.doFilter(request, response);
			}
		}, CsrfFilter.class);
	}

	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedOrigin("http://localhost:4200");
//		config.addAllowedOrigin("http://www.google.com.br");
		config.setAllowedMethods(newArrayList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-CSRF-TOKEN");
		return repository;
	}

	@Bean
	public SessionRepository sessionRepository() {
		return new MapSessionRepository();
	}

	@Bean
	// Setting the session strategy to support CSRF tokens
	public HeaderHttpSessionStrategy sessionStrategy() {
		HeaderHttpSessionStrategy strategy = new HeaderHttpSessionStrategy();
		strategy.setHeaderName("X-SESSION-TOKEN");
		return strategy;
	}

}