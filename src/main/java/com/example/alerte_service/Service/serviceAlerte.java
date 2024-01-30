package com.example.alerte_service.Service;
import com.example.alerte_service.Service.PurchaseRestController;
import com.example.alerte_service.entite.ModelEvaluation;
import com.example.alerte_service.entite.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Service
public class serviceAlerte {
    private final PurchaseRestController purchaseRestController;
    // Utilisez RestTemplate pour effectuer la requête POST avec le corps de la requête
    RestTemplate restTemplate = new RestTemplate();
    public serviceAlerte(PurchaseRestController purchaseRestController) {
        this.purchaseRestController = purchaseRestController;
    }
    public List<Integer> getPredictions() {
        // Créez les données que vous souhaitez envoyer
        // Définissez cette valeur dans votre fichier application.properties
        String pythonMicroserviceUrl = "https://prediction-microservice.azurewebsites.net";
        String pythonEndpoint = pythonMicroserviceUrl + "/prediction_model";
        String filePath = "FraudData.csv";
        // Écrire les données dans le fichier CSV
        try (FileWriter writer = new FileWriter(filePath)){
            List<Purchase> purchases = purchaseRestController.getAllPurchases();
            writer.write("\"user_id\",\"signup_time\",\"purchase_time\",\"purchase_value\",\"device_id\",\"source\",\"browser\",\"sex\",\"age\",\"ip_address\",\"class\"\n");
            for (Purchase purchase : purchases) {
                String rowData = purchase.getPurchase_id() + "," +
                        purchase.getFormattedSignupTime() + "," +
                        purchase.getFormattedPurchaseTime() + "," +
                        purchase.getPurchase_value() + "," +      // Purchase Value,
                        "\"" + purchase.getDevice_id() + "\"," +  // Device ID
                        "\"" + purchase.getSource() + "\"," +
                        "\"" + purchase.getBrowser() + "\"," +
                        purchase.getSex() + "," +
                        purchase.getAge() + "," +
                        purchase.getIpAdress() + "," +
                        purchase.getClasse() +"\n";
                writer.write(rowData);
            }
            System.out.println("Données exportées avec succès vers " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Créez les données que vous souhaitez envoyer
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(new File(filePath)));

        // Configurez les en-têtes de la requête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Créez un objet HttpEntity avec les données et les en-têtes
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                pythonEndpoint,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<Integer>>() {}
        );
        System.out.println("Response body: " + response.getBody());
        // Traitez la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Gérer les erreurs, par exemple, en lançant une exception
            throw new RuntimeException("Erreur lors de la récupération des prédictions depuis le microservice Python");
        }
    }
    public List<ModelEvaluation> getEvaluations() {
        String pythonMicroserviceUrl = "http://127.0.0.1:8000";
        String pythonEndpoint = pythonMicroserviceUrl + "/train_model";

        // Configurez les en-têtes de la requête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Créez un objet HttpEntity avec les données et les en-têtes
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ModelEvaluation>> response = restTemplate.exchange(
                pythonEndpoint,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        System.out.println("Response body: " + response.getBody());
        // Traitez la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Gérer les erreurs, par exemple, en lançant une exception
            throw new RuntimeException("Erreur lors de la récupération des prédictions depuis le microservice Python");
        }
    }
}



