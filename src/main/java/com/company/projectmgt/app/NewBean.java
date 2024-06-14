package com.company.projectmgt.app;

import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

@Component
public class NewBean {
    private final DataManager dataManager;

    public NewBean(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}