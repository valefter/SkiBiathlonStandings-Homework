package ro.adrian;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SkiBiathlonStandingsTest {

  @ParameterizedTest
  @MethodSource("standingsTestCases")
  public void testReturnCorrectStandings(List<AthleteResult> mockAthletes, String expectedResult) {

    // given
    AthleteResultOrchestrator athleteResultOrchestrator = mock(AthleteResultOrchestrator.class);
    SkiBiathlonStandings standings = new SkiBiathlonStandings(athleteResultOrchestrator);

    when(athleteResultOrchestrator.calculateAthletesTime("mockFile"))
        .thenReturn(new ArrayList<>(mockAthletes));

    // when
    String result = standings.finalStandings("mockFile");

    // then
    assertEquals(expectedResult, result);
  }

  private static Stream<Arguments> standingsTestCases() {

    AthleteResult piotr =
        new AthleteResult(27, "Piotr Smitzer", "CZ", "30:10", "xxxxx", "xxxxx", "xxxxx");
    piotr.setFinalTime("30:10");
    piotr.setPenaltySeconds(0);

    AthleteResult jimmy =
        new AthleteResult(1, "Jimmy Smiles", "UK", "29:15", "xxoox", "xooxo", "xxxxo");
    jimmy.setFinalTime("30:15");
    jimmy.setPenaltySeconds(60);

    AthleteResult umar =
        new AthleteResult(11, "Umar Jorgson", "SK", "30:27", "xxxox", "xxxxx", "xxoxo");
    umar.setFinalTime("30:57");
    umar.setPenaltySeconds(30);

    AthleteResult alex =
        new AthleteResult(15, "Alex Brown", "US", "28:15", "xxxxx", "xxxxx", "xxxxx");
    alex.setFinalTime("28:15");
    alex.setPenaltySeconds(0);

    return Stream.of(
        Arguments.of(
            new ArrayList<>(List.of(piotr)),
            "Winner - Piotr Smitzer 30:10 (30:10 + 0)" + System.lineSeparator()),
        Arguments.of(
            new ArrayList<>(List.of(piotr, jimmy)),
            "Winner - Piotr Smitzer 30:10 (30:10 + 0)"
                + System.lineSeparator()
                + "Runner-up - Jimmy Smiles 30:15 (29:15 + 60)"
                + System.lineSeparator()),
        Arguments.of(
            new ArrayList<>(List.of(umar, jimmy, piotr)),
            "Winner - Piotr Smitzer 30:10 (30:10 + 0)"
                + System.lineSeparator()
                + "Runner-up - Jimmy Smiles 30:15 (29:15 + 60)"
                + System.lineSeparator()
                + "Third Place - Umar Jorgson 30:57 (30:27 + 30)"
                + System.lineSeparator()),
        Arguments.of(
            new ArrayList<>(List.of(umar, jimmy, piotr, alex)),
            "Winner - Alex Brown 28:15 (28:15 + 0)"
                + System.lineSeparator()
                + "Runner-up - Piotr Smitzer 30:10 (30:10 + 0)"
                + System.lineSeparator()
                + "Third Place - Jimmy Smiles 30:15 (29:15 + 60)"
                + System.lineSeparator()));
  }
}
