<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<!--[if IE 6]><html lang="ko" class="no-js old ie6"><![endif]-->
<!--[if IE 7]><html lang="ko" class="no-js old ie7"><![endif]-->
<!--[if IE 8]><html lang="ko" class="no-js old ie8"><![endif]-->
<!--[if IE 9]><html lang="ko" class="no-js ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ko" class="no-js modern"><!--<![endif]-->

<% request.setCharacterEncoding("utf-8"); %>
<%
 response.setHeader("Cache-Control","no-cache"); 
 response.setHeader("Pragma","no-cache"); 
 response.setDateHeader("Expires",0); 
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>RealChekcUp</title>
	
	<link rel="stylesheet" type="text/css" href="/css/bb-1.0.2.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-form-1.0.2.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-popup-1.0.1.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-popup-2.0.1.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-tab-1.0.1.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-table-1.0.2.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-admin-1.0.3.css" />
	<link rel="stylesheet" type="text/css" href="/css/bb-calendar-1.0.1.css" /> 

	<script type="text/javascript" src="/lib/bestist/big-button-0.0.1.js"></script>
	<script type="text/javascript" src="/lib/bestist/big-button-form-0.0.1.js"></script>
	<script type="text/javascript" src="/lib/bestist/big-button-jquery-0.0.1.js"></script>
	<script type="text/javascript" src="/lib/bestist/jquery.leanModal.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" type="text/javascript"></script>


	<!-- DIV 스크롤 -->
<!-- 	<script type="text/javascript" src="/lib/iscroll/ender.js"></script> -->
	<script type="text/javascript" src="/lib/iscroll/iscroll.js"></script>
	<script type="text/javascript" src="/lib/iscroll/iscroll-lite.js"></script>

	<script type="text/javascript" src="/lib/jquery/jquery.form.js"></script>


