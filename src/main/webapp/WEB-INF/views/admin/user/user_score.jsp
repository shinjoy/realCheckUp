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
		document.location.href="/admin/user/user_score.go?userId="+frm.userId.value+"&type="+frm.type.value+"&page="+page;
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

				<form>
				<input type="hidden" name="userId" value="${user.userId }">
				<div class="tbl-list">
					<div>
						<select name="type" onchange="searchList(this.form,1)">
							<option value="health_score" ${type=='health_core' ? 'selected=/"selected/"':''  }>건강점수</option>
							<option value="stomach_cancer"  ${type=='stomach_cancer' ? 'selected=/"selected/"':''  }>위암</option>
							<option value="liver_cancer" ${type=='liver_cancer' ? 'selected=/"selected/"':''  }>간암</option>
							<option value="lug_cancer" ${type=='lug_cancer' ? 'selected=/"selected/"':''  }>폐암</option>
							<option value="colorectal_cancer" ${type=='colorectal_cancer' ? 'selected=/"selected/"':''  }>대장암</option>
							<option value="breast_cancer" ${type=='breast_cancer' ? 'selected=/"selected/"':''  }>유방암</option>
							<option value="stroke" ${type=='stroke' ? 'selected=/"selected/"':''  }>뇌졸증</option>
							<option value="heartdisease" ${type=='heartdisease' ? 'selected=/"selected/"':''  }>심질환</option>
							<option value="diabetes_mellitus" ${type=='diabetes_mellitus' ? 'selected=/"selected/"':''  }>당뇨</option>
							<option value="nephropathy" ${type=='nephropathy' ? 'selected=/"selected/"':''  }>신장</option>
							<option value="dementia" ${type=='dementia' ? 'selected=/"selected/"':''  }>치매</option>
						</select>
					</div>
					<table class="register" style="margin-top:10px; width: 1000px;">
					<c:choose>
					<c:when test="${list.size()>0 }">
						<thead>
							<tr class="center">
								<th>번호</th>
								<th>수치</th>
								<th>상태</th>
								<th>랭킹</th>
								<th>등록일</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${list}" var="it">
							<tr class="center" >
									<td style="padding-left:10px;">${it.dataSeq}</td>
									<c:choose>
										<c:when test="${type=='health_score'}">
											<td style="padding-left:10px;">${it.healthScore}</td>
										</c:when>
										<c:when test="${type=='stomach_cancer'}">
											<td style="padding-left:10px;">${it.stomachCancer}</td>
										</c:when>
										<c:when test="${type=='liver_cancer'}">
											<td style="padding-left:10px;">${it.liverCancer}</td>
										</c:when>
										<c:when test="${type=='lug_cancer'}">
											<td style="padding-left:10px;">${it.lugCancer}</td>
										</c:when>
										<c:when test="${type=='colorectal_cancer'}">
											<td style="padding-left:10px;">${it.colorectalCancer}</td>
										</c:when>
										<c:when test="${type=='breast_cancer'}">
											<td style="padding-left:10px;">${it.breastCancer}</td>
										</c:when>
											<c:when test="${type=='stroke'}">
											<td style="padding-left:10px;">${it.stroke}</td>
										</c:when>
										<c:when test="${type=='heartdisease'}">
											<td style="padding-left:10px;">${it.heartdisease}</td>
										</c:when>
										<c:when test="${type=='diabetes_mellitus'}">
											<td style="padding-left:10px;">${it.diabetesMellitus}</td>
										</c:when>
										<c:when test="${type=='nephropathy'}">
											<td style="padding-left:10px;">${it.nephropathy}</td>
										</c:when>
										<c:when test="${type=='dementia'}">
											<td style="padding-left:10px;">${it.dementia}</td>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									<td style="padding-left:10px;">${it.statusTxt}</td>
									<td style="padding-left:10px;">${it.userRank}</td>
									<td style="padding-left:10px;">${fn:substring(it.regDate,0,16)}</td>
							</tr>
						</c:forEach>	
						</tbody>
					
					
					<br>
				
					</c:when>
					<c:otherwise>
						<tr class="center"><td colspan="4" style="height:200px;">데이터가 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
				
				</table>
				${paging}
				</div>
				
			
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>