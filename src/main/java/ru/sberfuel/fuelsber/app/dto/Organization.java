package ru.sberfuel.fuelsber.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization {
    private String Name;
    private String Inn;
    private String Kpp;

    @Override
    public String toString() {
        return "Organization{" +
                "Name='" + Name + '\'' +
                ", Inn='" + Inn + '\'' +
                ", Kpp='" + Kpp + '\'' +
                '}';
    }
}
