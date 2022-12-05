package com.covid.analysis.app.config;

import com.covid.analysis.app.model.entities.CoronaHospitalization;
import com.covid.analysis.app.model.entities.OwidCovidData;
import com.covid.analysis.app.repository.entities.CoronaHospitalizationRepository;
import com.covid.analysis.app.repository.entities.OwidCovidDataRepository;
import io.swagger.annotations.Scope;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;


/**
 * This is a singleton class that retrieve
 * covid data on startup and store it in more
 *
 * This improves the system performance
 * Because by doing so, we avoid retrieving data from the DB
 *
 * */
@Component
@Scope(name = ConfigurableBeanFactory.SCOPE_PROTOTYPE, description = "Retrieve Corona Hospitalization Properties")
@Slf4j
public class RetrieveOwidCovidDataProp {

    private static List<OwidCovidData> list;
    @Autowired
    private OwidCovidDataRepository repository;

    @PostConstruct
    public void init() {
        log.info("Retrieving OwidCovidData ...");
        list = repository.findAll();
        log.info("Hospitalization List Size: {}", list.size());
    }

    public static List<OwidCovidData> getList() {
        return list;
    }

    public static void setList(List<OwidCovidData> list) {
        RetrieveOwidCovidDataProp.list = list;
    }

    public static List<OwidCovidData> getByYear(String year) {
        List<OwidCovidData> list=new ArrayList<>();
        for (OwidCovidData owidCovidData : list) {
            if(owidCovidData.getDate().contains(year)){
                list.add(owidCovidData);
            }
        }
        return list;
    }
}
