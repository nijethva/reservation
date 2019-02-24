package com.ridecell.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ridecell.entities.ParkingSlot;
import com.ridecell.repositories.ParkingSlotRepository;

@RunWith(SpringRunner.class)
public class ParkingSlotServiceTest {
	@TestConfiguration
	static class ParkingSlotServiceTestConfiguration {
		@Bean
		public ParkingSlotService parkingSlotService() {
			return new ParkingSlotService();
		}
	}
	
	@Autowired
	private ParkingSlotService parkingSlotService;
	
	@MockBean
	private ParkingSlotRepository parkingSlotRepository;
	
	@Before
	public void setUp() {
		ParkingSlot ps = new ParkingSlot(null, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, null);
		List<ParkingSlot> all = new ArrayList<>();
		all.add(ps);
		
		Mockito.when(parkingSlotRepository.findAll()).thenReturn(all);
	}
	
	@Test
	public void whenValidParkingSlotWithinRadius_thenReturnParkingSlotsAvailableWithinRadius() {
		List<ParkingSlot> allFound = parkingSlotService.findByLatLngAndRadius(new BigDecimal(1.13), new BigDecimal(1.14), 100);
		assertEquals(1, allFound.size());
	}
}
