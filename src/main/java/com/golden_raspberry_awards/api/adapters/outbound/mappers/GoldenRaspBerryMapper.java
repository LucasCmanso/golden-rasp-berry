package com.golden_raspberry_awards.api.adapters.outbound.mappers;

import com.golden_raspberry_awards.api.adapters.inbound.DTO.GoldenRaspBerryDataDto;
import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = QuarkusMappingConfig.class)
public interface GoldenRaspBerryMapper {

    GoldenRaspBerryMapper INSTANCE = Mappers.getMapper(GoldenRaspBerryMapper.class);
    @Mapping(target = "id", ignore = true)
    GoldenRaspBerryData goldenRaspBerryDtoToData(GoldenRaspBerryDataDto dto);
}
