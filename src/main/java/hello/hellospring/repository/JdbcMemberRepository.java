package hello.hellospring.repository;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import javax.sql.DataSource; //Datasource는 javax.sql에 그대로 존재함 - jdk 라이브러리라서

import org.springframework.jdbc.datasource.DataSourceUtils;

import hello.hellospring.domain.Member;

//jdbc를 이용해서 h2 Database에 데이터를 저장하는 레포지터리

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        /*
         * application.properties의 DataSource 정보를 바탕으로 DataSource 객체를 스프링이 만들어준다
         * 따라서 DataSource 객체를 사용할 다른 객체가 외부에서 DataSource 객체를 주입받을 수 있다 (DI)
         * getConnection() 을 통해 DataSource의 정보를 바탕으로 한 DB Connection을 얻을 수 있다
         * DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체이다
         * 
         */
    }

    @Override
    public Member save(Member member) {
        // TODO Auto-generated method stub
        String sql = "insert into member(name) values(?)"; // ?는 파라미터 바인딩 용도다

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null; // sql 실행한 결과값

        try {
            con = dataSource.getConnection();
            psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Statement.RETURN_GENERATED_KEYS - DB Sequence 된 값을 의미한다
            psmt.setString(1, member.getName());
            psmt.executeUpdate(); // 실제 쿼리 전송
            rs = psmt.getGeneratedKeys(); // Sequence 된 값을 리턴

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new IllegalStateException(e);
        } finally { // DB에서의 작업이 끝나면 해당 커넥션을 반환해줘야한다
            if (rs != null)
                try {
                    rs.close(); // 가장 늦게 연 ResultSet 부터 close 해줘야 한다
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (psmt != null)
                try {
                    psmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        // TODO Auto-generated method stub
        String sql = "select * from member where id = ?";
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setLong(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); // member 객체를 Optional 객체에 담아서 반환한다
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (psmt != null)
                try {
                    psmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

    }

    @Override
    public Optional<Member> findByName(String name) {
        // TODO Auto-generated method stub
        String sql = "select * from member where name= ?";
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, name);
            rs = psmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (psmt != null)
                try {
                    psmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    @Override
    public List<Member> findAll() {
        // TODO Auto-generated method stub
        String sql = "select * from member";
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (psmt != null)
                try {
                    psmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

        // private Connection getConnection(){
        // return DataSourceUtils.getConnection(dataSource);
        // }
        // DataSourceUtils를 통해 트랜젝션 처리등에서 커넥션이 유지될 수 있도록 getConnection 한 Connection 객체를
        // 반환한다

        // private void close(Connection con) throws SQLException{
        // DataSourceUtils.releaseConnection(con, dataSource);
        // }
        // 커넥션을 닫을 때 역시 DataSourceUtils를 통해 커넥션을 닫아(release)줘야한다
    }

}
