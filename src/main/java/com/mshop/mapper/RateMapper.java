package com.mshop.mapper;

import com.mshop.dto.RateDTO;
import com.mshop.entity.Rate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RateMapper {
    RateDTO toDTO(Rate entity);

    List<RateDTO> toDTOS(List<Rate> entities);
}
