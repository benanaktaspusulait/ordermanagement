package com.cgi.ordermanagement.exception;

import com.cgi.ordermanagement.config.StaticApplicationContext;
import com.cgi.ordermanagement.model.dto.ScreenMessageDTO;
import com.cgi.ordermanagement.model.enums.Language;
import com.cgi.ordermanagement.service.ScreenMessageService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@EqualsAndHashCode(callSuper=false)
public class AppException extends RuntimeException{

    private String errorDescription;
    private Integer errorCode;

    public AppException(Integer errorCode) {
        super(String.valueOf(errorCode));
        ScreenMessageService screenMessageService = StaticApplicationContext.getContext().getBean(ScreenMessageService.class);

        ScreenMessageDTO screenMessage = screenMessageService.findByLanguageAndCode(Language.EN, errorCode.toString());

        this.errorCode = errorCode;
        this.errorDescription = screenMessage.getValue();

    }

    public AppException(Integer errorCode, Throwable ex) {
        super();
        this.initCause(ex);
        ScreenMessageService screenMessageService = StaticApplicationContext.getContext().getBean(ScreenMessageService.class);

        ScreenMessageDTO screenMessage = screenMessageService.findByLanguageAndCode(Language.EN, errorCode.toString());

        this.errorCode = errorCode;
        this.errorDescription = screenMessage.getValue();

    }

    public AppException(Integer errorCode, String... parameters) {
        super(String.valueOf(errorCode));
        ScreenMessageService screenMessageService = StaticApplicationContext.getContext().getBean(ScreenMessageService.class);

        ScreenMessageDTO screenMessage = screenMessageService.findByLanguageAndCode(Language.EN, errorCode.toString());

        String message = screenMessage.getValue();

        for (int i = 0; i < parameters.length; i++) {
            message = message.replace("(" + i + ")", parameters[i]);
        }

        this.errorCode = errorCode;
        this.errorDescription = message;

    }

    public AppException(Integer errorCode, Throwable ex, String... parameters) {
        super();
        this.initCause(ex);
        ScreenMessageService screenMessageService = StaticApplicationContext.getContext().getBean(ScreenMessageService.class);

        ScreenMessageDTO screenMessage = screenMessageService.findByLanguageAndCode(Language.EN, errorCode.toString());

        String message = screenMessage.getValue();

        for (int i = 0; i < parameters.length; i++) {
            message = message.replace("(" + i + ")", parameters[i]);
        }

        this.errorCode = errorCode;
        this.errorDescription = message;
    }


}