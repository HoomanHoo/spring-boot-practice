package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP 적용
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 사용할 범위를 지정한다
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }

}

/*
 * 모든 메서드의 호출 시간 등 공통 관심사를 코드로 구현해야 하는 경우
 * AOP(관점 지향 프로그래밍) 을 사용할 수 있다
 * AOP를 사용하지 않았을 경우 공통 관심 사항에 대한 로직이 핵심 비즈니스 로직과 섞인다
 * 이는 유지보수의 어려움을 유발할 수 있다
 * 또한 별도의 공통 로직으로 만들기 어려운 경우도 존재한다 (시간 측정 등)
 * 로직에 적용된 모든 부분을 찾아 일일히 변경해줘야한다
 * 
 * AOP는 공통 관심사를 구현한 로직을 하나 구현하고 이를 적용하고 싶은 곳을 지정해주는 방식으로 사용한다
 * Spring은 AOP를 지원해준다
 * 
 * @Around("execution(* hello.hellospring..*(..))") 는 hellospring 패키지 하위의
 * 모든 패키지와 클래스들에 적용시킨다는 의미이다
 * 프록시 패턴으로 클래스 호출 이전에 가짜 클래스를 하나 만든다 이 가짜 클래스의 사용이 끝나면 joinPoint.proceed()가
 * 호출되어 진짜 클래스가 호출이 된다
 * Spring에서는 의존성 주입이 되기 때문에 프록시 방식 AOP가 가능하다
 */
