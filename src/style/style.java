package style;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author phucp
 */
public class style {

    public Color mainBackgroundColorOrange() {
        return new Color(255, 204, 153);
    }

    public Color mainBackgroundColor() {
        return new Color(240, 247, 250);
    }

    public Color mainBackgroundColorGray() {
        return new Color(255, 255, 255);
    }

    public Border borderInput() {
        return new EmptyBorder(2, 10, 2, 10);
    }

    public static void setUIFont16() {
        Font font = new Font("Tahoma", Font.PLAIN, 16);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    public static void setUIFont14() {
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    public void hoverButtonCreate(JButton btn) {
        btn.setForeground(new Color(250, 241, 230));
        btn.setBackground(new Color(50, 142, 110));

        btn.setBorderPainted(false);   // Không vẽ border
        btn.setContentAreaFilled(false); // Không tô nền
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.BLACK);
                btn.setBackground(new Color(103, 174, 110));
                btn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(250, 241, 230));
                btn.setBackground(new Color(50, 142, 110));
                btn.setOpaque(true);
            }

            private String rgb(int i, int i0, int i1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

}
