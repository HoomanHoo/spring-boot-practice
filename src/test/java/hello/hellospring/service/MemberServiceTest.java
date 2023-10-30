package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

public class MemberServiceTest {

    // MemberService memberService = new MemberService();
    // MemoryMemberRepository repository = new MemoryMemberRepository();
    // new 로 다른 객체가 생성되면 안의 데이터가 달라질 수도 있다

    MemberService memberService;
    MemoryMemberRepository repository;

    @BeforeEach // 테스트가 수행되기 전에 먼저 수행하는 코드
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }
    /*
     * 테스트 입장에서 기존의 service 코드는 스스로 repository 객체를 생성하여 사용한다
     * 테스트 코드도 repository 객체를 따로 생성하여 사용하기 때문에 두 repository 객체는 다른 객체이다
     * 이는 repository 객체 안에 든 데이터가 다를 가능성을 만들기 때문에 테스트의 정합성을 깨트릴 수 있다
     * 따라서 service는 외부에서 객체를 주입받도록 만들고, 테스트 코드에서 생성한 repository 객체를
     * service에 주입하여 사용하면 동일한 객체임이 보장된다.
     */

    @AfterEach // 테스트 메서드 하나가 끝날 때 마다 수행하는 코드
    public void afterEach() {
        repository.clearStore();
    }

    @Test // 테스트는 정상 케이스도 검증하지만 이외에도 예외케이스에 대한 검증 역시 필요하다
    void join() {
        // given 무언가를 주었을 때 - 데이터
        Member member = new Member();
        member.setName("spring");

        // when 이걸 실행했을 때 - 로직
        Long saveId = memberService.join(member);

        // then 그러면 이렇게 되어야 한다 - 검증
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void duplicateMemberExceptionTest() {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        // try {
        // memberService.join(member2);
        // fail("test 실패");
        // } catch (IllegalStateException e) {
        // Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        // }

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {

    }

}
// 테스트 코드는 실제 빌드 결과물에 포함되지 않는다
