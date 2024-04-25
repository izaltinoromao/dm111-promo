package br.inatel.dm111promo.api.core;

import org.springframework.http.HttpStatus;

public enum AppErrorCode {

    PROMO_NOT_FOUND("entity.promo.not-found", "The promo does not exist.", HttpStatus.NOT_FOUND),
    PROMO_QUERY_ERROR("query.promo.error", "The promo query is not working. Please try again!", HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORD_ENCRYPTION_ERROR("encrypt.user.error", "The user encryption did not work!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS("auth.user.invalid-credentials", "The provided credentials are invalid.", HttpStatus.UNAUTHORIZED),
    USERS_QUERY_ERROR("query.user.error", "The user query is not working. Please try again!", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCTS_OPERATION_NOT_ALLOWED("entity.products.operation-not-allowed", "The operation is not allowed.", HttpStatus.FORBIDDEN),
    PROMO_OPERATION_NOT_ALLOWED("entity.promo.operation-not-allowed", "The operation is not allowed.", HttpStatus.FORBIDDEN),
    PROMO_BY_USER_OPERATION_NOT_ALLOWED("entity.promoByUser.operation-not-allowed", "The operation is not allowed.", HttpStatus.FORBIDDEN),
    SUPERMARKET_LIST_OPERATION_NOT_ALLOWED("entity.supermarket-list.operation-not-allowed", "The operation is not allowed.", HttpStatus.FORBIDDEN),
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