<style>
	body { padding:20px; background-color:#fff; }
	h1 {font-weight:bold;font-size:14px;}
	ol.my-form li {
		list-style:decimal;
		font-weight:bold;
		font-size:16px;
		margin-left:30px;
	}
	.div-title { clear:both; border-bottom:1px solid #000; font-size:16px; font-weight:bold; margin-top:20px; color:#f40; padding-top:30px; }
	.api { min-width:1100px; clear:both; margin-top:20px; }
	.api dt { width:200px; }
	.api .in { float:left; }
	.api .out { float:left; width:600px; }
	.api .in dt { clear:left; float:left; }
	.api .in dd { float:left; }
	.result-view { border:1px solid #aaa; padding:10px; clear:both; word-break:break-all; }
	.real { color:#f40; min-height:26px; }
	input { width:264px !important; }
</style>

<script>

	$(document).ready(function() {
		/* 폼 ajax전송 : http://malsup.com/jquery/form/#ajaxForm */
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	formCheck,
			success :		formSuccess
		};
		$('#login').ajaxForm(options);
		$('#aimmed_check').ajaxForm(options);
		$('#join').ajaxForm(options);
		$('#myinfo_edit_do').ajaxForm(options);
		
		$('#photo_upload').ajaxForm(options);
		$('#profile_photo').ajaxForm(options);
		$('#profile_photo_delete').ajaxForm(options);
		$('#dup_check_id').ajaxForm(options);
		$('#dup_check_phone').ajaxForm(options);
		$('#myid').ajaxForm(options);
		$('#mypass').ajaxForm(options);
		$('#new_mypass').ajaxForm(options);
		$('#maga_list').ajaxForm(options);
		$('#myinfo').ajaxForm(options);
		$('#myinfo_phone_do').ajaxForm(options);
		$('#myinfo_drop_do').ajaxForm(options);
		$('#myinfo_pass_do').ajaxForm(options);
		$('#aimmed_send_sms').ajaxForm(options);
		$('#myinfo_push').ajaxForm(options);
		$('#now_version').ajaxForm(options);
		$('#logout').ajaxForm(options);
		$('#pressure').ajaxForm(options);
		$('#cholesterol').ajaxForm(options);
		$('#weight').ajaxForm(options);
		$('#bloodsugar').ajaxForm(options);
		$('#activity').ajaxForm(options);
		$('#smoke').ajaxForm(options);
		$('#drink').ajaxForm(options);
		$('#all').ajaxForm(options);
		$('#userData').ajaxForm(options);
		$('#userDataList').ajaxForm(options);
		$('#userDanger').ajaxForm(options);
		$('#med_exam').ajaxForm(options);
		
		$('#maga_view_check').ajaxForm(options);
		$('#magazinelist').ajaxForm(options);
		$('#basic').ajaxForm(options);
		$('#num').ajaxForm(options);
		('#basic_list').ajaxForm(options);
		$('#basic_info').ajaxForm(options);
		$('#checkup_info').ajaxForm(options);
		$('#checkup').ajaxForm(options);
		$('#checkup_list').ajaxForm(options);
		$('#main').ajaxForm(options);
		$('#health_main').ajaxForm(options);
		$('#real_rank').ajaxForm(options);
		$('#main_detail').ajaxForm(options);
		
	});
	
	function formCheck(formData, jqForm, options) {
		$("#"+resultDiv+"-result").html("");
		return true; 
	}
	function formSuccess(responseText, statusText, xhr, $form) {
		//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText );
		var json = JSON.parse(responseText);
		try {
			$("#"+resultDiv+"-result").html(responseText);
		} catch (e) {
            alert(json.message); 
		}
	}

	var resultDiv;
	function formSubmit(div) {
		resultDiv = div;
	}

</script>

</head>
<body>

<h1 class="ad_title">RealCheckUp Server API</h1>

<div class="div-title">
	회원관리
</div>

<ol class="my-form">
	
	
	
	
	<div class="api">
	<c:set var="url" value="dup_check_id"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>아이디중복체크</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="이메일" class="bb"></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"중복된 이메일 주소입니다 다른주소로 입력해주세요.
					       지속적인 문제 발생시 (관리자이메일)로 이메일을 보내주세요","result":true}<br>
					{"message":"사용가능한 이메일입니다.","result":false}<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
		<c:set var="url" value="dup_check_phone"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>전화번호중복체크</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>phoneNumber</dt>	<dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"사용중인 전화번호 입니다.","result":false}<br>
					{"message":"사용할 수 있는 전화번호입니다..","result":true}<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="aimmed_check"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
		<li>리엑트, 리본 사용자 체크</li>
			<h2 class="page-title">/m/${url}.go</h2>
		
			<dl class="in">
				<dt>userName</dt>	<dd><input type="text" name="userName" placeholder="userName" class="bb"></dd>
				<dt>phoneNumber</dt>	<dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>birthday</dt>		<dd><input type="text" name="birthday" placeholder="birthday" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"에임메드 회원입니다.","groupName":"홍길동","aimmedId":"redeastload","isAimmedUser":1,"groupCode":"123456"}<br>
					{"message":"에임메드 회원이 아닙니다.","groupName":"","aimmedId":"","isAimmedUser":0,"groupCode":""}
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	
	<div class="api">
	<c:set var="url" value="join"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>회원가입</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>		<dd><input type="text" name="userId" placeholder="이메일" class="bb"></dd>
				<dt>password</dt>		<dd><input type="password" name="password" placeholder="password" class="bb"></dd>
				<dt>userName</dt>	<dd><input type="text" name="userName" placeholder="userName" class="bb"></dd>
				<dt>phoneNumber</dt>	<dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>loginNaver</dt>	<dd><input type="text" name="loginNaver" placeholder="0:사용안함 1:사용" class="bb"></dd>
				<dt>loginKakao</dt>	<dd><input type="text" name="loginKakao" placeholder="0:사용안함 1:사용" class="bb"></dd>
				<dt>gender</dt>	<dd><dd><input type="text" name="gender" placeholder="1:남자 2:여자" class="bb"></dd>
				<dt>appVersion</dt>	<dd><input type="text" name="appVersion" placeholder="appVersion" class="bb"></dd>
				<dt>photo</dt>	<dd><input type="text" name="fileName"  class="bb"></dd>
				 <dt>osType</dt>	<dd><input type="text" name="osType" placeholder="osType" class="bb"></dd>
				<dt>osversion</dt>		<dd><input type="text" name="osversion" placeholder="osversion" class="bb"></dd>
				<dt>deviceName</dt>		<dd><input type="text" name="deviceName" placeholder="deviceName" class="bb"></dd>
				<dt>deviceId</dt>		<dd><input type="text" name="deviceId" placeholder="deviceId" class="bb"></dd>
				<dt>pushKey</dt>		<dd><input type="text" name="pushKey" placeholder="pushKey" class="bb"></dd>
				<dt>birthday</dt>		<dd><input type="text" name="birthday" placeholder="birthday" class="bb"></dd>
				<dt>usePushservice</dt><dd><input type="text" name="usePushservice" placeholder="0:사용안함 1:사용" class="bb"></dd>
				<dt>userType</dt>		<dd><input type="text" name="userType" placeholder="1: 최고관리자, 2:상담자, 3:일반회원" class="bb"></dd>
				<dt>aimmedId</dt>		<dd><input type="text" name="aimmedId" placeholder="리액트,리본계정" class="bb"></dd>
				<dt>groupCode</dt>		<dd><input type="text" name="groupCode" placeholder="에임메드 그룹코드" class="bb"></dd>
				<dt>groupName</dt>		<dd><input type="text" name="groupName" placeholder="에임메드 그룹이름" class="bb"></dd>
				<dt>&nbsp;</dt>		<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"회원가입되었습니다.","result":true}<br>
					{"message":"존재하는 아이디 입니다.","result":false}
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
		<c:set var="url" value="myid"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>아이디 찾기</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userName</dt>	<dd><input type="text" name="userName" placeholder="userName" class="bb"></dd>
				<dt>phoneNumber</dt><dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>&nbsp;</dt>		<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{{userId},"result":true}<br>
					{"message":"회원님의 ID를 찾을 수 없습니다.아직 회원이 아니시면 아래 회원가입 버튼을 클릭해 주세요","result":false}<br>

				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
 	<div class="api">
 		<c:set var="url" value="mypass"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>비밀번호 찾기</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	    <dd><input type="text" name="userId" placeholder="이메일" class="bb"></dd>
				<dt>phoneNumber</dt><dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>&nbsp;</dt>		<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"비밀번호를 재설정해주세요."->비밀번호변경 페이지로,"result":true}<br>
					{"message":"회원님의 데이터를 찾을 수 없습니다.아직 회원이 아니시면 아래 회원가입 버튼을 클릭해 주세요","result":false}<br>

				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
		
			<c:set var="url" value="login"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>모바일 로그인</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt> <dd><input type="text" name="userId" placeholder="이메일" class="bb"></dd>
				<dt>password</dt> <dd><input type="password" name="password" placeholder="password" class="bb"></dd>
				<dt>os_version</dt> <dd><input type="text" name="osVersion" placeholder="os_version" class="bb"></dd>
				<dt>os_type</dt> <dd><input type="text" name="osType" placeholder="osTyp" class="bb"></dd>
				<dt>device_name</dt> <dd><input type="text" name="deviceName" placeholder="deviceName" class="bb"></dd>
				<dt>device_id</dt> <dd><input type="text" name="deviceId" placeholder="deviceId" class="bb"></dd>
				<dt>pushKey</dt> <dd><input type="text" name="pushKey" placeholder="pushKey" class="bb"></dd>
				<dt>loginNaver</dt> <dd><input type="text" name="loginNaver" placeholder="1:네이버로 로그인, 0" class="bb"></dd>
				<dt>loginKakao</dt> <dd><input type="text" name="loginKakao" placeholder="1:카카오로 로그인, 0" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"로그인이 성공되었습니다.","result":true}<br>
					{"message":"해당 아이디는 존재하지 않습니다.","result":false}<br>
					{"message":"비밀번호가 일치하지 않습니다.","result":false}
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	


	
<div class="div-title">
	사진
