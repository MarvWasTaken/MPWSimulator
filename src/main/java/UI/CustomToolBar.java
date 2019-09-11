package UI;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class CustomToolBar extends ToolBar{


    private ToolBar toolBar;
    private Button createNewFileBtn;
    private Button openFileBtn;
    private Button saveFileBtn;
    private Button compileFileBtn;
    private ToggleButton hamsterBtn;
    private ToggleButton cornBtn;
    private ToggleButton wallBtn;
    private ToggleButton deleteBtn;
    private Button resizeBtn;
    private int hamsterDirection;
    private ToggleButton playBtn;
    private ToggleButton pauseBtn;

    private ToggleButton stopBtn;

    private ToggleGroup playPauseBtnTg;

    private TerritoryPanel territoryPanel;

    public CustomToolBar(CustomMenuBar customMenuBar){
        this.territoryPanel = customMenuBar.getTerritoryPanel();
        createNewFileBtn = new Button();
        quickButtonIcon(createNewFileBtn, "new24.gif");
        createNewFileBtn.setTooltip(new Tooltip("Neues Programm erstellen"));
        openFileBtn = new Button();
        quickButtonIcon(openFileBtn, "open24.gif");
        openFileBtn.setTooltip(new Tooltip("Programm öffnen"));

        saveFileBtn = new Button();
        quickButtonIcon(saveFileBtn, "save24.gif");
        saveFileBtn.setTooltip(new Tooltip("Programm speichern"));
        compileFileBtn = new Button();
        quickButtonIcon(compileFileBtn, "compile24.gif");
        compileFileBtn.setTooltip(new Tooltip("Kompilieren"));

        resizeBtn = new Button();
        quickButtonIcon(resizeBtn, "Terrain24.gif");
        resizeBtn.setTooltip(new Tooltip("Größe ändern..."));

        //toggleButton group
        hamsterBtn = new ToggleButton();
        cornBtn = new ToggleButton();
        wallBtn = new ToggleButton();
        deleteBtn = new ToggleButton();
        quickToggleButtonIcon(hamsterBtn, "3Hamster32.png");
        quickToggleButtonIcon(cornBtn, "corn24.gif");
        quickToggleButtonIcon(wallBtn, "wall24.gif");
        quickToggleButtonIcon(deleteBtn, "Delete24.gif");
        hamsterBtn.setTooltip(new Tooltip("Hamster platzieren"));
        cornBtn.setTooltip(new Tooltip("Korn platzieren"));
        wallBtn.setTooltip(new Tooltip("Mauer platzieren"));
        deleteBtn.setTooltip(new Tooltip("Kachel löschen"));

        //ToggleGroup Actions
        hamsterDirection = 3;
        hamsterBtn.setOnAction(event -> {
            int i = (int) (Math.floor(Math.random() * 4));
            hamsterDirection++;
            hamsterDirection %= 4;
            quickToggleButtonIcon(hamsterBtn, "" + hamsterDirection + "Hamster32.png");
            customMenuBar.getPlaceHamsterMenuItem().setSelected(!customMenuBar.getPlaceHamsterMenuItem().isSelected());
            territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_ACTOR);
        });
        cornBtn.setOnMousePressed(event -> {
            customMenuBar.getPlaceKornMenuItem().setSelected(!customMenuBar.getPlaceKornMenuItem().isSelected());
            territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_CORN);
        });
        wallBtn.setOnMousePressed(event -> {
            customMenuBar.getPlaceWallMenuItem().setSelected(!customMenuBar.getPlaceWallMenuItem().isSelected());
            territoryPanel.setTerritoryMode(TerritoryPanel.SETTING_OBSTACLE);
        });
        deleteBtn.setOnMousePressed(event -> {
            customMenuBar.getDeleteFieldMenuItem().setSelected(!customMenuBar.getDeleteFieldMenuItem().isSelected());
            territoryPanel.setTerritoryMode(TerritoryPanel.DELETING_STUFF);
        });
        resizeBtn.setOnMousePressed(event -> {
            territoryPanel.startResizeDialog();
            //territoryPanel.resize(5, 5);
        });

        ToggleGroup btnTg = new ToggleGroup();
        btnTg.getToggles().addAll(hamsterBtn, cornBtn, wallBtn, deleteBtn);

        Button hamsterCornBtn = new Button();
        Button hamsterLeftBtn = new Button();
        Button hamsterMoveBtn = new Button();
        Button hamsterPickBtn = new Button();
        Button hamsterPutBtn = new Button();
        quickButtonIcon(hamsterCornBtn, "HamsterCorn24.png");
        quickButtonIcon(hamsterLeftBtn, "HamsterLeft24.png");
        quickButtonIcon(hamsterMoveBtn, "HamsterMove24.png");
        quickButtonIcon(hamsterPickBtn, "HamsterPick24.png");
        quickButtonIcon(hamsterPutBtn, "HamsterPut24.png");
        hamsterCornBtn.setTooltip(new Tooltip("Körner im Maul..."));
        hamsterLeftBtn.setTooltip(new Tooltip("linksUm"));
        hamsterMoveBtn.setTooltip(new Tooltip("vor"));
        hamsterPickBtn.setTooltip(new Tooltip("nimm"));
        hamsterPutBtn.setTooltip(new Tooltip("gib"));

        hamsterLeftBtn.setOnMousePressed(event -> territoryPanel.linksUmEventTriggered());
        hamsterMoveBtn.setOnMousePressed(event -> territoryPanel.moveEventTriggered());

        playBtn = new ToggleButton();
        pauseBtn = new ToggleButton();
        stopBtn = new ToggleButton();
        quickToggleButtonIcon(playBtn, "play24.gif");
        quickToggleButtonIcon(pauseBtn, "pause24.gif");
        quickToggleButtonIcon(stopBtn, "stop24.gif");
        playBtn.setTooltip(new Tooltip("Start/Fortsetzen"));
        pauseBtn.setTooltip(new Tooltip("Pause"));
        stopBtn.setTooltip(new Tooltip("Stopp"));
        playPauseBtnTg = new ToggleGroup();
        playPauseBtnTg.getToggles().addAll(playBtn, pauseBtn, stopBtn);

        this.getItems().addAll(
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
                deleteBtn,
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

    }
    /**
     * helper method to make assigning images to Buttons easier
     *
     * @param button
     * @param fileName
     */
    private void quickButtonIcon(Button button, String fileName) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource("../main/resources/"+fileName).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + fileName + " nicht gefunden!");
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
     * @param fileName
     */
    private void quickToggleButtonIcon(ToggleButton toggleButton, String fileName) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource("../main/resources/"+fileName).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + fileName + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        toggleButton.setGraphic(imageView);

    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public Button getCreateNewFileBtn() {
        return createNewFileBtn;
    }

    public void setCreateNewFileBtn(Button createNewFileBtn) {
        this.createNewFileBtn = createNewFileBtn;
    }

    public Button getOpenFileBtn() {
        return openFileBtn;
    }

    public void setOpenFileBtn(Button openFileBtn) {
        this.openFileBtn = openFileBtn;
    }

    public Button getSaveFileBtn() {
        return saveFileBtn;
    }

    public void setSaveFileBtn(Button saveFileBtn) {
        this.saveFileBtn = saveFileBtn;
    }

    public Button getCompileFileBtn() {
        return compileFileBtn;
    }

    public void setCompileFileBtn(Button compileFileBtn) {
        this.compileFileBtn = compileFileBtn;
    }

    public ToggleButton getHamsterBtn() {
        return hamsterBtn;
    }

    public void setHamsterBtn(ToggleButton hamsterBtn) {
        this.hamsterBtn = hamsterBtn;
    }

    public ToggleButton getCornBtn() {
        return cornBtn;
    }

    public void setCornBtn(ToggleButton cornBtn) {
        this.cornBtn = cornBtn;
    }

    public ToggleButton getWallBtn() {
        return wallBtn;
    }

    public void setWallBtn(ToggleButton wallBtn) {
        this.wallBtn = wallBtn;
    }

    public ToggleButton getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(ToggleButton deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public Button getResizeBtn() {
        return resizeBtn;
    }

    public void setResizeBtn(Button resizeBtn) {
        this.resizeBtn = resizeBtn;
    }

    public int getHamsterDirection() {
        return hamsterDirection;
    }

    public void setHamsterDirection(int hamsterDirection) {
        this.hamsterDirection = hamsterDirection;
    }

    public ToggleButton getPlayBtn() {
        return playBtn;
    }

    public void setPlayBtn(ToggleButton playBtn) {
        this.playBtn = playBtn;
    }

    public ToggleButton getPauseBtn() {
        return pauseBtn;
    }

    public void setPauseBtn(ToggleButton pauseBtn) {
        this.pauseBtn = pauseBtn;
    }

    public ToggleButton getStopBtn() {
        return stopBtn;
    }

    public void setStopBtn(ToggleButton stopBtn) {
        this.stopBtn = stopBtn;
    }

    public ToggleGroup getPlayPauseBtnTg() {
        return playPauseBtnTg;
    }

    public void setPlayPauseBtnTg(ToggleGroup playPauseBtnTg) {
        this.playPauseBtnTg = playPauseBtnTg;
    }

    public TerritoryPanel getTerritoryPanel() {
        return territoryPanel;
    }

    public void setTerritoryPanel(TerritoryPanel territoryPanel) {
        this.territoryPanel = territoryPanel;
    }
}
