import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem54
{
    private static final String FILE_NAME = "C:\\Users\\sridhar\\eclipse-workspace\\ProjectEuler\\data\\p054_poker.txt";

    public static class Card implements Comparable<Card>
    {
        public static enum Value
        {
         TWO(2),
         THREE(3),
         FOUR(4),
         FIVE(5),
         SIX(6),
         SEVEN(7),
         EIGHT(8),
         NINE(9),
         TEN(10),
         JACK(11),
         QUEEN(12),
         KING(13),
         ACE(14);

            private final int value;

            private static final Map<Integer, Value> VALUES = new HashMap<>();

            private Value(final int value)
            {
                this.value = value;
            }

            public int getValue()
            { return value; }

            public static Value getValue(final int value)
            {
                if(VALUES.isEmpty())
                {
                    for(final Value current : Value.values())
                    {
                        VALUES.put(current.getValue(), current);
                    }
                }

                return VALUES.get(value);
            }
        }

        public static enum Suit
        {
         CLUBS,
         DIAMONDS,
         HEARTS,
         SPADES;

            public static Suit getSuit(final char suit)
            {
                switch (suit)
                {
                    case 'C':
                        return CLUBS;
                    case 'D':
                        return DIAMONDS;
                    case 'H':
                        return HEARTS;
                    case 'S':
                        return SPADES;
                    default:
                        throw new IllegalArgumentException("Invalid suit: " + suit);
                }
            }
        }

        private final Value value;
        private final Suit  suit;

        public Card(final Value value, final Suit suit)
        {
            this.value = value;
            this.suit  = suit;
        }

        public Card(final String card)
        {
            final char[] characters = card.toCharArray();
            switch (characters[0])
            {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    this.value = Value.getValue(Integer.valueOf(Character.toString(characters[0])));
                    break;
                case 'T':
                    this.value = Value.getValue(10);
                    break;
                case 'J':
                    this.value = Value.getValue(11);
                    break;
                case 'Q':
                    this.value = Value.getValue(12);
                    break;
                case 'K':
                    this.value = Value.getValue(13);
                    break;
                case 'A':
                    this.value = Value.getValue(14);
                    break;
                default:
                    throw new IllegalArgumentException("Cards can only be 1-9 and T, J, Q, K, and A.");
            }
            this.suit = Suit.getSuit(characters[1]);
        }

        public Value getValue()
        { return value; }

        public Suit getSuit()
        { return suit; }

        @Override
        public int compareTo(final Card o)
        {
            final Integer myValue = value.getValue();
            return myValue.compareTo(o.getValue().getValue());
        }
    }

    public static class Hand
    {
        private final Card[]                 cards;
        private EnumMap<Card.Value, Integer> counts = null;

        public Hand(final Card one, final Card two, final Card three, final Card four, final Card five)
        {
            final List<Card> myCards = List.of(one, two, three, four, five);
            cards = myCards.stream().sorted().collect(Collectors.toList()).toArray(new Card[0]);
        }

        public Hand(final String hand)
        {
            try(final Scanner processor = new Scanner(hand))
            {
                processor.useDelimiter(" ");
                cards = processor.tokens().map(s -> new Card(s)).sorted().collect(Collectors.toList()).toArray(new Card[0]);
            }
        }

        public int value()
        {
            if(isRoyalFlush()) return 9000;
            else if(isStraightFlush()) return 8000 + cards[4].getValue().getValue();
            else
            {
                final var isFourOfAKind = isFourOfAKind();
                if(isFourOfAKind != null) return 7000 + isFourOfAKind.getValue();
                else
                {
                    final var isFullHouse = isFullHouse();
                    if(isFullHouse != null) return 6000 + (50 * isFullHouse[0].getValue()) + isFullHouse[1].getValue();
                    else if(isFlush()) return 5000;
                    else if(isStraight()) return 4000 + cards[4].getValue().getValue();
                    else
                    {
                        final var isThreeOfAKind = isThreeOfAKind();
                        if(isThreeOfAKind != null) return 3000 + isThreeOfAKind.getValue();
                        else
                        {
                            final var isTwoPairs = isTwoPairs();
                            if(isTwoPairs != null) return 2000 + (isTwoPairs[0].getValue() * 50) + isTwoPairs[1].getValue();
                            else
                            {
                                final var isOnePair = isOnePair();
                                if(isOnePair != null) return 1000 + isOnePair.getValue();
                                else return highCard();
                            }
                        }
                    }
                }
            }
        }

        public int highCard()
        {
            return cards[4].getValue().getValue();
        }

        private EnumMap<Card.Value, Integer> countValues()
        {
            if(counts != null) return counts;

            counts = new EnumMap<>(Card.Value.class);

            for(final Card current : cards)
            {
                final Card.Value currentValue = current.getValue();
                if(!counts.containsKey(currentValue)) counts.put(currentValue, 1);
                else counts.put(currentValue, counts.get(currentValue) + 1);
            }

            return counts;
        }

        private Card.Value isFourOfAKind()
        {
            final var counts = countValues();
            for(final var entry : counts.entrySet())
            {
                if(entry.getValue() == 4) return entry.getKey();
            }

            return null;
        }

        private Card.Value[] isFullHouse()
        {
            final Card.Value[] returnme = new Card.Value[2];
            final var          counts   = countValues();
            for(final var entry : counts.entrySet())
            {
                if(entry.getValue() == 3)
                {
                    for(final var secondEntry : counts.entrySet())
                    {
                        if(secondEntry.getKey() == entry.getKey()) continue;
                        else if(secondEntry.getValue() == 2)
                        {
                            returnme[0] = entry.getKey();
                            returnme[1] = secondEntry.getKey();
                            return returnme;
                        } else return null;
                    }
                }
            }

            return null;
        }

        private Card.Value isThreeOfAKind()
        {
            final var counts = countValues();
            for(final var entry : counts.entrySet())
            {
                if(entry.getValue() == 3)
                {
                    for(final var secondEntry : counts.entrySet())
                    {
                        if(secondEntry.getKey() == entry.getKey()) continue;
                        else if(secondEntry.getValue() == 2) return null;
                        else if(secondEntry.getValue() == 1) return entry.getKey();
                    }
                }
            }

            return null;
        }

        private Card.Value isOnePair()
        {
            final var counts = countValues();
            for(final var entry : counts.entrySet())
            {
                if(entry.getValue() == 2)
                {
                    for(final var secondEntry : counts.entrySet())
                    {
                        if(secondEntry.getKey() == entry.getKey()) continue;
                        else if(secondEntry.getValue() > 1) return null;
                    }

                    return entry.getKey();
                }
            }

            return null;
        }

        private Card.Value[] isTwoPairs()
        {
            final var          counts   = countValues();
            final Card.Value[] returnme = new Card.Value[2];
            for(final var entry : counts.entrySet())
            {
                if(entry.getValue() == 2)
                {
                    for(final var secondEntry : counts.entrySet())
                    {
                        if(secondEntry.getKey() == entry.getKey()) continue;
                        else if(secondEntry.getValue() == 2)
                        {
                            if(entry.getKey().getValue() > secondEntry.getKey().getValue())
                            {
                                returnme[0] = entry.getKey();
                                returnme[1] = secondEntry.getKey();
                            } else
                            {
                                returnme[1] = entry.getKey();
                                returnme[0] = secondEntry.getKey();
                            }
                            return returnme;
                        }
                    }
                }
            }

            return null;
        }

        private boolean isStraightFlush()
        { return isFlush() && isStraight() && highCard() < 14; }

        private boolean isRoyalFlush()
        { return isFlush() && isStraight() && highCard() == 14; }

        private boolean isFlush()
        {
            final Card.Suit suit = cards[0].getSuit();
            for(final Card current : cards)
            {
                if(!current.getSuit().equals(suit)) return false;
            }
            return true;
        }

        private boolean isStraight()
        {
            final int[] values = new int[5];
            for(int i = 0 ; i < 5 ; i++)
            {
                values[i] = cards[i].getValue().getValue();
            }
            return values[4] == values[3] + 1 && values[3] == values[2] + 1 &&
                   values[2] == values[1] + 1 &&
                   values[1] == values[0] + 1;
        }
    }

    private static Stream<String> readFile(final String fileName) throws FileNotFoundException
    {
        final Scanner input = new Scanner(new FileReader(fileName));
        input.useDelimiter("\n");

        return input.tokens();
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println(readFile(FILE_NAME).map(s -> new String[] { s.substring(0, 14), s.substring(15) })
                                              .map(sa -> new Hand[]
                                              { new Hand(sa[0]), new Hand(sa[1]) })
                                              .map(ha -> new int[]
                                              { ha[0].value(), ha[1].value() })
                                              .filter(ia -> ia[0] > ia[1])
                                              .count());
    }
}
