# JWT 기반 인증/인가 백엔드 과제
> 본 프로젝트는 Spring Boot 기반의 JWT 인증/인가 기능을 구현한 백엔드 과제입니다.

## 기능 요약
- 회원가입
- 로그인 (JWT 발급)
- JWT 인증 필터를 통한 사용자 인증
- 관리자 권한 부여
- Swagger API 문서 제공
- 테스트 코드 작성 (JUnit)
- AWS EC2 배포 및 재부팅 시 자동 실행 설정

## 실행 방법
### 로컬 실행
```
./gradlew build
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

### EC2 서버에서 실행
아래 명령어는 EC2 서버를 사용하는 예시입니다.
해당 명령어는 퍼블릭 IP와 PEM 키 파일을 가진 사용자만 실행할 수 있습니다.
```
scp -i Your.pem build/libs/backend-0.0.1-SNAPSHOT.jar ubuntu@[YOUR_EC2_PUBLIC_IP]:~/app.jar
ssh -i Your.pem ubuntu@[YOUR_EC2_PUBLIC_IP]
java -jar app.jar
```

### 테스트 실행 방법
```
./gradlew test
```
- 회원가입 성공/실패 테스트
- 로그인 실패 테스트
- 관리자 권한 부여 예외 테스트 포함

## Swagger API 문서
[Swagger](http://13.60.171.139:8080/swagger-ui/index.html)

## 주요 API 명세 요약
| 메서드 | URL | 설명 | 인증 필요 여부 |
| -- | -- | -- | -- |
| POST | /signup | 회원가입 | No |
| POST | /login | 로그인(JWT 발급) | No |
| PATCH | /admin/promote | 관리자 권한 부여 | Yes |
| GET | /users/me | 내 정보 조회 | Yes | 

## 기타 사항
DB를 사용하지 않고 ConcurrentHashMap으로 사용자 저장
패스워드 단순 저장
관리자 권한은 서버 내부에서 수동으로 부여
