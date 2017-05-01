package com.soga.code.core.advice;

import org.springframework.stereotype.Service;


@Service
public class PointCutsServiceImpl implements PointCuts{
    /*@Pointcut(value = "within(com.jadyer.shiro.RedisClient.*)")*/
    public String aopDemo(){
    	System.out.println("[PointCuts][aopDemo] is ok!");
    	return " Hello,Spring AOP"; 
    }
    
    public String AfterThrowingDemo(){
    	//以下代码是供@AfterThrowing使用
    	Object a = "adfab";
    	Integer w = (Integer)a/1;
    	return " Hello,Spring AOP"; 
    }

}
