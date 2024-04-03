package com.example.footmark.todo.api;

import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.global.template.RspTemplate;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.res.TodoDateResDto;
import com.example.footmark.todo.application.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/sign-in")
    public RspTemplate<TodoDateResDto> signIn(@RequestBody TodoDateReqDto todoDateReqDto) {

        return new RspTemplate<>(HttpStatus.OK, "할일 조회 성공");
    }

}
