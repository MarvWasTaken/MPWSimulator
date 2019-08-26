public class TestAufgabe2 {
    static void startTest(){
        System.out.println("durchlauf1:");
        Territory t = new Territory(5, 5);
        t.addObstacle(1,0);
        t.addObstacle(1,1);
        t.addObstacle(1,2);
        t.addObstacle(1,3);
        t.print();
        boolean running = true;
        while (running){
            char c = IO.readChar("l = linksUm(), m = bewegen, n = nimm, g = gib, q = beenden");
            switch (c){
                case 'l':
                    t.getActor().linksUm();
                    t.print();
                    break;
                case 'm':
                    t.getActor().move();
                    t.print();
                    break;
                case 'g':
                    t.getActor().gib();
                    t.print();
                    break;
                case 'n':
                    t.getActor().nimm();
                    t.print();
                    break;
                case 'q':
                    running = false;
            }

        }
    }
}
