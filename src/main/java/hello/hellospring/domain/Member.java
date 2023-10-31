package hello.hellospring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // JPA가 관리하는 엔티티 객체임을 알려줌 - 이를 바탕으로 테이블이 매핑됨
public class Member {

    @Id // Primary Key임을 알려주는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 직접 값을 넣지 않아도 DB가 알아서 값을 생성해줌(GenerationType.IDENTITY)의 의미
    private Long id; // 시스템이 저장하는 id

    @Column(name = "name") // @Column(name = "컬럼명")DB의 컬럼명과 매핑시킴을 선언
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "id: " + id + "name: " + name;
    }

}
