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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owid_covid_data", uniqueConstraints = {@UniqueConstraint(columnNames = "date")})
public class OwidCovidData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "continent")
    private String continent;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private String date;

    @Column(name = "total_cases")
    private String totalCases;

    @Column(name = "new_cases")
    private String newCases;

    @Column(name = "new_cases_smoothed")
    private String newCasesSmoothed;

    @Column(name = "total_deaths")
    private String totalDeaths;

    @Column(name = "new_deaths")
    private String newDeaths;

    @Column(name = "new_deaths_smoothed")
    private String newDeathsSmoothed;

    @Column(name = "total_cases_per_million")
    private String totalCasesPerMillion;

    @Column(name = "new_cases_per_million")
    private String newCasesPerMillion;

    @Column(name = "new_cases_smoothed_per_million")
    private String newCasesSmoothedPerMillion;

    @Column(name = "total_deaths_per_million")
    private String totalDeathsPerMillion;

    @Column(name = "new_deaths_per_million")
    private String newDeathsPerMillion;

    @Column(name = "new_deaths_smoothed_per_million")
    private String newDeathsSmoothedPerMillion;

    @Column(name = "reproduction_rate")
    private String reproductionRate;

    @Column(name = "icu_patients")
    private String icuPatients;

    @Column(name = "icu_patients_per_million")
    private String icuPatientsPerMillion;

    @Column(name = "hosp_patients")
    private String hospPatients;

    @Column(name = "hosp_patients_per_million")
    private String hospPatientsPerPillion;

    @Column(name = "weekly_icu_admissions")
    private String weeklyIcuAdmissions;

    @Column(name = "weekly_icu_admissions_per_million")
    private String weeklyIcuAdmissionsPerMillion;

    @Column(name = "weekly_hosp_admissions")
    private String weeklyHospAdmissions;

    @Column(name = "weekly_hosp_admissions_per_million")
    private String weeklyHospAdmissionsPerMillion;

    @Column(name = "total_tests")
    private String totalTests;

    @Column(name = "new_tests")
    private String newTests;

    @Column(name = "total_tests_per_thousand")
    private String totalTestsPerThousand;

    @Column(name = "new_tests_per_thousand")
    private String newTestsPerThousand;

    @Column(name = "new_tests_smoothed")
    private String newTestsSmoothed;

    @Column(name = "new_tests_smoothed_per_thousand")
    private String newTestsSmoothedPerThousand;

    @Column(name = "positive_rate")
    private String positiveRate;

    @Column(name = "tests_per_case")
    private String testsPerCase;

    @Column(name = "tests_units")
    private String testsUnits;

    @Column(name = "total_vaccinations")
    private String totalVaccinations;

    @Column(name = "people_vaccinated")
    private String peopleVaccinated;

    @Column(name = "people_fully_vaccinated")
    private String peopleFullyVaccinated;

    @Column(name = "total_boosters")
    private String totalBoosters;

    @Column(name = "new_vaccinations")
    private String newVaccinations;

    @Column(name = "new_vaccinations_smoothed")
    private String newVaccinationsSmoothed;

    @Column(name = "total_vaccinations_per_hundred")
    private String totalVaccinationsPerHundred;

    @Column(name = "people_vaccinated_per_hundred")
    private String peopleVaccinatedPerHundred;

    @Column(name = "people_fully_vaccinated_per_hundred")
    private String peopleFullyVaccinatedPerHundred;

    @Column(name = "total_boosters_per_hundred")
    private String totalBoostersPerHundred;

    @Column(name = "new_vaccinations_smoothed_per_million")
    private String newVaccinationsSmoothedPerMillion;

    @Column(name = "new_people_vaccinated_smoothed")
    private String newPeopleVaccinatedSmoothed;

    @Column(name = "new_people_vaccinated_smoothed_per_hundred")
    private String newPeopleVaccinatedSmoothedPerHundred;

    @Column(name = "stringency_index")
    private String stringencyIndex;

    @Column(name = "population_density")
    private String populationDensity;

    @Column(name = "median_age")
    private String medianAge;

    @Column(name = "aged_65_older")
    private String aged65Older;

    @Column(name = "aged_70_older")
    private String aged70Older;

    @Column(name = "gdp_per_capita")
    private String gdpPerPapita;

    @Column(name = "extreme_poverty")
    private String extremePoverty;

    @Column(name = "cardiovasc_death_rate")
    private String cardiovascDeathRate;

    @Column(name = "diabetes_prevalence")
    private String diabetesPrevalence;

    @Column(name = "female_smokers")
    private String femaleSmokers;

    @Column(name = "male_smokers")
    private String maleSmokers;

    @Column(name = "handwashing_facilities")
    private String handwashingFacilities;

    @Column(name = "hospital_beds_per_thousand")
    private String hospitalBedsPerPhousand;

    @Column(name = "life_expectancy")
    private String lifeExpectancy;

    @Column(name = "human_development_index")
    private String humanDevelopmentIndex;

    @Column(name = "population")
    private String population;

    @Column(name = "excess_mortality_cumulative_absolute")
    private String excessMortalityCumulativeAbsolute;

    @Column(name = "excess_mortality_cumulative")
    private String excessMortalityCumulative;

    @Column(name = "excess_mortality")
    private String excess_mortality;

    @Column(name = "excess_mortality_cumulative_per_million")
    private String excessMortalityCumulativePerMillion;

}

