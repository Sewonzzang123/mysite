## interceptor


```
 : 반환값이 중요 
			false:중단하고 남은 인터셉터와 컨트롤러가 실행되지 않음 
			true:핸들러의 다음 체인이 실행
postHandle : 컨트롤러가 실행된 후에 호출
afterComletion : 뷰에서 최종결과가 생성하는 일을 포함한 모든 일이 완료 되었을 때 실행
```

### mybatis

```
 <![CDATA[ ]]> : 쿼리문 안에 <나>,기타 기호가 들어갈 경우 읽을 수 있게 함
 <choose> : 조건문
 	<when></when> : test=''
 	<otherwise></otherwise>
 </choose>
 #{}: ''가 붙음 
 ${}: ''가 없음 
 (parameterType이 단일타입인 경우 _parameter로 대체 할 수 있음)

 ```
 


