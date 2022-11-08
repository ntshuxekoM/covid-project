package com.covid.analysis.app.service;

import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.enums.ERole;
import com.covid.analysis.app.payload.CardData;
import com.covid.analysis.app.payload.Dataset;
import com.covid.analysis.app.payload.FuturePrediction;
import com.covid.analysis.app.payload.MonthData;
import com.covid.analysis.app.payload.UserDetails;
import com.covid.analysis.app.payload.VaccinationData;
import com.covid.analysis.app.payload.YearData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    Random rd = new Random();

    public boolean hasAdminRight(Set<Role> roles) {
        boolean isAdmin = false;
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                if (role.getName() == ERole.ROLE_ADMIN) {
                    isAdmin = true;
                    break;
                }
            }
        }
        return isAdmin;
    }

    public VaccinationData getVaccinationData() {
        VaccinationData data = new VaccinationData();

        data.setLabelList(
            Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
                "NOV", "DEC"));

        List<Dataset> datasetList = new ArrayList<>();
        datasetList.add(buildDataset("2019", "#008000"));
        datasetList.add(buildDataset("2020", "#0000000"));
        datasetList.add(buildDataset("2021", "#000080"));
        datasetList.add(buildDataset("2022", "#800080"));
        data.setDatasetList(datasetList);
        return data;
    }

    public FuturePrediction getFuturePrediction() {
        FuturePrediction prediction = new FuturePrediction();
        prediction.setLabelList(
            Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
                "NOV", "DEC"));

        List<Dataset> datasetList = new ArrayList<>();
        datasetList.add(buildDataset("2019", "#DFFF00"));
        datasetList.add(buildDataset("2020", "#fff"));
        datasetList.add(buildDataset("2021", "#008000"));
        datasetList.add(buildDataset("2022", "#FF00FF"));

        prediction.setDatasetList(datasetList);
        return prediction;
    }

    private Dataset buildDataset(String label, String color) {
        Dataset dataset = new Dataset();
        dataset.setLabel(label);
        dataset.setBackgroundColor(color);
        dataset.setBorderColor(color);
        dataset.setDataList(
            Arrays.asList(num(), num(), num(), num(), num(), num(), num(), num(), num(), num(),
                num(), num()));
        dataset.setFill(false);
        return dataset;
    }

    private int num() {
        int min = 10;
        int max = 100;
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public CardData getCasualties() {
        int min = 1000;
        int max = 10000000;
        CardData data = new CardData();
        data.setTotal((int) (Math.random() * (max - min + 1) + min));
        data.setPercentage("12");
        data.setPercentageTitle("Since yesterday");
        data.setPercentageIncreased(rd.nextBoolean());
        return data;
    }

    public CardData getRecoveries() {
        int min = 1000;
        int max = 10000000;
        CardData data = new CardData();
        data.setTotal((int) (Math.random() * (max - min + 1) + min));
        data.setPercentage("1.50");
        data.setPercentageTitle("Since yesterday");
        data.setPercentageIncreased(rd.nextBoolean());
        return data;
    }

    public CardData getRegisteredUser() {
        int min = 1000;
        int max = 10000000;
        CardData data = new CardData();
        data.setTotal((int) (Math.random() * (max - min + 1) + min));
        data.setPercentage("3.50");
        data.setPercentageTitle("Since last week");
        data.setPercentageIncreased(rd.nextBoolean());
        return data;
    }

    public CardData getAffected() {
        int min = 1000;
        int max = 10000000;
        CardData data = new CardData();
        data.setTotal((int) (Math.random() * (max - min + 1) + min));
        data.setPercentage("5,6");
        data.setPercentageTitle("Since last moth");
        data.setPercentageIncreased(rd.nextBoolean());
        return data;
    }

    private YearData buildYear() {
        int min = 10;
        int max = 101;
        YearData yearData = new YearData();
        yearData.setTitle("2021");
        List<MonthData> monthDataList = new ArrayList<>();
        MonthData monthData = new MonthData();
        monthData.setTitle("January");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("February");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("March");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("April");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("May");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("June");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("July");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("August");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("September");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("Octomer");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("November");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        monthData = new MonthData();
        monthData.setTitle("December");
        monthData.setTotal((int) (Math.random() * (max - min + 1) + min));
        monthDataList.add(monthData);

        yearData.setMonthDataList(monthDataList);
        return yearData;
    }


    public List<UserDetails> getRegisteredUserList() {
        List<UserDetails> list = new ArrayList<>();
        UserDetails user = new UserDetails();
        user.setId(1L);
        user.setName("Christoph");
        user.setSurname("Sibiya");
        user.setEmail("christophSibiya@gmail.com");
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        roles.add("USER");
        user.setRoles(roles);
        user.setIdNumber("9304246082082");
        user.setCellNumber("0729266076");
        list.add(user);

        user = new UserDetails();
        user.setId(2L);
        user.setName("Ntshuxeko");
        user.setSurname("Mabasa");
        user.setEmail("mabasanj@gmail.com");
        roles = new HashSet<>();
        roles.add("ADMIN");
        roles.add("USER");
        user.setRoles(roles);
        user.setIdNumber("9404246082059");
        user.setCellNumber("078566598");
        list.add(user);

        user = new UserDetails();
        user.setId(2L);
        user.setName("Xolani");
        user.setSurname("Mkhonto");
        user.setEmail("xolani@gmail.com");
        roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);
        user.setIdNumber("9504246082059");
        user.setCellNumber("078566505");
        list.add(user);

        user = new UserDetails();
        user.setId(2L);
        user.setName("Akani");
        user.setSurname("Makwakwa");
        user.setEmail("akani@gmail.com");
        roles = new HashSet<>();
        roles.add("ADMIN");
        user.setRoles(roles);
        user.setIdNumber("9304246082059");
        user.setCellNumber("078006505");
        list.add(user);

        user = new UserDetails();
        user.setId(2L);
        user.setName("John");
        user.setSurname("Manganyi");
        user.setEmail("johnmanganyi@gmail.com");
        roles = new HashSet<>();
        roles.add("ADMIN");
        user.setRoles(roles);
        user.setIdNumber("9204246082059");
        user.setCellNumber("078006500");
        list.add(user);

        user = new UserDetails();
        user.setId(2L);
        user.setName("Dunisani");
        user.setSurname("Hlongwane");
        user.setEmail("hlongwanedm@gmail.com");
        roles = new HashSet<>();
        roles.add("ADMIN");
        user.setRoles(roles);
        user.setIdNumber("9604246082059");
        user.setCellNumber("078006890");
        list.add(user);

        return list;
    }
}
