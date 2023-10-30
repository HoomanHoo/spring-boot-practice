package hello.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;

@Configuration // @Configuration 어노테이션 하위 Bean 클래스들도 Spring Bean으로 등록이 된다
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}

/*
 * Service, Repository는 @Bean 을 통해 관리 가능하다
 * Service가 Respository 등의 요소를 생성자 매개변수로 요구할 경우
 * 요구하는 클래스를 Bean으로 등록하고 매개변수로 넣어준다
 * Controller는 컴포넌트 스캔 방식으로 관리 - 생성자에 @Autowired 어노테이션 등으로 의존성을 주입해줘야한다
 * Controller는 URL 매핑을 통해 데이터를 Service 계층으로 넘겨주는 역할이 주기때문에 굳이 @Bean으로 등록할 필요는 없다
 * 정형화 되지 않은 Service, Repository 계층, 혹은 기타 클래스들을 @Bean 을 통해 등록을 해줄 수 있다
 * 상황에 따라 변경해야 하는 클래스도 @Bean으로 등록한다 (@Bean으로 등록할 경우 @Configuration 클래스 내 @Bean으로
 * 등록된 메서드만 변경해주면 된다 - interface를 통한 상속관계일 경우)
 * 
 * Spring Bean으로 등록(Component Scan이나 @Bean) 되어있지 않으면 @Autowired 역시 작동하지 않는다
 * 이는 Spring Bean에게 제공해주는 기능이기 때문이다
 * 직접 new 키워드를 이용해 객체를 생성하는 경우에도 @Autowired 는 동작하지 않는다
 */
