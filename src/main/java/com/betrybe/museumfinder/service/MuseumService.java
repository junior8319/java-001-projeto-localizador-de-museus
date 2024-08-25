package com.betrybe.museumfinder.service;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.util.CoordinateUtil;
import org.springframework.stereotype.Service;

/**
 * The type Museum service.
 */
@Service
public class MuseumService implements MuseumServiceInterface {
  private MuseumFakeDatabase museumFakeDatabase;

  @Override
  public Museum getClosestMuseum(Coordinate coordinate, Double maxDistance) {
    return null;
  }

  @Override
  public Museum createMuseum(Museum museum) {
    try {
      if (CoordinateUtil.isCoordinateValid(museum.getCoordinate())) {
        return museumFakeDatabase.saveMuseum(museum);
      }
    } catch (InvalidCoordinateException exception) {
      throw new InvalidCoordinateException("Coordenada inválida.");
    }
    return museum;
  }

  @Override
  public Museum getMuseum(Long id) {
    return null;
  }
}
