package hello.exception.resolver;

import java.io.IOException;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			// 클라이언트의 Accept type에 따라 html 또는 json으로 반환된다.
			if(ex instanceof IllegalArgumentException) {
				log.info("IllegalArgumentException resolver to 400");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return new ModelAndView();
			}
			
			// ModelAndView로 에러 페이지를 반환하면 클라이언트의 Accept type과는 무관하게 지정한 html 페이지가 랜더링 된다.
			// error 페이지를 찾기 위한 서블릿 요청을 하지 않는다.
			if(ex instanceof NullPointerException) {
				log.info("RuntimeException 오류 발생 시 error-page/404.html을 호출한다");
				return new ModelAndView("error-page/404.html");
			}
		} catch (IOException e) {
			log.error("resolver ex", ex);
		}
		return null;
	}

	
}
