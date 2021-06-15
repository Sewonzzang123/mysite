package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.MvcConfig;
//spring-servlet.xml을 대체해야 한다.
/*
 * <context:annotation-config />
	<context:component-scan
	base-package="com.douzone.mysite.controller,com.douzone.mysite.exception" />
 * 
 */
@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy />
@ComponentScan({"com.douzone.mysite.controller, com.douzone.mysite.exception"})
@Import({MvcConfig.class, MessageConfig.class})
public class WebConfig {


		
}
