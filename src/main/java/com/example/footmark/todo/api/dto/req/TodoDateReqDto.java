package com.example.footmark.todo.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TodoDateReqDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createAt
) {

}
