package com.example.footmark.todo.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TodoDateReqDto(

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
        LocalDate createAt
) {

}
