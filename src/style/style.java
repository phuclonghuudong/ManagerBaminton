package style;

import GUI.Component.SvgImageComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

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

//    Button
    public void hoverButtonCreate(JButton btn) {
        btn.setForeground(new Color(250, 241, 230));
        btn.setBackground(new Color(50, 142, 110));

        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
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

        });
    }

    public void ButtonCreate(JButton btn) {
        btn.setForeground(new Color(250, 241, 230));
        btn.setBackground(new Color(50, 142, 110));

        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
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

        });
    }

    public void ButtonSearch(JButton btn, String icon) {
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(255, 204, 153));
        btn.setPreferredSize(new Dimension(125, 0));
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);

        btn.setIcon(SvgImageComponent.loadSvgAsIcon(icon, 35, 40));

        // Mouse Listener để xử lý hiệu ứng hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.BLACK);
                btn.setBackground(new Color(103, 174, 110));
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(true); // Đảm bảo border được vẽ khi hover
                btn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.BLACK);
                btn.setBackground(new Color(255, 204, 153));
                btn.setContentAreaFilled(false); // Set lại phần nội dung của nút không tô màu khi rời chuột
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                btn.setBorderPainted(true); // Đảm bảo border vẫn được vẽ
            }
        });
    }

    public void buttonCustome(JButton btn) {
        if (btn == null) {
            return;
        }
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
                btn.setBackground(new Color(255, 204, 153));
                btn.setOpaque(true);
            }

            private String rgb(int i, int i0, int i1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

//  Table Model
    public void customizeTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setBorder(null);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                } else {
                    c.setBackground(new Color(220, 230, 240));
                    c.setForeground(new Color(0));
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        JTableHeader header = table.getTableHeader();

        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setReorderingAllowed(false);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBackground(new Color(255, 204, 153));
                label.setForeground(Color.BLACK);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setOpaque(true);
                return label;
            }
        });
    }

}
