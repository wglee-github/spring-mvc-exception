package hello.exception.exhandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 대상 컨트롤러 지정 방법
	// 특정 어노테이션이 선언된 컨트롤러에만 실행
 	@ControllerAdvice(annotations = RestController.class)
 	public class ExampleAdvice1 {}
 	
 	// 특정 패키지를 지정할 수 있다.(하위 패키지 포함)
 	@ControllerAdvice("org.example.controllers")
 	public class ExampleAdvice2 {}
 	
 	// 특정 컨트롤러를 직접 지정할 수 있다.
 	@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
 	public class ExampleAdvice3 {}

 * 스프링 공식 문서 참고
 	https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-anncontroller-advice 
 *
 */
@RestControllerAdvice("hello.exception.api")
@Slf4j
public class ExControllerAdivce {

	/**
	 * 	@ExceptionHandler 애노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다. 
		해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다. 참고로 지정한 예외 또는 그 예외의 자식 
		클래스는 모두 잡을 수 있다. 
		@ExceptionHandler 로 예외를 처리하면 결과적으로 정상적인 처리가 되었기 때문에(발생한 에러를 스프링이 자체적으로 처리하지 않았음) 
		클라이언트에게 상태코드 200를 보내게 된다. 그래서  @ResponseStatus 로 상태코드를 지정해 주면 원하는 상태코드를 클라이언트에게 전달해 줄 수 있다.
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResult illegalExHandler(IllegalArgumentException ex) {
		log.error("[exceptionHandler] ex", ex);
		return new ErrorResult("BAD", ex.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResult> userExHandler(UserException e){
		log.error("[exceptionHandler] ex", e);
		ErrorResult errorResult =  new ErrorResult("USER-EX", e.getMessage());
		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ErrorResult exHandler(Exception e) {
		return new ErrorResult("EX", "내부오류");
	}
}
