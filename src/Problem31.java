import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Problem31
{
    private static final int                       AMOUNT  = 200;
    private static final Map<Integer, Set<Amount>> AMOUNTS = new HashMap<>();

    public static enum Coin
    {
     ONE(1),
     TWO(2),
     FIVE(5),
     TEN(10),
     TWENTY(20),
     FIFTY(50),
     POUND(100),
     TWOPOUND(200);

        private final int value;

        private Coin(final int value)
        {
            this.value = value;
        }

        public int value()
        {
            return value;
        }
    }

    public static class Amount extends EnumMap<Coin, Integer>
    {
        private static final long serialVersionUID = 8058386934581246543L;

        public static final Amount ONE = new Amount(Coin.ONE);

        public Amount()
        {
            super(Coin.class);
            for(final Coin coin : Coin.values())
            {
                this.put(coin, 0);
            }
        }

        public Amount(final Coin coin)
        {
            this();
            this.add(coin);
        }

        public Amount(final Amount copyMe)
        {
            super(Coin.class);
            for(final Coin coin : Coin.values())
            {
                this.put(coin, copyMe.get(coin));
            }
        }

        public int total()
        {
            int returnme = 0;
            for(final Map.Entry<Coin, Integer> entry : entrySet())
            {
                returnme += entry.getKey().value() * entry.getValue();
            }
            return returnme;
        }

        public void add(final Coin coin)
        {
            this.put(coin, this.getOrDefault(coin, 0) + 1);
        }
    }

    private static Set<Amount> copyAmountSet(final Set<Amount> amounts)
    {
        final Set<Amount> returnme = new HashSet<>();
        for(final Amount amount : amounts)
        {
            returnme.add(new Amount(amount));
        }
        return returnme;
    }

    private static Set<Amount> combinations(final int amount)
    {
        if(amount < 0) throw new IllegalArgumentException("Cannot process negative amounts.");
        else if(amount == 0) return new HashSet<>();
        else if(AMOUNTS.containsKey(amount)) return copyAmountSet(AMOUNTS.get(amount));
        else
        {
            final Set<Amount> returnme = new HashSet<>();
            final Coin[]      values   = Coin.values();
            for(int i = values.length - 1 ; i >= 0 ; i--)
            {
                if(amount < values[i].value()) continue;
                else if(amount == values[i].value()) returnme.add(new Amount(values[i]));
                else
                {
                    final Set<Amount> subtotals = combinations(amount - values[i].value());
                    for(final Amount current : subtotals) current.add(values[i]);
                    returnme.addAll(subtotals);
                }
            }
            AMOUNTS.put(amount, returnme);
            return returnme;
        }
    }

    public static void main(final String[] args)
    {
        for(int i = 0 ; i <= AMOUNT ; i++) System.out.println("i = " + i + ", combinations(i).size() = " + combinations(i).size());
    }
}
