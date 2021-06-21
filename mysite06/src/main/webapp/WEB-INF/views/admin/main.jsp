<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/admin/main.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/admin/include/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-form">
					<form:form modelAttribute="siteVo" method="post"
						action="${pageContext.request.contextPath }/admin/main/update"
						enctype="multipart/form-data">
						<label class="block-label" for="title">사이트 타이틀</label>
						<form:input path="title" />
						<form:errors path="title" />
						<label class="block-label" for="welcomeMessage">환영 메세지</label>
						<form:input path="welcome" />
						<form:errors path="welcome" />
						<label class="block-label">프로필 이미지</label>
						<img id="profile"
							src="${pageContext.request.contextPath }${siteVo.profile }<c:if test='${empty siteVo.profile  }'>${profileimage }</c:if>">
						<input type="file" id="file" name="profileimage" value="" />

						<label class="block-label">사이트 설명</label>
						<form:textarea path="description" />
						<form:errors path="description" />
						<input type="submit" value="변경" />
					</form:form>



				</div>
			</div>
			<c:import url="/WEB-INF/views/admin/include/navigation.jsp">
				<c:param name="menu" value="main" />
			</c:import>
		</div>
	</div>
</body>
</html>