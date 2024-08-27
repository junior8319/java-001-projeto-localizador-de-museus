package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Museum controller.
 */
@RestController
@RequestMapping("/museums")
public class MuseumController {
  private MuseumServiceInterface service;

  /**
   * Instantiates a new Museum controller.
   *
   * @param service the service
   */
  public MuseumController(MuseumServiceInterface service) {
    this.service = service;
  }

  /**
   * Create museum response entity.
   *
   * @param museum the museum
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<MuseumDto> createMuseum(@RequestBody MuseumCreationDto museum) {
    Museum newMuseum = new Museum();
    newMuseum.setName(museum.name());
    newMuseum.setDescription(museum.description());
    newMuseum.setAddress(museum.address());
    newMuseum.setCollectionType(museum.collectionType());
    newMuseum.setSubject(museum.subject());
    newMuseum.setUrl(museum.url());
    newMuseum.setCoordinate(museum.coordinate());

    Museum savedMuseum = service.createMuseum(newMuseum);

    MuseumDto museumDto = new MuseumDto(
        savedMuseum.getId(),
        savedMuseum.getName(),
        savedMuseum.getDescription(),
        savedMuseum.getAddress(),
        savedMuseum.getCollectionType(),
        savedMuseum.getSubject(),
        savedMuseum.getUrl(),
        savedMuseum.getCoordinate()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(museumDto);
  }

  /**
   * Gets closest museum.
   *
   * @param latitude    the latitude
   * @param longitude   the longitude
   * @param maxDistance the max distance
   * @return the closest museum
   */
  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> getClosestMuseum(
      @RequestParam(name = "lat") Double latitude,
      @RequestParam(name = "lng") Double longitude,
      @RequestParam(name = "max_dist_km") Double maxDistance
  ) {
    Coordinate coordinate = new Coordinate(latitude, longitude);

    Museum museumFound = service.getClosestMuseum(coordinate, maxDistance);

    MuseumDto museumDto = ModelDtoConverter.modelToDto(museumFound);

    return ResponseEntity.status(HttpStatus.OK).body(museumDto);
  }

  /**
   * Gets museum.
   *
   * @param id the id
   * @return the museum
   */
  @GetMapping("/{id}")
  public ResponseEntity<MuseumDto> getMuseum(@PathVariable Long id) {
    Museum foundMuseum = service.getMuseum(id);

    MuseumDto museumDto = ModelDtoConverter.modelToDto((foundMuseum));

    return  ResponseEntity.status(HttpStatus.OK).body(museumDto);
  }
}
