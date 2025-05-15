package GUI.Dialog;

import BUS.LoaiSanPhamBUS;
import BUS.SanPhamBUS;
import DAO.SanPhamDAO;
import DTO.LoaiSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustome;
import GUI.Component.FormCheckbox;
import GUI.Component.FormInput;
import GUI.Component.FormSelect;
import GUI.Component.HeaderTitle;
import GUI.Panel.SanPham;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author phucp
 */
public final class SanPhamDialog extends JDialog implements MouseListener {

    SanPham jPanelSP;
    SanPhamDTO spDTO;
    SanPhamBUS spBUS = new SanPhamBUS();
    LoaiSanPhamBUS loaiSPBUS = new LoaiSanPhamBUS();
    ArrayList<LoaiSanPhamDTO> listLoaiSP;

    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private FormInput txtTenSP, txtMoTa, txtGiaBan, txtSoLuong;
    private FormCheckbox txtTrangThai;
    private ButtonCustome btnThem, btnCapNhat, btnHuyBo;
    private JTextField txtID;
    private FormSelect cbxLoaiSP;

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        String[] listStatus = new String[]{"Hoạt động", "Dừng", "Hết hàng"};
        listLoaiSP = loaiSPBUS.getAllStatus();
        String[] loaiSPNames = listLoaiSP.stream()
                .map(LoaiSanPhamDTO::getTen_Loai)
                .toArray(String[]::new);

        this.jPanelSP = jpSP;
        txtTenSP = new FormInput("Tên sản phẩm");
        cbxLoaiSP = new FormSelect("Loại sản phẩm", loaiSPNames);
        txtGiaBan = new FormInput("Giá bán");
        txtSoLuong = new FormInput("Số lượng");
        txtMoTa = new FormInput("Mô tả");
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);

        initComponents(title, type);
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type, DTO.SanPhamDTO sp) {
        super(owner, title, modal);
        this.spDTO = sp;
        txtID = new JTextField("");
        setID(Integer.toString(sp.getID()));

        txtTenSP = new FormInput("Tên sản phẩm");
        setTen_SP(sp.getTen_San_Pham());

        listLoaiSP = loaiSPBUS.getAllStatus();
        String[] loaiSanNames = listLoaiSP.stream()
                .map(LoaiSanPhamDTO::getTen_Loai)
                .toArray(String[]::new);

        cbxLoaiSP = new FormSelect("Loại sản phẩm", loaiSanNames);

        txtGiaBan = new FormInput("Giá bán");
        setGia_Ban(String.valueOf(sp.getGia_Ban()));

        txtSoLuong = new FormInput("Số lượng");
        setSo_Luong(String.valueOf(sp.getSo_Luong()));

        txtMoTa = new FormInput("Mô tả");
        setMo_Ta(sp.getMo_Ta());

        String[] listStatus = new String[]{"Hoạt động", "Dừng", "Hết hàng"};
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);
        setStatus(sp.getStatus());

        this.jPanelSP = jpSP;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(4, 1, 5, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(txtTenSP);
        pnlMain.add(cbxLoaiSP);
        pnlMain.add(txtGiaBan);
        pnlMain.add(txtSoLuong);
        pnlMain.add(txtMoTa);
        pnlMain.add(txtTrangThai);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustome("Thêm sản phẩm", "success", 14);
        btnCapNhat = new ButtonCustome("Lưu thông tin", "success", 14);
        btnHuyBo = new ButtonCustome("Huỷ bỏ", "danger", 14);

        btnThem.addMouseListener(this);
        btnCapNhat.addMouseListener(this);
        btnHuyBo.addMouseListener(this);

        switch (type) {
            case "create" ->
                pnlButtom.add(btnThem);
            case "update" ->
                pnlButtom.add(btnCapNhat);
            case "view" -> {
                txtTenSP.setDisable();
                cbxLoaiSP.setDisable();
                txtGiaBan.setDisable();
                txtSoLuong.setDisable();
                txtMoTa.setDisable();
                txtTrangThai.setDisabled();
            }
            default ->
                throw new AssertionError();
        }
        pnlButtom.add(btnHuyBo);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    boolean Validation() {

        int currentID = (spDTO != null) ? spDTO.getID() : -1;

        if (Validation.isEmpty(txtTenSP.getText())) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(txtGiaBan.getText())) {
            JOptionPane.showMessageDialog(this, "Giá bán không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (SanPhamDAO.getInstance().isNameUnique(txtTenSP.getText(), currentID)) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm đã được sử dụng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String trangThai = getStatus();
        if (trangThai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
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
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int index = cbxLoaiSP.getSelectedIndex();
        int idLoaiSan = listLoaiSP.get(index).getID();

        int soLuong = Integer.parseInt(txtSoLuong.getText());
        int trangThai;

        if (soLuong == 0) {
            trangThai = 2; // Hết hàng
        } else {
            trangThai = getStatus().equals("Hoạt động") ? 1 : 0;
        }

        if (e.getSource() == btnThem && Validation()) {
            int id = SanPhamDAO.getInstance().getAutoIncrement();

            jPanelSP.spBUS.add(new DTO.SanPhamDTO(id,
                    txtTenSP.getText(),
                    idLoaiSan,
                    Double.parseDouble(txtGiaBan.getText()),
                    soLuong,
                    txtMoTa.getText(),
                    trangThai));
            jPanelSP.listDS = jPanelSP.spBUS.getAllLoaiSP();
            jPanelSP.loadDataTable(jPanelSP.listDS);
            dispose();

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jPanelSP.spBUS.update(new SanPhamDTO(
                    spDTO.getID(),
                    txtTenSP.getText(),
                    idLoaiSan,
                    Double.parseDouble(txtGiaBan.getText()),
                    soLuong,
                    txtMoTa.getText(),
                    trangThai));
            jPanelSP.listDS = jPanelSP.spBUS.getAllLoaiSP();
            jPanelSP.loadDataTable(jPanelSP.listDS);
            dispose();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getID() {
        return txtID.getText();
    }

    public void setID(String id) {
        this.txtID.setText(id);
    }

    public String getTen_SP() {
        return txtTenSP.getText();
    }

    public void setTen_SP(String text) {
        txtTenSP.setText(text);
    }

    public String getGia_Ban() {
        return txtGiaBan.getText();
    }

    public void setGia_Ban(String text) {
        txtGiaBan.setText(text);
    }

    public String getSo_Luong() {
        return txtSoLuong.getText();
    }

    public void setSo_Luong(String text) {
        txtSoLuong.setText(text);
    }

    public String getMo_Ta() {
        return txtMoTa.getText();
    }

    public void setMo_Ta(String text) {
        txtMoTa.setText(text);
    }

    public String getStatus() {
        var selected = txtTrangThai.getSelectedValues();
        return selected.isEmpty() ? null : selected.get(0);

    }

    public void setStatus(int giatri) {
        String[] value = new String[]{giatri == 1 ? "Hoạt động" : giatri == 2 ? "Hết hàng" : "Dừng"};
        txtTrangThai.setSelectedValues(value);
    }

    public int getLoai_SP() {
        return (int) cbxLoaiSP.getSelectedItem();
    }

    public void setLoai_SP(int value) {
        cbxLoaiSP.setSelectedItem(value);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
