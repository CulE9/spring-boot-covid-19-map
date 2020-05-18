package com.github.cule9.covid19map.service;

import com.github.cule9.covid19map.model.DataURL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CaseParserTest {

    @ParameterizedTest
    @EnumSource(DataURL.class)
    void getCaseData_shouldReturnGreaterThanOrEqualTo252list(DataURL dataURL) {
        // given
        CaseParser caseParser = new CaseParser();

        // when
        List<CSVRecord> result = caseParser.getCaseData(dataURL);

        // then
        int resultSize = result.size();
        log.warn("result.size(): " + resultSize);
        assertThat(resultSize).isGreaterThanOrEqualTo(252);
    }
}