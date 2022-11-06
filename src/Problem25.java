import java.math.BigInteger;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem25
{
    public static void main(final String[] args)
    {
        final Iterator<BigInteger> iterator = Problem2.fibonacciNumbersByIteration().iterator();
        final AtomicInteger i = new AtomicInteger(1);
        
        while(iterator.hasNext())
        {
            final BigInteger current = iterator.next();
            if(current.toString().length() == 1000)
            {
                System.out.println(i);
                break;
            } else i.incrementAndGet();
        }
    }
}
