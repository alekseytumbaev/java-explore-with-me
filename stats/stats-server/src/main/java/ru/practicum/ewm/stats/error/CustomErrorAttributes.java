package ru.practicum.ewm.stats.error;

import lombok.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    public ErrorAttributesBuilder errorAttributes(WebRequest webRequest, ErrorAttributeOptions options,
                                                  HttpStatus status, String error) {
        return new ErrorAttributesBuilder(webRequest, options, status, error);
    }

    @Value
    public static class FieldError {
        String field;
        String message;
    }

    @Value
    public static class ObjectError {
        String message;
    }

    @Value
    public static class HttpAttributeError {
        String attribute;
        String message;
    }

    public class ErrorAttributesBuilder {
        private final Map<String, Object> attributes;

        private ErrorAttributesBuilder(WebRequest webRequest, ErrorAttributeOptions options, HttpStatus status, String error) {
            attributes = getErrorAttributes(webRequest, options);
            attributes.put("status", status.value());
            attributes.put("error", error);
        }

        public ErrorAttributesBuilder fieldErrors(Iterable<FieldError> fieldErrors) {
            attributes.put("fieldErrors", fieldErrors);
            return this;
        }

        public ErrorAttributesBuilder objectErrors(Iterable<ObjectError> objectErrors) {
            attributes.put("objectErrors", objectErrors);
            return this;
        }

        public ErrorAttributesBuilder httpAttributeErrors(Iterable<HttpAttributeError> httpAttributeErrors) {
            attributes.put("httpAttributeErrors", httpAttributeErrors);
            return this;
        }

        public Map<String, Object> build() {
            return attributes;
        }
    }
}
