package com.example.footmark.todo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodo is a Querydsl query type for Todo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodo extends EntityPathBase<Todo> {

    private static final long serialVersionUID = -1126783414L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodo todo = new QTodo("todo");

    public final com.example.footmark.global.domain.QBaseTimeEntity _super = new com.example.footmark.global.domain.QBaseTimeEntity(this);

    public final QCategory category;

    public final StringPath content = createString("content");

    //inherited
    public final DatePath<java.time.LocalDate> createAt = _super.createAt;

    public final BooleanPath isCompleted = createBoolean("isCompleted");

    public final com.example.footmark.member.domain.QMember member;

    public final NumberPath<Long> todoId = createNumber("todoId", Long.class);

    public QTodo(String variable) {
        this(Todo.class, forVariable(variable), INITS);
    }

    public QTodo(Path<? extends Todo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodo(PathMetadata metadata, PathInits inits) {
        this(Todo.class, metadata, inits);
    }

    public QTodo(Class<? extends Todo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category"), inits.get("category")) : null;
        this.member = inits.isInitialized("member") ? new com.example.footmark.member.domain.QMember(forProperty("member")) : null;
    }

}

