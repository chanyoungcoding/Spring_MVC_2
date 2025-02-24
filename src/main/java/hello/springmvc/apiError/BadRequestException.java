package hello.springmvc.apiError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류입니다.")
public class BadRequestException extends RuntimeException {

}
