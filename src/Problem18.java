import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.ikickass.util.NavigableLinkedHashMap;

public class Problem18
{
    private static int[][] INTEGERS;
    private static int[][] SUMS;

    static
    {
        INTEGERS = new int[15][];

        INTEGERS[0]  = new int[] { 75 };
        INTEGERS[1]  = new int[] { 95, 64 };
        INTEGERS[2]  = new int[] { 17, 47, 82 };
        INTEGERS[3]  = new int[] { 18, 35, 87, 10 };
        INTEGERS[4]  = new int[] { 20, 4, 82, 47, 65 };
        INTEGERS[5]  = new int[] { 19, 1, 23, 75, 3, 34 };
        INTEGERS[6]  = new int[] { 88, 2, 77, 73, 7, 63, 67 };
        INTEGERS[7]  = new int[] { 99, 65, 4, 28, 6, 16, 70, 92 };
        INTEGERS[8]  = new int[] { 41, 41, 26, 56, 83, 40, 80, 70, 33 };
        INTEGERS[9]  = new int[] { 41, 48, 72, 33, 47, 32, 37, 16, 94, 29 };
        INTEGERS[10] = new int[] { 53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14 };
        INTEGERS[11] = new int[] { 70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57 };
        INTEGERS[12] = new int[] { 91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48 };
        INTEGERS[13] = new int[] { 63, 66, 4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31 };
        INTEGERS[14] = new int[] { 4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 4, 23 };

        SUMS = new int[15][];

        SUMS[INTEGERS.length - 1] = new int[INTEGERS[INTEGERS.length - 1].length];
        for(int i = 0 ; i < INTEGERS[INTEGERS.length - 1].length ; i++)
        {
            SUMS[INTEGERS.length - 1][i] = INTEGERS[INTEGERS.length - 1][i];
        }

        for(int i = INTEGERS.length - 2 ; i >= 0 ; i--)
        {
            SUMS[i] = new int[INTEGERS[i].length];
            for(int j = 0 ; j < INTEGERS[i].length ; j++)
            {
                SUMS[i][j] = Math.max(SUMS[i + 1][j], SUMS[i + 1][j + 1]) + INTEGERS[i][j];
            }
        }
    }

    public static int findMaxPath()
    {
        return SUMS[0][0];
    }

    public static final void main(final String[] args)
    {
        System.out.println(Arrays.deepToString(INTEGERS).replaceAll(", \\[", "\n").replaceAll("[\\[\\]]", ""));
        System.out.println();
        System.out.println(Arrays.deepToString(SUMS).replaceAll(", \\[", "\n").replaceAll("[\\[\\]]*", ""));
        System.out.println();
        System.out.println(findMaxPath());
    }
}
