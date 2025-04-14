import java.util.*;

public class Question {
    private String text;
    private List<String> options;
    private Map <String, Map<String, Integer>> weights;

    public Question(String text, List<String> options, Map<String, Map<String, Integer>> weights) {
        this.text = text;
        this.options = options;
        this.weights = weights;
    }

    public void displayQuestion() {
        System.out.println(text);
        for (int i = 0; i < options.size(); i++) {
            char label = (char)('A' + i);
            System.out.println(label + ". " + options.get(i));
        }
    }

    public static Question fromFile(Scanner scanner) {
        String text = scanner.nextLine();

        List<String> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            options.add(scanner.nextLine());
        }

        Map<String, Map<String, Integer>> weights = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine().trim();
            if (!line.contains(":")) continue;

            String[] parts = line.split(":");
            if (parts.length < 2) continue;

            String option = parts[0];
            String weightsRaw = parts[1];

            Map<String, Integer> partyWeights = new HashMap<>();

            String[] weightPairs = weightsRaw.split(",");
            for (String pair : weightPairs) {
                String[] keyVal = pair.split("=");
                if (keyVal.length < 2) continue;

                String party = keyVal[0];
                int weight = Integer.parseInt(keyVal[1]);
                partyWeights.put(party, weight);
            }

            weights.put(option, partyWeights);
        }

        return new Question(text, options, weights);
    }

    public Map<String, Integer> getWeightsForOption(String option) {
        return weights.getOrDefault(option, new HashMap<>());
    }

}
