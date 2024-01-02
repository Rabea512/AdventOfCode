package year_2021.Day_08;

import java.util.Comparator;
import java.util.List;

class NumbersComparator implements Comparator<String>
{
    private List<Integer> sortOrder = List.of(2,3,4,7,6,5);

    public int compare(String c1, String c2)
    {
        int a1 = c1.length();
        int a2 = c2.length();
        return sortOrder.indexOf(a1) - sortOrder.indexOf(a2);
    }
}