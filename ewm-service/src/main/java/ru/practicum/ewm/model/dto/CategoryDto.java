package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Категория
 */

@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CategoryDto", description = "Категория")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class CategoryDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty("name")
    private String name;

    public CategoryDto id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Идентификатор категории
     *
     * @return id
     */

    @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "Идентификатор категории", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDto name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Название категории
     *
     * @return name
     */
    @NotNull
    @Size(min = 1, max = 50)
    @Schema(name = "name", example = "Концерты", description = "Название категории", requiredMode = Schema.RequiredMode.REQUIRED)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(this.id, categoryDto.id) &&
                Objects.equals(this.name, categoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CategoryDto {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

