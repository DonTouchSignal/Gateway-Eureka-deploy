# MSA 기반 시스템 - Eureka & API Gateway

## 프로젝트 소개

이 프로젝트는 마이크로서비스 아키텍처(MSA)를 기반으로 개발된 시스템의 핵심 인프라 구성요소입니다. Spring Cloud 기술 스택을 활용하여 서비스 디스커버리(Eureka)와 API Gateway를 구현하였으며, CI/CD 자동화 파이프라인과 컨테이너화를 통해 확장 가능하고 유지보수가 용이한 시스템을 구축했습니다.

## 주요 구성 요소

### Eureka 서버
- **역할**: 서비스 디스커버리 및 레지스트리
- **포트**: 8761
- **기술**: Spring Cloud Netflix Eureka Server

### API Gateway
- **역할**: 중앙 집중식 라우팅, 인증/인가, 로드 밸런싱
- **포트**: 8080
- **주요 기능**: JWT 기반 인증, Redis를 활용한 토큰 관리

## 기술 스택

- **프레임워크**: Spring Boot, Spring Cloud
- **보안**: JWT, Spring Security
- **데이터 저장소**: Redis
- **빌드/배포**: Docker, GitHub Actions
- **인프라**: AWS EC2

## 구현 특징

1. **자동화된 CI/CD 파이프라인**
   - GitHub Actions를 통한 빌드 및 배포 자동화
   - 설정 파일 동적 생성 및 보안 관리

2. **컨테이너 기반 배포**
   - Docker와 Docker Compose를 활용한 일관된 배포 환경
   - 서비스 간 독립성과 확장성 확보

3. **보안 아키텍처**
   - JWT 기반의 인증 체계
   - API Gateway를 통한 중앙 집중식 보안 관리

## 아키텍처 다이어그램

```
클라이언트 → API Gateway(8080) → 마이크로서비스
                    ↓
             Eureka Server(8761)
                    ↓
             서비스 디스커버리
```

## 배포 환경

AWS EC2 인스턴스에 Docker Compose를 통해 배포되며, GitHub Actions 워크플로우를 통한 자동 배포가 구성되어 있습니다.

## 학습 포인트

- Spring Cloud를 활용한 MSA 구현 기술
- Docker 및 CI/CD 파이프라인 구축 경험
- 분산 시스템의 서비스 디스커버리 패턴 적용
- API Gateway를 통한 중앙 집중식 인증/인가 구현
