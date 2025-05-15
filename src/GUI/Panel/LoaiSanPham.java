package GUI.Panel;

import BUS.LoaiSanPhamBUS;
import DTO.LoaiSanPhamDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableModel;
import GUI.Dialog.LoaiSanPhamDialog;
import GUI.Main;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import style.StyleColor;
import style.style;

/**
 *
 * @author phucp
 */
public final class LoaiSanPham extends JPanel implements ActionListener, ItemListener {

    StyleColor colorStyle = new StyleColor();
    style style = new style();
    PanelBorderRadius main, functionBar;
    MainFunction mainFunction;
    Main m;

    JTable tableContent;

    public LoaiSanPhamBUS loaiSPBUS = new LoaiSanPhamBUS();
    public ArrayList<LoaiSanPhamDTO> listDS = loaiSPBUS.getAll();
    private TableModel<LoaiSanPhamDTO> tableModel;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    IntegratedSearch search;
    DefaultTableModel tblModel;

    public LoaiSanPham(Main m) {
        this.m = m;
        initComponents();
        initComponent();
    }

    public void initComponent() {
        this.setSize(new Dimension(1030, 670));
        this.setBackground(colorStyle.mainBackgroundColor());
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] header = new String[]{"ID", "Tên loại sản phẩm", "Mô tả", "Trạng thái"};
        String[] methodNames = {"getID", "getTen_Loai", "getMo_Ta", "getStatus"};

        tableModel = new TableModel<>(listDS, header, methodNames);
        tableContent = new JTable(tableModel);

        style.customizeTable(tableContent);

        JScrollPane scrollTable = new JScrollPane(tableContent);
        this.add(scrollTable, BorderLayout.CENTER);
        scrollTable.setBorder(null);
        scrollTable.getViewport().setBorder(null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableContent.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        tableContent.setAutoCreateRowSorter(true);

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
        mainFunction = new MainFunction("loaisan", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã loại", "Tên loại sản phẩm", "Mô tả", "Trạng thái"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listDS = loaiSPBUS.search(txt, type);
                loadDataTable(listDS);
            }
        });

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listDS = loaiSPBUS.getAll();
            loadDataTable(listDS);
        });
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrollTable);
    }

    public void loadDataTable(ArrayList<LoaiSanPhamDTO> result) {
//        ((TableModel<LoaiSanPhamDTO>) tableContent.getModel()).setData(result);
        String[] columnNames = new String[]{"ID", "Tên loại sản phẩm", "Mô tả", "Trạng thái"};
        tblModel = new DefaultTableModel(columnNames, 0);

        //        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (LoaiSanPhamDTO kh : result) {
            String trangThai = kh.getStatus() == 1 ? "Hoạt động" : "Dừng";

            tblModel.addRow(new Object[]{
                kh.getID(),
                kh.getTen_Loai(),
                kh.getMo_Ta(),
                trangThai
            });
        }
        tableContent.setModel(tblModel);
        style.customizeTable(tableContent);

    }

    public int getRowSelected() {
        int index = tableContent.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!");
        }
        return index;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            LoaiSanPhamDialog lspDialog = new LoaiSanPhamDialog(this, owner, "Thêm loại sản phẩm", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                LoaiSanPhamDialog lsDialog = new LoaiSanPhamDialog(this, owner, "Chỉnh sửa loại sản phẩm", true, "update", listDS.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa loại sản phẩm này?", "Xóa loại sản phẩm",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    loaiSPBUS.delete(listDS.get(index));
                    loadDataTable(listDS);
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                LoaiSanPhamDialog lsDialog = new LoaiSanPhamDialog(this, owner, "Xem loại sản phẩm", true, "view", listDS.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableContent);
            } catch (IOException ex) {
                Logger.getLogger(LoaiSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listDS = loaiSPBUS.search(txt, type);
        loadDataTable(listDS);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
