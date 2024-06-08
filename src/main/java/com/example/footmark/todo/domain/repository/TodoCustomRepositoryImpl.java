package com.example.footmark.todo.domain.repository;

import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.TodoDateReqDto;
import com.example.footmark.todo.api.dto.res.TodoDateResDto;
import com.example.footmark.todo.api.dto.res.TodosResDto;
import com.example.footmark.todo.domain.Category;
import com.example.footmark.todo.domain.Todo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.footmark.emoji.domain.QEmoji.emoji;
import static com.example.footmark.review.domain.QReview.review;
import static com.example.footmark.todo.domain.QTodo.todo;

@RequiredArgsConstructor
@Repository
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public TodosResDto findAll(TodoDateReqDto todoDateReqDto, Member member) {
        LocalDate startOfDay = todoDateReqDto.createAt();

        // 할 일 조회
        List<Todo> todos = queryFactory
                .selectFrom(todo)
                .where(todo.member.eq(member),
                        todo.createAt.eq(startOfDay))
                .fetch();

        // 일기 존재 여부 확인
        Boolean diaryExists = queryFactory
                .selectFrom(review)
                .where(review.member.eq(member),
                        review.createAt.eq(startOfDay))
                .fetchFirst() != null; //결과가 없으면 null을 반환합니다.

        // 해당 일자의 이모지 조회
        String todayEmoji = queryFactory
                .select(emoji.todayEmoji)
                .from(emoji)
                .where(emoji.member.eq(member),
                        emoji.createAt.eq(startOfDay))
                .fetchOne();

        List<TodoDateResDto> todoDateResDtos = todos.stream()
                .collect(Collectors.groupingBy(Todo::getCategory))
                .entrySet().stream()
                .map(entry -> {
                    Category category = entry.getKey();
                    List<Todo> categoryTodos = entry.getValue();
                    List<String> contents = categoryTodos.stream().map(Todo::getContent).collect(Collectors.toList());
                    List<Boolean> isCompletedList = categoryTodos.stream().map(Todo::getIsCompleted).collect(Collectors.toList());
                    List<Long> todoIds = categoryTodos.stream().map(Todo::getTodoId).collect(Collectors.toList());

                    return new TodoDateResDto(
                            category.getCategoryId(),
                            category.getCategoryName(),
                            contents,
                            isCompletedList,
                            todoIds
                    );
                })
                .collect(Collectors.toList());

        // 완료된 할 일 개수 계산
        long completedCount = todos.stream().filter(Todo::getIsCompleted).count();
        // 전체 할 일의 완료율 계산
        int completionRate = todos.isEmpty() ? 0 : (int) (((double) completedCount / todos.size()) * 100);

        return new TodosResDto(todoDateResDtos, completionRate, diaryExists, todayEmoji);
    }

}
