package com.boot.api.globals.controller;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class BaseController {
    protected final LocalValidatorFactoryBean validatorFactory;

    public BaseController(LocalValidatorFactoryBean validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new CollectionValidator(validatorFactory));
    }
}
