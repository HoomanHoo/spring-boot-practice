# Spring에서 기본적으로 사용하는 라이브러리

- 라이브러리 관리를 위한 Gradle과 Maven 사용
   - 설정 파일에 있는 라이브러리와 의존성 라이브러리를 관리하고 자동으로 Repository에서 다운로드 한다
   - 최근에는 Gradle을 많이 사용하는 추세이다

- Spring-boot-starter
   - Spring Boot, Spring Core, Logging 모듈 등이 포함되어있음
     - 로그는 System.out.println 대신 로깅 함수를 사용해야 단계에 따른 로깅이 가능하다(slf4j(인터페이스) - logback(구현체)의 조합을 사용함)

- Spring-boot-starter-web
   - Spring으로 웹 서버 어플리케이션을 만들기 위한 기본적인 라이브러리(spring-webmvc)
   - tomcat(WAS)를 의존 라이브러리로 가지고 있다
     - Spring Boot가 아닌 그냥 Spring, 그 이전의 Framework는 Tomcat등의 WAS를 추가로 서버 혹은 개발환경에 설치를 해줘야 했다


- thymeleaf
   - Spring의 Template Engine
   - jsp를 대체하는 엔진

- Spring-boot-starter-test
  - Test Code 작성 및 테스트 진행 관련 라이브러리
  - JUnit 사용
    - 버전은 4에서 5로 업데이트됨 
  - mockito
    - mock up 라이브러리
  - assertj
    - 테스트 코드 작성 도와주는 라이브러리
  - spring-test
    - 스프링 통합 테스트 지원 
    
# Gradle Build 후 배포
- (System Path의 JAVA_HOME에 등록된 JDK 버전과 프로젝트 타겟 JDK 버전이 다를 경우) gradle.properties 파일 생성 후 org.gradle.java.home=사용할 jdk 경로 지정
- 프로젝트 경로로 이동
- gradle build  명령어 실행
- build 완료 후 프로젝트 루트 경로/build/libs 경로로 이동
- settings.gradle에 등록된 rootProject.name + build.gradle에 등록된 version.jar 파일이 생성되었는지 확인
- java -jar 파일명.jar 혹은 jdk 경로\bin\java.exe -jar 파일명.jar 실행
  
  




