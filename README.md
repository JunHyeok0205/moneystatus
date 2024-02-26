# moneystatus

### 지출기록장 - 나의 지출을 보다 더 효율적으로 관리해주는 어플
용돈, 생활비, 쇼핑, 각종 경조사까지 밑빠진 독처럼 흘러내리는 내 돈을 지키기 위해, 지출 관리를 효과적으로 할 수 있게 만든 어플입니다.
* 현재 플레이스토어 비공개 테스트 게시 완료 했으며, 테스터 모집 후 프로덕션 신청 예정입니다.

<br><br>

__Skills__
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
    - 연월이 나와있는 버튼을 통해 해당하는 달의 소비들을 한 눈에 확인 가능

<br><br>

__Description__
---

___1. 다이얼로그 내에서 인터페이스를 구현하여 클릭이벤트 실행___
- dialog 에서 인터페이스를 선언하여 setOnClickListener를 함수로 만들어 주었습니다. 이 인터페이스로 인해 mainActivity에서 dialog를 클릭했을 때의 이벤트를 처리 할 수 있었습니다.
- 굳이 그냥 setOnClickListener를 람다식으로 표현할 수 있었지만 인터페이스를 선언하여 만들어준 이유는 인터페이스 생성하는 것이 생소하여 어떠한 방식으로 이루어지는지 알고 싶어 새로운 방법으로 시도하였습니다.
- DateDialog ClickListener 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/DateDialog.kt)을 클릭하세요.

```
인터페이스를 선언하는 방식부터 어떻게 동작하는지 이해하는 것에 어려움을 겪었습니다.
```
```
dialog에서 구현한 인터페이스는 mainActivity에서 override를 꼭 해주어야하는 것을 알게되었습니다.
(default가 있는 method는 override를 안해주어도 됩니다.)
```
```
복수의 인터페이스 내 method가 동일한 이름으로 존재할 시,
super keyword를 사용하여 원하는 인터페이스의 method를 사용할 수 있음을 알게되었습니다.
```


<br>


___2. 리사이클러뷰, 생명주기를 이용하여 데이터를 동적으로 변경___
- data class를 선언하여 recyclerView item을 저장하고 화면 UI에 띄워주었습니다.
- recyclerView를 띄울땐, database에 저장되어 있는 정보들을 이용하여 띄워주었습니다.
- 생명주기의 한 종류인 onResume()을 통하여 View를 띄워, 이벤트가 발생할 때마다 새로고침이 되게 해주었습니다.
- mainActivity의 recyclerView 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/MainActivity.kt)을 클릭하세요.
- recyclerView에 사용된 Adapter 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/ItemAdapter.kt)을 클릭하세요.

   ```
   Adapter에서 onCreateViewHolder는 뷰 홀더를 생성하는 역할을 합니다.
   (ListView와 달리 View를 재사용하기 때문에 성능 저하가 줄어듭니다.)
   ```
   ```
   Adapter에서 onBindViewHolder는 뷰를 재사용할 때 사용하며 포지션 값에 따라 데이터를 뿌려주고
   저는 이곳에서 recyclerView Item 클릭이벤트를 실행했습니다.
   ```
   ```
   itemView를 클릭하였을 때 클릭한 itemView의 data를 intentExtra를 사용하여 새로 열리는 액티비티에 데이터를 전달해주었습니다.
   ```
   ```
   sortByDescending 함수를 사용하여 itemView의 data를 가격 기준의 내림차순으로 정렬하였습니다.
   ```


<br>


___3. Sqlite를 사용하여 사용자의 입력 값을 데이터베이스에 저장___
- editText에 입력한 값을 전부 database에 저장하였습니다.
- editText가 한 곳이라도 비어있는 경우, Toast Message를 출력하며 저장되지 않게 만들었습니다.
- Sqlite Helper에 선언해준 Insert문을 사용하여 data를 삽입하였습니다.
- 사용자의 입력 값을 저장하는 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/MoneySpendingWrite.kt)을 클릭하세요.
- Insert문:
```kotlin
fun insertData(inputToday: String,
                   inputYearandmonth: String,
                   inputDate: String,
                   inputMenu: String,
                   inputCategory: String,
                   inputPrice: Int,
                   inputImpression: String) {
        val db = writableDatabase
        db.execSQL(
            "insert into statusDB (today, yearandmonth, date, menu, category, price, impression) values (?,?,?,?,?,?,?)",
            arrayOf(inputToday, inputYearandmonth, inputDate, inputMenu, inputCategory, inputPrice, inputImpression)
        )
        db.close()
    }
```
    사용자의 입력 값을 insert문의 매개변수로 받아 database에 저장하였습니다.


