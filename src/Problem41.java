public class Problem41
{
    public static void main(final String[] args)
    {
        final long BEGIN = System.nanoTime();
        System.out.println(Problem38.permute(7, true).stream().filter(l -> Problem7.isPrime(l)).findFirst().get());
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
