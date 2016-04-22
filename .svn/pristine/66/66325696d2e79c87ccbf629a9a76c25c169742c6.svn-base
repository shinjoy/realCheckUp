package kr.nomad.mars.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.MedExam;



public class MedExamDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper medexamMapper = new RowMapper() {
		public MedExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			MedExam medexam = new MedExam();
			medexam.setSeq(rs.getInt("seq"));
			medexam.setSort(rs.getInt("sort"));
			medexam.setComment(rs.getString("comment"));
			medexam.setValue(rs.getString("value"));
			medexam.setAskind(rs.getInt("askind"));
			medexam.setPseq(rs.getInt("pseq"));
			medexam.setMove(rs.getInt("move"));
			medexam.setAnsType(rs.getString("ans_type")); 
			medexam.setAnsvalue(rs.getString("ansvalue"));
			medexam.setIsLast(rs.getInt("is_last"));
			return medexam;
		}
	};

	public List<MedExam> getMedExamList() {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_MED_EXAM " +
				"WHERE askind in ( 1, 4) "+
				"ORDER BY sort ASC ";
	
			return (List<MedExam>)this.jdbcTemplate.query(query,this.medexamMapper);
	
	}

	public List<MedExam> getMedExamAnswerList(int pseq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_MED_EXAM " +
				"WHERE pseq = ? "+
				"ORDER BY sort ASC ";
		return (List<MedExam>)this.jdbcTemplate.query(query, new Object[] { pseq }, this.medexamMapper);
	}
}
