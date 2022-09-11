package com.covid.analysis.app.payload;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationData implements Serializable {

    private YearData year1;
    private YearData year2;

}