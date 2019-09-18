package contoller;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RegistrationController {
    public static List<Stage> APPS_RUNNING = new ArrayList<Stage>();

    public static Stage getStageForApp(String fileName) {
        for (Stage stage : APPS_RUNNING) {
            if (stage.getTitle().equals("MPW Simulator - " + fileName)) {
                return stage;
            }
        }
        return null;
    }
}
