package com.example.footmark.todo.application;

import com.example.footmark.global.jwt.domain.CustomUserDetail;
import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.CategoryReqDto;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.req.TodoReqDto;
import com.example.footmark.todo.api.dto.res.CategoryResDto;
import com.example.footmark.todo.api.dto.res.TodoDateResDto;
import com.example.footmark.todo.api.dto.res.TodoResDto;
import com.example.footmark.todo.domain.Category;
import com.example.footmark.todo.domain.Todo;
import com.example.footmark.todo.domain.repository.CategoryRepository;
import com.example.footmark.todo.domain.repository.TodoRepository;
import com.example.footmark.todo.exception.CategoryMemberMismatchException;
import com.example.footmark.todo.exception.CategoryOrMemberNullException;
import com.example.footmark.todo.exception.ExistsCategoryNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public CategoryResDto createCategory(CategoryReqDto categoryReqDto, Member member){

        //이미 해당 멤버에 동일한 카테고리 있는지
        checkForAlreadyExistCategory(categoryReqDto.categoryName(), member);

        Category category = builderCategory(categoryReqDto,member);

        Category saveCategory = categoryRepository.save(category);

        return CategoryResDto.of(saveCategory);

    }

    private Category builderCategory(CategoryReqDto categoryReqDto, Member member){
        return Category.builder()
                .categoryName(categoryReqDto.categoryName())
                .member(member)
                .color(categoryReqDto.color())
                .build();
    }

    private void checkForAlreadyExistCategory(String categoryName, Member member){
        Optional<Category> existingCategory = categoryRepository.findByMemberAndCategoryName(member, categoryName);
        if (existingCategory.isPresent()) {
            throw new ExistsCategoryNameException();
        }
    }

    @Transactional
    public TodoResDto createTodo(TodoReqDto todoReqDto, Member member, Category category){
        //카테고리id의 멤버와 요청 멤버와 일치 해야함.
        validateCategoryMemberMatch(category, member);

        Todo todo = builderTodo(todoReqDto,member,category);

        Todo saveTodo = todoRepository.save(todo);

        return TodoResDto.of(saveTodo);

    }

    private Todo builderTodo(TodoReqDto todoReqDto, Member member, Category category){
        return Todo.builder()
                .isCompleted(false)
                .category(category)
                .member(member)
                .content(todoReqDto.content())
                .build();
    }

    private void validateCategoryMemberMatch(Category category, Member member){
        if(category == null || member == null) {
            throw new CategoryOrMemberNullException();
        }

        if(!category.getMember().getMemberId().equals(member.getMemberId())) {
            throw new CategoryMemberMismatchException();
        }
    }

    public List<TodoDateResDto> findAll(TodoDateReqDto todoDateReqDto, Member member) {
        LocalDate startOfDay = todoDateReqDto.createAt();
        LocalDate endOfDay = startOfDay.plusDays(1);

        // 데이터베이스에서 할일 목록 조회
        List<Todo> todos = todoRepository.findByMemberMemberIdAndCreateAtBetween(member.getMemberId(), startOfDay, endOfDay);

        // 조회한 할 일 목록을 TodoDateResDto로 변환하여 반환
        return todos.stream()
                .collect(Collectors.groupingBy(Todo::getCategory))
                .entrySet().stream()
                .map(entry -> new TodoDateResDto(
                        entry.getKey().getCategoryId(),
                        entry.getKey().getCategoryName(),
                        entry.getValue().stream().map(Todo::getContent).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
