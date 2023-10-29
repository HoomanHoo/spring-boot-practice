package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		String version = System.getProperty("java.version");
		System.out.println("version: " + version);	// 현재 프로젝트에서 사용중인 JAVA Version 확인 - 현재 JAVA 17
		
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
