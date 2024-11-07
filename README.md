# 편의점 (Convenience Store)

---
### Product

- 상품명과 가격의 정보를 제공

### Cart

- 사용자가 입력한 상품명과 수량의 정보를 제공

### Checkout

- 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
  - 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.

### Inventory

- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

### Promotion

- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- 1\+1 또는 2\+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### Membership

- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- 멤버십 할인의 최대 한도는 8,000원이다.

### Receipt

- 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- 영수증 항목은 아래와 같다.
  - 구매 상품 내역: 구매한 상품명, 수량, 가격
  - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
  - 금액 정보
    - 총구매액: 구매한 상품의 총 수량과 총 금액
    - 행사할인: 프로모션에 의해 할인된 금액
    - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    - 내실돈: 최종 결제 금액
- 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

### Controller

- 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.

### InputValidator

- 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`를 발생시키고, "\[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
  - `Exception`이 아닌 `IllegalArgumentException`, `IllegalStateException` 등과 같은 명확한 유형을 처리한다.

1. 상품 정보 출력: 편의점의 현재 재고 상황과 할인 정보가 표시됩니다.
2. 구매할 상품 정보 입력: 상품명과 수량을 입력받고, 프로모션 조건을 확인합니다.
3. 멤버십 적용 여부 입력: 멤버십 할인이 적용될지 확인 후 결제 금액에 반영합니다.
4. 영수증 출력: 총 구매 금액, 할인 금액, 최종 결제 금액 등을 포함한 영수증을 출력합니다.
5. 추가 구매 여부 입력: 사용자가 추가 구매 여부를 입력합니다. Yes 선택 시 1번 단계로 돌아갑니다.


## 개요 (Overview)
>이 프로젝트의 목적은 사용자가 **편의점 결제 시스템**을 통해 **할인 정책, 멤버십, 재고 관리** 등의 다양한 상황에서 결제를 원활하게 진행할 수 있도록 돕는 데 있습니다.  
**구현 목표**는 할인 혜택과 재고 관리를 반영하여, 최종 결제 금액을 정확히 산출하고 사용자에게 영수증을 제공하는 기능을 포함합니다.

## 개발 환경 (Environment)
- **JDK**: 21
- **빌드 도구**: Gradle
- **IDE 추천**: IntelliJ 또는 Eclipse (Java 21 호환)

## 설치 및 실행 방법 (Installation & Execution)
1. 저장소를 클론합니다:
    ```zsh
    git clone https://github.com/your-username/your-repository.git
    ```
2. 프로젝트 디렉토리로 이동합니다:
    ```zsh
    cd your-repository
    ```
3. Gradle을 사용하여 빌드하고 테스트를 실행합니다:
    ```zsh
    ./gradlew clean test   # Mac/Linux
    gradlew.bat clean test # Windows
    ```
    출력 결과: `BUILD SUCCESSFUL in 0s`     


4. 애플리케이션을 실행합니다:
    ```zsh
    ./gradlew run
    ```
## 프로젝트 구현 상세
이 프로젝트는 상품 목록과 행사 목록을 파일 입출력을 통해 불러와 활용하며, 이를 위해 다음과 같은 리소스 파일을 사용합니다.

### 파일 입출력
- 상품 목록: src/main/resources/products.md
- 행사 목록: src/main/resources/promotions.md
- 위 두 파일의 형식을 유지한다면, 내용은 자유롭게 수정할 수 있습니다.
- 상품과 행사 정보를 프로젝트에서 관리하기 위해 이 파일들을 통해 데이터를 로드합니다.

### 라이브러리
- 캠프 유틸 라이브러리: camp.nextstep.edu.missionutils 패키지에서 제공하는 API를 사용하여 필요한 기능을 구현합니다.
- 현재 날짜 및 시간: camp.nextstep.edu.missionutils.DateTimes의 now() 메서드를 사용하여 현재 날짜와 시간을 가져옵니다.
- 사용자 입력 처리: camp.nextstep.edu.missionutils.Console의 readLine() 메서드를 통해 사용자가 입력한 값을 읽어옵니다.
- 이 프로젝트는 camp.nextstep.edu.missionutils 라이브러리를 적극 활용하여 날짜와 시간, 사용자 입력 처리를 표준화된 방식으로 진행합니다.

## 진행 방식 및 요구사항 (Workflow & Requirements)
- **진행 방식**: 요구 사항에 따라 단계별로 기능을 구현하고, 각 기능 완료 후 커밋합니다.
- **요구사항**:
    - **커밋 방식**: [AngularJS Commit Convention](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)에 맞춰 커밋 메시지를 작성합니다.
    - **제출 방법**: 최종 구현 후 GitHub에 제출하고, 우아한테크코스 플랫폼에서 제출을 완료합니다.
- 새로운 방식으로 [비공개 저장소 과제 진행 가이드](https://docs.google.com/document/d/1cmg0VpPkuvdaetxwp4hnyyFC_G-1f2Gr8nIDYIWcKC8/edit?usp=sharing)를 참고하여 과제 제출물을 제출합니다.
- 상세 진행 방법 및 요구사항은 [상세 미션 정보](https://velog.io/@dan_d/4%EC%A3%BC%EC%B0%A8-%EB%AF%B8%EC%85%98-%EC%83%81%EC%84%B8-%EA%B0%80%EC%9D%B4%EB%93%9C)를 참고하세요.

## 기능 시나리오 및 도메인 개념 (Scenario & Domain Concepts)

---
## 기능 시나리오
1. **상품 정보 출력**: 편의점의 현재 재고 상황과 할인 정보가 표시됩니다.
2. **구매할 상품 정보 입력**: 상품명과 수량을 입력받고, 프로모션 조건을 확인합니다.
3. **멤버십 적용 여부 입력**: 멤버십 할인이 적용될지 확인 후 결제 금액에 반영합니다.
4. **영수증 출력**: 총 구매 금액, 할인 금액, 최종 결제 금액 등을 포함한 영수증을 출력합니다.
5. **추가 구매 여부 입력**: 사용자가 추가 구매 여부를 입력합니다. Yes 선택 시 1번 단계로 돌아갑니다.

## 예외사항
- **올바르지 않은 형식의 입력**: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
- **존재하지 않는 상품 입력**: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
- **재고 초과 구매 시도**: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
- **기타 잘못된 입력의 경우**: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`

