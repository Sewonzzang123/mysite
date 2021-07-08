<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	/* guestbook application based on jQuery */
	/*
	과제 ex1: 리스트(list)
	- no 기준의 리스트를 부분적(3개씩) 가져와서 리스트 렌더링(append)
	- 버튼 이벤트 구현 -> 스크롤 이벤트 바꾼다.
	- no 기준으로 동적 쿼리를 레포지토리에 구현한다.
	- 렌더링 참고: /ch08/test/gb/ex1
	과제 ex2: 메세지 등록(add)
	- validation
	- message 기반 dialog plugin 사용법
	- form submit 막기
	- 데이터 하나를 렌더링(prepend)
	- 참고: /ch08/test/gb/ex2
	과제 ex3: 메세지 삭제(delete)
	- a tag 기본동작 막기
	- live event
	- form 기반 dialog plugin 사용법
	- 응답에 대해 해당 li 삭제
	- 비밀번호가 틀린 경우(삭제실패, no=0) 사용자한테 알려주는 UI
	- 삭제가 성공한 경우(no > 0), data-no=10 인 li element를 삭제
	- 참고: /ch08/test/gb/ex3
	 */
	var listEJS = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/ejs/list-template.ejs"
			});

	var listItemEJS = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/ejs/listitem-template.ejs"
			});

	var fetch = function() {
		var no = $("#list-guestbook li:last-child").data("no");
		if (undefined == no) {
			no = -1
		}
		$.ajax({
			url : "${pageContext.request.contextPath }/guestbook/api/list",
			dataType : "json", // 받을 때 포멧
			type : "get", // 요청 메서드
			data : "no=" + no,
			success : function(response) {
				if (response.data.length == 0) {
					flag = true;
					let html = "<li><strong>더 이상 글이 없습니다.</strong></li>";
					$("#list-guestbook").append(html);
				} else {
					let html = listEJS.render(response);
					$("#list-guestbook").append(html);
				}
			},
		});
	};
	var add = function(event) {
		event.preventDefault();
		vo = {};

		vo.name = $("#input-name").val();
		if (vo.name == "") {
			messageBox("이름", "이름을 입력하세요.",function(){$("#input-name").focus();});
			return;
		}
		vo.password = $("#input-password").val();
		if (vo.password == "") {
			messageBox("비밀번호", "비밀번호를 입력하세요.",function(){$("#input-password").focus();});
			return;
		}
		vo.message = $("#tx-content").val();
		if (vo.message == "") {
			messageBox("메시지", "메시지를 작성하세요.",function(){$("#tx-content").focus();});
			return;
		}

		$.ajax({
			url : "${pageContext.request.contextPath }/guestbook/api/add",
			dataType : "json", // 받을 때 포멧
			type : "post", // 요청 메서드
			// 2. JSON 포맷
			contentType : "application/json",
			data : JSON.stringify(vo), // post요청시 보내는 데이터
			success : function(response) {
				var vo = response.data;
				let html = listItemEJS.render(vo);

				$("#list-guestbook").prepend(html);

				$('#add-form')[0].reset();
				//많아지면 어떻게 할래?? ㅇㅅㅇ...
				//$("#input-name").val("");
				//$("#input-password").val("");
				//$("#tx-content").val("");
			},
		});
	};
	var messageBox = function(title, message, callback) {
		$("#dialog-message p").text(message);
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				확인 : function() {
					$(this).dialog("close");
				},
			},
			title : title,
			close : callback,
		});
	};

	var flag = false;
	
	
	$(function() {
		
		fetch();
		$(window).scroll(function() {
			var $window = $(this);

			var windowHeight = $window.height();
			var scrollTop = $window.scrollTop();
			var documentHeight = $(document).height();

			if (scrollTop + windowHeight + 10 > documentHeight) {
				if (flag == false) {
					fetch();
				}
			}
		});
		$("#add-form").submit(function() {
			add(event);
		});

		// live event : 존재하지 않는 element의 이벤트 핸들러를 미리 등록
		// delegation(위임) -> document
		$(document).on('click', '#list-guestbook li a', function(event) {
			event.preventDefault();
			let no = $(this).data("no");
			$("#hidden-no").val(no);
			deleteDialog.dialog('open');
		});

		// 삭제 다이얼로그 만들기
		const deleteDialog = $('#dialog-delete-form')
				.dialog(
						{
							autoOpen : false,
							width : 300,
							height : 220,
							modal : true,
							buttons : {
								"삭제" : function() {
									const no = $('#hidden-no').val();
									const password = $('#password-delete')
											.val();
									$
											.ajax({
												url : "${pageContext.request.contextPath }/guestbook/api/delete/"
														+ no,
												dataType : "json", // 받을 때 포멧
												type : "post", // 요청 메서드
												data : 'password=' + password,
												success : function(response) {
													if (response.result != 'success') {
														response
																.error(response.message);
														return;
													}
													if (response.data == -1) {
														$(".validateTips.error")
																.show();
														$("#password-delete")
																.val("")
																.focus();
														return;
													}
													$(
															"#list-guestbook li[data-no="
																	+ response.data
																	+ "]")
															.remove();
													deleteDialog
															.dialog('close');
												},
											});

								},
								"취소" : function() {
									$(this).dialog("close");
								}
							},
							close : function() {
								//1. password 비우기
								$("#password-delete").val("");
								//2. no 비우기
								$('#hidden-no').val("");
								//3. error message 숨기기
								$(".validateTips.error").hide();
							}

						});
		
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름" /> <input
						type="password" id="input-password" placeholder="비밀번호" />
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">

				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.
				</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all" /> <input
						type="hidden" id="hidden-no" value="" /> <input type="submit"
						tabindex="-1" style="position: absolute; top: -1000px" />
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
