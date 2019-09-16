package contoller;

import view.CodeArea;

import java.io.File;
import java.io.IOException;

public class ProgrammController {
    private CodeArea codeArea;
    static private String PROGRAMM_DIRECTORY = "src/main/java/programms";

    public ProgrammController(CodeArea codeArea){
        this.codeArea = codeArea;
    }
    private File makeProgrammDir() {
        File programmDirectory = new File(PROGRAMM_DIRECTORY);
        if(!programmDirectory.exists()) {
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

    public static void makeProgrammFile(String fileName) {
        System.out.println(fileName);
        System.out.println(PROGRAMM_DIRECTORY +"/"+fileName);
        File file = new File(PROGRAMM_DIRECTORY+"/"+fileName+".java");
        try{
            boolean success = file.createNewFile();
            if(success){
                System.out.println("Geil man Porsche Cayman S!");
                return;
            }
        }catch (IOException ioe){
            System.out.println("File konnte nicht erstellt werden");
        }
    }
}
