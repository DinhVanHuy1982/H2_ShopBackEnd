package com.example.h2_shop.exception.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageError {
    private String code;

    private String content;
}
