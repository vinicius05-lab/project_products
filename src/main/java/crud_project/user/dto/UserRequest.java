package crud_project.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(

    @NotBlank
    String name,

    @NotBlank
    String email,

    @NotBlank
    String password
) {}