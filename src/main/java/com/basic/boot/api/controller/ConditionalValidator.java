package com.basic.boot.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;

/**
 * 객체가 not null일 때, validation 수행.
 */
@Component
public class ConditionalValidator implements Validator, SmartValidator {
	/**
	 * Logger.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ConditionalValidator.class);

	/**
	 * SpringValidatorAdapter.
	 */
	private final SpringValidatorAdapter validator;

	/**
	 * ConditionalValidator.
	 */
	public ConditionalValidator() {
		this.validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Object.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target != null) {
			validator.validate(target, errors);
		}
	}

	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		if (target != null) {
			validator.validate(target, errors, validationHints);
		}
	}
}
