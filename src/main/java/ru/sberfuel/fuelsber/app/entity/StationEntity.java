package ru.sberfuel.fuelsber.app.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "station")
@NoArgsConstructor
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dbId")
    private Long dbId;

    private String id;
    private String name;
    private String address;
    private String brand;
    private String brandId;
    private String takeOffMode;
    private Boolean postPay;
    private Boolean enable;

    //Решил схлопнуть таблицу
    private String orgName;
    private String Inn;
    private String Kpp;

    //Решил схлопнуть таблицу
    private Double Lon;
    private Double Lat;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuelEntity> fuels;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColumnEntity> columns;

}
