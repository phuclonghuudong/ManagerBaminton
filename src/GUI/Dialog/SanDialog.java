package GUI.Dialog;

import BUS.LoaiSanBUS;
import BUS.SanBUS;
import DAO.SanDAO;
import DTO.LoaiSanDTO;
import DTO.SanDTO;
import GUI.Component.ButtonCustome;
import GUI.Component.FormCheckbox;
import GUI.Component.FormInput;
import GUI.Component.FormSelect;
import GUI.Component.HeaderTitle;
import GUI.Panel.San;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import helper.Validation;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author phucp
 */
public final class SanDialog extends JDialog implements MouseListener {

    San jPanelSan;
    SanDTO sanDTO;
    SanBUS sanBUS = new SanBUS();
    LoaiSanBUS loaiSanBUS = new LoaiSanBUS();
    ArrayList<LoaiSanDTO> listLoaiSan;

    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private FormInput txtTenSan, txtMoTa, txtGiaSan;
    private FormCheckbox txtTrangThai;
    private ButtonCustome btnThem, btnCapNhat, btnHuyBo;
    private JTextField txtID;
    private FormSelect cbxLoaiSan;

    public SanDialog(San jpSan, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        listLoaiSan = loaiSanBUS.getAllStatus();
        String[] loaiSanNames = listLoaiSan.stream()
                .map(LoaiSanDTO::getTen_Loai)
                .toArray(String[]::new);

        this.jPanelSan = jpSan;
        txtTenSan = new FormInput("Tên sân");
        cbxLoaiSan = new FormSelect("Loại sân", loaiSanNames);
        txtGiaSan = new FormInput("Giá sân");
        txtMoTa = new FormInput("Mô tả");
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);

        initComponents(title, type);
    }

    public SanDialog(San jpSan, JFrame owner, String title, boolean modal, String type, DTO.SanDTO san) {
        super(owner, title, modal);
        this.sanDTO = san;
        txtID = new JTextField("");
        setID(Integer.toString(san.getID()));

        txtTenSan = new FormInput("Tên sân");
        setTen_San(san.getTen_San());

        listLoaiSan = loaiSanBUS.getAllStatus();
        String[] loaiSanNames = listLoaiSan.stream()
                .map(LoaiSanDTO::getTen_Loai)
                .toArray(String[]::new);

        cbxLoaiSan = new FormSelect("Loại sân", loaiSanNames);

        txtGiaSan = new FormInput("Giá sân");
        setGia_San(String.valueOf(san.getGia_San()));

        txtMoTa = new FormInput("Mô tả");
        setMo_Ta(san.getMo_Ta());

        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        txtTrangThai = new FormCheckbox("Trạng thái", listStatus);
        setStatus(san.getStatus());

        this.jPanelSan = jpSan;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(4, 1, 5, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(txtTenSan);
        pnlMain.add(cbxLoaiSan);
        pnlMain.add(txtGiaSan);
        pnlMain.add(txtMoTa);
        pnlMain.add(txtTrangThai);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustome("Thêm sân", "success", 14);
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
                txtTenSan.setDisable();
                cbxLoaiSan.setDisable();
                txtGiaSan.setDisable();
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

        int currentID = (sanDTO != null) ? sanDTO.getID() : -1;

        if (Validation.isEmpty(txtTenSan.getText())) {
            JOptionPane.showMessageDialog(this, "Tên sân không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(txtGiaSan.getText())) {
            JOptionPane.showMessageDialog(this, "Giá sân không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (SanDAO.getInstance().isNameUnique(txtTenSan.getText(), currentID)) {
            JOptionPane.showMessageDialog(this, "Tên sân đã được sử dụng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String trangThai = getStatus();
        if (trangThai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
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
        int index = cbxLoaiSan.getSelectedIndex();
        int idLoaiSan = listLoaiSan.get(index).getID();

        if (e.getSource() == btnThem && Validation()) {
            int id = SanDAO.getInstance().getAutoIncrement();

            jPanelSan.sanBUS.add(new DTO.SanDTO(id,
                    txtTenSan.getText(),
                    idLoaiSan,
                    Double.parseDouble(txtGiaSan.getText()),
                    txtMoTa.getText(),
                    getStatus().equals("Hoạt động") ? 1 : 0));
            jPanelSan.listDS = jPanelSan.sanBUS.getAllLoaiSan();
            jPanelSan.loadDataTable(jPanelSan.listDS);
            dispose();

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jPanelSan.sanBUS.update(new SanDTO(
                    sanDTO.getID(),
                    txtTenSan.getText(),
                    idLoaiSan,
                    Double.parseDouble(txtGiaSan.getText()),
                    txtMoTa.getText(),
                    getStatus().equals("Hoạt động") ? 1 : 0));
            jPanelSan.listDS = jPanelSan.sanBUS.getAllLoaiSan();
            jPanelSan.loadDataTable(jPanelSan.listDS);
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

    public String getTen_San() {
        return txtTenSan.getText();
    }

    public void setTen_San(String text) {
        txtTenSan.setText(text);
    }

    public String getGia_San() {
        return txtGiaSan.getText();
    }

    public void setGia_San(String text) {
        txtGiaSan.setText(text);
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

    public int getLoai_San() {
        return (int) cbxLoaiSan.getSelectedItem();
    }

    public void setLoai_San(int value) {
        cbxLoaiSan.setSelectedItem(value);
    }

}
