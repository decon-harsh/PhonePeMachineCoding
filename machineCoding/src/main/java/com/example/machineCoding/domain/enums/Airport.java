package com.example.machineCoding.domain.enums;

import lombok.Getter;

@Getter
public enum Airport {
    DEL("Indira Gandhi International Airport", "DELHI"),
    BOM("Chhatrapati Shivaji Maharaj International Airport", "MUMBAI"),
    BLR("Kempegowda International Airport", "BENGALURU"),
    IXR("Birsa Munda International Airpot", "RANCHI");

    private final String fullName;
    private final String city;

    Airport(String fullName, String city) {
        this.fullName = fullName;
        this.city = city;
    }
}

