package ru.sberfuel.fuelsber.app.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import ru.sberfuel.fuelsber.app.entity.ColumnEntity;
import ru.sberfuel.fuelsber.app.entity.FuelEntity;
import ru.sberfuel.fuelsber.app.entity.FuelInColumnEntity;
import ru.sberfuel.fuelsber.app.entity.StationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DbDataGenerator {

    private Faker faker = new Faker();

    public List<StationEntity> getFakeStations() {

        Random random = new Random();


        List<StationEntity> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StationEntity station = new StationEntity();

            station.setDbId(faker.number().randomNumber());
            station.setId(faker.internet().uuid());
            station.setName(faker.company().name());
            station.setAddress(faker.address().fullAddress());
            station.setBrand(faker.company().industry());
            station.setBrandId(faker.idNumber().valid());
            station.setTakeOffMode(faker.options().option("auto", "manual"));
            station.setPostPay(faker.bool().bool());
            station.setEnable(faker.bool().bool());

            station.setOrgName(faker.company().name());
            station.setInn(faker.number().digits(12));
            station.setKpp(faker.number().digits(9));

            station.setLon(faker.number().randomDouble(6, -180, 180));
            station.setLat(faker.number().randomDouble(6, -90, 90));

            // Для списков, возможно, вам нужно будет создать отдельные сущности FuelEntity и ColumnEntity.
            // Пример создания и добавления одного FuelEntity. В реальном сценарии возможно потребуется цикл или логика для добавления нескольких.
            List<FuelEntity> fuels = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                var fuel = new FuelEntity();
                fuel.setId(faker.internet().uuid());
                fuel.setPrice(faker.number().randomDouble(2, 10, 100)); // Пример: от 10 до 100 с 2 знаками после запятой
                fuel.setType(faker.options().option("92", "95", "98", "ДТ")); // Пример: случайный выбор типа топлива
                fuel.setTypeId(faker.random().nextInt(1, 4)); // Пример: случайный выбор ID типа топлива
                fuel.setBrand(faker.company().name());
                fuel.setName(faker.lorem().word());
                fuels.add(fuel);
            }

            station.setFuels(fuels);

            List<ColumnEntity> columns = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                var column = new ColumnEntity();
                column.setColumnNumber(faker.options().option("1", "2", "3", "4"));
                List<FuelInColumnEntity> fuelInColumnEntities = new ArrayList<>();
                for (int g = 0; g < 5; g++) {
                    FuelInColumnEntity fuelInColumn = new FuelInColumnEntity();
                    int index = random.nextInt(fuels.size());
                    fuelInColumn.setFuel(fuels.get(index));
                    fuelInColumnEntities.add(fuelInColumn);
                }
                column.setFuels(fuelInColumnEntities);
                columns.add(column);
            }
            station.setColumns(columns);
            objects.add(station);
        }

        return objects;
    }
}
