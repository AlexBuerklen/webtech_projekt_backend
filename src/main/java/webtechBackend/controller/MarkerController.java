package webtechBackend.controller;

import webtechBackend.model.Marker;
import webtechBackend.model.Trip;
import webtechBackend.service.MarkerService.MarkerService;
import webtechBackend.service.TripService.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/apiMarker")
public class MarkerController {

    private static final Logger log = LoggerFactory.getLogger(MarkerController.class);

    private final MarkerService markerService;

    private final TripService tripService;

    @GetMapping("/markers")
    public List<Marker> findAll(){
        return markerService.findAll();
    }

    @GetMapping("/markers/{markerId}")
    public Marker getMarker(@PathVariable int markerId){
        Marker theMarker = markerService.findById(markerId);

        if(theMarker == null){
            throw new RuntimeException("Marker id: " + markerId + " not found");
        }
        log.info("Getting Marker: {}", theMarker);
        return theMarker;
    }

    @PostMapping("/markers")
    public Marker addMarker(@RequestBody Marker theMarker, @RequestParam int tripId){
        Trip trip = tripService.findById(tripId);
        theMarker.setTrip(trip);
        log.info("Adding Marker: {}", theMarker);
        return markerService.save(theMarker);
    }

    @DeleteMapping("/markers/{markersId}")
    public String deleteMarker(@PathVariable int markersId){

        Marker tempMarker = markerService.findById(markersId);

        if(tempMarker == null){
            throw new RuntimeException("Marker id: " + markersId + " not found");
        }

        markerService.deleteById(markersId);

        return "Deleted markerId: " + markersId;
    }
}
