package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserBasic;

public class UserBasicDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userbasicMapper = new RowMapper() {
		public UserBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserBasic userbasic = new UserBasic();
			userbasic.setSeq(rs.getInt("seq"));
			userbasic.setUserId(rs.getString("user_id"));
			userbasic.setPressure(rs.getInt("pressure"));
			userbasic.setBloodsugar(rs.getInt("bloodsugar"));
			userbasic.setCholesterol(rs.getInt("cholesterol"));
			userbasic.setLiver(rs.getInt("liver"));
			userbasic.setmPressure(rs.getInt("m_pressure"));
			userbasic.setmBloodsugar(rs.getInt("m_bloodsugar"));
			userbasic.setmCholesterol(rs.getInt("m_cholesterol"));
			userbasic.setmLiver(rs.getInt("f_liver"));
			userbasic.setfPressure(rs.getInt("f_pressure"));
			userbasic.setfBloodsugar(rs.getInt("f_bloodsugar"));
			userbasic.setfCholesterol(rs.getInt("f_cholesterol"));
			userbasic.setfLiver(rs.getInt("f_liver"));
			userbasic.setEatBreakfast(rs.getInt("eat_breakfast"));
			userbasic.setRegDate(rs.getString("reg_date"));
			return userbasic;
		}
	};

	public int addUserBasic(final UserBasic userbasic) {
		String query = "" +
				"INSERT INTO T_NF_USER_BASIC " +
				"	( user_id, "
				+ " pressure, bloodsugar,cholesterol, liver,"
				+ " m_pressure ,m_bloodsugar, m_cholesterol, m_liver,"
				+ " f_pressure ,f_bloodsugar, f_cholesterol, f_liver,"
				+ " eat_breakfast, reg_date) " +
				"VALUES " +
				"	( ?,"
				+ " ?, ?, ?, ?,"
				+ " ?, ?, ?, ?,"
				+ " ?, ?, ?, ?,"
				+ " ?, sysdate() ) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			userbasic.getUserId(),
			userbasic.getPressure(),
			userbasic.getBloodsugar(),
			userbasic.getCholesterol(),
			userbasic.getLiver(),
			userbasic.getmPressure(),
			userbasic.getmBloodsugar(),
			userbasic.getmCholesterol(),
			userbasic.getmLiver(),
			userbasic.getfPressure(),
			userbasic.getfBloodsugar(),
			userbasic.getfCholesterol(),
			userbasic.getfLiver(),
			userbasic.getEatBreakfast()
		
		});
		String query2 = " SELECT max(seq) FROM T_NF_USER_BASIC WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query2, new Object[] {userbasic.getUserId()});
	}
	//public int maxSeq(String userId) {
	

	public void deleteUserBasic(String seq) {
		String query = "DELETE FROM T_NF_USER_BASIC WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void updateUserBasic(UserBasic userbasic) { 
		String query = "" + 
				"UPDATE T_NF_USER_BASIC SET " +
			
			
				"	pressure = ?, " +
				"	bloodsugar = ?, " +
				"	cholesterol = ?, " +
				"	liver = ?, " +
				"	m_pressure  = ?, " +
				"	m_bloodsugar = ?, " +
				"	m_cholesterol = ?, " +
				"	m_liver = ?, " +
				"	f_pressure  = ?, " +
				"	f_bloodsugar = ?, " +
				"	f_cholesterol = ?, " +
				"	f_liver = ?, " +
				"	eat_breakfast = ? " +
			
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			
			
			userbasic.getPressure(),
			userbasic.getBloodsugar(),
			userbasic.getCholesterol(),
			userbasic.getLiver(),
			userbasic.getmPressure(),
			userbasic.getmBloodsugar(),
			userbasic.getmCholesterol(),
			userbasic.getmLiver(),
			userbasic.getfPressure(),
			userbasic.getfBloodsugar(),
			userbasic.getfCholesterol(),
			userbasic.getfLiver(),
			userbasic.getEatBreakfast(),
			userbasic.getSeq()
		});
	}

	public UserBasic getUserBasicLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_BASIC " +
				"WHERE user_id = ? order by reg_date desc,seq desc  limit 1 ";
		try{
			return (UserBasic)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.userbasicMapper);
		}catch(Exception e){
			return new UserBasic();
		}
	}
	
	
	public UserBasic getUserBasicSeq(int Seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_BASIC " +
				"WHERE seq = ?  ";
		try{
			return (UserBasic)this.jdbcTemplate.queryForObject(query, new Object[] { Seq }, this.userbasicMapper);
		}catch(Exception e){
			return new UserBasic();
		}
	}
	public List<UserBasic> getUserBasicList(String userId,int page, int itemCountPerPage) {
		String query = ""
	            + " SELECT * "
	            + "	FROM ( "
	            + " 	SELECT "
	            + " 		* "
	            + " 	FROM T_NF_USER_basic "
	            + " where user_id = ? " 
	    		+ " 	ORDER BY reg_date DESC "
	    		+ "	) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserBasic>)this.jdbcTemplate.query(query, new Object[] { userId }, this.userbasicMapper);
	}
	public int getUserBasicCount(String userId) {
		String query = ""
	            + " SELECT count(*) "
	         
	            + " 	FROM T_NF_USER_basic "
	            + " where user_id = ? " ;
		return this.jdbcTemplate.queryForInt(query,new Object[] { userId });
	}

}
