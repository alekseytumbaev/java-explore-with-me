package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */

@AllArgsConstructor
@Schema(name = "EventRequestStatusUpdateRequest", description = "Изменение статуса запроса на участие в событии текущего пользователя")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class EventRequestStatusUpdateRequest {

    @JsonProperty("requestIds")
    @Valid
    private List<Long> requestIds = null;

    @JsonProperty("status")
    private ParticipationRequestStatus status;

    public EventRequestStatusUpdateRequest requestIds(List<Long> requestIds) {
        this.requestIds = requestIds;
        return this;
    }

    public EventRequestStatusUpdateRequest addRequestIdsItem(Long requestIdsItem) {
        if (this.requestIds == null) {
            this.requestIds = new ArrayList<>();
        }
        this.requestIds.add(requestIdsItem);
        return this;
    }

    /**
     * Идентификаторы запросов на участие в событии текущего пользователя
     *
     * @return requestIds
     */

    @Schema(name = "requestIds", example = "[1,2,3]", description = "Идентификаторы запросов на участие в событии текущего пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public List<Long> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<Long> requestIds) {
        this.requestIds = requestIds;
    }

    public EventRequestStatusUpdateRequest status(ParticipationRequestStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Новый статус запроса на участие в событии текущего пользователя
     *
     * @return status
     */

    @Schema(name = "status", example = "CONFIRMED", description = "Новый статус запроса на участие в событии текущего пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public ParticipationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipationRequestStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest = (EventRequestStatusUpdateRequest) o;
        return Objects.equals(this.requestIds, eventRequestStatusUpdateRequest.requestIds) &&
                Objects.equals(this.status, eventRequestStatusUpdateRequest.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIds, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EventRequestStatusUpdateRequest {\n");
        sb.append("    requestIds: ").append(toIndentedString(requestIds)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

