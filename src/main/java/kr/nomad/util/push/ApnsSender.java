package kr.nomad.util.push;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

public class ApnsSender {

	/**
	 * ?�플 ?�시 ?�송 (APNS)	
	 * @param certificate : ?�증???�일(*.p12) 경로
	 * @param password : ?�증??비�?번호
	 * @param production : ?�스??false | ?�서비스=true 
	 * @param udid : ?�바?�스 고유???�시??
	 * @param message : ?�송?�는 메세�?
	 * @param extra : ?�송?�는 메세�??�에 커스???�셔?�리???�을 ?�이??
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
	 * ??��???�플 ?�시 ?�송 (APNS)
	 * @param certificate : ?�증???�일(*.p12) 경로
	 * @param password : ?�증??비�?번호
	 * @param production : ?�스??false | ?�서비스=true 
	 * @param devices : ?�바?�스 고유???�시???�의 List
	 * @param message : ?�송?�는 메세�?
	 * @param extra : ?�송?�는 메세�??�에 커스???�셔?�리???�을 ?�이??
	 * @param threadCount : ?�송???�용??thread??�?��
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
			//success : [[16777217] transmitted {"title":"hello","aps":{"sound":"default","alert":"test �?�� ?�시","badge":-1},"seq":"1"} on first attempt to token eb7a5..fcb0e]
			//fail    : [[16777217] not transmitted to token eb7a5..fcb0e  javapns.communication.exceptions.InvalidCertificateChainException: Invalid certificate chain (Received fatal alert: certificate_unknown)!  Verify that the keystore you provided was produced according to specs...]
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
