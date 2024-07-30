package crud_project.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank
    String name
) {}