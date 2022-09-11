package com.covid.analysis.app.payload;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardData implements Serializable {

    private CardData affected;
    private CardData registeredUser;
    private CardData recoveries;
    private CardData casualties;
    private FuturePrediction futurePrediction;
    private VaccinationData vaccinationData;
    private List<UserDetails> registeredUserList;

}
