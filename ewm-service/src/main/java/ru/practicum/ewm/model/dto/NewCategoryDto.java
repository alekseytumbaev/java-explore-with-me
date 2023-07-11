package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Данные для добавления новой категории
 */

@Schema(name = "NewCategoryDto", description = "Данные для добавления новой категории")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class NewCategoryDto {

    @JsonProperty("name")
    private String name;

    public NewCategoryDto name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Название категории
     *
     * @return name
     */
    @NotNull
    @NotBlank
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
        NewCategoryDto newCategoryDto = (NewCategoryDto) o;
        return Objects.equals(this.name, newCategoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewCategoryDto {\n");
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

