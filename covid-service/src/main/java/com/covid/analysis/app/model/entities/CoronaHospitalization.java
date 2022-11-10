package com.covid.analysis.app.model.entities;

import com.covid.analysis.app.model.common.AbstractEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "corona_hospitalization", uniqueConstraints = {@UniqueConstraint(columnNames = "date")})
public class CoronaHospitalization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "date", length = 250)
    private String date;

    @Column(name = "are_hospitalized", length = 250)
    private String areHospitalized;

    @Column(name = "percentage_of_hospitalized_women", length = 250)
    private String hospitalizedWomenPercentage;

    @Column(name = "average_age_of_hospitalized_patients", length = 250)
    private String hospitalizedPatientsAveAge;

    @Column(name = "age_standard_deviation_from_hospitalized_patients", length = 250)
    private String ageStandardDeviationFromHospitalizedPatients;

    @Column(name = "breathing", length = 250)
    private String breathing;

    @Column(name = "percentage_of_women_breathing", length = 250)
    private String womenBreathingPercentage;

    @Column(name = "average_age_of_breathers", length = 250)
    private String breathersAveAge;

    @Column(name = "respiratory_age_standard_deviation", length = 250)
    private String respiratoryAgeStandardDeviation;

    @Column(name = "percentage_of_non_vaccinated_breathers", length = 250)
    private String nonVaccinatedBreathersPercentage;

    @Column(name = "easily_sick", length = 250)
    private String easilySick;

    @Column(name = "percentage_of_women_who_are_mildly_ill", length = 250)
    private String womenWhoAreMildlyIllPercentage;

    @Column(name = "percentage_of_mild_patients_not_vaccinated", length = 250)
    private String mildPatientsNotVaccinatedPercentage;

    @Column(name = "average_age_of_patients_is_easy", length = 250)
    private String averageAgeOfPatientsIsEasy;

    @Column(name = "patient_age_standard_deviation_is_slight", length = 250)
    private String patientAgeStandardDeviationIsSlight;

    @Column(name = "moderate_patients", length = 250)
    private String moderatePatients;

    @Column(name = "average_percentage_of_sick_women", length = 250)
    private String averagePercentageOfSickWomen;

    @Column(name = "a_moderate_percentage_of_patients_are_not_vaccinated", length = 250)
    private String moderatePercentageOfPatientsAreNotVaccinated;

    @Column(name = "average_age_of_patients_medium", length = 250)
    private String averageAgeOfPatientsMedium;

    @Column(name = "average_patient_age_standard_deviation", length = 250)
    private String averagePatientAgeStandardDeviation;

    @Column(name = "seriously_ill", length = 250)
    private String seriouslyIll;

    @Column(name = "percentage_of_seriously_ill_women", length = 250)
    private String percentageOfSeriouslyIllWomen;

    @Column(name = "percentage_of_seriously_ill_patients_not_vaccinated", length = 250)
    private String percentageOfSeriouslyIllPatientsNotVaccinated;

    @Column(name = "average_age_of_seriously_ill_patients", length = 250)
    private String averageAgeOfSeriouslyIllPatients;

    @Column(name = "patient_age_standard_deviation_is_severe", length = 250)
    private String patientAgeStandardDeviationIsSevere;

    @Column(name = "cumulatively_ill_patients", length = 250)
    private String cumulativelyIllPatients;

}

