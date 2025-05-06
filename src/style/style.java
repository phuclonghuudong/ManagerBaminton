package style;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.*;

/**
 *
 * @author phucp
 */
public class style {

    public Color mainBackgroundColorOrange() {
        return new Color(255, 204, 153);
    }

    public Color mainBackgroundColor() {
        return new Color(250, 250, 250);
    }

    public Border borderInput() {
        return new EmptyBorder(2, 10, 2, 10);
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
