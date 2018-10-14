# Conference Room Reservation Service [![Build Status](https://travis-ci.org/doooyeon/conference-room-reservation.svg?branch=master)](https://travis-ci.org/doooyeon/conference-room-reservation)

### 회의실과 날짜, 사용 시간을 입력하여 회의실을 예약하는 회의실 예약 웹 서비스

---

## Project Build & Run
- 방법 1. gradle build & jar run ([java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), [git](https://git-scm.com/downloads), [gradle](https://gradle.org/install/) 사전 설치)
    1. project clone
    ```
    ~$ git clone https://github.com/doooyeon/conference-room-reservation.git
    ```
    2. 위치 이동
    ```
    ~$ cd conference-room-reservation
    ```
    3. build
    ```
    ~/conference-room-reservation$ gradle build
    ```
    4. BUILD SUCCESSFUL 후 jar 파일 실행
    ```
    ~/conference-room-reservation$ java -jar -Dspring.profiles.active=prod build/libs/reservation-1.0.0.jar
    ```
    5. 웹 브라우저에서 http://localhost:8080/ 접속

- 방법 2. URL 접속 (AWS EC2)
    - [Conference Room Reservation Service](http://ec2-52-79-232-70.ap-northeast-2.compute.amazonaws.com:8080/)

---

## Feature
![add-reservation](./images/add-reservation.png)
- **예약하기**
    - 회의실 이름, 예약자명, 예약일자, 예약시간, 반복횟수 입력하여 예약
        - 회의실 : A~J
        - 예약자명 : 길이(2~10)
        - 예약일자 : yyyy-mm-dd
        - 예약시간 : hh:mm ~ hh:mm (9:00~20:00, 30분 단위)
            - validation message
                - 30분 단위로 입력해주세요.
                - 09:00~20:00 시간을 입력해주세요.
                - 종료시간은 시작시간 이후이어야 합니다.
                - 이미 예약된 시간입니다.
        - 반복횟수 : 값(2~10)

![view-reservation](./images/view-reservation.png)
- **예약조회**
    - 기본적으로 오늘 일자의 예약 내역 출력
    - 조회할 일자 선택하여 예약조회
        - 조회일자 : yyyy-mm-dd
    - 예약 시작시간과 종료시간 사이에 예약자명 출력

---

## Development Environment
- Language
    - Java8, HTML, CSS, JavaScript
- Back-end
    - Spring Boot
    - Spring Data JPA
    - H2
- Front-end
    - Bootstrap
    - jQuery
- Etc
    - GitHub
    - Travis CI
    - AWS EC2