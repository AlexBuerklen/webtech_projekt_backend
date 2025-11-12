package webtechBackend.controller;

import webtechBackend.model.Trip;
import webtechBackend.service.TripService.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequiredArgsConstructor
@RequestMapping("/apiTrip")
public class TripController {

    private static final Logger log = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    @GetMapping("/trips")
    public List<Trip> findAll(){
        return tripService.findAll();
    }

    @PostMapping("/trips")
    public Trip addTrip(@RequestBody Trip theTrip){

        log.info("Adding trip: " + theTrip);

        return tripService.save(theTrip);
    }

    @PostMapping("/tripsName/{tripId}")
    public Trip changeTripName(@RequestBody String newTripName, @PathVariable int tripId) throws UnsupportedEncodingException {
        Trip trip = tripService.findById(tripId);
        String decodedName = URLDecoder.decode(newTripName, StandardCharsets.UTF_8);
        trip.setName(decodedName);
        log.info("Changing trip name: " + trip.getName());
        return tripService.save(trip);
    }

    @PostMapping("/trips/{tripId}")
    public Trip addTotalDistance(@RequestBody Double totalDistance, @PathVariable int tripId){
        Trip trip = tripService.findById(tripId);
        trip.setTotalDistance(totalDistance);
        log.info("addTotalDistance: " + totalDistance);
        return tripService.save(trip);
    }

    @GetMapping("/trips/{tripId}")
    public Trip getTrips(@PathVariable int tripId){

        Trip theTrip = tripService.findById(tripId);

        if(theTrip == null){
            throw new RuntimeException("Trip id: " + tripId + " not found");
        }

        log.info("Getting trips: " + theTrip);

        return theTrip;
    }

    @DeleteMapping("/trips/{tripId}")
    public String deleteTrip(@PathVariable int tripId){

        Trip tempTrip = tripService.findById(tripId);

        if(tempTrip == null){
            throw new RuntimeException("Trip id: " + tripId + " not found");
        }

        tripService.deleteById(tripId);

        return "Deleted tripId: " + tripId;
    }
}
