package edu.aleksandrTreskov.mms.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String currPassword;
    private String newPassword;

}
