
package kr.nomad.mars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.oreilly.servlet.MultipartRequest;


import kr.nomad.mars.dao.ConfigDao;

import kr.nomad.mars.dao.MagazineDao;
import kr.nomad.mars.dao.MedExamDao;
import kr.nomad.mars.dao.NoticeDao;
import kr.nomad.mars.dao.ProcedureDao;
import kr.nomad.mars.dao.UserActivityDao;
import kr.nomad.mars.dao.UserAdvancedDao;
import kr.nomad.mars.dao.UserBasicDao;
import kr.nomad.mars.dao.UserBloodDao;
import kr.nomad.mars.dao.UserCheckupDao;
import kr.nomad.mars.dao.UserCholDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dao.UserDrinkDao;
import kr.nomad.mars.dao.UserIndexDao;
import kr.nomad.mars.dao.UserPressDao;
import kr.nomad.mars.dao.UserRegDataDao;
import kr.nomad.mars.dao.UserSmokeDao;
import kr.nomad.mars.dao.UserWeightDao;
import kr.nomad.mars.dto.Calculate;
import kr.nomad.mars.dto.Config;
import kr.nomad.mars.dto.DataMap;
import kr.nomad.mars.dto.Magazine;
import kr.nomad.mars.dto.MedExam;
import kr.nomad.mars.dto.Notice;
import kr.nomad.mars.dto.Procedure;
import kr.nomad.mars.dto.User;
import kr.nomad.mars.dto.UserActivity;
import kr.nomad.mars.dto.UserAdvanced;
import kr.nomad.mars.dto.UserBasic;
import kr.nomad.mars.dto.UserBlood;
import kr.nomad.mars.dto.UserCheckup;
import kr.nomad.mars.dto.UserChol;
import kr.nomad.mars.dto.UserDrink;
import kr.nomad.mars.dto.UserIndex;
import kr.nomad.mars.dto.UserPress;
import kr.nomad.mars.dto.UserRegData;
import kr.nomad.mars.dto.UserSmoke;
import kr.nomad.mars.dto.UserWeight;
import kr.nomad.util.F;
import kr.nomad.util.ImageUtil;
import kr.nomad.util.Response;
import kr.nomad.util.T;
import kr.nomad.util.XlsxWriter;
import kr.nomad.util.encrypt.CryptoNew;
import kr.nomad.util.file.UniqFileRenamePolicy;
import net.sf.json.JSONObject;

