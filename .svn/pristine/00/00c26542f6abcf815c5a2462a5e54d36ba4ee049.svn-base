<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	
	$(function() {
	    $( ".datepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: 'yy-mm-dd'
	    });
  	});
	$(document).ready(function() {
		aside.setActive(1,2);
	});
	
	function submitForm(frm) {
		
		
		var param = {
			userId : frm.userId.value,
			userName : frm.userName.value,
			birthday : frm.birthday.value,
			userType : frm.userType.value,
		
			password : frm.password.value,
			kind : frm.kind.value,
			gender : frm.gender.value
			
		};
		
		$.ajax({
			type:"POST",
			url:"/admin/manager/manager_edit_do.go",
			dataType:"json",
			data:param,
			success:function(json){
				alert(json.message);
				if (json.result) {
					document.location.href = "/admin/manager/manager_view.go?userId="+frm.userId.value;
				}
			}
		});

		return false;
	}
	
	

</script>

<style>
table.register th {
   vertical-align: top;
   text-align: right;
   padding:10px;
   font-weight: bold;
}

table.register td {
	padding:10px;
    vertical-align: top;
}	
.tbl-list TD{
	border-bottom: 1px solid #ddd;
	padding: 5px;
}
</style>
</head>
<body>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp"  %>

<section class="main-cover main-row">
	<section id="main">
		
		<%@ include file="/WEB-INF/views/admin/admin_menu.jsp"  %>

		<section id="contents">
			<header class="panel">
				 ■ 홈 > 회원관리 > 관리자
			</header>
		
			<div class="contents-block">
			
				<h1>관리자 ${user.userId != '' ? "수정" : "등록" }하기</h1>
				
				<div>

					<div>
				
						<form method="post" name="firmForm" onsubmit="return submitForm(this); return false;">
						<input type="hidden" name="userType" value="1">
						<input type="hidden" name="kind" value="${user.userId != '' ? 2 : 1 }">
						
								<table class="register" style="width:1000px;">
									<tr>
										<td>아이디</td>
										<td>
										<c:choose>
											<c:when test="${user.userId == ''}">
												<input type="text" class="itext" style="width:500px;" name="userId" value="">
											</c:when>
											<c:otherwise>
												<input type="hidden" name="userId" value="${user.userId}">
												${user.userId}
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
									<tr>
										<td>이름</td>
										<td><input type="text" class="itext" style="width:500px;" name="userName" value="${user.userName}" ></td>
									</tr>
									<tr>
											<td>비밀번호</td>
											<td>
											<c:choose>
											<c:when test="${user.userId == ''}">
												<input type="text" class="itext" style="width:500px;" name="password" value="">
											</c:when>
											<c:otherwise>
												비밀번호는 변경 할 수 없습니다.
											</c:otherwise>
											</c:choose>
											</td>
									
									</tr>
									<tr>
										<td>생년월일</td>
										<td><input type="text" name="birthday" class="itext datepicker"  value="${user.birthday }" style="width:80px;"></td>
									</tr>
									<tr>
										<td>성별</td>
										<td>
											<input type="radio" name="gender" value ="1" ${user.gender== 1 ? 'checked=\"checked\"' : ""} >남자
											<input type="radio" name="gender" value ="2" ${user.gender== 2 ? 'checked=\"checked\"' : ""} >여자
										</td>
									</tr>
									
									
								</table>
							
						</div>
					</div>
				<br><br>
					<div  class="btn-tools" style="width:1000px;">	
						<button type="submit" class="btn-blue" >${user.userId!='' ? "수정":"등록"  }</button>
						<button type="button" class="btn" onclick="document.location.href='/admin/manager/manager_view.go?userId=${user.userId}';">뒤로가기</button>
					</div>
				</form>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>