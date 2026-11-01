package ro.adrian;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

  private final FileLinesReader fileLinesReader;

  public CSVReader(FileLinesReader fileLinesReader) {
    this.fileLinesReader = fileLinesReader;
  }

  public List<AthleteResult> readCSV(String fileName) throws IOException {
    List<String> csvLines = fileLinesReader.readLines(fileName);
    List<AthleteResult> athletes = new ArrayList<>();

    if (csvLines.isEmpty()) {
      return athletes;
    }

    csvLines.removeFirst();
    for (String line : csvLines) {
      AthleteResult athleteResult = null;
      try {
        athleteResult = parseCsvLine(line);
      } catch (ParseException e) {
        System.out.println("Error while parsing line \"" + line + "\"");
      }
      if (athleteResult != null) {
        athletes.add(athleteResult);
      }
    }

    return athletes;
  }

  private AthleteResult parseCsvLine(String line) throws ParseException {
    String[] lineParts = line.split(",");
    if (lineParts.length != 7) {
      throw new ParseException("Invalid csv line: " + line, 0);
    }

    String time = lineParts[3];
    if (!isValidTime(time)) {
      throw new ParseException("Invalid csv line: " + line, 0);
    }

    for (int i = 4; i <= 6; i++) {
      if (!isValidShootingRange(lineParts[i])) {
        throw new ParseException("Invalid csv line: " + line, 0);
      }
    }

    int number;

    try {
      number = Integer.parseInt(lineParts[0]);
    } catch (NumberFormatException e) {
      throw new ParseException("Invalid csv line: " + line, 0);
    }

    String name = lineParts[1];
    String countryCode = lineParts[2];

    if (name.isBlank() || countryCode.isBlank()) {
      throw new ParseException("Invalid csv line: " + line, 0);
    }

    String skiTimeResult = lineParts[3];
    String firstShootingRange = lineParts[4];
    String secondShootingRange = lineParts[5];
    String thirdShootingRange = lineParts[6];

    return new AthleteResult(
        number,
        name,
        countryCode,
        skiTimeResult,
        firstShootingRange,
        secondShootingRange,
        thirdShootingRange);
  }

  private boolean isValidShootingRange(String shootingRange) {
    return shootingRange.length() == 5 && isValidCharacter(shootingRange);
  }

  private boolean isValidCharacter(String shootingRange) {
    for (int i = 0; i < shootingRange.length(); i++) {
      char shotResult = shootingRange.charAt(i);
      if (shotResult != 'x' && shotResult != 'o') {
        return false;
      }
    }
    return true;
  }

  private boolean isValidTime(String time) {
    String[] timeParts = time.split(":");

    if (timeParts.length != 2) {
      return false;
    }

    try {
      int minutes = Integer.parseInt(timeParts[0]);
      int seconds = Integer.parseInt(timeParts[1]);

      return minutes >= 0 && seconds >= 0 && seconds < 60;

    } catch (NumberFormatException e) {
      return false;
    }
  }
}
