<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	
	$(document).ready(function() {
		aside.setActive(1,1);
		
	});
	
	function searchList(frm,page){
		document.location.href="/admin/user/drink.go?userId="+frm.userId.value+"&page="+page;
	}

</script>

<style>


</style>
</head>
<body>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp"  %>

<section class="main-cover main-row">
	<section id="main">
		
		<%@ include file="/WEB-INF/views/admin/admin_menu.jsp"  %>

		<section id="contents">
			<header class="panel">
				 ■ 홈 > 회원관리 > 일반회원 상세보기
			</header>
		
			<div class="contents-block">
			
				<h1>${user.userName}</h1>
				
				<%@ include file="/WEB-INF/views/admin/user/user_info.jsp"  %>
				<%@ include file="/WEB-INF/views/admin/user/tabbar.jsp"  %>
		
				<div class="tbl-list">
					<form method="post">
					<input type="hidden" name="userId" value="${user.userId }">
					<table class="register" style="margin-top:10px;width: 1000px;">
					<c:choose>
						<c:when test="${list.size()>0 }">
						<thead>
							<tr class="center">
								<th>번호</th>
								<th>음주기간</th>
								<th>음주량</th>
								<th>상태</th>
								<th>등록일</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${list}" var="it">
							<tr class="center" >
									<td style="padding-left:10px;">${it.seq}</td>
									<td style="padding-left:10px;">${it.num1txt}</td>
									<td style="padding-left:10px;">${it.num2txt}</td>
									<td style="padding-left:10px;">${it.statusTxt}</td>
									<td style="padding-left:10px;">${fn:substring(it.regDate,0,16)}</td>
							</tr>
						</c:forEach>	
						</tbody>
						</c:when>
						<c:otherwise>
							<tr class="center"><td colspan="5" style="height:200px;">데이터가 없습니다.</td></tr>
						</c:otherwise>
						</c:choose>
					</table>
					
					<br>
					
				${paging}
				</div>
				</form>
			
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>