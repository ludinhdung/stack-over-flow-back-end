package com.stackoverflowbackend.mappers;

import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.models.Answer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "body", target = "body")
    Answer toEntity(AnswerDto answerDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "approve", target = "approve")
    AnswerDto toDtoResponseCreate(Answer answer);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.id", target = "userId")
    AnswerDto toDtoResponseAll(Answer answer);
}
