## mysite
```
mysite02(mvc=model2(Servlet+jsp)
mysite03(spring mvc,xml설정)
mysite04(spring mvc, java설정1)
mysite05(spring mvc, java설정2)
mysite06(spring boot)
```

jsp template > html5 설정 edit에서 taglib를 추가하기

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--  - 기본기능 ( core ) :  --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--  - 형식화(format) :--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--  - 함수처리(function) : --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
```