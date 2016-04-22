package kr.nomad.mars;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.nomad.mars.dao.ProcedureDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dao.UserRegDataDao;
import kr.nomad.mars.dto.Calculate;
import kr.nomad.mars.dto.Config;
import kr.nomad.mars.dto.Procedure;
import kr.nomad.mars.dto.User;
import kr.nomad.mars.dto.UserRegData;
import kr.nomad.util.T;

@Service

public class SchedularController {
	
	@Autowired UserDao userDao;
	@Autowired ProcedureDao procedureDao;
	@Autowired UserRegDataDao userRegDataDao;
	@Autowired Calculate calculate;

	@Scheduled(cron = "1 * 0 * * *") 
    public void regData() {
	
		String yesday = T.getDateAdd(T.getToday(), -1);
		
		List<User>userlist=userDao.getAllUser();
		for(int i =0;i<userlist.size();i++){
			User user = userlist.get(i);
			String userId =user.getUserId();
			int chk =procedureDao.getchkData(userId,yesday);
			if(chk>-1){
				List<Procedure> list = procedureDao.getData(userId, yesday);
				UserRegData ur = new UserRegData();
				ur.setUserId(userId);
				if(list.size()>0){
					
					for(int k=0;k<list.size();k++){
						Procedure pc = list.get(i);
						String code = pc.getDiseaseCode();
						Double num= pc.getRistRat();
					
						if(code.equals(Config.DISEASE_CODE_STOMACH_CANCER)){//위암
							ur.setStomachCancer(num);
						}
						if(code.equals(Config.DISEASE_CODE_LIVER_CANCER)){//간암
							ur.setLiverCancer(num);					
						}
						if(code.equals(Config.DISEASE_CODE_LUG_CANCER)){//폐암
							ur.setLugCancer(num);
						}
						if(code.equals(Config.DISEASE_CODE_COLORECTAL_CANCER)){//대장암
							ur.setColorectalCancer(num);
						}
						if(code.equals(Config.DISEASE_CODE_BREAST_CANCER)){ //유방암
							ur.setBreastCancer(num);
						}
						if(code.equals(Config.DISEASE_CODE_STROKE)){//뇌졸증
							ur.setStroke(num);
						}
						if(code.equals(Config.DISEASE_CODE_HEARTDISEASE)){//심질환
							ur.setHeartdisease(num);
						}
						if(code.equals(Config.DISEASE_CODE_DIABETES_MELLITUS)){//당뇨
							ur.setDiabetesMellitus(num);					
						}
						if(code.equals(Config.DISEASE_CODE_NEPHROPATHY)){//신장
							ur.setNephropathy(num);
						}
						if(code.equals(Config.DISEASE_CODE_DEMENTIA)){//치매
							ur.setDementia(num);
						}
	
					}
				}
				int score =procedureDao.getHealthScore(userId,yesday);
				ur.setHealthScore(score);
			
				int status =calculate.scoreStatus(score, userId);
				ur.setStatus(status);
				
				
				userRegDataDao.addUserRegData(ur);
			}
		}
		
		
		
	}
}