# moneystatus

### 지출기록장 - 나의 지출을 보다 더 효율적으로 관리해주는 어플
용돈, 생활비, 쇼핑, 각종 경조사까지 밑빠진 독처럼 흘러내리는 내 돈을 지키기 위해, 지출 관리를 효과적으로 할 수 있게 만든 어플입니다.
* 현재 플레이스토어 비공개 테스트 게시 완료 했으며, 테스터 모집 후 프로덕션 신청 예정입니다.

<br></br>

__주요기능__
---
1. 지출작성
    - 달력 라이브러리를 이용하여 날짜 입력, 여러가지 내용을 적은 후 작성
2. 지출검색
    - 검색어(메뉴, 카테고리 등)를 통한 지출을 검색, 검색한 뷰를 클릭하여 지출 상세로 넘어갈 수 있음
3. 올해지출
    - 그래프 버튼을 통해 올해의 지출을 확인할 수 있음, 원형 차트 라이브러리를 이용하여 화면을 구성
4. 지출상세
    - 메인 화면이나 지출 검색란에서 띄워지는 뷰를 클릭하면 띄워지는 페이지, 날짜와 여러가지 내용들을 수정하거나, 삭제 가능
5. 월별검색
    - 연월이 나와있는 버튼을 통해 원하는 달의 소비들을 한 눈에 확인 가능

<br></br>

__사용한 기술__

---

1. 다이얼로그 내에서 인터페이스를 구현하여 클릭이벤트 실행
- dialog 에서 인터페이스를 선언하여 setOnClickListener를 함수로 만들어 주었다. 이 인터페이스로 인해 mainActivity에서 dialog를 클릭했을 때의 이벤트를 처리 할 수 있었다.
- 굳이 그냥 setOnClickListener를 람다식으로 표현할 수 있었지만 인터페이스를 선언하여 만들어준 이유는 인터페이스 생성하는 것이 생소하여 어떠한 방식으로 이루어지는지 알고 싶었다. -> 새로운 방법으로 적용시켜보고 싶었다.
- 전체 코드를 보고 싶다면 [여기](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/DateDialog.kt)를 클릭

2. 리사이클러뷰, 생명주기를 이용하여 데이터를 동적으로 변경
3. Sqlite를 사용하여 사용자의 입력값을 데이터베이스에 저장
4. 데이터베이스에 저장되어 있는 내용들을 추가, 삭제, 변경 등 여러가지 용도로 사용(CRUD)
5. 여러가지 코틀린 라이브러리 사용(Calendar, pieChart)

<br></br>

__업데이트 사항__
---
1. 여러가지 디자인 패턴(MVC, MVP, MVVM)을 공부한 후 패턴 적용 예정
2. 어댑터 두 개를 하나의 어댑터에서 연결
3. 전역변수를 사용하지 않고 해결할 수 있는 방법 생각하기
