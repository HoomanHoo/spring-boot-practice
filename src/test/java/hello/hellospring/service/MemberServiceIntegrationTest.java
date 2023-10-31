package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@SpringBootTest // Spring 프레임워크를 사용한 테스트를 해야할 경우 사용
@Transactional // 테스트 실행 시 트랜젝션을 실행하고 테스트가 끝나면 db를 롤백 시킨다
class MemberServiceIntegrationTest {

    // DB 연관되는 테스트, 혹은 통합 테스트의 경우 Test DB를 따로 구축, local DB를 사용하기도 한다

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository repository;

    @Test // 테스트는 정상 케이스도 검증하지만 이외에도 예외케이스에 대한 검증 역시 필요하다
    void join() {
        // given 무언가를 주었을 때 - 데이터
        Member member = new Member();
        member.setName("hello");

        // when 이걸 실행했을 때 - 로직
        Long saveId = memberService.join(member);

        // then 그러면 이렇게 되어야 한다 - 검증
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void duplicateMemberExceptionTest() {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

}
// 테스트 코드는 실제 빌드 결과물에 포함되지 않는다
// spring 사용하는 테스트는 자바 코드만을 이용한 테스트보다 시간이 오래 걸린다
// spring 을 이용한 테스트 - 통합 테스트
// java 코드만을 이용한 테스트 - 단위 테스트
// 단위 테스트는 프레임워크에 비의존적이기 때문에 좋은 테스트 코드가 될 확률이 높다
