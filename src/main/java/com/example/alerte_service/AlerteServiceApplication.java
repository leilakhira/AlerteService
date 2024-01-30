package com.example.alerte_service;

import com.example.alerte_service.Service.PurchaseRestController;
import com.example.alerte_service.entite.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class AlerteServiceApplication {
    @Autowired
    private PurchaseRestController purchaseRestController;
    public static void main(String[] args) {
        SpringApplication.run(AlerteServiceApplication.class, args);
    }
    public Purchase test(){
        Purchase p = purchaseRestController.getPurchaseById(3L);
        p.setClasse(1);
        purchaseRestController.save(p);
        return p;
    }

}
