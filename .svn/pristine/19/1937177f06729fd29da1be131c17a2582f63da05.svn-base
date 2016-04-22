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
						<th>기본항목</th>	
						<td>	
							<table class="register" style="width:900px;">
									<colgroup>
										
										<col width="20%">
										<col width="*">
									</colgroup>
									<tr>
										<th>키</th>
										<td>
											${checkup.height}
										</td>
									</tr>	
									<tr>	
										<th>체중</th>
										<td>
											${weight.seq >0 ? weight.weight:''}
										</td>
									</tr>
									<tr>	
										<th>수축기/이완기(혈압)</th>
										<td>
											<c:if test="${pressure.seq>0}">
											${ pressure.splessure}/${pressure.dplessure}
											</c:if>
										</td>
									</tr>
									<tr>	
								<th>공복혈당</th>
								<td>
									${bloodsugar.seq>0 ? bloodsugar.bloodSugar:''}
								</td>
							</tr>
							</table>
						</td>		
					</tr>
					<tr>
						<th>지질성분 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>총콜레스테롤</th>
								<td>
									${cholesterol.seq>0 ? cholesterol.cholesterol:''}
								</td>
							</tr>
							<tr>
								<th>중성지방</th>
								<td>
									${cholesterol.seq>0 ? cholesterol.tg:''}
								</td>
							</tr>		
							<tr>
								<th>저밀도콜레스테롤</th>
								<td>
									${cholesterol.seq>0 ? cholesterol.ldl:''}
								</td>
							</tr>
							<tr>
								<th>고밀도콜레스테롤</th>
								<td>
									${cholesterol.seq>0 ? cholesterol.hdl:''}
								</td>
							</tr>
						</table>
						</td>
					</tr>	
					<tr>
						<th>간기능 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>지오티(AST)</th>
								<td>
									${checkup.ast}
								</td>
							</tr>
							<tr>
								<th>지피티(ALT)</th>
								<td>
									${checkup.alt}
								</td>
							</tr>		
							
							</table>
						</td>
					</tr>
					<tr>
						<th>일반혈액 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>적혈구수</th>
								<td>
									${checkup.redBlood}
								</td>
							</tr>
							<tr>
								<th>백혈구수</th>
								<td>
									${checkup.whiteBlood}
								</td>
							</tr>		
							<tr>
								<th>혈소판수</th>
								<td>
									${checkup.platelet}
								</td>
							</tr>	
							</table>
						</td>
					 </tr>
					 <tr>
						<th>갑상선 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>유리티록신(FreeT4)</th>
								<td>
									${checkup.freet4}
								</td>
							</tr>
							<tr>
								<th>갑상선자극호르몬(TSH)</th>
								<td>
									${checkup.tsh}
								</td>
							</tr>		
							
							</table>
						</td>
					 </tr>
					  <tr>
						<th>폐기능 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>1초간노력성호기량(FEV1)</th>
								<td>
									${checkup.fev1}
								</td>
							</tr>
							</table>
						</td>
					 </tr>	
					  <tr>
						<th>신장기능 검사</th>	
						<td>
							<table class="register" style="width:900px;">
							<colgroup>
								<col width="20%">
								<col width="*">
							</colgroup>
							<tr>	
								<th>크레아티닌(Cr)</th>
								<td>
									${checkup.cr}
								</td>
							</tr>
							<tr>	
								<th>혈액요소질소(BUN)</th>
								<td>
									${checkup.bun}
								</td>
							</tr>
							</table>
						</td>
					 </tr>			
				</table>			
					<br>
			
				</div>
					<div class="btn-tools" style="margin-top:0;">
								<button type="button" class="btn" onclick="document.location.href='/admin/user/user_checkup.go?userId=${checkup.userId}';"><span>목록으로</span></button>
					</div>
			
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>