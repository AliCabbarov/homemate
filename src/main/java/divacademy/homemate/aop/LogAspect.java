package divacademy.homemate.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor
public class LogAspect {

    @Around(value = "processPayment()")
    public Object logAllPayments(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();
            log.info("Process with {} request:  {}",signature.getName(),List.of(proceedingJoinPoint.getArgs()));
            Object response = proceedingJoinPoint.proceed();
            log.info("Process with {} response: {}", signature.getName(), response);
            return response;

    }

    @Pointcut("@annotation(Log)")
    public void processPayment() {
    }
}
