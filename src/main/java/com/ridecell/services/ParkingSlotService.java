package com.ridecell.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ridecell.entities.ParkingSlot;
import com.ridecell.repositories.ParkingSlotRepository;
import com.ridecell.utils.Utilities;

@Service
public class ParkingSlotService {
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	
	public ParkingSlotRepository getParkingSlotRepository() {
		return this.parkingSlotRepository;
	}
	
	public List<ParkingSlot> findByLatLngAndRadius(BigDecimal lat, BigDecimal lng, Integer radius) {
		List<ParkingSlot> list = new ArrayList<>();
		
		Iterable<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
		Iterator<ParkingSlot> psIterator = parkingSlots.iterator();
		while(psIterator.hasNext()) {
			ParkingSlot parkingSlot = psIterator.next();
			if(!isParkingSlotWithinRadius(parkingSlot, lat, lng, radius)) {
				continue;
			}
			list.add(parkingSlot);
		}
		
		return list;
	}
	
	private boolean isParkingSlotWithinRadius(ParkingSlot parkingSlot, BigDecimal lat, BigDecimal lng, Integer radius) {
		double distance = Utilities.getDistance(parkingSlot.getLatitude(), parkingSlot.getLongitude(), lat, lng);
		if(distance <= radius.doubleValue()) {
			return true;
		}
		
		return false;
	}
}
