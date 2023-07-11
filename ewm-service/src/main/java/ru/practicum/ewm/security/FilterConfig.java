package ru.practicum.ewm.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.service.UserService;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<UserIdFilter> filterRegistrationBean(UserService userService) {
        FilterRegistrationBean<UserIdFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new UserIdFilter(userService));
        filterRegistrationBean.addUrlPatterns("/users/*");
        return filterRegistrationBean;
    }
}
