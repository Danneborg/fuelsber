package ru.sberfuel.fuelsber.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Station {
    private String Id;
    private String Name;
    private String Address;
    private String Brand;
    private String BrandId;
    private String TakeOffMode;
    private Boolean PostPay;
    private Boolean Enable;

    private Location Location;
    private Organization Organization;
    private List<Fuel> Fuels;
    private Map<String, Column> Columns;

    @Override
    public String toString() {
        return "Station{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Brand='" + Brand + '\'' +
                ", BrandId='" + BrandId + '\'' +
                ", TakeOffMode='" + TakeOffMode + '\'' +
                ", PostPay=" + PostPay +
                ", Enable=" + Enable +
                ", Location=" + Location +
                ", Organization=" + Organization +
                ", Fuels=" + Fuels +
                ", Columns=" + Columns +
                '}';
    }
}








