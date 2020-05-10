package com.github.cule9.covid19map.service;

import com.github.cule9.covid19map.model.DataURL;
import com.github.cule9.covid19map.model.Point;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataParser {

    private final List<CSVRecord> confirmedRecords;
    private final List<CSVRecord> deathsRecords;
    private final List<CSVRecord> recoveredRecords;

    public DataParser() {
        this.confirmedRecords = new CaseParser().getCaseData(DataURL.CONFIRMED_URL);
        log.warn("confirmedRecords: " + confirmedRecords.size());
        this.deathsRecords = new CaseParser().getCaseData(DataURL.DEATHS_URL);
        log.warn("deathsRecords: " + deathsRecords.size());
        this.recoveredRecords = new CaseParser().getCaseData(DataURL.RECOVERED_URL);
        log.warn("recoveredRecords: " + recoveredRecords.size());
    }

    public List<Point> getCovidDataPoints() {
        List<Point> mapPoints = new ArrayList<>();
        for (CSVRecord confirmedRecord : confirmedRecords) {
            double latitude = Double.parseDouble(confirmedRecord.get("Lat"));
            double longitude = Double.parseDouble(confirmedRecord.get("Long"));
            String description = setPopupText(confirmedRecord, recoveredRecords, deathsRecords);
            mapPoints.add(new Point(latitude, longitude, description));
        }
        return mapPoints;
    }

    private String setPopupText(CSVRecord confirmedRecord, List<CSVRecord> recoveredRecords, List<CSVRecord> deathsRecords) {
        int recordNumber = (int) confirmedRecord.getRecordNumber() - 1;
        String newline = "<br>";
        int lastDay = confirmedRecord.size() - 1;

        StringBuilder popupText = new StringBuilder();
        String locationTitle = getLocationTitle(confirmedRecord);
        popupText.append(locationTitle);

        long confirmedCasesNumber = Long.parseLong(confirmedRecord.get(lastDay));
        popupText.append(newline).append("Confirmed: ").append(casesWithThousandsSeparator(confirmedCasesNumber));

        long deathsCasesNumber = Long.parseLong(deathsRecords.get(recordNumber).get(lastDay));
        String deathRateText = getRateText(confirmedCasesNumber, deathsCasesNumber);
        popupText.append(newline).append("Deaths: ").append(casesWithThousandsSeparator(deathsCasesNumber))
                .append(newline).append("Death rate: ").append(deathRateText);

        // incomplete data for recovery cases
        for (CSVRecord recoveredRecord : recoveredRecords) {
            if (areTheSameCoordinates(recoveredRecord, confirmedRecord)) {
                int recoveredRecordNumber = (int) recoveredRecord.getRecordNumber() - 1;
                int recoveredLastDay = recoveredRecord.size() - 1;
                long recoveredCasesNumber = Long.parseLong(recoveredRecords.get(recoveredRecordNumber).get(recoveredLastDay));
                String recoveryRateText = getRateText(confirmedCasesNumber, recoveredCasesNumber);
                String str = casesWithThousandsSeparator(recoveredCasesNumber);
                popupText.append(newline).append("Recovered: ").append(str)
                        .append(newline).append("Recovery rate: ").append(recoveryRateText);
                break;
            }
        }
        return popupText.toString();
    }

    private String getLocationTitle(CSVRecord confirmedRecord) {
        String provinanceOrState = confirmedRecord.get("Province/State");
        String countryOrRegion = confirmedRecord.get("Country/Region");
        String locationTitle = provinanceOrState.isEmpty() ? countryOrRegion : countryOrRegion + ": " + provinanceOrState;
        return String.format("<b>%s</b>", locationTitle).toUpperCase();
    }

    private String getRateText(long confirmedCasesNumber, long casesNumber) {
        double deathRateValue = (double) casesNumber / (double) confirmedCasesNumber * 100;
        return String.format("%.2f%%", deathRateValue);
    }

    private boolean areTheSameCoordinates(CSVRecord recoveredRecord, CSVRecord confirmedRecord) {
        String recoveredLatitude = recoveredRecord.get("Lat");
        String recoveredLongitude = recoveredRecord.get("Long");
        String confirmedLatitude = confirmedRecord.get("Lat");
        String confirmedLongitude = confirmedRecord.get("Long");
        return recoveredLatitude.equals(confirmedLatitude) && recoveredLongitude.equals(confirmedLongitude);
    }

    private String casesWithThousandsSeparator(long cases) {
        return String.format("%,d", cases);
    }
}
