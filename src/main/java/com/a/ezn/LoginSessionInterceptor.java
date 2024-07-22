package com.a.ezn;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginSessionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler ) throws IOException {
		
		//session 객체를 가져옴
		HttpSession session = request.getSession();
		
		// login처리를 담당하는 사용자 정보를 담고 있는 객체를 가져옴.
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 되돌려보냄.
			response.sendRedirect(request.getContextPath() + "/user/login.do");
			return false; // 더 이상 컨트롤러 요청으로 가지 않도록 false로 반환함
		}else {
			// preHandle의 return 은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
			// 따라서 true로 하면 컨트롤러 uri로 가게됨.
			return true;
		}
	}

}
