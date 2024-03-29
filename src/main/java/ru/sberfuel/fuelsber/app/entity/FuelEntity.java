package ru.sberfuel.fuelsber.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "fuel")
@NoArgsConstructor
public class FuelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dbId")
    private Long dbId;

    private String Id;
    private Double Price;
    private String Type;
    private Integer TypeId;
    private String Brand;
    private String Name;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @OneToOne(mappedBy = "fuel")
    private FuelInColumnEntity fuelInColumn;

}
