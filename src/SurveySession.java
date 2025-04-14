import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SurveySession {
    private List<Question> questions;
    private Map<String, Integer> partyScores;
    private Scanner input;

    public SurveySession(List<Question> questions) {
        this.questions = questions;
        this.partyScores = new HashMap<>();
        this.input = new Scanner(System.in);
    }

    public void runSurvey() {
        for (Question q : questions) {
            q.displayQuestion();

            System.out.print("Your answer (A/B/C/D): ");
            String choice = input.nextLine().trim().toUpperCase();

            Map<String, Integer> weights = q.getWeightsForOption(choice);

            // Add weights to running partyScores
            for (String party : weights.keySet()) {
                int current = partyScores.getOrDefault(party, 0);
                partyScores.put(party, current + weights.get(party));
            }

            System.out.println(); // space between questions
        }

        // After all questions, show total scores:
        System.out.println("Final Party Scores:");
        for (String party : partyScores.keySet()) {
            System.out.println(party + ": " + partyScores.get(party));
        }

        // Optional: Guess the party
        String guess = guessParty();
        System.out.println("Based on your answers, you might be: " + guess);

        System.out.print("What political party do you actually affiliate with? ");
        String actualParty = input.nextLine().trim();

        try {
            FileWriter writer = new FileWriter("responses.txt", true); // true = append mode

            for (String party : partyScores.keySet()) {
                writer.write(party + "=" + partyScores.get(party) + ",");
            }

            writer.write("ACTUAL=" + actualParty + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save response.");
        }
    }

    private String guessParty() {
        String bestParty = null;
        int maxScore = Integer.MIN_VALUE;

        for (String party : partyScores.keySet()) {
            int score = partyScores.get(party);
            if (score > maxScore) {
                maxScore = score;
                bestParty = party;
            }
        }
        return bestParty;
    }
}