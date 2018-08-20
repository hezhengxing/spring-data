package com.example.data.repository;

import com.example.data.entity.CaseInfo;
import com.example.data.entity.Person;
import com.example.data.entity.QPerson;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,String> ,QuerydslBinderCustomizer<QPerson>,QuerydslPredicateExecutor<CaseInfo>{
    @Override
    default void customize(final QuerydslBindings bindings, final QPerson root) {
        bindings.bind(String.class).first((StringPath path, String value)-> path.like("%".concat(StringUtils.trim(value))));
    }
}
