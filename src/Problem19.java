import java.util.Calendar;
import java.util.GregorianCalendar;

public class Problem19
{
    public static final int BEGINNING_YEAR = 1901;
    public static final int ENDING_YEAR    = 2000;

    public static void main(final String[] args)
    {
        int count = 0;
        for(int year = BEGINNING_YEAR ; year <= ENDING_YEAR ; year++)
        {
            for(int month = 1 ; month <= 12 ; month++)
            {
                final GregorianCalendar firstOfTheMonth = new GregorianCalendar(year, month, 1);
                final int dayOfWeek = firstOfTheMonth.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek == Calendar.SUNDAY) count++;
            }
        }
        System.out.println(count);
    }
}
