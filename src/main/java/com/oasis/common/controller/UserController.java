package com.oasis.common.controller;

import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oasis.common.service.LoginService;
import com.oasis.common.service.UserService;
import com.oasis.common.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@Resource(name="loginSer")
	private LoginService loginSer;
	
	@RequestMapping(value="/login")
	public String loginByGet(Model model,HttpServletRequest req){
		Enumeration e = req.getParameterNames();
		int idx = 0;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String[] arr = req.getParameterValues(key);
			//log.info("key > "+key);
			//log.info("value > "+req.getParameter(key));
		}
		
		//model.addAttribute("message",req.getServletContext());
		return "login";
	}

	@PostMapping("/sign")
	public String signup(UserVo userVo) { // 회원 추가
		log.info("getPasswd>>>>>>>>>>>> " + userVo.getPasswd());
		log.info("getEmail>>>>>>>>>>>> " + userVo.getEmail());
		userService.save(userVo);
		return "redirect:/login";
	}
/*  
	@PostMapping(value = "/logoutAA")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		log.info("logout1>>>>>>>>>>>>");
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}
	  */
	@RequestMapping(value = "/users")
	public ModelAndView User() throws Exception {
		log.info("User>>>>>>>>>>>>");
		logger.info("User>>>>>>>>>>>>");		
		ModelAndView mav = new ModelAndView("users");
		List<UserVo> userList = userService.getUser();
		mav.addObject("users", userList);
		return mav;
	}
	
	@RequestMapping("/users/{id:.+}")
	public ModelAndView UserById(@PathVariable String id) throws Exception {
		log.info("userAuthService >>>>>>>>>>>> "+userService);
		ModelAndView mav = new ModelAndView("user");
		UserVo userVo = userService.getUserById(id);
		
		loginSer.checkFailureCount("bbb");

		mav.addObject("user", userVo);
		return mav;
	}
}