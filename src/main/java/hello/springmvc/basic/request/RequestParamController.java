package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

  /**
   * 반환 타입이 없으면서 응답에 값을 직접 집어넣으면, View 조회 X
   */
  @RequestMapping("/request-param-v1")
  public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    log.info("V1 - username={}, age={}", username, age);

    response.getWriter().write("ok");
  }

  @ResponseBody
  @RequestMapping("request-param-v2")
  public String requestParamV2(
      @RequestParam("username") String userName,
      @RequestParam("age") int numberAge
  ) {
    log.info("V2 - username={}, age={}", userName, numberAge);
    return "ok";
  }

  /**
   * HTTP 파라미터 이름과 변수이름이 같다면 () 생략 가능
   */
  @ResponseBody
  @RequestMapping("request-param-v3")
  public String requestParamV3(
      @RequestParam String userName,
      @RequestParam int numberAge
  ) {
    log.info("V3 - username={}, age={}", userName, numberAge);
    return "ok";
  }

  /**
   * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
   */
  @ResponseBody
  @RequestMapping("request-param-v4")
  public String requestParamV4(
      String userName,
      int numberAge
  ) {
    log.info("V4 - username={}, age={}", userName, numberAge);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-required")
  public String requestParamRequired(
      @RequestParam(required = true) String username,
      @RequestParam(required = false) Integer age
  ) {

    log.info("Required - username={}, age={}", username, age);

    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-default")
  public String requestParamDefault(
      @RequestParam(required = false, defaultValue = "chan") String username,
      @RequestParam(required = false, defaultValue = "-1") int age
  ) {

    log.info("Default - username={}, age={}", username, age);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-map")
  public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
    log.info("Map - username={}, age={}", paramMap.get("username"), paramMap.get("age"));
    return "ok";
  }

}
