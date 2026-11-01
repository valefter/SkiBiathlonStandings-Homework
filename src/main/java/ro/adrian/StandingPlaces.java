package ro.adrian;

public enum StandingPlaces {
  WINNER("Winner"),
  RUNNER_UP("Runner-up"),
  THIRD_PLACE("Third Place");

  private final String displayName;

  StandingPlaces(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
