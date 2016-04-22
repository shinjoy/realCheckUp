package kr.nomad.mars.dto;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nomad.mars.dao.UserDao;
@Service
public class Calculate {
	
	@Autowired UserDao userDao;
	
	
	public int scoreStatus(int score,String userId){
		int status=0;
		
		User user = userDao.getUsers(userId);
		int age = user.getUserAge();
		int gender = user.getGender();
		if(19<age&&age<30){
			if(user.getGender()==User.MAN){
				if(85<score){
					status=1;
				}
				if(79<=score&&score<=85){
					status=2;
				}
				if(73<=score&&score<79){
					status=3;
				}
				if(59<=score&&score<73){
					status=4;
				}
				if(0<score&&score<59){
					status=5;
				}
			}else{
				if(92<score){
					status=1;
				}
				if(90<=score&&score<=92){
					status=2;
				}
				if(87<=score&&score<90){
					status=3;
				}
				if(68<=score&&score<87){
					status=4;
				}
				if(0<score&&score<68){
					status=5;
				}
				
			}
			
		}
		if(29<age&&age<40){
			if(user.getGender()==User.MAN){
				if(80<score){
					status=1;
				}
				if(76<=score&&score<=80){
					status=2;
				}
				if(71<=score&&score<76){
					status=3;
				}
				if(50<=score&&score<71){
					status=4;
				}
				if(0<score&&score<50){
					status=5;
				}
			}else{
				if(92<score){
					status=1;
				}
				if(89<=score&&score<=92){
					status=2;
				}
				if(86<=score&&score<89){
					status=3;
				}
				if(66<=score&&score<86){
					status=4;
				}
				if(0<score&&score<66){
					status=5;
				}
				
			}		
		}
		if(39<age&&age<50){
			if(user.getGender()==User.MAN){
				if(77<score){
					status=1;
				}
				if(73<=score&&score<=77){
					status=2;
				}
				if(69<=score&&score<73){
					status=3;
				}
				if(47<=score&&score<69){
					status=4;
				}
				if(0<score&&score<47){
					status=5;
				}
			}else{
				if(90<score){
					status=1;
				}
				if(87<=score&&score<=90){
					status=2;
				}
				if(83<=score&&score<87){
					status=3;
				}
				if(67<=score&&score<83){
					status=4;
				}
				if(0<score&&score<67){
					status=5;
				}
				
			}	
		}
		if(49<age&&age<60){
			if(user.getGender()==User.MAN){
				if(73<score){
					status=1;
				}
				if(69<=score&&score<=73){
					status=2;
				}
				if(65<=score&&score<69){
					status=3;
				}
				if(45<=score&&score<65){
					status=4;
				}
				if(score<45){
					status=5;
				}
			}else{
				if(86<score){
					status=1;
				}
				if(83<=score&&score<=86){
					status=2;
				}
				if(80<=score&&score<83){
					status=3;
				}
				if(63<=score&&score<80){
					status=4;
				}
				if(score<63){
					status=5;
				}
				
			}	
		}
		if(60<=age){
			if(user.getGender()==User.MAN){
				if(70<score){
					status=1;
				}
				if(64<=score&&score<=70){
					status=2;
				}
				if(60<=score&&score<64){
					status=3;
				}
				if(50<=score&&score<60){
					status=4;
				}
				if(score<50){
					status=5;
				}
			}else{
				if(85<score){
					status=1;
				}
				if(84<=score&&score<=85){
					status=2;
				}
				if(78<=score&&score<84){
					status=3;
				}
				if(75<=score&&score<78){
					status=4;
				}
				if(score<75){
					status=5;
				}
				
			}	
		}
		
		
		
		
		return status;
	}

}
