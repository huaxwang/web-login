package com.soga.code.core.annotation;

@TestAnnotation(say = "Do it!")
public class TestMain {
	public static void main(String[] args) {
		 TestAnnotation annotation = TestMain.class.getAnnotation(TestAnnotation.class);//获取TestMain类上的注解对象
	     System.out.println(annotation.say());//调用注解对象的say方法，并打印到控制台
	}
}
