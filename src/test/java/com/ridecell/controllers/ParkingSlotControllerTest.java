package com.ridecell.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ridecell.entities.ParkingSlot;
import com.ridecell.repositories.ParkingSlotRepository;
import com.ridecell.services.ParkingSlotService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingSlotController.class)
public class ParkingSlotControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ParkingSlotService parkingSlotService;
	
	@MockBean
	private ParkingSlotRepository parkingSlotRepository;
	
	@Test
	public void givenParkingSlots_whenSearchParkingSlotForLatLngWithinRadius_thenReturnAllValidParkingSlots() throws Exception {
		ParkingSlot ps = new ParkingSlot(null, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, null);
		List<ParkingSlot> all = Arrays.asList(ps);
		
		Mockito.when(parkingSlotService.findByLatLngAndRadius(ps.getLatitude(), ps.getLongitude(), 100)).thenReturn(all);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/parking-slots/search?lat=" + ps.getLatitude() + "&lng=" + ps.getLongitude() + "&radius=100")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}
	
	@Test
	public void givenParkingSlots_thenReturnAllParkingSlotsBookedByCurrentUser() throws Exception {
		ParkingSlot ps = new ParkingSlot(null, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, 9987076937l);
		List<ParkingSlot> all = Arrays.asList(ps);
		
		Mockito.when(parkingSlotService.getParkingSlotRepository()).thenReturn(parkingSlotRepository);
		Mockito.when(parkingSlotRepository.findByBookedBy(9987076937l)).thenReturn(all);
		
		mvc.perform(MockMvcRequestBuilders.get("/parking-slots/my-reservations?bookedBy=9987076937")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}
	
	@Test
	public void givenValidParkingSlot_whenVacant_thenReserveParkingSlot() throws Exception {
		ParkingSlot input = new ParkingSlot(1l, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, null);
		Optional<ParkingSlot> optional = Optional.of(input);
		
		ParkingSlot output = new ParkingSlot(1l, new BigDecimal(1.13), new BigDecimal(1.14), false, 25.00, 9987076937l);
		
		Mockito.when(parkingSlotService.getParkingSlotRepository()).thenReturn(parkingSlotRepository);
		Mockito.when(parkingSlotRepository.findByIdAndVacant(1l, true)).thenReturn(optional);
		Mockito.when(parkingSlotRepository.save(input)).thenReturn(output);
		
		mvc.perform(MockMvcRequestBuilders.put("/parking-slots/reserve/" + 1l)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"vacant\":true, \"bookedBy\":9987076937}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.vacant", Matchers.is(false)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookedBy", Matchers.is(9987076937l)));
	}
	
	@Test
	public void givenInValidParkingSlot_thenReturnBadRequestResponse() throws Exception {
		Optional<ParkingSlot> optional = Optional.empty();
		
		Mockito.when(parkingSlotService.getParkingSlotRepository()).thenReturn(parkingSlotRepository);
		Mockito.when(parkingSlotRepository.findByIdAndVacant(1l, true)).thenReturn(optional);
		
		mvc.perform(MockMvcRequestBuilders.put("/parking-slots/reserve/" + 1l)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"vacant\":true, \"bookedBy\":9987076937}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void givenValidParkingSlot_whenNotVacant_thenReturnBadRequestResponse() throws Exception {
		ParkingSlot input = new ParkingSlot(1l, new BigDecimal(1.13), new BigDecimal(1.14), false, 25.00, 9987076937l);
		Optional<ParkingSlot> optional = Optional.of(input);
		
		Mockito.when(parkingSlotService.getParkingSlotRepository()).thenReturn(parkingSlotRepository);
		Mockito.when(parkingSlotRepository.findByIdAndVacant(1l, true)).thenReturn(optional);
		
		mvc.perform(MockMvcRequestBuilders.put("/parking-slots/reserve/" + 1l)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"vacant\":true, \"bookedBy\":9987076937}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
