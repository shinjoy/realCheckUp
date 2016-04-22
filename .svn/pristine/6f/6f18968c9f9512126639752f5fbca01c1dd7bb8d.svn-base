package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.MagazineCounter;
public class MagazineCounterDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper magazinecounterMapper = new RowMapper() {
		public MagazineCounter mapRow(ResultSet rs, int rowNum) throws SQLException {
			MagazineCounter magazinecounter = new MagazineCounter();
			magazinecounter.setSeq(rs.getInt("seq"));
			magazinecounter.setUserId(rs.getString("user_id"));
			magazinecounter.setmSeq(rs.getInt("m_seq"));
			magazinecounter.setRegDate(rs.getString("reg_date"));
			return magazinecounter;
		}
	};

	public void addMagazineCounter(final MagazineCounter magazinecounter) {
		String query = "" +
				"INSERT INTO T_NF_MAGAZINE_COUNTER " +
				"	( user_id, m_seq, reg_date) " +
				"VALUES " +
				"	( ?, ?, sysdate()) ";
		this.jdbcTemplate.update(query, new Object[] {
			
			magazinecounter.getUserId(),
			magazinecounter.getmSeq()
		
		});
	}

	public void deleteMagazineCounter(String seq) {
		String query = "DELETE FROM T_NF_MAGAZINE_COUNTER WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { seq });
	}

	public void updateMagazineCounter(MagazineCounter magazinecounter) { 
		String query = "" + 
				"UPDATE T_NF_MAGAZINE_COUNTER SET " +
				"	seq = ?, " +
				"	user_id = ?, " +
				"	m_seq = ?, " +
				"	reg_date = ? " +
				"WHERE seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			magazinecounter.getSeq(),
			magazinecounter.getUserId(),
			magazinecounter.getmSeq(),
			magazinecounter.getRegDate()
		});
	}
	
	public int getMagazineCounterChk(int seq,String userId) {
		String query = "" + 
				"SELECT count(*) " +
				"FROM T_NF_MAGAZINE_COUNTER " +
				"WHERE m_seq = ? and user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { seq,userId });
	}

	public MagazineCounter getMagazineCounter(String seq) {
		String query = "" + 
				"SELECT seq, user_id, m_seq, reg_date " +
				"FROM T_NF_MAGAZINE_COUNTER " +
				"WHERE seq = ? ";
		return (MagazineCounter)this.jdbcTemplate.queryForObject(query, new Object[] { seq }, this.magazinecounterMapper);
	}

	public List<MagazineCounter> getMagazineCounterList(int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" seq, user_id, m_seq, reg_date " +
				"FROM T_NF_MAGAZINE_COUNTER " +
				"WHERE seq <= ( " +
				"	SELECT MIN(seq) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" seq " +
				"		FROM T_NF_MAGAZINE_COUNTER " +
				"		ORDER BY seq DESC " +
				"	) AS A) " +
				"ORDER BY seq DESC";
		return (List<MagazineCounter>)this.jdbcTemplate.query(query, this.magazinecounterMapper);
	}

}
