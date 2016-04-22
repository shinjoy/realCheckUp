<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				
		<table class="list">
		<colgroup>
			<col width="10%">
			<col width="20%">
			
			<col width="10%">
			<col width="10%">
		</colgroup>
			<thead>
				<th>이름</th>
				<th>아이디</th>
				<th>나이</th>
				<th>가입일</th>
			</thead>
		
			<tbody class="rl">
			<c:choose>
				<c:when test="${list.size() > 0}">
					<c:forEach var="it" items="${list}">
					<tr>
						<td><a href="/admin/manager/manager_view.go?userId=${it.userId}">${it.userName}</a></td>
						<td style="text-align:left; padding-left:5px;"><a href="/admin/manager/manager_view.go?userId=${it.userId}">${it.userId}</a></td>
						<td>${it.userAge}</td>
																				
						
						<td>${fn:substring(it.regDate,0,16)}</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<td colspan="7" style="height:200px;">조회된 데이터가 없습니다.</td>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
			
		${paging}
		
	
	
