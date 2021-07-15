public class tickerToName {
    public static String convert(String ticker){
        switch (ticker) {
            case "BTC":
                return "bitcoin";
            case "ETH":
                return "ethereum";
            case "ADA":
                return "cardano";
            default:
                return "INVALID_TICKER";
        }
    }
}
