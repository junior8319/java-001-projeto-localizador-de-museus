package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes da camada de controle de CollectionTypes")
public class CollectionTypeControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  CollectionTypeService mockCollectionTypeService;

  String mockHistTerm = "hist";
  String mockImagTerm = "imag";
  String mockCombinedTerms = "hist,imag";
  String mockInvalidTerm = "host";

  String[] mockHistArray = new String[] { mockHistTerm };
  String[] mockImagArray = new String[] { mockImagTerm };
  String[] mockCombinedArray = new String[] { mockHistTerm, mockImagTerm };
  String[] mockInvalidArray = new String[] { mockInvalidTerm };


  CollectionTypeCount collectionTypeHistCount =
      new CollectionTypeCount(mockHistArray, 387L);
  CollectionTypeCount collectionTypeImagCount =
      new CollectionTypeCount(mockImagArray, 105L);
  CollectionTypeCount collectionCombinedCount =
      new CollectionTypeCount(mockCombinedArray, 492L);
  CollectionTypeCount collectionInvalidCount =
      new CollectionTypeCount(mockInvalidArray, 0L);

  String histUrl = "/collections/count/%s".formatted(mockHistTerm);
  String imagUrl = "/collections/count/%s".formatted(mockImagTerm);
  String combinedUrl = "/collections/count/%s".formatted(mockCombinedTerms);
  String invalidUrl = "/collections/count/%s".formatted(mockInvalidTerm);


  @Test
  @DisplayName(
      "Deve retornar status OK quando informa o termo de busca 'hist'"
      + " que combina com ao menos um registro existente no banco"
  )
  public void testGetHistCount() throws Exception {
    Mockito.when(mockCollectionTypeService.countByCollectionTypes(mockHistTerm))
        .thenReturn(collectionTypeHistCount);

    mockMvc.perform(get(histUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.collectionTypes").value("hist"))
        .andExpect(jsonPath("$.count").value(387L));
    Mockito.verify(mockCollectionTypeService, Mockito.atLeast(1))
        .countByCollectionTypes(eq(mockHistTerm));
  }

  @Test
  @DisplayName(
      "Deve retornar status OK quando informa o termo de busca 'imag'"
          + " que combina com ao menos um registro existente no banco"
  )
  public void testGetImagCount() throws Exception {
    Mockito.when(mockCollectionTypeService.countByCollectionTypes(mockImagTerm))
        .thenReturn(collectionTypeImagCount);

    mockMvc.perform(get(imagUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.collectionTypes").value("imag"))
        .andExpect(jsonPath("$.count").value(105L));
    Mockito.verify(mockCollectionTypeService, Mockito.atLeast(1))
        .countByCollectionTypes(eq(mockImagTerm));
  }

  @Test
  @DisplayName(
      "Deve retornar status OK quando informa os termo de busca 'hist' e 'imag'"
          + " combinados e encontra ao menos um registro existente no banco"
  )
  public void testGetCombinedCount() throws Exception {
    Mockito.when(mockCollectionTypeService.countByCollectionTypes(mockCombinedTerms))
        .thenReturn(collectionCombinedCount);

    mockMvc.perform(get(combinedUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.collectionTypes", containsInAnyOrder(
            is("hist"),
            is("imag")
        )))
        .andExpect(jsonPath("$.count").value(492L));
    Mockito.verify(mockCollectionTypeService, Mockito.atLeast(1))
        .countByCollectionTypes(eq(mockCombinedTerms));
  }

  @Test
  @DisplayName(
      "Deve retornar status NOT_FOUND quando informa um termo de busca inválido"
          + " e não encontra nenhum registro existente no banco"
  )
  public void testGetInvalidTermCount() throws Exception {
    Mockito.when(mockCollectionTypeService.countByCollectionTypes(mockInvalidTerm))
        .thenReturn(collectionInvalidCount);

    mockMvc.perform(get(invalidUrl))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.collectionTypes").doesNotExist())
        .andExpect(jsonPath("$.count").doesNotExist());
    Mockito.verify(mockCollectionTypeService, Mockito.atLeast(1))
        .countByCollectionTypes(eq(mockInvalidTerm));
  }
}
