package GUI.Component;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

/**
 *
 * @author phucp
 */
public class RadioForm extends JPanel {

    private JLabel lblTitle;
    private ButtonGroup group;
    private ArrayList<JRadioButton> radioButtons;

    public RadioForm(String title, String[] options) {
        setLayout(new BorderLayout(5, 5));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblTitle.setBorder(new EmptyBorder(0, 0, 5, 0));

        int count = options.length;
        GridLayout grid;
        if (count > 3) {
            grid = new GridLayout(2, count / 2, 5, 5);
        } else {
            grid = new GridLayout(0, 3, 5, 5);
        }

        JPanel radioPanel = new JPanel(grid);
        radioPanel.setBackground(new Color(250, 241, 230));

        group = new ButtonGroup();
        radioButtons = new ArrayList<>();

        for (String option : options) {
            JRadioButton radio = new JRadioButton(option);
            radio.setBackground(new Color(255, 248, 230));
            radio.setForeground(Color.BLACK);
            radio.setFocusPainted(false);
            radio.setFont(new Font("Tahoma", Font.PLAIN, 13));
            radio.setCursor(new Cursor(Cursor.HAND_CURSOR));

            group.add(radio);
            radioButtons.add(radio);
            radioPanel.add(radio);
        }

        add(lblTitle, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);
    }

    public String getSelectedValue() {
        for (JRadioButton radio : radioButtons) {
            if (radio.isSelected()) {
                return radio.getText();
            }
        }
        return null;
    }

    public void setSelectedValue(String value) {
        for (JRadioButton radio : radioButtons) {
            if (radio.getText().trim().equalsIgnoreCase(value.trim())) {
                radio.setSelected(true);
                return;
            }
        }
    }

    public void setDisabled() {
        for (JRadioButton radio : radioButtons) {
            radio.setEnabled(false);
            radio.setBackground(new Color(220, 220, 220));
        }
    }

    public void setEnabledAll(boolean enabled) {
        for (JRadioButton radio : radioButtons) {
            radio.setEnabled(enabled);
        }
    }

    public void setOptions(String[] options) {
        removeAll();
        radioButtons.clear();
        group = new ButtonGroup();

        JPanel radioPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        radioPanel.setBackground(Color.WHITE);

        for (String option : options) {
            JRadioButton radio = new JRadioButton(option);
            radio.setBackground(new Color(255, 248, 230));
            radio.setForeground(Color.BLACK);
            radio.setFocusPainted(false);
            radio.setFont(new Font("Tahoma", Font.PLAIN, 13));
            radio.setCursor(new Cursor(Cursor.HAND_CURSOR));

            group.add(radio);
            radioButtons.add(radio);
            radioPanel.add(radio);
        }

        add(lblTitle, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
