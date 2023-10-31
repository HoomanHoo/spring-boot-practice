package hello.hellospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
/*
 * Spring Data JPA는 JPA를 좀 더 편하게 사용할 수 있도록 해주는 기술
 * 반복적인 CRUD 기능도 모두 제공해줌
 * JPA와 같은 환경 설정에서 동작한다
 * JPA 에 대한 학습이 선행되어야한다
 * Spring Data JPA는 JpaRepository 라는 인터페이스를 상속받아야 한다
 * Spring에서 자동적으로 JpaRepository를 상속받은 인터페이스를 탐색하고 구현체를 만들어준다
 * 기본적인 CRUD는 JpaRepository에 모두 존재하기 때문에 선언하지 않아도 사용이 가능하다
 * 하지만 pk 이외의 요소로 select 하는 경우는 선언을 해줘야한다
 * findBy + 컬럼-멤버 변수명으로 select 할 컬럼을 바꿀 수 있으며
 * findBy + 컬럼-멤버 변수명 And / Or 등의 조합을 통해 다양한 조건의 select 가 가능하다
 * 페이징 기능도 자동으로 제공해준다
 * 복잡한 쿼리, 동적 쿼리는 Querydsl 로 해결할 수 있다
 * 이외에도 JdbcTemplate, MyBatis, DB Query를 직접 입력하는 방식 등으로 대처할 수 있다
 * 
 */
