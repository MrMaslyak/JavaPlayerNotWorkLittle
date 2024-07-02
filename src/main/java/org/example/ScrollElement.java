package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ScrollElement extends JPanel implements MouseMotionListener {
    private int xLine = 0;
    private int trackLength;

    ScrollElement(int width, int height, int x, int y, int trackLength) {
        this.trackLength = trackLength;
        setSize(width, height);
        setLayout(null);
        setLocation(x, y);
        setOpaque(false);
        addMouseMotionListener(this);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(241, 210, 210));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g2d.setColor(new Color(200, 66, 66));
        g2d.fillOval(xLine, getHeight() / 2 - 5, 10, 10);
    }

    public void updatePosition(int currentTime) {
        xLine = (int) ((double) currentTime / trackLength * getWidth());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        xLine = e.getX() - 5;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
