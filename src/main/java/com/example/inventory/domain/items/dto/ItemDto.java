package com.example.inventory.domain.items.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {

    private String id;
    private String name;

    private String category;

}
