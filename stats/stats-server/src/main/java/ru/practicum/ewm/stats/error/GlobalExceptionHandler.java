package ru.practicum.ewm.stats.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.practicum.ewm.stats.error.exception.EndBeforeStartTimeException;

import javax.validation.ConstraintViolationException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final CustomErrorAttributes attributes;

    public GlobalExceptionHandler(CustomErrorAttributes attributes) {
        this.attributes = attributes;
    }

    @ExceptionHandler(EndBeforeStartTimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, Object> onEndBeforeStartTimeException(final EndBeforeStartTimeException e, final WebRequest request) {
        Map<String, Object> errorAttributes = attributes
                .errorAttributes(request, defaults(), BAD_REQUEST, e.getMessage())
                .build();
        log.warn("{}: {}", e.getMessage(), errorAttributes);
        return errorAttributes;
    }

    //Fields and object validation violations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, Object> onMethodArgumentNotValidException(final MethodArgumentNotValidException e, final WebRequest request) {
        List<CustomErrorAttributes.FieldError> fieldErrors = new LinkedList<>();
        e.getBindingResult().getFieldErrors().forEach(fe ->
                fieldErrors.add(new CustomErrorAttributes.FieldError(fe.getField(), fe.getDefaultMessage()))
        );

        List<CustomErrorAttributes.ObjectError> objectErrors = new LinkedList<>();
        e.getBindingResult().getGlobalErrors().forEach(ge ->
                objectErrors.add(new CustomErrorAttributes.ObjectError(ge.getDefaultMessage()))
        );

        String error = String.format("Validation failed for object '%s'", e.getBindingResult().getObjectName());
        Map<String, Object> errorAttributes = attributes
                .errorAttributes(request, defaults(), BAD_REQUEST, error)
                .objectErrors(objectErrors)
                .fieldErrors(fieldErrors)
                .build();

        log.warn("{}: {}", error, errorAttributes);
        return errorAttributes;
    }

    //Http attributes (path variables, headers, request parameters) violations
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, Object> onConstraintViolationException(final ConstraintViolationException e, final WebRequest request) {
        List<CustomErrorAttributes.HttpAttributeError> httpAttributeErrors = new LinkedList<>();
        e.getConstraintViolations().forEach(cv -> {
            String propertyPath = cv.getPropertyPath().toString();
            String attribute = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);

            httpAttributeErrors.add(new CustomErrorAttributes.HttpAttributeError(attribute, cv.getMessage()));
        });

        String error = "Validation failed";
        Map<String, Object> errorAttributes = attributes
                .errorAttributes(request, defaults(), BAD_REQUEST, error)
                .httpAttributeErrors(httpAttributeErrors)
                .build();

        log.warn("{}: {}", error, error, e);
        return errorAttributes;
    }
}
