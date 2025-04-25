# /api 는 클래스 레벨보다는 yml로 따로 빼는 건 어떨까요?

---
## 문제점

* 개발할 때는 /api 경로로 접근하지만 배포 환경에서는 /prod 경로로 접근한다고 가정한다. -> @RequestMapping("/api")를 전부 /prod로 바꿔야 한다.
* 또한 URI.create(~~) 부분도 모두 수정해야 한다.

## 해결

* application.yml 파일에 경로를 미리 설정한다.
* URI 부분은 ServletUriComponentsBuilder의 fromCurrentRequestUri() 메서드를 사용하여 해결한다. -> 해당 메서드는 현재 요청을 보내는 경로를 받아오는 기능을 제공한다. 

# 에러 코드 재사용성

---
## 문제점(현재 에러 코드 구조)

~~~
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalErrorResponse> handleException(GlobalException e) {
    }
}

@Getter
public enum GlobalErrorCode {

}

@Getter
public class GlobalException extends RuntimeException {

    private final GlobalErrorCode errorCode;

    public GlobalException(GlobalErrorCode errorCode) {
    }
}
~~~

* 현재 이 3단계 구조로, 특정 관심사 패키지마다 exception 을 관리하고 있음
* 이렇게 할 경우, 패키지마다 계속해서 exception 을 생성 및 관리해야함 -> 코드 재활용이 안됌.

## 해결책

* common 패키지에, 공통적으로 사용되는 errorCode 를 interface화 시킴.
* 이후 interface(errorCode) 를 사용한 BaseException 클래스를 생성 -> 다른 패키지 exception 에서 BaseException 상속받아 사용할 수 있음
* 그렇기에 exception handler 도 하나로만 관리 가능

## 결론

* ErrorCode 인터페이스 하나 파서 관리
* BaseException 생성, 그리고 이 클래스를 이용한  BaseExceptionHandler 생성 -> 핸들러 하나만 사용 가능
* MemberException, ArticleException 등등 BaseException 을 상속받아 BaseExceptionHandler 에서 에러 처리 가능
* ErrorCode 추가될때마다 exception 을 추가할 필요가 없어짐.

---

# Offset Paging

--- 
## 장점
* 구현이 쉽다 -> JPA에서 바로 Pageable 객체로 페이징 가능
* 페이지 번호가 있는 프론트 구현 시, 잘 맞는다.

## 단점
* OFFSET 100000 같이 뒤 페이지로 갈수록 느려짐 → 인덱스가 있어도 느림
* 페이징 도중 데이터가 추가/삭제되면 순서가 뒤틀릴 수 있음 (같은 댓글이 여러 번 보이거나 사라짐)

