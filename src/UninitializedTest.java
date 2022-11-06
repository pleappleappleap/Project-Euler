import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UninitializedTest
{
    public static void main(final String[] args)
    {
        Collector<BigInteger,
        Stream<BigInteger>,
        Stream<BigInteger>> streamCollector = Collector.of(() -> Arrays.stream(new BigInteger[0]),
                                                           (s, bi) -> Stream.concat(s, Stream.of(bi)),
                                                           (s1, s2) -> Stream.concat(s1, s2),
                                                           s -> s);
        Collector<BigInteger,
        Stream<BigInteger>,
        Stream<BigInteger>> streamCollector2 = Collector.of(() -> Arrays.stream(new BigInteger[0]),
                                                           (s, bi) -> Stream.concat(s, Stream.of(bi)),
                                                           (s1, s2) -> Stream.concat(s1, s2),
                                                           s -> s);
        
        var blong = Stream.of(BigInteger.ONE, BigInteger.TWO, BigInteger.TEN).collect(streamCollector);
        System.out.println(blong.getClass().getName());
    }
}
