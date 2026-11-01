package ro.adrian;

import java.util.Objects;

public class AthleteResult {

  private int number;
  private String name;
  private String countryCode;
  private String skiTimeResult;
  private String firstShootingRange;
  private String secondShootingRange;
  private String thirdShootingRange;
  private String finalTime;
  private int penaltySeconds;

  public AthleteResult(
      int number,
      String name,
      String countryCode,
      String skiTimeResult,
      String firstShootingRange,
      String secondShootingRange,
      String thirdShootingRange) {
    this.number = number;
    this.name = name;
    this.countryCode = countryCode;
    this.skiTimeResult = skiTimeResult;
    this.firstShootingRange = firstShootingRange;
    this.secondShootingRange = secondShootingRange;
    this.thirdShootingRange = thirdShootingRange;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getSkiTimeResult() {
    return skiTimeResult;
  }

  public void setSkiTimeResult(String skiTimeResult) {
    this.skiTimeResult = skiTimeResult;
  }

  public String getFirstShootingRange() {
    return firstShootingRange;
  }

  public void setFirstShootingRange(String firstShootingRange) {
    this.firstShootingRange = firstShootingRange;
  }

  public String getSecondShootingRange() {
    return secondShootingRange;
  }

  public void setSecondShootingRange(String secondShootingRange) {
    this.secondShootingRange = secondShootingRange;
  }

  public String getThirdShootingRange() {
    return thirdShootingRange;
  }

  public void setThirdShootingRange(String thirdShootingRange) {
    this.thirdShootingRange = thirdShootingRange;
  }

  public String getFinalTime() {
    return finalTime;
  }

  public void setFinalTime(String finalTime) {
    this.finalTime = finalTime;
  }

  public int getPenaltySeconds() {
    return penaltySeconds;
  }

  public void setPenaltySeconds(int penaltySeconds) {
    this.penaltySeconds = penaltySeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AthleteResult that = (AthleteResult) o;
    return number == that.number
        && Objects.equals(name, that.name)
        && Objects.equals(countryCode, that.countryCode)
        && Objects.equals(skiTimeResult, that.skiTimeResult)
        && Objects.equals(firstShootingRange, that.firstShootingRange)
        && Objects.equals(secondShootingRange, that.secondShootingRange)
        && Objects.equals(thirdShootingRange, that.thirdShootingRange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        number,
        name,
        countryCode,
        skiTimeResult,
        firstShootingRange,
        secondShootingRange,
        thirdShootingRange);
  }

  @Override
  public String toString() {
    return "AthleteResult{"
        + "number="
        + number
        + ", name='"
        + name
        + '\''
        + ", countryCode='"
        + countryCode
        + '\''
        + ", skiTimeResult='"
        + skiTimeResult
        + '\''
        + ", firstShootingRange='"
        + firstShootingRange
        + '\''
        + ", secondShootingRange='"
        + secondShootingRange
        + '\''
        + ", thirdShootingRange='"
        + thirdShootingRange
        + '\''
        + ", finalTime='"
        + finalTime
        + '\''
        + ", penaltySeconds="
        + penaltySeconds
        + '}';
  }
}
