package ro.adrian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AthleteResultOrchestrator {

  private final CSVReader csvReader;

  public AthleteResultOrchestrator(CSVReader csvReader) {
    this.csvReader = csvReader;
  }

  private List<AthleteResult> calculateShootingRangePenalty(String fileName) {
    List<AthleteResult> athleteResults = new ArrayList<>();

    try {
      athleteResults = csvReader.readCSV(fileName);
    } catch (IOException e) {
      throw new CSVFileReadException("Could not read CSV file");
    }

    for (AthleteResult athlete : athleteResults) {
      StringBuilder builder = new StringBuilder();

      String shootingResults =
          builder
              .append(athlete.getFirstShootingRange())
              .append(athlete.getSecondShootingRange())
              .append(athlete.getThirdShootingRange())
              .toString();

      int secondsPenalty = 0;

      for (int i = 0; i < shootingResults.length(); i++) {
        char shotResult = shootingResults.charAt(i);
        if (shotResult == 'o') {
          secondsPenalty += 10;
        }
      }
      athlete.setPenaltySeconds(secondsPenalty);
    }
    return athleteResults;
  }

  public List<AthleteResult> calculateAthletesTime(String fileName) {
    List<AthleteResult> athleteResults = calculateShootingRangePenalty(fileName);

    for (int i = 0; i < athleteResults.size(); i++) {
      String result = athleteResults.get(i).getSkiTimeResult();

      String[] parts = result.split(":");
      int minutes = Integer.parseInt(parts[0]);
      int seconds = Integer.parseInt(parts[1]);

      int penaltySeconds = athleteResults.get(i).getPenaltySeconds();
      seconds += penaltySeconds;

      minutes += seconds / 60;
      seconds = seconds % 60;

      String finalTime = minutes + ":" + seconds;

      athleteResults.get(i).setFinalTime(finalTime);
    }
    return athleteResults;
  }
}
