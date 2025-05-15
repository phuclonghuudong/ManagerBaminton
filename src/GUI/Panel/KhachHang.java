package GUI.Panel;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableModel;
import GUI.Dialog.KhachHangDialog;
import GUI.Main;
import helper.Formater;
import helper.JTableExporter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import style.style;

/**
 *
 * @author phucp
 */
public class KhachHang extends JPanel implements ActionListener, ItemListener {

    style style = new style();
    Main m;
    MainFunction mainFunction;
    PanelBorderRadius main, functionBar;
    public KhachHangBUS khachhangBUS = new KhachHangBUS();
    public ArrayList<KhachHangDTO> listkh = khachhangBUS.getAll();
    JTable tableKhachHang;
    private TableModel<KhachHangDTO> tableModel;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    DefaultTableModel tblModel;
    IntegratedSearch search;

    public void initComponent() {
        this.setSize(new Dimension(1030, 670));
        this.setBackground(style.mainBackgroundColor());
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] header = new String[]{"STT", "Mã ID", "Tên khách hàng", "Email", "Số điện thoại", "Giới tính", "Vai trò", "Sinh Nhật", "Trạng thái"};
        String[] methodNames = {"", "getID", "getTen_Nguoi_Dung", "getEmail", "getSo_Dien_Thoai", "isGioi_Tinh", "getVai_Tro", "getNgay_Sinh", "getStatus"};

        tableModel = new TableModel<>(listkh, header, methodNames);
        tableKhachHang = new JTable(tableModel);

        style.customizeTable(tableKhachHang);

        JScrollPane scrollTableKhachHang = new JScrollPane(tableKhachHang);
        this.add(scrollTableKhachHang, BorderLayout.CENTER);
        scrollTableKhachHang.setBorder(null);
        scrollTableKhachHang.getViewport().setBorder(null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableKhachHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);

        tableKhachHang.setAutoCreateRowSorter(true);

        JPanel contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(new Color(240, 247, 250));
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction("khachhang", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã khách hàng", "Tên khách hàng", "Email", "Số điện thoại", "Giới tính", "Vai trò", "Trạng thái"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listkh = khachhangBUS.search(txt, type);
                loadDataTable(listkh);
            }
        });

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listkh = khachhangBUS.getAll();
            loadDataTable(listkh);
        });
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrollTableKhachHang);
    }

    public KhachHang(Main m) {
        this.m = m;
        initComponents();
        initComponent();

        tableKhachHang.setDefaultEditor(Object.class, null);
        loadDataTable(listkh);

    }

    public int getRowSelected() {
        int index = tableKhachHang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
        }
        return index;
    }

    public void loadDataTable(ArrayList<KhachHangDTO> result) {
        String[] columnNames = new String[]{"STT", "Mã ID", "Tên khách hàng", "Email", "Số điện thoại", "Giới tính", "Vai trò", "Sinh Nhật", "Trạng thái"};
        tblModel = new DefaultTableModel(columnNames, 0);

        for (int i = 0; i < result.size(); i++) {
            String gioiTinh = result.get(i).isGioi_Tinh() ? "Nam" : "Nữ";
            String ngaySinh = result.get(i).getNgay_Sinh() != null ? Formater.FormatDate(result.get(i).getNgay_Sinh()) : "";
            String trangThai = result.get(i).getStatus() == 1 ? "Hoạt động" : "Dừng";

            tblModel.addRow(new Object[]{
                i + 1,
                result.get(i).getID(),
                result.get(i).getTen_Nguoi_Dung(),
                result.get(i).getEmail(),
                result.get(i).getSo_Dien_Thoai(),
                gioiTinh,
                result.get(i).getVai_Tro(),
                ngaySinh,
                trangThai
            });
        }

        tableKhachHang.setModel(tblModel);
        style.customizeTable(tableKhachHang);

//        ((TableModel<KhachHangDTO>) tableKhachHang.getModel()).setData(result);
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
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mainFunction.btn.get("create")) {
            KhachHangDialog khDialog = new KhachHangDialog(this, owner, "Thêm người dùng", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                KhachHangDialog khDialog = new KhachHangDialog(this, owner, "Chỉnh sửa người dùng", true, "update", listkh.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa người dùng ?", "Xóa người dùng",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    khachhangBUS.delete(listkh.get(index));
                    loadDataTable(listkh);
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                KhachHangDialog khDialog = new KhachHangDialog(this, owner, "Xem người dùng", true, "view", listkh.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableKhachHang);

            } catch (IOException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listkh = khachhangBUS.search(txt, type);
        loadDataTable(listkh);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
