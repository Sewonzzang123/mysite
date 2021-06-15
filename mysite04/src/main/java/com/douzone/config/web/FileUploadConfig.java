package com.douzone.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileUploadConfig extends WebMvcConfigurerAdapter{
	/*
	 * <!-- Multipart resolver --> 
	 * <bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver"
	 * id="multipartResolver"> 
	 * <!-- 최대업로드 가능한 바이트크기 --> 
	 * <property name="maxUploadSize" value="52428800" /> 
	 * <!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 --> <!-- property name="maxInMemorySize" value="52428800" / -->
	 * <!-- defaultEncoding --> 
	 * <property name="defaultEncoding" value="utf-8" />
	 * </bean>
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(52428800);
		resolver.setMaxInMemorySize(52428800);
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	/*
	 * <!-- mvc resources mapping -->
		<mvc:resources location="file:/uploads-mysite/"
		mapping="/images/**" />
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").
		addResourceLocations("file:/uploads-mysite/");
	}

	
}
