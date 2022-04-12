package edu.aleksandrTreskov.mms.dto;

import lombok.Data;

@Data
public class RecoverPasswordDTO {
    private String loginParameter;
    private String password;
}
