package com.covid.analysis.app.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owid_covid_data")
public class OwidCovidData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "iso_code", length = 5 )
    private String isoCode;

    @Column(name = "continent", length = 50 )
    private String continent;

    @Column(name = "location", length = 100 )
    private String location;

    @Column(name = "date", length = 50 )
    private String date;

    @Column(name = "total_cases" , length = 50 )
    private String totalCases;

    @Column(name = "new_cases" , length = 50 )
    private String newCases;

    @Column(name = "new_cases_smoothed" , length = 10 )
    private String newCasesSmoothed;

    @Column(name = "total_deaths" , length = 10 )
    private String totalDeaths;

    @Column(name = "new_deaths" , length = 10 )
    private String newDeaths;

    @Column(name = "new_deaths_smoothed" , length = 10 )
    private String newDeathsSmoothed;

    @Column(name = "total_cases_per_million" , length = 10 )
    private String totalCasesPerMillion;

    @Column(name = "new_cases_per_million" , length = 10 )
    private String newCasesPerMillion;

    @Column(name = "new_cases_smoothed_per_million" , length = 10 )
    private String newCasesSmoothedPerMillion;

    @Column(name = "total_deaths_per_million" , length = 10 )
    private String totalDeathsPerMillion;

    @Column(name = "new_deaths_per_million" , length = 10 )
    private String newDeathsPerMillion;

    @Column(name = "new_deaths_smoothed_per_million" , length = 10 )
    private String newDeathsSmoothedPerMillion;

    @Column(name = "reproduction_rate" , length = 100 )
    private String reproductionRate;

    @Column(name = "icu_patients" , length = 10 )
    private String icuPatients;

    @Column(name = "icu_patients_per_million" , length = 10 )
    private String icuPatientsPerMillion;

    @Column(name = "hosp_patients" , length = 10 )
    private String hospPatients;

    @Column(name = "hosp_patients_per_million" , length = 10 )
    private String hospPatientsPerPillion;

    @Column(name = "weekly_icu_admissions" , length = 10 )
    private String weeklyIcuAdmissions;

    @Column(name = "weekly_icu_admissions_per_million" , length = 10 )
    private String weeklyIcuAdmissionsPerMillion;

    @Column(name = "weekly_hosp_admissions" , length = 10 )
    private String weeklyHospAdmissions;

    @Column(name = "weekly_hosp_admissions_per_million" , length = 10 )
    private String weeklyHospAdmissionsPerMillion;

    @Column(name = "total_tests" , length = 10 )
    private String totalTests;

    @Column(name = "new_tests" , length = 10 )
    private String newTests;

    @Column(name = "total_tests_per_thousand" , length = 10 )
    private String totalTestsPerThousand;

    @Column(name = "new_tests_per_thousand" , length = 10 )
    private String newTestsPerThousand;

    @Column(name = "new_tests_smoothed" , length = 10 )
    private String newTestsSmoothed;

    @Column(name = "new_tests_smoothed_per_thousand" , length = 10 )
    private String newTestsSmoothedPerThousand;

    @Column(name = "positive_rate" , length = 10 )
    private String positiveRate;

    @Column(name = "tests_per_case" , length = 10 )
    private String testsPerCase;

    @Column(name = "tests_units" , length = 10 )
    private String testsUnits;

    @Column(name = "total_vaccinations" , length = 10 )
    private String totalVaccinations;

    @Column(name = "people_vaccinated" , length = 10 )
    private String peopleVaccinated;

    @Column(name = "people_fully_vaccinated" , length = 10 )
    private String peopleFullyVaccinated;

    @Column(name = "total_boosters" , length = 10 )
    private String totalBoosters;

    @Column(name = "new_vaccinations" , length = 10 )
    private String newVaccinations;

    @Column(name = "new_vaccinations_smoothed" , length = 10 )
    private String newVaccinationsSmoothed;

    @Column(name = "total_vaccinations_per_hundred" , length = 10 )
    private String totalVaccinationsPerHundred;

    @Column(name = "people_vaccinated_per_hundred" , length = 10 )
    private String peopleVaccinatedPerHundred;

    @Column(name = "people_fully_vaccinated_per_hundred" , length = 10 )
    private String peopleFullyVaccinatedPerHundred;

    @Column(name = "total_boosters_per_hundred" , length = 10 )
    private String totalBoostersPerHundred;

    @Column(name = "new_vaccinations_smoothed_per_million" , length = 10 )
    private String newVaccinationsSmoothedPerMillion;

    @Column(name = "new_people_vaccinated_smoothed" , length = 10 )
    private String newPeopleVaccinatedSmoothed;

    @Column(name = "new_people_vaccinated_smoothed_per_hundred" , length = 10 )
    private String newPeopleVaccinatedSmoothedPerHundred;

    @Column(name = "stringency_index" , length = 10 )
    private String stringencyIndex;

    @Column(name = "population_density" , length = 10 )
    private String populationDensity;

    @Column(name = "median_age" , length = 10 )
    private String medianAge;

    @Column(name = "aged_65_older" , length = 10 )
    private String aged65Older;

    @Column(name = "aged_70_older" , length = 10 )
    private String aged70Older;

    @Column(name = "gdp_per_capita" , length = 10 )
    private String gdpPerPapita;

    @Column(name = "extreme_poverty" , length = 10 )
    private String extremePoverty;

    @Column(name = "cardiovasc_death_rate" , length = 10 )
    private String cardiovascDeathRate;

    @Column(name = "diabetes_prevalence" , length = 10 )
    private String diabetesPrevalence;

    @Column(name = "female_smokers" , length = 10 )
    private String femaleSmokers;

    @Column(name = "male_smokers" , length = 10 )
    private String maleSmokers;

    @Column(name = "handwashing_facilities" , length = 10 )
    private String handwashingFacilities;

    @Column(name = "hospital_beds_per_thousand" , length = 10 )
    private String hospitalBedsPerPhousand;

    @Column(name = "life_expectancy" , length = 10 )
    private String lifeExpectancy;

    @Column(name = "human_development_index" , length = 10 )
    private String humanDevelopmentIndex;

    @Column(name = "population" , length = 10 )
    private String population;

    @Column(name = "excess_mortality_cumulative_absolute" , length = 10 )
    private String excessMortalityCumulativeAbsolute;

    @Column(name = "excess_mortality_cumulative" , length = 10 )
    private String excessMortalityCumulative;

    @Column(name = "excess_mortality" , length = 10 )
    private String excess_mortality;

    @Column(name = "excess_mortality_cumulative_per_million" , length = 10 )
    private String excessMortalityCumulativePerMillion;

}

