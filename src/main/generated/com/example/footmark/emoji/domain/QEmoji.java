package com.example.footmark.emoji.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmoji is a Querydsl query type for Emoji
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmoji extends EntityPathBase<Emoji> {

    private static final long serialVersionUID = -2752698L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmoji emoji = new QEmoji("emoji");

    public final DatePath<java.time.LocalDate> createAt = createDate("createAt", java.time.LocalDate.class);

    public final NumberPath<Long> emojiId = createNumber("emojiId", Long.class);

    public final com.example.footmark.member.domain.QMember member;

    public final StringPath todayEmoji = createString("todayEmoji");

    public QEmoji(String variable) {
        this(Emoji.class, forVariable(variable), INITS);
    }

    public QEmoji(Path<? extends Emoji> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmoji(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmoji(PathMetadata metadata, PathInits inits) {
        this(Emoji.class, metadata, inits);
    }

    public QEmoji(Class<? extends Emoji> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.footmark.member.domain.QMember(forProperty("member")) : null;
    }

}

