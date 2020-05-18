package com.github.cule9.covid19map.service;

import com.github.cule9.covid19map.model.DataURL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Slf4j
public class CaseParser {

    public List<CSVRecord> getCaseData(DataURL dataUrl) {
        RestTemplate restTemplate = new RestTemplate();
        String restTemplateForObject = restTemplate.getForObject(dataUrl.getUrl(), String.class);

        if (restTemplateForObject != null) {
            StringReader stringReader = new StringReader(restTemplateForObject);
            try {
                return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader).getRecords();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }
}
