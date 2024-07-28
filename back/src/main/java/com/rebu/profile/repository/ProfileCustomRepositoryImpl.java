package com.rebu.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rebu.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.rebu.profile.entity.QProfile.profile;

@Repository
@RequiredArgsConstructor
public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Profile findFirstByEmailOrderByRecentTimeDesc(String email) {
        return jpaQueryFactory.selectFrom(profile)
                .where(profile.member.email.eq(email))
                .orderBy(profile.recentTime.desc())
                .fetchFirst();
    }
}
