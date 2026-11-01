package ro.adrian;

import java.util.Comparator;

public class TimeResultComparator implements Comparator<AthleteResult> {

  @Override
  public int compare(AthleteResult athleteResult1, AthleteResult athleteResult2) {
    int time1 = convertToSeconds(athleteResult1.getFinalTime());
    int time2 = convertToSeconds(athleteResult2.getFinalTime());

    return Integer.compare(time1, time2);
  }

  private int convertToSeconds(String time) {
    String[] parts = time.split(":");

    int minutes = Integer.parseInt(parts[0]);
    int seconds = Integer.parseInt(parts[1]);

    return minutes * 60 + seconds;
  }
}
