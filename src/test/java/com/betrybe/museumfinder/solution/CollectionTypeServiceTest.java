package com.betrybe.museumfinder.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DisplayName("Testa a camada de serviço das CollectionTypes")
public class CollectionTypeServiceTest {
  @Autowired
  CollectionTypeService collectionTypeService;

  @MockBean
  private MuseumFakeDatabase mockMuseumFakeDatabase;

  String[] mockCollectionTypes = new String[] {
    "hist",
    "imag"
  };

  String mockInvalidCollectionType = "host";

  @Test
  @DisplayName(
      "Deve retornar o objeto correto no caso de existência "
      + " de registros que atendam aos termos de busca"
  )
  void testCountByCollactionTypes() {
    Mockito.when(mockMuseumFakeDatabase.countByCollectionType(mockCollectionTypes[0]))
        .thenReturn(387L);
    Mockito.when(mockMuseumFakeDatabase.countByCollectionType("história"))
        .thenReturn(387L);
    Mockito.when(mockMuseumFakeDatabase.countByCollectionType(mockCollectionTypes[1]))
        .thenReturn(105L);

    CollectionTypeCount mockCollectionTypesCount = new CollectionTypeCount(
        mockCollectionTypes,
        492L
    );


    CollectionTypeCount serviceResponse = collectionTypeService.countByCollectionTypes(
        String.join(",", mockCollectionTypes)
    );

    assertTrue(serviceResponse instanceof CollectionTypeCount);
    assertEquals(
        collectionTypeService.countByCollectionTypes("história").count(),
        387L
    );
    assertEquals(
        collectionTypeService.countByCollectionTypes("hist").count(),
        387L
    );
  }

  @Test
  @DisplayName(
      "Deve retornar 0 se o termo de busca estiver incorreto"
          + " ou não combinar com um tipo de museum cadastrado"
  )
  void testNoMatchTerm() {
    Mockito.when(mockMuseumFakeDatabase.countByCollectionType(mockInvalidCollectionType))
        .thenReturn(0L);

    CollectionTypeCount serviceResponse = collectionTypeService
        .countByCollectionTypes("host");

    assertTrue(serviceResponse instanceof CollectionTypeCount);
    assertEquals(
        collectionTypeService
            .countByCollectionTypes(mockInvalidCollectionType).count(),
        0L
    );
  }
}
