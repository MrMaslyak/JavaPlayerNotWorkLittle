package org.example;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MusicFile {

    public void playMP3(String filePath) {
        try {
            // Завантажуємо файл з ресурсів
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не знайдено: " + filePath);
            }
            AdvancedPlayer player = new AdvancedPlayer(inputStream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }


}


