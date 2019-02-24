package com.ridecell.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Boolean vacant;
	private Double cost;
	
	// telephone no of user
	private Long bookedBy;
	
	public ParkingSlot() {
	}
	
	public ParkingSlot(Long id, BigDecimal latitude, BigDecimal longitude, Boolean vacant, Double cost, Long bookedBy) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.vacant = vacant;
		this.cost = cost;
		this.bookedBy = bookedBy;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public Boolean getVacant() {
		return vacant;
	}
	public void setVacant(Boolean vacant) {
		this.vacant = vacant;
	}
	public Double getCost() {
		return this.cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Long getBookedBy() {
		return this.bookedBy;
	}
	public void setBookedBy(Long bookedBy) {
		this.bookedBy = bookedBy;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((vacant == null) ? 0 : vacant.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((bookedBy == null) ? 0 : bookedBy.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingSlot other = (ParkingSlot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (vacant == null) {
			if (other.vacant != null)
				return false;
		} else if (!vacant.equals(other.vacant))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (bookedBy == null) {
			if (other.bookedBy != null)
				return false;
		} else if (!bookedBy.equals(other.bookedBy))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ParkingSlot [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", vacant=" + vacant
				+ ", cost=" + cost + ", bookedBy=" + bookedBy + "]";
	}
}
