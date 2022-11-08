package com.covid.analysis.app.payload;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationData implements Serializable {
    private List<Dataset> datasetList;
    private List<String> labelList;
}