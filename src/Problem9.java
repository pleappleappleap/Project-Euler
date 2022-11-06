import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Problem9
{
    public static class Triple<T>
    {
        public final T first;
        public final T second;
        public final T third;

        Triple(final T first, final T second, final T third)
        {
            this.first  = first;
            this.second = second;
            this.third  = third;
        }

        public static <T> Builder<T> builder()
        {
            return new Builder<>();
        }

        static class Builder<T>
        {
            private T       first;
            private T       second;
            private T       third;
            private boolean built;

            private Builder()
            {
                this.first  = null;
                this.second = null;
                this.third  = null;
                built       = false;
            }

            public Builder<T> setFirst(final T first)
            {
                if(this.first == null || built) this.first = first;
                else throw new IllegalStateException("Builder can only be used once.");
                return this;
            }

            public Builder<T> setSecond(final T second)
            {
                if(this.second == null || built) this.second = second;
                else throw new IllegalStateException("Builder can only be used once.");
                return this;
            }

            public Builder<T> setThird(final T third)
            {
                if(this.third == null || built) this.third = third;
                else throw new IllegalStateException("Builder can only be used once.");
                return this;
            }

            public Triple<T> build()
            {
                built = true;
                return new Triple<>(this.first, this.second, this.third);
            }
        }
        
        public String toString()
        {
            return first.toString() + ", " + second.toString() + ", " + third.toString();
        }
    }

    public static class PythagoreanTriple extends Triple<Integer>
    {
        protected PythagoreanTriple(final Integer first, final Integer second, final Integer third)
        {
            super(first, second, third);
        }
        
        @SuppressWarnings("unchecked") public static Builder builder()
        {
            return new Builder();
        }

        static class Builder extends Triple.Builder<Integer>
        {
            private Integer first;
            private Integer second;
            private boolean built;

            private Builder()
            {
                this.first  = null;
                this.second = null;
                built       = false;
            }

            public Builder setFirst(final Integer first)
            {
                if(this.first == null || built) this.first = first;
                else throw new IllegalStateException("Builder can only be used once.");
                return this;
            }

            public Builder setSecond(final Integer second)
            {
                if(this.second == null || built) this.second = second;
                else throw new IllegalStateException("Builder can only be used once.");
                return this;
            }

            public PythagoreanTriple build()
            {
                if(first == null ||
                   second == null) throw new IllegalStateException("Cannot create Pythagorean triple with null sides.");
                else
                {
                    final Integer      hypotenuseSquare = first * first + second * second;
                    final BigInteger[] hypotenuse       = new BigInteger(hypotenuseSquare.toString()).sqrtAndRemainder();
                    if(!hypotenuse[1].equals(BigInteger.ZERO))
                    {
                        throw new ArithmeticException("Not a Pythagorean triple.");
                    } else
                    {
                        built = true;
                        return new PythagoreanTriple(first, second, hypotenuse[0].intValue());
                    }
                }
            }
        }

        public int sum()
        {
            return first + second + third;
        }

        public long product()
        {
            return first * second * third;
        }
    }

    private static List<PythagoreanTriple> pythagoreanTriples;

    public static void main(final String[] args)
    {
        final int                     LIMIT               = 1000;
        final List<PythagoreanTriple> PYTHAGOREAN_TRIPLES = getPythagoreanTriples(LIMIT);

        for(final PythagoreanTriple current : PYTHAGOREAN_TRIPLES)
        {
            if(current.sum() == LIMIT)
            {
                System.out.println(current);
                System.out.println(current.product());
            }
        }
    }

    public static List<PythagoreanTriple> getPythagoreanTriples(final int limit)
    {
        if(pythagoreanTriples == null)
        {
            pythagoreanTriples = new ArrayList<>();

            for(int i = 1 ; i < limit ; i++)
            {
                for(int j = i ; j < limit ; j++)
                {
                    try
                    {
                        pythagoreanTriples.add(PythagoreanTriple.builder().setFirst(i).setSecond(j).build());
                    } catch(ArithmeticException e)
                    {
                    }
                }
            }
        }
        return pythagoreanTriples;
    }
}
