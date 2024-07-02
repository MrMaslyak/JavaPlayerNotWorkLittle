package org.example;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.Header;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlay extends JFrame implements ActionListener {
    JButton playButton;
    AdvancedPlayer player;
    String filePath;
    Thread playThread;
    volatile boolean isPlaying;
    int trackLength;
    int currentTime;
    ScrollElement scrollElement;

    MusicPlay() {
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Music Player");
        filePath = "Eminem - Lose Yourself.mp3";
        trackLength = getTrackLength(filePath);
        ui();
        setVisible(true);
    }

    public void ui() {
        setLayout(null);
        playButton = new JButton("|>");
        playButton.setBounds(170, 350, 50, 50);
        playButton.addActionListener(this);
        playButton.setFocusable(false);
        playButton.setBackground(new Color(228, 225, 225));
        add(playButton);

        scrollElement = new ScrollElement(300, 30, 50, 320, trackLength);
        add(scrollElement);
    }

    private int getTrackLength(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            Bitstream bitstream = new Bitstream(bis);
            Header header = bitstream.readFrame();
            int size = bis.available();
            int maxNumberOfFrames = size / header.max_number_of_frames(size);
            return (int) (header.total_ms(size) / maxNumberOfFrames);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void playMP3() {
        isPlaying = true;
        playThread = new Thread(() -> {
            try {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
                if (inputStream == null) {
                    throw new IllegalArgumentException("File not found: " + filePath);
                }
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                player = new AdvancedPlayer(bis);
                player.play();
                while (isPlaying && player.play(1)) {
                    currentTime += 26; // Update current play time (approximate value)
                    scrollElement.updatePosition(currentTime);
                    Thread.sleep(26); // Update every 26 milliseconds
                }
            } catch (JavaLayerException | IllegalArgumentException | InterruptedException e) {
                e.printStackTrace();
                isPlaying = false;
            }
        });
        playThread.start();
    }

    public void stopMP3() {
        isPlaying = false;
        if (player != null) {
            player.close();
        }
        if (playThread != null) {
            playThread.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            if (playButton.getText().equals("|>")) {
                playButton.setText("||");
                playMP3();
            } else if (playButton.getText().equals("||")) {
                playButton.setText("|>");
                stopMP3();
            }
        }
    }

}
