package contoller;

import java.io.File;

public class ProgrammController {
    public static void makeProgrammDir() {
        File programmDirectory = new File("src/main/java/programms");
        if(!programmDirectory.exists()) {
            boolean success = programmDirectory.mkdirs();
            if (!success) {
                System.out.println("no success creating programms directory");
            } else {
                System.out.println("directory created");
            }
        }
    }
}
