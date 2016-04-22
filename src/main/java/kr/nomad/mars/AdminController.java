
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.oreilly.servlet.MultipartRequest;


import kr.nomad.mars.dao.ConfigDao;

import kr.nomad.mars.dao.MagazineDao;
import kr.nomad.mars.dao.MagazinePageDao;
import kr.nomad.mars.dao.MedExamDao;
import kr.nomad.mars.dao.NoticeDao;
import kr.nomad.mars.dao.QnaDao;
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
import kr.nomad.mars.dto.Config;
import kr.nomad.mars.dto.Magazine;
import kr.nomad.mars.dto.MagazinePage;
import kr.nomad.mars.dto.MedExam;
import kr.nomad.mars.dto.Notice;
import kr.nomad.mars.dto.Qna;
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
import kr.nomad.util.Paging;
import kr.nomad.util.Response;
import kr.nomad.util.T;
import kr.nomad.util.XlsxWriter;
import kr.nomad.util.encrypt.CryptoNew;
import kr.nomad.util.file.UniqFileRenamePolicy;
import net.sf.json.JSONObject;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired UserDao userDao;
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
	@Autowired NoticeDao noticeDao;
	@Autowired QnaDao qnaDao;
	@Autowired MagazineDao magazineDao;
	@Autowired MagazinePageDao magazinePageDao;
	@Autowired UserRegDataDao userRegDataDao;
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


	@RequestMapping("/main.go")
	public String wUserMenuController(Model model) {

		return "/admin/login";
	}
	
	@RequestMapping("/admin/main.go")
	public String mainController(Model model) {
		
		
		List<User>list=userDao.getUserTop3();
		model.addAttribute("user", list);
		
		
		return "/admin/main";
	}
	
	// 관리자 헤더 count
	@RequestMapping("/admin/count.go")
	public String adminCountController(HttpSession session, Model model, HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		int manCount = userDao.getCount(User.USERTYPE_USER);
	
		
		map.put("Count", manCount);
	
		map.put("result", result);
		map.put("message", message);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;

	}
	
	////////일반회원
	// 회원 관리 > 일반회원
	@RequestMapping("/admin/user/user.go")
	public String usertController(HttpSession session, Model model) {

		return "/admin/user/user";
	}

	// 회원 관리 > 일반회원 리스트
	@RequestMapping("/admin/user/user_list.go")
	public String usertListController(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
			@RequestParam(value = "age", required = false, defaultValue = "0") int age,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {

		List<User> list = null;
		int count = 0;

		list = userDao.getUserList(3,keyword, gender, age, page,ITEM_COUNT_PER_PAGE);
		count = userDao.getCount(3,keyword, gender, age);
		
		for(int i=0;i<list.size();i++){
			User user= list.get(i);
			String userId=user.getUserId();
			UserPress up =userPressDao.getUserPressLimit1(userId);
			UserBlood ub= userBloodDao.getUserBloodLimit1(userId);
			UserChol uc = userCholDao.getUserCholLimit1(userId);
			UserWeight uw = userWeightDao.getUserWeightLimit1(userId);
			UserActivity ua = userActivityDao.getUserActivityLimit1(userId);
			UserSmoke us = userSmokeDao.getUserSmokeLimit1(userId);
			UserDrink ud= userDrinkDao.getUserDrinkLimit1(userId);
			List diseaselist= new ArrayList<>();
			diseaselist=dangerList(userId);
			user.setList(diseaselist);
			list.set(i, user);
		}	
		// 페이징
		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);

		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);
		return "admin/user/user_list";
	}

	

	// 엑셀 다운로드
	@RequestMapping("/admin/list_excel.go")
	public ModelAndView userListExcelController(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
			@RequestParam(value = "age", required = false, defaultValue = "0") int age,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "userType", required = false, defaultValue = "0") int userType,
			HttpSession session, Model model
			) {

		List list = userDao.getUserListNopaging(userType, keyword, gender, age);

		SimpleDateFormat formatdate = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "code_" + formatdate.format(new Date());
		XlsxWriter writer = new XlsxWriter(FILE_ROOT + "/files/excel/"+ fileName + ".xls");

		List title = new ArrayList();
		List contents = new ArrayList();

		title.add("이름");
		title.add("아이디");
		title.add("생년월일");
		if(userType==3){
			title.add("위험요인");
		}
		title.add("가입일");

		for (int i = 0; i < list.size(); i++) {
			User user = (User) list.get(i);

			List dataList = new ArrayList();
			dataList.add(user.getUserName());
			dataList.add(user.getUserId());
			dataList.add(user.getBirthday());
			if(userType==3){
				dataList.add(dangerList(user.getUserId()));
			}
			dataList.add(user.getRegDate());
			contents.add(dataList);
		}
		
			writer.writeFile(title, contents);
	
			File file = new File(FILE_ROOT + "/files/excel/" + fileName + ".xls");
		

		return new ModelAndView("fileDownloadView", "file", file);
	}
	
	@RequestMapping("/admin/user/user_view.go")
	public String MedController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpSession session, Model model) {

		int count = 0;

		User user;
	
		

		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		// 해당 유저 문진 리스트
		List<UserBasic>list =userBasicDao.getUserBasicList(userId,page, ITEM_COUNT_PER_PAGE);
		count = userBasicDao.getUserBasicCount(userId);

		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("currentPage", page);
		model.addAttribute("diseaselist", diseaselist);
		model.addAttribute("diseaseId", "view");
		
		return "admin/user/user_view";
	}
	
	@RequestMapping("/admin/user/user_view_med.go")
	public String MedViewController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			HttpSession session, Model model) {

		int count = 0;

		User user;
	
		
		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		// 해당 유저 문진 리스트
		UserBasic userb = userBasicDao.getUserBasicSeq(seq);
		
		UserActivity UserActivity = userActivityDao.getUserActivityMedSeq(seq);
		UserSmoke UserSmoke = userSmokeDao.getUserSmokeMedSeq(seq);
		UserDrink UserDrink= userDrinkDao.getUserDrinkMedSeq(seq);
		
		model.addAttribute("user", user);
		model.addAttribute("userBasic", userb);
		model.addAttribute("userActivity", UserActivity);
		model.addAttribute("userSmoke", UserSmoke);
		model.addAttribute("userDrink", UserDrink);

		model.addAttribute("diseaselist", diseaselist);
		model.addAttribute("diseaseId", "view");
		
		return "admin/user/user_view_med";
	}
	
	@RequestMapping("/admin/user/user_checkup.go")
	public String checkUpController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpSession session, Model model) {

		int count = 0;

		User user;
	
		

	
		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		// 해당 유저 검진
		List<UserCheckup>list =userCheckupDao.getUserCheckupList(userId,page, ITEM_COUNT_PER_PAGE);
		count = userCheckupDao.getUserCheckupCount(userId);

		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("currentPage", page);
		model.addAttribute("diseaselist", diseaselist);
		model.addAttribute("diseaseId", "checkup");
		return "admin/user/user_checkup";
	}
	
	@RequestMapping("/admin/user/user_view_checkup.go")
	public String checkupViewController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
			HttpSession session, Model model) {

		int count = 0;

		User user;
	
		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		
		UserCheckup userCheckup = userCheckupDao.getUserCheckupSeq(seq);
		UserWeight userWeight = userWeightDao.getUserWeightCheckSeq(seq);
		UserPress userPress = userPressDao.getUserPressCheckSeq(seq);
		UserBlood userBlood = userBloodDao.getUserBloodCheckSeq(seq);
		UserChol userChol = userCholDao.getUserCholCheckCheckSeq(seq);
		model.addAttribute("checkup", userCheckup);
		model.addAttribute("weight", userWeight);
		model.addAttribute("pressure", userPress);
		model.addAttribute("bloodsugar", userBlood);
		model.addAttribute("cholesterol", userChol);
		model.addAttribute("user", user);
		
		model.addAttribute("diseaseId", "checkup");
		model.addAttribute("diseaselist", diseaselist);
		
		return "admin/user/user_view_checkup";
	}
	
	
	@RequestMapping("/admin/user/{diseaseId}.go")
	public String WeightController(
			@PathVariable String diseaseId,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpSession session, Model model) {

		int count = 0;

		User user;
	
		
		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		// 해당 유저 체중
		
		List<UserPress>list =userPressDao.getUserDataList(diseaseId, userId, page, ITEM_COUNT_PER_PAGE);
		count = userPressDao.getUserDataCount(diseaseId, userId);

		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("currentPage", page);
		model.addAttribute("diseaseId", diseaseId);
		model.addAttribute("diseaselist", diseaselist);
		
		String txt="admin/user/user_"+diseaseId;
		return txt;
	}
	
	@RequestMapping("/admin/user/user_score.go")
	public String HealthScoreController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpSession session, Model model) {

	

		User user;
	
		if (userId.equals("")) {
			user = new User();
		} else {
			user = userDao.getUsers(userId);
			
		}
		List diseaselist= dangerList(userId);
		//페이징 처리 
		List<UserRegData>list = userRegDataDao.getUserRegDataListPaging(userId,type,page,ITEM_COUNT_PER_PAGE);
		int count = userRegDataDao.getUserRegDataListCount(userId);
		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
		model.addAttribute("paging", paging);
		model.addAttribute("currentPage", page);
		model.addAttribute("user", user);
		model.addAttribute("type", type);
		model.addAttribute("list", list);
		model.addAttribute("diseaseId", "user_score");
		model.addAttribute("diseaselist", diseaselist);
		
		return "admin/user/user_score";
	}
	////////관리자
	// 회원 관리 > 관리자
	@RequestMapping("/admin/manager/manager.go")
	public String ManagerController(HttpSession session, Model model) {
	
		return "/admin/manager/manager";
	}
	
	// 회원 관리 > 관리자 리스트
	@RequestMapping("/admin/manager/manager_list.go")
	public String managerListController(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
			@RequestParam(value = "age", required = false, defaultValue = "0") int age,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {
	
		List<User> list = null;
		int count = 0;
	
		list = userDao.getUserList(1,keyword, gender, age, page,ITEM_COUNT_PER_PAGE);
		count = userDao.getCount(1,keyword, gender, age);
		
		
		// 페이징
		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
	
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);
		return "admin/manager/manager_list";
	}

		// 회원 관리 > 관리자 뷰
		@RequestMapping("/admin/manager/manager_view.go")
		public String managerViewController(
				@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			
				HttpSession session, Model model) {
		
			User user = userDao.getUsers(userId);
			if(user==null){
				user=new User();
			}
			model.addAttribute("user", user);
		
			return "admin/manager/manager_view";
		}
		// 회원 관리 > 관리자 수정
		@RequestMapping("/admin/manager/manager_edit.go")
		public String managerEditController(
				@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			
				HttpSession session, Model model) {
		
			User user = userDao.getUsers(userId);
			if(user==null){
				user=new User();
			}
			model.addAttribute("user", user);
		
			return "admin/manager/manager_edit";
		}
	
		@RequestMapping("/admin/manager/manager_edit_do.go")
		public String managerEditDoController(
				
				@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
				@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
				@RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
				@RequestParam(value = "userType", required = false, defaultValue = "0") int userType,
				@RequestParam(value = "gender", required = false, defaultValue = "1") int gender,
				@RequestParam(value = "password", required = false, defaultValue = "0") String password,
				@RequestParam(value = "kind", required = false, defaultValue = "0") int kind, //1등록 2수정
				HttpSession session, Model model, HttpServletResponse res) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			User uu = new User();
			uu.setUserId(userId);
			uu.setBirthday(birthday);
			uu.setUserType(userType);
			uu.setUserName(userName);
			uu.setGender(gender);
			uu.setStatus(1);
			boolean chk =userDao.correctId(userId); //1이면 아이디있음.
			
			
		
			try {
			
				if (kind==2) {// 수정
					userDao.updateadmin(uu);
					map.put("result", true);
					map.put("message", "수정되었습니다.");
				} else {
					if(chk){
						map.put("result", false);
						map.put("message", "중복되는 아이디가 있습니다.");
						
					}else{
						String enPw = CryptoNew.encrypt(password);
						uu.setPassword(enPw);
						userDao.addUser(uu);
						map.put("result", true);
						map.put("message", "등록되었습니다.");
					}
				
				}

			} catch (Exception e) {
				map.put("result", false);
				map.put("message", e.getMessage());
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);

			return null;
		}

		// 관리자삭제
		@RequestMapping("/admin/manager/manager_delete.go")
		public String adminDeleteController(

				@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
				HttpSession session, Model model, HttpServletResponse res) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				userDao.deleteUser(userId);
				

				map.put("result", true);
				map.put("msg", "삭제되었습니다");
			} catch (Exception e) {
				map.put("result", false);
				map.put("msg", e.getMessage());
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
		}

	
	
	
	/////////////////////공지사항

	// 공지사항
	@RequestMapping("/admin/board/notice/notice.go")
	public String noticeController(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {

		return "/admin/board/notice/notice";
	}

	// 공지사항 리스트

	@RequestMapping("/admin/board/notice/notice_list.go")
	public String noticeListController(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {

		List<Notice> list = null;
		int count = 0;
		int notiType = 0;

		

		list = noticeDao.getNoticeMainList(keyword, page,ITEM_COUNT_PER_PAGE);
		count = noticeDao.getNoticeMainCount(keyword);
		

		// 페이징
		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);

		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);
		return "admin/board/notice/notice_list";
	}

	// 공지사항 등록/수정
	@RequestMapping("/admin/board/notice/notice_edit.go")
	public String noticeEditController(
			@RequestParam(value = "noticeSeq", required = false, defaultValue = "0") int noticeSeq,
			Model model) {

		Notice notice = null;
		if (noticeSeq == 0) {
			notice = new Notice();
		} else {
			notice = noticeDao.getNotice(noticeSeq);
		}

		model.addAttribute("notice", notice);

		return "admin/board/notice/notice_edit";
	}

	// 공지사항 수정의 처리
	@RequestMapping("/admin/board/notice/notice_edit_do.go")
	public String noticeEditDoController(
			HttpServletRequest req,
			@RequestParam(value = "noticeSeq", required = false, defaultValue = "0") int noticeSeq,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
			@RequestParam(value = "notiType", required = false, defaultValue = "0") int notiType,
			@RequestParam(value = "sendPush", required = false, defaultValue = "0") int sendPush,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "ir1", required = false, defaultValue = "") String contentsHtml,
			@RequestParam(value = "ir1_text", required = false, defaultValue = "") String contentsText,
			HttpServletResponse res, Model model) throws IllegalStateException,
			IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "새로운 공지사항이 등록 되었습니다.";

		try {
			if (noticeSeq == 0) {
				Notice notice = new Notice();
				notice.setUserId(userId);
				notice.setTitle(title);
				notice.setStartDate(startDate);
				notice.setEndDate(endDate);
				// notice.setNotiType(notiType);
				notice.setSendPush(sendPush);
				notice.setContentsHtml(contentsHtml);
				notice.setContentsText(contentsText);
				noticeDao.addNotice(notice);
				noticeSeq = noticeDao.getLastSeq();
				
			/*	if(notice.getSendPush()==1){//푸시 발송 설정이면
					
					List<User>list = userDao.getUserPushList();
					for (int i=0; i<list.size(); i++) {
						User user = (User) list.get(i);
					
						if (user.getPushkey().equals("") == false) {
						
							Push push = new Push();
							push.setBadge(1);
							push.setOs(user.getOsType());
							push.setPushKey(user.getPushkey());
							push.setMsgType(Push.MSG_TYPE_NOTICE);
							push.setUserid(user.getUserId());
							push.setStatus(0);
							push.setServiceId("RECOVER");
							push.setPushType(1);							
							push.setMsg(message);
							push.setMsgKey("0");
							userDao.addPush(push);
							userDao.updateBadge(user.getUserId(),user.getNoticeBadge()+1);
						}
					}
				}*/

				result = true;
				message = "등록되었습니다.";
			} else {
				Notice notice = noticeDao.getNotice(noticeSeq);

				notice.setUserId(userId);
				notice.setTitle(title);
				notice.setStartDate(startDate);
				notice.setEndDate(endDate);
				// notice.setNotiType(notiType);
				notice.setSendPush(sendPush);
				notice.setContentsHtml(contentsHtml);
				notice.setContentsText(contentsText);
				noticeDao.updateNotice(notice);
				result = true;
				message = "수정되었습니다.";
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		map.put("noticeSeq", noticeSeq);
		map.put("result", result);
		map.put("message", message);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	// 공지사항 삭제
	@RequestMapping("/admin/board/notice/notice_delete_do.go")
	public String noticeDeleteDoController(@RequestParam int noticeSeq,
			HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 게시물 삭제
			noticeDao.deleteNotice(noticeSeq);

			map.put("message", "게시물이 삭제되었습니다.");
			map.put("result", true);
		} catch (Exception e) {

			map.put("message", "게시물이 삭제되지 않았습니다.\n" + e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
	
	//////////////////////qna
	
	// QNA
	@RequestMapping("/admin/board/qna/qna.go")
	public String qnaController(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {

		return "/admin/board/qna/qna";
	}

	// QNA 리스트

	@RequestMapping("/admin/board/qna/qna_list.go")
	public String qnaListController(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "cate", required = false, defaultValue = "0") int cate,
			HttpSession session, Model model) {

		List<Qna> list = null;
		int count = 0;
		int notiType = 0;

		

		list = qnaDao.getQnaList(keyword,cate, page, ITEM_COUNT_PER_PAGE);
		count = qnaDao.getCount(keyword,cate);
		

		// 페이징
		String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,
				PAGE_COUNT_PER_PAGING);

		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);

		return "admin/board/qna/qna_list";
	}

	// QNA 등록/수정
	@RequestMapping("/admin/board/qna/qna_edit.go")
	public String qnaEditController(
			@RequestParam(value = "qnaSeq", required = false, defaultValue = "0") int qnaSeq,
			Model model) {
		
		Qna qna = null;
		if (qnaSeq == 0) {
			qna = new Qna();
		} else {
			qna = qnaDao.getQna(qnaSeq);
		}

		model.addAttribute("qna", qna);


		return "admin/board/qna/qna_edit";
	}

	// QNA 수정의 처리
	@RequestMapping("/admin/board/qna/qna_edit_do.go")
	public String qnaEditDoController(
			HttpServletRequest req,
			@RequestParam(value = "qnaSeq", required = false, defaultValue = "0") int qnaSeq,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "cateKind", required = false, defaultValue = "0") int cateKind,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "ir1", required = false, defaultValue = "") String contentsHtml,
			@RequestParam(value = "ir1_text", required = false, defaultValue = "") String contentsText,
			HttpServletResponse res, Model model) throws IllegalStateException,
			IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";

		try {
			if (qnaSeq == 0) {
				Qna qna = new Qna();
				qna.setUserId(userId);
				qna.setTitle(title);
				qna.setCateKind(cateKind);
				qna.setContentsHtml(contentsHtml);
				qna.setContentsText(contentsText);
				qnaDao.addQna(qna);

				result = true;
				message = "등록되었습니다.";
			} else {
				Qna qna = qnaDao.getQna(qnaSeq);
				qna.setUserId(userId);
				qna.setTitle(title);
				qna.setCateKind(cateKind);
				qna.setContentsHtml(contentsHtml);
				qna.setContentsText(contentsText);
				qnaDao.updateQna(qna);
				result = true;
				message = "수정되었습니다.";
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		map.put("qnaSeq", qnaSeq);
		map.put("result", result);
		map.put("message", message);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	// QNA 삭제
	@RequestMapping("/admin/board/qna/qna_delete_do.go")
	public String qnaDeleteDoController(
			@RequestParam int qnaSeq,
			HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			
			qnaDao.deleteQna(qnaSeq);

			map.put("message", "게시물이 삭제되었습니다.");
			map.put("result", true);
		} catch (Exception e) {

			map.put("message", "게시물이 삭제되지 않았습니다.\n" + e.getMessage());
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;
	}
/////////////////앱버전 관리
	// 앱버전
	@RequestMapping("/admin/board/app/app.go")
	public String appController(
		
	Model model) {
		


		return "admin/board/app/app";
	}
	
	@RequestMapping("/admin/board/app/app_list.go")
	public String appListController(
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
	Model model) {
		
		List<Config> list = null;
	
		list=configdao.getConfigList(page, ITEM_COUNT_PER_PAGE);
		

		model.addAttribute("list", list);


		return "admin/board/app/app_list";
	}

	// 앱버전
	@RequestMapping("/admin/board/app/app_edit.go")
	public String appEditController(
			@RequestParam(value = "appSeq", required = false, defaultValue = "0") int appSeq,
			Model model) {
		
		Config cf = null;
	
		if(appSeq==0){
			cf= new Config();
		}else{
			cf=configdao.getConfig(appSeq);
		}

		model.addAttribute("config", cf);


		return "admin/board/app/app_edit";
	}

	// 앱버전 등록수정
	@RequestMapping("/admin/board/app/app_edit_do.go")
	public String appEditDoController(
			@RequestParam(value = "appSeq", required = false, defaultValue = "0") int appSeq,
			@RequestParam(value = "appVersion", required = false, defaultValue = "") String appVersion,
			@RequestParam(value = "comment", required = false, defaultValue = "") String comment,
			Model model,HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(appSeq==0){
				Config cf = new Config();
				cf.setAppVersion(appVersion);
				cf.setComment(comment);
				
				configdao.addConfig(cf);
				map.put("message", "등록되었습니다.");
				map.put("result", true);
			}else{
				Config cf = configdao.getConfig(appSeq);
				cf.setAppVersion(appVersion);
				cf.setComment(comment);
				configdao.updateConfig(cf);
				map.put("message", "수정되었습니다.");
				map.put("result", true);
			}

		}catch(Exception e){
			map.put("message", e.getMessage());
			map.put("result", false);
		}
			
		

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	//앱버전 삭제
	@RequestMapping("/admin/board/app/app_delete.go")
	public String appdeleteController(
			@RequestParam(value = "appSeq", required = false, defaultValue = "0") int appSeq,
		
			Model model,HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
				configdao.deleteConfig(appSeq);
				map.put("message", "삭제되었습니다.");
				map.put("result", true);
			

		}catch(Exception e){
			map.put("message", e.getMessage());
			map.put("result", false);
		}
			
		

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}

	////////////////////////컨텐츠
	
	// 컨텐츠 관리 > 건강매거진
		@RequestMapping("/admin/contents/contents.go")
		public String contentsController(
				@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
				HttpSession session, Model model) {

			return "/admin/contents/contents";
		}

		// 건강매거진 리스트
		@RequestMapping("/admin/contents/contents_list.go")
		public String contentsListController(
				@RequestParam(value = "page", required = false, defaultValue = "1") int page,
				@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
				@RequestParam(value = "type", required = false, defaultValue = "") String type,
				HttpSession session, Model model) {

			List<Magazine> list = null;
			int count = 0;

		

				list = magazineDao.getMagazineList(keyword,type, page,ITEM_COUNT_PER_PAGE);
				count = magazineDao.getCount(keyword,type);
		

			// 페이징
			String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,
					PAGE_COUNT_PER_PAGING);

			model.addAttribute("list", list);
			model.addAttribute("paging", paging);
			model.addAttribute("keyword", keyword);
			model.addAttribute("currentPage", page);
			return "admin/contents/contents_list";
		}

			// 건강매거진 등록/수정
		@RequestMapping("/admin/contents/contents_edit.go")
		public String contentsEditController(
				@RequestParam(value = "mSeq", required = false, defaultValue = "0") int mSeq,
				Model model) {

			Magazine magazine = null;
			List<MagazinePage> pageList = new ArrayList<MagazinePage>();
			if (mSeq == 0) {
				magazine = new Magazine();
			} else {
				magazine = magazineDao.getMagazine(mSeq);
				pageList = magazinePageDao.getMagazinePageList(mSeq);

			}

			model.addAttribute("magazine", magazine);
			model.addAttribute("pageList", pageList);
			return "admin/contents/contents_edit";
		}

		// 건강매거진 수정의 처리
		@RequestMapping("/admin/contents/contents_edit_do.go")
		public String contentsEditDoController(HttpServletRequest req,
				HttpServletResponse res, Model model) throws IllegalStateException,
				IOException {

			Map<String, Object> map = new HashMap<String, Object>();
			boolean result = true;
			String message = "";

			int mSeq = 0;

			try {
				String FILE_PATH = "/files/magazine/";
				String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
				String photo = "";
				String path = "";
				String photoPre = "";
				int fileSize = FILE_MAX_SIZE * 1024 * 1024;
				String fileName = "";
				String thumbName = "";

				req.setCharacterEncoding("utf-8");

				File file = null;
				MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH,
						fileSize, "UTF-8", new UniqFileRenamePolicy());

				mSeq = Integer
						.parseInt(F.nullCheck(multi.getParameter("mSeq"), "0"));
				String userId = F.nullCheck(multi.getParameter("userId"), "");
				String title = F.nullCheck(multi.getParameter("title"), "");
				String subTitle = F.nullCheck(multi.getParameter("subTitle"), "");
				String contents = F.nullCheck(multi.getParameter("contents"), "");
				String year = F.nullCheck(multi.getParameter("year"), "");
				String type = F.nullCheck(multi.getParameter("type"), "");

				Enumeration files = multi.getFileNames();
				while (files.hasMoreElements()) {
					String elementName = (String) files.nextElement();
					file = multi.getFile(elementName);
					if (file != null) {
						fileName = file.getName();
					}
				}

				if (fileName.equals("") == false) {
					// 축소이미지 저장
					File newFile = new File(FILE_LOCAL_PATH + fileName);
					File thumbFile = new File(FILE_LOCAL_PATH + "thumb/" + fileName);
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

				if (mSeq == 0) {
					Magazine magazine = new Magazine();
					magazine.setContents(contents);
					magazine.setType(type);
					magazine.setTitle(title);
					magazine.setSubTitle(subTitle);
					magazine.setFileName(fileName);
					magazine.setThumFile(fileName);
					magazineDao.addMagazine(magazine);
					mSeq = magazineDao.getLastId();

					result = true;
					message = "등록되었습니다.";
				} else {
					Magazine magazine = magazineDao.getMagazine(mSeq);
					magazine.setContents(contents);
					magazine.setType(type);
					magazine.setTitle(title);
					magazine.setSubTitle(subTitle);
					if (fileName.equals("") == false) {
						magazine.setFileName(fileName);
						magazine.setThumFile(fileName);
					}
					magazineDao.updateMagazine(magazine);
					result = true;
					message = "수정되었습니다.";
				}
			} catch (Exception e) {
				message = e.getMessage();
			}
			map.put("mSeq", mSeq);
			map.put("result", result);
			map.put("message", message);

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
		}

		// 건강매거진 삭제
		@RequestMapping("/admin/contents/contents_delete_do.go ")
		public String contentsDeleteDoController(@RequestParam int mSeq,
				HttpServletResponse res) {

			Map<String, Object> map = new HashMap<String, Object>();

			try {
				// 게시물 삭제
			
				Magazine magazine = magazineDao.getMagazine(mSeq);
				String fileName = magazine.getFileName();

				String FILE_LOCAL_PATH = FILE_ROOT + "/files/magazine/";
				String filePath = FILE_LOCAL_PATH + fileName;
				String thumbPath = FILE_LOCAL_PATH + "thumb/" + fileName;

				File file = new File(filePath);
				file.delete();

				File thumb = new File(thumbPath);
				thumb.delete();

				// 게시물 삭제
				magazineDao.deleteMagazinePageMain(mSeq);

				
				map.put("message", "게시물이 삭제되었습니다.");
				map.put("result", true);
			} catch (Exception e) {

				map.put("message", "게시물이 삭제되지 않았습니다.\n" + e.getMessage());
				map.put("result", false);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);

			return null;
		}

		// 건강매거진 이미지 삭제
		@RequestMapping("/admin/contents/contents_file_delete.go")
		public String contentsFileDeleteController(
				@RequestParam(value = "mSeq", required = false, defaultValue = "0") int mSeq,
				@RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
				HttpServletResponse res) {

			Map<String, Object> map = new HashMap<String, Object>();

			try {

				String FILE_LOCAL_PATH = FILE_ROOT + "/files/magazine/";
				String filePath = FILE_LOCAL_PATH + fileName;
				String thumbPath = FILE_LOCAL_PATH + "thumb/" + fileName;

				File file = new File(filePath);
				file.delete();

				File thumb = new File(thumbPath);
				thumb.delete();

				// 게시물 업데이트
				magazineDao.updateMagazineFile(mSeq, "");

				map.put("message", "파일이 삭제되었습니다.");
				map.put("result", true);
			} catch (Exception e) {

				map.put("message", "파일이 삭제되지 않았습니다.\n" + e.getMessage());
				map.put("result", false);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);

			return null;
		}

	// 서브 등록/수정
		@RequestMapping("/admin/contents/subcontents_edit.go")
		public String subcontentsEditController(
				@RequestParam(value = "pSeq", required = false, defaultValue = "0") int pSeq,
				@RequestParam(value = "mSeq", required = false, defaultValue = "0") int mSeq,
				Model model) {

			MagazinePage mp = null;

			if (pSeq == 0) {
				mp = new MagazinePage();
				int page = magazinePageDao.getMagazinePageCnt(mSeq);
				model.addAttribute("page", page + 1);
			} else {
				mp = magazinePageDao.getMagaginePage(pSeq);
				model.addAttribute("page", mp.getPage());
			}
			model.addAttribute("mSeq", mSeq);
			model.addAttribute("mp", mp);

			return "admin/contents/subcontents_edit";
		}

		// 건강매거진 서브 수정의 처리
		@RequestMapping("/admin/contents/subcontents_edit_do.go")
		public String subcontentsEditDoController(HttpServletRequest req,
				HttpServletResponse res, Model model) throws IllegalStateException,
				IOException {

			Map<String, Object> map = new HashMap<String, Object>();
			boolean result = true;
			String message = "";

			int mSeq = 0;

			try {
				String FILE_PATH = "/files/submagazine/";
				String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
				String photo = "";
				String path = "";
				String photoPre = "";
				int fileSize = FILE_MAX_SIZE * 1024 * 1024;

				String fileName = "";
				String thumbName = "";

				req.setCharacterEncoding("utf-8");

				File file = null;
				MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH,
						fileSize, "UTF-8", new UniqFileRenamePolicy());

				mSeq = Integer
						.parseInt(F.nullCheck(multi.getParameter("mSeq"), "0"));
				int pSeq = Integer.parseInt(F.nullCheck(multi.getParameter("pSeq"),
						"0"));
				String userId = F.nullCheck(multi.getParameter("userId"), "");
				String pageTitle = F.nullCheck(multi.getParameter("pageTitle"), "");
				String pageContents = F.nullCheck(
						multi.getParameter("pageContents"), "");
				int page = Integer.parseInt(F.nullCheck(multi.getParameter("page"),
						"0"));

				Enumeration files = multi.getFileNames();
				while (files.hasMoreElements()) {
					String elementName = (String) files.nextElement();
					file = multi.getFile(elementName);
					if (file != null) {
						fileName = file.getName();
					}
				}

				if (fileName.equals("") == false) {
					// 축소이미지 저장
					File newFile = new File(FILE_LOCAL_PATH + fileName);
					File thumbFile = new File(FILE_LOCAL_PATH + "thumb/" + fileName);
					if (!thumbFile.exists()) {
						thumbFile.mkdirs();
					}
					try {
						ImageUtil.resize(newFile, thumbFile, 100, 0);
						result = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (pSeq == 0) {
					MagazinePage mp = new MagazinePage();
					mp.setmSeq(mSeq);
					mp.setPageContents(pageContents);
					mp.setPageTitle(pageTitle);
					mp.setPage(page);
					mp.setPageFilename(fileName);
					mp.setPageThumname(fileName);
					magazinePageDao.addMagazinePage(mp);
					pSeq = magazinePageDao.getLastId();

					result = true;
					message = "등록되었습니다.";
				} else {
					MagazinePage mp = magazinePageDao.getMagaginePage(pSeq);
					mp.setmSeq(mSeq);
					mp.setPageContents(pageContents);
					mp.setPageTitle(pageTitle);
					mp.setPage(page);
					if (fileName.equals("") == false) {
						mp.setPageFilename(fileName);
						mp.setPageThumname(fileName);
					}
					magazinePageDao.updateMagazinePage(mp);
					result = true;
					message = "수정되었습니다.";
				}
			} catch (Exception e) {
				message = e.getMessage();
			}
			map.put("mSeq", mSeq);
			map.put("result", result);
			map.put("message", message);

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
		}

		// 건강매거진 서브 이미지 삭제
		@RequestMapping("/admin/contents/subcontents_file_delete.go")
		public String subcontentsFileDeleteController(
				@RequestParam(value = "pSeq", required = false, defaultValue = "0") int pSeq,
				@RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
				HttpServletResponse res) {

			Map<String, Object> map = new HashMap<String, Object>();

			try {

				String FILE_LOCAL_PATH = FILE_ROOT + "/files/submagazine/";
				String filePath = FILE_LOCAL_PATH + fileName;
				String thumbPath = FILE_LOCAL_PATH + "thumb/" + fileName;

				File file = new File(filePath);
				file.delete();

				File thumb = new File(thumbPath);
				thumb.delete();

				// 게시물 업데이트
				magazinePageDao.updateFile("", pSeq);
				map.put("pSeq", pSeq);
				map.put("message", "파일이 삭제되었습니다.");
				map.put("result", true);
			} catch (Exception e) {

				map.put("message", "파일이 삭제되지 않았습니다.\n" + e.getMessage());
				map.put("result", false);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);

			return null;
		}

		// 서브페이지 삭제
		@RequestMapping("/admin/contents/subcontents_delete_do.go")
		public String subcontentsDeleteController(
				@RequestParam(value = "pSeq", required = false, defaultValue = "0") int pSeq,

				HttpServletResponse res) {

			Map<String, Object> map = new HashMap<String, Object>();

			try {
				MagazinePage mp = magazinePageDao.getMagaginePage(pSeq);
				String fileName = mp.getPageFilename();

				String FILE_LOCAL_PATH = FILE_ROOT + "/files/submagazine/";
				String filePath = FILE_LOCAL_PATH + fileName;
				String thumbPath = FILE_LOCAL_PATH + "thumb/" + fileName;

				File file = new File(filePath);
				file.delete();

				File thumb = new File(thumbPath);
				thumb.delete();

				// 게시물 삭제
				magazinePageDao.deleteMagazinePage(pSeq);

				List<MagazinePage> list = magazinePageDao.getMagazinePageList(mp
						.getmSeq());
				for (int i = 1; i < list.size() + 1; i++) {
					int seq = (list.get(i - 1)).getpSeq();
					magazinePageDao.updatePage(seq, i);
				}

				map.put("message", "페이지가 삭제되었습니다.");
				map.put("result", true);
			} catch (Exception e) {

				map.put("message", "페이지가 삭제되지 않았습니다.\n" + e.getMessage());
				map.put("result", false);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);

			return null;
		}

		@RequestMapping("/admin/contents/subcontents_sort_do.go")
		public String subsortEditController(
				@RequestParam(value = "arrSeq", required = false, defaultValue = "") String[] arrSeq,
				HttpServletResponse res, Model model) {

			Map<String, Object> map = new HashMap<String, Object>();
			boolean result = true;
			String message = "";

			try {

				int Count = arrSeq.length;

				if (arrSeq != null || !(arrSeq.equals(""))) {

					for (int i = 0; i < Count; i++) {

						int pSeq = Integer.parseInt(arrSeq[i]);

						magazinePageDao.updatePage(pSeq, i + 1);

						result = true;
						message = "수정되었습니다.";
					}

				}

			} catch (Exception e) {
				message = e.getMessage();
			}

			map.put("result", result);
			map.put("message", message);

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
		}

	List dangerList(String userId){
		List list = new ArrayList<>();
		
		UserPress up =userPressDao.getUserPressLimit1(userId);
		UserBlood ub= userBloodDao.getUserBloodLimit1(userId);
		UserChol uc = userCholDao.getUserCholLimit1(userId);
		UserWeight uw = userWeightDao.getUserWeightLimit1(userId);
		UserActivity ua = userActivityDao.getUserActivityLimit1(userId);
		UserSmoke us = userSmokeDao.getUserSmokeLimit1(userId);
		UserDrink ud= userDrinkDao.getUserDrinkLimit1(userId);
	
		if(up.getStatus()!=2&&up.getStatus()!=0){
			list.add("혈압");
		}
		if(ub.getStatus()!=2&&ub.getStatus()!=0){
			list.add("혈당");
		}
		if(uc.getStatus()!=2&&uc.getStatus()!=0){
			list.add("콜레스테롤");
		}
		if(uw.getStatus()!=2&&uw.getStatus()!=0){
			list.add("체중");
		}
		if(ua.getStatus()==3){
			list.add("활동");
		}
		if(0<us.getStatus()){
			list.add("흡연");
		}
		if(0<ud.getStatus()){
			list.add("음주");
		}
		
	
		return list;
	}

}
