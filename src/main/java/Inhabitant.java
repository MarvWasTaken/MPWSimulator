import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Inhabitant {
    private ImageView image;

    public Inhabitant(){};

    public Inhabitant(String path){
        try {
            image = new ImageView(new Image(getClass().getResource(path).toString()));
            image.setFitHeight(24);
            image.setFitWidth(24);
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + path + " nicht gefunden!");
            return;
        }
    }
}