<br>


___4. 데이터베이스에 저장되어 있는 내용들을 추가, 삭제, 변경 등 여러가지 용도로 사용(CRUD)___
- DB Helper class를 만들어준 뒤에, 데이터베이스를 create해주고, 용도에 맞게 select, insert, delete, update 등을 해주었습니다.
- 원하는 부분의 data를 뽑아내기 위해 where절, like절 등을 사용하였습니다.
- SqliteHelper 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/DBHelper.kt)을 확인하세요.

```
쿼리문에서도 사용하는 목적에 따라 변수를 사용해 적재적소에 원하는 데이터를 select할 수 있다는 것을 알게 되었습니다.
```
```
delete, update 구문에서도 인자로 가지는 변수를 통해 원하는 데이터를 삭제, 수정할 수 있다는 것을 알게 되었습니다.
```
```
where절, like절 등 쿼리문이 가지고 있는 여러가지 조건식들에 대해 알게되었고, 상황에 맞게 사용하는 방법에 대해 알게되었습니다.
```


<br>


___5. 여러가지 코틀린 라이브러리 사용(Calendar, pieChart)___
- 여러가지 많이 존재하는 library등을 사용하고 싶어 찾아보았습니다.
- 달력, 올해 지출 등 사용해볼만한 곳에 library를 적용하였습니다.
- pieChart library 코드: [이곳](https://github.com/JunHyeok0205/moneystatus/blob/master/app/src/main/java/com/JunHyeok0205/portfolio/moneystatus/GraphActivity.kt)을 클릭하세요.
- Calendar library 코드: 
```kotlin
binding.calenderImage.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // 날짜를 보여주는 다이얼로그를 생성, 그곳에서 삽입되는 데이터들을 view, year, month, dayOfMonth의 순서대로 dateSetListenter에 적용
                    dateString = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    binding.calenderText.setTextSize(Dimension.SP, 17F) // 달력 선택하고 날짜가 텍스트로 나올 때 크기 조정
                    binding.calenderText.setTextColor(Color.parseColor("#000000")) // 색 변경(검은색)
                    binding.calenderText.text = dateString // UI파일 내에 텍스트를 해당하는 날짜로 변경
                    date = dayOfMonth.toString()
                    yearandmonth = "${year}년 ${month + 1}월"
                    dateOfDay = "${dayOfMonth}일"
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show() // Calendar함수에서 필요한 부분을 가져와 show
        }

```


<br><br>


__Todo List__
---
1. 여러가지 디자인 패턴(MVC, MVP, MVVM)을 공부한 후 패턴 적용 예정
2. 동일 기능의 어댑터 두 개를 하나의 어댑터에서 연결
3. 전역변수를 지양하는 방법 고찰


<br><br>


__Review__
---
1. 혼자 공부하면서 모르는 부분이 너무 많아 하나하나 찾아보는 것이 정말 힘들었다. 하지만 반대로 __혼자서 고생하며__ 찾아 적용하였을 때, 뿌듯함과 성취감도 느껴지고 누군가 알려주는 것보다 __더 기억에 선명한 것 같다.__
2. 개발적인 부분에서 __성장한다는 것의 의미를__ 잘 알지 못했는데, 천천히 하다보니 __아주 작은 변화라도 실력이 향상되는 것이__ 느껴졌다.
3. 앱 개발에는 코딩만 있는 것이 아니라 __기획하는 단계도__ 몹시 중요하고 그에 따라 설계를 해야한다는 것, 플레이스토어에 __배포하는 것까지__ 모두 중요한 부분임을 깨달았다.
4. 위 3)의 이유로, 이 앱을 만들때 혼자서 기획을 했었는데 준비가 미흡했다는 개인적인 판단이 있다. 다음번 앱을 출시할 때는 __팀원들과 협업을 통하여 서로의 기획을 조율하는 과정을__ 거쳐 앱을 만들어보고 싶다.
5. 앱을 만드는 데에는 내부 기능도 물론 중요하지만, __디자인도__ 그에 못지 않게 __중요하다는 생각이 들었다.__ 앱 기능이 아무리 좋아도 (디자인적으로)손이 가야 소비자들의 선택을 받지 않을까?