</div>
		

	<div class="api">
	
		<form method="post" id="photo_upload" name="photo_upload" action="/m/photo_upload.go" enctype="multipart/form-data">
			<li>사진 업로드</li>
			<h1 class="page-title">/m/photo_upload.go</h1>
			<dl class="in">
				<dt>type</dt>				<dd><input type="text" name="type" placeholder="chat" class="bb"></dd>
				<dt>isThumb</dt>			<dd><input type="text" name="isThumb" placeholder="0:원본 1:썸네일" class="bb"></dd>
				<dt>addThumb</dt>	<dd><input type="text" name="addThumb" placeholder="0:저장안함 1:섬네일저장" class="bb"></dd>
				<dt>userId</dt>				<dd><input type="text" name="userId" placeholder="등록자ID" class="bb"></input></dd>
				<dt>file</dt>				<dd><input type="file" name="file" class="bb"></dd>
				<dt>&nbsp;</dt>				<dd><button type="submit" onclick="formSubmit('photo_upload');" class="bb round green" style="width:274px;">확인</button></dd>
				<dt>&nbsp;</dt>			
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"사진이 등록되었습니다.","result":true,"path":"/files/chat/201511/","orgFileName":"","photo":"3143420151116005447.jpg"}
					<br>
				</div>
				<div id="photo_upload-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>



