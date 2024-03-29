package ru.sberfuel.fuelsber.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fuel {
    private Long dbId;
    private String Id;
    private Double Price;
    private String Type;
    private Integer TypeId;
    private String Brand;
    private String Name;

    @Override
    public String toString() {
        return "Fuel{" +
                "dbId=" + dbId +
                ", Id='" + Id + '\'' +
                ", Price=" + Price +
                ", Type='" + Type + '\'' +
                ", TypeId=" + TypeId +
                ", Brand='" + Brand + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
