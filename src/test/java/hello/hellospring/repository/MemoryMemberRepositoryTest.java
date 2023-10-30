package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 테스트 메서드 하나가 끝날 때 마다 수행하는 코드
    public void afterEach() {
        repository.clearStore();
    }

    @Test // 테스트할 내용을 작성하는 메서드
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        // Assertions.assertEquals(member, null); 값이 다르기 때문에 테스트 실패
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring2").get();

        // assertThat(result).isEqualTo(member1); 다른 값이기 때문에 테스트 실패
        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        // assertThat(result.size()).isEqualTo(3); 테스트 실패 2개 들었는데 3개 기대값으로 넣었기 때문에
        assertThat(result.size()).isEqualTo(2);
    }
    // 회원 리포지토리 테스트 케이스 작성 11:20 까지 들었음

}

/*
 * 모든 테스트는 순서와 상관 없이 동작해야한다
 * 다른 테스트에 의존적인 테스트는 좋지 않음
 * 다른 테스트의 성공 여부에 따라 결과가 달라질 수 있기 때문이다
 * 테스트 코드를 먼저 작성하고 구현을 시작하는 것을 테스트 주도 개발 방법이라고 한다
 * (Test-Driven Development)
 * gradlew test 명령어로 테스트를 수행할 수도 있다
 */