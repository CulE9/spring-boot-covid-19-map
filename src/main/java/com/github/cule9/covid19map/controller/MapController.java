package com.github.cule9.covid19map.controller;

import com.github.cule9.covid19map.service.DataParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/covid19")
public class MapController {

    private final DataParser dataParser;

    public MapController(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    @GetMapping("/map")
    public String getMap(Model model) {
        model.addAttribute("points", dataParser.getCovidDataPoints());
        return "map";
    }
}
