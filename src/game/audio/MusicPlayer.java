package game.audio;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer extends Thread {

    private String filename;

    private Position curPosition;

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

    enum Position {
        LEFT, RIGHT, NORMAL
    };

    public MusicPlayer(String wavfile) {

        filename = wavfile;
        curPosition = Position.NORMAL;
    }

    public MusicPlayer(String wavfile, Position p) {
        filename = wavfile;
        curPosition = p;
    }

    public void run() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(MusicPlayer.class.getResource(filename));
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine sourceDataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (sourceDataLine.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl pan = (FloatControl) sourceDataLine
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT)
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT)
                pan.setValue(-1.0f);
        }

        sourceDataLine.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    sourceDataLine.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            sourceDataLine.drain();
            sourceDataLine.close();
        }
    }
} 
 