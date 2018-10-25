package com.hzy.oauth2server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableAuthorizationServer
public class Oauth2ServerApplication  extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerApplication.class, args);
	}

	// 启动的时候要注意，由于我们在controller中注入了RestTemplate，所以启动的时候需要实例化该类的一个实例
	@Autowired
	private RestTemplateBuilder builder;

	// 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}


//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.headers().frameOptions().disable();
//		http.authorizeRequests()
//				.antMatchers("/403").permitAll() // for test
//				.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/appManager").permitAll() // for login
//				.antMatchers("/image", "/js/**", "/fonts/**").permitAll() // for login
//				.antMatchers("/j_spring_security_check").permitAll()
//				.antMatchers("/oauth/authorize").authenticated();
//        /*.anyRequest().fullyAuthenticated();*/
//		http.formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
//				.and()
//				.authorizeRequests().anyRequest().authenticated()
//				.and().logout().invalidateHttpSession(true)
//				.and().sessionManagement().maximumSessions(1).expiredUrl("/login?expired").sessionRegistry(sessionRegistry());
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//		http.rememberMe().disable();
//		http.httpBasic();
//
//	}

}
