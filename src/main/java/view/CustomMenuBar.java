package view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMenuBar extends MenuBar {

    private TerritoryPanel territoryPanel;

    private MenuItem createFileMenuItem;

    private MenuItem openFileMenuItem;
    private MenuItem printEditorMenuItem;
    private MenuItem compileMenuItem;
    private MenuItem quitMenuItem;
    private Menu territoriumMenu;
    private Menu saveSubMenu;
    private MenuItem xmlMenuItem;
    private MenuItem jaxbMenuItem;
    private MenuItem serializeMenuItem;
    private Menu loadSubMenu;
    private Menu saveAsImageSubMenu;
    private MenuItem printTerritoriumMenuItem;
    private MenuItem resizeMenuItem;
    private RadioMenuItem placeHamsterMenuItem;
    private RadioMenuItem placeKornMenuItem;
    private RadioMenuItem placeWallMenuItem;
    private RadioMenuItem deleteFieldMenuItem;
    private ToggleGroup insertDeleteTg;
    private Menu hamsterMenu;
    private MenuItem hasCornMenuItem;
    private MenuItem turnLeftMenuItem;
    private MenuItem moveMenuItem;
    private MenuItem takeCornMenuItem;
    private MenuItem giveCornMenuItem;
    private Menu simulationMenu;

    private MenuItem playMenuItem;

    private MenuItem pauseMenuItem;
    private MenuItem stopMenuItem;
    Menu editorMenu;

    private CustomToolBar customToolBar;

    public CustomMenuBar(TerritoryPanel territoryPanel) {
        this.territoryPanel = territoryPanel;
        //Das Editor Menü
        editorMenu = new Menu("_Editor");
        createFileMenuItem = new MenuItem("Neu");
        quickItemAcc(createFileMenuItem, "SHORTCUT+N");
        quickItemIcon(createFileMenuItem, "New16.gif");
        openFileMenuItem = new MenuItem("Öffnen...");
        quickItemAcc(openFileMenuItem, "SHORTCUT+O");
        quickItemIcon(openFileMenuItem, "Open16.gif");
        compileMenuItem = new MenuItem("Kompilieren");
        quickItemAcc(compileMenuItem, "SHORTCUT + K");
        quickItemIcon(compileMenuItem, "compile24.gif");
        printEditorMenuItem = new MenuItem("Drucken");
        quickItemAcc(printEditorMenuItem, "SHORTCUT+P");
        quickItemIcon(printEditorMenuItem, "Print16.gif");
        printEditorMenuItem.setOnAction(event -> {
            System.out.println("Gedruckt!");
        });
        quitMenuItem = new MenuItem("Beenden");
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
        territoriumMenu = new Menu("Terri_torium");

        saveSubMenu = new Menu("Speichern");
        xmlMenuItem = new MenuItem("XML");
        jaxbMenuItem = new MenuItem("JAXB");
        serializeMenuItem = new MenuItem("Serialisieren");
        saveSubMenu.getItems().addAll(
                xmlMenuItem,
                jaxbMenuItem,
                serializeMenuItem);
        loadSubMenu = new Menu("Laden");
        saveAsImageSubMenu = new Menu("Als Bild speichern");
        printTerritoriumMenuItem = new MenuItem("Drucken");
        resizeMenuItem = new MenuItem("Größe ändern...");

        //Togglebare auswahl zum platzieren oder löschen von objekten im Territorium
        placeHamsterMenuItem = new RadioMenuItem("Hamster platzieren");
        placeKornMenuItem = new RadioMenuItem("Korn platzieren");
        placeWallMenuItem = new RadioMenuItem("Mauer platzieren");
        deleteFieldMenuItem = new RadioMenuItem("Kachel löschen");
        insertDeleteTg = new ToggleGroup();
        insertDeleteTg.getToggles().addAll(placeHamsterMenuItem, placeKornMenuItem, placeWallMenuItem, deleteFieldMenuItem);
        //Verheiraten der ToggleMenuItems mit den korrespondierenden ToggleButtons
        placeHamsterMenuItem.setOnAction(e -> {
            if (customToolBar.getHamsterBtn().isSelected()) {
                customToolBar.getHamsterBtn().setSelected(false);
                placeHamsterMenuItem.setSelected(false);
                territoryPanel.setTerritoryMode(TerritoryPanel.DOING_NOTHING);
            } else {
                customToolBar.getHamsterBtn().setSelected(true);
                territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_ACTOR);
            }
        });
        placeKornMenuItem.setOnAction(e -> {
            if (customToolBar.getCornBtn().isSelected()) {
                customToolBar.getCornBtn().setSelected(false);
                placeKornMenuItem.setSelected(false);
                territoryPanel.setTerritoryMode(TerritoryPanel.DOING_NOTHING);
            } else {
                customToolBar.getCornBtn().setSelected(true);
                territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_CORN);
            }
        });
        placeWallMenuItem.setOnAction(e -> {
            if (customToolBar.getCornBtn().isSelected()) {
                customToolBar.getCornBtn().setSelected(false);
                placeWallMenuItem.setSelected(false);
                territoryPanel.setTerritoryMode(TerritoryPanel.DOING_NOTHING);
            } else {
                customToolBar.getWallBtn().setSelected(true);
                territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_OBSTACLE);
            }
        });
        deleteFieldMenuItem.setOnAction(e -> {
            if(customToolBar.getDeleteBtn().isSelected()){
                customToolBar.getDeleteBtn().setSelected(false);
                deleteFieldMenuItem.setSelected(false);
                territoryPanel.setTerritoryMode(TerritoryPanel.DOING_NOTHING);
            } else {
                customToolBar.getDeleteBtn().setSelected(true);
                territoryPanel.setTerritoryMode(TerritoryPanel.DELETING_STUFF);
            }
        });
        resizeMenuItem.setOnAction(e -> {
            territoryPanel.startResizeDialog();
        });

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
        hamsterMenu = new Menu("_Hamster");
        hasCornMenuItem = new MenuItem("Körner im Maul...");
        turnLeftMenuItem = new MenuItem("linksUm");
        quickItemAcc(turnLeftMenuItem, "SHORTCUT+SHIFT+L");
        moveMenuItem = new MenuItem("vor");
        quickItemAcc(moveMenuItem, "SHORTCUT+SHIFT+V");
        takeCornMenuItem = new MenuItem("nimm");
        quickItemAcc(takeCornMenuItem, "SHORTCUT+SHIFT+N");
        giveCornMenuItem = new MenuItem("gib");
        quickItemAcc(giveCornMenuItem, "SHORTCUT+SHIFT+G");
        hamsterMenu.getItems().addAll(hasCornMenuItem, turnLeftMenuItem, moveMenuItem, takeCornMenuItem, giveCornMenuItem);
        //Ende Hamstermenü

        //Das Simulationsmenü
        simulationMenu = new Menu("_Simulation");
        playMenuItem = new MenuItem("Start/Fortsetzen");
        quickItemAcc(playMenuItem, "SHORTCUT+F11");
        quickItemIcon(playMenuItem, "play16.gif");
        pauseMenuItem = new MenuItem("Pause");
        quickItemIcon(pauseMenuItem, "pause16.gif");
        stopMenuItem = new MenuItem("Stopp");
        quickItemAcc(stopMenuItem, "SHORTCUT+F12");
        quickItemIcon(stopMenuItem, "stop16.gif");
        simulationMenu.getItems().addAll(playMenuItem, pauseMenuItem, stopMenuItem);
        //Ende Simulationsmenü

        this.getMenus().addAll(editorMenu, territoriumMenu, hamsterMenu, simulationMenu);
    }

    /**
     * helper method to make assigning images easier
     *
     * @param menuItem
     * @param fileName
     */
    private void quickItemIcon(MenuItem menuItem, String fileName) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource("../main/resources/" + fileName).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + fileName + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        menuItem.setGraphic(imageView);

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

    public MenuItem getCreateFileMenuItem() {
        return createFileMenuItem;
    }

    public void setCreateFileMenuItem(MenuItem createFileMenuItem) {
        this.createFileMenuItem = createFileMenuItem;
    }

    public MenuItem getOpenFileMenuItem() {
        return openFileMenuItem;
    }

    public void setOpenFileMenuItem(MenuItem openFileMenuItem) {
        this.openFileMenuItem = openFileMenuItem;
    }

    public MenuItem getPrintEditorMenuItem() {
        return printEditorMenuItem;
    }

    public void setPrintEditorMenuItem(MenuItem printEditorMenuItem) {
        this.printEditorMenuItem = printEditorMenuItem;
    }

    public MenuItem getCompileMenuItem() {
        return compileMenuItem;
    }

    public void setCompileMenuItem(MenuItem compileMenuItem) {
        this.compileMenuItem = compileMenuItem;
    }

    public MenuItem getQuitMenuItem() {
        return quitMenuItem;
    }

    public void setQuitMenuItem(MenuItem quitMenuItem) {
        this.quitMenuItem = quitMenuItem;
    }

    public Menu getTerritoriumMenu() {
        return territoriumMenu;
    }

    public void setTerritoriumMenu(Menu territoriumMenu) {
        this.territoriumMenu = territoriumMenu;
    }

    public Menu getSaveSubMenu() {
        return saveSubMenu;
    }

    public void setSaveSubMenu(Menu saveSubMenu) {
        this.saveSubMenu = saveSubMenu;
    }

    public MenuItem getXmlMenuItem() {
        return xmlMenuItem;
    }

    public void setXmlMenuItem(MenuItem xmlMenuItem) {
        this.xmlMenuItem = xmlMenuItem;
    }

    public MenuItem getJaxbMenuItem() {
        return jaxbMenuItem;
    }

    public void setJaxbMenuItem(MenuItem jaxbMenuItem) {
        this.jaxbMenuItem = jaxbMenuItem;
    }

    public MenuItem getSerializeMenuItem() {
        return serializeMenuItem;
    }

    public void setSerializeMenuItem(MenuItem serializeMenuItem) {
        this.serializeMenuItem = serializeMenuItem;
    }

    public Menu getLoadSubMenu() {
        return loadSubMenu;
    }

    public void setLoadSubMenu(Menu loadSubMenu) {
        this.loadSubMenu = loadSubMenu;
    }

    public Menu getSaveAsImageSubMenu() {
        return saveAsImageSubMenu;
    }

    public void setSaveAsImageSubMenu(Menu saveAsImageSubMenu) {
        this.saveAsImageSubMenu = saveAsImageSubMenu;
    }

    public MenuItem getPrintTerritoriumMenuItem() {
        return printTerritoriumMenuItem;
    }

    public void setPrintTerritoriumMenuItem(MenuItem printTerritoriumMenuItem) {
        this.printTerritoriumMenuItem = printTerritoriumMenuItem;
    }

    public MenuItem getResizeMenuItem() {
        return resizeMenuItem;
    }

    public void setResizeMenuItem(MenuItem resizeMenuItem) {
        this.resizeMenuItem = resizeMenuItem;
    }

    public RadioMenuItem getPlaceHamsterMenuItem() {
        return placeHamsterMenuItem;
    }

    public void setPlaceHamsterMenuItem(RadioMenuItem placeHamsterMenuItem) {
        this.placeHamsterMenuItem = placeHamsterMenuItem;
    }

    public RadioMenuItem getPlaceKornMenuItem() {
        return placeKornMenuItem;
    }

    public void setPlaceKornMenuItem(RadioMenuItem placeKornMenuItem) {
        this.placeKornMenuItem = placeKornMenuItem;
    }

    public RadioMenuItem getPlaceWallMenuItem() {
        return placeWallMenuItem;
    }

    public void setPlaceWallMenuItem(RadioMenuItem placeWallMenuItem) {
        this.placeWallMenuItem = placeWallMenuItem;
    }

    public RadioMenuItem getDeleteFieldMenuItem() {
        return deleteFieldMenuItem;
    }

    public void setDeleteFieldMenuItem(RadioMenuItem deleteFieldMenuItem) {
        this.deleteFieldMenuItem = deleteFieldMenuItem;
    }

    public ToggleGroup getInsertDeleteTg() {
        return insertDeleteTg;
    }

    public void setInsertDeleteTg(ToggleGroup insertDeleteTg) {
        this.insertDeleteTg = insertDeleteTg;
    }

    public Menu getHamsterMenu() {
        return hamsterMenu;
    }

    public void setHamsterMenu(Menu hamsterMenu) {
        this.hamsterMenu = hamsterMenu;
    }

    public MenuItem getHasCornMenuItem() {
        return hasCornMenuItem;
    }

    public void setHasCornMenuItem(MenuItem hasCornMenuItem) {
        this.hasCornMenuItem = hasCornMenuItem;
    }

    public MenuItem getTurnLeftMenuItem() {
        return turnLeftMenuItem;
    }

    public void setTurnLeftMenuItem(MenuItem turnLeftMenuItem) {
        this.turnLeftMenuItem = turnLeftMenuItem;
    }

    public MenuItem getMoveMenuItem() {
        return moveMenuItem;
    }

    public void setMoveMenuItem(MenuItem moveMenuItem) {
        this.moveMenuItem = moveMenuItem;
    }

    public MenuItem getTakeCornMenuItem() {
        return takeCornMenuItem;
    }

    public void setTakeCornMenuItem(MenuItem takeCornMenuItem) {
        this.takeCornMenuItem = takeCornMenuItem;
    }

    public MenuItem getGiveCornMenuItem() {
        return giveCornMenuItem;
    }

    public void setGiveCornMenuItem(MenuItem giveCornMenuItem) {
        this.giveCornMenuItem = giveCornMenuItem;
    }

    public Menu getSimulationMenu() {
        return simulationMenu;
    }

    public void setSimulationMenu(Menu simulationMenu) {
        this.simulationMenu = simulationMenu;
    }

    public MenuItem getPlayMenuItem() {
        return playMenuItem;
    }

    public void setPlayMenuItem(MenuItem playMenuItem) {
        this.playMenuItem = playMenuItem;
    }

    public MenuItem getPauseMenuItem() {
        return pauseMenuItem;
    }

    public void setPauseMenuItem(MenuItem pauseMenuItem) {
        this.pauseMenuItem = pauseMenuItem;
    }

    public MenuItem getStopMenuItem() {
        return stopMenuItem;
    }

    public void setStopMenuItem(MenuItem stopMenuItem) {
        this.stopMenuItem = stopMenuItem;
    }

    public TerritoryPanel getTerritoryPanel() {
        return territoryPanel;
    }

    public void setTerritoryPanel(TerritoryPanel territoryPanel) {
        this.territoryPanel = territoryPanel;
    }

    public CustomToolBar getCustomToolBar() {
        return customToolBar;
    }

    public void setCustomToolBar(CustomToolBar customToolBar) {
        this.customToolBar = customToolBar;
    }
}
