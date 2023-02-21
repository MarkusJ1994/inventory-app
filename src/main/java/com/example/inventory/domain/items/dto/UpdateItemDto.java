package com.example.inventory.domain.items.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateItemDto {

    private String name;

    private String category;

    public static UpdateItemDto fromItemDto(ItemDto itemDto) {
        return new UpdateItemDto(itemDto.getName(), itemDto.getCategory());
    }

    public ItemDto toItemDto(String id) {
        return new ItemDto(id, name, category);
    }

}
