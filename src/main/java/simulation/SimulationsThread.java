package simulation;

import contoller.SimulationController;
import lombok.Getter;
import view.TerritoryPanel;

import java.time.Instant;


public class SimulationsThread extends Thread {
    TerritoryPanel territoryPanel;
    SimulationController parent;

    public Object getLock() {
        return lock;
    }

    public Object lock;

    public SimulationsThread(TerritoryPanel territoryPanel, SimulationController parent) {
        this.territoryPanel = territoryPanel;
        this.parent = parent;
        lock = new Object();
    }

    public void run() {
        territoryPanel.getTerritory().addObserver((o, arg) -> {
            territoryPanel.draw();
            try {
                this.sleep((int) (1000 - Math.floor(1000 * (parent.getWindowController().getCustomToolBar().getSpeedSlider().getValue() / 100))));
                this.sleep(100);
            } catch (InterruptedException e) {
                this.interrupt();
                this.territoryPanel.getTerritory().deleteObservers();
            }
        });
        try {
            if(parent.isRunning()){
                territoryPanel.getTerritory().main();
                territoryPanel.draw();
                this.sleep((int) (5000 - Math.floor(5000 * (parent.getWindowController().getCustomToolBar().getSpeedSlider().getValue() / 100))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.interrupt();
            this.territoryPanel.getTerritory().deleteObservers();
        }
    }

}
