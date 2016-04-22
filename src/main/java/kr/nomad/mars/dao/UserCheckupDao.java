package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserCheckup;

public class UserCheckupDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper usercheckupMapper = new RowMapper() {
		public UserCheckup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserCheckup usercheckup = new UserCheckup();
			usercheckup.setSeq(rs.getInt("seq"));
			usercheckup.setUserId(rs.getString("user_id"));
			usercheckup.setHeight(rs.getInt("height"));
			usercheckup.setAst(rs.getInt("ast"));
			usercheckup.setAlt(rs.getInt("alt"));
			usercheckup.setRedBlood(rs.getInt("red_blood"));
			usercheckup.setWhiteBlood(rs.getInt("white_blood"));
			usercheckup.setPlatelet(rs.getInt("platelet"));
			usercheckup.setFreet4(rs.getInt("freet4"));
			usercheckup.setTsh(rs.getInt("tsh"));
			usercheckup.setFev1(rs.getInt("fev1"));
			usercheckup.setCr(rs.getInt("cr"));
			usercheckup.setBun(rs.getInt("bun"));
			usercheckup.setRegDate(rs.getString("reg_date"));
			return usercheckup;
		}
	};

	public int addUserCheckup(final UserCheckup usercheckup) {
		String query = "" +
				"INSERT INTO t_nf_user_checkup " +
				"	( user_id, height, ast,"
				+ " alt, red_blood, white_blood,"
				+ " platelet, freet4, tsh,"
				+ " fev1, cr, bun,"
				+ " reg_date) " +
				"VALUES " +
				"	( ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " sysdate()) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			usercheckup.getUserId(),
			usercheckup.getHeight(),
			usercheckup.getAst(),
			usercheckup.getAlt(),
			usercheckup.getRedBlood(),
			usercheckup.getWhiteBlood(),
			usercheckup.getPlatelet(),
			usercheckup.getFreet4(),
			usercheckup.getTsh(),
			usercheckup.getFev1(),
			usercheckup.getCr(),
			usercheckup.getBun()
		
		});
		
		query = " select max(seq) from t_nf_user_checkup ";
		return this.jdbcTemplate.queryForInt(query);
	}

	public void deleteUserCheckup(String seq) {
		String query = "DELETE FROM t_nf_user_checkup WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void updateUserCheckup(UserCheckup usercheckup) { 
		String query = "" + 
				"UPDATE t_nf_user_checkup SET " +
			
				"	user_id = ?, " +
				"	height = ?, " +
				"	ast = ?, " +
				"	alt = ?, " +
				"	red_blood = ?, " +
				"	white_blood = ?, " +
				"	platelet = ?, " +
				"	freet4 = ?, " +
				"	tsh = ?, " +
				"	fev1 = ?, " +
				"	cr = ?, " +
				"	bun = ? " +
		
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			
			usercheckup.getUserId(),
			usercheckup.getHeight(),
			usercheckup.getAst(),
			usercheckup.getAlt(),
			usercheckup.getRedBlood(),
			usercheckup.getWhiteBlood(),
			usercheckup.getPlatelet(),
			usercheckup.getFreet4(),
			usercheckup.getTsh(),
			usercheckup.getFev1(),
			usercheckup.getCr(),
			usercheckup.getBun(),
			usercheckup.getSeq()
		});
	}

	public UserCheckup getUserCheckupLimit1(String userId) {
		String query = "" + 
				"SELECT * " +
				"FROM t_nf_user_checkup " +
				"WHERE user_id = ? order by reg_date desc, seq desc limit 1 ";
		try{
			return (UserCheckup)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, this.usercheckupMapper);
		}catch(Exception e){
			return new UserCheckup();
		}
	}

	public UserCheckup getUserCheckupSeq(int seq) {
		String query = "" + 
				"SELECT * " +
				"FROM t_nf_user_checkup " +
				"WHERE seq = ? ";
		try{
			return (UserCheckup)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.usercheckupMapper);
		}catch(Exception e){
			return new UserCheckup();
		}
	}
	public List<UserCheckup> getUserCheckupList(String userId,int page, int itemCountPerPage) {
		String query = ""
	            + " SELECT * "
	            + "	FROM ( "
	            + " 	SELECT "
	            + " 		* "
	            + " 	FROM t_nf_user_checkup "
	            + " where user_id = ? " 
	    		+ " 	ORDER BY reg_date DESC "
	    		+ "	) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
		return (List<UserCheckup>)this.jdbcTemplate.query(query, new Object[] { userId },this.usercheckupMapper);
	}
	public int getUserCheckupCount(String userId) {
		String query = ""
	            + " SELECT count(*) "
	         
	            + " FROM t_nf_user_checkup "
	            + " where user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId });
	}
}
