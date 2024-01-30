package com.example.alerte_service.web;

import com.example.alerte_service.Service.PurchaseRestController;
import com.example.alerte_service.Service.serviceAlerte;
import com.example.alerte_service.entite.Purchase;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class alerteRestController{
    private final serviceAlerte service;
    @Autowired
    private final PurchaseRestController purchaseRestController;
    @Autowired
    public alerteRestController(serviceAlerte service, PurchaseRestController purchaseRestController) {
        this.service = service;
        this.purchaseRestController = purchaseRestController;
    }
    @GetMapping("/check-fraud")
    public String isFraude(Model model){
        List<Integer> predictions = service.getPredictions();
        System.out.println(predictions);
        System.out.println("prediction");
        for (int i = 0; i < predictions.size(); i++) {
            Integer prediction = predictions.get(i);
            if (prediction == 1) {
                // Envoyer un message en cas de fraude
                Purchase purchase = purchaseRestController.getPurchaseById((long) i+1);
                purchase.setClasse(1);
                purchaseRestController.save(purchase);
            }
        }
        List<Purchase> Fraudpurchases = new ArrayList<>();
        List<Purchase> purchases =purchaseRestController.getAllPurchases();
        for (Purchase purchase : purchases) {
            // Assuming that the Purchase class has a method getClassValue() to get the class
            int purchaseClass = purchase.getClasse();
            // Increment the counters based on the class value
            if (purchaseClass == 1) {
                assert false;
                Fraudpurchases.add(purchase);
            }
        }
        model.addAttribute("Fraudpurchases",Fraudpurchases);
        return "fraud";
    }
    @GetMapping("/admin")
    public String findall(Model model) {
        List<Purchase> purchases =purchaseRestController.getAllPurchases();
        model.addAttribute("purchases",purchases);
        int class1Count = 0;
        int class0Count = 0;
        for (Purchase purchase : purchases) {
            int purchaseClass = purchase.getClasse();
            if (purchaseClass == 1) {
                class1Count++;
            } else if (purchaseClass == 0) {
                class0Count++;
            }
        }
        model.addAttribute("class0",class0Count);
        model.addAttribute("class1",class1Count);
        return "admin";
    }
    @GetMapping("/OurModel")
    public String Model(Model model){
        return "OurModel";
    }
}