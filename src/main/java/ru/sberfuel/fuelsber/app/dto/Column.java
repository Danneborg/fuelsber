package ru.sberfuel.fuelsber.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Column {
    private List<String> Fuels;

    @Override
    public String toString() {
        return "Column{" +
                "Fuels=" + Fuels +
                '}';
    }
}
