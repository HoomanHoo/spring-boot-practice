package hello.hellospring.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;

import hello.hellospring.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate; // 자동으로 주입되지 않음

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // DataSource를 Injection 받아서 JdbcTemplate 객체를 생성해야한다
    // Spring Bean으로 등록된 객체가 생성자를 하나만 가질 경우
    // @Autowired 어노테이션을 생략할 수 있다
    // tempate method 패턴을 이용한 것이며 코드량이 많이 줄어든다

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // 위에 입력한 코드를 이용해 jdbcTemplate가 insert문을 생성해준다

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        // return new RowMapper<Member>() {

        // @Override
        // public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Member member = new Member();
        // member.setId(rs.getLong("id"));
        // member.setName(rs.getString("name"));
        // return member;
        // }

        // };
        // 람다 스타일로 사용 가능
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };

    };

}

/*
 * JdbcTemplate는 순수 Jdbc와 동일한 환경 설정 하에서 동작한다
 * Spring JdbcTemplate나 MyBatis 와 같은 라이브러리는 JDBC API에서 반복 코드를 제거해준다
 * 반복 코드 예시 - getConnection, preparedStatement 등
 * SQL은 직접 작성해야한다
 */