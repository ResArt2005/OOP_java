package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;

import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {
    private int borderRadius;

    public RoundedLabel(String text, int borderRadius) {
        super(text);
        this.borderRadius = borderRadius;
        setOpaque(false); // Устанавливаем непрозрачность в false
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем закругленный прямоугольник
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        super.paintComponent(g);
    }
}