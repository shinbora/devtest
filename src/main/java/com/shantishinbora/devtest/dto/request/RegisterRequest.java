package com.shantishinbora.devtest.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
  private String name;
  private String email;
  private String phone;
}
