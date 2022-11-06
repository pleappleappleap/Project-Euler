import org.ikickass.number.PentagonalNumber;
import org.ikickass.number.SquareNumber;
import org.ikickass.number.TriangularNumber;

public class TripleNumberTest
{
    public static void main(final String[] args)
    {
        TriangularNumber.stream().parallel().filter(n -> PentagonalNumber.isPentagonal(n)).map(n ->
        {
            System.err.println(n);
            return n;
        }).filter(n -> SquareNumber.isSquare(n)).limit(2l).forEach(n -> System.out.println(n));
    }
}
