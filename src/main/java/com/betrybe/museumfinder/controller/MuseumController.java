package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
