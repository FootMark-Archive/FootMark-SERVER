package com.example.footmark.todo.domain.repository;

import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.res.TodosResDto;

public interface TodoCustomRepository {
    TodosResDto findAll(String createAt, Member member);
}
