
package com.ntc.fraud.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Aspect for logging execution.
 *
 * @author Ramesh Fadatare
 *
 */

@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* com.ntc.fraud.controller..*(..))")
	public void pointcut(){
	}

	@Before("pointcut()")
	public void before(JoinPoint joinPoint){
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		logger.info("Using Controller ==> {} : Using Method ==> {}",joinPoint.getSignature().getDeclaringTypeName().substring(25),joinPoint.getSignature().getName());
	}

	@After("pointcut()")
	public void after(JoinPoint joinPoint){
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		logger.info("Request Success");
	}
}
