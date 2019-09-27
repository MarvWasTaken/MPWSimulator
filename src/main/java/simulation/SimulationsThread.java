package simulation;

import contoller.SimulationController;
import view.TerritoryPanel;

import java.time.Instant;

public class SimulationsThread extends Thread {
    TerritoryPanel territoryPanel;
    SimulationController parent;

    public SimulationsThread(TerritoryPanel territoryPanel, SimulationController parent) {
        this.territoryPanel = territoryPanel;
        this.parent = parent;
    }

    public void run() {
        territoryPanel.getTerritory().addObserver((o, arg) -> {
            System.out.println("Hat glaube ich geklappt :)");
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
            System.out.println(Instant.now().toString());
            /**if (territoryPanel.getTerritory().getActor().vornFrei(territoryPanel.getTerritory())){
             territoryPanel.getTerritory().getActor().vor();
             territoryPanel.draw();
             }*/
            if(parent.isRunning()){
                territoryPanel.getTerritory().main();
                territoryPanel.draw();
                this.sleep((int) (5000 - Math.floor(5000 * (parent.getWindowController().getCustomToolBar().getSpeedSlider().getValue() / 100))));
            }
        } catch (Exception e) {
            System.out.println("Abgefangene exception! " + e.getClass());
            e.printStackTrace();
            this.interrupt();
            this.territoryPanel.getTerritory().deleteObservers();
        }
    }
}