### 주요 도메인 개념 객체
- **Product**: 각 상품의 기본 정보(이름, 가격, 재고 등)를 담은 클래스입니다.
- **Cart**: 사용자가 선택한 상품과 수량을 저장하고, 총 구매 금액을 계산하는 기능을 포함합니다.
- **Promotion**: 프로모션 조건과 할인을 처리하는 객체입니다.
- **Membership**: 멤버십 관련 할인 계산과 최대 한도 적용을 담당합니다.
- **Receipt**: 최종 결제 내역을 구성하고, 출력할 영수증을 관리합니다.

## 프로젝트 구조 (Project Structure)


## 참고 링크 (References)
- [상세 미션 정보](https://velog.io/@dan_d/4%EC%A3%BC%EC%B0%A8-%EB%AF%B8%EC%85%98-%EC%83%81%EC%84%B8-%EA%B0%80%EC%9D%B4%EB%93%9C)
- [프리코스 과제 제출 가이드](https://docs.google.com/document/d/1cmg0VpPkuvdaetxwp4hnyyFC_G-1f2Gr8nIDYIWcKC8/edit?usp=sharing)
- [우아한테크코스 편의점 GitHub 저장소](https://github.com/woowacourse-precourse/java-convenience-store-7)
- [Java Style Guide](https://github.com/woowacourse/woowacourse-docs/blob/main/styleguide/java)
- [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)
- [3주차 공통 피드백](https://docs.google.com/document/d/1MsfVKpgiDyhq6ArbTwsf9EEqDBD85vyt9Oj25i0zkEM/edit?usp=sharing)
- [비공개 저장소 과제 진행 가이드](https://docs.google.com/document/d/1cmg0VpPkuvdaetxwp4hnyyFC_G-1f2Gr8nIDYIWcKC8/edit?usp=sharing)
- [우아한테크코스 지원 플랫폼]()
- [JUnit5 User Guide](https://junit.org/junit5/docs/current/user-guide)
- [AssertJ User Guide](https://assertj.github.io/doc)
- [AssertJ Exception Assertions](https://www.baeldung.com/assertj-exception-assertion)
- [Guide to JUnit5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5)
