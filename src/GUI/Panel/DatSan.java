package GUI.Panel;

import GUI.Main;
import java.awt.*;
import javax.swing.*;
import style.StyleColor;

/**
 *
 * @author phucp
 */
public class DatSan extends JPanel {

    StyleColor colorStyle = new StyleColor();
    Main main;

    public DatSan(Main m) {
        this.main = m;
        initComponents();
        initComponent();
    }

    public void initComponent() {
        this.setSize(new Dimension(1030, 670));
        this.setBackground(colorStyle.mainBackgroundColor());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
