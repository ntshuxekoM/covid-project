package com.covid.analysis.app.config;

import com.covid.analysis.app.model.entities.CoronaHospitalization;
import com.covid.analysis.app.repository.entities.CoronaHospitalizationRepository;
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
public class RetrieveCoronaHospitalizationProp {

    private static List<CoronaHospitalization> hospitalizationList;
    @Autowired
    private CoronaHospitalizationRepository repository;

    @PostConstruct
    public void init() {
        hospitalizationList = repository.findAll();
        log.info("Hospitalization List Size: {}", hospitalizationList.size());
    }

    public static List<CoronaHospitalization> getHospitalizationList() {
        return hospitalizationList;
    }

    public static List<CoronaHospitalization> getHospitalizationListByYear(String year) {
        List<CoronaHospitalization> list=new ArrayList<>();
        for (CoronaHospitalization coronaHospitalization : hospitalizationList) {
            if(coronaHospitalization.getDate().contains(year)){
                list.add(coronaHospitalization);
            }
        }
        return list;
    }
}
