<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js" ></script>
<script>
//	# : id
// $가있으면 jquery...
	$(function(){
		var btn =$('#btn-check');
		btn.click(function(){
			var email = $('#email').val();
			if(email==""){
				alert('이메일을 작성해 주세요');
				$('#email').focus();
				return;
			}
			$.ajax({
					url:"/mysite03/user/api/checkemail?email="+email,
					type:"get",
					dataType:"json",
					error:function(xhr, status, e){
						//xml http request
						//status
						//error
						console.error(status,e);
						return;
					},
					success:function(response){	
						if(response.result != "success"){ //통신 실패
							console.error(response.message);
							return;
						}				
						if(response.data){
							alert('존재하는 이메일 입니다. 다른 이메일을 사용해 주세요.');
							$('#email').val("");
							$('#email').focus();
							return;
						}
						
						$('#btn-check').hide();
						$('#img-check').show();
						
					}
			});
		});
	});
	

</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<p style="color:red; text-align:left; padding-left:0">
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name") }'>
							<spring:message code='${errors.getFieldError("name").codes[0] }' /> 
						</c:if>
					</spring:hasBindErrors>
					</p>
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input type="button" id="btn-check" value="id 중복체크">
					<img id="img-check"src="${pageContext.request.contextPath }/assets/images/check.png" style="width:25px; vertical-align:bottom; display:none"/>
						<p style="color:red; text-align:left; padding-left:0">
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("email") }'>
							 <spring:message code='${errors.getFieldError("email").codes[0] }' />
						</c:if>
					</spring:hasBindErrors>
					</p>
					<label class="block-label">패스워드</label>
					<%-- 언어별로 바꿀 수 있게 설정 가능 spring-servlet.xml에서 설정하고 src/main/resources/messages/... 작성 --%>
					<%-- <label class="block-label"><spring:message code="user.join.label.password" /></label>--%>
					<input name="password" type="password" value="">
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("password") }'>
							<spring:message code='${errors.getFieldError("password").codes[0] }' />
						</c:if>
					</spring:hasBindErrors>
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>