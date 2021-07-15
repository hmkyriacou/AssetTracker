import java.time.LocalDate;

public interface AssetObject {
    String getAssetName();
    void setAssetName(String assetName);
    Double getPurchaseValue();
    void setPurchaseValue(Double purchaseValue);
    Double getCurrentValue();
    void setCurrentValue(Double currentValue);
    LocalDate getPurchaseDate();
    void setPurchaseDate(LocalDate purchaseDate);
    LocalDate getSellDate();
    void setSellDate(LocalDate sellDate);
    Double getCurrentPL();
    void setCurrentPL(Double currentPL);
    Double getAmount();
    void setAmount(Double amount);

    void getData();
}
