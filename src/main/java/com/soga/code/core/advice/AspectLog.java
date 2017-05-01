package com.soga.code.core.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class AspectLog {
	
	private static final String ASPECT = "execution(* com.soga.code.core.advice.PointCuts.*(..))";
	
	@Before(ASPECT)
    public void before(){
		System.out.println("[AspectLog] before advise");
    }

//    @Around(ASPECT)
    public void around() throws  Throwable{
        System.out.println("[AspectLog] around advise 1");
    }

    @AfterReturning(value=ASPECT,returning="rt")
    public void afterReturning(JoinPoint joinPoint, String rt) {
    	 System.out.println("****************************注解解释器执行开始*********************************");

         //获取目标对象对应的类名

         System.out.println("类名："+joinPoint.getTarget().getClass());

         //获取实现类继承的接口名

         @SuppressWarnings("rawtypes")
         Class[] c = joinPoint.getTarget().getClass().getInterfaces();

         System.out.println("注解对象所实现的接口名："+c[0]);

         //获取到了注解在这个service实现类上的annotation

         Annotation[] a = joinPoint.getTarget().getClass().getAnnotations();

         //获取这个类上的注解的个数

         System.out.println("应用注解类上的注解个数："+a.length);

         //判断这个类上面的注释是否是AnnotationName这个自定义的注解，如果是返回这个注解，如果不是返回null

         if(joinPoint.getTarget().getClass().getAnnotation(Service.class)!=null){

             //获取到这个类上的注解

        	 Service anns = joinPoint.getTarget().getClass().getAnnotation(Service.class);

              //输出这个类上的注解的值

             System.out.println("注释在实现类上的annotation："+anns.annotationType());

         }

         //判断这个接口上是否存在此注解

         if(c[0].getAnnotation(Service.class)!=null){

        	 Service an = (Service) c[0].getAnnotation(Service.class);

             System.out.println("注解对象所实现接口上的注解值："+an.value());

         }

         //获取目标对象上正在执行的方法名

         String methodString = joinPoint.getSignature().getName();

         System.out.println("目标对象上正在执行的方法名："+methodString);

         //获取到这个类上面的方法全名

         Method meths[] = joinPoint.getSignature().getDeclaringType().getMethods();

         System.out.println("方法上面的全名："+meths[0]);

         //获取到这个类上面的方法上面的注释

//         Annotation[] anns = meths[0].getDeclaredAnnotations();
//
//         System.out.println("正在执行方法上面的注释："+((Service)anns[0]).value());

         //让你注释的那个方法执行

//         joinPoint.proceed();

         //完毕

         System.out.println("****************************注解解释器执行完毕*********************************");
        System.out.println("[AspectLog] afterReturning & retrun:"+rt);
    }

    @AfterThrowing(throwing = "ex",pointcut = ASPECT)
    public void afterThrowing(JoinPoint jp,Throwable ex) {
        System.out.println("[AspectLog] afterThrowing start");
        System.out.println("目标方法中抛出的异常：" + ex);
        System.out.println("模拟抛出异常后的增强处理...");
        System.out.println("[AspectLog] afterThrowing end");
    }

    @After(ASPECT)
    public void after() {
        System.out.println("[AspectLog] after advise");
    }
    
}
