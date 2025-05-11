package GUI.Component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author phucp
 */
public class IntegratedSearch extends JPanel {

    public JComboBox<String> cbxChoose;
    public JButton btnReset;
    public JTextField txtSearchForm;
    JScrollPane scrollPane;

    private void initComponent(String str[]) {

        this.setBackground(Color.WHITE);
        BoxLayout bx = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bx);

        JPanel jpSearch = new JPanel(new BorderLayout(5, 10));
        jpSearch.setBorder(new EmptyBorder(18, 15, 18, 15));
        jpSearch.setBackground(Color.white);
        cbxChoose = new JComboBox();
        cbxChoose.setModel(new DefaultComboBoxModel<>(str));
        cbxChoose.setPreferredSize(new Dimension(140, 0));
        cbxChoose.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cbxChoose.setFocusable(false);
        cbxChoose.setOpaque(false);
        cbxChoose.setBackground(Color.WHITE);
        cbxChoose.setForeground(Color.BLACK);

        jpSearch.add(cbxChoose, BorderLayout.WEST);

        txtSearchForm = new JTextField();
        txtSearchForm.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtSearchForm.setBorder(new EmptyBorder(2, 10, 2, 10));
        txtSearchForm.setBackground(new Color(238, 241, 218));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        jpSearch.add(txtSearchForm);

        btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(255, 204, 153));
        btnReset.setForeground(Color.BLACK);
        btnReset.setPreferredSize(new Dimension(125, 0));
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReset.setBorderPainted(false);
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
        btnReset.setOpaque(true);
        btnReset.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        btnReset.setIcon(SvgImageComponent.loadSvgAsIcon("refresh.svg", 35, 40));

        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnReset.setForeground(Color.BLACK);
                btnReset.setBackground(new Color(204, 214, 219));
                btnReset.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnReset.setForeground(Color.BLACK);
                btnReset.setBackground(new Color(255, 204, 153));
                btnReset.setOpaque(true);
            }
        });
        jpSearch.add(btnReset, BorderLayout.EAST);
        this.add(jpSearch);

    }

    public IntegratedSearch(String str[]) {
        initComponent(str);
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent e) {
        txtSearchForm.setText("");
        cbxChoose.setSelectedIndex(0);
    }
}
