package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.testData.FlightBuilder;
import com.gridnine.testing.service.FilteringFlights;
import com.gridnine.testing.service.FilteringFlightsImpl;

import java.util.List;
import java.util.Objects;

public class Main {
    public static FilteringFlights filteringFlights;

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        filteringFlights = new FilteringFlightsImpl();

        System.out.println("List of all flights:");
        printFilteringList(flights);

        System.out.println("\n List of flights up to the current time: ");
        printFilteringList(filteringFlights.filteringTimeFlights(flights));

        System.out.println("\n List of flights with an arrival date later than the departure date:");
        printFilteringList(filteringFlights.filteringDateEarlierThanDeparture(flights));

        System.out.println("\n List of flights where the time spent on the ground does not exceed two hours");
        printFilteringList(filteringFlights.filteringTimeMoreTwoHoursOnGround(flights));
    }

    public static void printFilteringList(List<Flight> list) {
        list.stream()
                .filter(Objects::nonNull)
                .reduce(1, (a, b) -> {
                            System.out.println(
                                    a + " " + b.getSegments()
                            );
                            return ++a;
                        },
                        (a, b) -> ++a);

    }
}
