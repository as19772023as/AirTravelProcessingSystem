package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilteringFlightsImpl implements FilteringFlights {
    private final LocalDateTime timeNow;

    public FilteringFlightsImpl() {
        this.timeNow = LocalDateTime.now();
    }

    @Override
    public List<Flight> filteringTimeFlights(List<Flight> flights) {
        if (flights != null) {
            return flights.stream()
                    .filter(flight -> flight.getSegments().stream()
                            .anyMatch(segment -> timeNow.isBefore(segment.getDepartureDate())))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Flight> filteringDateEarlierThanDeparture(List<Flight> flights) {
        List<Flight> filters = new ArrayList<>();
        if (flights != null) {
            flights.forEach(flight -> flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())).limit(1)
                    .forEach(segment -> filters.add(flight)));
            return filters;
        }
        return filters;
    }

    @Override
    public List<Flight> filteringTimeMoreTwoHoursOnGround(List<Flight> flights) {
        List<Flight> filters = new ArrayList<>();
        if (flights != null) {
            filters = flights.stream()
                    .filter(flight -> flight.getSegments().size() > 1)
                    .filter(flight -> {
                        long countHours = 0;
                        try {
                            countHours = IntStream.range(1, flight.getSegments().size())
                                    .mapToLong(i -> findTimeDifference(flight.getSegments().get(i - 1).getArrivalDate(),
                                            flight.getSegments().get(i).getDepartureDate()))
                                    .sum();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        return countHours <= 2;
                    })
                    .collect(Collectors.toList());
            return filters;
        }
        return filters;
    }

    private long findTimeDifference(LocalDateTime arrival, LocalDateTime departure) {
        return ChronoUnit.HOURS.between(arrival, departure);
    }

}
