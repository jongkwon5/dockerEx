package com.example.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.example.demo.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class  UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long getNextId() {
        QUser user = QUser.user;

        Long maxId = queryFactory
                .select(user.id.max())
                .from(user)
                .fetchOne();

        return (maxId != null ? maxId : 0L) + 1;
    }

}
