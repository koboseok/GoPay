![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=300&section=header&text=GoPay%20Project&fontSize=90)


패스트캠퍼스 온라인 강의 기반으로 구현한 프로젝트 입니다.

일반적인 간편결제 도메인을 주제로, MSA를 중심적으로 학습했습니다.

회원(Membership), 뱅킹(Banking), 머니(Money), 송금(Remittance), 결제(Payment), 정산(Settlement) 6개의 서비스로 구성되어 있으며, 각각의 독립적인 프로젝트로 구성되어 있습니다.

각 서비스에서는 기본적인 기능을 먼저 Hexagonal Architecture 로 구현하고, 일부 기능들에 EDA, CQRS, Event Sourcing, Saga Pattern 등을 적용하며 진행하였으며,
기본적으로 Springboot 와 Java 11 을 기준으로 프로젝트를 구성하고 EDA 구현을 위해서 Axon Framework, 로깅 파이프라인은 따로 구성하진 않았지만 kafka 의 실습을 목적으로 하는 간단한 로깅 파이프라인을 구성해보는 프로젝트 입니다.


## Membership Service
#### 고객의 회원 가입, 회원 정보 변경, 회원 정보 조회 등의 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 기본적인 Membership Service 를 구현 (회원 가입)
- gradle build tool 을 이용하여 Docker build 연동. 추가적인 Membership Service 를 구현 (회원 정보 변경)
- 고객 정보가 변경될 경우, 이벤트를 발행하고, 이를 구독하는 Money 서비스 에서 고객의 머니 정보를 변경하는 CQRS 패턴 구현 (with/ AWS DynamoDB)

#### Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose, AWS DynamoDB


## Banking Service
#### 고객의 계좌 정보 등록, 등록된 계좌 정보 조회, 입/출금, 거래내역 조회 등의 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 기본적인 Banking Service 를 구현 (가상의 법인 계좌 및 고객 계좌 정보 등록, 은행으로 입/출금 요청하기)

#### Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose, AWS DynamoDB


## Money Service
#### 고객의 계좌 정보 등록, 등록된 계좌 정보 조회, 입/출금, 거래내역 조회 등의 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 Membership 서비스 및 Banking 서비스를 이용하는 충전 잔액(머니)을 충전하는 기능 구현
- 충전 내역 조회 기능 구현
- kafka 을 이용한 간단 로깅 파이프라인 적용
- Membership 서비스로부터 고객 정보 변경 이벤트를 수신하고, 이를 기반으로 CQRS 패턴을 구현 (with/ AWS DynamoDB)

#### Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose, Kafka, Kafka-ui, Zookeeper, AWS DynamoDB


## Remittance Service
#### 고객 간 송금 기능 및 송금 내역 정보 조회 등의 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 Membership 서비스, Banking 서비스, Money 서비스를 이용하는 고객 간 혹은 계좌 송금 기능 구현
- 고객 간 송금하는 기능은 기능은 Axon Framework 를 이용하여 Saga Pattern 적용  
- 특정 송금 건을 기준으로, 머니의 충전 내역을 조회해보는 기능 구현을 위해 API Aggregation Pattern 적용

#### Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose, AWS DynamoDB


## Payment Service
#### 가맹점에서 GoPay 를 이용한 간편 결제 및 결제 내역 조회 등의 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 Membership 서비스, Money 서비스를 이용하는 가맹점에서의 결제 기능 구현
- Membership Service 의 가맹점주 기능 확장

#### Stack
- Spring Boot, Java 11, Spring Data JPA, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose


## Settlement Service
#### 완료된 결제 내역을 기준으로 가맹점에 정산된 금액을 입금하고, 수수료 수취를 위한 기능을 제공하는 서비스입니다.
#### Explanation
- Hexagonal Architecture 를 활용하여 Payment 서비스를 이용하는 기간별 정산 기능 구현

#### Stack
- Spring Boot, Java 11, Spring Data JPA, Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose


## Execution
```
./gradlew docker
docker-compose up -d
```

## Service EndPoint & Swagger UI
- membership-service
  - http://localhost:8081/membership/
  - http://localhost:8081/swagger-ui.html
    
- banking-service
  - http://localhost:8082/banking/
  - http://localhost:8082/swagger-ui.html
    
- money-service
  - http://localhost:8083/money/
  - http://localhost:8083/swagger-ui.html
    
- remittance-service
  - http://localhost:8084/remittance/
  - http://localhost:8084/swagger-ui.html
    
- payment-service
  - http://localhost:8085/payment/
  - http://localhost:8085/swagger-ui.html
    
- settlment-service
  - http://localhost:8086/settlment/
  - http://localhost:8086/swagger-ui.html

- mysql
  - http://localhost:3307
  - root password: rootpassword
  - database: gopay
  - user / pw : mysqluser / mysqlpw

- kafka ui
  - http://localhost:8989

- axon server dashboard
   - http://localhost:8024
 

## Sample Screenshots

#### :mailbox_with_mail: Docker, Docker-Compose
<img width="2303" height="1274" alt="스크린샷 2025-07-16 오후 2 38 20" src="https://github.com/user-attachments/assets/62b9192d-02cf-45b7-b34a-23ec4678cd35" />


#### :bookmark_tabs: Swagger-ui
<img width="1456" height="572" alt="스크린샷 2025-07-16 오후 2 44 58" src="https://github.com/user-attachments/assets/f9cc4fc1-d906-4018-9daf-e09816c96e16" />


#### :chart_with_downwards_trend: Kafka-ui
<img width="1452" height="589" alt="스크린샷 2025-07-16 오후 2 47 02" src="https://github.com/user-attachments/assets/7f22326a-564b-4f04-83da-06553a05ce84" />


#### :chart_with_upwards_trend: Axon Framwork-ui
<img width="1479" height="847" alt="스크린샷 2025-07-16 오후 2 49 18" src="https://github.com/user-attachments/assets/c620fb81-f34d-4aa0-bac4-6b3d97d3d504" />


#### :floppy_disk: Database
<img width="1350" height="494" alt="스크린샷 2025-07-16 오후 2 51 59" src="https://github.com/user-attachments/assets/5501b645-dc82-41ab-adbc-55e525b1b1c4" />







