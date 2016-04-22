package kr.nomad.mars.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import kr.nomad.mars.dto.Magazine;
import kr.nomad.mars.dto.Notice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MagazineDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper magazineMapper = new RowMapper() {
		public Magazine mapRow(ResultSet rs, int rowNum) throws SQLException {
			Magazine magazine = new Magazine();
			magazine.setmSeq(rs.getInt("m_seq"));
			magazine.setContents(rs.getString("contents"));
			magazine.setType(rs.getString("type"));
			magazine.setTitle(rs.getString("title"));
			magazine.setFileName(rs.getString("file_name"));
			magazine.setSubTitle(rs.getString("sub_title"));
			magazine.setThumFile(rs.getString("thum_file"));
			magazine.setRegDate(rs.getString("reg_date"));
			return magazine;
		}
	};

	public void addMagazine(final Magazine magazine) {
	    String query = ""
	            + "    INSERT INTO T_NF_DOCTOR_MAGAZINE "
	            + "        (type, title, sub_title, contents, file_name, thum_file, reg_date) "
	            + "    VALUES "
	            + "        (?, ?, ?, ?, ?, ?, SYSDATE())";
	    this.jdbcTemplate.update(query, new Object[] {
	    	magazine.getType(),
	    	magazine.getTitle(),
	    	magazine.getSubTitle(),
	    	magazine.getContents(),
	    	magazine.getFileName(),
	    	magazine.getThumFile()
	    });
	}
	public int getLastId() {
	    String query = "SELECT LAST_INSERT_ID(); ";
	    return this.jdbcTemplate.queryForInt(query );
	}

	public void updateMagazine(Magazine magazine) { 
		String query = "" + 
				"UPDATE T_NF_DOCTOR_MAGAZINE SET " +
				"	type = ?, " +
				"	title = ?, " +
				"	sub_title = ?, " +
				"	contents = ?, " +
				"	file_name = ?, " +
				"	thum_file = ? " +
				"WHERE m_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
	    	magazine.getType(),
	    	magazine.getTitle(),
	    	magazine.getSubTitle(),
	    	magazine.getContents(),
	    	magazine.getFileName(),
	    	magazine.getThumFile(),
			magazine.getmSeq()
		});
	}
	public void updateMagazineFile(int mSeq, String fileName) { 
		String query = "" + 
				"UPDATE T_NF_DOCTOR_MAGAZINE SET " +
				"	file_name = ?, " +
				"	thum_file = ? " +
				"WHERE m_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { fileName, fileName, mSeq });
	}


	public List<Magazine> getMagazineList(int page, int itemCountPerPage) {
		String query = ""
	            + "    SELECT * FROM ( "
	            + "         SELECT "
	            + "            * "
	            + "         FROM T_NF_DOCTOR_MAGAZINE "
	            + "			ORDER BY reg_date DESC "
	            + "    ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
	    return (List<Magazine>)this.jdbcTemplate.query(query, this.magazineMapper);
	}
	public int getCount() {
	    String query = " SELECT COUNT(*) FROM T_NF_DOCTOR_MAGAZINE ";
	    return this.jdbcTemplate.queryForInt(query);
	}

	public List<Magazine> getMagazineList(String keyword,String type, int page, int itemCountPerPage) {
		String con=" where 1=1 ";
		if(!keyword.equals("")){
			con+=" and (title like '%"+keyword+"%' OR subtitle like '%"+keyword+"%' OR contents like '%"+keyword+"%') ";
		}
		if(!type.equals("")){
			con+=" and type ='"+type+"' ";
		}
		String query = ""
	            + "    SELECT * FROM ( "
	            + "         SELECT "
	            + "            * "
	            + "         FROM T_NF_DOCTOR_MAGAZINE "
	            + "		    "+con
	            + "			ORDER BY reg_date DESC "
	            + "    ) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
	    return (List<Magazine>)this.jdbcTemplate.query(query,  this.magazineMapper);
	}
	public int getCount(String keyword,String type) {
		String con=" where 1=1 ";
		if(!keyword.equals("")){
			con+=" and (title like '%"+keyword+"%' OR subtitle like '%"+keyword+"%' OR contents like '%"+keyword+"%') ";
		}
		if(!type.equals("")){
			con+=" and type ='"+type+"' ";
		}
	    String query = " SELECT COUNT(*) FROM T_NF_DOCTOR_MAGAZINE  "+con;
	    return this.jdbcTemplate.queryForInt(query);
	}

	//처음 리스트
	public List<Magazine> getMagazineList() {
		String query = "" +
				"SELECT * FROM T_NF_DOCTOR_MAGAZINE order by reg_date desc";
		return (List<Magazine>)this.jdbcTemplate.query(query, this.magazineMapper);
	}
	
	//처음 뷰
	public Magazine getMagazine(int seq) {
		String query = "" +
				"SELECT * FROM T_NF_DOCTOR_MAGAZINE where m_seq = ?";
		return (Magazine)this.jdbcTemplate.queryForObject(query,  new Object[] { seq } ,this.magazineMapper);
	}

	
	public void deleteMagazinePageMain(int m_seq) {
		String query = "DELETE FROM T_NF_DOCTOR_MAGAZINE WHERE m_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { m_seq });
	}

	public List<Magazine> getRandomMagazineList(String keyword) {
		String con=" where 1=1 ";
		if(!keyword.equals("")){
			con+=" and type in("+keyword+")";
		}
		String query = ""
				+ "         SELECT "
	            + "            * "
	            + "         FROM T_NF_DOCTOR_MAGAZINE "
	            +  con
	            + "			ORDER BY rand() limit 3 ";
	      
	    return (List<Magazine>)this.jdbcTemplate.query(query, this.magazineMapper);
	}
	

	

}
