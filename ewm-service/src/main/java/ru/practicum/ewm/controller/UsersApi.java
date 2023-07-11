/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.practicum.ewm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import ru.practicum.ewm.error.ApiError;
import ru.practicum.ewm.model.dto.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
@Validated
@Tag(name = "users", description = "Закрытый API для работы с событиями")
public interface UsersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users/{userId}/events : Добавление нового события
     * Обратите внимание: дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     *
     * @param userId      id текущего пользователя (required)
     * @param newEventDto данные добавляемого события (required)
     * @return Событие добавлено (status code 201)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не удовлетворяет правилам создания (status code 409)
     */
    @Operation(
            operationId = "addEvent",
            summary = "Добавление нового события",
            description = "Обратите внимание: дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Событие добавлено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Событие не удовлетворяет правилам создания", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/{userId}/events",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    default ResponseEntity<EventFullDto> addEvent(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "NewEventDto", description = "данные добавляемого события", required = true) @Valid @RequestBody NewEventDto newEventDto
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
     * POST /users/{userId}/requests : Добавление запроса от текущего пользователя на участие в событии
     * Обратите внимание: - нельзя добавить повторный запрос  (Ожидается код ошибки 409) - инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409) - нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409) - если у события достигнут лимит запросов на участие - необходимо вернуть ошибку  (Ожидается код ошибки 409) - если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Заявка создана (status code 201)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не найдено или недоступно (status code 404)
     * or Нарушение целостности данных (status code 409)
     */
    @Operation(
            operationId = "addParticipationRequest",
            summary = "Добавление запроса от текущего пользователя на участие в событии",
            description = "Обратите внимание: - нельзя добавить повторный запрос  (Ожидается код ошибки 409) - инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409) - нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409) - если у события достигнут лимит запросов на участие - необходимо вернуть ошибку  (Ожидается код ошибки 409) - если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Заявка создана", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipationRequestDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Событие не найдено или недоступно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Нарушение целостности данных", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/{userId}/requests",
            produces = {"application/json"}
    )
    default ResponseEntity<ParticipationRequestDto> addParticipationRequest(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @NotNull @Parameter(name = "eventId", description = "id события", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "eventId", required = true) Long eventId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /users/{userId}/requests/{requestId}/cancel : Отмена своего запроса на участие в событии
     *
     * @param userId    id текущего пользователя (required)
     * @param requestId id запроса на участие (required)
     * @return Заявка отменена (status code 200)
     * or Запрос не найден или недоступен (status code 404)
     */
    @Operation(
            operationId = "cancelRequest",
            summary = "Отмена своего запроса на участие в событии",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заявка отменена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipationRequestDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Запрос не найден или недоступен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/users/{userId}/requests/{requestId}/cancel",
            produces = {"application/json"}
    )
    default ResponseEntity<ParticipationRequestDto> cancelRequest(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "requestId", description = "id запроса на участие", required = true, in = ParameterIn.PATH) @PathVariable("requestId") Long requestId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /users/{userId}/events/{eventId}/requests : Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
     * Обратите внимание: - если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется - нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409) - статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409) - если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
     *
     * @param userId                          id текущего пользователя (required)
     * @param eventId                         id события текущего пользователя (required)
     * @param eventRequestStatusUpdateRequest Новый статус для заявок на участие в событии текущего пользователя (required)
     * @return Статус заявок изменён (status code 200)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не найдено или недоступно (status code 404)
     * or Достигнут лимит одобренных заявок (status code 409)
     */
    @Operation(
            operationId = "changeRequestStatus",
            summary = "Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя",
            description = "Обратите внимание: - если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется - нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409) - статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409) - если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус заявок изменён", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventRequestStatusUpdateResult.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Событие не найдено или недоступно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Достигнут лимит одобренных заявок", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/users/{userId}/events/{eventId}/requests",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    default ResponseEntity<EventRequestStatusUpdateResult> changeRequestStatus(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("eventId") Long eventId,
            @Parameter(name = "EventRequestStatusUpdateRequest", description = "Новый статус для заявок на участие в событии текущего пользователя", required = true) @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"confirmedRequests\" : [ { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }, { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" } ], \"rejectedRequests\" : [ { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }, { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId}/events/{eventId} : Получение полной информации о событии добавленном текущим пользователем
     * В случае, если события с заданным id не найдено, возвращает статус код 404
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Событие найдено (status code 200)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не найдено или недоступно (status code 404)
     */
    @Operation(
            operationId = "getEvent",
            summary = "Получение полной информации о событии добавленном текущим пользователем",
            description = "В случае, если события с заданным id не найдено, возвращает статус код 404",
            tags = {"Private: События"},
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
            value = "/users/{userId}/events/{eventId}",
            produces = {"application/json"}
    )
    default ResponseEntity<EventFullDto> getEvent(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true, in = ParameterIn.PATH) @PathVariable("eventId") Long eventId
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
     * GET /users/{userId}/events/{eventId}/requests : Получение информации о запросах на участие в событии текущего пользователя
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Найдены запросы на участие (status code 200)
     * or Запрос составлен некорректно (status code 400)
     */
    @Operation(
            operationId = "getEventParticipants",
            summary = "Получение информации о запросах на участие в событии текущего пользователя",
            description = "В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Найдены запросы на участие", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParticipationRequestDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/{userId}/events/{eventId}/requests",
            produces = {"application/json"}
    )
    default ResponseEntity<List<ParticipationRequestDto>> getEventParticipants(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true, in = ParameterIn.PATH) @PathVariable("eventId") Long eventId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }, { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId}/events : Получение событий, добавленных текущим пользователем
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     *
     * @param userId id текущего пользователя (required)
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора (optional, default to 0)
     * @param size   количество элементов в наборе (optional, default to 10)
     * @return События найдены (status code 200)
     * or Запрос составлен некорректно (status code 400)
     */
    @Operation(
            operationId = "getEvents",
            summary = "Получение событий, добавленных текущим пользователем",
            description = "В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список",
            tags = {"Private: События"},
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
            value = "/users/{userId}/events",
            produces = {"application/json"}
    )
    default ResponseEntity<List<EventShortDto>> getEvents(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Min(0) @Parameter(name = "from", description = "количество элементов, которые нужно пропустить для формирования текущего набора", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество элементов в наборе", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
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


    /**
     * GET /users/{userId}/requests : Получение информации о заявках текущего пользователя на участие в чужих событиях
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     *
     * @param userId id текущего пользователя (required)
     * @return Найдены запросы на участие (status code 200)
     * or Запрос составлен некорректно (status code 400)
     * or Пользователь не найден (status code 404)
     */
    @Operation(
            operationId = "getUserRequests",
            summary = "Получение информации о заявках текущего пользователя на участие в чужих событиях",
            description = "В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Найдены запросы на участие", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParticipationRequestDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/{userId}/requests",
            produces = {"application/json"}
    )
    default ResponseEntity<List<ParticipationRequestDto>> getUserRequests(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" }, { \"requester\" : 2, \"created\" : \"2022-09-06T21:10:05.432\", \"id\" : 3, \"event\" : 1, \"status\" : \"PENDING\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /users/{userId}/events/{eventId} : Изменение события добавленного текущим пользователем
     * Обратите внимание: - изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409) - дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409)
     *
     * @param userId                 id текущего пользователя (required)
     * @param eventId                id редактируемого события (required)
     * @param updateEventUserRequest Новые данные события (required)
     * @return Событие обновлено (status code 200)
     * or Запрос составлен некорректно (status code 400)
     * or Событие не найдено или недоступно (status code 404)
     * or Событие не удовлетворяет правилам редактирования (status code 409)
     */
    @Operation(
            operationId = "updateEvent",
            summary = "Изменение события добавленного текущим пользователем",
            description = "Обратите внимание: - изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409) - дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409) ",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие обновлено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Событие не найдено или недоступно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Событие не удовлетворяет правилам редактирования", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/users/{userId}/events/{eventId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    default ResponseEntity<EventFullDto> updateEvent(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id редактируемого события", required = true, in = ParameterIn.PATH) @PathVariable("eventId") Long eventId,
            @Parameter(name = "UpdateEventUserRequest", description = "Новые данные события", required = true) @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest
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

}