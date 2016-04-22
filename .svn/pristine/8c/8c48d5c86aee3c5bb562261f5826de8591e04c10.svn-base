package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.UserIndex;

public class UserIndexDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userindexMapper = new RowMapper() {
		public UserIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserIndex userindex = new UserIndex();
			userindex.setSeq(rs.getInt("seq"));
			userindex.setType(rs.getString("type"));
			userindex.setComment(rs.getString("comment"));
			return userindex;
		}
	};

	public void addUserIndex(final UserIndex userindex) {
		String query = "" +
				"INSERT INTO T_NF_USER_INDEX " +
				"	(seq, type, comment) " +
				"VALUES " +
				"	(?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			userindex.getSeq(),
			userindex.getType(),
			userindex.getComment()
		});
	}

	public void deleteUserIndex(String seq) {
		String query = "DELETE FROM T_NF_USER_INDEX WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void updateUserIndex(UserIndex userindex) { 
		String query = "" + 
				"UPDATE T_NF_USER_INDEX SET " +
				"	seq = ?, " +
				"	type = ?, " +
				"	comment = ? " +
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			userindex.getSeq(),
			userindex.getType(),
			userindex.getComment()
		});
	}

	public UserIndex getUserIndex(String type) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_USER_INDEX " +
				"WHERE type = ? ";
		return (UserIndex)this.jdbcTemplate.queryForObject(query, new Object[] { type }, this.userindexMapper);
	}

	public List<UserIndex> getUserIndexList(int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" seq, type, comment " +
				"FROM T_NF_USER_INDEX " +
				"WHERE seq <= ( " +
				"	SELECT MIN(seq) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" seq " +
				"		FROM T_NF_USER_INDEX " +
				"		ORDER BY seq DESC " +
				"	) AS A) " +
				"ORDER BY seq DESC";
		return (List<UserIndex>)this.jdbcTemplate.query(query, this.userindexMapper);
	}

}
