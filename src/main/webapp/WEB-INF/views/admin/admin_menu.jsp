<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<aside id="nav">

			<ul class="m1">
				<li class="menu" id="menu1">
					<a href="#" onclick="aside.togleSub(1);">
						<dl>
							<dt class="bullet">
								<div class="bullet-image"><img src="/images/user.png" style="height:20px;"></div>
							</dt>
							<dd class="menu-name">
								<span>회원 관리</span>
							</dd>
							<dd class="arrow">
								<img src="/images/arrow_bottom.png">
							</dd>
						</dl>
					</a>
					<ul class="m2" id="u1">
						<li id="menu-sub11">
							<a href="/admin/user/user.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>일반 회원</span>
									</dd>
								</dl>
							</a>
						</li>
		
						<li id="menu-sub12">
							<a href="/admin/manager/manager.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>관리자</span>
									</dd>
								</dl>
							</a>
						</li>
					</ul>
				</li>		
				</ul>	
		

				
				
				<li class="menu" id="menu2">
					<a href="#" onclick="aside.togleSub(2);">
						<dl>
							<dt class="bullet">
								<div class="bullet-image"><img src="/images/icon_board.png"></div>
							</dt>
							<dd class="menu-name">
								<span>게시물 관리</span>
							</dd>
							<dd class="arrow">
								<img src="/images/arrow_bottom.png">
							</dd>
						</dl>
					</a>
					 <ul class="m2" id="u2">
						<li id="menu-sub21">
							<a href="/admin/board/notice/notice.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>공지사항</span>
									</dd>
								</dl>
							</a>
						</li>
						<li id="menu-sub22">
							<a href="/admin/board/qna/qna.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>QNA </span>
									</dd>
								</dl>
							</a>
						</li>
						<li id="menu-sub23">
							<a href="/admin/board/app/app.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>앱버전 관리</span>
									</dd>
								</dl>
							</a>
						</li>
					</ul>
					
				</li>
				
				<li class="menu" id="menu3">
					<a href="#" onclick="aside.togleSub(3);">
						<dl>
							<dt class="bullet">
								<div class="bullet-image"><img src="/images/point.png"></div>
							</dt>
							<dd class="menu-name">
								<span>컨텐츠 관리</span>
							</dd>
							<dd class="arrow">
								<img src="/images/arrow_bottom.png">
							</dd>
						</dl>
					</a>
					<ul class="m2" id="u3">
						
						<li id="menu-sub31">
							<a href="/admin/contents/contents.go">
								<dl>
									<dt class="bullet">
										<div class="bullet-image"><img src="/images/arrow_right.png"></div>
									</dt>
									<dd class="menu-name">
										<span>컨텐츠 관리</span>
									</dd>
								</dl>
							</a>
						</li>
					</ul>
				</li>
			</ul>
		
		</aside>
