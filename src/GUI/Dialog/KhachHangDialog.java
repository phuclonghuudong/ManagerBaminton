package GUI.Dialog;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import GUI.Component.ButtonCustome;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.KhachHang;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import helper.Validation;

/**
 *
 * @author phucp
 */
public final class KhachHangDialog extends JDialog implements MouseListener {

    KhachHang jPanelKH;
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    ButtonCustome btnThem, btnCapNhat, btnHuyBo;
    private InputForm ten_Nguoi_Dung, so_Dien_Thoai, Email, vai_Tro, status, gioi_Tinh, ngay_Sinh;
    private JTextField maKH;
    KhachHangDTO khDTO;
    KhachHangBUS khachHangBUS = new KhachHangBUS();
    SelectForm cbxVaiTro;

    public KhachHangDialog(KhachHang jPanelKH, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jPanelKH = jPanelKH;
        ten_Nguoi_Dung = new InputForm("Tên khách hàng");
        Email = new InputForm("Email");
        so_Dien_Thoai = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) so_Dien_Thoai.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));

        String[] listKh = khachHangBUS.getArrVaiTro();
        cbxVaiTro = new SelectForm("Vai trò", listKh);

        status = new InputForm("Trạng thái");
        initComponents(title, type);
    }

    public KhachHangDialog(KhachHang jpKH, JFrame owner, String title, boolean modal, String type, DTO.KhachHangDTO kh) {
        super(owner, title, modal);
        this.khDTO = kh;
        maKH = new JTextField("");
        setID(Integer.toString(kh.getID()));
        ten_Nguoi_Dung = new InputForm("Tên người dùng");
        setTen_Nguoi_Dung(kh.getTen_Nguoi_Dung());
        so_Dien_Thoai = new InputForm("Số điện thoại");
        setSo_Dien_Thoai(kh.getSo_Dien_Thoai());
        Email = new InputForm("Email");
        setEmail(kh.getEmail());
//        vai_Tro = new InputForm("Vai trò");
//        setVai_Tro(kh.getVai_Tro());

        String[] listKh = khachHangBUS.getArrVaiTro();
        cbxVaiTro = new SelectForm("Vai trò", listKh);
        cbxVaiTro.setSelectedItem(kh.getVai_Tro());

        this.jPanelKH = jPanelKH;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(4, 2, 20, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(ten_Nguoi_Dung);
        pnlMain.add(so_Dien_Thoai);
        pnlMain.add(Email);
        pnlMain.add(cbxVaiTro);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustome("Thêm khách hàng", "success", 14);
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
                ten_Nguoi_Dung.setDisable();
                Email.setDisable();
                so_Dien_Thoai.setDisable();
                cbxVaiTro.setDisable();
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
        return maKH.getText();
    }

    public void setID(String id) {
        this.maKH.setText(id);
    }

    public String getTen_Nguoi_Dung() {
        return ten_Nguoi_Dung.getText();
    }

    public void setTen_Nguoi_Dung(String Ten_Nguoi_Dung) {
        ten_Nguoi_Dung.setText(Ten_Nguoi_Dung);
    }

    public String getEmail() {
        return Email.getText();
    }

    public void setEmail(String email) {
        Email.setText(email);
    }

//    public String getNgay_Sinh() {
//        return ngay_Sinh.getText();
//    }
//
//    public void setNgay_Sinh(Date Ngay_Sinh) {
//        ngay_Sinh.setText(Ngay_Sinh);
//    }
    public String getSo_Dien_Thoai() {
        return so_Dien_Thoai.getText();
    }

    public void setSo_Dien_Thoai(String So_Dien_Thoai) {
        so_Dien_Thoai.setText(So_Dien_Thoai);
    }

    public String getVai_Tro() {
        return (String) cbxVaiTro.getSelectedItem();
    }

    public void setVai_Tro(String Vai_Tro) {
        cbxVaiTro.setSelectedItem(Vai_Tro);
    }

    public String isGioi_Tinh() {
        return gioi_Tinh.getText();
    }

    public void setGioi_Tinh(boolean Gioi_Tinh) {
        gioi_Tinh.setText(Gioi_Tinh ? "Nam" : "Nữ");
    }

    public String getStatus() {
        return status.getText();
    }

    public void setStatus(String Status) {
        status.setText(Status);
    }

    boolean Validation() {
        if (Validation.isEmpty(ten_Nguoi_Dung.getText())) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(so_Dien_Thoai.getText()) || !Validation.isNumber(so_Dien_Thoai.getText()) && so_Dien_Thoai.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(Email.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
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
            int id = KhachHangDAO.getInstance().getAutoIncrement();
            jPanelKH.khachhangBUS.add(new DTO.KhachHangDTO(id, ten_Nguoi_Dung.getText(), so_Dien_Thoai.getText(), Email.getText()));
            jPanelKH.loadDataTable(jPanelKH.listkh);
            dispose();

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jPanelKH.khachhangBUS.update(new KhachHangDTO(khDTO.getID(), ten_Nguoi_Dung.getText(), so_Dien_Thoai.getText(), Email.getText()));
            jPanelKH.loadDataTable(jPanelKH.listkh);
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
