package com.ridecell.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ridecell.entities.ParkingSlot;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingSlotRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	
	@Test
	public void givenParkingSlot_whenFindByIdAndVacant_thenReturnParkingSlot() {
		//given
		ParkingSlot ps = new ParkingSlot(null, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, null);
		entityManager.persist(ps);
		entityManager.flush();
		
		//when
		ParkingSlot found = parkingSlotRepository.findByIdAndVacant(ps.getId(), ps.getVacant()).get();
		
		//then
		assertTrue(ps.equals(found));
	}
	
	@Test
	public void givenParkingSlot_whenFindByBookedBy_thenReturnParkingSlotsBookedByCurrentUser() {
		//given
		ParkingSlot ps = new ParkingSlot(null, new BigDecimal(1.13), new BigDecimal(1.14), true, 25.00, 9987076937l);
		entityManager.persist(ps);
		entityManager.flush();
		
		//when
		List<ParkingSlot> allFound = parkingSlotRepository.findByBookedBy(ps.getBookedBy());
		
		//then
		assertEquals(1, allFound.size());
	}
}
