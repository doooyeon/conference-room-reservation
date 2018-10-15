package com.doy.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home() {
        return "addReservation";
    }

    @GetMapping("/addReservation")
    public String addReservation() {
        return "addReservation";
    }

    @GetMapping("/viewReservation")
    public String viewReservation() {
        return "viewReservation";
    }
}
