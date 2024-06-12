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
import com.example.footmark.todo.api.dto.res.TodosResDto;
import com.example.footmark.todo.application.TodoService;
import com.example.footmark.todo.domain.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "category", description = "Category Controller")
@RestController
@RequestMapping("/category")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "카테고리 등록", description = "카테고리 등록합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping
    public RspTemplate<CategoryResDto> createCategory(@Valid @RequestBody CategoryReqDto categoryReqDto, @AuthenticationPrincipal CustomUserDetail member) {

        CategoryResDto categoryResDto = todoService.createCategory(categoryReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "카테고리 등록 성공", categoryResDto);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PutMapping("/{categoryId}")
    public RspTemplate<CategoryResDto> updateCategory(@Valid @RequestBody CategoryReqDto categoryReqDto, @AuthenticationPrincipal CustomUserDetail member, @PathVariable Long categoryId) {
        CategoryResDto categoryResDto = todoService.updateCategory(categoryId, categoryReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "카테고리 수정 성공", categoryResDto);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{categoryId}")
    public RspTemplate<Void> deleteCategory(@PathVariable Long categoryId,
                                            @AuthenticationPrincipal CustomUserDetail member) {

        todoService.deleteCategory(categoryId, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "카테고리 삭제 성공");
    }

    @Operation(summary = "할일 등록", description = "할일 등록합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{categoryId}/todo")
    public RspTemplate<TodoResDto> createTodo(@Valid @RequestBody TodoReqDto todoReqDto, @AuthenticationPrincipal CustomUserDetail member, @PathVariable Long categoryId) {

        TodoResDto todoResDto = todoService.createTodo(todoReqDto, member.getMember(),categoryId);

        return new RspTemplate<>(HttpStatus.OK, "할일 등록 성공", todoResDto);
    }

    @Operation(summary = "할일 조회", description = "당일 할일 조회합니다",parameters = {
            @Parameter(name = "createAt", description = "value = 조회 날짜, example = 2024-05-01")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/todos")
    public RspTemplate<TodosResDto> getTodos(@Valid @RequestParam String createAt, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        // 해당 멤버의 연, 월, 일로 검색
        TodosResDto todosResDto = todoService.findAll(createAt, customUserDetail.getMember());

        return new RspTemplate<>(HttpStatus.OK, "할일 조회 성공", todosResDto);
    }

    @Operation(summary = "할일 수정", description = "할일 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PutMapping("/todo/{todoId}")
    public RspTemplate<TodoResDto> updateTodo(@PathVariable Long todoId,
                                              @Valid @RequestBody TodoReqDto todoReqDto,
                                              @AuthenticationPrincipal CustomUserDetail member) {

        TodoResDto todoResDto = todoService.updateTodo(todoId, todoReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "할일 수정 성공", todoResDto);
    }

    @Operation(summary = "할일 삭제", description = "할일 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/todo/{todoId}")
    public RspTemplate<Void> deleteTodo(@PathVariable Long todoId,
                                        @AuthenticationPrincipal CustomUserDetail member){

        todoService.deleteTodo(todoId, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "할일 삭제 성공");
    }

    @Operation(summary = "할일 완료 유무", description = "할일 완료 유무 변경합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/todo-completion/{todoId}")
    public RspTemplate<Void> updateTodoCompletion(@PathVariable Long todoId,
                                                  @AuthenticationPrincipal CustomUserDetail member) {
        todoService.updateTodoIsCompleted(todoId, member.getMember());
        return new RspTemplate<>(HttpStatus.OK, "할일 완료 변경");
    }

}
