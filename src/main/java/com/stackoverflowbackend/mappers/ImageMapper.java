package com.stackoverflowbackend.mappers;

import com.stackoverflowbackend.dtos.ImageDto;
import com.stackoverflowbackend.models.Image;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {
    Image toEntity(ImageDto imageDto);

    ImageDto toDto(Image image);

}