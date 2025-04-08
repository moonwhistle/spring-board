# /api 는 클래스 레벨보다는 yml로 따로 빼는 건 어떨까요?

---
## 문제점

* 개발할 때는 /api 경로로 접근하지만 배포 환경에서는 /prod 경로로 접근한다고 가정한다. -> @RequestMapping("/api")를 전부 /prod로 바꿔야 한다.
* 또한 URI.create(~~) 부분도 모두 수정해야 한다.

## 해결

* application.yml 파일에 경로를 미리 설정한다.
* URI 부분은 ServletUriComponentsBuilder의 fromCurrentRequestUri() 메서드를 사용하여 해결한다. -> 해당 메서드는 현재 요청을 보내는 경로를 받아오는 기능을 제공한다. 
