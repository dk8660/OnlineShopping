package com.example.onlineshopping.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration  // 이 클래스가 설정 클래스임을 나타냄
public class WebConfig {

    // HiddenHttpMethodFilter 필터를 등록하는 메소드
    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HiddenHttpMethodFilter());  // 필터 객체 생성
        registrationBean.addUrlPatterns("/*");  // 모든 URL 경로에 대해 필터 적용
        return registrationBean;
    }
}
