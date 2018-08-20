package com.example.data.repository;

import com.example.data.entity.CaseInfo;
import com.example.data.entity.QCaseInfo;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseInfoRepository extends JpaRepository<CaseInfo,String> ,QuerydslPredicateExecutor<CaseInfo> ,QuerydslBinderCustomizer<QCaseInfo>{
    @Override
    default void customize(final QuerydslBindings bindings, final QCaseInfo root) {
        bindings.bind(String.class).first((StringPath path,String value)-> path.like("%".concat(StringUtils.trim(value))));
        bindings.bind(root.personInfo.name).first((path, value) -> path.eq(StringUtils.trim(value)));
    }
}
