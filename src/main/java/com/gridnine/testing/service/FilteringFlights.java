package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FilteringFlights {
    List<Flight> filteringTimeFlights(List<Flight> flights);
    List<Flight> filteringDateEarlierThanDeparture(List<Flight> flights);
    List<Flight> filteringTimeMoreTwoHoursOnGround(List<Flight> flights);
}
