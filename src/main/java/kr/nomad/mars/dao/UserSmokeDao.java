package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserSmoke;
public class UserSmokeDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper usersmokeMapper = new RowMapper() {
		public UserSmoke mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserSmoke usersmoke = new UserSmoke();
			usersmoke.setSeq(rs.getInt("seq"));
			usersmoke.setUserId(rs.getString("user_id"));
			usersmoke.setAvgSmoke(rs.getInt("avg_smoke"));
			usersmoke.setStatus(rs.getInt("status"));
			usersmoke.setRegDate(rs.getString("reg_date"));
			usersmoke.setMedSeq(rs.getInt("medSeq"));
			return usersmoke;
		}
	};

	public int addUserSmoke(final UserSmoke usersmoke) {
		String query = "" +
				"INSERT INTO T_NF_USER_SMOKE " +
				"	( user_id, avg_smoke , status , reg_date,medSeq) " +
				"VALUES " +
				"	( ?, ?, ?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			
			usersmoke.getUserId(),
			usersmoke.getAvgSmoke(),
			usersmoke.getStatus(),
			usersmoke.getRegDate(),
			usersmoke.getMedSeq()
	
		});
		
		query = " select max(seq) from T_NF_USER_SMOKE where user_id =? "; 
		return this.jdbcTemplate.queryForInt(query, new Object[] {usersmoke.getUserId()});
				
	}

	public void deleteUserSmoke(int seq) {
		String query = "DELETE FROM T_NF_USER_SMOKE WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}
	
	public void deleteUserSmokebyId(String userId) {
		String query = "DELETE FROM T_NF_USER_SMOKE WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userId });
	}


	public void updateUserSmoke(UserSmoke usersmoke) { 
		String query = "" + 
				"UPDATE T_NF_USER_SMOKE SET " +
				
				"	user_id = ?, " +
				"	avg_smoke  = ?, " +
				"	reg_date  = ?, " +
				"	medSeq  = ?, " +
				"	status  = ? " +
				
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			usersmoke.getUserId(),
			usersmoke.getAvgSmoke(),
			usersmoke.getRegDate(),
			usersmoke.getMedSeq(),
			usersmoke.getStatus(),
			usersmoke.getSeq()
		});
	}

	public UserSmoke getUserSmoke(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_SMOKE " +
				"WHERE seq = ? ";
		return (UserSmoke)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.usersmokeMapper);
	}
	
	public UserSmoke getUserSmokeLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_SMOKE " +
				"WHERE user_id = ? order by reg_date desc, seq desc limit 1 ";
		try{
			return (UserSmoke)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.usersmokeMapper);
		}catch(Exception e){
			return new UserSmoke();
		}
	}
	public UserSmoke getUserSmokeMedSeq(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_SMOKE " +
				"WHERE medSeq = ? ";
		try{
			return (UserSmoke)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.usersmokeMapper);
		}catch(Exception e){
			return new UserSmoke();
		}
	}
	public List<UserSmoke> getUserSmokeList(String userId,int page, int itemCountPerPage) {
		String query = ""
	            + " SELECT * FROM ( "
	            + " 	SELECT "
	            + "			* "
	            + " 	FROM T_NF_USER_SMOKE "
	            + " 	where user_id = ? " 
	    		+ " 	ORDER BY reg_date DESC "
	    		+ " ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserSmoke>)this.jdbcTemplate.query(query, new Object[] { userId }, this.usersmokeMapper);
	}
	
	public int getUserSmokeCount(String userId) {
		String query = "" + 
				"SELECT count(*) " +
				"FROM T_NF_USER_SMOKE " +
				"WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId });
	}

}
