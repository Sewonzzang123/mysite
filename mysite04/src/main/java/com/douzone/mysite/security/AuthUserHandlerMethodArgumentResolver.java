package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * HandlerMethodArgumentResolver은 컨트롤러 메서드에서 
	 * 특정 조건에 맞는 파라미터가 있을 때 원하는 값을 바인딩해주는 인터페이스입니다.
	 * 
	 * 
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session==null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		// @AuthUser가 안붙어 있는경우
		if (authUser == null) {
			return false;
		}
		// parameter type이 UserVo가 아닌경우
		if (parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}

		return true;
	}

}
