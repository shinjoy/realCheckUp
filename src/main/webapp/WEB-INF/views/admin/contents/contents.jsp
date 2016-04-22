<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">

	$(document).ready(function() {
 		aside.setActive(3,1);
		searchList(listForm,1);
	});

	function searchList(listForm,page) {
		var param = {
			keyword		: 	listForm.keyword.value,
			type		: 	$("select[name=type]").val(),
			page		:	page
		};
		
		$.ajax({
			type:"POST",
			url:"/admin/contents/contents_list.go",
			dataType:"html",
			data:param,
			success:function(msg){
				$("#contents-list").html(msg);
			}
		});
		return false;
	}
	
	
	

	function popAnalysistAdd(userId) {
		pop.openPage("/admin/member/analyst_edit.go?userId="+userId);
	}
</script>

</head>
<body>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp"  %>

<section class="main-cover main-row">
	<section id="main">
		
		<%@ include file="/WEB-INF/views/admin/admin_menu.jsp"  %>

		<section id="contents">
			<header class="panel">
				 ■ 홈 > 컨텐츠 관리 > 리얼매거진
			</header>
		
			<div class="contents-block">
			
				<h1>리얼매거진</h1>
				
				<div class="contents-main">
				
					<form method="post" name="listForm" id="listForm" onsubmit="return searchList(this,1); return false;">
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">
									<input type="text" name="keyword" value="${keyword}" placeholder="제목/내용 검색" class="itext">
									<select name="type" >
										<option value="">전체</option>
										<option value="bloodsugar">혈당</option>
										<option value="pressure">혈압</option>
										<option value="cholesterol">콜레스테롤</option>
										<option value="activity">활동량</option>
										<option value="smoke">흡연</option>
										<option value="drink">음주</option>
										<option value="weight">체중</option>
									</select>
									
									<button type="submit" class="btn" >검색</button>
								</div>
								<div class="btn-tools"><button class="btn-blue" onclick="document.location.href='/admin/contents/contents_edit.go?mSeq=0';">건강매거진 등록</button></div>
<!-- 								<div class="btn-tools"><button class="btn" onclick="windowOpen('/m/notice/notice.go','FaceTalk',320,480,'yes','no');">모바일 공지사항</button></div> -->
							</div>
						</div>
						
						<div id="contents-list">
						</div>
					
					</form>
					
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>