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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;

public class ProgrammController {
    private CodeArea codeArea;
    static public String JAVA_DIRECTORY = "src/main/java/";
    static private String PROGRAMM_DIRECTORY = JAVA_DIRECTORY + "programms/";


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
                System.out.println("directory created");
            }
        } else {
            System.out.println("Programm directory besteht bereits!");
        }
        return programmDirectory;
    }

    public static void makeProgrammFile(String fileName, CodeArea codeArea) {
        String prefix =
                "package compiled;\n" +
                        "import model.Territory;\n" +
                        "public class " + fileName + " extends Territory{\n";
        String suffix = "\n}";


        System.out.println(fileName);
        System.out.println(PROGRAMM_DIRECTORY + "/" + fileName);
        File file = new File(PROGRAMM_DIRECTORY + "/" + fileName + ".java");
        if(!file.exists()){
            try {
                boolean success = file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(prefix);
                bufferedWriter.write(codeArea.getText());
                bufferedWriter.write(suffix);
                bufferedWriter.close();
                if (success) {
                    System.out.println("Geil man Porsche Cayman S!");
                    loadProgrammFromFile(file);
                    return;
                }
            } catch (IOException ioe) {
                System.out.println("File konnte nicht erstellt werden");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(fileName+" ist bereits vorhanden!");
            alert.show();
        }

    }


    public static void loadProgrammFromFile(File file) {
        /**
         * so war es bisher
         */
        System.out.println(file.getAbsolutePath() + " wurde geladen.");

        /**
         * so ist es jetzt.
         */

        String fileName = file.getName().substring(0,file.getName().indexOf('.'));

        try {

            File pathToProgramms = new File(PROGRAMM_DIRECTORY);
            File defaultTerritoryFile = new File(PROGRAMM_DIRECTORY + fileName +".java");

            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            boolean success =
                    javac.run(null, null, err, defaultTerritoryFile.getAbsolutePath()) == 0;
            if (!success) {
                System.out.println(err.toString());
            } else {
                File compiledClass = new File(JAVA_DIRECTORY + "compiled/"+ fileName +".class");
                File compiledFile = new File(JAVA_DIRECTORY + "compiled/"+ fileName +".java");
                checkAndClear(compiledClass);
                checkAndClear(compiledFile);
                Path copyClass = Files.copy(
                        Paths.get(PROGRAMM_DIRECTORY + fileName +".class"),
                        Paths.get(JAVA_DIRECTORY + "compiled/"+ fileName +".class")
                );
                Path copyFile = Files.copy(
                        Paths.get(PROGRAMM_DIRECTORY + fileName +".java"),
                        Paths.get(JAVA_DIRECTORY + "compiled/"+ fileName +".java")
                );
                if (copyClass != null && copyFile != null) {
                    System.out.println("Files moved successfully!");
                } else {
                    System.out.println("File not removed :(");
                }

                System.out.println("ok");
            }
            /**File compiledFile = new File(JAVA_DIRECTORY + "compiled/"+ fileName +".java");
            Instant start = Instant.now();
            Duration between = Duration.between(start, Instant.now());
            while(between.getSeconds()<20){
                between = Duration.between(start, Instant.now());
                System.out.println("Warte auf CompiledFile! seit "+between.getSeconds()+" Sekunden.");
            }*/

            URL[] urls = new URL[]{pathToProgramms.toURI().toURL()};
            URLClassLoader loader = new URLClassLoader(urls);
            Class<?> territoryClass = loader.loadClass("compiled."+ fileName);
            Territory territory = (Territory) territoryClass.newInstance();

            new WindowController().prepareStage(new Stage(), territory, territoryClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * File path = new File("C:\\Users\\dibo\\test\\");
         * URL[] urls = new URL[] { path.toURI().toURL() };
         * ClassLoader cl = new URLClassLoader(urls);
         * String clsName = "Beispiel";
         * Class<?> cls = cl.loadClass(clsName);
         * cls.newInstance();
         */

    }

    public static void loadDefaultTerritory() {
        loadProgrammFromFile(new File("DefaultTerritory.java"));
    }

    /**
     * helper method to clear out existing compiledStuff
     * @param file
     */
    private static void checkAndClear(File file) {
        if (file.exists()) {
            System.out.print("Klasse gibts schon");
            if (file.delete()) {
                System.out.println(", wurde aber jetzt gelöscht :)");
            } else {
                System.out.println(" und konnte nicht gelöscht werden O_o");
            }
        } else {
            System.out.println("Kompilierte klasse noch nicht vorhanden :)");
            System.out.println(file.getAbsolutePath());
        }
    }
}
