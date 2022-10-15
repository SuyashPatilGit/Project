package com.app.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//AOP
//Configuration
@Aspect
@Component
public class AfterAopAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning(value = "execution(* com.app.service.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returned with value {}", joinPoint.getSignature(), result);
	}

	@After(value = "execution(* com.app.service.*.*(..))")
	public void after(JoinPoint joinPoint) {
		// getSignature() : rets entire method signature.
		logger.info("after execution of {}", joinPoint.getSignature());
	}

	@AfterThrowing(value = "execution(* com.app.service.*.*(..))", throwing = "exc")
	public void afterThrowing(Exception exc) {
		logger.info("Exception occurred : " + exc);
	}
}