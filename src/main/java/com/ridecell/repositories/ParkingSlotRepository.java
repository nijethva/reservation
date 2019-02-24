package com.ridecell.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ridecell.entities.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends PagingAndSortingRepository<ParkingSlot, Long> {
	/**
	 * returns a ParkingSlot by id and boolean vacant
	 * 
	 * @param id
	 * @param vacant
	 * @return
	 */
	Optional<ParkingSlot> findByIdAndVacant(Long id, Boolean vacant);
	
	/**
	 * return ParkingSlot(s) booked by current user
	 * 
	 * @param bookedBy
	 * @return
	 */
	List<ParkingSlot> findByBookedBy(Long bookedBy);
}
