import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import model.Territory;

public class MenuBuilder {
    ToggleButton hamsterBtn;
    int hamsterpos;

    public MenuBuilder() {

    }

    public Scene buildScene() {
        //Initializing the central window components
        BorderPane root = new BorderPane();
        BorderPane subRootPane = new BorderPane();
        SplitPane splitPane = new SplitPane();
        TextArea codeEditor = new TextArea();

        ScrollPane scrollPane = new ScrollPane();
        TerritoryPanel territoryPanel = new TerritoryPanel(new Territory(40,40), scrollPane);
        scrollPane.setContent(territoryPanel);

        //Filling root with the MenuBar and the subroot.
        root.setTop(this.buildMenuBar());
        root.setCenter(subRootPane);
        //Filling the subroot with the toolbar and the splitpane
        subRootPane.setTop(this.buildToolbar());
        subRootPane.setCenter(splitPane);
        Label statusLabel = new Label("Hallo Dibo");
        subRootPane.setBottom(statusLabel);

        splitPane.getItems().addAll(codeEditor, scrollPane);
        Scene scene = new Scene(root, 950, 650);

        return scene;
    }

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
        quickItemIcon(compileMenuItem, "main/resources/compile24.gif");
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
        Menu loadSubMenu = new Menu("Laden");
        Menu saveAsImageSubMenu = new Menu("Als Bild speichern");
        MenuItem printTerritoriumMenuItem = new MenuItem("Drucken");
        MenuItem resizeMenuItem = new MenuItem("Größe ändern...");
        //Togglebare auswahl zum platzieren oder löschen von objekten im Territorium
        RadioMenuItem placeHamsterMenuItem = new RadioMenuItem("Hamster platzieren");
        RadioMenuItem placeKornMenuItem = new RadioMenuItem("Korn platzieren");
        RadioMenuItem placeWallMenuItem = new RadioMenuItem("Mauer platzieren");
        RadioMenuItem deleteFieldMenuItem = new RadioMenuItem("Kachel löschen");
        ToggleGroup insertDeleteTg = new ToggleGroup();
        insertDeleteTg.getToggles().addAll(placeHamsterMenuItem, placeKornMenuItem, placeWallMenuItem, deleteFieldMenuItem);

        territoriumMenu.getItems().addAll(
                saveSubMenu,
                loadSubMenu,
                saveAsImageSubMenu,
                printTerritoriumMenuItem,
                resizeMenuItem,
                new SeparatorMenuItem(),
                placeHamsterMenuItem,
                placeKornMenuItem,
                placeWallMenuItem,
                deleteFieldMenuItem
        );
        //Ende Territorium Menü


        //Das Hamstermenü
        Menu hamsterMenu = new Menu("_Hamster");
        MenuItem hasCornMenuItem = new MenuItem("Körner im Maul...");
        MenuItem turnLeftMenuItem = new MenuItem("linksUm");
        quickItemAcc(turnLeftMenuItem, "SHORTCUT+SHIFT+L");
        MenuItem moveMenuItem = new MenuItem("vor");
        quickItemAcc(moveMenuItem, "SHORTCUT+SHIFT+V");
        MenuItem takeCornMenuItem = new MenuItem("nimm");
        quickItemAcc(takeCornMenuItem, "SHORTCUT+SHIFT+N");
        MenuItem giveCornMenuItem = new MenuItem("gib");
        quickItemAcc(giveCornMenuItem, "SHORTCUT+SHIFT+G");
        hamsterMenu.getItems().addAll(hasCornMenuItem, turnLeftMenuItem, moveMenuItem, takeCornMenuItem, giveCornMenuItem);
        //Ende Hamstermenü

        //Das Simulationsmenü
        Menu simulationMenu = new Menu("_Simulation");
        MenuItem playMenuItem = new MenuItem("Start/Fortsetzen");
        quickItemAcc(playMenuItem, "SHORTCUT+F11");
        quickItemIcon(playMenuItem, "main/resources/play16.gif");
        MenuItem pauseMenuItem = new MenuItem("Pause");
        quickItemIcon(pauseMenuItem, "main/resources/pause16.gif");
        MenuItem stopMenuItem = new MenuItem("Stopp");
        quickItemAcc(stopMenuItem, "SHORTCUT+F12");
        quickItemIcon(stopMenuItem, "main/resources/stop16.gif");
        simulationMenu.getItems().addAll(playMenuItem, pauseMenuItem, stopMenuItem);
        //Ende Simulationsmenü

