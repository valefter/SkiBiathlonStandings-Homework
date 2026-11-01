package ro.adrian;

import java.util.List;

public class SkiBiathlonStandings {

  private final AthleteResultOrchestrator athleteResultOrchestrator;

  public SkiBiathlonStandings(AthleteResultOrchestrator athleteResultOrchestrator) {
    this.athleteResultOrchestrator = athleteResultOrchestrator;
  }

  public String finalStandings(String fileName) {

    List<AthleteResult> athletes = athleteResultOrchestrator.calculateAthletesTime(fileName);

    athletes.sort(new TimeResultComparator());

    StandingPlaces[] places = StandingPlaces.values();
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < 3; i++) {
      if (i == athletes.size()) {
        break;
      }

      AthleteResult athlete = athletes.get(i);

      result.append(
          String.format(
              "%s - %s %s (%s + %d)",
              places[i].getDisplayName(),
              athlete.getName(),
              athlete.getFinalTime(),
              athlete.getSkiTimeResult(),
              athlete.getPenaltySeconds()));

      result.append(System.lineSeparator());
    }
    return result.toString();
  }
}
