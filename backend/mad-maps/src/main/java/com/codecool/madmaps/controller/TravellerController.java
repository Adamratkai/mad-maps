package com.codecool.madmaps.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/traveller")
public class TravellerController {

    private final TravellerService travellerService;
}
