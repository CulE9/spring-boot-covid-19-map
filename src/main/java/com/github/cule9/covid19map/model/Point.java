package com.github.cule9.covid19map.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Point {

    private double latitude;
    private double longitude;
    private String description;
}
