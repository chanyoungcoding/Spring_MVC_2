package hello.springmvc.servletError;

import hello.springmvc.apiError.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WebErrorController {

    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외발생");
    }

    @GetMapping("/error-404")
    public String error404() {
        return "/error-page/404";
    }

    @GetMapping("/api/badRequest")
    public String badRequest() {
        throw new BadRequestException();
    }

    @GetMapping("/api/responseStatus")
    public String responseStatus() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }
}
