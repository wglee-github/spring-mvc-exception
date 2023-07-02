package hello.exception.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

//	/**
//	 * 	@ExceptionHandler 애노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다. 
//		해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다. 참고로 지정한 예외 또는 그 예외의 자식 
//		클래스는 모두 잡을 수 있다. 
//		@ExceptionHandler 로 예외를 처리하면 결과적으로 정상적인 처리가 되었기 때문에(발생한 에러를 스프링이 자체적으로 처리하지 않았음) 
//		클라이언트에게 상태코드 200를 보내게 된다. 그래서  @ResponseStatus 로 상태코드를 지정해 주면 원하는 상태코드를 클라이언트에게 전달해 줄 수 있다.
//	 */
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ErrorResult illegalExHandler(IllegalArgumentException ex) {
//		log.error("[exceptionHandler] ex", ex);
//		return new ErrorResult("BAD", ex.getMessage());
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<ErrorResult> userExHandler(UserException e){
//		log.error("[exceptionHandler] ex", e);
//		ErrorResult errorResult =  new ErrorResult("USER-EX", e.getMessage());
//		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
//	}
//	
//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler
//	public ErrorResult exHandler(Exception e) {
//		return new ErrorResult("EX", "내부오류");
//	}
	
	@GetMapping("/api2/members/{id}")
	public MemberDto getMember(@PathVariable("id") String id) {
		
		if(id.equals("ex")) {
			throw new RuntimeException("잘못된 사용자");
		}
		
		if(id.equals("bad")) {
			throw new IllegalArgumentException("잘못된 입력 값");
		}
		
		if(id.equals("null")) {
			throw new NullPointerException("NULL 값 입력");
		}
		
		if(id.equals("user-ex")) {
			throw new UserException("사용자 오류");
		}
		
		return new MemberDto(id, "hello " + id);
	}
	
	@Data
	@AllArgsConstructor
	static class MemberDto {
		private String memberId;
		private String name;
	}
}
