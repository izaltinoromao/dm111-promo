package br.inatel.dm111promo.api.core;

import org.springframework.http.HttpStatus;

public enum AppErrorCode {

    PROMO_NOT_FOUND("entity.promo.not-found", "The promo does not exist.", HttpStatus.NOT_FOUND),
    PROMO_QUERY_ERROR("query.promo.error", "The promo query is not working. Please try again!", HttpStatus.INTERNAL_SERVER_ERROR),
    PROMO_INVALID("entity.promo.invalid", "The promo is invalid.", HttpStatus.BAD_REQUEST);

    private String code;
    private String message;
    private HttpStatus status;

    AppErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