@Controller
public class ServerController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired UserDao userdao;
	@Autowired NoticeDao noticedao;
	@Autowired MagazineDao magaDao;
	@Autowired ConfigDao configdao;
	@Autowired UserPressDao userPressDao;
	@Autowired UserBloodDao userBloodDao;
	@Autowired UserCholDao userCholDao;
	@Autowired UserWeightDao userWeightDao;
	@Autowired UserActivityDao  userActivityDao;
	@Autowired UserSmokeDao userSmokeDao;
	@Autowired UserDrinkDao userDrinkDao;
	@Autowired UserAdvancedDao userAdvancedDao;
	@Autowired UserIndexDao userIndexDao;
	@Autowired MedExamDao medExamDao;
	@Autowired UserBasicDao userBasicDao;
	@Autowired UserCheckupDao userCheckupDao;
	@Autowired ProcedureDao procedureDao;
	@Autowired UserRegDataDao userRegDataDao;
	
	@Autowired Calculate calculate;
	
	// 페이지당 아이템 갯수
	@Value("#{config['page.itemCountPerPage']}")
	int ITEM_COUNT_PER_PAGE;

	// 페이징당 페이지 갯수
	@Value("#{config['page.pageCountPerPaging']}")
	int PAGE_COUNT_PER_PAGING;

	// 파일 루트
	@Value("#{config['file.root']}")
	String FILE_ROOT;

	String FILE_PATH = "";
	String FILE_LOCAL_PATH = "";

	// 파일 최대크기(Mb)
	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;

	public String SERVER_DOMAIN = "http://mint.aimmed.com:8070";
	public String GOOGLE_MAIL_ID = "aimmednote@gmail.com";
	public String GOOGLE_MAIL_PW = "Tkdfkdlxm*";
	
	public String DEFAULT_COUNSELOR_ID = "counselor@test.com";


	@RequestMapping("/api_view.go")
	public String wUserMenuController(Model model) {

		return "/api_view";
	}

	/**
	 * 회원가입
	 * 
	 * @param loginId
	 * @param loginPw
	 * @param res
	 * @return
	 */
	@RequestMapping("/m/aimmed_check.go")
	public String aimmedCheckController(
		@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
		@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
		@RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
		HttpServletRequest req, HttpServletResponse res
	) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		int isAimmedUser = 0;
		String userMobile = "";
		String userBirth = "";
		String groupCode = "";
		String groupName = "";
		String aimmedId = "";

		try {

			// 끌어모았어 get 요청 URL 문자열 사용 URLEncoder.encode 대한 특별한 및 안 보이는 문자 인코딩 진행하다
			String getURL = "http://api.aimmed.co.kr/recovery/check_user.asp";
			String secret = "AimmedRecover";
			getURL += "?name="+ URLEncoder.encode(userName, "UTF-8");
			getURL += "&mobile="+ phoneNumber;
			getURL += "&birth="+ birthday;
			getURL += "&secret="+ secret;
			
			//String getURL = GET_URL + "?name=" + URLEncoder.encode("zhangshan", "utf-8");
			URL getUrl = new URL(getURL);
			// 끌어모았어 URL을 열 따라 연결할 URL 형식은 따라 할 URL.openConnection 함수, 
			// 다시 다른 URLConnection 하위 클래스 대상, 여기 URL 한 http 때문에 실제 복귀 것은 HttpURLConnection
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			// 을 연결 하지만 실제로는 get request 반드시 다음 구의 connection.getInputStream () 함수 중 비로소 진정한 발 까지 서버
			connection.connect();
			// 확실한 입력 및 사용 Reader 읽기
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 인코딩 설정 을 함께 했 다., 그렇지 않으면, 중국어
			System.out.println("=============================");
			System.out.println("Contents of get request");
			System.out.println("=============================");
			String lines;
			String message = "";
			while ((lines = reader.readLine()) != null) {
				String str = new String(lines.getBytes());
				message += URLDecoder.decode(str, "utf-8");
				System.out.println(lines);
			}
			reader.close();
			// 연결 끊기
			connection.disconnect();
			
			JSONObject jsonObject = JSONObject.fromObject(message);
			String statusCode = jsonObject.getString("STATUS_CODE");
			
			if (statusCode.equals("200")) {
				
				//userName = jsonObject.getString("name");
				//userMobile = jsonObject.getString("mobile");
				//userBirth = jsonObject.getString("birth");
				groupCode = jsonObject.getString("group_code");
				groupName = jsonObject.getString("group_name");
				aimmedId = jsonObject.getString("mem_id");
				
				isAimmedUser = 1;
				
				map.put("aimmedId", aimmedId);
				map.put("groupCode", groupCode);
				map.put("groupName", groupName);
				map.put("message", "에임메드 회원입니다.");
				map.put("isAimmedUser", isAimmedUser);
			} else {
				map.put("aimmedId", aimmedId);
				map.put("groupCode", groupCode);
				map.put("groupName", groupName);
				map.put("message", "에임메드 회원이 아닙니다.");
				map.put("isAimmedUser", isAimmedUser);
			}


		} catch (Exception e) {
			map.put("aimmedId", aimmedId);
			map.put("groupCode", groupCode);
			map.put("groupName", groupName);
			map.put("message", e.getMessage());
			map.put("isAimmedUser", isAimmedUser);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;

	}


	
	/**
	 * 회원가입
	 * 
	 * @param loginId
	 * @param loginPw
	 * @param res
	 * @return
	 */
	@RequestMapping("/m/join.go")
	public String joinController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "userType", required = false, defaultValue = "0") int userType,
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
			@RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
			@RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
			@RequestParam(value = "loginNaver", required = false, defaultValue = "") int loginNaver,
			@RequestParam(value = "loginKakao", required = false, defaultValue = "") int loginKakao,
			@RequestParam(value = "osType", required = false, defaultValue = "") String osType,
			@RequestParam(value = "osVersion", required = false, defaultValue = "") String osVersion,
			@RequestParam(value = "appVersion", required = false, defaultValue = "") String appVersion,
			@RequestParam(value = "deviceName", required = false, defaultValue = "") String deviceName,
			@RequestParam(value = "deviceId", required = false, defaultValue = "") String deviceId,
			@RequestParam(value = "pushkey", required = false, defaultValue = "") String pushkey,
			@RequestParam(value = "usePushservice", required = false, defaultValue = "") int usePushservice,
			@RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
			@RequestParam(value = "aimmedId", required = false, defaultValue = "") String aimmedId,
			@RequestParam(value = "groupCode", required = false, defaultValue = "") String groupCode,
			@RequestParam(value = "groupName", required = false, defaultValue = "") String groupName,

	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			boolean userCheck = userdao.correctId(userId);
			if (userCheck) {
				map.put("result", false);
				map.put("message", "중복된 이메일 주소입니다. 다른 주소로 입력해 주십시오. 지속적인 문제 발생 시 (관리자이메일)로 이메일을 보내 주세요.");
			} else {
				
				
				User uu = new User();
				uu.setAppVersion(appVersion);
				uu.setBirthday(birthday);
				uu.setDeviceId(deviceId);
				uu.setDeviceName(deviceName);
				uu.setFileName(fileName);
				uu.setGender(gender);
				uu.setLoginKakao(loginKakao);
				uu.setLoginNaver(loginNaver);
				uu.setOsType(osType);
				uu.setOsVersion(osVersion);
				uu.setPhoneNumber(phoneNumber);
				uu.setPushkey(pushkey);
				uu.setStatus(1);
				uu.setUsePushservice(usePushservice);
				uu.setUserId(userId);
				uu.setUserName(userName);
				uu.setUserType(userType);
			
				uu.setAimmedId(aimmedId);
				uu.setGroupCode(groupCode);
				uu.setGroupName(groupName);
				uu.setPassword(password);
				uu.setApplyTerms(1);
				uu.setApplyPrivacy(1);
				userdao.addUser(uu);
			

				map.put("result", true);
				map.put("message", "회원가입이 완료되었습니다.");
			}
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", e.getMessage());
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;

	}



	// 사진 업로드
	@RequestMapping("/m/photo_upload.go")
	public String photoUploadController(HttpServletRequest req, HttpServletResponse res, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;

		String FILE_PATH = "/files/temp/";
		String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
		String userId = "";
		String photo = "";
		String fileName = "";
		String photoPre = "";
		String type = "";
		int isThumb = 0;
		int addThumb = 0;
		String orgFileName = "";

		int fileSize = FILE_MAX_SIZE * 1024 * 1024;

		try {
			req.setCharacterEncoding("utf-8");

			File file = null;
			try {
				MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH, fileSize, "UTF-8",
						new UniqFileRenamePolicy());

				// 폼에서 입력한 값을 가져옴
				userId = F.nullCheck(multi.getParameter("userId"), "");
				type = F.nullCheck(multi.getParameter("type"), "");
				isThumb = Integer.parseInt(F.nullCheck(multi.getParameter("isThumb"), "0"));
				addThumb = Integer.parseInt(F.nullCheck(multi.getParameter("addThumb"), "0"));
				orgFileName = F.nullCheck(multi.getParameter("fileName"), "");

				Enumeration files = multi.getFileNames();
				while (files.hasMoreElements()) {
					String elementName = (String) files.nextElement();
					file = multi.getFile(elementName);
					if (file != null) {
						photo = file.getName();
					}
				}

				if (type.equals("profile")) {
					fileName = userId + "." + photo.substring(photo.lastIndexOf(".") + 1, photo.length());
				} else {
					String yearMonth = T.getTodayYear() + T.getTodayMonth();

					fileName = photo;
					photoPre = yearMonth + "/";
				}
				if (isThumb == 1) {
					photoPre += "thumb/";
				}

				File copyFolder = new File(FILE_ROOT + "/files/" + type + "/" + photoPre);
				if (!copyFolder.exists()) {
					copyFolder.mkdirs();
				}

				// 파일 복사
				FileInputStream fis = new FileInputStream(FILE_LOCAL_PATH + photo);
				FileOutputStream fos = new FileOutputStream(FILE_ROOT + "/files/" + type + "/" + photoPre + fileName);
				int data = 0;
				while ((data = fis.read()) != -1) {
					fos.write(data);
				}
				fis.close();
				fos.close();

				// 필요시에 썸네일 저장
				if (addThumb == 1) {
					// 축소이미지 저장
					File newFile = new File(FILE_LOCAL_PATH + photo);
					File thumbFile = new File(FILE_ROOT + "/files/" + type + "/" + photoPre + "thumb/" + fileName);
					if (!thumbFile.exists()) {
						thumbFile.mkdirs();
					}
					try {
						ImageUtil.resize(newFile, thumbFile, 300, 0);
						result = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}


				// 복사한뒤 원본파일을 삭제함
				file.delete();
			} catch (Exception e) {
				e.getMessage();
			}

		
			map.put("photo", fileName);
			map.put("path", "/files/" + type + "/" + photoPre);
			// map.put("thumbPath", "/files/"+ type +"/thumb/"+photoPre);
			map.put("result", true);
			map.put("message", "사진이 등록되었습니다.");

		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "사진 등록에 실패하였습니다.\n" + e.getMessage());
		}

		JSONObject jsonObject = JSONObject.fromObject(map);

		Gson gson = new Gson();
		String outputstr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputstr);

		return null;
	}

	/* 프로필이미지 변경 */

	@RequestMapping("/m/profile_photo.go")
	public String proUploadController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId, 
			@RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
			
			HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			User user = userdao.getUsers(userId);
			if (user != null) {
				// 파일삭제

				if (fileName.equals("")) {
					filedelete(user.getFileName());
				}
			
				userdao.updateProfileImgAdd(userId,fileName);

				map.put("message", "프로필 이미지가 변경되었습니다.");
				map.put("result", true);
			} else {
				map.put("message", "존재하지 않는 ID 입니다.");
				map.put("result", false);
			}

		} catch (Exception e) {
			map.put("result", false);
			map.put("message", e.getMessage());
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	/**
	 * 로그인
	 * 
	 * @param loginId
	 * @param loginPw
	 * @param res
	 * @return
	 */
	@RequestMapping("/m/login.go")
	public String loginController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "os_version", required = false, defaultValue = "") String osVersion,
			@RequestParam(value = "os_type", required = false, defaultValue = "") String osType,
			@RequestParam(value = "device_name", required = false, defaultValue = "") String deviceName,
			@RequestParam(value = "device_id", required = false, defaultValue = "") String deviceId,
			@RequestParam(value = "pushKey", required = false, defaultValue = "") String pushKey,
			@RequestParam(value = "loginNaver", required = false, defaultValue = "") int loginNaver,
			@RequestParam(value = "loginKakao", required = false, defaultValue = "") int loginKakao,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		String enPw = "";
		try {

			boolean userCheck = userdao.correctId(userId);
			if (userCheck == true) {

				boolean loginCheck = false;

				if (loginNaver == 1) {
					loginCheck = userdao.correctNaver(userId);
				} else if (loginKakao == 1) {
					loginCheck = userdao.correctKakao(userId);
				} else {
					// enPw = Sha256Util.encryptPassword(user.getPassword());
					loginCheck = userdao.correctPw(userId, password);
				}

				if (loginCheck == true) {
					
					User userdata = userdao.getUsers(userId);
					if(userdata.getStatus()!=4){
						
					//	UserBasic userBasic = ubasicdao.getUserBasic(userId);
						HttpSession session = req.getSession();
						session.setAttribute("USER_ID", userdata.getUserId());
						session.setAttribute("USER_NAME", userdata.getUserName());
	
						User uu = new User();
						uu.setUserId(userId);
						uu.setPassword(password);
						uu.setOsType(osType);
						uu.setOsVersion(osVersion);
						uu.setDeviceName(deviceName);
						uu.setDeviceId(deviceId);
						uu.setPushkey(pushKey);
	
						userdao.updateUserData(uu);
	
						String birth = userdata.getBirthday();
						int birthyear = Integer.parseInt(birth.substring(0, 4));
						int nowyear = Integer.parseInt(T.getTodayYear());
						int age = nowyear - birthyear;
	
						map.put("message", "로그인이 성공되었습니다.");
						map.put("result", true);
						map.put("userName", userdata.getUserName());
						map.put("userType", userdata.getUserType());
						map.put("photo", userdata.getFileName());
					
						map.put("age", age);
						
						map.put("gender", userdata.getGender());
						
						map.put("loginNaver", loginNaver);
						map.put("loginKakao", loginKakao);
						map.put("loginEimmed", 0);
						map.put("gender", userdata.getGender());
					
						
					}else{
						map.put("message", "탈퇴한 사용자 입니다.");
						map.put("result", false);
					}

				} else {
					map.put("message", "비밀번호가 일치하지 않습니다.");
					map.put("result", false);
					map.put("userType", 3);
				}

			} else {
				map.put("message", "해당 아이디는 존재하지 않습니다.");
				map.put("result", false);
				map.put("userType", 3);
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
			map.put("userType", 3);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	/**
	 * 아이디중복체크
	 * 
	 * @return
	 */

	@RequestMapping("/m/dup_check_id.go")
	public String checkIdController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			boolean userCheck = userdao.correctId(userId);
			if (userCheck == true) {

				map.put("message", "중복된 이메일 주소입니다 다른주소로 입력해주세요. " + "지속적인 문제 발생시 (관리자이메일)로 이메일을 보내주세요");
				map.put("result", true);

			} else {

				map.put("message", "사용가능한 이메일입니다.");
				map.put("result", false);
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	/**
	 * 전화번호중복체크
	 * 
	 * @return
	 */

	@RequestMapping("/m/dup_check_phone.go")
	public String checkPhoneController(
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			boolean phoneCheck = userdao.correctPhone(phoneNumber);
			if (phoneCheck == true) {

				map.put("message", "사용중인 전화번호 입니다.");
				map.put("result", false);

			} else {

				map.put("message", "사용할 수 있는 전화번호입니다.");
				map.put("result", true);
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	/**
	 * 아이디찾기
	 * 
	 * @return
	 */
	@RequestMapping("/m/myid.go")
	public String searchIdController(
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			User user = userdao.findId(phoneNumber, userName);

			if (user != null) {
				String userId = user.getUserId();
				map.put("message", userId);
				map.put("result", true);

			} else {
				map.put("message", "회원님의 ID를 찾을 수 없습니다." 
						+ "아직 회원이 아니시면 아래 회원가입 버튼을 클릭해 주세요");
				map.put("result", false);

			}
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "error : " + e.getMessage());

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	/**
	 * 비밀번호찾기
	 * 
	 * @return
	 */

	@RequestMapping("/m/mypass.go")
	public String searchPwController(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		boolean userCheck = userdao.findPw(userId, phoneNumber);
		if (userCheck == false) {
			map.put("result", false);
			map.put("message", "회원님의 데이터를 찾을 수 없습니다." + "아직 회원이 아니시면 아래 회원가입 버튼을 클릭해 주세요");
		} else {
			try {

				map.put("result", true);
				map.put("message", "비밀번호를 재설정해주세요.");
			} catch (Exception e) {
				map.put("result", false);
				map.put("message", "error : " + e.getMessage());
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}


	
	/* 계정관리 */

	@RequestMapping("/m/myinfo.go")
	public String myinfoController(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			User user = userdao.getUsers(userId);
			map.put("data", user);
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	//프로필 수정
	@RequestMapping("/m/myinfo_edit_do.go")
	public String myinfoEditDoController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "gender", required = false, defaultValue = "") int gender,
			@RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
		
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			User user = userdao.getUsers(userId);
			if(user!=null){
				user.setGender(gender);
				user.setBirthday(birthday);
				user.setUserName(userName);
				user.setPhoneNumber(phoneNumber);
				userdao.updateUser(user);
				map.put("data", user);
				map.put("result", true);
			}else{
				map.put("message", "회원 정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
		

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	

	//푸시 설정
	@RequestMapping("/m/myinfo_push.go")
	public String myinfoPushController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			
			@RequestParam(value = "usePushservice", required = false, defaultValue = "") int usePushservice,
		
		
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			User user = userdao.getUsers(userId);
			if(user!=null){
				user.setUsePushservice(usePushservice);
			
				userdao.updateUser(user);
				map.put("data", user);
				map.put("result", true);
				map.put("message", "변경되었습니다.");
			}else{
				map.put("message", "회원 정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
		

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	/* 탈퇴 */

	@RequestMapping("/m/myinfo_drop_do.go")
	public String myinfoDropController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		String enPw = "";

		try {

			// enPw = Sha256Util.encryptPassword(password);
			boolean loginCheck = userdao.correctPw(userId, password);

			if (loginCheck) {
				
				User uu=userdao.getUser(userId);
				String fileName = uu.getFileName();
				userdao.deleteUser(userId);
				//프사지우기
				if(fileName!=null && !fileName.equals("")){
					filedelete(fileName);
					
				}
				userPressDao.deleteUserPressbyId(userId);
				userBloodDao.deleteUserBloodbyId(userId);
				userCholDao.deleteUserCholbyId(userId);
				userWeightDao.deleteUserWeightbyId(userId);
				userActivityDao.deleteUserActivitybyId(userId);
				userSmokeDao.deleteUserSmokebyId(userId);
				userDrinkDao.deleteUserDrinkbyId(userId);
				userdao.deleteUser(userId);
				userRegDataDao.deleteUserRegDataById(userId);
				
				map.put("message", "정상적으로 탈퇴되었습니다.");
				map.put("result", true);

			} else {
				map.put("message", "비밀번호가 일치하지않습니다");
				map.put("result", false);

			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	// 휴대전화변경
	@RequestMapping("/m/myinfo_phone_do.go")
	public String myphoneController(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		String enPw = "";

		try {
			// enPw = Sha256Util.encryptPassword(password);
			boolean loginCheck = userdao.correctPw(userId, password);
			if (loginCheck) {

				userdao.editPhone(userId, phoneNumber);
				map.put("message", "변경되었습니다");
				map.put("result", true);

			} else {
				map.put("message", "비밀번호가 일치하지않습니다");
				map.put("result", false);

			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	// 비밀번호재설정
	@RequestMapping("/m/new_mypass.go")
	public String mynpassController(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		String enPw = "";

		try {
			// enPw = Sha256Util.encryptPassword(password);

			userdao.updatePw(userId, password);
			map.put("message", "변경되었습니다");
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", "변경에 실패하였습니다.");
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	// 비밀번호변경
	@RequestMapping("/m/myinfo_pass_do.go")
	public String mypassController(@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "npassword", required = false, defaultValue = "") String npassword,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		String enPw = "";

		try {
			// enPw = Sha256Util.encryptPassword(password);
			boolean loginCheck = userdao.correctPw(userId, password);
			if (loginCheck) {
				// enPw = Sha256Util.encryptPassword(npassword);
				userdao.updatePw(userId, npassword);
				map.put("message", "변경되었습니다");
				map.put("result", true);

			} else {
				map.put("message", "비밀번호가 일치하지않습니다");
				map.put("result", false);

			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	//////////////////////////위험요인 및 관리
	
	//// 혈압 등록, 수정
	
	@RequestMapping("/m/pressure/edit_do.go")
	public String pressureEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "checkSeq", required = false, defaultValue = "0") int checkSeq,
			@RequestParam(value = "splessure", required = false, defaultValue = "0") int splessure,
			@RequestParam(value = "dplessure", required = false, defaultValue = "0") int dplessure,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			
			if(seq==0){//등록
				UserPress up = new UserPress();
				up.setUserId(userId);
				up.setSplessure(splessure);
				up.setDplessure(dplessure);
				up.setCheckSeq(checkSeq);
				int status=PressureCal(splessure,dplessure);
				up.setStatus(status);
				up.setRegDate(regDate);
				seq =userPressDao.addUserPress(up);
				
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
				
			}else{
				UserPress up= userPressDao.getUserPress(seq);
				up.setUserId(userId);
				up.setSplessure(splessure);
				up.setDplessure(dplessure);
				int status=PressureCal(splessure,dplessure);
				up.setStatus(status);
				up.setRegDate(regDate);
				userPressDao.updateUserPress(up);
				map.put("seq", seq);
				map.put("message", "변경되었습니다");
				map.put("result", true);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	////혈당 등록, 수정
	
	@RequestMapping("/m/bloodsugar/edit_do.go")
	public String bloodEditController(
			@RequestParam(value = "checkSeq", required = false, defaultValue = "0") int checkSeq,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "bloodTime", required = false, defaultValue = "0") int bloodTime,
			@RequestParam(value = "bloodKind", required = false, defaultValue = "0") int bloodKind,
			@RequestParam(value = "bloodSugar", required = false, defaultValue = "0") int bloodSugar,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserBlood ub = new UserBlood();
				ub.setUserId(userId);
				ub.setBloodTime(bloodTime);
				ub.setBloodSugar(bloodSugar);
				ub.setBloodKind(bloodKind);
				ub.setCheckSeq(checkSeq);
				int status=bloodSugarCal(bloodSugar);
				ub.setStatus(status);
				ub.setRegDate(regDate);
				seq=userBloodDao.addUserBlood(ub);
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
				
			}else{
				UserBlood ub = userBloodDao.getUserBlood(seq);
				ub.setUserId(userId);
				ub.setBloodTime(bloodTime);
				ub.setBloodSugar(bloodSugar);
				ub.setBloodKind(bloodKind);
				int status=bloodSugarCal(bloodSugar);
				ub.setStatus(status);
				ub.setRegDate(regDate);
				userBloodDao.updateUserBlood(ub);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	

	////콜레스테롤 등록, 수정
	
	@RequestMapping("/m/cholesterol/edit_do.go")
	public String cholesterolEditController(
			@RequestParam(value = "checkSeq", required = false, defaultValue = "0") int checkSeq,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "ldl", required = false, defaultValue = "0") int ldl,
			@RequestParam(value = "type", required = false, defaultValue = "0") int type,
			@RequestParam(value = "cholesterol", required = false, defaultValue = "0") int cholesterol,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
		
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserChol uc = new UserChol();
				uc.setLdl(ldl);
				uc.setCholesterol(cholesterol);
				uc.setUserId(userId);
				uc.setType(type);
				uc.setCheckSeq(checkSeq);
				int status = cholesterolCal(cholesterol);
				uc.setStatus(status);
				status=ldlCal(ldl);
				uc.setStatus2(status);
				uc.setRegDate(regDate);
				seq=userCholDao.addUserChol(uc);
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
				
			}else{
				UserChol uc= userCholDao.getUserChol(seq);
				uc.setLdl(ldl);
				uc.setCholesterol(cholesterol);
				uc.setUserId(userId);
				uc.setType(type);
				int status = cholesterolCal(cholesterol);
				uc.setStatus(status);
				status=ldlCal(ldl);
				uc.setStatus2(status);
				uc.setRegDate(regDate);
				userCholDao.updateUserChol(uc);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	////체중 등록, 수정
	
	@RequestMapping("/m/weight/edit_do.go")
	public String weightEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "checkSeq", required = false, defaultValue = "0") int checkSeq,
			@RequestParam(value = "weight", required = false, defaultValue = "0")  int weight,
			@RequestParam(value = "bmi", required = false, defaultValue = "0.0") Double bmi,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserWeight uw = new UserWeight();
				uw.setUserId(userId);
				uw.setWeight(weight);
				uw.setBmi(bmi);
				uw.setCheckSeq(checkSeq);
				int status = bmiCal(bmi);
				uw.setStatus(status);
				uw.setRegDate(regDate);
				seq=userWeightDao.addUserWeight(uw);
			
				
		
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
				
			}else{
				UserWeight uw = userWeightDao.getUserWeight(seq);
				uw.setUserId(userId);
				uw.setWeight(weight);
				uw.setBmi(bmi);
				int status = bmiCal(bmi);
				uw.setStatus(status);
				uw.setRegDate(regDate);
				userWeightDao.updateUserWeight(uw);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	////활동량 등록, 수정
	
	@RequestMapping("/m/activity/edit_do.go")
	public String activityEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "medSeq", required = false, defaultValue = "0") int medSeq,
			@RequestParam(value = "avgActivity", required = false, defaultValue = "0")  int avgActivity,
			@RequestParam(value = "timeActivity", required = false, defaultValue = "0") int timeActivity,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserActivity ua = new UserActivity();
				ua.setUserId(userId);
				ua.setAvgActivity(avgActivity);
				ua.setTimeActivity(timeActivity);
				int status=ActivityCal(avgActivity,timeActivity);
				 ua.setStatus(status);
				 ua.setMedSeq(medSeq);
				ua.setRegDate(regDate);
			    seq =userActivityDao.addUserActivity(ua);
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}else{
				UserActivity ua = userActivityDao.getUserActivity(seq);
				ua.setUserId(userId);
				ua.setAvgActivity(avgActivity);
				ua.setTimeActivity(timeActivity);
				int status=ActivityCal(avgActivity,timeActivity);
				 ua.setStatus(status);
				ua.setRegDate(regDate);
				userActivityDao.updateUserActivity(ua);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);

			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	////흡연량 등록, 수정
	
	@RequestMapping("/m/smoke/edit_do.go")
	public String smokeEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "medSeq", required = false, defaultValue = "0") int medSeq,
			@RequestParam(value = "avgSmoke", required = false, defaultValue = "0")  int avgSmoke,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserSmoke us= new UserSmoke();
				us.setAvgSmoke(avgSmoke);
				us.setUserId(userId);
				int status =smokeCal(avgSmoke);
				us.setStatus(status);
				us.setRegDate(regDate);
				us.setMedSeq(medSeq);
				seq = userSmokeDao.addUserSmoke(us);
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}else{
				UserSmoke us= userSmokeDao.getUserSmoke(seq);
				us.setAvgSmoke(avgSmoke);
				us.setUserId(userId);
				int status =smokeCal(avgSmoke);
				us.setStatus(status);
				us.setRegDate(regDate);
				userSmokeDao.updateUserSmoke(us);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	////음주 등록, 수정
	
	@RequestMapping("/m/drink/edit_do.go")
	public String drinkEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			@RequestParam(value = "medSeq", required = false, defaultValue = "0") int medSeq,
			@RequestParam(value = "avgPeriodDrinking", required = false, defaultValue = "0")  int avgPeriodDrinking,
			@RequestParam(value = "avgDrinkingCapacity", required = false, defaultValue = "0")  int avgDrinkingCapacity,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if(seq==0){//등록
				UserDrink ud = new UserDrink();
				ud.setUserId(userId);
				ud.setAvgDrinkingCapacity(avgDrinkingCapacity);
				ud.setAvgPeriodDrinking(avgPeriodDrinking);
				int status=drinkCal(avgPeriodDrinking,avgDrinkingCapacity);
				ud.setStatus(status);
				ud.setMedSeq(medSeq);
				ud.setRegDate(regDate);
				seq = userDrinkDao.addUserDrink(ud);
				map.put("message", "등록되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}else{
				UserDrink ud = userDrinkDao.getUserDrink(seq);
				ud.setUserId(userId);
				ud.setAvgDrinkingCapacity(avgDrinkingCapacity);
				ud.setAvgPeriodDrinking(avgPeriodDrinking);
				int status=drinkCal(avgPeriodDrinking,avgDrinkingCapacity);
				ud.setStatus(status);
				ud.setRegDate(regDate);
				userDrinkDao.updateUserDrink(ud);
				map.put("message", "변경되었습니다");
				map.put("result", true);
				map.put("seq", seq);
			}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	////한번에
	
	@RequestMapping("/m/all/edit_do.go")
	public String allEditController(
			
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "splessure", required = false, defaultValue = "0") int splessure,
			@RequestParam(value = "dplessure", required = false, defaultValue = "0") int dplessure,
			@RequestParam(value = "bloodTime", required = false, defaultValue = "0") int bloodTime,
			@RequestParam(value = "bloodKind", required = false, defaultValue = "0") int bloodKind,
			@RequestParam(value = "bloodNum", required = false, defaultValue = "0") int bloodSugar,
			@RequestParam(value = "ldl", required = false, defaultValue = "0") int ldl,
			@RequestParam(value = "cholesterol", required = false, defaultValue = "0") int cholesterol,
			@RequestParam(value = "weight", required = false, defaultValue = "0")  int weight,
			@RequestParam(value = "bmi", required = false, defaultValue = "0.0") Double bmi,
			@RequestParam(value = "avgActivity", required = false, defaultValue = "0")  int avgActivity,
			@RequestParam(value = "timeActivity", required = false, defaultValue = "0") int timeActivity,
			@RequestParam(value = "avgSmoke", required = false, defaultValue = "0")  int avgSmoke,
			@RequestParam(value = "avgPeriodDrinking", required = false, defaultValue = "0")  int avgPeriodDrinking,
			@RequestParam(value = "avgDrinkingCapacity", required = false, defaultValue = "0")  int avgDrinkingCapacity,
			@RequestParam(value = "regDate", required = false, defaultValue = "0") String regDate,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			UserPress up = new UserPress();
			up.setUserId(userId);
			up.setSplessure(splessure);
			up.setDplessure(dplessure);
			int status=PressureCal(splessure,dplessure);
			up.setStatus(status);
			up.setRegDate(regDate);
			userPressDao.addUserPress(up);
			
			UserBlood ub = new UserBlood();
			ub.setUserId(userId);
			ub.setBloodTime(bloodTime);
			ub.setBloodSugar(bloodSugar);
			ub.setBloodKind(bloodKind);
			status=bloodSugarCal(bloodSugar);
			ub.setStatus(status);
			ub.setRegDate(regDate);
			userBloodDao.addUserBlood(ub);
			
			UserChol uc = new UserChol();
			uc.setLdl(ldl);
			uc.setCholesterol(cholesterol);
			uc.setUserId(userId);
			status = cholesterolCal(cholesterol);
			uc.setStatus(status);
			status=ldlCal(ldl);
			uc.setStatus2(status);
			uc.setRegDate(regDate);
			userCholDao.addUserChol(uc);
			
			UserWeight uw = new UserWeight();
			uw.setUserId(userId);
			uw.setWeight(weight);
			uw.setBmi(bmi);
			status = bmiCal(bmi);
			uw.setStatus(status);
			uw.setRegDate(regDate);
			userWeightDao.addUserWeight(uw);
			
			UserActivity ua = new UserActivity();
			ua.setUserId(userId);
			ua.setAvgActivity(avgActivity);
			ua.setTimeActivity(timeActivity);
			status=ActivityCal(avgActivity,timeActivity);
			ua.setStatus(status);
			ua.setRegDate(regDate);
		    userActivityDao.addUserActivity(ua);
		    
		    UserSmoke us= new UserSmoke();
			us.setAvgSmoke(avgSmoke);
			us.setUserId(userId);
			status =smokeCal(avgSmoke);
			us.setStatus(status);
			us.setRegDate(regDate);
			userSmokeDao.addUserSmoke(us);
			
			UserDrink ud = new UserDrink();
			ud.setUserId(userId);
			ud.setAvgDrinkingCapacity(avgDrinkingCapacity);
			ud.setAvgPeriodDrinking(avgPeriodDrinking);
			status=drinkCal(avgPeriodDrinking,avgDrinkingCapacity);
			ud.setStatus(status);
			ud.setRegDate(regDate);
			userDrinkDao.addUserDrink(ud);
			
			map.put("message", "등록되었습니다");
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	//// 삭제
	
	@RequestMapping("/m/userData/delete.go")
	public String pressureDeleteController(
			@RequestParam(value = "diseaseId", required = false, defaultValue = "") String diseaseId,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
				User user= userdao.getUsers(userId);
				if(user!=null){
					if(diseaseId.equals(Config.DISEASE_PRESSURE)){
						userPressDao.deleteUserPress(seq);
					}
					if(diseaseId.equals(Config.DISEASE_BLOODSUGAR)){
						userBloodDao.deleteUserBlood(seq);
					}
					if(diseaseId.equals(Config.DISEASE_CHOLESTEROL)){
						userCholDao.deleteUserChol(seq);
					}
					if(diseaseId.equals(Config.DISEASE_WEIGHT)){
						userWeightDao.deleteUserWeight(seq);
					}
					if(diseaseId.equals(Config.DISEASE_ACTIVITY)){
						userActivityDao.deleteUserActivity(seq);
					}
					if(diseaseId.equals(Config.DISEASE_SMOKE)){
						userSmokeDao.deleteUserSmoke(seq);
					}
					if(diseaseId.equals(Config.DISEASE_DRINK)){
						userDrinkDao.deleteUserDrink(seq);
					}
					map.put("message", "삭제되었습니다");
					map.put("result", true);
					
				
				}else{
					map.put("message", "조회 불가능한 회원입니다.");
					map.put("result", false);
				}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	@RequestMapping("/m/userDataList/list.go")
	public String userDataListController(
			@RequestParam(value = "diseaseId", required = false, defaultValue = "") String diseaseId,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			
				List list=new  ArrayList();
				int count=0;
				List iconlist= new ArrayList();
				User user= userdao.getUsers(userId);
				if(user!=null){
					
					UserPress up =userPressDao.getUserPressLimit1(userId);
					UserBlood ub= userBloodDao.getUserBloodLimit1(userId);
					UserChol uc = userCholDao.getUserCholLimit1(userId);
					UserWeight uw = userWeightDao.getUserWeightLimit1(userId);
					UserActivity ua = userActivityDao.getUserActivityLimit1(userId);
					UserSmoke us = userSmokeDao.getUserSmokeLimit1(userId);
					UserDrink ud= userDrinkDao.getUserDrinkLimit1(userId);
					
					if(up.getStatus()!=2&&up.getStatus()!=0){
						iconlist.add("혈압이미지경로");
					}
					if(ub.getStatus()!=2&&ub.getStatus()!=0){
						iconlist.add("혈당이미지경로");
					}
					if(uc.getStatus()!=2&&uc.getStatus()!=0){
						iconlist.add("콜레스테롤이미지경로");
					}
					if(uw.getStatus()!=2&&uw.getStatus()!=0){
						iconlist.add("체중이미지경로");
					}
					if(ua.getStatus()==3){
						iconlist.add("활동이미지경로");
					}
					if(0<us.getStatus()){
						iconlist.add("흡연이미지경로");
					}
					if(0<ud.getStatus()){
						iconlist.add("음주이미지경로");
					}
					String goal="";
					list=userPressDao.getUserDataList(diseaseId,userId,page, ITEM_COUNT_PER_PAGE);
					count=userPressDao.getUserDataCount(diseaseId,userId);
					UserIndex ui=new UserIndex();
					if(diseaseId.equals(Config.DISEASE_DRINK)){
						ui=userIndexDao.getUserIndex(diseaseId+user.getGender());
					
						
					}else if(!diseaseId.equals("")){
						ui=userIndexDao.getUserIndex(diseaseId);
					}
					String type="";
					if(diseaseId.equals(Config.DISEASE_DRINK)){
						type="음주 조절";
					}
					if(diseaseId.equals(Config.DISEASE_SMOKE)){
						type="흡연 조절";
					}
					if(diseaseId.equals(Config.DISEASE_ACTIVITY)){
						type="활동량";
					}
					if(diseaseId.equals(Config.DISEASE_BLOODSUGAR)){
						type="혈당";
					}
					if(diseaseId.equals(Config.DISEASE_PRESSURE)){
						type="혈압";
					}
					if(diseaseId.equals(Config.DISEASE_CHOLESTEROL)){
						type="콜레스테롤";
					}
					if(diseaseId.equals(Config.DISEASE_WEIGHT)){
						type="체중";
					}
					
					goal=ui.getComment();
					map.put("result", true);
					map.put("iconlist", iconlist);
					map.put("list", list);
					map.put("goal",user.getUserName()+"님의 "+type+" 목표는 "+goal+" 입니다.");
					map.put("count", count);
					
				
				}else{
					map.put("message", "조회 불가능한 회원입니다.");
					map.put("result", false);
				}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	//사용자 위험요인
	@RequestMapping("/m/userDanger/list.go")
	public String userDangerListController(
		
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			
			
				List list= new ArrayList();
				User user= userdao.getUsers(userId);
				if(user!=null){
					
					UserPress up =userPressDao.getUserPressLimit1(userId);
					UserBlood ub= userBloodDao.getUserBloodLimit1(userId);
					UserChol uc = userCholDao.getUserCholLimit1(userId);
					UserWeight uw = userWeightDao.getUserWeightLimit1(userId);
					UserActivity ua = userActivityDao.getUserActivityLimit1(userId);
					UserSmoke us = userSmokeDao.getUserSmokeLimit1(userId);
					UserDrink ud= userDrinkDao.getUserDrinkLimit1(userId);
					
					
					if(up.getStatus()!=2&&up.getStatus()!=0){
						list.add(Config.DISEASE_PRESSURE);
					}
					if(ub.getStatus()!=2&&ub.getStatus()!=0){
						list.add(Config.DISEASE_BLOODSUGAR);
					}
					if(uc.getStatus()!=2&&uc.getStatus()!=0){
						list.add(Config.DISEASE_CHOLESTEROL);
					}
					if(uw.getStatus()!=2&&uw.getStatus()!=0){
						list.add(Config.DISEASE_WEIGHT);
					}
					if(ua.getStatus()==3){
						list.add(Config.DISEASE_ACTIVITY);
					}
					if(0<us.getStatus()){
						list.add(Config.DISEASE_SMOKE);
					}
					if(0<ud.getStatus()){
						list.add(Config.DISEASE_DRINK);
					}
					
					map.put("result", true);
					map.put("list", list);
				
					
				
				}else{
					map.put("message", "조회 불가능한 회원입니다.");
					map.put("result", false);
				}

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	

	
	//공지사항 진입 시점 
	@RequestMapping("/m/service.go")
	public String noticeController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			User uu = userdao.getUsers(userId);
			
			map.put("cnt", uu.getNoticeBadge());//내 뱃지갯수
			
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	// 문진

	@RequestMapping("/m/med_exam.go")
	public String medExamController(
	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		List<MedExam> list = medExamDao.getMedExamList();
		for (int i = 0; i < list.size(); i++) {
			MedExam me = (MedExam) list.get(i);
			if (me.getAskind() == 1 || me.getAskind() == 4) {
				List<MedExam> answerList = medExamDao.getMedExamAnswerList(me.getSeq());
				me.setAnswerList(answerList);
				list.set(i, me);
			}
		}
		map.put("list", list);
		map.put("result", true);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	
	// 문진등록
	@RequestMapping("/m/basic/edit_do.go")
	public String basicEditController(
		 UserBasic ub,
	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user= userdao.getUsers(ub.getUserId());
		int seq=0;
		if(user!=null){
			if(ub.getSeq()==0){
				seq =userBasicDao.addUserBasic(ub);
			}else{
				userBasicDao.updateUserBasic(ub);
				seq=ub.getSeq();
			}
				map.put("seq", seq);
				map.put("result", true);
				map.put("message", "등록되었습니다.");
		}else{
			map.put("result", false);
			map.put("message", "조회 불가능한 회원입니다.");
		}
				
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	// 번호업데이트

	@RequestMapping("/m/num/edit_do.go")
	public String examNumEditController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		@RequestParam(value = "num", required = false, defaultValue = "0") int num,
		@RequestParam(value = "type", required = false, defaultValue = "0") int type,//1문진 2 검진
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(type==1){
				userdao.updateExamNum(userId,num);
			}else{
				userdao.updateCheckNum(userId,num);
			}
		
			map.put("result", true);
		}catch(Exception e){
			map.put("result", false);
			map.put("message", e.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	// 문진리스트 받기
	@RequestMapping("/m/basic_list.go")
	public String basicListController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userdao.getUsers(userId);
			if (user != null) {
				List list =userBasicDao.getUserBasicList(userId, page, ITEM_COUNT_PER_PAGE);
				int count = userBasicDao.getUserBasicCount(userId);
				
			
				map.put("result", true);
				map.put("list", list);
				map.put("count", count);
			
			} else {
				map.put("result", false);
				map.put("message", "조회 불가능한 회원입니다.");
			}
		}catch(Exception e){
			map.put("result", false);
			map.put("message", e.getMessage());
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}


	
	
	// 문진데이터 받기
	@RequestMapping("/m/basic_info.go")
	public String basicInfoController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,	
		@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,	
	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user= userdao.getUsers(userId);
		if(user!=null){
			
			
			UserBasic ub = userBasicDao.getUserBasicSeq(seq);
			UserActivity ua = userActivityDao.getUserActivityMedSeq(seq);
			UserSmoke us = userSmokeDao.getUserSmokeMedSeq(seq);
			UserDrink ud= userDrinkDao.getUserDrinkMedSeq(seq);
		
		
			map.put("result", true);
			map.put("basic", ub);
			map.put("activity", ua);
			map.put("smoke", us);
			map.put("drink", ud);
			map.put("num", user.getExamNum());
		}else{
			map.put("result", false);
			map.put("message", "조회 불가능한 회원입니다.");
		}
				
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	// 검진리스트

	@RequestMapping("/m/checkup_list.go")
	public String checkupListController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,	
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,	
	HttpServletRequest req, HttpServletResponse res) {

	Map<String, Object> map = new HashMap<String, Object>();
	try{
		User user = userdao.getUsers(userId);
		if (user != null) {
			
			List list = userCheckupDao.getUserCheckupList(userId, page, ITEM_COUNT_PER_PAGE);
			int count = userCheckupDao.getUserCheckupCount(userId);
			
			map.put("result", true);
			map.put("list", list);
			map.put("count", count);
		
			
		} else{
			map.put("result", false);
			map.put("message", "조회 불가능한 회원입니다.");
		}

	}catch(Exception e){
		map.put("result", false);
		map.put("message", e.getMessage());
	}
	
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	// 검진등록

	@RequestMapping("/m/checkup/edit_do.go")
	public String checkupEditController(
	 UserCheckup uc,		
	HttpServletRequest req, HttpServletResponse res) {

	Map<String, Object> map = new HashMap<String, Object>();
	User user = userdao.getUsers(uc.getUserId());
	if (user != null) {
		int seq =0;
		if(uc.getSeq()==0){
			seq=userCheckupDao.addUserCheckup(uc);
		}else{
			userCheckupDao.updateUserCheckup(uc);
			seq=uc.getSeq();
		}
		map.put("seq", seq);
		map.put("result", true);
		map.put("message", "등록되었습니다.");
		
	} else{
		map.put("result", false);
		map.put("message", "조회 불가능한 회원입니다.");
	}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	// 검진데이터 받기
	@RequestMapping("/m/checkup_info.go")
	public String checkupInfoController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,	
		@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,	
	HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user= userdao.getUsers(userId);
		if(user!=null){
			UserCheckup uc = userCheckupDao.getUserCheckupSeq(seq);
			UserWeight uw = userWeightDao.getUserWeightCheckSeq(seq);
			UserPress up = userPressDao.getUserPressCheckSeq(seq);
			UserBlood ub = userBloodDao.getUserBloodCheckSeq(seq);
			UserChol ucol = userCholDao.getUserCholCheckCheckSeq(seq);
			map.put("checkup", uc);
			map.put("weight", uw);
			map.put("pressure", up);
			map.put("bloodsugar", ub);
			map.put("cholesterol", ucol);
			map.put("num",user.getCheckNum());
		}else{
			map.put("result", false);
			map.put("message", "조회 불가능한 회원입니다.");
		}
				
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	
	/////
	/*5대암 5대질병  */
	@RequestMapping("/m/main.go")
	public String MainController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		@RequestParam(value = "date", required = false, defaultValue = "") String date,
	
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
		
			User user =userdao.getUsers(userId);
			if(user!=null){
				String today="";
				if(date.equals("")){
					today= T.getTodaytxt();///20160331
				}else{
					today=date;
				}
			
			
				
				int chk =procedureDao.getchkData(userId,today);
				if(chk>-1){
					List<Procedure> list = procedureDao.getData(userId, today);
				
					map.put("list", list);
					map.put("result", true);
				}else{
					map.put("message","데이터가 존재하지 않습니다.");
					map.put("result", false);
				}
			}else{
				map.put("message","정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
			
			
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	//건강점수
	@RequestMapping("/m/health_main.go")
	public String healthController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		@RequestParam(value = "date", required = false, defaultValue = "") String date,
	
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
		
			User user =userdao.getUsers(userId);
			if(user!=null){
				String today="";
				if(date.equals("")){
					today= T.getTodaytxt();///20160331
				}else{
					today=date;
				}
			
				int score =procedureDao.getHealthScore(userId,today);
				
				int status =calculate.scoreStatus(score, userId);
				String statusTxt ="";
				if(status!=0){
					if(status==1){
						statusTxt="양호";
					}
					if(status==2){
						statusTxt="우수";				
					}
					if(status==3){
						statusTxt="보통";
					}
					if(status==4){
						statusTxt="불량";
					}
					if(status==5){
						statusTxt="심각";
					}
	
					UserRegData  userRegData= userRegDataDao.getUserRank(userId,today,"health_score");
					map.put("userRegData",userRegData);
					map.put("score",score);
					map.put("statusTxt",statusTxt);
					map.put("result", true);
				}else{
					map.put("message","입력된 데이터가 존재하지 않습니다.");
					map.put("result", false);
				}
			}else{
				map.put("message","정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
			
			
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	//상세 페이지
	@RequestMapping("/m/main_detail.go")
	public String healthDetailController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		@RequestParam(value = "type", required = false, defaultValue = "") String type,
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
		
			User user =userdao.getUsers(userId);
			if(user!=null){
				List<UserRegData>list = userRegDataDao.getUserRegDataList(userId,type);
				List<DataMap> data = new ArrayList<>();
				List periodList = new ArrayList<>();
				if(!type.equals("HEALTH_SCORE")){
				
					data= userRegDataDao.getUserRegDataListMonth(userId,type);
					
					//그래프 그리기 6개월
					
					for(int i=0;i<6;i++){
						periodList.add(T.getTodayBeforeYearMonth(-i));
					}
					
				
				
				}else{
					
			
					data=userRegDataDao.getUserRegDataListYear(userId,type);
					
					//그래프 그리기 5년전
					String nowYear = T.getTodayYear();
					
					for(int i=0;i<5;i++){
						periodList.add(Integer.toString(Integer.parseInt(nowYear)-i));
					}
				}
				boolean chk=true;
				for (int i = 0; i < periodList.size(); i++) {
					boolean hasData = false;
					String period=(String) periodList.get(i);
					for (int j = 0; j < data.size(); j++) {
						DataMap Dm =data.get(j);
						String date = (String)Dm.getMonth();
						if (date.equals(period)) {
							hasData = true;
							
							break;
						}
					}
					if (hasData == false) {
						DataMap Dm = new DataMap();
						Dm.setMonth(period);
						Dm.setUserId(userId);
						Dm.setNum(200);
						data.add(i, Dm);
					}
				}
				
				map.put("datamap",data);
				map.put("list",list);
				map.put("result", true);
			}else{
				map.put("message","정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
			
			
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	///리얼 랭킹
	@RequestMapping("/m/real_rank.go")
	public String realRankController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		@RequestParam(value = "type", required = false, defaultValue = "") String type,
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
		
			User user =userdao.getUsers(userId);
			if(user!=null){
				
				List<HashMap>list = userRegDataDao.getUserRegDataListRank(type); //분포도
				int total = userRegDataDao.getUserRegDatatotalCount();
				double rank = userRegDataDao.getUserRegDataMyRank(type, userId);
				double percent = (rank/total);
				
				HashMap usermap = new HashMap();
				usermap.put("total", total);
				usermap.put("rank", (int)rank);
				usermap.put("percent", (int)(percent*100));
				
				map.put("list",list);
				map.put("usermap",usermap);
				map.put("result", true);
			}else{
				map.put("message","정보를 찾을 수 없습니다.");
				map.put("result", false);
			}
			
			
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	
	//리얼매거진 리스트
	@RequestMapping("/m/magazinelist.go")
	public String magazineListController(
		
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			
			///혈압 혈당 신체활동 콜레스테롤 항상 랜덤
			///음주 흡연 비만 조건에 따라. 음주,흡연, 비만은 bmi 23 초과 
		
			
			User user=userdao.getUsers(userId);
			List<Magazine>magalist=new ArrayList<>();
			if(user!=null){
				List list=new ArrayList();
				list.add(Config.DISEASE_PRESSURE);
				list.add(Config.DISEASE_BLOODSUGAR);
				list.add(Config.DISEASE_ACTIVITY);
				list.add(Config.DISEASE_CHOLESTEROL);
				UserWeight uw = userWeightDao.getUserWeightLimit1(userId);
				UserSmoke us = userSmokeDao.getUserSmokeLimit1(userId);
				UserDrink ud= userDrinkDao.getUserDrinkLimit1(userId);
				
				if(uw==null||uw.getBmi()>23){
					list.add(Config.DISEASE_WEIGHT);
				}
				if(us==null||0<us.getStatus()){
					list.add(Config.DISEASE_SMOKE);
				}
				if(ud==null||0<ud.getStatus()){
					list.add(Config.DISEASE_DRINK);
				}
				String str="";
				for(int i=0;i<list.size();i++){
					if(i==list.size()-1){
						str+="'"+list.get(i)+"'";
					}else{
						str+="'"+list.get(i)+"'"+",";
					}
				}
					
				magalist=magaDao.getRandomMagazineList(str);	
					
				
			}
			
			map.put("list", magalist);
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
/*	// 공지사항

	@RequestMapping("/m/service_list.go")
	public String noticeController(
			@RequestParam(value = "page", required = false, defaultValue = "") int page,
		
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			
			List<Notice> list = noticedao.getNoticeMainList(page, ITEM_COUNT_PER_PAGE);
			int cnt = noticedao.getNoticeMainCount();
		
			map.put("data", list);
			map.put("cnt", cnt);
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}

	// 공지사항상세

	@RequestMapping("/m/service_detail.go")
	public String noticeDController(
			@RequestParam(value = "noticeSeq", required = false, defaultValue = "") int noticeSeq,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {

			Notice notice = noticedao.getNotice(noticeSeq);
			map.put("data", notice);
			map.put("result", true);

		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);

		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
*/

	/* 버전확인 */
	@RequestMapping("/m/now_version.go")
	public String nowVersionController(HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Config config = configdao.getConfig();
			String version = config.getAppVersion();
			map.put("version", version);
			map.put("result", true);
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}



	/* 푸시 클리어 */
	@RequestMapping("/m/clear_noticeBadge.go")
	public String noticeBadgeController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			
			//userdao.updateBadge(userId, 0);
			
			map.put("result", true);
			
		} catch (Exception e) {
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	
	

	/* 로그아웃 */
	@RequestMapping("/m/logout.go")
	public String LogoutController(
		@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
	
		HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
		
			userdao.updateout(userId);
			map.put("result", true);
			map.put("message", "로그아웃 되었습니다.");
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	
	public void filedelete(String fullName){
		
		
		
		File file = new File(FILE_ROOT+fullName);
		file.delete();
		//썸네일
		String  path = fullName.substring(0,fullName.lastIndexOf("/"));//경로까지
		String fileName = fullName.substring(fullName.lastIndexOf("/"));//파일명
		String thumbName = path+"/thumb/"+fileName;
		File thumb = new File(FILE_ROOT+thumbName);
		thumb.delete();
		
	}
	
	int PressureCal(int num1,int num2){
		int result=0;
		if(num1<120&&num2<90){
			result=2;
		}
		if((120<=num1&&num1<=139)||(80<=num2&&num2<=89)){
			result=3;
		}
		if((140<=num1)||(90<=num2)){
			result=4;
		}
		
		return result;
	}
	
	int bloodSugarCal(int num1){
		int result=0;
		if(num1<100){
			result=2;
		}
		if((100<=num1&&num1<=125)){
			result=3;
		}
		if(126<=num1){
			result=4;
		}
		
		return result;
	}
	
	int bmiCal(Double num1){
		int result=0;
		if(num1<18.5){
			result=1;
		}
		if((18.5<=num1&&num1<=22.9)){
			result=2;
		}
		if((23<=num1&&num1<=24.9)){
			result=3;
		}
		if(25<=num1){
			result=4;
		}
		return result;
	}
	
	int ActivityCal(int num1,int num2){
		int result=0;
		if(num1==2&&num2==4){
			result=2;
		}else if(num1==3&&(num2==3||num2==4)){
			result=2;
		}else if(num1==4&&(num2==2||num2==3||num2==4)){
			result=2;
		}else{
			result=3;
		}
		return result;
	}
	
	int smokeCal(int num1){
		int result=0;
		if(num1==2){
			result=2;
		}else if(num1==3||num1==4||num1==5||num1==6){
			result=3;
		}
		return result;
	}
	int drinkCal(int num1,int num2){
		int result=0;
		if(num1==1){
			result=0;
		}else if(num1==2&&(num2==1||num2==2||num2==3||num2==4)){
			result=2;
		}else if(num1==3&&(num2==1||num2==2)){
			result=2;
		}
		else if(num1==4&&(num2==1)){
			result=2;
		}else{
			result=3;
		}
		return result;
	}
	
	int cholesterolCal(int num){
		int result=0;
		if(num<200){
			result=2;
		}else if(200<=num&&num<=239){
			result=3;
		}else if(240<=num){
			result=4;
		}
		return result;
		
	}
	int ldlCal(int num){
		int result=0;
		if(num<=129){
			result=2;
		}else if(130<=num&&num<=159){
			result=3;
		}else if(160<=num){
			result=4;
		}
		return result;
	}
}