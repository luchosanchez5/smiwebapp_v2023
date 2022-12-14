package com;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}	

	/*@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Value("${spring.queries.userrs-query}")
	private String usersQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource);
				//.passwordEncoder(bCryptPasswordEncoder);
	}
	
	*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	http.
			authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login.html","/login","/prueba", "/sealhome").permitAll()
				.antMatchers("**/build/css/**","**/js/**","/registration","/customers","**/list-leads","principal.html").permitAll()
				//.antMatchers("/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated().and().csrf().disable().formLogin()
				.loginPage("/loginini").failureUrl("/probando")
				//.defaultSuccessUrl("/admin/home")
				//.defaultSuccessUrl("/ecohome")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
				
				
	
	
	           /* .and().sessionManagement()
                .invalidSessionUrl("/session/logout")
                .sessionFixation()
                .changeSessionId()
                .maximumSessions(1)
                .expiredUrl("/sesion/login");*/
	
		
	/*	http
		.httpBasic().and()
		.authorizeRequests()
			.antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll()
			.anyRequest().authenticated()
			.and()
		.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); */
	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/build/**","/vendors/**","/resources/**","/list-leads", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	

	private CsrfTokenRepository csrfTokenRepository() {
	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	  repository.setHeaderName("X-XSRF-TOKEN");
	  return repository;
	}

}
