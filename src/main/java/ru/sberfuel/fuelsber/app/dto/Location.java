package ru.sberfuel.fuelsber.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private Double lon;
    private Double lat;

    @Override
    public String toString() {
        return "Location{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
