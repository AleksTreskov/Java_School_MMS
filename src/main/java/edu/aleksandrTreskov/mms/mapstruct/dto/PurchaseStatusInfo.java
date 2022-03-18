package edu.aleksandrTreskov.mms.mapstruct.dto;

import edu.aleksandrTreskov.mms.common.PurchaseStatus;
import lombok.Data;

@Data
public class PurchaseStatusInfo {
    private long purchaseId;
    private PurchaseStatus purchaseStatus;
}
