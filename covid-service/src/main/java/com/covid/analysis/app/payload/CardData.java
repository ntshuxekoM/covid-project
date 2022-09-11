package com.covid.analysis.app.payload;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardData implements Serializable {

    private int total;
    private String percentage;
    private String percentageTitle;
    private boolean percentageIncreased;

}