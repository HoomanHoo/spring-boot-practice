package hello.hellospring.repository;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager entityManager; // JPA는 EntityManager을 사용

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    // Spring이 applicaton.properties에 등록된 정보를 바탕으로 EntityManager를 생성해서 주입해준다
    // 자동으로 EntityManagerFactory 객체를 Spring Bean으로 등록해주기 때문에 따로 Bean 등록해줄 필요는 없다

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);

    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = entityManager.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
    /*
     * primary key 기반 탐색이 아닌 경우 단일 row 조회라도 JPQL을 작성해줘야한다
     */

    @Override
    public List<Member> findAll() {
        List<Member> result = entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;

        /*
         * 여러개의 row를 조회해야할 경우 JPQL(Java Persistence Query Language)을 작성해야한다
         * 
         * "select m from Member m"는 JPQL이라는 쿼리로, 객체를 대상으로 쿼리를 날린다
         * 날린 쿼리는 SQL문으로 번역이 된다
         * "select m from Member m" - 소문자 m은 alias이며
         * select m 이라는 의미는 Member 객체를 select 한다는 의미이다
         * 
         * 
         */
    }

}

/*
 * JPA (Java Persistence API) - 자바 진영의 표준 ORM 인터페이스
 * SQL문을 자바 코드를 통해서 자동으로 생성 처리 해준다
 * 개발 생산성을 늘려줄 수 있다
 * SQL 쿼리 전송 및 리턴 까지 JPA가 처리해준다
 * SQL과 데이터 중심 설계에서 객체 중심의 설계로 설계 패러다임을 전환할 수 있다
 * JPA 자체는 인터페이스고 이것의 구현체 중 하나가 Hibernate다
 * ORM - Object Relational Mapping
 * 객체와 연관된 테이블을 매핑해주는 기술
 * 
 * JPA 를 사용하기 위해서는 항상 transaction이 존재해야한다
 * 
 */
