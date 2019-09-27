package contoller;

import simulation.SimulationsThread;

public class SimulationController {
    public static volatile int simulationState;
    public static final int PAUSED = 0;
    public static final int RUNNING = 1;
    public static final int STOPPED = 2;
    SimulationsThread thread;

    WindowController windowController;
    int speed;

    public SimulationController(WindowController windowController) {
        this.windowController = windowController;
        this.simulationState = PAUSED;
        this.thread = new SimulationsThread(windowController.getTerritoryPanel(), this);
        this.speed = (int)Math.floor(windowController.getCustomToolBar().getSpeedSlider().getValue());
    }

    public void play() {
        try {
            thread.start();
        } catch (IllegalThreadStateException e) {
            thread.resume();
        }
        this.simulationState = RUNNING;
    }

    public void pause() {
        this.simulationState = PAUSED;
        thread.suspend();
        stop();
    }

    public void stop() {
        this.simulationState = STOPPED;
        thread.interrupt();
        thread = new SimulationsThread(this.windowController.getTerritoryPanel(), this);
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public void killAllThreads() {
        this.thread.interrupt();
    }
    public WindowController getWindowController() {
        return windowController;
    }

    public boolean isRunning(){ return this.simulationState == RUNNING;}
    public boolean isStopped(){ return this.simulationState == STOPPED;}
    public boolean isPaused(){return this.simulationState == PAUSED;}
}
