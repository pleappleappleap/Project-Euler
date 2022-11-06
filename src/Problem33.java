import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class Problem33
{
    private static final byte[]      FLAGS   = { 0b0001, 0b0010, 0b0100, 0b1000 };
    private static final MathContext CONTEXT = new MathContext(20, RoundingMode.HALF_UP);

    public static void main(final String[] args)
    {
        BigDecimal product = BigDecimal.ONE;

        for(int i = 1 ; i < 100 ; i++)
        {
            if(i % 10 == 0) continue;
            for(int j = i ; j < 100 ; j++)
            {
                if(j % 10 == 0) continue;
                final int[] canceled = cancel(i, j);

                if(canceled == null) continue;

                final BigDecimal uncanceledFraction = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(j), CONTEXT);
                final BigDecimal canceledFraction   =
                        BigDecimal.valueOf(canceled[0]).divide(BigDecimal.valueOf(canceled[1]), CONTEXT);

                if(uncanceledFraction.equals(BigDecimal.ONE) || canceledFraction.equals(BigDecimal.ONE)) continue;

                if(uncanceledFraction.equals(canceledFraction)) product = product.multiply(canceledFraction);
            }
        }

        System.out.println(product);
    }

    private static int[] cancel(final int a, final int b)
    {
        final List<Integer> A = List.of(a / 10, a % 10);
        final List<Integer> B = List.of(b / 10, b % 10);

        if(A.get(1) == 0 || B.get(1) == 0) return null;

        byte flags = 0;

        if(B.contains(A.get(0))) flags |= FLAGS[0];
        if(B.contains(A.get(1))) flags |= FLAGS[1];
        if(A.contains(B.get(0))) flags |= FLAGS[2];
        if(A.contains(B.get(1))) flags |= FLAGS[3];

        if((flags & 0b0011) == 0 || (flags & 0b1100) == 0) return null;

        int[] returnme = new int[2];

        if((flags & 0b1100) == 0b1100) returnme[0] = 1;
        else if((flags & 0b1100) == 0b1000) returnme[0] = A.get(1);
        else returnme[0] = A.get(0);

        if((flags & 0b0011) == 0b0011) returnme[1] = 1;
        else if((flags & 0b0011) == 0b0010) returnme[1] = B.get(1);
        else returnme[1] = B.get(0);

        if(returnme[0] == 0 || returnme[1] == 0) return null;

        return returnme;
    }
}
