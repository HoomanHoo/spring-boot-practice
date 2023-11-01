package hello.hellospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

@Controller
public class MemberController {
    // private final MemberService memberService = new MemberService();
    // 여러 컨트롤러에서 공용으로 사용하나 이를 매번 인스턴스 생성하는 것은 비용이 많이 든다
    // 의존성 주입 방식

    // 1. 생성자 주입 방식 가장 권장하는 방식 - 생성 시점에 객체가 주입되고 외부에서 접근할 수 없게 된다
    // 등록된 Bean을 Spring Container에 적재할 때 알아서 생성자를 통해 객체를 주입해준다 / 생성자가 한 개일 때는
    // @Autowired 생략 가능하다 / 주입 받을 필드를 final로 선언 가능하다 - 객체의 불변성 확보 가능하기 때문
    // final 필드는 생성 시점에 초기화 되어야 한다
    // 순환 참조 문제가 발생할 때 어플리케이션 시작 시점에 발생하게 된다 - Bean 객체 생성 시점에 발생하기 때문이다(생성자의 특징)
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
        // memberService = class hello.hellospring.service.MemberService$$SpringCGLIB$$0
        // cglib 이라는 라이브러리로 프록시 객체를 생성한다
    }
    // 2. 필드 주입 방식 - 주입된 객체를 불변 상태로 만들 수 없다 (final 키워드 선언 불가 - 생성 시점에 초기화되지 않기 때문)
    // Spring 밖에서 작동하지 않는다,
    // 순환 참조 문제가 발생할 때 어플리케이션 런타임에서 발생하게 된다 - Bean 객체 생성 이후에 주입하기 때문이다
    // @Autowired
    // private MemberService memberService;

    // 3. Setter 주입 방식 - 주입 받을 객체 생성되고 나중에 Setter를 통해 객체가 주입된다
    // 단점은 setter가 public으로 노출이 되기 때문에 어플리케이션 로딩 시점에 변조가 일어날 수 있다
    // 순환 참조 문제가 발생할 때 어플리케이션 런타임에서 발생하게 된다 - Bean 객체 생성 이후에 주입하기 때문이다
    // private MemberService memberService;

    // @Autowired
    // public void setMemberService(MemberService memberService) {
    // this.memberService = memberService;
    // }

    @GetMapping("members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("members/new") // 데이터를 전달할 때 사용하는 hTTP Method가 POST
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // redirect:/URL 을 이용하여 리다이렉트 시킬 수 있다

        /*
         * MemberForm을 커맨드 객체로 이용하여 별다른 선언 작성 없이 바로 getName()으로
         * 데이터를 꺼내서 사용한다
         * 커맨드 객체의 변수명은 넘어오는 데이터(key:value)의 key와 같아야 한다
         */

    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}

/*
 * Controller 어노테이션이 붙은 클래스들은 Spring이 처음 실행될 때
 * Spring Container 안에서 Spring Bean 형태로 관리되기 시작한다
 * 
 * @Controller, @Service, @Repository를 통해 클래스를 Spring Bean으로 등록하는 방식을
 * 컴포넌트 스캔과 자동 의존관계 설정 방식이라고 한다
 * 
 * @Controller, @Service, @Repository는 @Component 어노테이션을 상속받은 어노테이션(클래스)
 * 이렇게 등록한 Bean들을 생성자에 @Autowired 어노테이션을 붙여 자동으로 객체가 주입되도록 할 수 있다
 * 기본적으로는 SpringApplication.java 가 있는 패키지의 내의 클래스 및 하위 패키지들을 스캔하여 등록을 하게 된다
 * Spring Bean을 Spring Container에 등록할 때는 기본적으로 Singleton으로 등록된다
 * 특수한 케이스에서는 Singleton이 아니도록 설정할 수 있다
 * 
 * @Controller, @Service, @Repository 어노테이션을 이용하지 않고 자바 클래스와 메서드를 통하여 직접 Bean을
 * 등록할 수도 있다
 * 정형화된 Service, Repository의 경우 @Service, @Repository 어노테이션을 사용하는 것이 편리하다
 * 정형화된 Service, Repository란 이후 구현체가 변경될 일이 거의 없는 클래스를 의미한다
 */