package challenge176.easy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * [2014-8-22] Challenge #176 [Easy] Pivot Table
 * https://www.reddit.com/r/dailyprogrammer/comments/2eajf7/8222014_challenge_176_easy_pivot_table/
 *
 * Description:
 * An interesting way to represent data is a pivot table. If you use spreadsheet programs like Excel you might have seen these before. If not then you are about to enjoy it.
 * Say you have data that is related in three parts. We can field this in a table with column and rows and the middle intersection is a related field. For this challenge you will need to make a pivot table for a wind energy farm. These farms of wind mills run several windmills with tower numbers. They generate energy measured in kilowatt hours (kWh).
 * You will need to read in raw data from the field computers that collect readings throughout the week. The data is not sorted very well. You will need to display it all in a nice pivot table.
 * Top Columns should be the days of the week. Side Rows should be the tower numbers and the data in the middle the total kWh hours produced for that tower on that day of the week.
 *
 * Input:
 * The challenge input is 1000 lines of the computer logs. You will find it HERE - gist of it[1]
 * The log data is in the format:
 *   (tower #) (day of the week) (kWh)
 *
 * Output:
 * A nicely formatted pivot table to report to management of the weekly kilowatt hours of the wind farm by day of the week.
 *
 * Date: 2014-09-12
 * @author fgsguedes
 */
public class PivotTable {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final Map<String, Map<String, Integer>> data = new TreeMap<>();
    final String[] days = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    final Path input = Paths.get(PivotTable.class.getClassLoader().getResource("challenge176/easy/input.txt").toURI());

    Files.readAllLines(input, UTF_8).forEach(line -> {
      final String[] split = line.split(" ");
      data.computeIfAbsent(split[0], k -> new HashMap<>()).merge(split[1], Integer.parseInt(split[2]), Integer::sum);
    });

    System.out.printf("Tower | %4s | %4s | %4s | %4s | %4s | %4s | %4s |\n", days);
    data.forEach((windMill, weekDaysMap) -> {
      System.out.printf("%-5s | ", windMill);
      Arrays.stream(days).forEach(day -> System.out.printf("%4s | ", weekDaysMap.get(day)));
      System.out.println();
    });
  }
}
