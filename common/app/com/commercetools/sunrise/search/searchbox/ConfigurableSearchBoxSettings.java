package com.commercetools.sunrise.search.searchbox;

import play.Configuration;

public class ConfigurableSearchBoxSettings extends SearchBoxSettingsImpl {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String DEFAULT_FIELD_NAME = "q";

    public ConfigurableSearchBoxSettings(final Configuration configuration) {
        super(configuration.getString(CONFIG_FIELD_NAME, DEFAULT_FIELD_NAME));
    }
}