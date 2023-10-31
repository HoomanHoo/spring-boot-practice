package hello.hellospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration // @Configuration 어노테이션 하위 Bean 클래스들도 Spring Bean으로 등록이 된다
public class SpringConfig {

    // DataSource dataSource;

    // @Autowired
    // public SpringConfig(DataSource dataSource) {
    // this.dataSource = dataSource;
    // }
    /*
     * DastaSource는 application.properties에 저장된 내용을 보고 Spring에서 Bean을 생성해준다
     * 이를 생성자 주입 방식으로 주입받을 수 있다
     */
    // @PersistenceContext
    // EntityManager entityManager;

    // public SpringConfig(EntityManager entityManager) {
    // this.entityManager = entityManager;
    // }

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        // return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

    // @Bean
    // public MemberRepository memberRepository() {
    // return new JpaMemberRepository(entityManager);
    // }

    // @Bean
    // public MemberRepository memberRepository() {
    // return new JdbcTemplateMemberRepository(dataSource);
    // }

    // @Bean
    // public MemberRepository memberRepository() {
    // return new MemoryMemberRepository();
    // }

    // @Bean
    // public MemberRepository memberRepository() {
    // return new JdbcMemberRepository(dataSource);
    // }

    /*
     * MemberRepository를 상속받은 memory~ 와 Jdbc~를 만들고
     * 
     * @Bean이 붙은 메서드만 수정하여 주입할 객체를 변경하였다.
     * 다형성을 활용하여 구현체만 변경하였다. 이를 지원해주기 위해
     * Spring Container 와 Bean을 통해 의존성을 주입해준다
     * 
     * MemberService는 상위 클래스인 MemberRepository만 알면 되기 때문에
     * 이 구현체를 바꿔 끼울 수 있다
     * SOLID의 O 인 Open Close Principle(개방 폐쇄 원칙)이 적용된 사례이다
     * 
     */
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
