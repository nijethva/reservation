package com.ridecell.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ridecell.entities.ParkingSlot;
import com.ridecell.services.ParkingSlotService;

@RestController
@RequestMapping(path = "/parking-slots")
public class ParkingSlotController {
	@Autowired
	private ParkingSlotService parkingSlotService;
	
	@GetMapping(path = "/search")
	public @ResponseBody List<ParkingSlot> getAllParkingSlotByLatLngAndRadius(@RequestParam String lat, @RequestParam String lng, @RequestParam String radius) {
		return parkingSlotService.findByLatLngAndRadius(new BigDecimal(lat), new BigDecimal(lng), new Integer(radius));
	}
	
	@GetMapping(path = "/my-reservations")
	public @ResponseBody List<ParkingSlot> getAllParkingSlotIReserved(@RequestParam String bookedBy) {
		return parkingSlotService.getParkingSlotRepository().findByBookedBy(new Long(bookedBy));
	}
	
	@PutMapping(path = "/reserve/{id}")
	public @ResponseBody ParkingSlot reserve(@PathVariable Long id, @RequestBody ParkingSlot parkingSlot) {
		boolean vacant = parkingSlot.getVacant();
		return parkingSlotService.getParkingSlotRepository().findByIdAndVacant(id, vacant)
				.map(ps -> {
					ps.setVacant(!vacant);
					ps.setBookedBy(parkingSlot.getBookedBy());
					return parkingSlotService.getParkingSlotRepository().save(ps);
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find ParkingSlot with id - " + id + " and vacant - " + vacant));
	}
}
