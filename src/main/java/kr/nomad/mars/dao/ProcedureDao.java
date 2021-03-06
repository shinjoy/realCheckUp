package kr.nomad.mars.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.Procedure;

public class ProcedureDao {
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper procedureMapper = new RowMapper() {
		public Procedure mapRow(ResultSet rs, int rowNum) throws SQLException {
			Procedure procedure = new Procedure();
			procedure.setDiseaseCode(rs.getString("disease_code"));
			procedure.setDiseaseName(rs.getString("disease_name"));
			procedure.setDiseaseType(rs.getInt("disease_type"));
			procedure.setRistRat(rs.getDouble("RISK_RAT"));
			
			return procedure;
		}
	};
	
	//프로시져 체크
	public int getchkData(String userId,String today) {
		
		String query = " call ASW_SP_MEM_GET_EXAM_DATA('"+userId+"','"+today+"',@1) ";
	  
		return this.jdbcTemplate.queryForInt(query);
	}
	
	//5대암 5대질병 
	public List<Procedure> getData(String userId,String today) {
		
		String query = " call ASW_SP_MEM_DICISION(?,?) ";
	  
		return this.jdbcTemplate.query(query,new Object[] { userId,today },this.procedureMapper);
	}
	
	//건강점수
	public int getHealthScore(String userId,String today) {
		
		String query = " call ASW_SP_MEM_HEALTH_INDEX(?,?) ";
	  
		return this.jdbcTemplate.queryForInt(query,new Object[] { userId,today });
	}
}
