# T-Shopping (온라인 쇼핑몰 프로젝트)

## 1. 프로젝트 개요
**프로젝트명**: T-Shopping (온라인 쇼핑몰)

~~**배포 주소**: [http://13.209.43.90:8080](http://13.209.43.90:8080)~~ (현재는 무료 이용이 끝나 서버를 종료하였음)

T-Shopping은 Spring Boot 기반의 온라인 쇼핑몰 프로젝트로, 회원가입 및 로그인, 상품 관리, 장바구니, 결제 시스템을 포함한 주요 전자상거래 기능을 구현하였습니다.

---
## 2. 기술 스택

### **백엔드**
- Java 23
- Spring Boot 3.4.2
- Spring Security (세션 기반 인증)
- JPA (Hibernate) & MariaDB
- Lombok

### **프론트엔드**
- Thymeleaf (템플릿 엔진)
- HTML/CSS, JavaScript

### **배포 환경**
- AWS EC2
- Docker

---
## 3. 프로젝트 주요 기능

### ✅ **회원가입 및 로그인**
- Spring Security 기반 로그인
- `USER`, `ADMIN`, `SELLER` 역할 구분
- 역할에 따라 페이지 접근 제한

### ✅ **상품 관리**
- `ADMIN`과 `SELLER`가 상품 등록, 수정, 삭제 가능
- 사용자는 상품을 조회하고 장바구니에 추가 가능

### ✅ **장바구니 및 주문 기능**
- 장바구니에 담긴 상품을 주문으로 변환
- 주문 상태 관리 (`PENDING`, `SUCCESS` 등)

### ✅ **결제 시스템 구현**
- 가상의 결제 처리 로직을 적용하여 주문 완료 후 결제 상태 변경
- `OrderService`와 연동하여 트랜잭션 관리

---
## 4. 프로젝트 아키텍처
- **MVC (Model-View-Controller) 패턴 적용**
- **Layered Architecture** (Entity - Repository - Service - Controller)
- **Spring Boot & JPA 기반 데이터 관리**

---
## 5. 배포 과정 (Docker & AWS EC2)

### **1. Docker를 이용한 컨테이너화**
- Spring Boot 애플리케이션을 JAR 파일로 빌드 후 Docker 이미지 생성
- `Dockerfile`을 활용하여 애플리케이션과 정적 리소스를 포함

### **2. AWS EC2에 배포**
- AWS EC2 인스턴스에 Docker 설치 후, 컨테이너 실행
- MariaDB 컨테이너를 실행하여 애플리케이션과 연동

```sh
# Docker 컨테이너 실행
$ docker run -p 8080:8080 --name tshopping-container -d tshopping
$ docker run -p 3306:3306 --name mariadb-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tutorial__dev -d mariadb:10.6
```

### **3. 배포된 서비스 주소**
- [http://13.209.43.90:8080](http://13.209.43.90:8080)

---
## 6. 트러블슈팅 경험

### ❌ **문제: IoC 컨테이너가 Bean을 생성하지 않는 문제 발생**
- 원인: Spring Boot 애플리케이션이 실행되지 않아 Bean 생성이 되지 않음
- 해결: 로그 분석 후 다른 예외를 해결하자 Bean이 정상적으로 생성됨

### ❌ **문제: MariaDB 연결 오류**
- 원인: `application.yml`에서 `localhost` 대신 `mariadb-container`를 사용해야 했음
- 해결: Docker 네트워크 설정을 변경하여 컨테이너 간 통신 가능하게 수정

---
## 7. 향후 개선 계획

- **실제 결제 시스템(PG 연동) 적용**
- **React 기반의 프론트엔드 리팩토링**
- **쿼리 최적화 및 캐싱을 통한 성능 개선**

---
## 8. 제공 API

### 회원 API
- `GET /member/join`: 회원가입 페이지
- `POST /member/join`: 회원가입 처리
- `GET /member/login`: 로그인 페이지
- `POST /member/login`: 로그인 처리
- `POST /member/logout`: 로그아웃

### 상품 API
- `GET /product`: 전체 상품 목록 조회
- `GET /product/myproducts`: 내 상품 목록 조회
- `GET /product/register`: 상품 등록 페이지
- `POST /product/doProductRegister`: 상품 등록
- `DELETE /product/delete/{id}`: 상품 삭제
- `GET /product/update/{id}`: 상품 수정 페이지

### 주문 API
- `POST /order/create`: 주문 생성
- `GET /order/list`: 주문 목록 조회
- `GET /order/{id}`: 주문 상세 조회

### 장바구니 API
- `GET /cart`: 장바구니 조회
- `POST /cart/add`: 상품 추가
- `DELETE /cart/remove/{id}`: 상품 제거

### 결제 API
- `POST /payment/pay/{orderId}`: 결제 처리
