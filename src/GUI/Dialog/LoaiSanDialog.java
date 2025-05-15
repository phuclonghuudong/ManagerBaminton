package GUI.Dialog;

import BUS.LoaiSanBUS;
import DAO.LoaiSanDAO;
import DTO.LoaiSanDTO;
import GUI.Component.ButtonCustome;
import GUI.Component.FormCheckbox;
import GUI.Component.FormInput;
import GUI.Component.HeaderTitle;
import GUI.Panel.LoaiSan;
import helper.Validation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author phucp
 */
public final class LoaiSanDialog extends JDialog implements MouseListener {

    LoaiSan jPanelLS;
    LoaiSanDTO loaiSanDTO;
    LoaiSanBUS loaiSanBUS = new LoaiSanBUS();

    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private FormInput txtTenLoai, txtMoTa;
    private FormCheckbox txtTrangThai;
    private ButtonCustome btnThem, btnCapNhat, btnHuyBo;
    private JTextField txtID;

    public LoaiSanDialog(LoaiSan jpLS, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jPanelLS = jpLS;
        txtTenLoai = new FormInput("Tên loại sân");
        txtMoTa = new FormInput("Mô tả");
        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);

        initComponents(title, type);
    }

    public LoaiSanDialog(LoaiSan jpLS, JFrame owner, String title, boolean modal, String type, DTO.LoaiSanDTO ls) {
        super(owner, title, modal);
        this.loaiSanDTO = ls;
        txtID = new JTextField("");
        setID(Integer.toString(ls.getID()));
        txtTenLoai = new FormInput("Tên loại sân");
        setTen_Loai(ls.getTen_Loai());

        txtMoTa = new FormInput("Mô tả");
        setMo_Ta(ls.getMo_Ta());

        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);
        setStatus(ls.getStatus());

        this.jPanelLS = jpLS;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(500, 600));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(4, 1, 5, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(txtTenLoai);
        pnlMain.add(txtMoTa);
        pnlMain.add(txtTrangThai);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustome("Thêm loại", "success", 14);
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
                txtTenLoai.setDisable();
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

    public String getID() {
        return txtID.getText();
    }

    public void setID(String id) {
        this.txtID.setText(id);
    }

    public String getTen_Loai() {
        return txtTenLoai.getText();
    }

    public void setTen_Loai(String text) {
        txtTenLoai.setText(text);
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
        String[] value = new String[]{giatri == 1 ? "Hoạt động" : "Dừng"};
        txtTrangThai.setSelectedValues(value);
    }

    boolean Validation() {

        int currentID = (loaiSanDTO != null) ? loaiSanDTO.getID() : -1;

        if (Validation.isEmpty(txtTenLoai.getText())) {
            JOptionPane.showMessageDialog(this, "Tên loại sân không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String trangThai = getStatus();
        if (trangThai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (LoaiSanDAO.getInstance().isNameUnique(txtTenLoai.getText(), currentID)) {
            JOptionPane.showMessageDialog(this, "Tên loại đã được sử dụng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == btnThem && Validation()) {
            int id = LoaiSanDAO.getInstance().getAutoIncrement();

            jPanelLS.loaiSanBUS.add(new DTO.LoaiSanDTO(id,
                    txtTenLoai.getText(),
                    txtMoTa.getText(),
                    getStatus().equals("Hoạt động") ? 1 : 0));
            jPanelLS.loadDataTable(jPanelLS.listDS);
            dispose();

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jPanelLS.loaiSanBUS.update(new LoaiSanDTO(
                    loaiSanDTO.getID(),
                    txtTenLoai.getText(),
                    txtMoTa.getText(),
                    getStatus().equals("Hoạt động") ? 1 : 0));
            jPanelLS.loadDataTable(jPanelLS.listDS);
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

}
