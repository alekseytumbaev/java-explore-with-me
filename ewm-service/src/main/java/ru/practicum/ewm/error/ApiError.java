package ru.practicum.ewm.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Сведения об ошибке
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ApiError", description = "Сведения об ошибке")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:44:05.928962+07:00[Asia/Barnaul]")
public class ApiError {

    @JsonProperty("errors")
    @Valid
    private List<String> errors = null;

    @JsonProperty("message")
    private String message;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("timestamp")
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime timestamp;

    public ApiError errors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public ApiError addErrorsItem(String errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(errorsItem);
        return this;
    }

    /**
     * Список стектрейсов или описания ошибок
     *
     * @return errors
     */

    @Schema(name = "errors", example = "[]", description = "Список стектрейсов или описания ошибок", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiError message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Сообщение об ошибке
     *
     * @return message
     */

    @Schema(name = "message", example = "Only pending or canceled events can be changed", description = "Сообщение об ошибке", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiError reason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * Общее описание причины ошибки
     *
     * @return reason
     */

    @Schema(name = "reason", example = "For the requested operation the conditions are not met.", description = "Общее описание причины ошибки", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ApiError status(HttpStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Код статуса HTTP-ответа
     *
     * @return status
     */

    @Schema(name = "status", example = "FORBIDDEN", description = "Код статуса HTTP-ответа", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ApiError timestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Дата и время когда произошла ошибка (в формате \"yyyy-MM-dd HH:mm:ss\")
     *
     * @return timestamp
     */

    @Schema(name = "timestamp", example = "2022-06-09 06:27:23", description = "Дата и время когда произошла ошибка (в формате \"yyyy-MM-dd HH:mm:ss\")", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiError apiError = (ApiError) o;
        return Objects.equals(this.errors, apiError.errors) &&
                Objects.equals(this.message, apiError.message) &&
                Objects.equals(this.reason, apiError.reason) &&
                Objects.equals(this.status, apiError.status) &&
                Objects.equals(this.timestamp, apiError.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, message, reason, status, timestamp);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiError {\n");
        sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

