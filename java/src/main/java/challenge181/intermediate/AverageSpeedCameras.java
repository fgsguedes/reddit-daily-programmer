package challenge181.intermediate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * [2014-09-24] Challenge #181 [Intermediate] Average Speed Cameras
 * https://www.reddit.com/r/dailyprogrammer/comments/2hcwzn/09242014_challenge_181_intermediate_average_speed/
 * <p/>
 * Description:
 * In the UK, a common safety measure on motorways is the so-called average speed cameras[1].
 * These, unlike normal speed cameras which measure a vehicle's speed instantaneously, have several connected cameras at intervals along a motorway.
 * The speed of a vehicle can be determined by dividing the distance between two cameras by the time it takes the vehicle to get from one to another.
 * This can be used to stop vehicles breaking the speed limit over long stretches of roads, rather than allowing vehicles to speed up after they are out of range.
 * The Home Office has contacted you to replace the aging software system in the cameras with something more up to date.
 * In this challenge, you will be given a number of speed cameras and their positions along a road, along with the speed limit.
 * You will then be given the camera logs for each camera in turn. From this data, you will work out which vehicles are breaking the speed limit.
 * <p/>
 * Input:
 * The first section of the input will contain the speed limit and the position of the speed cameras.
 * The speed limit may be in miles per hour or kilometres per hour. The lines will be in the format.
 * Speed limit is <limit> mph.
 * OR
 * Speed limit is <limit> km/h.
 * The lines describing the positions of the speed cameras will look like:
 * Speed camera <number> is <distance> metres down the motorway.
 * Speed camera number 1 will always have a distance of 0.
 * After this, you will get logs for each speed camera, like this:                                               ºº
 * Start of log for camera <number>:
 * Vehicle <registration number> passed camera <number> at <time>.
 * Vehicle <registration number> passed camera <number> at <time>.
 * ...
 * <p/>
 * Output:
 * For each vehicle that breaks the speed limit, print a line like so:
 * Vehicle <registration number> broke the speed limit by <amount>.
 * Where <amount> is in the local units.
 */
public class AverageSpeedCameras {

  private static final Pattern speedLimitPattern = Pattern.compile("Speed limit is ([0-9]+\\.[0-9]+) (mph|km/h)\\.");
  private static final Pattern cameraDistancePattern = Pattern.compile("Speed camera number ([0-9]+) is ([0-9]+) metres down the motorway\\.");
  private static final Pattern vehicleLogPattern = Pattern.compile("Vehicle ([0-9A-Z ]+) passed camera ([0-9]+) at (([0-9]{2}:){2}[0-9]{2})\\.");

  public static void main(String[] args) throws URISyntaxException, IOException {
    final List<Integer> cameraDistances = new ArrayList<>();
    final Map<String, List<LocalTime>> vehicleLog = new HashMap<>();

    final Path input = Paths.get(AverageSpeedCameras.class.getClassLoader().getResource("challenge181/intermediate/sample-input.txt").toURI());
    final List<String> inputLines = Files.readAllLines(input);

    final float speedLimit = Float.parseFloat(inputLines.get(0).replaceAll(speedLimitPattern.pattern(), "$1"));

    inputLines.stream().skip(1).forEach(line -> {
      final Matcher cameraDistancePattern = AverageSpeedCameras.cameraDistancePattern.matcher(line);
      if (cameraDistancePattern.matches()) {
        cameraDistances.add(Integer.parseInt(cameraDistancePattern.group(2)));

      } else {

        final Matcher vehicleLogMatcher = AverageSpeedCameras.vehicleLogPattern.matcher(line);
        if (vehicleLogMatcher.matches()) {
          final String vehicle = vehicleLogMatcher.group(1);
          final LocalTime time = LocalTime.parse(vehicleLogMatcher.group(3));
          vehicleLog.computeIfAbsent(vehicle, t -> new ArrayList()).add(time);
        }
      }
    });

    for (int i = 1; i < cameraDistances.size(); i++) {
      final int distance = cameraDistances.get(i) - cameraDistances.get(i - 1);
      for (Map.Entry<String, List<LocalTime>> entry : vehicleLog.entrySet()) {

        final List<LocalTime> logs = entry.getValue();
        final LocalTime first = logs.get(i - 1);
        final LocalTime second = logs.get(i);

        final float interval = first.until(second, SECONDS);;
        final double speed = 2.237 * distance / interval;

        if (speed > speedLimit) {
          System.out.printf("Vehicle %s broke the speed limit by %.1f mph.\n", entry.getKey(), (speed - speedLimit));
        }
      }
    }
  }
}
