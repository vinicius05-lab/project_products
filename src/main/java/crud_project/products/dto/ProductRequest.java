package crud_project.products.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(

    @NotBlank
    String name,

    @NotBlank
    String description,

    double price,
    
    int quantity,

    Long category_id
) {}