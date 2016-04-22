package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserPress;

public class UserPressDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userpressMapper = new RowMapper() {
		public UserPress mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserPress userpress = new UserPress();
			userpress.setSeq(rs.getInt("seq"));
			userpress.setUserId(rs.getString("user_id"));
			userpress.setSplessure(rs.getInt("splessure"));
			userpress.setDplessure(rs.getInt("dplessure"));
			userpress.setRegDate(rs.getString("reg_date"));
			userpress.setStatus(rs.getInt("status"));
			userpress.setCheckSeq(rs.getInt("checkSeq"));
			return userpress;
		}
	};
	
	private RowMapper VuserdataMapper = new RowMapper() {
		public UserPress mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserPress userpress = new UserPress();
			userpress.setSeq(rs.getInt("seq"));
			userpress.setUserId(rs.getString("user_id"));
			userpress.setNum1(rs.getInt("num1"));
			userpress.setNum2(rs.getInt("num2"));
			userpress.setRegDate(rs.getString("reg_date"));
			userpress.setStatusTxt(rs.getString("statustxt"));
			userpress.setStatusTxt2(rs.getString("statustxt2"));
			userpress.setType(rs.getString("type"));
			userpress.setStatus(rs.getInt("status"));
			userpress.setStatus2(rs.getInt("status2"));
			userpress.setTg(rs.getInt("tg"));
			userpress.setHdl(rs.getInt("hdl"));
			return userpress;
		}
	};

	public int addUserPress(final UserPress userpress) {
		String query = "" +
				"INSERT INTO T_NF_USER_PRESSURE " +
				"	( user_id, splessure, dplessure, reg_date, status,checkSeq) " +
				"VALUES " +
				"	( ?, ?, ?, ? , ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			userpress.getUserId(),
			userpress.getSplessure(),
			userpress.getDplessure(),
			userpress.getRegDate(),
			userpress.getStatus(),
			userpress.getCheckSeq()
		});
		
		query = "select max(seq) from t_nf_user_pressure where user_id = ? " ;
		return this.jdbcTemplate.queryForInt(query, new Object[] {userpress.getUserId()});
	}

	public void deleteUserPress(int seq) {
		String query = "DELETE FROM T_NF_USER_PRESSURE WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}
	public void deleteUserPressbyId(String userId) {
		String query = "DELETE FROM T_NF_USER_PRESSURE WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userId });
	}


	public void updateUserPress(UserPress userpress) { 
		String query = "" + 
				"UPDATE T_NF_USER_PRESSURE SET " +
			
				"	user_id = ?, " +
				"	splessure = ?, " +
				"	dplessure = ?, " +
				"	reg_date = ?, " +
				"	checkSeq = ?, " +
				"	status = ? " +
			
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			
			userpress.getUserId(),
			userpress.getSplessure(),
			userpress.getDplessure(),
			userpress.getRegDate(),
			userpress.getCheckSeq(),
			userpress.getStatus(),
			userpress.getSeq()
		});
	}

	public UserPress getUserPress(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_PRESSURE " +
				"WHERE seq = ? ";
		return (UserPress)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userpressMapper);
	}
	
	public UserPress getUserPressLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_PRESSURE " +
				"WHERE user_id = ? order by reg_date desc, seq desc limit 1 ";
		try{
			return (UserPress)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.userpressMapper);
		}catch(Exception e){
			return new UserPress();
		}
	}
	public UserPress getUserPressCheckSeq(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_PRESSURE " +
				"WHERE checkSeq = ? ";
		try{
			return (UserPress)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.userpressMapper);
		}catch(Exception e){
			return new UserPress();
		}
	}
	public List<UserPress> getUserDataList(String type,String userId,int page, int itemCountPerPage) {
			String con=" where 1=1 ";
			if(!type.equals("")){
				con+=" and user_id = '"+userId+"'";
			}
			if(!type.equals("")){
				con+=" and type= '"+type+"'";
			}
		
			String query = ""
			            + " SELECT * FROM ( "
			            + " 	SELECT "
			            + "			* "
			            + " 	FROM V_NF_USER_DATA "+con
			            + " 	 " 
			    		+ " 	ORDER BY reg_date DESC , seq desc "
			    		+ " ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserPress>)this.jdbcTemplate.query(query,this.VuserdataMapper);
	}
	
	public int getUserDataCount(String type,String userId) {
		String con=" where 1=1 ";
		if(!type.equals("")){
			con+=" and user_id = '"+userId+"'";
		}
		if(!type.equals("")){
			con+=" and type= '"+type+"'";
		}
		String query = "" + 
				"SELECT count(*) " +
				"FROM V_NF_USER_DATA "+con;
		return this.jdbcTemplate.queryForInt(query);
	}

}
