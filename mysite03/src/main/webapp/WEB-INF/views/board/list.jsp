<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath }/board/search"
					method="get">
					<input type="text" id="kwd" name="kwd" value="${kwd }"> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align: left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${pageInfo.totalCount}" />
					<c:forEach begin="0" end="5" items="${list }" var="board"
						varStatus="boardNo">
						<tr>
							<td>${count-boardNo.index-((pageInfo.currentPage-1)*5) }</td>
							<td
								style="text-align:left <c:if test="${board.depth!=0 && board.parentNo != -1}">; padding-left: ${board.depth*20}px;</c:if>">
								<c:if test="${board.parentNo==-1 }">
									<div style="color: red">[원글이 삭제된 답글]</div>
								</c:if> <c:if test="${board.depth!=0 && board.parentNo != -1}">
									<img
										src="${pageContext.request.contextPath }/assets/images/reply.png" />
								</c:if><a
								href="${pageContext.request.contextPath }/board/view/${board.no}/${pageInfo.currentPage}">${board.title }</a>
							</td>
							<td>${board.userName }</td>
							<td>${board.hit }</td>
							<td>${board.regDate }</td>
							<td><c:if
									test="${board.userName==sessionScope.authUser.name }">
									<a
										href="${pageContext.request.contextPath }/board/delete/${board.no}/${pageInfo.currentPage}"
										class="del"
										style='background-image:url("${pageContext.request.contextPath}/assets/images/recycle.png")'>삭제</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<c:if test="${empty requestScope.kwd }">
					<div class="pager">
						<ul>
							<c:if test="${pageInfo.firstPageNo>1 && pageInfo.lastPageNo>5}">
								<li><a
									href="${pageContext.request.contextPath }/board/${pageInfo.firstPageNo-5}">◀◀</a></li>
							</c:if>
							<c:if test="${pageInfo.currentPage>1}">
								<li><a
									href="${pageContext.request.contextPath }/board/${pageInfo.prevPageNo}">◀</a></li>
							</c:if>
							<c:forEach begin="${pageInfo.firstPageNo }"
								end="${pageInfo.lastPageNo }" var="page">
								<c:if test="${page==pageInfo.currentPage}">
									<li class="selected">${page }</li>
								</c:if>
								<c:if
									test="${page<=pageInfo.totalPage && page!=pageInfo.currentPage}">
									<li><a
										href="${pageContext.request.contextPath }/board/${page }">${page }</a></li>
								</c:if>
								<c:if
									test="${page>pageInfo.totalPage && pageInfo.totalPage<pageInfo.lastPageNo }">${page } </c:if>
							</c:forEach>
							<c:if test="${pageInfo.currentPage<pageInfo.totalPage}">
								<li><a
									href="${pageContext.request.contextPath }/board/${pageInfo.nextPageNo}">▶</a></li>
							</c:if>
							<c:if test="${pageInfo.lastPageNo<pageInfo.totalPage}">
								<li><a
									href="${pageContext.request.contextPath }/board/${pageInfo.lastPageNo+1}">▶▶</a></li>
							</c:if>
						</ul>
					</div>
				</c:if>
				<c:if test="${not empty requestScope.kwd }">
					<div class="pager">
						<ul>
							<c:if test="${pageInfo.firstPageNo>1 && pageInfo.lastPageNo>5}">
								<li><a
									href="${pageContext.request.contextPath }/board/search/${pageInfo.firstPageNo-5}?kwd=${kwd }">◀◀</a></li>
							</c:if>
							<c:if test="${pageInfo.currentPage>1}">
								<li><a
									href="${pageContext.request.contextPath }/board/search/${pageInfo.prevPageNo}?kwd=${kwd }">◀</a></li>
							</c:if>
							<c:forEach begin="${pageInfo.firstPageNo }"
								end="${pageInfo.lastPageNo }" var="page">
								<c:if test="${page==pageInfo.currentPage}">
									<li class="selected">${page }</li>
								</c:if>
								<c:if
									test="${page<=pageInfo.totalPage && page!=pageInfo.currentPage}">
									<li><a
										href="${pageContext.request.contextPath }/board/search/${page }?kwd=${kwd }">${page }</a></li>
								</c:if>
								<c:if
									test="${page>pageInfo.totalPage && pageInfo.totalPage<pageInfo.lastPageNo }">${page } </c:if>
							</c:forEach>
							<c:if test="${pageInfo.currentPage<pageInfo.totalPage}">
								<li><a
									href="${pageContext.request.contextPath }/board/search/${pageInfo.nextPageNo}?kwd=${kwd }">▶</a></li>
							</c:if>
							<c:if test="${pageInfo.lastPageNo<pageInfo.totalPage}">
								<li><a
									href="${pageContext.request.contextPath }/board/search/${pageInfo.lastPageNo+1}?kwd=${kwd }">▶▶</a></li>
							</c:if>
						</ul>
					</div>
				</c:if>

				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/write"
							id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>