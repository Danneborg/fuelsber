package ru.sberfuel.fuelsber.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.sberfuel.fuelsber.app.dto.Fuel;
import ru.sberfuel.fuelsber.app.entity.FuelEntity;

import java.util.List;

@Mapper(componentModel = "spring")
@MapperConfig(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FuelMapper {

    FuelEntity toFuelEntity(Fuel dto);

    Fuel toFuel(FuelEntity entity);

    List<FuelEntity> toFuelEntities(List<Fuel> dtos);

    List<Fuel> toFuels(List<FuelEntity> entities);
}
