package com.oasis.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.oasis.common.service.LoginService;
import com.oasis.common.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity // 1
@Configuration 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // 2

	@Resource(name="loginSer")
	private LoginService loginSer;
	
	private final UserService userService; // 3

	@Override
	public void configure(WebSecurity web) { // 4
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/3rd/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // 5
		http
				// .csrf().disable()
				.authorizeRequests() // 6
				// .antMatchers("/", "/errors/**").permitAll()
				// .antMatchers("/ajaxTest").authenticated()
				// .antMatchers("/login**").anonymous()
				// .antMatchers("/register**").anonymous()
				// .antMatchers("/register/**").anonymous()
				// error을 추가하면 통합에러처리
				.antMatchers("/login", "/signup", "/sign", "/logout", "/denied").permitAll() // 누구나 접근 허용
				.antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
				.antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
				.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
				.and().formLogin() // 7
				.loginPage("/login") // 로그인 페이지 링크
				// .loginProcessingUrl("/loginProcess")
				.defaultSuccessUrl("/").failureUrl("/login?error=true")
				// .failureHandler(new LoginFailureHandler())
				.failureHandler(new AuthenticationFailureHandler() {

					private String loginidname = "username";
					private String loginpwdname = "password";
					private String errormsgname = "ERRORMSG";
					private String defaultFailureUrl = "/login?error";

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						String email = request.getParameter("email");
						String error = exception.getMessage();
						// System.out.println("A failed login attempt with email: " + email + ". Reason:
						// " + error);

						String username = request.getParameter(loginidname);
						String password = request.getParameter(loginpwdname);
						String errormsg = null;

						if (exception instanceof BadCredentialsException) {
							loginFailureCount(username);
							errormsg = "error.BadCredentials";
						} else if (exception instanceof InternalAuthenticationServiceException) {
							errormsg = "error.BadCredentials";
						} else if (exception instanceof DisabledException) {
							errormsg = "error.Disaled";
							// errormsg = MessageUtils.getMessage("error.Disaled");
						} else if (exception instanceof CredentialsExpiredException) {
							errormsg = "error.CredentialsExpired";
						}

						log.info("username > " + username);
						log.info("password > " + password);
						log.info("errormsg > " + errormsg);
						request.setAttribute(loginidname, username);
						request.setAttribute(loginpwdname, password);
						request.setAttribute(errormsgname, errormsg);

						request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
					}

					protected void loginFailureCount(String username) {
						log.info("loginSer >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + loginSer);
						loginSer.checkFailureCount(username);
						/*
						 * int cnt = userService.checkFailureCount(username); if(cnt==3) {
						 * userService.disabledUsername(username); }
						 */
					}
				}).and().logout().invalidateHttpSession(true).deleteCookies("JSESSIONID") // 8
				.logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint()) // Here
				.accessDeniedHandler(new AccessDeniedHandler() {

					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						response.sendRedirect("/denied");
					}
				});

	}

	@Bean
	public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {

		LoginUrlAuthenticationEntryPoint entry = new LoginUrlAuthenticationEntryPoint("/login") {
			@Override
			public void commence(final HttpServletRequest request, final HttpServletResponse response,
					final AuthenticationException authException) throws IOException, ServletException {

				String ajaxHeader = request.getHeader("X-Requested-With");
				if (ajaxHeader != null && "XMLHttpRequest".equals(ajaxHeader)) {
					// InsufficientAuthenticationException
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				} else {
					super.commence(request, response, authException);
				}
			}
		};
		return entry;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
		log.info("Config>>>>>>");
		auth.userDetailsService(userService)
				// 해당 서비스(userService)에서는 UserDetailsService를 implements해서
				// loadUserByUsername() 구현해야함 (서비스 참고)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}