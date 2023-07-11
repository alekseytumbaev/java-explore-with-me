/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.practicum.ewm.controller.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import ru.practicum.ewm.controller.ApiUtil;
import ru.practicum.ewm.error.ApiError;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.EventSort;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
@Validated
@Tag(name = "events", description = "Публичный API для работы с событиями")
public interface EventsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /events/{id} : Получение подробной информации об опубликованном событии по его идентификатору
     * Обратите внимание: - событие должно быть опубликовано - информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики  В случае, если события с заданным id не найдено, возвращает статус код 404
     *
     * @param id id события (required)
     * @return Событие найдено (status code 200)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не найдено или недоступно (status code 404)
     */
    @Operation(
            operationId = "getEventPublic",
            summary = "Получение подробной информации об опубликованном событии по его идентификатору",
            description = "Обратите внимание: - событие должно быть опубликовано - информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики  В случае, если события с заданным id не найдено, возвращает статус код 404",
            tags = {"Public: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Событие не найдено или недоступно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/events/{id}",
            produces = {"application/json"}
    )
    default ResponseEntity<EventFullDto> getEventPublic(
            @Parameter(name = "id", description = "id события", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"annotation\" : \"Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории\", \"initiator\" : { \"name\" : \"Фёдоров Матвей\", \"id\" : 3 }, \"description\" : \"Что получится, если соединить кукурузу и полёт? Создатели \"Шоу летающей кукурузы\" испытали эту идею на практике и воплотили в жизнь инновационный проект, предлагающий свежий взгляд на развлечения...\", \"publishedOn\" : \"2022-09-06 15:10:05\", \"title\" : \"Знаменитое шоу 'Летающая кукуруза'\", \"confirmedRequests\" : 5, \"createdOn\" : \"2022-09-06 11:00:23\", \"participantLimit\" : 10, \"paid\" : true, \"requestModeration\" : true, \"location\" : { \"lon\" : 37.62, \"lat\" : 55.754167 }, \"id\" : 1, \"state\" : \"PUBLISHED\", \"category\" : { \"name\" : \"Концерты\", \"id\" : 1 }, \"views\" : 999, \"eventDate\" : \"2024-12-31 15:10:05\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /events : Получение событий с возможностью фильтрации
     * Обратите внимание:  - это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события - текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв - если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени - информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики  В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     *
     * @param text          текст для поиска в содержимом аннотации и подробном описании события (optional)
     * @param categories    список идентификаторов категорий в которых будет вестись поиск (optional)
     * @param paid          поиск только платных/бесплатных событий (optional)
     * @param rangeStart    дата и время не раньше которых должно произойти событие (optional)
     * @param rangeEnd      дата и время не позже которых должно произойти событие (optional)
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие (optional, default to false)
     * @param sort          Вариант сортировки: по дате события или по количеству просмотров (optional)
     * @param from          количество событий, которые нужно пропустить для формирования текущего набора (optional, default to 0)
     * @param size          количество событий в наборе (optional, default to 10)
     * @return События найдены (status code 200)
     * or Запрос составлен некорректно (status code 400)
     */
    @Operation(
            operationId = "getEventsPublic",
            summary = "Получение событий с возможностью фильтрации",
            description = "Обратите внимание:  - это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события - текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв - если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени - информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики  В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список",
            tags = {"Public: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "События найдены", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EventShortDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/events",
            produces = {"application/json"}
    )
    default ResponseEntity<List<EventShortDto>> getEventsPublic(
            @Size(min = 1, max = 7000) @Parameter(name = "text", description = "текст для поиска в содержимом аннотации и подробном описании события", in = ParameterIn.QUERY) @Valid @RequestParam(value = "text", required = false) String text,
            @Parameter(name = "categories", description = "список идентификаторов категорий в которых будет вестись поиск", in = ParameterIn.QUERY) @Valid @RequestParam(value = "categories", required = false) List<Long> categories,
            @Parameter(name = "paid", description = "поиск только платных/бесплатных событий", in = ParameterIn.QUERY) @Valid @RequestParam(value = "paid", required = false) Boolean paid,
            @Parameter(name = "rangeStart", description = "дата и время не раньше которых должно произойти событие", in = ParameterIn.QUERY) @Valid @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeStart,
            @Parameter(name = "rangeEnd", description = "дата и время не позже которых должно произойти событие", in = ParameterIn.QUERY) @Valid @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeEnd,
            @Parameter(name = "onlyAvailable", description = "только события у которых не исчерпан лимит запросов на участие", in = ParameterIn.QUERY) @Valid @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
            @Parameter(name = "sort", description = "Вариант сортировки: по дате события или по количеству просмотров", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort", required = false) EventSort sort,
            @Min(0) @Parameter(name = "from", description = "количество событий, которые нужно пропустить для формирования текущего набора", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество событий в наборе", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ [ { \"annotation\" : \"Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории\", \"category\" : { \"id\" : 1, \"name\" : \"Концерты\" }, \"confirmedRequests\" : 5, \"eventDate\" : \"2024-03-10 14:30:00\", \"id\" : 1, \"initiator\" : { \"id\" : 3, \"name\" : \"Фёдоров Матвей\" }, \"paid\" : true, \"title\" : \"Знаменитое шоу 'Летающая кукуруза'\", \"views\" : 999 }, { \"annotation\" : \"За почти три десятилетия группа 'Java Core' закрепились на сцене как группа, объединяющая поколения.\", \"category\" : { \"id\" : 1, \"name\" : \"Концерты\" }, \"confirmedRequests\" : 555, \"eventDate\" : \"2025-09-13 21:00:00\", \"id\" : 1, \"initiator\" : { \"id\" : 3, \"name\" : \"Паша Петров\" }, \"paid\" : true, \"title\" : \"Концерт рок-группы 'Java Core'\", \"views\" : 991 } ], [ { \"annotation\" : \"Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории\", \"category\" : { \"id\" : 1, \"name\" : \"Концерты\" }, \"confirmedRequests\" : 5, \"eventDate\" : \"2024-03-10 14:30:00\", \"id\" : 1, \"initiator\" : { \"id\" : 3, \"name\" : \"Фёдоров Матвей\" }, \"paid\" : true, \"title\" : \"Знаменитое шоу 'Летающая кукуруза'\", \"views\" : 999 }, { \"annotation\" : \"За почти три десятилетия группа 'Java Core' закрепились на сцене как группа, объединяющая поколения.\", \"category\" : { \"id\" : 1, \"name\" : \"Концерты\" }, \"confirmedRequests\" : 555, \"eventDate\" : \"2025-09-13 21:00:00\", \"id\" : 1, \"initiator\" : { \"id\" : 3, \"name\" : \"Паша Петров\" }, \"paid\" : true, \"title\" : \"Концерт рок-группы 'Java Core'\", \"views\" : 991 } ] ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}