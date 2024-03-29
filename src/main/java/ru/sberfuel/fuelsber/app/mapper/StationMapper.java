package ru.sberfuel.fuelsber.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.sberfuel.fuelsber.app.dto.Station;
import ru.sberfuel.fuelsber.app.entity.StationEntity;

@Mapper(componentModel = "spring")
@MapperConfig(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
        }
)
public interface StationMapper {

    @Mapping(target = "columns", ignore = true)
    @Mapping(target = "fuels", ignore = true)
    @Mapping(target = "orgName", source = "organization.name")
    @Mapping(target = "inn", source = "organization.inn")
    @Mapping(target = "kpp", source = "organization.kpp")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "lat", source = "location.lat")
    StationEntity stationToStationEntity(Station dto);

    @Mapping(target = "columns", ignore = true)
    @Mapping(target = "fuels", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "location", ignore = true)
    Station stationEntityToStation(StationEntity entity);
}
