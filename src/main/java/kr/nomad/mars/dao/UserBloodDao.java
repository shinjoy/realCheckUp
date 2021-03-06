package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserBlood;

public class UserBloodDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userbloodMapper = new RowMapper() {
		public UserBlood mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserBlood userblood = new UserBlood();
			userblood.setSeq(rs.getInt("seq"));
			userblood.setUserId(rs.getString("user_id"));
			userblood.setBloodTime(rs.getInt("blood_time"));
			userblood.setBloodKind(rs.getInt("blood_kind"));
			userblood.setBloodSugar(rs.getInt("blood_sugar"));
			userblood.setStatus(rs.getInt("status"));
			userblood.setRegDate(rs.getString("reg_date"));
			userblood.setCheckSeq(rs.getInt("checkSeq"));
			return userblood;
		}
	};

	public int addUserBlood(final UserBlood userblood) {
		String query = "" +
				"INSERT INTO T_NF_USER_BLOODSUGAR " +
				"	( user_id, blood_time, blood_kind, "
				+ "blood_sugar, status , reg_date,checkSeq) " +
				"VALUES " +
				"	( ?, ?, ?,"
				+ " ?, ?, ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
			
			userblood.getUserId(),
			userblood.getBloodTime(),
			userblood.getBloodKind(),
			userblood.getBloodSugar(),
			userblood.getStatus(),
			userblood.getRegDate(),
			userblood.getCheckSeq()
		
		});
		query=" select max(seq) from T_NF_USER_BLOODSUGAR where user_id = ? ";
		return this.jdbcTemplate.queryForInt(query,new Object[] {userblood.getUserId()});
	}

	public void deleteUserBlood(int seq) {
		String query = "DELETE FROM T_NF_USER_BLOODSUGAR WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void deleteUserBloodbyId(String userId) {
		String query = "DELETE FROM T_NF_USER_BLOODSUGAR WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userId });
	}
	public void updateUserBlood(UserBlood userblood) { 
		String query = "" + 
				"UPDATE T_NF_USER_BLOODSUGAR SET " +
			
				"	user_id = ?, " +
				"	blood_time = ?, " +
				"	blood_kind = ?, " +
				"	blood_sugar = ?, " +
				"	reg_date = ?, " +
				"	checkSeq = ?, " +
				"	status  = ? " +
			
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			userblood.getUserId(),
			userblood.getBloodTime(),
			userblood.getBloodKind(),
			userblood.getBloodSugar(),
			userblood.getRegDate(),
			userblood.getCheckSeq(),
			userblood.getStatus(),
			userblood.getSeq()
	
		});
	}

	public UserBlood getUserBlood(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_BLOODSUGAR " +
				"WHERE seq = ? ";
		return (UserBlood)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userbloodMapper);
	}
	public UserBlood getUserBloodLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_BLOODSUGAR " +
				"WHERE user_id = ?  order by reg_date desc, seq desc limit 1 ";
		try{
			return (UserBlood)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.userbloodMapper);
		}catch(Exception e){
			return new UserBlood();
		}
	}
	public UserBlood getUserBloodCheckSeq(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_BLOODSUGAR " +
				"WHERE checkSeq = ?  ";
		try{
			return (UserBlood)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userbloodMapper);
		}catch(Exception e){
			return new UserBlood();
		}
	}
	public List<UserBlood> getUserBloodList(String userId,int page, int itemCountPerPage) {

		String query = ""
		            + " SELECT * FROM ( "
		            + " 	SELECT "
		            + "			* "
		            + " 	FROM T_NF_USER_BLOODSUGAR "
		            + " 	where user_id = ? " 
		    		+ " 	ORDER BY reg_date DESC "
		    		+ " ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserBlood>)this.jdbcTemplate.query(query, new Object[] { userId }, this.userbloodMapper);
	}
	
	public int getUserBloodCount(String userId) {
		String query = "" + 
				"SELECT count(*) " +
				"FROM T_NF_USER_BLOODSUGAR " +
				"WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId });
	}


}
