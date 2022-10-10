package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public <T> ResourceNotFoundException(String sourceName, String fieldName, T valueName) {
        super(String.format("%s with {%s=%s} not found", sourceName, fieldName, valueName));
    }

    public <T> ResourceNotFoundException(String sourceName, Map<String, Object> fieldNameMap) {
        super(String.format("%s with %s not found", sourceName, fieldNameMap.keySet().stream()
                .map(key -> key + "=" + fieldNameMap.get(key))
                .collect(Collectors.joining(", ", "{", "}"))));
    }

}
