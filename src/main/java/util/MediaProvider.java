package util;

import javafx.scene.media.AudioClip;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;

public class MediaProvider {
    InputStream in;

    public MediaProvider() {
        in = (getClass().getResourceAsStream("../main/resources/death.wav"));

    }

    public void playDeathSound() {
        try {
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(in);
        }catch(IOException ioe){
            System.out.println("Audiofile nicht gefunden");
        }
    }
}
