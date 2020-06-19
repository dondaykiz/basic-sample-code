package com.basic.boot.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 설정.
 *
 * @author yjkim@ntels.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 메시지 파일 경로.
     */
    @Value("${spring.messages.basename}")
    private String messagesBasename;

    /**
     * 메시지 인코딩.
     */
    @Value("${spring.messages.encoding}")
    private String messagesEncoding;

    /**
     * 메시지 소스.
     *
     * @return ReloadableResourceBundleMessageSource
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messagesBasename);
        messageSource.setDefaultEncoding(messagesEncoding);
        return messageSource;
    }

    /**
     * 메시지 소스 억세서.
     *
     * @return MessageSourceAccessor
     */
    @Bean
    public MessageSourceAccessor getMessageSourceAccessor() {
        ReloadableResourceBundleMessageSource m = messageSource();
        return new MessageSourceAccessor(m);
    }

    /**
     * '@Validated' 어노테이션으로 유효성 체크할 때 메시지 처리.
     */
    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    /**
     * SmartValidator로 수동 유효성 체크할 때 메시지 처리.
     *
     * @return SmartValidator
     */
    @Bean(name = "customValidator")
    public SmartValidator getSmartValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
