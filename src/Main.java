import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("questions.txt");
            Scanner scanner = new Scanner(file);

            List<Question> questions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                questions.add(Question.fromFile(scanner));
            }
            scanner.close();

            SurveySession session = new SurveySession(questions);
            session.runSurvey();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}