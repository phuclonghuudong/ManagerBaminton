package GUI.Component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author phucp
 */
public class ButtonToolBar extends JButton {

    String permisson;

    public ButtonToolBar(String text, String icon, String permisson) {
        this.permisson = permisson;
        this.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        this.setForeground(new Color(1, 88, 155));
        this.setIcon(SvgImageComponent.loadSvgAsIcon(icon, 35, 40));
        this.setText(text);
        this.setFocusable(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.putClientProperty("JButton.buttonType", "toolBarButton");
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.BLACK);
                setBackground(new Color(204, 214, 219));
                setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(new Color(1, 88, 155));
                setBackground(Color.WHITE);
                setOpaque(true);
            }
        });
    }

    public String getPermisson() {
        return this.permisson;
    }
}

//public ButtonToolBar(String text, String icon, String permisson) {
//        this.permisson = permisson;
//        JButton button = new JButton(text);
//        button.setFont(new Font("Tahoma", Font.BOLD, 13));
//        button.setForeground(new Color(1, 88, 155));
//        button.setBackground(Color.WHITE);
//        button.setIcon(SvgImageComponent.loadSvgAsIcon(icon, 35, 40));
//        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setVerticalTextPosition(SwingConstants.BOTTOM);
//        button.setFocusable(false);
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
//        button.setOpaque(true);
//        button.putClientProperty("JButton.buttonType", "toolBarButton");
//
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                button.setForeground(Color.BLACK);
//                button.setBackground(new Color(204, 214, 219));
//                button.setOpaque(true);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                button.setForeground(new Color(1, 88, 155));
//                button.setBackground(Color.WHITE);
//                button.setOpaque(true);
//            }
//        });
//
//        this.add(button);
//    }
