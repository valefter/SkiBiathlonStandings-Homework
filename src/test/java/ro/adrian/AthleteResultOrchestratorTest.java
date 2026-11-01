package ro.adrian;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AthleteResultOrchestratorTest {

  @Test
  public void testCalculateShootingPenaltySeconds() throws IOException {
    // given
    CSVReader csvReader = mock(CSVReader.class);
    AthleteResultOrchestrator athleteResultOrchestrator = new AthleteResultOrchestrator(csvReader);

    AthleteResult athlete = new AthleteResult(
            11, "Umar Jorgson", "SK",
            "30:27", "xxxox", "xxxxx", "xxoxo");

    when(csvReader.readCSV("mockFile")).thenReturn(new ArrayList<>(List.of(athlete)));

    // when
    List<AthleteResult> results =
            athleteResultOrchestrator.calculateAthletesTime("mockFile");

    // then
    assertEquals(30, results.get(0).getPenaltySeconds());
  }

  @ParameterizedTest
  @MethodSource("finalTimeTestCases")
  public void testCalculateFinalTimeForDifferentShootingResults(
      AthleteResult athlete, String expectedFinalTime) throws IOException {
    // given
    CSVReader csvReader = mock(CSVReader.class);
    AthleteResultOrchestrator athleteResultOrchestrator = new AthleteResultOrchestrator(csvReader);
    when(csvReader.readCSV("mockFile")).thenReturn(new ArrayList<>(List.of(athlete)));

    // when
    List<AthleteResult> results = athleteResultOrchestrator.calculateAthletesTime("mockFile");

    // then
    assertEquals(expectedFinalTime, results.get(0).getFinalTime());
  }

  private static Stream<Arguments> finalTimeTestCases() {
    return Stream.of(
        Arguments.of(new AthleteResult(11, "Umar Jorgson", "SK", "30:27", "xxxox", "xxxxx", "xxoxo"), "30:57"),
        Arguments.of(new AthleteResult(27, "Piotr Smitzer", "CZ", "30:10", "xxxxx", "xxxxx", "xxxxx"), "30:10"),
        Arguments.of(new AthleteResult(1, "Jimmy Smiles", "UK", "29:15", "xxoox", "xooxo", "xxxxo"), "30:15"));
  }

  @Test
  public void testThrowCSVFileReadExceptionWhenFileCannotBeRead() throws IOException {
    // given
    CSVReader csvReader = mock(CSVReader.class);
    AthleteResultOrchestrator athleteResultOrchestrator = new AthleteResultOrchestrator(csvReader);

    when(csvReader.readCSV("mockFile")).thenThrow(new IOException());

    // when + then
    assertThatThrownBy(() -> athleteResultOrchestrator.calculateAthletesTime("mockFile"))
        .isInstanceOf(CSVFileReadException.class)
        .hasMessage("Could not read CSV file");
  }
}
