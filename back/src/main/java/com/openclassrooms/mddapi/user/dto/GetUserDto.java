package com.openclassrooms.mddapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String email;
    private String username;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
