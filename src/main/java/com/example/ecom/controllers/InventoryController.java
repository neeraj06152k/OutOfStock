package com.example.ecom.controllers;

import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.dtos.UpdateInventoryRequestDto;
import com.example.ecom.dtos.UpdateInventoryResponseDto;
import com.example.ecom.libraries.SendNotification;
import com.example.ecom.libraries.SendNotificationImpl;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.services.InventoryService;
import com.example.ecom.services.NotificationService;
import com.example.ecom.services.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryController {

    private InventoryService inventoryService;
    //private final NotificationService notificationService = new NotificationServiceImpl();
    private final SendNotification sendNotification = new SendNotificationImpl();

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public UpdateInventoryResponseDto updateInventory(UpdateInventoryRequestDto requestDto) {
        Product product = null;
        User user = null;
        UpdateInventoryResponseDto responseDto = new UpdateInventoryResponseDto();
        try{
            Inventory inventory = inventoryService.updateInventory(requestDto.getProductId(), requestDto.getQuantity());
            product = inventory.getProduct();

            responseDto.setInventory(inventory);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        inventoryService.sendNotification(product.getId());

        return responseDto;
    }
}
