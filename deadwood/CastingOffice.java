public class CastingOffice extends Room {
    private static int[] rankPricesCash;
    private static int[] rankPricesCredits;

    public CastingOffice(int cashPrices[], int creditPrices[]) {
        super("Casting Office");

        rankPricesCash = cashPrices;
        rankPricesCredits = creditPrices;

    }

    public int getPrice(int rank, Currency c) {
        if (c == Currency.CASH) {
            return rankPricesCash[rank];
        } else {
            return rankPricesCredits[rank];
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("$\tCredits\n");
        for(int i = 0; i < rankPricesCash.length; i++){
            str.append(rankPricesCash[i] + "\t" + rankPricesCredits[i] + "\n");
        }

        return str.toString();
    }
}
