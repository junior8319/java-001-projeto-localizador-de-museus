package com.betrybe.museumfinder.service;

import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import java.util.Optional;

/**
 * Interface for Museum service class.
 */
public interface MuseumServiceInterface {

  /**
   * Gets closest museum.
   *
   * @param coordinate  the coordinate
   * @param maxDistance the max distance
   * @return the closest museum
   */
  Museum getClosestMuseum(Coordinate coordinate, Double maxDistance);

  /**
   * Create museum museum.
   *
   * @param museum the museum
   * @return the museum
   */
  Museum createMuseum(Museum museum);

  /**
   * Gets museum.
   *
   * @param id the id
   * @return the museum
   */
  Museum getMuseum(Long id);
}
