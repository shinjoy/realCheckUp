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
					<table class="register" style="width:1000px;">
							<colgroup>
								
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>
								<th>기왕력</th>
								<td>
									${userBasic.pressure==1?'혈압':''}
									${userBasic.bloodsugar==1?'혈당':''}
									${userBasic.cholesterol==1?'콜레스테롤':''}
									${userBasic.liver==1?'간질환':''}
								</td>
							</tr>
							<tr>
								<th>복약력</th>
								<td>
									${userBasic.mPressure==1?'혈압':''}
									${userBasic.mBloodsugar==1?'혈당':''}
									${userBasic.mCholesterol==1?'콜레스테롤':''}
									${userBasic.mLiver==1?'간질환':''}
								</td>
							</tr>
							<tr>
								<th>가족력</th>
								<td>
									${userBasic.fPressure==1?'혈압':''}
									${userBasic.fBloodsugar==1?'혈당':''}
									${userBasic.fCholesterol==1?'콜레스테롤':''}
									${userBasic.fLiver==1?'간질환':''}
								</td>
							</tr>
							<tr>
								<th>사회력</th>
								<td>
									<c:if test="${userDrink.seq >0 }">
								  	 ${userDrink.apdtxt}	|  ${userDrink.adctxt}	 
								   </c:if>
								   <c:if test="${userSmoke.seq >0 }">
								   | ${userSmoke.avgSmoketxt}		 
								   </c:if>
								</td>
							</tr>
							<tr>
								<th>운동</th>
								<td>
									<c:if test="${userActivity.seq >0 }">
								  		 ${userActivity.avgActivitytxt}	|  ${userActivity.timeActivitytxt}	 
								   </c:if>
								</td>
							</tr>
							<tr>
								<th>아침식사</th>
								<td>
									${userBasic.eatBreakfast==1?'아침식사를 한다':'안함'}
								</td>
							</tr>
					</table>
					
					<br>
			
				</div>
					<div class="btn-tools" style="margin-top:0;">
								<button type="button" class="btn" onclick="document.location.href='/admin/user/user_view.go?userId=${userBasic.userId}';"><span>목록으로</span></button>
					</div>
			
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>