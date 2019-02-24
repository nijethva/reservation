package com.ridecell.utils;

import java.math.BigDecimal;

public class Utilities {
	/**
	 * eath radius vary from 6357 to 6378
	 * https://en.wikipedia.org/wiki/Earth_radius
	 */
	public static final Integer EARTH_RADIUS_IN_METERS = 6378 * 1000;

	/**
	 * find distance between to points based on,
	 * https://en.wikipedia.org/wiki/Haversine_formula
	 * 
	 * @param fromLat
	 * @param fromLng
	 * @param toLat
	 * @param toLng
	 * @return
	 */
	public static double getDistance(BigDecimal fromLat, BigDecimal fromLng, BigDecimal toLat, BigDecimal toLng) {
		double fromLatR = Math.toRadians(fromLat.doubleValue());
		double fromLngR = Math.toRadians(fromLng.doubleValue());
		double toLatR = Math.toRadians(toLat.doubleValue());
		double toLngR = Math.toRadians(toLng.doubleValue());
		
		double latDiff = toLatR - fromLatR;
		double lngDiff = toLngR - fromLngR;
		
		double distance = 2 * EARTH_RADIUS_IN_METERS * Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff / 2), 2)
				+ (Math.cos(fromLatR) * Math.cos(toLatR) * Math.pow(Math.sin(lngDiff / 2), 2)) ));
		
		return distance;
	}
}
