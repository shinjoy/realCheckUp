/**
 * publish : bestist@nate.com
 * create date : 2013.06.12
 * update version : 1
 * update :
 * 
 * pushKey, pushOs κ°??΄κΈ΄ push ?€λΈ?νΈλ₯?λ°μ??android, ios??λ©?° ?°λ ?λ‘ ?Έμλ₯??μ‘?λ€.
 * 
 */
package kr.nomad.util.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;

public class PushSender {

	protected String gcmApiKey;
	protected String appleCertificateFilePath;
	protected String appleCertificatePassword;
	protected JSONArray pushList;
	protected String message;
	protected HashMap extraMessage;
	
	// ?μ±??
	public PushSender(String gcmApiKey, String appleCertificateFilePath, String appleCertificatePassword) {
		this.gcmApiKey = gcmApiKey;
		this.appleCertificateFilePath = appleCertificateFilePath;
		this.appleCertificatePassword = appleCertificatePassword;
	}
	public PushSender(String gcmApiKey, String appleCertificateFilePath, String appleCertificatePassword, JSONArray pushList, String message, HashMap extraMessage) {
		this.gcmApiKey = gcmApiKey;
		this.appleCertificateFilePath = appleCertificateFilePath;
		this.appleCertificatePassword = appleCertificatePassword;
		this.pushList = pushList;
		this.message = message;
		this.extraMessage = extraMessage;
	}

	// setter
	public void setPush(JSONArray pushList) {
		this.pushList = pushList;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setExtraMessage(HashMap extraMessage) {
		this.extraMessage = extraMessage;
	}
	
	// ?Έμ ?μ‘
	public void pushSendMulti() {
		
		boolean production = false;	// ?μ€??false | ?€μλΉμ€=true
		int pushListVolume = 500;	// Thread ?λ??μ²λ¦¬??push κ°?
		int threadPoolCount;	// ?μ±??Thread Pool κ°?
		
		List androidPushList = new ArrayList();
		List applePushList = new ArrayList();
		
		try {
			for (int i=0; i<pushList.length(); i++) {
				
				String pushOs = pushList.getJSONObject(i).getString("os");
				String pushKey = pushList.getJSONObject(i).getString("pushKey");
				
				if (pushOs.equals("ANDROID")) {
					androidPushList.add(pushKey);
				} else if (pushOs.equals("IOS")) {
					applePushList.add(pushKey);
				}
			}
			
			// GCM ?μ‘
			if (androidPushList.size() > 0) {
				GcmSender gcmSender = new GcmSender(gcmApiKey, androidPushList, message, extraMessage);
				threadPoolCount = (androidPushList.size() / 100) + 1;
				try {
					ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
					executorService.execute(gcmSender);
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// APNS ?μ‘
			if (applePushList.size() > 0) {
				ApnsSender apnsSender = new ApnsSender();
				threadPoolCount = (applePushList.size() / 100) + 1;
				try {
					apnsSender.sendLargeAmount(appleCertificateFilePath, appleCertificatePassword, production, applePushList, message, extraMessage, threadPoolCount);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
