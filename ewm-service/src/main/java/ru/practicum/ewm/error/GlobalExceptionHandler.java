package ru.practicum.ewm.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.ewm.error.exception.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentActionRestrictedException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onCommentActionRestricted(final CommentActionRestrictedException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Comment action restricted", CONFLICT, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(CommentingRestrictedException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onCommentingRestricted(final CommentingRestrictedException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Commenting restricted", CONFLICT, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onCommentNotFoundException(final CommentNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(EndBeforeStartTimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError onEndBeforeStartTimeException(final EndBeforeStartTimeException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "End before start time", BAD_REQUEST, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(CompilationNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onCompilationNotFoundException(final CompilationNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(RequestCreationRestrictedException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onRequestCreationRestricted(final RequestCreationRestrictedException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Request creation restricted", CONFLICT, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onRequestNotFoundException(final RequestNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(RequestUpdateRestrictedException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onRequestUpdateRestrictedException(final RequestUpdateRestrictedException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Request update restricted", CONFLICT, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(EventUpdateRestrictedException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onEventUpdateRestricted(final EventUpdateRestrictedException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Event update restricted", CONFLICT, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(IllegalEventStartTimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError onIllegalEventStartTime(final IllegalEventStartTimeException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Illegal event start time", BAD_REQUEST, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onEventNotFoundException(final EventNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(CannotGetStatsException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError onCannotGetStatsException(final CannotGetStatsException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Cannot required get statistics",
                INTERNAL_SERVER_ERROR, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onCategoryNotFoundException(final CategoryNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError onUserNotFoundException(final UserNotFoundException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), NOT_FOUND.getReasonPhrase(), NOT_FOUND, LocalDateTime.now());
        log.warn("{}: {}", e.getMessage(), apiError);
        return apiError;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    public ApiError onDataIntegrityViolationException(final DataIntegrityViolationException e) {
        ApiError apiError = new ApiError(null, e.getMessage(), "Data integrity violation", CONFLICT, LocalDateTime.now());
        log.warn("{}: ", e.getMessage(), e);
        return apiError;
    }

    //Fields and object validation violations
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>(ex.getErrorCount());
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                errors.add(format("Field: %s. Error: %s. Value: '%s'", fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
        );

        ex.getBindingResult().getGlobalErrors().forEach(ge ->
                errors.add(format("Object: %s. Error: %s", ge.getObjectName(), ge.getDefaultMessage()))
        );

        String message = format("Validation failed for object '%s'", ex.getBindingResult().getObjectName());
        ApiError apiError = new ApiError(errors, message, "Incorrectly made request", BAD_REQUEST, LocalDateTime.now());

        log.warn("{}: {}", message, apiError);
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }

    //Http attributes (path variables, headers, request parameters) violations
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError onConstraintViolationException(final ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(cv -> {
            String propertyPath = cv.getPropertyPath().toString();
            String attribute = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);

            errors.add(format("Attribute: %s. Error: %s. Value: '%s'", attribute, cv.getMessage(), cv.getInvalidValue()));
        });

        String message = "Validation of http attribute(s) failed";
        ApiError apiError = new ApiError(errors, message, "Incorrectly made request", BAD_REQUEST, LocalDateTime.now());

        log.warn("{}: {}", message, apiError);
        return apiError;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError onThrowable(final Throwable e) {
        ApiError apiError = new ApiError(null, e.getMessage(), INTERNAL_SERVER_ERROR.getReasonPhrase(),
                INTERNAL_SERVER_ERROR, LocalDateTime.now());
        log.error("{}: ", INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return apiError;
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex,
                                                             Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatus status,
                                                             @NonNull WebRequest request) {
        ApiError apiError = new ApiError(null, ex.getMessage(), status.getReasonPhrase(), BAD_REQUEST, LocalDateTime.now());
        log.warn("{}: ", status.getReasonPhrase(), ex);
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }
}
