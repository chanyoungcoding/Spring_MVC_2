package hello.springmvc.servletError;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
