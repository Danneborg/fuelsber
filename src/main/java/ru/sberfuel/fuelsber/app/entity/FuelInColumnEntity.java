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
//@Entity
//@Table(name = "FuelInColumn")
@NoArgsConstructor
public class FuelInColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dbId")
    private Long dbId;

    @OneToOne
    @JoinColumn(name = "fuel_id")
    private FuelEntity fuel;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private ColumnEntity column;


}
