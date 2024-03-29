package ru.sberfuel.fuelsber.app.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.sberfuel.fuelsber.app.dto.Station;
import ru.sberfuel.fuelsber.app.entity.ColumnEntity;
import ru.sberfuel.fuelsber.app.entity.FuelEntity;
import ru.sberfuel.fuelsber.app.entity.FuelInColumnEntity;
import ru.sberfuel.fuelsber.app.entity.StationEntity;
import ru.sberfuel.fuelsber.app.mapper.FuelMapper;
import ru.sberfuel.fuelsber.app.mapper.StationMapper;
import ru.sberfuel.fuelsber.app.service.DbDataGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StationInfoUpdate implements ApplicationRunner {

    private final DbDataGenerator dbDataGenerator;
    private final StationMapper stationMapper;
    private final FuelMapper fuelMapper;

    /**
     * Это метод заглушка, который демонстрирует работу получения данных по http, сравнивает их с данными из БД,
     * а затем вносит изменения
     */
    @Override
    public void run(ApplicationArguments arg0) {

        List<Station> stations = getStationsFromHttp();
        if (CollectionUtils.isEmpty(stations)) {
            System.out.println("Данные по HTTP не получены. Сравнивать нечего, данные в БД не изменились!");
        } else {

            processData(stations);

        }
        System.exit(-1);

    }

    private List<Station> getStationsFromHttp() {

        try {
            URL url = new URL("https://aggregator.api.test.fuelup.ru/v2/station?apikey=uwvgx6sx47bednyy7lo6ghq56jbw8wpb");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Station>>() {}.getType();

            return gson.fromJson(response.toString(), listType);

        } catch (IOException e) {
            System.out.println("Произошла ошибка во время получения данных по HTTP : " + e.getMessage());
            return Collections.emptyList();
        }

    }

    public void processData(List<Station> stations) {

        //Создаем себе фейковые данные из БД
        List<StationEntity> stationEntities = dbDataGenerator.getFakeStations();

        stations.get(0).setId(stationEntities.get(0).getId());
        stations.get(5).setId(stationEntities.get(5).getId());
        stations.get(8).setId(stationEntities.get(8).getId());

        var fakeEntitiesToUuid = stationEntities.stream()
                .collect(Collectors.toMap(StationEntity::getId, Function.identity()));

        stations.forEach(elem -> {

            if (fakeEntitiesToUuid.containsKey(elem.getId())) {
                findDelta(elem, fakeEntitiesToUuid.get(elem.getId()));
            } else {
                createNewEntity(elem);
            }
        });

    }

    private void createNewEntity(Station elem){
        var stationEntity = stationMapper.stationToStationEntity(elem);

        var fuelsToId = fuelMapper.toFuelEntities(elem.getFuels()).stream()
                .collect(Collectors.toMap(FuelEntity::getId, Function.identity()));

        if (CollectionUtils.isEmpty(elem.getColumns())) {
            //TODO бросить варнинг
        } else {
            var columns = elem.getColumns().entrySet().stream().map(singleColumn -> {
                        var column = new ColumnEntity();
                        column.setColumnNumber(singleColumn.getKey());

                        var fuelsInColumn = singleColumn.getValue().getFuels()
                                .stream()
                                .filter(fuelsToId::containsKey)
                                .map(singleFuelCode -> {
                                    var fuelInColumn = new FuelInColumnEntity();
                                    fuelInColumn.setFuel(fuelsToId.get(singleFuelCode));
                                    return fuelInColumn;
                                })
                                .collect(Collectors.toList());

                        column.setFuels(fuelsInColumn);
                        return column;
                    })
                    .collect(Collectors.toList());

            stationEntity.setFuels(fuelsToId.values().stream().toList());
            stationEntity.setColumns(columns);
        }
        System.out.println("Сохраняем новую АЗС : " + stationEntity);
        //TODO сохранить сущность
    }

    private void findDelta(Station dtoNewInfo, StationEntity entityToUpdate) {
        boolean updateRequired = false;
        var rootWithDelta = new StationEntity();
        //Нужно проверить есть ли разница между пришедшим ДТО и тем, что есть в базе
        //Очень топорная логика сравнения, надо бы делать через рефлексию, но это требует долгой отладки

        if (stringPropertiesHaveDifference(dtoNewInfo.getName(), entityToUpdate.getName())) {
            rootWithDelta.setName(dtoNewInfo.getName());
            updateRequired = true;
        }

        if (stringPropertiesHaveDifference(dtoNewInfo.getAddress(), entityToUpdate.getAddress())) {
            rootWithDelta.setAddress(dtoNewInfo.getAddress());
            updateRequired = true;
        }

        if (stringPropertiesHaveDifference(dtoNewInfo.getBrand(), entityToUpdate.getBrand())) {
            rootWithDelta.setBrand(dtoNewInfo.getBrand());
            updateRequired = true;
        }

        if (stringPropertiesHaveDifference(dtoNewInfo.getBrandId(), entityToUpdate.getBrandId())) {
            rootWithDelta.setBrandId(dtoNewInfo.getBrandId());
            updateRequired = true;
        }

        if (stringPropertiesHaveDifference(dtoNewInfo.getTakeOffMode(), entityToUpdate.getTakeOffMode())) {
            rootWithDelta.setTakeOffMode(dtoNewInfo.getTakeOffMode());
            updateRequired = true;
        }

        if (boolPropertiesHaveDifference(dtoNewInfo.getPostPay(), entityToUpdate.getPostPay())) {
            rootWithDelta.setPostPay(dtoNewInfo.getPostPay());
            updateRequired = true;
        }

        if (boolPropertiesHaveDifference(dtoNewInfo.getEnable(), entityToUpdate.getEnable())) {
            rootWithDelta.setEnable(dtoNewInfo.getEnable());
            updateRequired = true;
        }

        if (dtoNewInfo.getOrganization() != null) {
            if (stringPropertiesHaveDifference(dtoNewInfo.getOrganization().getInn(), entityToUpdate.getInn())) {
                rootWithDelta.setInn(dtoNewInfo.getOrganization().getInn());
                updateRequired = true;
            }
            if (stringPropertiesHaveDifference(dtoNewInfo.getOrganization().getName(), entityToUpdate.getOrgName())) {
                rootWithDelta.setOrgName(dtoNewInfo.getOrganization().getName());
                updateRequired = true;
            }
            if (stringPropertiesHaveDifference(dtoNewInfo.getOrganization().getKpp(), entityToUpdate.getKpp())) {
                rootWithDelta.setKpp(dtoNewInfo.getOrganization().getKpp());
                updateRequired = true;
            }
        }

        if (dtoNewInfo.getLocation() != null) {
            if (longPropertiesHaveDifference(dtoNewInfo.getLocation().getLon(), entityToUpdate.getLon())) {
                rootWithDelta.setLon(dtoNewInfo.getLocation().getLon());
                updateRequired = true;
            }
            if (longPropertiesHaveDifference(dtoNewInfo.getLocation().getLat(), entityToUpdate.getLat())) {
                rootWithDelta.setLat(dtoNewInfo.getLocation().getLat());
                updateRequired = true;
            }
        }

        if (updateRequired) {
            rootWithDelta.setDbId(entityToUpdate.getDbId());
            rootWithDelta.setId(entityToUpdate.getId());
            System.out.println("====================");
            System.out.println("Найдено различие! АЗС UUID : " + entityToUpdate.getId());
            System.out.println("Содержимое : " + rootWithDelta);
            System.out.println("====================");
            //TODO вызвать апдейт корня
        }

        //Что если топлива нет, но есть колонки? Они будут иметь пустые массивы? Много белых пятен
        if (CollectionUtils.isEmpty(entityToUpdate.getFuels())
                && !CollectionUtils.isEmpty(entityToUpdate.getFuels())) {
            //TODO Просто создать новые типы топлива
        } else {
            //TODO Ищем дельту
        }

        //Ничего не сказано о том, могут ли колонки быть, а потом пропасть, что в таком случае делать?
        if (CollectionUtils.isEmpty(entityToUpdate.getColumns())
                && !CollectionUtils.isEmpty(entityToUpdate.getColumns())) {
            //TODO Просто создать новые колонки
        } else {
            //TODO Ищем дельту
        }

    }

    private boolean stringPropertiesHaveDifference(String dtoProp, String entityProp) {

        /**
         В документе явно не сказано как поступать с null значениями при обновлении.
         Будем считать, то такое поле является пустым и не записывается в бд при обновлении.
         */
        if (!StringUtils.hasText(dtoProp)) {
            return false;
        }

        return !Objects.equals(dtoProp, entityProp);

    }

    private boolean longPropertiesHaveDifference(Double dtoProp, Double entityProp) {

        /**
         В документе явно не сказано как поступать с null значениями при обновлении.
         Будем считать, то такое поле является пустым и не записывается в бд при обновлении.
         */
        if (dtoProp == null) {
            return false;
        }

        return Double.compare(dtoProp, entityProp) != 0;

    }

    private boolean boolPropertiesHaveDifference(Boolean dtoProp, Boolean entityProp) {

        /**
         В документе явно не сказано как поступать с null значениями при обновлении.
         Будем считать, то такое поле является пустым и не записывается в бд при обновлении.
         */
        if (dtoProp == null) {
            return false;
        }

        return !Objects.equals(dtoProp, entityProp);

    }
}
