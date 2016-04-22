package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserActivity;

public class UserActivityDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper useractivityMapper = new RowMapper() {
		public UserActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserActivity useractivity = new UserActivity();
			useractivity.setSeq(rs.getInt("seq"));
			useractivity.setUserId(rs.getString("user_id"));
			useractivity.setAvgActivity(rs.getInt("avg_activity"));
			useractivity.setTimeActivity(rs.getInt("time_activity"));
			useractivity.setStatus(rs.getInt("status"));
			useractivity.setRegDate(rs.getString("reg_date"));
			useractivity.setMedSeq(rs.getInt("medSeq"));
			return useractivity;
		}
	};

	public int addUserActivity(final UserActivity useractivity) {
		String query = "" +
				"INSERT INTO T_NF_USER_ACTIVITY " +
				"	( user_id, avg_activity , time_activity ,"
				+ " status , reg_date,medSeq) " +
				"VALUES " +
				"	( ?, ?, ?,"
				+ " ?, ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
			
			useractivity.getUserId(),
			useractivity.getAvgActivity(),
			useractivity.getTimeActivity(),
			useractivity.getStatus(),
			useractivity.getRegDate(),
			useractivity.getMedSeq()
		
		});
		query=" select max(seq) from T_NF_USER_ACTIVITY where user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] {	useractivity.getUserId()});
	}

	public void deleteUserActivity(int seq) {
		String query = "DELETE FROM T_NF_USER_ACTIVITY WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}
	public void deleteUserActivitybyId(String userId) {
		String query = "DELETE FROM T_NF_USER_ACTIVITY WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userId });
	}

	public void updateUserActivity(UserActivity useractivity) { 
		String query = "" + 
				"UPDATE T_NF_USER_ACTIVITY SET " +
				
				"	user_id = ?, " +
				"	avg_activity  = ?, " +
				"	time_activity  = ?, " +
				"	reg_date  = ?, " +
				"	medSeq  = ?, " +
				"	status  = ? " +
			
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			
			useractivity.getUserId(),
			useractivity.getAvgActivity(),
			useractivity.getTimeActivity(),
			useractivity.getRegDate(),
			useractivity.getMedSeq(),
			useractivity.getStatus(),
			useractivity.getSeq()
		});
	}

	public UserActivity getUserActivity(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_ACTIVITY " +
				"WHERE seq = ? ";
		return (UserActivity)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.useractivityMapper);
	}
	
	public UserActivity getUserActivityLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_ACTIVITY " +
				"WHERE user_id = ?  order by reg_date desc, seq desc limit 1";
		try{
			return (UserActivity)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.useractivityMapper);
		}catch(Exception e){
			return new UserActivity();
		}
	}

	public UserActivity getUserActivityMedSeq(int medSeq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_ACTIVITY " +
				"WHERE medSeq = ?  ";
		try{
			return (UserActivity)this.jdbcTemplate.queryForObject(query, new Object[] { medSeq }, this.useractivityMapper);
		}catch(Exception e){
			return new UserActivity();
		}
	}
	public List<UserActivity> getUserActivityList(String userId,int page, int itemCountPerPage) {
		String query = ""
	            + " SELECT * FROM ( "
	            + " 	SELECT "
	            + "			* "
	            + " 	FROM T_NF_USER_ACTIVITY "
	            + " 	where user_id = ? " 
	    		+ " 	ORDER BY reg_date DESC "
	    		+ " ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserActivity>)this.jdbcTemplate.query(query, new Object[] { userId },  this.useractivityMapper);
	}
	
	public int getUserActivityCount(String userId) {
		String query = "" + 
				"SELECT count(*) " +
				"FROM T_NF_USER_ACTIVITY " +
				"WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId });
	}
}
