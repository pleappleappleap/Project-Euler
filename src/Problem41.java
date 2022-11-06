import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Problem41
{
    public static void main(final String[] args)
    {
        final List<Long> times = new ArrayList<>();
        times.add(System.nanoTime());
        System.out.println(times.get(times.size() - 1) / 1000000000l);
        
        Problem7.isPrime(7654321l);
        times.add(System.nanoTime());
        System.out.println((times.get(times.size() - 1) - times.get(times.size() - 2)) / 1000000000l);
        
        final Set<Long> pandigitals = Problem38.permute(7, true);
        times.add(System.nanoTime());
        System.out.println((times.get(times.size() - 1) - times.get(times.size() - 2)) / 1000000000l);
        
        System.out.println(pandigitals);
        times.add(System.nanoTime());
        System.out.println((times.get(times.size() - 1) - times.get(times.size() - 2)) / 1000000000l);
        
        for(final Long current : pandigitals)
        {
            System.out.println(current);
            if(Problem7.isPrime(current))
            {
                times.add(System.nanoTime());
                System.out.println((times.get(times.size() - 1) - times.get(times.size() - 2)) / 1000000000l);
                
                System.out.println(current);
                break;
            }
            times.add(System.nanoTime());
            System.out.println((times.get(times.size() - 1) - times.get(times.size() - 2)) / 1000000000l);
        }
    }
}
