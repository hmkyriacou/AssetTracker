import com.litesoftwares.coingecko.CoinGeckoApi;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.constant.Currency;
import com.litesoftwares.coingecko.domain.Ping;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class CryptoAsset implements AssetObject{

    @CsvBindByName (column = "Asset")
    private String assetName;

    @CsvBindByName (column = "Cost")
    private Double purchaseValue;

    @CsvBindByName (column = "Amount")
    private Double amount;

    private Double currentValue = null;

    @CsvBindByName (column = "Date")
    @CsvDate("yyyy-MM-dd")
    private LocalDate purchaseDate;

    private LocalDate sellDate = null;

    private Double currentPL = null;

    /* ------------------------------------------------------------- */

    public CryptoAsset(){

    }

    public CryptoAsset(String assetName, Double purchaseValue, Double amount, LocalDate purchaseDate) {
        this.assetName = assetName;
        this.purchaseValue = purchaseValue;
        this.amount = amount;
        this.purchaseDate = purchaseDate;

    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(Double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }

    public Double getCurrentPL() {
        return currentPL;
    }

    public void setCurrentPL(Double currentPL) {
        this.currentPL = currentPL;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void getData() {
        CoinGeckoApiClient cg = new CoinGeckoApiClientImpl();
        String name = tickerToName.convert(this.assetName);
        Map<String, Map<String, Double>> res = cg.getPrice(name, Currency.USD);
        Double price = res.get(name).get("usd");

        this.currentValue = this.amount * price;
        this.currentPL = this.currentValue - this.purchaseValue;
    }
}
