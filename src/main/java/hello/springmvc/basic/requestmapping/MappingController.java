package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MappingController {

  /**
   *  기본 요청
   */
  @RequestMapping("/hello-basic")
  public String helloBasic() {
    log.info("helloBasic");
    return "ok";
  }

  /**
   * PathVariable
   */
  @GetMapping("/mapping/{userId}")
  public String mappingPath(@PathVariable("userId") String userId) {
    log.info("PathVariable name = {}", userId);
    return "ok";
  }

  /**
   * PathVariable 2
   */
  @GetMapping("/mapping/{userId}/orders/{orderId}")
  public String mappingPath2(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId) {
    log.info("userId name = {}, orderId name = {}", userId, orderId);
    return "ok";
  }

}
