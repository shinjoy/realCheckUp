<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">	
	function deleteUser(userId,pw) {
		if(confirm("사용자를 삭제하시겠습니까?")) {
			var param = {
					userId	:	userId,
					password : pw
			};
			
			$.ajax({
				type:"POST",
				url:"/m/myinfo_drop_do.go",
				dataType:"json",
				data:param,
				success:function(json){
					alert(json.message);
					if (json.result) {
						document.location.href = "/admin/user/user.go";
					}
				}
			});
		}
		return false;

	}
</script>

			<div class="contents-box">
					<form method="post" name="firmForm" onsubmit="return submitForm(this); return false;">
					<input type="hidden" name="userId" value="${user.userId}">
						<table class="register" style="width:1000px;">
							<colgroup>
								<col width="10%">
								<col width="20%">
								<col width="10%">
								<col width="20%">
								<col width="10%">
								<col width="30%">
							</colgroup>
							<tr>
								<th>아이디</th>
								<td>${user.userId}</td>
								<th>위험요소</th>
								<td>
									<c:forEach items="${diseaselist}" var="it">
										${it}
									</c:forEach>
								</td>
								
							</tr>
							<tr>
								<th>이름</th>
								<td>${user.userName}</td>
								<th>등록일</th>
								<td>${fn:substring(user.regDate,0,16)}</td>									
							<tr>
								<th>나이</th>
								<td>${user.userAge}</td>
								<th>최근접속일</th>
								<td>${fn:substring(user.lastLogindate,0,16)}</td>
							</tr>
							<tr>
								<th>성별</th>
								<td>${user.genderText}</td>
							</tr>
							
						</table>
						

					</form>
				</div>
