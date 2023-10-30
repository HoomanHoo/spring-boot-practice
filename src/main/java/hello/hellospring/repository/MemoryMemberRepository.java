package hello.hellospring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hello.hellospring.domain.Member;

//@Repository // Repository 어노테이션을 통해 Spring Container에 Spring Bean으로 등록한다
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 동시성 문제 고려 안함
    private static long sequence = 0L; // 동시성 문제 고려 안함

    @Override
    public Member save(Member member) {
        // TODO Auto-generated method stub
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // TODO Auto-generated method stub
        return Optional.ofNullable(store.get(id));
        /*
         * 원래는 null이 들어오게 되면 프로그램이 작동을 멈춘다
         * 하지만 Optional 객체로 감싸게 되면 null이 들어와도 프로그램을 계속 동작 시킬 수 있다.
         */
    }

    @Override
    public Optional<Member> findByName(String name) {
        // TODO Auto-generated method stub
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    /*
     * store은 Map 객체(자세히는 HashMap)이기 때문에 stream()을 통해 스트림 객체를 생성할 수 있다.
     * values의 값이 들어있는 Stream 객체를 filter() 메서드를 통해 조건에 맞는 객체만 뽑아낼 수 있다.
     * filter() 의 매개변수로는 boolean 값을 리턴하는 람다식을 줄 수 있다.
     * findAny()는 Stream에서 가장 먼저 탐색되는 요소를 리턴한다.
     */

    @Override
    public List<Member> findAll() {
        // TODO Auto-generated method stub
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
