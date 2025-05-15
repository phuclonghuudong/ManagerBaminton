package GUI.Component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author phucp
 */
public class ButtonCustome extends JButton {

    public void initComponent(String type, String text, int fontsize, int width, int height) {
        Color colorKey;
        switch (type) {
            case "success":
                colorKey = new Color(19, 62, 135);
                break;
            case "danger":
                colorKey = new Color(220, 53, 69);
                break;
            case "warning":
                colorKey = new Color(255, 193, 7);
                break;
            case "excel":
                colorKey = new Color(40, 167, 69);
                break;
            case "return":
                colorKey = new Color(255, 133, 27);
                break;
            case "ok":
                colorKey = new Color(33, 37, 41);
                break;
            default:
                colorKey = Color.WHITE;
        }
        this.setText(text.toUpperCase());
        this.setBackground(colorKey);
        this.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.setForeground(Color.WHITE);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.BLACK);
                setBackground(new Color(204, 214, 219));
                setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorKey);
                setForeground(Color.WHITE);
                setOpaque(true);
            }
        });
    }

    public ButtonCustome(String text, String type, int fontsize) {
        initComponent(type, text, fontsize, 190, 40);
    }

    public ButtonCustome(String text, String type, int fontsize, int w, int h) {
        initComponent(type, text, fontsize, w, h);
    }

    public ButtonCustome(String text, String type, int fontsize, String linkIcon) {
        initComponent(type, text, fontsize, 150, 40);
        this.setIcon(SvgImageComponent.loadSvgAsIcon(linkIcon, 35, 40));
    }

    public ButtonCustome(String text, String type, int fontsize, String linkIcon, int width, int height) {
        initComponent(type, text, fontsize, width, height);
        this.setIcon(SvgImageComponent.loadSvgAsIcon(linkIcon, 35, 40));
    }

    public void setVisible(Boolean value) {
        this.setVisible(value);
    }

}
