package GUI.Panel;

import BUS.SanPhamBUS;
import DTO.SanPhamDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableModel;
import GUI.Dialog.SanPhamDialog;
import GUI.Main;
import helper.Formater;
import helper.JTableExporter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class SanPham extends JPanel implements ActionListener, ItemListener {

    StyleColor colorStyle = new StyleColor();
    style style = new style();
    PanelBorderRadius main, functionBar;
    MainFunction mainFunction;
    Main m;

    JTable tableContent;

    public SanPhamBUS spBUS = new SanPhamBUS();
    public ArrayList<SanPhamDTO> listDS = spBUS.getAllLoaiSP();
    private TableModel<SanPhamDTO> tableModel;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    IntegratedSearch search;
    DefaultTableModel tblModel;

    public SanPham(Main main) {
        this.m = main;
        initComponents();
        initComponent();

        tableContent.setDefaultEditor(Object.class, null);
        loadDataTable(listDS);
    }

    public void initComponent() {
        this.setSize(new Dimension(1030, 670));
        this.setBackground(colorStyle.mainBackgroundColor());
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Loại sản phẩm", "Giá bán", "Số lương", "Mô tả", "Trạng thái"};
        String[] methodNames = {"", "getID", "getTen_San", "getTen_Loai_SP", "getGia_Ban", "getSo_Luong", "getMo_Ta", "getStatus"};

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
        tableContent.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tableContent.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

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

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã ID", "Tên sản phẩm", "Tên loại SP", "Giá bán", "Mô tả", "Trạng thái"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listDS = spBUS.search(txt, type);
                loadDataTable(listDS);
            }
        });

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listDS = spBUS.getAll();
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

    public void loadDataTable(ArrayList<SanPhamDTO> result) {
        String[] columnNames = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Loại sản phẩm", "Giá bán", "Số lương", "Mô tả", "Trạng thái"};
        tblModel = new DefaultTableModel(columnNames, 0);

        int size = result.size();

        for (int i = 0; i < size; i++) {
            String trangThai = result.get(i).getStatus() == 1 ? "Hoạt động" : result.get(i).getStatus() == 2 ? "Hết hàng" : "Dừng";

            tblModel.addRow(new Object[]{
                i + 1,
                result.get(i).getID(),
                result.get(i).getTen_San_Pham(),
                result.get(i).getTen_Loai_SP(),
                Formater.FormatVND(result.get(i).getGia_Ban()),
                result.get(i).getSo_Luong(),
                result.get(i).getMo_Ta(),
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
            SanPhamDialog t = new SanPhamDialog(this, owner, "Thêm sản phẩm", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                SanPhamDialog t = new SanPhamDialog(this, owner, "Chỉnh sửa sản phẩm", true, "update", listDS.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xóa sản phẩm",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    spBUS.delete(listDS.get(index));
                    loadDataTable(listDS);
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                SanPhamDialog t = new SanPhamDialog(this, owner, "Xem sản phẩm", true, "view", listDS.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableContent);
            } catch (IOException ex) {
                Logger.getLogger(LoaiSan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listDS = spBUS.search(txt, type);
        loadDataTable(listDS);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
