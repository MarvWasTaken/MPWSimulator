import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

public class MenuBuilder {
    public MenuBar buildMenuBar() {
        MenuBar toBeReturned = new MenuBar();

        //Das Editor Menü
        Menu editorMenu = new Menu("_Editor");
        MenuItem createFileMenuItem = new MenuItem("Neu");
        quickItemAcc(createFileMenuItem, "SHORTCUT+N");
        quickItemIcon(createFileMenuItem, "main/resources/New16.gif");
        MenuItem openFileMenuItem = new MenuItem("Öffnen...");
        quickItemAcc(openFileMenuItem, "SHORTCUT+O");
        quickItemIcon(openFileMenuItem, "main/resources/Open16.gif");
        MenuItem compileMenuItem = new MenuItem("Kompilieren");
        quickItemAcc(compileMenuItem, "SHORTCUT + K");
        MenuItem printEditorMenuItem = new MenuItem("Drucken");
        quickItemAcc(printEditorMenuItem, "SHORTCUT+P");
        quickItemIcon(printEditorMenuItem, "main/resources/Print16.gif");
        printEditorMenuItem.setOnAction(event -> {
            System.out.println("Gedruckt!");
        });
        MenuItem quitMenuItem = new MenuItem("Beenden");
        quickItemAcc(quitMenuItem, "SHORTCUT+Q");
        quitMenuItem.setOnAction(event -> {
            Platform.exit();
        });
        editorMenu.getItems().addAll(
                createFileMenuItem,
                openFileMenuItem,
                new SeparatorMenuItem(),
                compileMenuItem,
                printEditorMenuItem,
                new SeparatorMenuItem(),
                quitMenuItem);
        //Ende Editor Menü

        //Das Territorium Menü
        Menu territoriumMenu = new Menu("Terri_torium");

        Menu saveSubMenu = new Menu("Speichern");
        MenuItem xmlMenuItem = new MenuItem("XML");
        MenuItem jaxbMenuItem = new MenuItem("JAXB");
        MenuItem serializeMenuItem = new MenuItem("Serialisieren");
        saveSubMenu.getItems().addAll(
                xmlMenuItem,
                jaxbMenuItem,
                serializeMenuItem);
        MenuItem printTerritoriumMenuItem = new MenuItem("Drucken");

        territoriumMenu.getItems().addAll(
                saveSubMenu,
                printTerritoriumMenuItem
        );
        //Ende Territorium Menü

        //Das Hamstermenü
        Menu hamsterMenu = new Menu("Hamster");
        //Ende Hamstermenü

        //Das Simulationsmenü
        Menu simulationMenu = new Menu("Simulation");
        //Ende Simulationsmenü

        toBeReturned.getMenus().addAll(editorMenu, territoriumMenu, hamsterMenu, simulationMenu);
        return toBeReturned;
    }

    public ToolBar buildToolbar(){
        ToolBar toolBar = new ToolBar();
        Button deadHamsterBtn = new Button();
        quickButtonIcon(deadHamsterBtn, "main/resources/0Hamster32.png");
        toolBar.getItems().addAll(deadHamsterBtn);
        return toolBar;
    }

    /**
     * helper method to make assigning images easier
     * @param menuItem
     * @param filepath
     */
    private void quickItemIcon(MenuItem menuItem, String filepath) {
        Image iconImage;
        try{
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e){
            System.out.println("Bild bei "+filepath+" nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        menuItem.setGraphic(imageView);

    }

    /**
     * helper method to make assigning images to Buttons easier
     * @param button
     * @param filepath
     */
    private void quickButtonIcon(Button button, String filepath) {
        Image iconImage;
        try{
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e){
            System.out.println("Bild bei "+filepath+" nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        button.setGraphic(imageView);

    }

    /**
     * helper method to make assigning accelerators easier
     * @param item
     * @param accelerator
     */
    static void quickItemAcc(MenuItem item, String accelerator){
        item.setAccelerator(KeyCombination.keyCombination(accelerator));
    }
}
