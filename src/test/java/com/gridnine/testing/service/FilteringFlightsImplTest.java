package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Checking the air travel processing system")
class FilteringFlightsImplTest {
    FilteringFlights filteringFlightsTest = new FilteringFlightsImpl();

    Segment testOne = new Segment(LocalDateTime.of(
            2024, 6, 26, 0, 30),
            LocalDateTime.of(2024, 6, 26, 2, 10)
    );
    Segment testTwo = new Segment(LocalDateTime.of(
            2024, 11, 8, 5, 30),
            LocalDateTime.of(2024, 10, 8, 6, 0)
    );
    Segment testThree = new Segment(
            LocalDateTime.of(2024, 6, 25, 20, 30),
            LocalDateTime.of(2024, 6, 25, 23, 0)
    );
    Segment testFour = new Segment(
            LocalDateTime.of(2024, 9, 10, 5, 30),
            LocalDateTime.of(2024, 9, 9, 1, 0)
    );

    List<Segment> segmentList = List.of(testOne);
    List<Segment> segmentListTwo = List.of(testTwo);
    List<Segment> segmentListThree = List.of(testThree, testOne);
    List<Segment> segmentListFour = List.of(testThree, testOne);

    Flight testFlight = new Flight(segmentList);
    Flight testFlightTwo = new Flight(segmentListTwo);
    Flight testFlightThree = new Flight(segmentListThree);
    Flight testFlightFour = new Flight(segmentListFour);

    List<Flight> expected = new ArrayList<>();
    List<Flight> actualresult = new ArrayList<>();


    @AfterEach
    void clearFlight() {
        expected.clear();
        actualresult.clear();
    }

    @Test
    @DisplayName("Test for exclusion from the list of departures up to the current point in time")
    void filterFlightTimeUntilNowTest() {
        expected.add(testFlight);

        actualresult.add(testFlight);

        List<Flight> actual = filteringFlightsTest.filteringTimeFlights(actualresult);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test for exclusion from the list of departures with an arrival date earlier than the departure date")
    void filterFlightsBeforeDepartureDateTest() {
        actualresult.add(testFlightTwo);

        List<Flight> actual = filteringFlightsTest.filteringDateEarlierThanDeparture(actualresult);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test for exclusion from the flight list where the total time spent on the ground exceeds two hours")
    void filterTimeOnEarthMoreTwoHoursTest() {
        expected.add(testFlightFour);

        List<Flight> actual = filteringFlightsTest.filteringTimeMoreTwoHoursOnGround(expected);

        assertEquals(expected, actual);
    }
}