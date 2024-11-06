# 편의점 (Convenience Store)

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
