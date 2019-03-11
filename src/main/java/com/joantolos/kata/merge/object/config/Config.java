package com.joantolos.kata.merge.object.config;

import java.util.List;

public class Config {

    private Integer id;
    private String name;
    private Boolean enabled;
    private Integer[] clearances;
    private Default defaultDescription;
    private List<Default> descriptions;

    public Config() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer[] getClearances() {
        return clearances;
    }

    public Default getDefaultDescription() {
        return defaultDescription;
    }

    public List<Default> getDescriptions() {
        return descriptions;
    }
}
