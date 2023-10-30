package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import helloPractice.Hello;
import helloPractice.HelloSell;

/*
 * Controller는 Http Request가 Web Application 레벨에서 가장 처음 진입하는 곳이다 
 */

@Controller // 해당 클래스를 Controller로 선언
public class HelloController {

	@GetMapping("hello") // web application에서 /hello를 GET Method로 호출하면 해당 메서드를 호출한다
	public String hello(Model model) {
		model.addAttribute("data", "hello");
		model.addAttribute("inputValue", "th:value");
		return "hello";
	}

	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello-template";
	}
	/*
	 * name 파라미터를 넘기지 않으면 HTTP400 에러가 발생한다
	 * Resolved
	 * [org.springframework.web.bind.MissingServletRequestParameterException:
	 * Required request parameter 'name' for method parameter type String is not
	 * present
	 */

	/*
	 * @RequestParam("parameter name") 은 GET 방식으로 Parameter가 넘어올 때 어떤 이름의 Parameter를
	 * 받을지 정해준다
	 * 
	 * @RequestParam은 HttpServletRequest 객체와 같은 역할을 한다
	 * 여러개의 매개변수를 받아야 할 경우 @RequestParam("id") String id, @RequestParam("passwd")
	 * String passwd 방식으로 사용한다.
	 * 매개변수가 많을 경우 VO(Value Object)객체를 커멘드 객체로 사용하면 된다.
	 */

	@GetMapping("hello-string")
	@ResponseBody // HTTP Resopnse Body에 직접 데이터를 넣어서 return
	public String helloString(@RequestParam("name") String name, Model model) {
		return "hello" + name;
	}
	/*
	 * http://localhost:8080/hello-string?name=Spring 으로 접속시
	 * helloSpring 이 리턴된다
	 * HTML을 리턴하는 것이 아니기 때문에 리턴된 문자열의 값만 표시된다
	 * HTML 태그를 직접 작성할 수 있지만 비효율적이다
	 * 
	 * @ResponseBody 사용 시 해당 메서드가 매핑된 URL로 Http Request가 올 경우
	 * viewResolver 대신 HttpMessageConverter가 호출됨
	 * return 타입이 String인 경우 StringHttpMessageConverter,
	 * return 타입이 객체인 경우 MappingJackson2HttpMessageConverter(JsonConverter)가 동작함
	 * 이후 convert된 데이터를 HTTP Response Body에 넣어서 반환함
	 * 기타 경우에 따라 다양한 HttpMessageConverter를 사용함
	 * 
	 * 클라이언트의 HTTP Accept(Response Type 요청) 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
	 * HttpMessageConverter 가 선택된다
	 */

	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name, @RequestParam("age") int age) {
		Hello hello = new Hello();
		hello.setName(name);
		hello.setAge(age);

		return hello;
	}
	/*
	 * http://localhost:8080/hello-api?name=Spring&age=14으로 접속시
	 * {"name":"Spring","age":14} 이 출력된다
	 * 즉 객체를 리턴할 경우 알아서 JSON 포멧으로 리턴해준다
	 * Spring boot는 기본적으로 @ResponseBody 붙은 api method 실행 시
	 * 객체를 리턴 타입으로 지정하였으면 이를 JSON 포멧으로 바꿔서 리턴해준다
	 */

	@GetMapping("hello-vo")
	@ResponseBody
	public HelloSell helloVo(HelloSell helloSell) {
		// HelloSell responseHellSell = new HelloSell();
		return helloSell;
	}
	/*
	 * http://localhost:8080/hello-vo?price=30000&productName=steak 로 접속 시
	 * {"price":30000,"productName":"steak"} 를 반환해준다
	 * HelloSell 객체가 커멘드 변수로 사용되어 Spring에서 자동으로 처리해주었기 때문이다.
	 */

	@GetMapping("hello-calc")
	@ResponseBody
	public String helloCalc(HelloSell helloSell) {
		int price = helloSell.getPrice();
		String productName = helloSell.getProductName();

		String resultString = "구매하신 상품은 " + productName + "입니다. 가격은 " + price + "입니다.";

		return resultString;
	}
	/*
	 * http://localhost:8080/hello-calc?price=30000&productName=steak 접속 시
	 * 구매하신 상품은 steak입니다. 가격은 30000입니다. 를 반환해준다
	 * 매개변수로 들어온 helloSell 객체를 메서드 내부에서 그대로 사용하여 연산하고 값을 돌려줄 수 있다.
	 */

}

/*
 * 서버사이드 렌더링 시 return 하는 html로 데이터를 넘기는 역할을 하는 객체가 Model 객체이다
 * Model 객체는 Map의 형태로 key(attribute name)와 value(attribute value)로 이루어져있다
 * Controller 로 선언된 클래스에서 String을 return 하게 되면
 * viewResolver 클래스에서 resources/templates 경로를 탐색하여
 * return 된 String과 매치되는 이름의 html 파일을 찾는다
 * prefix는 resource/templates, suffix는 .html
 * viewResolver 클래스에서 thymeleaf 엔진을 이용하여 Controller에서 넘어온 데이터를
 * HTML 파일에 삽입하여 클라이언트로 Response 한다
 * build.gradle에 dependencies(developmentOnly)로 spring-boot-devtools를 추가할 경우
 * html 파일 컴파일만 되면 서버 재시작 없이 View 단의 파일 변경이 가능하다
 */