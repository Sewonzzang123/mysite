package com.douzone.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.mysite.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, // header를 알아야 하기 때문에
			HttpServletResponse response, // request,response
			Exception e) throws Exception {
		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOGGER.error(errors.toString());

		// 2. 요청 구분
		// 만약, JSON 요청인 경우이면 request header의 Accept에 applicaion/json이있음
		// html 요청인 경우이면 text/html
		String accept = request.getHeader("accept");

		if (accept.matches(".*application/json.*")) {
			
			// 3-1. json 응답
			response.setStatus(HttpServletResponse.SC_OK);
			
			JsonResult result = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(result);
			
			OutputStream os = response.getOutputStream();
			
			os.write(jsonString.getBytes("UTF-8"));
			os.close();
		} else {
			// 3-2. 사과 페이지 가기(정상 종료)
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}

}