        toBeReturned.getMenus().addAll(editorMenu, territoriumMenu, hamsterMenu, simulationMenu);
        return toBeReturned;
    }

    public ToolBar buildToolbar() {
        ToolBar toolBar = new ToolBar();
        Button createNewFileBtn = new Button();
        quickButtonIcon(createNewFileBtn, "main/resources/new24.gif");
        createNewFileBtn.setTooltip(new Tooltip("Neues Programm erstellen"));
        Button openFileBtn = new Button();
        quickButtonIcon(openFileBtn, "main/resources/open24.gif");
        openFileBtn.setTooltip(new Tooltip("Programm öffnen"));

        Button saveFileBtn = new Button();
        quickButtonIcon(saveFileBtn, "main/resources/save24.gif");
        saveFileBtn.setTooltip(new Tooltip("Programm speichern"));
        Button compileFileBtn = new Button();
        quickButtonIcon(compileFileBtn, "main/resources/compile24.gif");
        compileFileBtn.setTooltip(new Tooltip("Kompilieren"));

        Button resizeBtn = new Button();
        quickButtonIcon(resizeBtn, "main/resources/Terrain24.gif");
        resizeBtn.setTooltip(new Tooltip("Größe ändern..."));

        hamsterBtn = new ToggleButton();
        ToggleButton cornBtn = new ToggleButton();
        ToggleButton wallBtn = new ToggleButton();
        ToggleButton deleteBtn = new ToggleButton();
        quickToggleButtonIcon(hamsterBtn, "main/resources/3Hamster32.png");
        quickToggleButtonIcon(cornBtn, "main/resources/corn24.gif");
        quickToggleButtonIcon(wallBtn, "main/resources/wall24.gif");
        quickToggleButtonIcon(deleteBtn, "main/resources/Delete24.gif");
        hamsterBtn.setTooltip(new Tooltip("Hamster platzieren"));
        cornBtn.setTooltip(new Tooltip("Korn platzieren"));
        wallBtn.setTooltip(new Tooltip("Mauer platzieren"));
        deleteBtn.setTooltip(new Tooltip("Kachel löschen"));

        hamsterpos = 3;
        hamsterBtn.setOnAction(event -> {
            int i = (int) (Math.floor(Math.random() * 4));
            hamsterpos++;
            hamsterpos %= 4;
            quickToggleButtonIcon(hamsterBtn, "main/resources/" + hamsterpos + "Hamster32.png");
        });
        ToggleGroup btnTg = new ToggleGroup();
        btnTg.getToggles().addAll(hamsterBtn, cornBtn, wallBtn, deleteBtn);

        Button hamsterCornBtn = new Button();
        Button hamsterLeftBtn = new Button();
        Button hamsterMoveBtn = new Button();
        Button hamsterPickBtn = new Button();
        Button hamsterPutBtn = new Button();
        quickButtonIcon(hamsterCornBtn, "main/resources/HamsterCorn24.png");
        quickButtonIcon(hamsterLeftBtn, "main/resources/HamsterLeft24.png");
        quickButtonIcon(hamsterMoveBtn, "main/resources/HamsterMove24.png");
        quickButtonIcon(hamsterPickBtn, "main/resources/HamsterPick24.png");
        quickButtonIcon(hamsterPutBtn, "main/resources/HamsterPut24.png");
        hamsterCornBtn.setTooltip(new Tooltip("Körner im Maul..."));
        hamsterLeftBtn.setTooltip(new Tooltip("linksUm"));
        hamsterMoveBtn.setTooltip(new Tooltip("vor"));
        hamsterPickBtn.setTooltip(new Tooltip("nimm"));
        hamsterPutBtn.setTooltip(new Tooltip("gib"));

        ToggleButton playBtn = new ToggleButton();
        ToggleButton pauseBtn = new ToggleButton();
        ToggleButton stopBtn = new ToggleButton();
        quickToggleButtonIcon(playBtn, "main/resources/play24.gif");
        quickToggleButtonIcon(pauseBtn, "main/resources/pause24.gif");
        quickToggleButtonIcon(stopBtn, "main/resources/stop24.gif");
        playBtn.setTooltip(new Tooltip("Start/Fortsetzen"));
        pauseBtn.setTooltip(new Tooltip("Pause"));
        stopBtn.setTooltip(new Tooltip("Stopp"));
        ToggleGroup playPauseBtnTg = new ToggleGroup();
        playPauseBtnTg.getToggles().addAll(playBtn, pauseBtn, stopBtn);

        toolBar.getItems().addAll(
                createNewFileBtn,
                openFileBtn,
                new Separator(),
                saveFileBtn,
                compileFileBtn,
                new Separator(),
                resizeBtn,
                hamsterBtn,
                cornBtn,
                wallBtn,
                new Separator(),
                hamsterCornBtn,
                hamsterLeftBtn,
                hamsterMoveBtn,
                hamsterPickBtn,
                hamsterPutBtn,
                new Separator(),
                playBtn,
                pauseBtn,
                stopBtn,
                new Separator(),
                new Slider()
        );
        return toolBar;
    }

    /**
     * helper method to make assigning images easier
     *
     * @param menuItem
     * @param filepath
     */
    private void quickItemIcon(MenuItem menuItem, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        menuItem.setGraphic(imageView);

    }

    /**
     * helper method to make assigning images to Buttons easier
     *
     * @param button
     * @param filepath
     */
    private void quickButtonIcon(Button button, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        button.setGraphic(imageView);

    }

    /**
     * helper method to make assigning images to Buttons easier
     *
     * @param toggleButton
     * @param filepath
     */
    private void quickToggleButtonIcon(ToggleButton toggleButton, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        toggleButton.setGraphic(imageView);

    }

    /**
     * helper method to make assigning accelerators easier
     *
     * @param item
     * @param accelerator
     */
    static void quickItemAcc(MenuItem item, String accelerator) {
        item.setAccelerator(KeyCombination.keyCombination(accelerator));
    }
}
