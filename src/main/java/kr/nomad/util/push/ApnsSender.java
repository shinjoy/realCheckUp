package kr.nomad.util.push;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

public class ApnsSender {

	/**
	 * ? ν ?Έμ ?μ‘ (APNS)	
	 * @param certificate : ?Έμ¦???μΌ(*.p12) κ²½λ‘
	 * @param password : ?Έμ¦??λΉλ?λ²νΈ
	 * @param production : ?μ€??false | ?€μλΉμ€=true 
	 * @param udid : ?λ°?΄μ€ κ³ μ ???Έμ??
	 * @param message : ?μ‘?λ λ©μΈμ§?
	 * @param extra : ?μ‘?λ λ©μΈμ§??Έμ μ»€μ€???μ?λ¦¬???£μ ?°μ΄??
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
	 * ??©??? ν ?Έμ ?μ‘ (APNS)
	 * @param certificate : ?Έμ¦???μΌ(*.p12) κ²½λ‘
	 * @param password : ?Έμ¦??λΉλ?λ²νΈ
	 * @param production : ?μ€??false | ?€μλΉμ€=true 
	 * @param devices : ?λ°?΄μ€ κ³ μ ???Έμ???€μ List
	 * @param message : ?μ‘?λ λ©μΈμ§?
	 * @param extra : ?μ‘?λ λ©μΈμ§??Έμ μ»€μ€???μ?λ¦¬???£μ ?°μ΄??
	 * @param threadCount : ?μ‘???¬μ©??thread??κ°?
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
			//success : [[16777217] transmitted {"title":"hello","aps":{"sound":"default","alert":"test λ©?° ?Έμ","badge":-1},"seq":"1"} on first attempt to token eb7a5..fcb0e]
			//fail    : [[16777217] not transmitted to token eb7a5..fcb0e  javapns.communication.exceptions.InvalidCertificateChainException: Invalid certificate chain (Received fatal alert: certificate_unknown)!  Verify that the keystore you provided was produced according to specs...]
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
