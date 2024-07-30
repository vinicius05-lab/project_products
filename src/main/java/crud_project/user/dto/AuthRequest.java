package crud_project.user.dto;

public record AuthRequest(
    String email,
    String password
) {}