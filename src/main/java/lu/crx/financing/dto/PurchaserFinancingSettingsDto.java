package lu.crx.financing.dto;

public record PurchaserFinancingSettingsDto(long id, long creditorId, int annualRateInBps, long purchaserId) {

}
