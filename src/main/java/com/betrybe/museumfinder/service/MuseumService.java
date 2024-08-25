package com.betrybe.museumfinder.service;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.util.CoordinateUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Museum service.
 */
@Service
public class MuseumService implements MuseumServiceInterface {
  private MuseumFakeDatabase museumFakeDatabase;

  /**
   * Instantiates a new Museum service.
   *
   * @param museumFakeDatabase the museum fake database
   */
  @Autowired
  public MuseumService(MuseumFakeDatabase museumFakeDatabase) {
    this.museumFakeDatabase = museumFakeDatabase;
  }

  @Override
  public Museum getClosestMuseum(Coordinate coordinate, Double maxDistance) {
    boolean isVlaidMuseumCoordinate = CoordinateUtil
        .isCoordinateValid(coordinate);
    if (!isVlaidMuseumCoordinate) {
      throw new InvalidCoordinateException("Coordenada inválida.");
    }

    Optional<Museum> foundMuseum = museumFakeDatabase.getClosestMuseum(coordinate, maxDistance);
    if (foundMuseum.isEmpty()) {
      System.out.println("Distância: " + maxDistance + "km.");
      throw new MuseumNotFoundException();
    }

    if (!foundMuseum.isPresent()) {
      System.out.println("Distância: " + maxDistance + "km.");
      throw new MuseumNotFoundException();
    }

    return foundMuseum.get();
  }

  @Override
  public Museum createMuseum(Museum museum) {
    boolean isValidMuseumCoordinate = CoordinateUtil
        .isCoordinateValid(museum.getCoordinate());
    if (!isValidMuseumCoordinate) {
      throw new InvalidCoordinateException("Coordenada inválida");
    }

    return this.museumFakeDatabase.saveMuseum(museum);
  }

  @Override
  public Museum getMuseum(Long id) {
    return null;
  }
}
