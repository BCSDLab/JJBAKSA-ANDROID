# JJBACSA-ANDROID

## 🍽 쩝쩝박사 안드로이드 앱 🍽

## Technology Stacks

- Kotlin
- Coroutine Flow
- Hilt
- Retrofit 2

## App Architecture

Clean architecture + MVVM Design pattern

### Layer Declaration

- domain
- data
- app

## Code Convention

> 아래 명시된 컨벤션의 예외가 발견되면 이슈를 열어서 컨벤션 추가/수정 등 검토

### XML id

```
[view]_[where]_[purpose]
```

- [view] : View의 이름을 snack_case로 변환
  - TextView -> text_view_...
  - RecyclerView -> recycler_view_...
  - Toolbar -> toolbar_...
- [where] : View가 위치하 곳을 명시
  - Main screen text view -> text_view_main_...
- [purpose] : View의 사용 용도를 명시
  - 로그인->비밀번호 입력 EditText -> edit_text_login_password

### Shape drawables (button shape, dialog shape, ...)

```
shape_[rect|circ|oval...](_[colorcode]_stroke)(_[colorcode]_solid)(_etc variables).xml
```

- shape_rect_ff1234_stroke_f1f2f3_solid_radius_4.xml
- shape_circ_4321fe_solid.xml

### State list drawables (checked, unchecked, selected, unselected, ...)

```
sel_[where]_[what].xml
```

### Kotlin code convention

- ktlint
- detekt

### Architectual convention

- usecase가 단순 repository를 호출하는 경우에도 usecase 필수 적용
- usecase의 반환값을 `kotlin.Result`로 래핑
- API request/response <-> Repository Model 간 매핑(mapper 사용)

### Git convention

#### 공통 사항

[type]

- fix : 버그, 오류 해결 ```[fix] #10 - callback error```
- add : Feat 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 View나 Activity 생성 ```[add] #11 - LoginActivity``` ```[add] #12 - CircleImageView Library```
- feat : 새로운 기능 구현 ```[feat] #11 - google login```
- del : 쓸모없는 코드 삭제 [del] ```#12 - unnecessary import package```
- docs : README나 WIKI 등의 문서 개정 ```[docs] update readme```
- refactor : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩토링 시 [refactor] ```#15 - MVP architecture to MVVM```
- chore : 그 이외의 잡일/ 버전 코드 수정, 패키지 구조 변경, 파일 이동, 가독성이나 변수명, reformat 등 [chore] #20 - delete unnecessary import package ```[chore] #21 - reformat MainActivity```
- ui : xml 파일만 수정한 경우 ```[ui] #30 - use constraintlayout in activity_main.xml```
- mod : 그 외의 나머지를 수정한 경우 ```[mod] #31 - modify login process```
- test : 테스트 코드 추가

#### Issue

```
[[type]] 작업 내용
```

모든 작업 전 이슈 생성이 선행되어야 한다.

- [ui] Add login view
- [feat] Integrate login api

#### Branch

```
#[issue number]-[type] 작업-내용
```

- #1-ui-Add-login-View
- #2-feat-Integrate-login-api

#### Commit Message

```
#[issue number] [[type]] [commit message]
```

commit message는 현재 시제로, 최대한 간결하게 작성

- #1 [ui] Make password invisible
- #1 [ui] Add sns login button

#### Pull Request

```
#[issue number] [[type]] 작업 내용
```

branch 이름을 그대로 넘겨받아 작성

