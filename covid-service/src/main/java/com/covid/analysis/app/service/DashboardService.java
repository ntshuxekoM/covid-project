package com.covid.analysis.app.service;

import com.covid.analysis.app.config.RetrieveCoronaHospitalizationProp;
import com.covid.analysis.app.config.RetrieveOwidCovidDataProp;
import com.covid.analysis.app.model.entities.CoronaHospitalization;
import com.covid.analysis.app.model.entities.OwidCovidData;
import com.covid.analysis.app.payload.CardData;
import com.covid.analysis.app.payload.Dataset;
import com.covid.analysis.app.payload.FuturePrediction;
import com.covid.analysis.app.payload.VaccinationData;
import com.covid.analysis.app.repository.entities.UserRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DashboardService {

    Random rd = new Random();
    private List<String> months = Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
        "AUG", "SEP", "OCT", "NOV", "DEC");
    @Autowired
    private UserRepository userRepository;


    /**
     * This method builds and return vaccination data
     */
    public VaccinationData getVaccinationData() {
        VaccinationData data = new VaccinationData();

        data.setLabelList(months);
        LocalDate oneYearAfter = LocalDate.now().plusYears(1);
        List<Dataset> datasetList = new ArrayList<>();
        for (int x = 2019; x <= oneYearAfter.getYear(); x++) {
            List<CoronaHospitalization> hospitalizationList =
                RetrieveCoronaHospitalizationProp.getHospitalizationListByYear(String.valueOf(x));
            datasetList.add(buildVaccinationDataset(String.valueOf(x), hospitalizationList, x,
                oneYearAfter.getYear()));
        }
        data.setDatasetList(datasetList);
        return data;
    }


    /**
     * This method build and returns future prediction data
     */
    public FuturePrediction getFuturePrediction() {
        FuturePrediction prediction = new FuturePrediction();
        prediction.setLabelList(months);

        List<Dataset> datasetList = new ArrayList<>();
        LocalDate oneYearAfter = LocalDate.now().plusYears(1);
        for (int x = 2019; x <= oneYearAfter.getYear(); x++) {
            List<CoronaHospitalization> hospitalizationList =
                RetrieveCoronaHospitalizationProp.getHospitalizationListByYear(String.valueOf(x));
            datasetList.add(buildFuturePredictionDataset(String.valueOf(x), hospitalizationList, x,
                oneYearAfter.getYear()));
        }

        prediction.setDatasetList(datasetList);
        return prediction;
    }


    /**
     * This method builds future prediction dataset using covid dataset Given a year, this method
     * will calculate and return the total number of people who were hospitalised on that year for
     * each month
     * <p>
     * If the year is next year, this method will predict the number of people who might be
     * hospitalised using the existing data
     */
    private Dataset buildFuturePredictionDataset(String label,
        List<CoronaHospitalization> hospitalizationList, int year, int nextYear) {
        String color = getColor();
        Dataset dataset = new Dataset();
        dataset.setLabel(label);

        if (year != nextYear) {
            dataset.setBackgroundColor(color);
            dataset.setBorderColor(color);
            dataset.setDataList(
                Arrays.asList(getTotalPerMonth(hospitalizationList, "01"),
                    getTotalPerMonth(hospitalizationList, "02"),
                    getTotalPerMonth(hospitalizationList, "03"),
                    getTotalPerMonth(hospitalizationList, "04"),
                    getTotalPerMonth(hospitalizationList, "05"),
                    getTotalPerMonth(hospitalizationList, "06"),
                    getTotalPerMonth(hospitalizationList, "07"),
                    getTotalPerMonth(hospitalizationList, "08"),
                    getTotalPerMonth(hospitalizationList, "09"),
                    getTotalPerMonth(hospitalizationList, "10"),
                    getTotalPerMonth(hospitalizationList, "11"),
                    getTotalPerMonth(hospitalizationList, "12")));
        } else {
            log.info("Next year prediction. Year {}, nextYear : {}", year, nextYear);
            dataset.setBackgroundColor("#2ECC71");
            dataset.setBorderColor("#2ECC71");
            dataset.setDataList(
                Arrays.asList(getAveragePerMonth("01"),
                    getAveragePerMonth("02"),
                    getAveragePerMonth("03"),
                    getAveragePerMonth("04"),
                    getAveragePerMonth("05"),
                    getAveragePerMonth("06"),
                    getAveragePerMonth("07"),
                    getAveragePerMonth("08"),
                    getAveragePerMonth("09"),
                    getAveragePerMonth("10"),
                    getAveragePerMonth("11"),
                    getAveragePerMonth("12")));
        }
        dataset.setFill(false);
        return dataset;
    }


    /**
     * This method is used to calculate the average number of people who are hospitalised per month
     * using existing covid data The average number is used to do future predictions
     */
    private int getAveragePerMonth(String month) {
        List<CoronaHospitalization> hospitalizationList =
            RetrieveCoronaHospitalizationProp.getHospitalizationList();
        int total = 0;
        for (CoronaHospitalization hospitalization : hospitalizationList) {
            try {
                if (hospitalization.getDate() != null && !hospitalization.getDate().isEmpty()) {
                    String dateElements[] = hospitalization.getDate().split("-");
                    if (dateElements != null && dateElements.length > 0) {
                        String hospitalisationMon = dateElements[1];
                        if (hospitalisationMon.equalsIgnoreCase(month)) {
                            total += Integer.parseInt(hospitalization.getAreHospitalized().trim());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error when getting total vaccinated: {}", e);
                e.printStackTrace();
            }
        }

        LocalDate oneYearAfter = LocalDate.now().plusYears(1);
        int count = 0;
        for (int x = 2019; x <= (oneYearAfter.getYear() - 1); x++) {
            count++;
        }
        log.info("Average Count: {}", count);
        return total / count;
    }

    /**
     * This method is used to calculate the total number of people who are hospitalised per month
     * using existing covid data The total number is used to calculate the average per month
     */

    private int getTotalPerMonth(List<CoronaHospitalization> hospitalizationList, String month) {
        int total = 0;
        for (CoronaHospitalization hospitalization : hospitalizationList) {
            try {
                if (hospitalization.getDate() != null && !hospitalization.getDate().isEmpty()) {
                    String dateElements[] = hospitalization.getDate().split("-");
                    if (dateElements != null && dateElements.length > 0) {
                        String hospitalisationMon = dateElements[1];
                        if (hospitalisationMon.equalsIgnoreCase(month)) {
                            total += Integer.parseInt(hospitalization.getAreHospitalized().trim());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error when getting total vaccinated: {}", e);
                e.printStackTrace();
            }
        }
        return total;
    }


    /**
     * This method builds vaccination dataset using covid dataset Given a year, this method will
     * calculate and return the total number of people who were vaccinated on that year for each
     * month
     * <p>
     * If the year is next year, this method will predict the number of people who might be
     * vaccinated using the existing data
     */
    private Dataset buildVaccinationDataset(String label,
        List<CoronaHospitalization> hospitalizationList, int year, int nextYear) {
        String color = getColor();
        Dataset dataset = new Dataset();
        dataset.setLabel(label);

        if (year != nextYear) {
            dataset.setBackgroundColor(color);
            dataset.setBorderColor(color);
            dataset.setDataList(
                Arrays.asList(getTotalVaccinated(hospitalizationList, "01"),
                    getTotalVaccinated(hospitalizationList, "02"),
                    getTotalVaccinated(hospitalizationList, "03"),
                    getTotalVaccinated(hospitalizationList, "04"),
                    getTotalVaccinated(hospitalizationList, "05"),
                    getTotalVaccinated(hospitalizationList, "06"),
                    getTotalVaccinated(hospitalizationList, "07"),
                    getTotalVaccinated(hospitalizationList, "08"),
                    getTotalVaccinated(hospitalizationList, "09"),
                    getTotalVaccinated(hospitalizationList, "10"),
                    getTotalVaccinated(hospitalizationList, "11"),
                    getTotalVaccinated(hospitalizationList, "12")));
        } else {
            dataset.setBackgroundColor("#2ECC71");
            dataset.setBorderColor("#2ECC71");
            dataset.setDataList(
                Arrays.asList(getAverageVaccinated("01"),
                    getAverageVaccinated("02"),
                    getAverageVaccinated("03"),
                    getAverageVaccinated("04"),
                    getAverageVaccinated("05"),
                    getAverageVaccinated("06"),
                    getAverageVaccinated("07"),
                    getAverageVaccinated("08"),
                    getAverageVaccinated("09"),
                    getAverageVaccinated("10"),
                    getAverageVaccinated("11"),
                    getAverageVaccinated("12")));
        }
        dataset.setFill(false);
        return dataset;
    }

    /**
     * Method to generate a random color String
     */
    public String getColor() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }


    /**
     * This method calculate the total number of people who got vaccinated per month
     */
    private int getTotalVaccinated(List<CoronaHospitalization> hospitalizationList, String month) {
        int total = 0;
        for (CoronaHospitalization hospitalization : hospitalizationList) {
            try {
                if (hospitalization.getDate() != null && !hospitalization.getDate().isEmpty()) {
                    String dateElements[] = hospitalization.getDate().split("-");
                    if (dateElements != null && dateElements.length > 0) {
                        //nonVaccinatedBreathersPercentage
                        String hospitalisationMon = dateElements[1];
                        if (hospitalisationMon.equalsIgnoreCase(month)) {
                            if (hospitalization.getNonVaccinatedBreathersPercentage() != null
                                && hospitalization.getAreHospitalized() != null) {
                                double nonVac = Double.parseDouble(
                                    hospitalization.getNonVaccinatedBreathersPercentage().trim());
                                int areHospitalized = Integer.parseInt(
                                    hospitalization.getAreHospitalized().trim());
                                total += areHospitalized - (areHospitalized * (nonVac / 100));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error when getting total vaccinated: {}", e);
                e.printStackTrace();
            }
        }
        return total;
    }


    /**
     * This method calculate the average of people who got vaccinated per month
     */
    private int getAverageVaccinated(String month) {
        List<CoronaHospitalization> hospitalizationList =
            RetrieveCoronaHospitalizationProp.getHospitalizationList();
        int total = 0;
        for (CoronaHospitalization hospitalization : hospitalizationList) {
            try {
                if (hospitalization.getDate() != null && !hospitalization.getDate().isEmpty()) {
                    String dateElements[] = hospitalization.getDate().split("-");
                    if (dateElements != null && dateElements.length > 0) {
                        //nonVaccinatedBreathersPercentage
                        String hospitalisationMon = dateElements[1];
                        if (hospitalisationMon.equalsIgnoreCase(month)) {
                            if (hospitalization.getNonVaccinatedBreathersPercentage() != null
                                && hospitalization.getAreHospitalized() != null) {
                                double nonVac = Double.parseDouble(
                                    hospitalization.getNonVaccinatedBreathersPercentage().trim());
                                int areHospitalized = Integer.parseInt(
                                    hospitalization.getAreHospitalized().trim());
                                total += areHospitalized - (areHospitalized * (nonVac / 100));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error when getting total vaccinated: {}", e);
                e.printStackTrace();
            }
        }

        LocalDate oneYearAfter = LocalDate.now().plusYears(1);
        int count = 0;
        for (int x = 2019; x <= (oneYearAfter.getYear() - 1); x++) {
            count++;
        }
        return total / count;
    }


    /**
     * This method build and returns the data of people who are affected by covid
     */
    public CardData getAffected() {
        LinkedList<OwidCovidData> list = new LinkedList<>(
            RetrieveOwidCovidDataProp.getList().stream()
                .sorted(Comparator.comparingLong(OwidCovidData::getId).reversed())
                .collect(Collectors.toList()));

        String lastDateYearAndMonth = getLastDateYearAndMonth(list);
        String prevYearAndMonth = getPrevYearAndMonth(list, lastDateYearAndMonth);

        int total = 0;
        int totalForCurrentMon = 0;
        int totalForPrevMon = 0;

        for (OwidCovidData owidCovidData : list) {
            try {

                if (owidCovidData.getNewCases() != null && !owidCovidData.getNewCases().trim()
                    .isEmpty() && isANumber(owidCovidData.getNewCases())) {
                    total += Integer.parseInt(owidCovidData.getNewCases().trim());
                    log.info("***************** New cases: {}, total: {}",
                        owidCovidData.getNewCases().trim(), total);

                    if (owidCovidData.getDate().contains(lastDateYearAndMonth)) {
                        totalForCurrentMon += Integer.parseInt(owidCovidData.getNewCases().trim());
                    }

                    if (owidCovidData.getDate().contains(prevYearAndMonth)) {
                        totalForPrevMon += Integer.parseInt(owidCovidData.getNewCases().trim());
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                log.error("Get Affected, {}", e.getMessage());
            }
        }

        log.info("***************** total: {}, totalForCurrentMon: {}, totalForPrevMon: {}", total,
            totalForCurrentMon, totalForPrevMon);

        CardData data = new CardData();
        data.setTotal(total);
        data.setPercentageIncreased(totalForCurrentMon > totalForPrevMon);

        if (totalForCurrentMon > 0 && totalForPrevMon > 0) {
            if (data.isPercentageIncreased()) {
                double num = totalForCurrentMon / totalForPrevMon;
                data.setPercentage(String.valueOf(num * 100));
            } else {
                double num = totalForPrevMon / totalForCurrentMon;
                data.setPercentage(String.valueOf(num * 100));
            }
        }
        data.setPercentageTitle("Since last month");

        log.info("***************** Card: {}", data);
        return data;
    }


    /**
     * Builds and return covid casualties/deaths data
     */
    public CardData getCasualties() {

        CardData data = new CardData();

        LinkedList<OwidCovidData> list = new LinkedList<>(
            RetrieveOwidCovidDataProp.getList().stream()
                .sorted(Comparator.comparingLong(OwidCovidData::getId).reversed())
                .collect(Collectors.toList()));

        log.info("First ID: {}", list.get(0).getId());

        String lastDateYearAndMonth = getLastDateYearAndMonth(list);
        String prevYearAndMonth = getPrevYearAndMonth(list, lastDateYearAndMonth);

        int total = 0;
        int totalForCurrentMon = 0;
        int totalForPrevMon = 0;
        for (OwidCovidData owidCovidData : list) {
            try {
                String deaths = owidCovidData.getTotalDeaths();
                if (deaths != null && !deaths.trim().isEmpty() && isANumber(deaths)) {
                    total += Integer.parseInt(deaths.trim());

                    if (owidCovidData.getDate().contains(lastDateYearAndMonth)) {
                        totalForCurrentMon += Integer.parseInt(deaths.trim());
                    }

                    if (owidCovidData.getDate().contains(prevYearAndMonth)) {
                        totalForPrevMon += Integer.parseInt(deaths.trim());
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                log.error("Get Casualties, {}", e.getMessage());
            }
        }

        data.setTotal(total);
        data.setPercentageIncreased(totalForCurrentMon > totalForPrevMon);

        if (totalForCurrentMon > 0 && totalForPrevMon > 0) {
            if (data.isPercentageIncreased()) {
                double num = totalForCurrentMon / totalForPrevMon;
                data.setPercentage(String.valueOf(num * 100));
            } else {
                double num = totalForPrevMon / totalForCurrentMon;
                data.setPercentage(String.valueOf(num * 100));
            }
        }
        data.setPercentageTitle("Since last month");

        return data;
    }


    /**
     * Given the year and month, this method returns the previous year and month from covid dataset
     * e.g given 2022-05, will return 2022-04
     */
    private static String getPrevYearAndMonth(LinkedList<OwidCovidData> list,
        String lastDateYearAndMonth) {

        String prevYearAndMonth = null;
        for (OwidCovidData owidCovidData : list) {
            if (owidCovidData.getDate() != null && !owidCovidData.getDate().trim().isEmpty()
                && !owidCovidData.getDate().contains(lastDateYearAndMonth)) {
                String[] elements = owidCovidData.getDate().trim().split("-");
                prevYearAndMonth = elements[0] + "-" + elements[1];
                log.info("Prev Month and Year : {}, prevYearAndMonth: {}", owidCovidData.getDate(),
                    prevYearAndMonth);
                break;
            }
        }
        return prevYearAndMonth;
    }

    /**
     * This method finds and return the last date (the latest date) from covid dataset
     */
    private static String getLastDateYearAndMonth(LinkedList<OwidCovidData> list) {
        String lastDateYearAndMonth = null;
        for (OwidCovidData owidCovidData : list) {
            log.info("*******ID: {}, Date: {}*******", owidCovidData.getId(),
                owidCovidData.getDate());
            if (owidCovidData.getDate() != null && !owidCovidData.getDate().trim().isEmpty()) {
                String[] lastDateElement = owidCovidData.getDate().trim().split("-");
                lastDateYearAndMonth = lastDateElement[0] + "-" + lastDateElement[1];
                log.info("Date: {}. Last Date Year and Month: {}", owidCovidData.getDate(),
                    lastDateYearAndMonth);
                break;
            }
        }
        return lastDateYearAndMonth;
    }

    private boolean isANumber(String value) {
        boolean isNum = true;
        if (value != null && !value.isEmpty()) {
            try {
                Integer.parseInt(value.trim());
            } catch (Exception e) {
                isNum = false;
            }
        }
        return isNum;
    }


    /**
     * Builds and return registered user data
     */
    public CardData getRegisteredUser() {
        CardData data = new CardData();
        data.setTotal((int) userRepository.count());

        LocalDate now = LocalDate.now(); // 2015-11-24
        LocalDate lastMont = now.minusMonths(1);

        long thisMonTotal = userRepository.countByCreatedDate(
            Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        long lastMonTotal = userRepository.countByCreatedDate(
            Date.from(lastMont.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        data.setPercentageTitle("Since last month");
        data.setPercentageIncreased(thisMonTotal > lastMonTotal);
        if (thisMonTotal > 0 && lastMonTotal > 0) {
            if (data.isPercentageIncreased()) {
                double num = thisMonTotal / lastMonTotal;
                data.setPercentage(String.valueOf(num * 100));
            } else {
                double num = lastMonTotal / thisMonTotal;
                data.setPercentage(String.valueOf(num * 100));
            }
        } else {
            data.setPercentage("100");
        }
        return data;
    }


    /**
     * Builds and return covid recoveries data
     * */
    public CardData getRecoveries() {
        //TODO get the data from covid dataset
        int min = 1000;
        int max = 10000000;
        CardData data = new CardData();
        data.setTotal((int) (Math.random() * (max - min + 1) + min));
        data.setPercentage("1.50");
        data.setPercentageTitle("Since yesterday");
        data.setPercentageIncreased(rd.nextBoolean());
        return data;
    }

}
