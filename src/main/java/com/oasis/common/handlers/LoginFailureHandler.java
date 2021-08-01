package com.oasis.common.handlers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.oasis.common.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Resource(name="loginSer")
	private LoginService loginSer;
	
    private String loginidname = "username";
    private String loginpwdname = "password";
    private String errormsgname = "ERRORMSG";
    private String defaultFailureUrl = "/login?error";
 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {


        String username = request.getParameter(loginidname);
        String password = request.getParameter(loginpwdname);
        String errormsg = null;
        
        if(exception instanceof BadCredentialsException) {
        	loginFailureCount(username);
            errormsg = "error.BadCredentials";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = "error.BadCredentials";
        } else if(exception instanceof DisabledException) {
        	errormsg = "error.Disaled";
            //errormsg = MessageUtils.getMessage("error.Disaled");
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = "error.CredentialsExpired";
        }
        
        log.info("exception > "+exception.getLocalizedMessage());
        log.info("exception > "+exception.getMessage());
        log.info("username > "+username);
        log.info("password > "+password);
        log.info("errormsg > "+errormsg);
        request.setAttribute(loginidname, username);
        request.setAttribute(loginpwdname, password);
        request.setAttribute(errormsgname, errormsg);
 
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
    }
	
    protected void loginFailureCount(String username) {
    	log.info("loginSer >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + loginSer);
    	
    	loginSer.checkFailureCount(username);
/*        int cnt = userService.checkFailureCount(username);
        if(cnt==3) {
        	userService.disabledUsername(username);
        }*/
    }

    public String getLoginidname() {
        return loginidname;
    }
 
    public void setLoginidname(String loginidname) {
        this.loginidname = loginidname;
    }
 
    public String getLoginpwdname() {
        return loginpwdname;
    }
 
    public void setLoginpwdname(String loginpwdname) {
        this.loginpwdname = loginpwdname;
    }
 
    public String getErrormsgname() {
        return errormsgname;
    }
 
    public void setErrormsgname(String errormsgname) {
        this.errormsgname = errormsgname;
    }
 
    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }
 
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }
 
}


