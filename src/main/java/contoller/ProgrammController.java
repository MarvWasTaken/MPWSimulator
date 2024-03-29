package contoller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.DefaultTerritory;
import model.Territory;
import view.CodeArea;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static util.MyAppConstants.PROGRAMM_DIRECTORY;

public class ProgrammController {
    private CodeArea codeArea;


    public ProgrammController(CodeArea codeArea) {
        this.codeArea = codeArea;
        makeProgrammDir();
    }

    private File makeProgrammDir() {
        File programmDirectory = new File(PROGRAMM_DIRECTORY);
        if (!programmDirectory.exists()) {
            boolean success = programmDirectory.mkdirs();
            if (!success) {
                System.out.println("no success creating programms directory");
            } else {
                System.out.println("programms directory created");
            }
        } else {
            System.out.println("Programm directory already exists!");
        }
        return programmDirectory;
    }

    public static boolean makeProgrammFile(String fileName, CodeArea codeArea) {
        String prefix =
                        "import model.Territory; public class " + fileName + " extends Territory{ public void main(){} ";
        String suffix = "}";


        File file = new File(PROGRAMM_DIRECTORY +"/"+ fileName + ".java");
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(prefix);
                bufferedWriter.write(codeArea.getText());
                bufferedWriter.write(suffix);
                bufferedWriter.close();
                if (success) {
                    loadProgrammFromFile(file);
                    return true;
                }
            } catch (IOException ioe) {
                System.out.println("File could not be created");
            }
        }
        return false;
    }

    public static Territory compileTerritoryFile(File file){
        String classNameOfFile = getClassNameOfFile(file);
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        boolean success =
                javac.run(null, null, err, file.getAbsolutePath()) == 0;
        if (!success) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kompilierfehler");
            alert.setHeaderText("Fehler beim kompilieren von "+file.getName());
            alert.setContentText(err.toString());
            alert.show();
            System.out.println(err.toString());
        } else {
            System.out.println(classNameOfFile+" Compiled successfully!");
            try{
                /**
                 * Wie in der Vorlesung gezeigte Nutzung des Classloaders.
                 */
                URL[] urls = new URL[]{new File(PROGRAMM_DIRECTORY).toURI().toURL()};
                URLClassLoader loader = new URLClassLoader(urls);
                Class<?> territoryClass = loader.loadClass(classNameOfFile);
                return (Territory) territoryClass.newInstance();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getClassNameOfFile(File file) {

        //Setting current OS fileseparator char
        String ret = System.getProperty("file.separator");
        return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(ret)+1,file.getAbsolutePath().indexOf('.'));

    }

    public static void loadProgrammFromFile(File file) {
        /**
         * so war es bisher
         */
        System.out.println(file.getAbsolutePath() + " wurde geladen.");

        /**
         * so ist es jetzt.
         */

        String fileName = file.getName().substring(0, file.getName().indexOf('.'));
        Stage appStage = RegistrationController.getStageForApp(fileName);

        if (appStage == null) {
            try {
                //compileTerritoryFile(file);
                new WindowController().prepareStage(new Stage(), new DefaultTerritory(), fileName, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            appStage.toFront();
        }
    }

    public static String getCodeAreaContentFrom(File file) {
        try{
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            content = content.substring(content.indexOf('{') + 1, content.lastIndexOf('}'));
            return content;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "Konnte Content nicht lesen!";
    }

    public static void saveProgrammCodeToFile(String content, File file) {
        String prefix =
                        "import model.Territory; public class " + file.getName().substring(0, file.getName().indexOf('.')) + " extends Territory{";
        String suffix = "}";

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(prefix);
            bufferedWriter.write(content);
            bufferedWriter.write(suffix);
            bufferedWriter.close();
            System.out.println("Contents wurden gespeichert!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("File konnte nicht gespeichert werden");
        }
    }


    public static void loadDefaultTerritory() {
        loadProgrammFromFile(new File("DefaultTerritory.java"));
    }
}
