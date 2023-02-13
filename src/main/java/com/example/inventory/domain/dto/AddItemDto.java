package com.example.inventory.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddItemDto {

    private String name;

    private String category;

    public static AddItemDto fromItemDto(ItemDto itemDto) {
        return new AddItemDto(itemDto.getName(), itemDto.getCategory());
    }

    public ItemDto toItemDto() {
        return new ItemDto(null, name, category);
    }

}
