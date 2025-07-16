![header](https://capsule-render.vercel.app/api?type=wave&color=auto&height=300&section=header&text=GoPay%20Project&fontSize=90)


패스트캠퍼스 온라인 강의 기반으로 구현한 프로젝트 입니다.

일반적인 간편결제 도메인을 주제로, MSA를 중심적으로 학습했습니다.

회원(Membership), 뱅킹(Banking), 머니(Money), 송금(Remittance), 결제(Payment), 정산(Settlement) 6개의 서비스로 구성되어 있으며, 각각의 독립적인 프로젝트로 구성되어 있습니다.

각 서비스에서는 기본적인 기능을 먼저 Hexagonal Architecture 로 구현하고, 일부 기능들에 EDA, CQRS, Event Sourcing, Saga Pattern 등을 적용하며 진행하였으며,
기본적으로 Springboot 와 Java 11 을 기준으로 프로젝트를 구성하고 EDA 구현을 위해서 Axon Framework, 로깅 파이프라인은 따로 구성하진 않았지만 kafka 의 실습을 목적으로 하는 간단한 로깅 파이프라인을 구성해보는 프로젝트 입니다.
