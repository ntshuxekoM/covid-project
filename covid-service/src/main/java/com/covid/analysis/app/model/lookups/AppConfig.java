package com.covid.analysis.app.model.lookups;
import com.covid.analysis.app.model.common.AbstractLookup;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_config")
public class AppConfig extends AbstractLookup {

}
