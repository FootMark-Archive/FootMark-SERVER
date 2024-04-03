package com.example.footmark.todo.domain;

import com.example.footmark.global.domain.BaseTimeEntity;
import com.example.footmark.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    @Schema(description = "할일 id", example = "1")
    private Long todoId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String content;

    @Column
    private Boolean isCompleted;

    @Builder
    public Todo(Long todoId, Member member, Category category, String content, Boolean isCompleted) {
        this.todoId = todoId;
        this.member = member;
        this.category = category;
        this.content = content;
        this.isCompleted = isCompleted;
    }

}
