package com.github.cule9.covid19map.service;

import com.github.cule9.covid19map.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataParserTest {

    @Test
    void getCovidDataPoints_shouldReturnNonEmptyListOfPoints() {
        // given
        DataParser dataParser = new DataParser();

        // when
        List<Point> result = dataParser.getCovidDataPoints();

        // then
        assertThat(result)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(252);
    }
}