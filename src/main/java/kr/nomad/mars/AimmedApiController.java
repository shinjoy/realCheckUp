
package kr.nomad.mars;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.nomad.mars.dao.NoticeDao;

import kr.nomad.mars.dao.UserDao;

import kr.nomad.mars.dto.User;

import kr.nomad.util.Response;
import kr.nomad.util.T;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AimmedApiController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	


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

	public String GOOGLE_MAIL_ID = "csk740222@gmail.com";
	public String GOOGLE_MAIL_PW = "csk740222";

	private int category1;


	@RequestMapping("/m/aimmed_send_sms.go")
	public String aimmedSendSmsController(
			@RequestParam(value = "message", required = false, defaultValue = "") String message,
			@RequestParam(value = "to", required = false, defaultValue = "") String to,
			@RequestParam(value = "from", required = false, defaultValue = "0") String from,
			@RequestParam(value = "secret", required = false, defaultValue = "") String secret, HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String sendMessage = "[Web발신] 에임메드 인증번호 [" + message + "]입니다.";
			// 끌어모았어 get 요청 URL 문자열 사용 URLEncoder.encode 대한 특별한 및 안 보이는 문자 인코딩
			// 진행하다
			String getURL = "http://api.aimmed.co.kr/recovery/sms.asp";
			getURL += "?secret=" + secret;
			getURL += "&message=" + URLEncoder.encode(sendMessage, "UTF-8");
			getURL += "&to=" + to;
			getURL += "&from=" + from;

			// String getURL = GET_URL + "?name=" +
			// URLEncoder.encode("zhangshan", "utf-8");
			URL getUrl = new URL(getURL);
			// 끌어모았어 URL을 열 따라 연결할 URL 형식은 따라 할 URL.openConnection 함수,
			// 다시 다른 URLConnection 하위 클래스 대상, 여기 URL 한 http 때문에 실제 복귀 것은
			// HttpURLConnection
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			// 을 연결 하지만 실제로는 get request 반드시 다음 구의 connection.getInputStream ()
			// 함수 중 비로소 진정한 발 까지 서버
			connection.connect();
			// 확실한 입력 및 사용 Reader 읽기
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 인코딩
																													// 설정
																													// 을
																													// 함께
																													// 했
																													// 다.,
																													// 그렇지
																													// 않으면,
																													// 중국어
			System.out.println("=============================");
			System.out.println("Contents of get request");
			System.out.println("=============================");
			String lines;
			String msg = "";
			while ((lines = reader.readLine()) != null) {
				String str = new String(lines.getBytes());
				msg += URLDecoder.decode(str, "utf-8");
				System.out.println(lines);
			}
			reader.close();
			// 연결 끊기
			connection.disconnect();

			JSONObject jsonObject = JSONObject.fromObject(msg);
			String statusCode = jsonObject.getString("STATUS_CODE");

			if (statusCode.equals("201")) {
				map.put("result", true);
				map.put("message", "인증번호가 발송되었습니다.");
			} else if (statusCode.equals("400")) {
				map.put("message", "데이터를 올바르게 입력해주세요.");
				map.put("result", false);
			} else if (statusCode.equals("401")) {
				map.put("message", "인증이 불가능합니다.\n관리자에게 문의하세요.");
				map.put("result", false);
			}
		} catch (Exception e) {
			map.put("message", "");
			map.put("result", false);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);

		return null;

	}

}
