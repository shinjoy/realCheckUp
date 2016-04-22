package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserDrink;



public class UserDrinkDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userdrinkMapper = new RowMapper() {
		public UserDrink mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDrink userDrink = new UserDrink();
			userDrink.setSeq(rs.getInt("seq"));
			userDrink.setUserId(rs.getString("user_id"));
			userDrink.setAvgDrinkingCapacity(rs.getInt("avg_drinking_capacity"));
			userDrink.setAvgPeriodDrinking(rs.getInt("avg_period_drinking"));
			userDrink.setStatus(rs.getInt("status"));
			userDrink.setRegDate(rs.getString("reg_date"));
			userDrink.setMedSeq(rs.getInt("medSeq"));
			return userDrink;
		}
	};

	public int addUserDrink(final UserDrink userDrink) {
		String query = "" +
				"INSERT INTO T_NF_USER_DRINK " +
				"	( user_id, avg_drinking_capacity ,avg_period_drinking, status , reg_date,medSeq) " +
				"VALUES " +
				"	( ?, ?, ?, ?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			
			userDrink.getUserId(),
			userDrink.getAvgDrinkingCapacity(),
			userDrink.getAvgPeriodDrinking(),
			userDrink.getStatus(),
			userDrink.getRegDate(),
			userDrink.getMedSeq()
	
		});
		query = " select max(seq) from T_NF_USER_DRINK where user_id = ? "; 
		return this.jdbcTemplate.queryForInt(query, new Object[] {userDrink.getUserId()});
	}

	public void deleteUserDrink(int seq) {
		String query = "DELETE FROM T_NF_USER_DRINK WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}
	public void deleteUserDrinkbyId(String userId) {
		String query = "DELETE FROM T_NF_USER_DRINK WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userId });
	}

	public void updateUserDrink(UserDrink userDrink) { 
		String query = "" + 
				"UPDATE T_NF_USER_DRINK SET " +
				
				"	user_id = ?, " +
				"	avg_drinking_capacity  = ?, " +
				"	avg_period_drinking  = ?, " +
				"	reg_date  = ?, " +
				"   medSeq= ?, "+
				"	status  = ? " +
				
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			userDrink.getUserId(),
			userDrink.getAvgDrinkingCapacity(),
			userDrink.getAvgPeriodDrinking(),
			userDrink.getRegDate(),
			userDrink.getMedSeq(),
			userDrink.getStatus(),
			userDrink.getSeq()
		});
	}

	public UserDrink getUserDrink(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_DRINK " +
				"WHERE seq = ? ";
		return (UserDrink)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userdrinkMapper);
	}
	
	public UserDrink getUserDrinkLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_DRINK " +
				"WHERE user_id = ?  order by reg_date desc, seq desc limit 1";
		try{
			return (UserDrink)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.userdrinkMapper);
		}catch(Exception e){
			return new UserDrink();
		}
	}
	public UserDrink getUserDrinkMedSeq(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_DRINK " +
				"WHERE medSeq = ? ";
		try{
			return (UserDrink)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userdrinkMapper);
		}catch(Exception e){
			return new UserDrink();
		}
	}
	public List<UserDrink> getUserSmokeList(String userId,int page, int itemCountPerPage) {
		String query = ""
	            + " SELECT * FROM ( "
	            + " 	SELECT "
	            + "			* "
	            + " 	FROM T_NF_USER_DRINK "
	            + " 	where user_id = ? " 
	    		+ " 	ORDER BY reg_date DESC "
	    		+ " ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserDrink>)this.jdbcTemplate.query(query, new Object[] { userId }, this.userdrinkMapper);
	}

	public int getUserDrinkCount(String userId) {
		String query = "" + 
				"SELECT count(*) " +
				"FROM T_NF_USER_DRINK " +
				"WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId });
	}
	


}
