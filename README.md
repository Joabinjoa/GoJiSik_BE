# GoJiSik_BE

## 커밋 규칙

### 브랜치 전략

1. develop 브랜치를 생성하여 개발을 진행한다.

2. 기본적으로 기능 구현이 필요할 경우 develop 브랜치에서 feature 브랜치를 하나 생성해서 기능 구현을 진행한다. (예: feature/login)

3. 기능 구현이 완료된 feature 브랜치는 검토 후 develop 브랜치에 merge한다.

4. 최종 기능 개발 및 테스트가 완료된 이후 master 브랜치에 merge한다.

5. 배포한다.

### 커밋 메세지 작성법

|메세지명|작업 유형|예시|
|:--:|:--:|:--|
|Feat|새 기능 구현|[Feat] 락커 회원 목록 검색 기능 추가|
|Fix|버그 수정|[Fix] 상점 목록의 에러처리 예외케이스 대응|
|Docs|문서 관련 작업|[Docs] 데코레이터 옵션에 대한 문서 추가|
|Refactor|리팩토링|[Refactor] createStore의 함수를 작은 함수로 분리|
|Test|테스트 관련 작업|[Test] 상점 생성 테스트 추가|
|Chore|기타 작업|[Chore] 프로덕션 빌드시 소스맵 생성하도록 변경|
