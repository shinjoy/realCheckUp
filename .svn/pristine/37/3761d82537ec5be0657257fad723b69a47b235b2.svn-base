package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.DataMap;
import kr.nomad.mars.dto.UserRegData;


public class UserRegDataDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userregdataMapper = new RowMapper() {
		public UserRegData mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRegData userregdata = new UserRegData();
			userregdata.setDataSeq(rs.getInt("data_seq"));
			userregdata.setUserId(rs.getString("user_id"));
			userregdata.setStomachCancer(rs.getDouble("stomach_cancer"));
			userregdata.setLiverCancer(rs.getDouble("liver_cancer"));
			userregdata.setLugCancer(rs.getDouble("lug_cancer"));
			userregdata.setColorectalCancer(rs.getDouble("colorectal_cancer"));
			userregdata.setBreastCancer(rs.getDouble("breast_cancer"));
			userregdata.setStroke(rs.getDouble("stroke"));
			userregdata.setHeartdisease(rs.getDouble("heartdisease"));
			userregdata.setDiabetesMellitus(rs.getDouble("diabetes_mellitus"));
			userregdata.setNephropathy(rs.getDouble("nephropathy"));
			userregdata.setDementia(rs.getDouble("dementia"));
			userregdata.setHealthScore(rs.getInt("health_score"));
			userregdata.setUserRank(rs.getInt("user_rank"));
			userregdata.setRegDate(rs.getString("reg_date"));
			userregdata.setStatus(rs.getInt("status"));
			return userregdata;
		}
	};
	

	private RowMapper VuserregdataMapper = new RowMapper() {
		public UserRegData mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRegData userregdata = new UserRegData();
			userregdata.setDataSeq(rs.getInt("data_seq"));
			userregdata.setUserId(rs.getString("user_id"));
			userregdata.setStomachCancer(rs.getDouble("stomach_cancer"));
			userregdata.setLiverCancer(rs.getDouble("liver_cancer"));
			userregdata.setLugCancer(rs.getDouble("lug_cancer"));
			userregdata.setColorectalCancer(rs.getDouble("colorectal_cancer"));
			userregdata.setBreastCancer(rs.getDouble("breast_cancer"));
			userregdata.setStroke(rs.getDouble("stroke"));
			userregdata.setHeartdisease(rs.getDouble("heartdisease"));
			userregdata.setDiabetesMellitus(rs.getDouble("diabetes_mellitus"));
			userregdata.setNephropathy(rs.getDouble("nephropathy"));
			userregdata.setDementia(rs.getDouble("dementia"));
			userregdata.setHealthScore(rs.getInt("health_score"));
			userregdata.setUserRank(rs.getInt("rownum"));
			userregdata.setRegDate(rs.getString("reg_date"));
			userregdata.setStatus(rs.getInt("status"));
			
			return userregdata;
		}
	};
	

	private RowMapper DataMapMapper = new RowMapper() {
		public DataMap mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			DataMap map= new DataMap();
			map.setMonth(rs.getString("month"));
			map.setNum(rs.getInt("num"));
			map.setRegDate(rs.getString("reg_date"));
			map.setUserId(rs.getString("user_id"));
			return map;
		}
	};
	
	private RowMapper HashMapper = new RowMapper() {
		public HashMap mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			HashMap map= new HashMap();
			map.put("count",rs.getInt("count"));
			map.put("step",rs.getInt("step"));
			
			return map;
		}
	};

	public void addUserRegData(final UserRegData userregdata) {
		String query = "" +
				"INSERT INTO T_NF_USER_REG_DATA " +
				"	( user_id, stomach_cancer,"
				+ " liver_cancer, lug_cancer, colorectal_cancer,"
				+ " breast_cancer, stroke, heartdisease,"
				+ " diabetes_mellitus, nephropathy, dementia"
				+ ", health_score, user_rank, reg_date,"
				+ " status) " +
				"VALUES " +
				"	( ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, sysdate(),"
				+ " ?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			userregdata.getUserId(),
			userregdata.getStomachCancer(),
			userregdata.getLiverCancer(),
			userregdata.getLugCancer(),
			userregdata.getColorectalCancer(),
			userregdata.getBreastCancer(),
			userregdata.getStroke(),
			userregdata.getHeartdisease(),
			userregdata.getDiabetesMellitus(),
			userregdata.getNephropathy(),
			userregdata.getDementia(),
			userregdata.getHealthScore(),
			userregdata.getUserRank(),

			userregdata.getStatus()
		});
	}

	public void deleteUserRegDataById(String user_id) {
		String query = "DELETE FROM T_NF_USER_REG_DATA WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { user_id });
	}

	public void updateUserRegData(UserRegData userregdata) { 
		String query = "" + 
				"UPDATE T_NF_USER_REG_DATA SET " +
				"	data_seq = ?, " +
				"	user_id = ?, " +
				"	stomach_cancer = ?, " +
				"	liver_cancer = ?, " +
				"	lug_cancer = ?, " +
				"	colorectal_cancer = ?, " +
				"	breast_cancer = ?, " +
				"	stroke = ?, " +
				"	heartdisease = ?, " +
				"	diabetes_mellitus = ?, " +
				"	nephropathy = ?, " +
				"	dementia = ?, " +
				"	health_score = ?, " +
				"	user_rank = ?, " +
				"	reg_date = ?, " +
				"	status = ? " +
				"WHERE data_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			userregdata.getDataSeq(),
			userregdata.getUserId(),
			userregdata.getStomachCancer(),
			userregdata.getLiverCancer(),
			userregdata.getLugCancer(),
			userregdata.getColorectalCancer(),
			userregdata.getBreastCancer(),
			userregdata.getStroke(),
			userregdata.getHeartdisease(),
			userregdata.getDiabetesMellitus(),
			userregdata.getNephropathy(),
			userregdata.getDementia(),
			userregdata.getHealthScore(),
			userregdata.getUserRank(),
			userregdata.getRegDate(),
			userregdata.getStatus()
		});
	}

	public UserRegData getUserRegData(String data_seq) {
		String query = "" + 
				"SELECT data_seq, user_id, stomach_cancer, liver_cancer, lug_cancer, colorectal_cancer, breast_cancer, stroke, heartdisease, diabetes_mellitus, nephropathy, dementia, health_score, user_rank, reg_date, status " +
				"FROM T_NF_USER_REG_DATA " +
				"WHERE data_seq = ? ";
		return (UserRegData)this.jdbcTemplate.queryForObject(query, new Object[] { data_seq }, this.userregdataMapper);
	}
	
	public UserRegData getUserRank(String user_id,String date,String type) {
		String query = "" 
				+" select * from ( "
				+" SELECT @RNUM := @RNUM + 1 AS ROWNUM, t.* "
				+ " FROM "
				+ " ( "
				+ "  SELECT * "
				+ "	FROM T_NF_USER_REG_DATA ) t, "
				+ "	( SELECT @RNUM := 0 ) R "
				+ "  where  reg_date = ? ORDER BY "+type+" desc) as b "
				+ "  where b.user_id = ? ";
		
		try{
			return this.jdbcTemplate.queryForObject(query, new Object[] { date,user_id }, this.VuserregdataMapper);
		}catch(Exception e){
			return new UserRegData();
		}
	}

	public List<UserRegData> getUserRegDataList(String userId,String type) {
		String query = "" 
				/*+ " select * from ( "
				+ "		 SELECT @RNUM := @RNUM + 1 AS ROWNUM, t.*  "
				+ "		 FROM  "
				+ "		 (  "
				+ "		 SELECT *  "
				+ "			FROM T_NF_USER_REG_DATA ) t,  "
				+ "		( SELECT @RNUM := 0 ) R "
				+ "		  ORDER BY "+type+" desc) as b "
				+ "		  where b.user_id = ? ";*/
				+" SELECT * "
				+" FROM ( "
				+"    SELECT a.*,  "
				+"            (CASE @regDate WHEN a.reg_date THEN @rownum:=@rownum+1 ELSE @rownum:=1 END) ROWNUM,  " 
				+"            (@regDate:=a.reg_date) regDate  "
				+"    FROM t_nf_user_reg_data a, (SELECT @regDate:='', @rownum:=0 FROM DUAL) b  "
				+"    "
				+"    ORDER BY a.reg_date desc ,a."+type+" desc      "             
				+"  ) c "
				+" where user_id= ? and 0< "+type+" ";
				
								
		return (List<UserRegData>)this.jdbcTemplate.query(query,new Object[] { userId }, this.VuserregdataMapper);
	}
	
	public List<DataMap> getUserRegDataListMonth(String userId,String type) {
		String query = "" 
				+" select user_id,avg("+type+") as num,reg_date ,DATE_FORMAT(reg_date, '%Y-%m')  as month "
					
				+"	FROM T_NF_USER_REG_DATA  "
						
				+"	 where user_id = ? and  reg_date >= date_sub( now() , interval  6  month ) "
				+"  && reg_date <= now()  group by user_id,month order by reg_date desc ";
				
		return (List<DataMap>)this.jdbcTemplate.query(query,new Object[] { userId }, this.DataMapMapper);
	}

	public List<DataMap> getUserRegDataListYear(String userId,String type) {
		String query = "" 
				+" select user_id,avg("+type+") as num,reg_date ,DATE_FORMAT(reg_date, '%Y')  as month "
					
				+"	FROM T_NF_USER_REG_DATA  "
						
				+"	 where user_id = ? and health_score >0 and  reg_date >= date_sub( now() , interval  5  year ) "
				+"  && reg_date <= now()  group by user_id,month order by reg_date desc ";
				
		return (List<DataMap>)this.jdbcTemplate.query(query,new Object[] { userId }, this.DataMapMapper);
	}
	
	
	public List<HashMap> getUserRegDataListRank(String type) {
		String query = "" 

		+" select count(*)as count,1 as step from "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 0<avgT.avg and  avgT.avg<11 "
			 
		+" union all "	
			
		+" select count(*)as count,2 as step from "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 10<avgT.avg and  avgT.avg<21 "		
			
		+" union all "	
			
		+" select count(*)as count,3 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 20<avgT.avg and  avgT.avg<31	 "	
		
		+" union all  "	
			
		+" select count(*)as count,4 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 30<avgT.avg and  avgT.avg<41	 "	
		
		+" union all	 "
			
		+" select count(*)as count,5 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 40<avgT.avg and  avgT.avg<51		 "
		
		+" union all "	
			
		+" select count(*)as count,6 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 50<avgT.avg and  avgT.avg<61		 "
		
		+" union all	 "
			
		+" select count(*)as count,7 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 60<avgT.avg and  avgT.avg<71	 "	
		
		+" union all "	
			
		+" select count(*)as count,8 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 70<avgT.avg and  avgT.avg<81 "		
		
		+" union all "	
			
		+" select count(*)as count,9 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 80<avgT.avg and  avgT.avg<91	 "	
		
		+" union all "	
			
		+" select count(*)as count,10 as step from  "
		+" 	(select avg("+type+")as avg,user_id from t_nf_user_reg_data  group by user_id) as avgT "
		+" 	where 90<avgT.avg and  avgT.avg<=100	 ";		

				
		return (List<HashMap>)this.jdbcTemplate.query(query, this.HashMapper);
	}
	
	
	public int getUserRegDatatotalCount() {
		String query =" "
				+ " select count(distinct(user_id)) from t_nf_user_reg_data as total " ;
		return this.jdbcTemplate.queryForInt(query);
	}
	
	public double getUserRegDataMyRank(String type,String userId) {
		String query =" "
				+" select ROWNUM from ( "
				+"		 SELECT @RNUM := @RNUM + 1 AS ROWNUM, t.* "
				+"		 FROM "
				+"		 ( "
				+"		 SELECT *,avg("+type+")as avg "
				+"			FROM T_NF_USER_REG_DATA group by user_id) t, "
				+"			( SELECT @RNUM := 0 ) R "
				+"			  ORDER BY t.avg desc) as b "
				+"		where user_id = ?	";
			
		return this.jdbcTemplate.queryForInt(query,new Object[] { userId });
	}
	
	public List<UserRegData> getUserRegDataListPaging(String userId,String type,int page,int itemCountPerPage) {
		String query = "" 
				/*+ " select * from ( "
				+ "		 SELECT @RNUM := @RNUM + 1 AS ROWNUM, t.*  "
				+ "		 FROM  "
				+ "		 (  "
				+ "		 SELECT *  "
				+ "			FROM T_NF_USER_REG_DATA ) t,  "
				+ "		( SELECT @RNUM := 0 ) R "
				+ "		  ORDER BY "+type+" desc) as b "
				+ "		  where b.user_id = ? ";*/
				+ " SELECT * "
		        + "	FROM ( "
				+"		 SELECT * "
				+" 			FROM ( "
				+"   	 SELECT a.*,  "
				+"            (CASE @regDate WHEN a.reg_date THEN @rownum:=@rownum+1 ELSE @rownum:=1 END) ROWNUM,  " 
				+"            (@regDate:=a.reg_date) regDate  "
				+"   	 FROM t_nf_user_reg_data a, (SELECT @regDate:='', @rownum:=0 FROM DUAL) b  "
				+"   	 "
				+"   	 ORDER BY a.reg_date desc ,a."+type+" desc      "             
				+"  	) c "
				+" 		where user_id= ? and 0 < "+type
				+ " 	ORDER BY reg_date DESC "
				+ "	) AS a LIMIT " + (page - 1) * itemCountPerPage + "," + itemCountPerPage;
								
		return (List<UserRegData>)this.jdbcTemplate.query(query,new Object[] { userId }, this.VuserregdataMapper);
	}
	public int getUserRegDataListCount(String userId) {
		String query = "" 
		
				+ " SELECT count(*) "
				+"   	 FROM t_nf_user_reg_data "
				+" 		where user_id= ?  ";
								
		return this.jdbcTemplate.queryForInt(query,new Object[] { userId });
	}
}
