package com.jukebox.test.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseEntity {
    private String rootException;
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<NestedExceptionResponseEntity> nestedExceptions;

    private ExceptionResponseEntity() {
    }
    public ExceptionResponseEntity(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public ExceptionResponseEntity(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public ExceptionResponseEntity(HttpStatus httpStatus, Throwable rootException) {
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.rootException = rootException.getLocalizedMessage();
    }
    public ExceptionResponseEntity(HttpStatus httpStatus, String message, Throwable rootException) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.rootException = rootException.getLocalizedMessage();
    }
    public void addNestedException(NestedExceptionResponseEntity nestedExceptionResponseEntity) {
        if (nestedExceptions == null) {
            nestedExceptions = new ArrayList<>();
        }
        nestedExceptions.add(nestedExceptionResponseEntity);
    }
    public void addValidationError(String object, String field, Object rejectedValue, String message) {
        addNestedException(new NestedExceptionResponseEntity(object, field, rejectedValue, message));
    }
    public void addValidationError(String object, String message) {
        addNestedException(new NestedExceptionResponseEntity(object, message));
    }
    public void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }
    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }
    public void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }
    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }
    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    public void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }
    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
    public String getRootException() {
        return rootException;
    }
    public void setRootException(String rootException) {
        this.rootException = rootException;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public List<NestedExceptionResponseEntity> getNestedExceptions() {
        return nestedExceptions;
    }
    public void setNestedExceptions(List<NestedExceptionResponseEntity> nestedExceptions) {
        this.nestedExceptions = nestedExceptions;
    }
}
