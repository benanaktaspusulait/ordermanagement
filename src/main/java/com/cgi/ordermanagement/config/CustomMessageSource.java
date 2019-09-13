package com.cgi.ordermanagement.config;

import com.cgi.ordermanagement.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class CustomMessageSource {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String getMessage(String code) {
        return accessor.getMessage(code);
    }

    public String getMessage(String code, Object[] args) {return accessor.getMessage(code, args);}

    public String getErrorMessage(String code) {
        return accessor.getMessage(AppConstants.ERROR_PREFIX + code);
    }


}
