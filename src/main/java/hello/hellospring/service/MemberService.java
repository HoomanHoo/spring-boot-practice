package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Transactional // JPA를 사용하기 위해 Transaction을 생성해주어야 해서 붙여준다
// @Service // Service 어노테이션을 통해 Spring Container에 Spring Bean으로 등록한다
public class MemberService {

    private final MemberRepository memberRepository; // = new MemoryMemberRepository();

    // @Autowired // 주입받을 객체가 하나이며 final 타입인 경우 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     * MemberRepository 인스턴스를 service 내부에서 생성하지 않고 외부에서 주입되도록 바꿔주면
     * 테스트 케이스에서도 동일한 인스턴스를 사용할 수 있다
     * 생성자등의 방법으로 해당 객체 입장에서 바라본 외부에서 객체를 주입받는 방식을 의존성 역전 이라고 한다
     */

    // 회원 가입 메서드
    // 같은 이름(중복회원)은 가입 불가능
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복회원 검증 메서드
        memberRepository.save(member);

        return member.getId();

    }

    public void validateDuplicateMember(Member member) {
        // Optional<Member> result = memberRepository.findByName(member.getName());
        // findByName() 한 값을 변수에 담지 않고 체이닝으로 바로 검증할 수 있다.
        // findByName()의 return type이 Optional이기 때문이다
        // 리턴 타입에 따라 리턴 값에 대한 연산을 계속 이어가야할 경우 리턴 값을 변수에 받지 않고
        // 바로 체이닝을 할 수 있다.
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });

    }

    // 전체회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

/*
 * service는 domain과 repository를 활용하여 실제 비즈니스 로직이 들어가는 곳이다
 * repository는 DB에 접근하는 계층으로 DB에 접근하여 작업하는 코드를 작성한다
 * service는 비즈니스 로직이 들어가는 곳으로 기획, 비즈니스적인 네이밍 규칙을 권장한다
 */
