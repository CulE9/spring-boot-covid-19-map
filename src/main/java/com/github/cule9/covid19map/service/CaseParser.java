package com.github.cule9.covid19map.service;

import com.github.cule9.covid19map.model.DataURL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class CaseParser {

    public List<CSVRecord> getCaseData(DataURL dataUrl) {

        RestTemplate restTemplate = new RestTemplate();
        String confirmedObject = restTemplate.getForObject(dataUrl.getUrl(), String.class);

        assert confirmedObject != null;
        StringReader confirmedStringReader = new StringReader(confirmedObject);

        try {
            return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(confirmedStringReader).getRecords();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
