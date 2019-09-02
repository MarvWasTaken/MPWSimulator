import exceptions.CollectibleCountMayNotBeNegativeException;
import exceptions.ObstacleNotPossibleException;
import model.Territory;

public class TestAufgabe2 {
    static void startTest() throws ObstacleNotPossibleException, CollectibleCountMayNotBeNegativeException {
        System.out.println("durchlauf1:");
        Territory t = new Territory(5, 5);
        t.addObstacle(1, 0);
        t.addObstacle(1, 1);
        t.addObstacle(1, 2);
        t.addObstacle(1, 3);
        t.setCollectiblesCount(4, 4, 9);
        t.print();
        boolean running = true;
        while (running) {
            try {
                char c = IO.readChar("l = linksUm(), m = bewegen, n = nimm, g = gib, q = beenden");
                switch (c) {
                    case 'l':
                        t.getActor().linksUm();
                        break;
                    case 'm':
                        t.getActor().move();
                        break;
                    case 'g':
                        t.getActor().gib();
                        break;
                    case 'n':
                        t.getActor().nimm();
                        break;
                    case 'q':
                        running = false;
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getClass().toString());
            }
            t.print();
        }
    }
}