<div class="div-title">
	홈메뉴
</div>

	<div class="api">
	<c:set var="url" value="myinfo"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>내정보(프로필)</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{data}
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="myinfo_edit_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>프로필수정</li>
			<h2 class="page-title">/m/${url}.go</h2>
		
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId"  class="bb"></dd>
				<dt>userName</dt>	 <dd><input type="text" name="userName"  class="bb"></dd>
				<dt>gender</dt>	 <dd><input type="text" name="gender"  class="bb"></dd>
				<dt>birthday</dt>	 <dd><input type="text" name="birthday"  class="bb"></dd>
				
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div> 
		
	<div class="api">
	<c:set var="url" value="profile_photo"></c:set>
	<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
	
			<li>프로필이미지 업데이트</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt> <dd><input type="text" name="userId" placeholder="이메일" class="bb"></dd>
				<dt>fileName</dt> <dd><input type="text" name="fileName" placeholder="이미지 삭제시 빈값" class="bb"></dd>
			
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="myinfo_phone_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>휴대전화변경</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>phoneNumber</dt> <dd><input type="text" name="phoneNumber" placeholder="phoneNumber" class="bb"></dd>
				<dt>password</dt>	 <dd><input type="password" name="password" placeholder="password" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{result : false msg:비밀번호가 일치하지않습니다}
					{result : true msg:변경되었습니다.}
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="myinfo_pass_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>비밀번호변경</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>password</dt>	 <dd><input type="password" name="password" placeholder="password" class="bb"></dd>
			    <dt>npassword</dt>	 <dd><input type="password" name="npassword" placeholder="npassword" class="bb"></dd> 
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{result : false msg:비밀번호가 일치하지않습니다}
					{result : true msg:변경되었습니다.}
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
		
	<div class="api">
	<c:set var="url" value="myinfo_drop_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>탈퇴</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>password</dt>	 <dd><input type="password" name="password" placeholder="password" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{result : false msg:비밀번호가 일치하지않습니다}
					{result : true msg:정상적으로 탈퇴 되었습니다.}
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
			
	<div class="api">
	<c:set var="url" value="myinfo_push"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>push관리</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>usePushservice</dt>	 <dd><input type="text" name="usePushservice" placeholder="0끔 1 켬" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
					<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
		<c:set var="url" value="now_version"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>앱버전</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>				
	<div class="api">
		<c:set var="url" value="logout"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>로그아웃</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<br>
	<br>
	<br>
	
<div class="div-title">
	위험요인 및 관리
