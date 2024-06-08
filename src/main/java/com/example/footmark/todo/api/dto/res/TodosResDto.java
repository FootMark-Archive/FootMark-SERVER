package com.example.footmark.todo.api.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class TodosResDto {
    List<TodoDateResDto> todoDateResDtos;
    int completionRate;
    Boolean diaryExists;
    String todayEmoji;

    public TodosResDto(List<TodoDateResDto> todoDateResDtos, int completionRate, Boolean diaryExists,  String todayEmoji) {
        this.todoDateResDtos = todoDateResDtos;
        this.completionRate = completionRate;
        this.diaryExists = diaryExists;
        this.todayEmoji = todayEmoji;
    }
}
