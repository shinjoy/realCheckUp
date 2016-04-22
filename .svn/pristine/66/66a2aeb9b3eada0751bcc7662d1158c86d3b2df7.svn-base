package kr.nomad.util.push;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

public class ApnsSender {

	/**
	 * ? í”Œ ?¸ì‹œ ?„ì†¡ (APNS)	
	 * @param certificate : ?¸ì¦???Œì¼(*.p12) ê²½ë¡œ
	 * @param password : ?¸ì¦??ë¹„ë?ë²ˆí˜¸
	 * @param production : ?ŒìŠ¤??false | ?¤ì„œë¹„ìŠ¤=true 
	 * @param udid : ?”ë°”?´ìŠ¤ ê³ ìœ ???¸ì‹œ??
	 * @param message : ?„ì†¡?˜ëŠ” ë©”ì„¸ì§?
	 * @param extra : ?„ì†¡?˜ëŠ” ë©”ì„¸ì§??¸ì— ì»¤ìŠ¤???•ì…”?ˆë¦¬???£ì„ ?°ì´??
	 * @return
	 */
	public boolean sendSimple(String certificate, String password, boolean production, String udid, String message, Map<String, String> extra) {
		try {
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(-1);
			payload.addSound("default");
			
			if (extra != null) {
				Iterator<String> keys = extra.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					String value = extra.get(key);
					
					payload.addCustomDictionary(key, value);
				}
			}
			
			PushedNotifications notifications = Push.payload(payload, certificate, password, production, udid);
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ??š©??? í”Œ ?¸ì‹œ ?„ì†¡ (APNS)
	 * @param certificate : ?¸ì¦???Œì¼(*.p12) ê²½ë¡œ
	 * @param password : ?¸ì¦??ë¹„ë?ë²ˆí˜¸
	 * @param production : ?ŒìŠ¤??false | ?¤ì„œë¹„ìŠ¤=true 
	 * @param devices : ?”ë°”?´ìŠ¤ ê³ ìœ ???¸ì‹œ???¤ì˜ List
	 * @param message : ?„ì†¡?˜ëŠ” ë©”ì„¸ì§?
	 * @param extra : ?„ì†¡?˜ëŠ” ë©”ì„¸ì§??¸ì— ì»¤ìŠ¤???•ì…”?ˆë¦¬???£ì„ ?°ì´??
	 * @param threadCount : ?„ì†¡???¬ìš©??thread??ê°?ˆ˜
	 * @return
	 */
	public boolean sendLargeAmount(String certificate, String password, boolean production, List devices, String message, Map<String, String> extra, int threadPoolCount) {
		try {
			
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(-1);
			payload.addSound("default");
			
			if (extra != null) {
				Iterator<String> keys = extra.keySet().iterator();
				int keyIdx = 1;
				while (keys.hasNext()) {
					String key = keys.next();
					String value = extra.get(key);
					//payload.addCustomDictionary(key, value);
					payload.addCustomDictionary("P"+keyIdx, value);
					keyIdx++;
				}
			}
			
			PushedNotifications notifications = Push.payload(payload, certificate, password, production, threadPoolCount, devices);
			//success : [[16777217] transmitted {"title":"hello","aps":{"sound":"default","alert":"test ë©?‹° ?¸ì‹œ","badge":-1},"seq":"1"} on first attempt to token eb7a5..fcb0e]
			//fail    : [[16777217] not transmitted to token eb7a5..fcb0e  javapns.communication.exceptions.InvalidCertificateChainException: Invalid certificate chain (Received fatal alert: certificate_unknown)!  Verify that the keystore you provided was produced according to specs...]
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