</div>

	<div class="api">
		<c:set var="url" value="userDanger"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/list.go">
			<li>사용자 위험요인</li>
			<h2 class="page-title">/m/${url}/delete.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<div class="api">
		<c:set var="url" value="pressure"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>혈압 등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>checkSeq</dt>	 <dd><input type="text" name="checkSeq" placeholder="checkSeq" class="bb"></dd>
				<dt>splessure</dt>	 <dd><input type="text" name="splessure" placeholder="수축기" class="bb"></dd>
				<dt>dplessure</dt>	 <dd><input type="text" name="dplessure" placeholder="이완기" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
		<c:set var="url" value="bloodsugar"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>혈당 등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>checkSeq</dt>	 <dd><input type="text" name="checkSeq" placeholder="checkSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>bloodTime</dt>	 <dd><input type="text" name="bloodTime" placeholder="1아침 2 점심 3 저녁" class="bb"></dd>
				<dt>bloodKind</dt>	 <dd><input type="text" name="bloodKind" placeholder="1공복 2식후 3 취침전" class="bb"></dd>
				<dt>bloodSugar</dt>	 <dd><input type="text" name="bloodSugar" placeholder="bloodSugar" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
		<div class="api">
		<c:set var="url" value="cholesterol"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>콜레스테롤등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>checkSeq</dt>	 <dd><input type="text" name="checkSeq" placeholder="checkSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>cholesterol</dt>	 <dd><input type="text" name="cholesterol" placeholder="총콜레스테롤" class="bb"></dd>
				<dt>tg</dt>	 <dd><input type="text" name="cholesterol" placeholder="중성지방" class="bb"></dd>
				<dt>hdl</dt>	 <dd><input type="text" name="cholesterol" placeholder="고밀도콜레스테롤" class="bb"></dd>
				<dt>type</dt>	 <dd><input type="text" name="cholesterol" placeholder="type 0:그냥등록 1:검진에서 등록" class="bb"></dd>
				<dt>ldl</dt>	 <dd><input type="text" name="ldl" placeholder="저밀도콜레스테롤" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
		<c:set var="url" value="weight"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>체중등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>medSeq</dt>	 <dd><input type="text" name="medSeq" placeholder="medSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>bmi</dt>	 <dd><input type="text" name="bmi" placeholder="bmi" class="bb"></dd>
				<dt>weight</dt>	 <dd><input type="text" name="weight" placeholder="weight" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
		<c:set var="url" value="activity"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>활동량등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
			<dt>medSeq</dt>	 <dd><input type="text" name="medSeq" placeholder="medSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>avgActivity</dt>	 <dd><input type="text" name="avgActivity" placeholder="avgActivity" class="bb"></dd>
				<dt>timeActivity</dt>	 <dd><input type="text" name="timeActivity" placeholder="timeActivity" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					avgActivity 	0: 전혀 하지 않음
									1: 주 1~2회
									2: 주 3~4회
									3: 주 5회 이상<br>
					timeActivity	0: 30분이하
									1: 30~60분
									2: 60~120분
									3: 120분 이상			
									
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<div class="api">
		<c:set var="url" value="smoke"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>흡연량등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
			<dt>medSeq</dt>	 <dd><input type="text" name="medSeq" placeholder="medSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>avgSmoke</dt>	 <dd><input type="text" name="avgSmoke" placeholder="avgSmoke" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					avgSmoke 	1: 피웠지만 끊었음
								2: 매일 0.5갑 미만
								3: 매일 0.5~1갑 미만
								4: 매일 1~2갑 미만
								5: 매일 2갑 이상			
									
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<div class="api">
		<c:set var="url" value="drink"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>음주등록/수정</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
			<dt>medSeq</dt>	 <dd><input type="text" name="medSeq" placeholder="medSeq" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="0등록 수정 seq" class="bb"></dd>
				<dt>avgPeriodDrinking</dt>	 <dd><input type="text" name="avgPeriodDrinking" placeholder="avgPeriodDrinking" class="bb"></dd>
				<dt>avgDrinkingCapacity</dt>	 <dd><input type="text" name="avgDrinkingCapacity" placeholder="avgDrinkingCapacity" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					avgPeriodDrinking 	0: 안마심
										1: 월 2회
										2: 주 1회
										3: 주 3회
										4: 거의 매일	<br>
				   avgDrinkingCapacity	0: 소주반병(맥주 3잔) 이하
										1: 소주 1병 (맥주 7잔) 이하
										2: 소주 2병 (맥주 5병) 미만
										3: 소주 2병 (맥주 5병) 이상					
										
									
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<div class="api">
		<c:set var="url" value="all"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>한번에등록</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>regDate</dt>	 <dd><input type="text" name="regDate" placeholder="2016-03-05 16:30" class="bb"></dd>
				<dt>splessure</dt>	 <dd><input type="text" name="splessure" placeholder="수축기" class="bb"></dd>
				<dt>dplessure</dt>	 <dd><input type="text" name="dplessure" placeholder="이완기" class="bb"></dd>
				<dt>bloodTime</dt>	 <dd><input type="text" name="bloodTime" placeholder="1아침 2 점심 3 저녁" class="bb"></dd>
				<dt>bloodKind</dt>	 <dd><input type="text" name="bloodKind" placeholder="1공복 2식후 3 취침전" class="bb"></dd>
				<dt>bloodSugar</dt>	 <dd><input type="text" name="bloodSugar" placeholder="bloodSugar" class="bb"></dd>
				<dt>cholesterol</dt>	 <dd><input type="text" name="cholesterol" placeholder="총콜레스테롤" class="bb"></dd>
				<dt>ldl</dt>	 <dd><input type="text" name="ldl" placeholder="저밀도콜레스테롤" class="bb"></dd>
				<dt>bmi</dt>	 <dd><input type="text" name="bmi" placeholder="bmi" class="bb"></dd>
				<dt>weight</dt>	 <dd><input type="text" name="weight" placeholder="weight" class="bb"></dd>
				<dt>avgActivity</dt>	 <dd><input type="text" name="avgActivity" placeholder="avgActivity" class="bb"></dd>
				<dt>timeActivity</dt>	 <dd><input type="text" name="timeActivity" placeholder="timeActivity" class="bb"></dd>
				<dt>avgSmoke</dt>	 <dd><input type="text" name="avgSmoke" placeholder="avgSmoke" class="bb"></dd>
				<dt>avgPeriodDrinking</dt>	 <dd><input type="text" name="avgPeriodDrinking" placeholder="avgPeriodDrinking" class="bb"></dd>
				<dt>avgDrinkingCapacity</dt>	 <dd><input type="text" name="avgDrinkingCapacity" placeholder="avgDrinkingCapacity" class="bb"></dd>
				
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					avgActivity 	0: 전혀 하지 않음
									1: 주 1~2회
									2: 주 3~4회
									3: 주 5회 이상<br>
					timeActivity	0: 30분이하
									1: 30~60분
									2: 60~120분
									3: 120분 이상			
									
					avgSmoke 	1: 피웠지만 끊었음
								2: 매일 0.5갑 미만
								3: 매일 0.5~1갑 미만
								4: 매일 1~2갑 미만
								5: 매일 2갑 이상		<br>
					avgPeriodDrinking 	0: 안마심
										1: 월 2회
										2: 주 1회
										3: 주 3회
										4: 거의 매일	<br>
				   	avgDrinkingCapacity	0: 소주반병(맥주 3잔) 이하
										1: 소주 1병 (맥주 7잔) 이하
										2: 소주 2병 (맥주 5병) 미만
										3: 소주 2병 (맥주 5병) 이상		
									
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>		
	
	<div class="api">
		<c:set var="url" value="userData"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/delete.go">
			<li>삭제</li>
			<h2 class="page-title">/m/${url}/delete.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>diseaseId</dt>	 <dd><input type="text" name="diseaseId" placeholder="" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="seq" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					diseaseId -> 혈압:pressure	혈당:bloodsugar 콜레스테롤: cholesterol 체중:weight 활동량:activity
								 흡연량:smoke 음주:drink
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	
	<div class="api">
		<c:set var="url" value="userDataList"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}/list.go">
			<li>측정 리스트</li>
			<h2 class="page-title">/m/${url}/list.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>diseaseId</dt>	 <dd><input type="text" name="diseaseId" placeholder="" class="bb"></dd>
				<dt>page</dt>	 <dd><input type="text" name="page" placeholder="page" class="bb"></dd>
			
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					diseaseId -> 혈압:pressure	혈당:bloodsugar 콜레스테롤: cholesterol 체중:weight 활동량:activity
								 흡연량:smoke 음주:drink  전체 리스트는 빈값
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
		
	<div class="div-title">
	문진
	</div>	
	<div class="api">
	<c:set var="url" value="med_exam"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>문진 질문</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="basic_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>문진 리스트</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
			<dt>page</dt>	 <dd><input type="text" name="page" placeholder="page" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="basic_info"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>문진 데이터 받기</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="seq" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="num"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>번호 업데이트</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				<dt>num</dt>	 <dd><input type="text" name="num" placeholder="num" class="bb"></dd>
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>type</dt>	 <dd><input type="text" name="type" placeholder="1문진 2 검진" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="basic"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>문진 basic 등록</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="seq" class="bb"></dd>
				<dt>pressure</dt>	 <dd><input type="text" name="pressure" placeholder="pressure" class="bb"></dd>
				<dt>bloodsugar</dt>	 <dd><input type="text" name="bloodsugar" placeholder="bloodsugar" class="bb"></dd>
				<dt>cholesterol</dt>	 <dd><input type="text" name="cholesterol" placeholder="cholesterol" class="bb"></dd>
				<dt>liver</dt>	 <dd><input type="text" name="liver" placeholder="liver" class="bb"></dd>
				<dt>mPressure</dt>	 <dd><input type="text" name="mPressure" placeholder="mPressure" class="bb"></dd>
				<dt>mBloodsugar</dt>	 <dd><input type="text" name="mBloodsugar" placeholder="mBloodsugar" class="bb"></dd>
				<dt>mCholesterol</dt>	 <dd><input type="text" name="mCholesterol" placeholder="mCholesterol" class="bb"></dd>
				<dt>mLiver</dt>	 <dd><input type="text" name="mLiver" placeholder="mLiver" class="bb"></dd>
				<dt>fPressure</dt>	 <dd><input type="text" name="fPressure" placeholder="fPressure" class="bb"></dd>
				<dt>fBloodsugar</dt>	 <dd><input type="text" name="fBloodsugar" placeholder="fBloodsugar" class="bb"></dd>
				<dt>fCholesterol</dt>	 <dd><input type="text" name="fCholesterol" placeholder="fCholesterol" class="bb"></dd>
				<dt>fLiver</dt>	 <dd><input type="text" name="fLiver" placeholder="fLiver" class="bb"></dd>
				<dt>eatBreakfast</dt>	 <dd><input type="text" name="eatBreakfast" placeholder="eatBreakfast" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="div-title">
	검진
	</div>	
	<div class="api">
	<c:set var="url" value="checkup_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>검진 리스트</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>page</dt>	 <dd><input type="text" name="page" placeholder="page" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	<div class="api">
	<c:set var="url" value="checkup_info"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>검진 데이터 받기</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="seq" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="checkup"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}/edit_do.go">
			<li>검진 등록</li>
			<h2 class="page-title">/m/${url}/edit_do.go</h2>
			<dl class="in">
				
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>seq</dt>	 <dd><input type="text" name="seq" placeholder="seq" class="bb"></dd>
				<dt>height</dt>	 <dd><input type="text" name="height" placeholder="키" class="bb"></dd>
				<dt>ast</dt>	 <dd><input type="text" name="ast" placeholder="지오티" class="bb"></dd>
				<dt>alt</dt>	 <dd><input type="text" name="alt" placeholder="cholesterol" class="bb"></dd>
				<dt>redBlood</dt>	 <dd><input type="text" name="redBlood" placeholder="liver" class="bb"></dd>
				<dt>whiteBlood</dt>	 <dd><input type="text" name="whiteBlood" placeholder="mPressure" class="bb"></dd>
				<dt>platelet</dt>	 <dd><input type="text" name="platelet" placeholder="mBloodsugar" class="bb"></dd>
				<dt>freet4</dt>	 <dd><input type="text" name="freet4" placeholder="mCholesterol" class="bb"></dd>
				<dt>tsh</dt>	 <dd><input type="text" name="tsh" placeholder="mLiver" class="bb"></dd>
				<dt>fev1</dt>	 <dd><input type="text" name="fev1" placeholder="fPressure" class="bb"></dd>
				<dt>cr</dt>	 <dd><input type="text" name="cr" placeholder="fBloodsugar" class="bb"></dd>
				<dt>bun</dt>	 <dd><input type="text" name="bun" placeholder="fCholesterol" class="bb"></dd>
				
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="div-title">
	 	메인
	</div>
	
	<div class="api">
	<c:set var="url" value="main"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>메인 위험도 그래프</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>date</dt>	 <dd><input type="text" name="date" placeholder="20160203" class="bb"></dd>
			
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="health_main"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>건강점수</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>date</dt>	 <dd><input type="text" name="date" placeholder="20160203" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
				
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="main_detail"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>상세화면</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>type</dt>	 <dd><input type="text" name="type" placeholder="type" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					type 
					
					STROKE //뇌졸증
					HEARTDISEASE//심질환
					DIABETES_MELLITUS//당뇨
					DEMENTIA//치매
					NEPHROPATHY//신장
					STOMACH_CANCER//위암
					LIVER_CANCER//간암
					LUG_CANCER//폐암
					COLORECTAL_CANCER//대장암
					BREAST_CANCER//유방암
					HEALTH_SCORE//건강점수
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
		<div class="api">
		<c:set var="url" value="real_rank"></c:set>
			<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
				<li>리얼랭킹</li>
				<h2 class="page-title">/m/${url}.go</h2>
				<dl class="in">
					<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
					<dt>type</dt>	 <dd><input type="text" name="type" placeholder="type" class="bb"></dd>
					<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
				</dl>
				<div class="out">
					<div class="result-view">
						type 
					
						STROKE //뇌졸증
						HEARTDISEASE//심질환
						DIABETES_MELLITUS//당뇨
						DEMENTIA//치매
						NEPHROPATHY//신장
						STOMACH_CANCER//위암
						LIVER_CANCER//간암
						LUG_CANCER//폐암
						COLORECTAL_CANCER//대장암
						BREAST_CANCER//유방암
						HEALTH_SCORE//건강점수
					</div>
					<div id="${url}-result" class="result-view real"></div>
				</div>
				<div class="clear"></div>
			</form>
		</div>
	<div class="div-title">
	  건강매거진
	</div>
	
	<div class="api">
	<c:set var="url" value="maga_view_check"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>건강매거진 조회수</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
				<dt>mSeq</dt>	 <dd><input type="text" name="mSeq" placeholder="매거진 시퀀스" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
		
			<div class="clear"></div>
		</form>
	</div>
	<div class="api">
	<c:set var="url" value="magazinelist"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>건강매거진 리스트 top3</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			<dt>userId</dt>	 <dd><input type="text" name="userId" placeholder="userId" class="bb"></dd>
			<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
		<div class="out">
				<div class="result-view">
					{"message":"사용중인 전화번호 입니다.","result":false}<br>
					{"message":"사용할 수 있는 전화번호입니다..","result":true}<br>
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	
	<div class="api">
	<c:set var="url" value="maga_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<li>건강매거진</li>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			</dl>
		
			<div class="clear"></div>
			
		</form>
	</div>



	


	

	
	<div class="div-title">
	  공지사항&도움말
	</div>

	
	<div class="api">
		<form method="post" id="service-list" name="service-list" action="/m/notice/notice.go">
			<li>공지사항(n)</li>
			<h1 class="page-title">/m/notice/notice.go</h1>
			<dl class="in">
				<dt>page</dt>	 <dd><input type="text" name="page" placeholder="page" class="bb"></dd>
			
			
				
			</dl>
		
			
			<div class="clear"></div>
		</form>
	</div>	

	<div class="api">
		<form method="post" id="service-qna-detail" name="service-qna-detail" action="/m/faq/faq.go">
			<li>도움말(n)</li>
			<h1 class="page-title">/m/faq/faq.go</h1>
			<dl class="in">
				
			
			
			<div class="clear"></div>
		</form>
	</div>		


	




	
	<div class="api">
		<form method="post" id="aimmed_send_sms" name="aimmed_send_sms" action="/m/aimmed_send_sms.go">
			<li>에임메드 SMS 발송</li>
			<h1 class="page-title">/m/aimmed_send_sms.go</h1>
			<dl class="in">
				<dt>message</dt>	 <dd><input type="text" name="message" placeholder="message" class="bb"></dd>
				<dt>to</dt>	 <dd><input type="text" name="to" value="" placeholder="수신전화번호" class="bb"></dd>
				<dt>from</dt>	 <dd><input type="text" name="from" value="0230157554" class="bb"></dd>
				<dt>secret</dt>	 <dd><input type="text" name="secret" value="AimmedRecover" class="bb"></dd>
				<dt>&nbsp;</dt>	 <dd><button type="submit" onclick="formSubmit('aimmed_send_sms');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"인증번호가 발송되었습니다.","result":true}
				</div>
				<div id="aimmed_send_sms-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</div>	
	

</ol>

</body>
</html>