# ColorAid Backend Server

소프트웨어 마에스트로 12기 NULL팀의 웹툰 밑색 자동채색 어플리케이션 ColorAid의 Backend Server입니다.

다음 기능을 구현했습니다.

- 유저의 회원가입, 로그인
- 이미지(채색할 선화, reference 이미지, stroke 이미지)를 받아서 S3에 업로드
- [ColorAid AI Server](https://github.com/swmaestro-null/Null-AI)에 밑색 자동채색 요청

## 기술스택

Spring boot, Spring Data JPA, MariaDB, EC2, RDS, S3

## 아키텍쳐도

![image](https://user-images.githubusercontent.com/52124204/140560478-4b907c2d-3f8d-4d2d-a75d-e64685d3831f.png)

## API

User Controller

- `/user/signup` 회원가입
- `/user/login` 로그인
- `user/sendCode` 이메일 인증
- `/user/checkCode` 이메일 인증 코드 확인

Painter Controller

- `/painter/upload/{imageType}/{email}` 이미지 업로드
- `/painter/reference` Based on reference 밑색 자동채색

