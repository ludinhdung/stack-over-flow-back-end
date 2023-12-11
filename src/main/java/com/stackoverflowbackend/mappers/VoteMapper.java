package com.stackoverflowbackend.mappers;

import com.stackoverflowbackend.dtos.VoteDto;
import com.stackoverflowbackend.models.Vote;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "type", target = "type")
    Vote toEntity(VoteDto voteDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    VoteDto toDtoResponseCreate(Vote vote);

    VoteDto toDto(Vote vote);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Vote partialUpdate(VoteDto voteDto, @MappingTarget Vote vote);
}