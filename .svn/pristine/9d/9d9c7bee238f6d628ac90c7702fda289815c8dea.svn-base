package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserAdvanced;




public class UserAdvancedDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper useradvancedMapper = new RowMapper() {
		public UserAdvanced mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAdvanced useradvanced = new UserAdvanced();
			useradvanced.setSeq(rs.getInt("seq"));
			useradvanced.setUserId(rs.getString("user_id"));
			useradvanced.setHeight(rs.getInt("height"));
			useradvanced.setUsedBloodpressure(rs.getInt("used_bloodpressure"));
			useradvanced.setUsedBloodsugar(rs.getInt("used_bloodsugar"));
			useradvanced.setUsedActivity(rs.getInt("used_activity"));
			useradvanced.setUsedSmoke(rs.getInt("used_smoke "));
			useradvanced.setUsedDrinking(rs.getInt("used_drinking"));
			useradvanced.setUsedCholesterol(rs.getInt("used_cholesterol"));
			useradvanced.setUsedWeight(rs.getInt("used_weight"));
			return useradvanced;
		}
	};

	public void addUserAdvanced(final UserAdvanced useradvanced) {
		String query = "" +
				"INSERT INTO T_NF_USER_ADVANCED " +
				"	(seq, user_id, height, used_bloodpressure, used_bloodsugar, used_activity, used_smoke , used_drinking, used_cholesterol, used_weight) " +
				"VALUES " +
				"	(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			useradvanced.getSeq(),
			useradvanced.getUserId(),
			useradvanced.getHeight(),
			useradvanced.getUsedBloodpressure(),
			useradvanced.getUsedBloodsugar(),
			useradvanced.getUsedActivity(),
			useradvanced.getUsedSmoke(),
			useradvanced.getUsedDrinking(),
			useradvanced.getUsedCholesterol(),
			useradvanced.getUsedWeight()
		});
	}

	public void deleteUserAdvanced(String seq) {
		String query = "DELETE FROM T_NF_USER_ADVANCED WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void updateUserAdvanced(UserAdvanced useradvanced) { 
		String query = "" + 
				"UPDATE T_NF_USER_ADVANCED SET " +
				"	seq = ?, " +
				"	user_id = ?, " +
				"	height = ?, " +
				"	used_bloodpressure = ?, " +
				"	used_bloodsugar = ?, " +
				"	used_activity = ?, " +
				"	used_smoke  = ?, " +
				"	used_drinking = ?, " +
				"	used_cholesterol = ?, " +
				"	used_weight = ? " +
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			useradvanced.getSeq(),
			useradvanced.getUserId(),
			useradvanced.getHeight(),
			useradvanced.getUsedBloodpressure(),
			useradvanced.getUsedBloodsugar(),
			useradvanced.getUsedActivity(),
			useradvanced.getUsedSmoke(),
			useradvanced.getUsedDrinking(),
			useradvanced.getUsedCholesterol(),
			useradvanced.getUsedWeight()
		});
	}

	public UserAdvanced getUserAdvanced(String seq) {
		String query = "" + 
				"SELECT seq, user_id, height, used_bloodpressure, used_bloodsugar, used_activity, used_smoke , used_drinking, used_cholesterol, used_weight " +
				"FROM T_NF_USER_ADVANCED " +
				"WHERE seq = ? ";
		return (UserAdvanced)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.useradvancedMapper);
	}

	public List<UserAdvanced> getUserAdvancedList(int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" seq, user_id, height, used_bloodpressure, used_bloodsugar, used_activity, used_smoke , used_drinking, used_cholesterol, used_weight " +
				"FROM T_NF_USER_ADVANCED " +
				"WHERE seq <= ( " +
				"	SELECT MIN(seq) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" seq " +
				"		FROM T_NF_USER_ADVANCED " +
				"		ORDER BY seq DESC " +
				"	) AS A) " +
				"ORDER BY seq DESC";
		return (List<UserAdvanced>)this.jdbcTemplate.query(query, this.useradvancedMapper);
	}

}
