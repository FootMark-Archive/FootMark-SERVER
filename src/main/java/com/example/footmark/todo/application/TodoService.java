package com.example.footmark.todo.application;

import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.CategoryReqDto;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.req.TodoReqDto;
import com.example.footmark.todo.api.dto.res.CategoryResDto;
import com.example.footmark.todo.api.dto.res.TodoDateResDto;
import com.example.footmark.todo.api.dto.res.TodoResDto;
import com.example.footmark.todo.api.dto.res.TodosResDto;
import com.example.footmark.todo.domain.Category;
import com.example.footmark.todo.domain.Todo;
import com.example.footmark.todo.domain.repository.CategoryRepository;
import com.example.footmark.todo.domain.repository.TodoRepository;
import com.example.footmark.todo.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        String color = categoryReqDto.color() != null ? categoryReqDto.color() : "black";
        return Category.builder()
                .categoryName(categoryReqDto.categoryName())
                .member(member)
                .color(color)
                .build();
    }

    private void checkForAlreadyExistCategory(String categoryName, Member member){
        Optional<Category> existingCategory = categoryRepository.findByMemberAndCategoryName(member, categoryName);
        if (existingCategory.isPresent()) {
            throw new ExistsCategoryNameException();
        }
    }

    @Transactional
    public CategoryResDto updateCategory(Long categoryId, CategoryReqDto categoryReqDto, Member member) {
        Category category = categoryRepository.findByCategoryIdAndMember(categoryId, member)
                .orElseThrow(CategoryMemberNotFoundException::new);

        category.update(categoryReqDto);

        return CategoryResDto.of(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId, Member member) {
        Category category = categoryRepository.findByCategoryIdAndMember(categoryId, member)
                .orElseThrow(CategoryMemberNotFoundException::new);

        todoRepository.deleteByCategory(category);

        categoryRepository.delete(category);
    }

    @Transactional
    public TodoResDto createTodo(TodoReqDto todoReqDto, Member member, Long categoryId){

        Category category = categoryRepository.findByCategoryIdAndMember(categoryId, member)
                .orElseThrow(CategoryMemberNotFoundException::new);
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

    public TodosResDto findAll(TodoDateReqDto todoDateReqDto, Member member) {
        return todoRepository.findAll(todoDateReqDto, member);
    }



    @Transactional
    public TodoResDto updateTodo(Long todoId, TodoReqDto todoReqDto, Member member){
        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        validateTodoMemberMatch(todo, member);

        todo.update(todoReqDto);
        return TodoResDto.of(todo);
    }

    private void validateTodoMemberMatch(Todo todo, Member member){
        if(!todo.getMember().getMemberId().equals(member.getMemberId())) {
            throw new TodoMemberMismatchException();
        }
    }

    @Transactional
    public void deleteTodo(Long todoId, Member member){
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(TodoNotFoundException::new);

        validateTodoMemberMatch(todo, member);

        todoRepository.delete(todo);
    }

    @Transactional
    public void updateTodoIsCompleted(Long todoId, Member member) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        validateTodoMemberMatch(todo, member);

        todo.updateIsCompleted();

        todoRepository.save(todo);
    }

}
