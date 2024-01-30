package com.example.alerte_service.Service;

import com.example.alerte_service.entite.Purchase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PurchaseRestController {

    private final String BASE_URL = "http://127.0.0.1:8080";
    private final RestTemplate restTemplate;
    public PurchaseRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<Purchase> getAllPurchases() {
        String url = BASE_URL + "/purchasesList";
        ResponseEntity<List<Purchase>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Purchase>>() {}
        );
        return responseEntity.getBody();
    }
    public Page<Purchase> getAllPurchase(@SpringQueryMap Pageable pageRequest) {
        String url = BASE_URL + "/purchasesPage";
        ResponseEntity<Page<Purchase>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Page<Purchase>>() {}
        );
        return responseEntity.getBody();
    }
    public Purchase getPurchaseById(Long id) {
        String url = BASE_URL + "/purchase/{id}";

        try {
            ResponseEntity<Purchase> responseEntity = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Purchase>() {},
                    id
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Log the unsuccessful response status
                System.out.println("Error retrieving Purchase. Status code: " + responseEntity.getStatusCodeValue());
                return null;
            }
        } catch (Exception e) {
            // Log the exception
            System.out.println("Error retrieving Purchase: " + e.getMessage());
            System.out.println("Error retrieving Purchase: ");
            return null;
        }
    }
    public void save(Purchase purchase) {
        String url = BASE_URL + "/save";
        try {
            ResponseEntity<Purchase> responseEntity = restTemplate.postForEntity(
                    url,
                    purchase,
                    Purchase.class
            );
            if (responseEntity.getStatusCode().is2xxSuccessful()){
                // Successfully saved to the database
                System.out.println("Purchase saved successfully!");
            } else {
                // Log the unsuccessful response status
                System.out.println("Error saving Purchase. Status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            // Log the exception
            System.out.println("Error saving Purchase: " + e.getMessage());
        }
    }
}

