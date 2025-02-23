package hello.springmvc.itemservice.web.item.basic;

import hello.springmvc.itemservice.domain.item.Item;
import hello.springmvc.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

  private final ItemRepository itemRepository;

  @ModelAttribute("testData1")
  public String testData1() {
    return "testData1";
  }

  @ModelAttribute("testData2")
  public int testData2() {
    return 3;
  }

  @ModelAttribute("testData3")
  public Map<String, Object> testData3() {
    Map<String, Object> map = new HashMap<>();
    map.put("test1", "test1");
    map.put("test2", "test2");
    return map;
  }

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable("itemId") Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/item";
  }

  @GetMapping("/addV0")
  public String addForm() {
    return "basic/addForm";
  }

  // 상품등록 처리 @ModelAndView 시작

  @PostMapping("/addV1")
  public String addItemV1(@RequestParam("itemName") String itemName,
                          @RequestParam("price") int price,
                          @RequestParam("quantity") Integer quantity,
                          Model model) {

    Item item = new Item();
    item.setItemName(itemName);
    item.setPrice(price);
    item.setQuantity(quantity);

    itemRepository.save(item);
    model.addAttribute("item", item);

    return "basic/item";
  }

  /**
   * @ModelAttribute 에 이름을 추가하면
   * model.addAttribute("item", item); 자동 추가
   */
  @PostMapping("/addV2")
  public String addItemV2(@ModelAttribute("item") Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

  /**
   * @ModelAttribute name 생략 가능
   * model.addAttribute(item); 자동 추가, 생략 가능
   * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
   */
  @PostMapping("/addV3")
  public String addItemV3(@ModelAttribute Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

  /**
   * @ModelAttribute 자체 생략 가능
   * model.addAttribute(item) 자동 추가
   */
  @PostMapping("/addV4")
  public String addItemV4(Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

  /**
   * PRG - Post/Redirect/Get
   * 하지만 + 연산자를 사용하는 것은 위험하다.
   */
  @PostMapping("/addV5")
  public String addItemV5(Item item, BindingResult bindingResult) {

    if(!StringUtils.hasText(item.getItemName())) {
      bindingResult.addError(new FieldError("item", "name", "아이템이름이 없습니다."));
    }

    if(item.getPrice() <= 1000) {
      bindingResult.addError(new FieldError("item", "price", "가격은 1000원 이상입니다."));
    }

    if(item.getQuantity() == null || item.getQuantity() > 10000) {
      bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 9,999 까지 허용합니다."));
    }

    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  @PostMapping("/addV6")
  public String addItemV6(Item item, BindingResult bindingResult) {

    if(!StringUtils.hasText(item.getItemName())) {
      bindingResult.rejectValue("name", "name", new Object[]{"잘못된정보"}, null);
    }

    if(item.getPrice() <= 1000) {
      bindingResult.rejectValue("price", "price", new Object[]{"1000"}, null);
    }

    if(item.getQuantity() == null || item.getQuantity() > 10000) {
      bindingResult.reject("quantity", new Object[]{"10000"}, null);
    }

    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  /**
   * 진짜 최종본
   *  저장이 잘 되면 "저장되었습니다" 출력
   */
  @PostMapping("/add")
  public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/basic/items/{itemId}";
  }

  // 상품등록 처리 @ModelAndView 끝

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable("itemId") Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  /**
   * 테스트용 데이터 추가
   */
  @PostConstruct
  public void init() {
    itemRepository.save(new Item("testA", 10000, 10));
    itemRepository.save(new Item("testB", 20000, 20));
  }

}
