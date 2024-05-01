package com.example.footmark.todo.api;

import com.example.footmark.global.jwt.domain.CustomUserDetail;
import com.example.footmark.global.template.RspTemplate;
import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.CategoryReqDto;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.req.TodoReqDto;
import com.example.footmark.todo.api.dto.res.CategoryResDto;
import com.example.footmark.todo.api.dto.res.TodoDateResDto;
import com.example.footmark.todo.api.dto.res.TodoResDto;
import com.example.footmark.todo.application.TodoService;
import com.example.footmark.todo.domain.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public RspTemplate<CategoryResDto> createCategory(@Valid @RequestBody CategoryReqDto categoryReqDto, @AuthenticationPrincipal CustomUserDetail member) {

        CategoryResDto categoryResDto = todoService.createCategory(categoryReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "카테고리 등록 성공", categoryResDto);
    }

    @PostMapping("/{categoryId}/todo")
    public RspTemplate<TodoResDto> createTodo(@Valid @RequestBody TodoReqDto todoReqDto, @AuthenticationPrincipal CustomUserDetail member, @PathVariable Category categoryId) {

        TodoResDto todoResDto = todoService.createTodo(todoReqDto, member.getMember(),categoryId);

        return new RspTemplate<>(HttpStatus.OK, "할일 등록 성공", todoResDto);
    }

    @GetMapping("/list") //카테고리id 반환해줘야함
    public RspTemplate<List<TodoDateResDto>> getTodolist(@Valid @RequestBody TodoDateReqDto todoDateReqDto, @AuthenticationPrincipal CustomUserDetail member) {

        //해당 멤버의 연 월 일로 검색
        List<TodoDateResDto> todoDateResDto = todoService.findAll(todoDateReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "할일 조회 성공",todoDateResDto);
    }

    //할일완료체크유무

}
