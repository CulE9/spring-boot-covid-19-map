package com.github.cule9.covid19map.model;

/**
 * Data from CSSEGISandData github
 * @see <a href="https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_time_series">CSSEGISandData</a>
 */
public enum DataURL {

    CONFIRMED_URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"),
    RECOVERED_URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv"),
    DEATHS_URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv");

    private final String url;

    DataURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
