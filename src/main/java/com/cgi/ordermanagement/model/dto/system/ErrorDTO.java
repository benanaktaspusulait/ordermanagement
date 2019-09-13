package com.cgi.ordermanagement.model.dto.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {

    private String error;
    private String errorDescription;
    private Integer errorCode;

    public ErrorDTO() {
    }

    public ErrorDTO(Integer errorCode) {
        this.setErrorCode(errorCode);
    }

    public ErrorDTO(String error, String error_description) {
        this.setError(error);
        this.setErrorDescription(error_description);
    }

    public ErrorDTO(String error, String error_description, Integer errorCode) {
        this.setError(error);
        this.setErrorDescription(error_description);
        this.setErrorCode(errorCode);
    }


    public interface ResponseCodes {

        public static final Integer SUCCESS = 0;
        public static final Integer GENERAL = 100;
        public static final Integer ALREADY_EXIST_EMAIL = 101;
        public static final Integer RAILSBANK_INDIVIDUAL_MANDATORY = 102;
        public static final Integer ACCOUNT_ALREADY_EXISTS = 103;
        public static final Integer ACCOUNT_NOT_EXISTS = 104;
        public static final Integer NOT_FOUND_GENERAL = 105;
        public static final Integer ROLE_NOT_FOUND = 106;
        public static final Integer RAILSBANK_COMPANY_MANDATORY = 107;
        public static final Integer EMPTY_ACTIVATION_CODE = 108;
        public static final Integer FX_RATE_UNVALID = 109;
        public static final Integer COMMON_ERROR_RAILSBANK = 110;
        public static final Integer NOT_ENOUGH_MONEY_IN_RAILSBANK_ACC = 111;
        public static final Integer CARD_NOT_EXISTS = 112;
        public static final Integer AMOUNT_LIMIT_EXISTS = 113;
        public static final Integer RECIPIENT_MISSING_DATA = 114;
        public static final Integer USER_PASSIVE = 115;
        public static final Integer MERCHANT_NOT_ACTIVE = 116;
        public static final Integer FX_RATE_NOT_VALID = 117;
        public static final Integer FX_RATE_MUST_EQUAL_ONE = 118;
        public static final Integer DECLINED_REASON_REQUIRED = 119;
        public static final Integer DECLINED_REASON_MUST_BE_EMPTY = 120;
        public static final Integer FILE_NOT_CREATED = 121;
        public static final Integer EMPTY_OUTLINK = 122;
        public static final Integer NOTFOUND_ACTIVATION_CODE = 123;
        public static final Integer EXPIRED_DURATION_ACTIVATION_CODE = 124;
        public static final Integer RECIPIENT_ACCOUNT_NUMBER_OR_SORTCODE_NOT_NULL = 125;
        public static final Integer RECIPIENT_BIC_OR_IBAN_NOT_NULL = 126;
        public static final Integer EMAIL_VERIFICATION_FAILED = 127;
        public static final Integer NOT_FOUND_MERCHANT_ACCOUNT = 128;
        public static final Integer SEND_SMS_NOTIFICATION_FAILED = 129;
        public static final Integer SEND_EMAIL_NOTIFICATION_FAILED = 130;
        public static final Integer CALCULATION_NOT_FOUND = 131;


    }

}