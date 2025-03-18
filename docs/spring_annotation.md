## **Java Bean Validation: `@NotNull`, `@NotEmpty`, `@NotBlank` 차이점**  

---

### 1. @NotNull

* null 값만 허용하지 않음
* 빈 값("")과 공백(" ")은 허용됨

### 2. @NotEmpty 

* null 값과 빈 값("") 모두 허용되지 않음
* 공백(" ")은 허용됨

### 3. @NotBlank 

* null 값, 빈 값(""), 공백(" ") 모두 허용되지 않음

**회원 가입 시, @NotBlank 어노테이션 활용하는 것이 좋을 것 같음**


## **Lombok의 생성자 어노테이션 비교 (`@AllArgsConstructor`, `@RequiredArgsConstructor`, `@NoArgsConstructor`)**

---

### 1. @AllArgsConstructor 

* 클래스의 모든 필드를 초기화하는 생성자를 자동 생성
* @NonNull 필드가 null이면 NullPointerException 발생

### 2. @RequiredArgsConstructor 

* final 필드 및 @NonNull 필드만 포함하는 생성자를 생성
* 초기화된 final 필드는 생성자에서 제외됨

### 3. @NoArgsConstructor 

* 매개변수가 없는 기본 생성자를 생성
* final 필드가 있을 경우 force = true 옵션 필요

### JPA 에서 기본 생성자가 필요한 이유(protected 쓰는 이유까지)

1. JPA는 데이터베이스에서 조회한 결과를 기반으로 엔티티 객체를 생성해야 합니다.
2. Reflection을 이용하여 객체를 만들기 때문에, 기본 생성자가 반드시 필요합니다.
3. 외부에서 기본 생성자를 직접 호출하는 것을 막기 위해 protected 사용합니다.
3. 만약 기본 생성자가 없으면 InstantiationException 오류 발생!
