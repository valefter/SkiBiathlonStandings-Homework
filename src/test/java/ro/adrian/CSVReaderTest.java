package ro.adrian;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CSVReaderTest {

  @Test
  public void testReadValidCSV() throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    List<String> mockFile =
        new ArrayList<>(
            List.of(
                "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                "11,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo"));

    when(fileLinesReader.readLines("mockFile")).thenReturn(mockFile);

    AthleteResult athleteResult =
        new AthleteResult(11, "Umar Jorgson", "SK", "30:27", "xxxox", "xxxxx", "xxoxo");

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).hasSize(1).contains(athleteResult);
  }

  @Test
  public void testReturnMultipleAthletesWhenCSVContainsMultipleRows() throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    List<String> mockFile =
        new ArrayList<>(
            List.of(
                "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                "11,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo",
                "1,Jimmy Smiles,UK,29:15,xxoox,xooxo,xxxxo"));

    when(fileLinesReader.readLines("mockFile")).thenReturn(mockFile);

    AthleteResult athleteResult1 =
        new AthleteResult(11, "Umar Jorgson", "SK", "30:27", "xxxox", "xxxxx", "xxoxo");
    AthleteResult athleteResult2 =
        new AthleteResult(1, "Jimmy Smiles", "UK", "29:15", "xxoox", "xooxo", "xxxxo");

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).hasSize(2).contains(athleteResult1, athleteResult2);
  }

  @ParameterizedTest
  @MethodSource("csvLinesWithNoAthletes")
  public void testReturnEmptyListWhenCSVContainsNoAthletes(List<String> mockFile)
      throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    when(fileLinesReader.readLines("mockFile")).thenReturn(mockFile);

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).isEmpty();
  }

  private static Stream<Arguments> csvLinesWithNoAthletes() {
    return Stream.of(
        Arguments.of(
            new ArrayList<>(
                List.of(
                    "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting"))),
        Arguments.of(new ArrayList<>(List.of(""))),
        Arguments.of(new ArrayList<>(List.of())));
  }

  @ParameterizedTest
  @MethodSource("columns")
  public void testSkipRowWhenCSVHasInvalidNumberOfColumns(List<String> mockFile)
      throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    when(fileLinesReader.readLines("mockFile")).thenReturn(mockFile);

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).isEmpty();
  }

  private static Stream<Arguments> columns() {
    return Stream.of(
        Arguments.of(
            new ArrayList<>(
                List.of(
                    "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                    "11,Umar Jorgson,30:27,xxxox,xxxxx,xxoxo"))),
        Arguments.of(
            new ArrayList<>(
                List.of(
                    "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                    ""))),
        Arguments.of(
            new ArrayList<>(
                List.of(
                    "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                    "11,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo,abcd"))));
  }

  @ParameterizedTest
  @MethodSource("invalidInputs")
  public void testSkipRowWhenCSVContainsInvalidInput(List<String> mockFile) throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    when(fileLinesReader.readLines("mockFile")).thenReturn(new ArrayList<>(mockFile));

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).isEmpty();
  }

  private static Stream<Arguments> invalidInputs() {
    String header =
        "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting";
    return Stream.of(
        Arguments.of(List.of(header, ",Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "abc,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:27,xxxox,xxxxx")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo,extra")),
        Arguments.of(List.of(header, "11,,SK,30:27,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,3027,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:60,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,abc:27,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,-30:27,xxxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:27,xxox,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:27,xxxxxx,xxxxx,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,SK,30:27,xxxox,xxaox,xxoxo")),
        Arguments.of(List.of(header, "11,,SK,30:27,xxxox,xxxox,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson,,30:27,xxxox,xxxox,xxoxo")),
        Arguments.of(List.of(header, "11,Umar Jorgson, ,30:27,xxxox,xxxox,xxoxo")),
        Arguments.of(List.of(header, "11, ,SK,30:27,xxxox,xxxox,xxoxo")));
  }

  @Test
  public void testContinueReadingCSVAfterInvalidRow() throws IOException {
    // given
    FileLinesReader fileLinesReader = mock(FileLinesReader.class);
    CSVReader csvReader = new CSVReader(fileLinesReader);
    List<String> mockFile =
        new ArrayList<>(
            List.of(
                "AthleteNumber,AthleteName,CountryCode,SkiTimeResult,FirstShootingRange,SecondShooting,ThirdShooting",
                "11,Umar Jorgson,SK,30:27,xxxox,xxxxx,xxoxo",
                "15,John Peterson,SK,abc:27,xxxox,xxxxx,xxoxo",
                "1,Jimmy Smiles,UK,29:15,xxoox,xooxo,xxxxo"));

    when(fileLinesReader.readLines("mockFile")).thenReturn(mockFile);

    AthleteResult athleteResult1 =
        new AthleteResult(11, "Umar Jorgson", "SK", "30:27", "xxxox", "xxxxx", "xxoxo");
    AthleteResult athleteResult2 =
        new AthleteResult(1, "Jimmy Smiles", "UK", "29:15", "xxoox", "xooxo", "xxxxo");

    // when
    List<AthleteResult> athleteResults = csvReader.readCSV("mockFile");

    // then
    assertThat(athleteResults).hasSize(2).contains(athleteResult1, athleteResult2);
  }
}
