<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header5.jsp"  %>


	<div style="color:#fff; font-size:20px; font-weight:bold; margin:10px 10px 10px 15px;">
		 <c:choose>

		 	<c:when test="${category == 1 }"> 
			 	<a href="javascript:history.back(-1);">
			 	<div style="float:left; margin-right:10px; margin-top:2px;">
			 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
			 	</div>
			 	</a> 혈압 
		 	</c:when>

		 	<c:when test="${category == 2 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 혈당
		 	</c:when>

		 	<c:when test="${category == 3 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 콜레스테롤/당화혈색소
		 	</c:when>
			
			
		 	<c:when test="${category == 4 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 복약관리
		 	</c:when>

		 	<c:when test="${category == 5 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 문진정보
		 	</c:when>
			
		 	<c:when test="${category == 6 }"> 
			 	<a href="javascript:history.back(-1);">
			 	<div style="float:left; margin-right:10px; margin-top:2px;">
			 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
			 	</div>
			 	</a> 데이터 백업
		 	</c:when>

		 	<c:when test="${category == 7 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 리헬스톡
		 	</c:when>

		 	<c:when test="${category == 8 }"> 
			 	<a href="javascript:history.back(-1);">
				 	<div style="float:left; margin-right:10px; margin-top:2px;">
				 		<img src="/images/bullet_arrow_left.png" style="width:30px;">
				 	</div>
			 	</a> 전화예약
		 	</c:when>
			
		 </c:choose>
	</div>
	
	<ul class="contents-faq">
		<c:forEach var="dl" items="${list}">
			<li>
				<dl class="qna-title-bar" style="" onclick="readContents(${dl.qnaSeq},this); ">
					<dt class="title-cell">
						${dl.title}
					</dt>
					<dd class="title-re" style="display:table-cell;">
						<img src="/images/bullet_arrow_right.png" class="arrow">
					</dd>
				</dl>
				<div class="faq-contents" style="background-color:#0f2334; border:1px solid #fff;"><p>${dl.contentsHtml}</p></div>
			</li>
		</c:forEach>	
	</ul>

<!-- 	<div class="faq-contents" style="background-color:#0f2334;"></div> -->



	

	<!-- 	
	<div class="temp-more">
		<div class="read-more">
			다음 페이지를 불러오고 있습니다.
		</div>
	</div>
	 -->
	${paging}
	
