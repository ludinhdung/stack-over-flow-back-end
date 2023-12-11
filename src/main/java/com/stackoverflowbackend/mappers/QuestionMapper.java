package com.stackoverflowbackend.mappers;

import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.models.Question;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "tags", target = "tags")
    Question toEntity(QuestionDto questionDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "tags", target = "tags")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "user.username", target = "username")
    QuestionDto toDtoResponseSingle(Question questionDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    QuestionDto toDtoResponseCreate(Question question);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    QuestionDto toDtoResponseAll(Question question);
}
