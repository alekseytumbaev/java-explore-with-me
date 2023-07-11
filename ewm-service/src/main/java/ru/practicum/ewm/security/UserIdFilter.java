package ru.practicum.ewm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.error.ApiError;
import ru.practicum.ewm.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
public class UserIdFilter implements Filter {

    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /**
     * Checks if user id is in the database. If not, sends not found response
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        Optional<Long> userIdOpt = getUserId(requestUri);

        if (userIdOpt.isPresent() && !userService.existsById(userIdOpt.get())) {
            log.warn("User with id={} not found, cannot perform request '{}'", userIdOpt.get(), requestUri);
            sendError(response, userIdOpt.get());
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Retrieves user id from uris of pattern "/users/{userId}"
     * @return user id or empty optional if failed to retrieve id
     */
    private Optional<Long> getUserId(String uri) {
        String[] parts = uri.split("/");

        long userId;
        if (parts.length >= 3 && parts[1].equals("users")) {
            try {
                userId = Long.parseLong(parts[2]);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(userId);
    }

    private void sendError(ServletResponse response, long userId) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ApiError apiError = new ApiError(
                null,
                format("User with id=%d not found", userId),
                NOT_FOUND.getReasonPhrase(),
                NOT_FOUND,
                LocalDateTime.now()
        );
        String body = objectMapper.writeValueAsString(apiError);

        httpResponse.setStatus(NOT_FOUND.value());
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(body);
    }
}
