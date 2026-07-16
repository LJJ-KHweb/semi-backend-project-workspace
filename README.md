[README (6).md](https://github.com/user-attachments/files/30074311/README.6.md)
<div align="center">

#  EVRE

### 전기차 충전 인프라 & 친환경 주행 마일리지 통합 플랫폼

**E**lectric **V**ehicle + **RE**newable

지도 기반 충전소 조회부터, 실주행 데이터로 계산한 탄소 절감량을 마일리지로 보상하는<br/>
**데이터 수집 → 탄소 절감 연산 → 리워드 → 소비 → 랭킹**의 완결된 순환 구조를 구현한 웹 서비스

<br/>

![Java](https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-3.0-000000?style=flat-square)
![Oracle](https://img.shields.io/badge/Oracle-21-F80000?style=flat-square&logo=oracle&logoColor=white)
![React](https://img.shields.io/badge/React-19-61DAFB?style=flat-square&logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-8-646CFF?style=flat-square&logo=vite&logoColor=white)
![Kakao Map](https://img.shields.io/badge/Kakao_Map-API-FFCD00?style=flat-square&logo=kakao&logoColor=black)

</div>

<br/>

##  프로젝트 개요

| 항목 | 내용 |
|:---|:---|
| **프로젝트명** | EVRE (Electric Vehicle + RE) |
| **개발 기간** | 2026.06.04 ~ 2026.07.14 (약 5주) |
| **팀 구성** | 3인 (김선겸 · 심영도 · 이재준) |
| **Backend** | https://github.com/LJJ-KHweb/semi-backend-project-workspace |
| **Frontend** | https://github.com/LJJ-KHweb/semi-frontend-project-workspace |

### 기획 배경

전기차 보급이 확대되면서 두 가지 문제가 동시에 나타났습니다.

- **충전 인프라 정보의 파편화** — 충전소 위치와 충전기 가용 여부를 한눈에 확인하기 어렵습니다.
- **친환경 운전에 대한 동기 부여 부재** — 전기차 주행이 실제로 얼마나 탄소를 줄이는지 운전자가 체감하기 어렵습니다.

EVRE는 이 둘을 하나의 웹 서비스로 묶었습니다. 지도 기반 충전소 조회로 인프라 정보를 제공하고, 라즈베리파이(Raspberry Pi) 차량 단말에서 수집한 실주행 데이터를 바탕으로 탄소 절감량을 계산해 마일리지로 환산·보상합니다.

>  단순 게시판 CRUD에 머무르지 않고, **데이터 수집 → 탄소 절감량 연산 → 마일리지 리워드**라는 하나의 완결된 도메인 흐름을 구현한 것이 핵심 차별점입니다.


---
<br/>

##  주요 기능

###  사용자 인증

* JWT 기반 회원가입 및 로그인
* Access Token / Refresh Token 기반 인증
* 사용자 및 관리자 권한 관리

###  충전소 조회

* 지도 API를 활용한 충전소 조회
* 충전소 위치 및 상세 정보 제공

###  친환경 마일리지

* 차량에서 수집한 주행거리 및 전력 소모량 데이터를 서버로 전송
* 탄소 절감량을 계산하여 친환경 마일리지 자동 적립
* 마일리지 적립 및 사용 내역 조회

###  마일리지 상점

* 적립된 마일리지로 상품 구매
* 상품 구매 시 마일리지 자동 차감
* 구매 내역 및 잔여 마일리지 조회

---

<br/>

##  시스템 아키텍처

<img width="1536" height="1024" alt="아키택쳐" src="https://github.com/user-attachments/assets/74d9341f-ff26-408c-9e77-075fc2aa9950" />

---

<br/>

##  시스템 설계 주안점

* **세션 대신 JWT** — 서버 확장성을 위해 상태를 서버에 저장하지 않는 Stateless 인증을 택하고, Access/Refresh Token으로 보안과 사용성을 함께 확보했습니다.
* **하드웨어 데이터의 서비스화** — 단순 조회·게시판에 그치지 않고, Raspberry Pi가 수집한 실주행 데이터를 탄소 절감량이라는 서비스 가치로 연결하는 것을 설계의 중심에 두었습니다.
* **일관된 응답·예외 규격** — 도메인이 늘어도 프론트엔드가 동일한 방식으로 처리할 수 있도록, 공통 응답 형식과 예외 처리 구조를 먼저 정하고 전 도메인에 적용했습니다.
* **계층 분리** — Controller-Service-Mapper로 책임을 나눠, 각 계층을 독립적으로 수정·테스트할 수 있는 구조를 지향했습니다.
* **협업 기반 형상 관리** — GitHub Issues와 PR 템플릿으로 3인의 작업을 충돌 없이 통합할 수 있는 협업 체계를 갖췄습니다.
---

<br/>

>  ERD와 테이블별 설계 의도 자세히 보기 → **[ERD 설계 문서](docs/ERD.md)**

<br/>

##  팀 구성 및 역할 분담

| 팀원 | 주요 담당 |
|:---|:---|
| **심영도** | 충전소(Station) · 충전기(Charger), Kakao Map 연동, 이용방법, 저장소 관리 |
| **김선겸** | 상품(Product), 문의(Require/Answer), 관리자 대시보드, 예외 처리 체계 |
| **이재준** | 공지사항, 라즈베리파이(Rasp), 마이페이지, 랭킹, 파일 업로드, 상점(Shop) |
| **공통** | 회원/인증(JWT), 게시판(Board) |

<br/>


##  핵심 구현

### JWT 인증 및 자동 재발급
- `JwtFilter`를 `UsernamePasswordAuthenticationFilter` 앞에 배치, 세션 정책 `STATELESS`
- 클라이언트 Axios 인터셉터가 토큰을 자동 첨부하고, `401 + 토큰 만료` 시 `/auth/refresh`로 재발급 후 원 요청 재시도
- `_retry` 플래그로 **무한 재발급 루프 차단**

### 탄소 절감량 계산
```
탄소 절감량 = 주행거리 × (내연기관 배출계수 − 전력 배출계수 ÷ 전비)
```
"같은 거리를 내연기관차로 달렸다면 나왔을 배출량"에서 "전기 생산 과정에서 실제로 발생한 배출량"을 뺀 순 절감분이며, 이 값이 랭킹과 마일리지의 근거가 됩니다.

### 상품 구매 트랜잭션
`@Transactional` 하에서 **검증 → 재고 차감 → 마일리지 이력 생성**을 원자적으로 처리합니다. 어느 단계에서든 예외가 발생하면 롤백되어, 재고만 줄고 마일리지는 안 빠지는 부분 실패가 발생하지 않습니다.

### 계층적 예외 처리 체계
약 60여 개의 커스텀 예외를 도메인별로 분류하고 `@RestControllerAdvice` 기반 `GlobalExceptionHandler`에서 일괄 처리합니다. Spring Security(인증·인가)와 연계해 인증/인가/Validation/비즈니스 예외까지 **하나의 표준화된 응답 형식(`ApiResponse`)**으로 통일했습니다.

### 공통 페이징
`PageInfo(page, size)` 하나로 전체 게시물 수·시작/끝 페이지·오프셋 계산을 캡슐화하여 모든 도메인이 재사용합니다.

<br/>

##  협업 방식

- **브랜치** — master 중심 + 기능별 작업
- **이슈 트래킹** — GitHub Issues (기능 단위 번호 부여)
- **코드 리뷰** — PR 템플릿 강제 적용

PR 템플릿에 작업 내용, 해결 방법, 테스트 체크리스트, 리뷰 포인트, Postman 응답을 필수로 넣어 리뷰 품질을 팀 차원에서 담보했습니다. 특히 **"이해가 안 됐는데 그냥 쓴 부분도 물어보라"**는 문항은 학습 목적 프로젝트에서 모르는 것을 숨기지 않게 만드는 장치로 작동했습니다.

>  3인이 5주간 **17개 테이블 · 60여 개 커스텀 예외 · 40여 개 API · 400여 커밋**을 하나의 응답 규격과 페이징 규격으로 통일해 충돌 없이 통합했습니다.

---

<br/>

## 한계 및 개선 방향

프로젝트를 마무리하며 팀이 스스로 짚은, 지금 코드가 가진 한계와 다음에 개선하고 싶은 지점들입니다.

### 예외 코드 기반 프론트엔드 처리의 미완성

* **의도** — 백엔드는 팀 자체 `CustomHttpStatus` Enum으로 예외 코드를 관리하고, 공통 응답 객체의 `code` 필드에 팀끼리 약속한 코드를 담아 내려줍니다. 프론트엔드는 이 `code` 값에 따라 상황별로 세분화된 예외 처리를 하도록 설계하는 것이 목표였습니다.
* **현실** — 백엔드에서 코드를 정의하고 응답에 담아 내려주는 부분까지는 구현했지만, 일정에 쫓기면서 프론트엔드가 그 `code`에 맞춰 예외를 세분화해 처리하는 부분은 완성하지 못했습니다.
* **개선 방향** — 백엔드는 이미 `code`를 정상적으로 내려주고 있으므로, 프론트엔드에서 이 `code`를 받아 상황별로 예외를 처리하는 로직을 붙일 계획입니다.

<br/>

<div align="center">
<br/>

**EVRE** — 기능을 *돌아가게* 만드는 것과 *믿을 수 있게* 만드는 것 사이의 거리를 체감한 프로젝트

</div>
