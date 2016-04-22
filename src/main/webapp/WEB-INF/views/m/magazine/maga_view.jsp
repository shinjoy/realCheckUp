<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header5.jsp"  %>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<script type="text/javascript">
window.fbAsyncInit = function() {
    FB.init({
      appId      : '748163835248059',
      xfbml      : true,
      version    : 'v2.0'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/ko_KR/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));


</script>

<style type="text/css">
.maga td { padding:10px;} 
</style>

</head>

<body class="magazine">

<div class="magazine-top">
	<div class="magazine-main-img" style="background-image:url('/files/magazine/${top.fileName}');" >
		<!-- <div class="back"><a href="/m/maga_list.go"><img src="/images/bullet_arrow_left.png"></a></div> -->
		<div class='photo_main_up'>
			<p class="sub-title">${top.subTitle}</p>
			<p class="title">${top.title}</p>
		</div>
	</div>
</div>

<div class="magazine-share">
	<ul>
		<li class="info">혼자보기 아깝다면<br> 친구에게도 알려주세요.</li>
		<li>
			<a id="kakao-link-btn" href="javascript:;">
		      <img style="width:40px;" src="http://dn.api1.kage.kakao.co.kr/14/dn/btqa9B90G1b/GESkkYjKCwJdYOkLvIBKZ0/o.jpg" />
		    </a>
			
		</li>
	</ul>

</div>
 
<div class="subpage-list">

	<div class="magazine-img">
		<img src="/images/magazine_title.png" style="width:216px;">
	</div> 

	<ul class="page-list">
	<c:forEach items="${sub}" var="it">
		<li><span class="ellipsis title"><a href="/m/maga_subview.go?pSeq=${it.pSeq }">${it.pageTitle }</a></span> <span class="arrow"><img src="/images/arrow_list_right.png"></span></li>
	</c:forEach>
	</ul> 
	
	<p class="list-contents">
		<% pageContext.setAttribute("LF", "\n"); %>
		${fn:replace(top.contents, LF,'<br>')}
	</p>
</div>

<script>
jQuery(function($){
    Kakao.init('5b30961c9cfc164c4d43e81346136bce'); //99f3621a4b36d5f6be80af325eb6eb7f
 
    Kakao.Link.createTalkLinkButton({ //카카오톡
        container: '#kakao-link-btn',
        label: 'Recover',
       
        image: {
            src: 'http://mint.aimmed.com:8070/files/magazine/${top.fileName}',
            width: '600',
            height: '225'
        },
       
        webButton: {
            text: '[Recover]${top.title}' ,
            url: 'http://recover.bestist.net/m/maga_view.go?mSeq=${top.mSeq}' // 앱 설정의 웹 플랫폼에 등록한 도메인의 URL이어야 합니다.
        }
    });
});
</script>

</body>
</html>