package ru.sberfuel.fuelsber.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberfuel.fuelsber.app.logic.StationInfoUpdate;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/station/")
@RequiredArgsConstructor
public class StationController {

//    private final StationInfoUpdate stationInfoUpdate;

    @GetMapping("updateInfo")
    public void updateStationInfo() {
//        stationInfoUpdate.processData();
    }

}
