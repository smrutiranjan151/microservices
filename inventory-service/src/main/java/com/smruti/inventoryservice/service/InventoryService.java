package com.smruti.inventoryservice.service;

import com.smruti.inventoryservice.dto.InventoryResponse;
import com.smruti.inventoryservice.model.Inventory;
import com.smruti.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = false)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder().skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0).build()
                )
                .toList();
    }
}
