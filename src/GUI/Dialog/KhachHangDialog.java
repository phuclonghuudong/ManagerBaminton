package GUI.Dialog;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import GUI.Component.ButtonCustome;
import GUI.Component.FormCheckbox;
import GUI.Component.FormDate;
import GUI.Component.FormInput;
import GUI.Component.FormSelect;
import GUI.Component.HeaderTitle;
import GUI.Component.NumericDocumentFilter;
import GUI.Panel.KhachHang;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import helper.Validation;
import java.util.Date;

/**
 *
 * @author phucp
 */
public final class KhachHangDialog extends JDialog implements MouseListener {

    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustome btnThem, btnCapNhat, btnHuyBo;
    private FormInput tenNguoiDung, soDienThoai, Email, vaiTro;
    private JTextField maKH;
    private FormSelect cbxVaiTro;
    private FormCheckbox cbxStatus, cbxGioiTinh;
    private FormDate dayNgaySinh;

    KhachHang jPanelKH;
    KhachHangDTO khDTO;
    KhachHangBUS khachHangBUS = new KhachHangBUS();

    public KhachHangDialog(KhachHang jpKH, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jPanelKH = jpKH;
        tenNguoiDung = new FormInput("Tên khách hàng");
        Email = new FormInput("Email");

        soDienThoai = new FormInput("Số điện thoại");
        PlainDocument phonex = (PlainDocument) soDienThoai.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));

        String[] listKh = khachHangBUS.getArrVaiTro();
        cbxVaiTro = new FormSelect("Vai trò", listKh);

        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        cbxStatus = new FormCheckbox("Trạng thái", listStatus);

        String[] listGioiTinh = new String[]{"Nam", "Nữ"};
        cbxGioiTinh = new FormCheckbox("Giới tính", listGioiTinh);

        dayNgaySinh = new FormDate("Ngày sinh:");

        initComponents(title, type);
    }

    public KhachHangDialog(KhachHang jpKH, JFrame owner, String title, boolean modal, String type, DTO.KhachHangDTO kh) {
        super(owner, title, modal);
        this.khDTO = kh;
        maKH = new JTextField("");
        setID(Integer.toString(kh.getID()));
        tenNguoiDung = new FormInput("Tên người dùng");
        setTen_Nguoi_Dung(kh.getTen_Nguoi_Dung());
        soDienThoai = new FormInput("Số điện thoại");
        setSo_Dien_Thoai(kh.getSo_Dien_Thoai());
        Email = new FormInput("Email");
        setEmail(kh.getEmail());

        String[] listKh = khachHangBUS.getArrVaiTro();
        cbxVaiTro = new FormSelect("Vai trò", listKh);
        cbxVaiTro.setSelectedItem(kh.getVai_Tro());

        String[] listStatus = new String[]{"Hoạt động", "Dừng"};
        cbxStatus = new FormCheckbox("Trạng thái", listStatus);
        setStatus(kh.getStatus());

        String[] listGioiTinh = new String[]{"Nam", "Nữ"};
        cbxGioiTinh = new FormCheckbox("Giới tính", listGioiTinh);
        setGioi_Tinh(kh.isGioi_Tinh());

        dayNgaySinh = new FormDate("Ngày sinh");
        setNgay_Sinh(kh.getNgay_Sinh());

        this.jPanelKH = jpKH;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(700, 500));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(4, 2, 5, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(tenNguoiDung);
        pnlMain.add(soDienThoai);
        pnlMain.add(Email);
        pnlMain.add(cbxVaiTro);
        pnlMain.add(cbxGioiTinh);
        pnlMain.add(cbxStatus);
        pnlMain.add(dayNgaySinh);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustome("Thêm người dùng", "success", 14);
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
                tenNguoiDung.setDisable();
                Email.setDisable();
                soDienThoai.setDisable();
                cbxVaiTro.setDisable();
                cbxStatus.setDisabled();
                cbxGioiTinh.setDisabled();
                cbxStatus.setDisabled();
                dayNgaySinh.setDisabled();
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
        return tenNguoiDung.getText();
    }

    public void setTen_Nguoi_Dung(String Ten_Nguoi_Dung) {
        tenNguoiDung.setText(Ten_Nguoi_Dung);
    }

    public String getEmail() {
        return Email.getText();
    }

    public void setEmail(String email) {
        Email.setText(email);
    }

    public Date getNgay_Sinh() {
        return dayNgaySinh.getDate();
    }

    public void setNgay_Sinh(Date Ngay_Sinh) {
        dayNgaySinh.setSelectedDate(Ngay_Sinh);
    }

    public String getSo_Dien_Thoai() {
        return soDienThoai.getText();
    }

    public void setSo_Dien_Thoai(String So_Dien_Thoai) {
        soDienThoai.setText(So_Dien_Thoai);
    }

    public String getVai_Tro() {
        return (String) cbxVaiTro.getSelectedItem();
    }

    public void setVai_Tro(String Vai_Tro) {
        cbxVaiTro.setSelectedItem(Vai_Tro);
    }

    public String isGioi_Tinh() {
        var selected = cbxGioiTinh.getSelectedValues();
        return selected.isEmpty() ? null : selected.get(0);
    }

    public void setGioi_Tinh(boolean gioiTinh) {
        String[] value = new String[]{gioiTinh ? "Nữ" : "Nam"};
        cbxGioiTinh.setSelectedValues(value);
    }

    public String getStatus() {
        var selected = cbxStatus.getSelectedValues();
        return selected.isEmpty() ? null : selected.get(0);

    }

    public void setStatus(int giatri) {
        String[] value = new String[]{giatri == 1 ? "Hoạt động" : "Dừng"};
        cbxStatus.setSelectedValues(value);
    }

    boolean Validation() {

        int currentID = (khDTO != null) ? khDTO.getID() : -1;

        if (Validation.isEmpty(tenNguoiDung.getText())) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(soDienThoai.getText()) || !Validation.isNumber(soDienThoai.getText()) && soDienThoai.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(Email.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (getNgay_Sinh() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String gioiTinh = isGioi_Tinh();
        if (gioiTinh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String trangThai = getStatus();
        if (trangThai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (KhachHangDAO.getInstance().isEmailUnique(Email.getText(), currentID)) {
            JOptionPane.showMessageDialog(this, "Email đã được sử dụng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (KhachHangDAO.getInstance().isPhoneNumberUnique(soDienThoai.getText(), currentID)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
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

            jPanelKH.khachhangBUS.add(new DTO.KhachHangDTO(id,
                    tenNguoiDung.getText(),
                    Email.getText(),
                    soDienThoai.getText(),
                    getVai_Tro(),
                    dayNgaySinh.getDate(),
                    isGioi_Tinh().equals("Nữ"),
                    getStatus().equals("Hoạt động") ? 1 : 0));
            jPanelKH.loadDataTable(jPanelKH.listkh);
            dispose();

        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jPanelKH.khachhangBUS.update(new KhachHangDTO(
                    khDTO.getID(),
                    tenNguoiDung.getText(),
                    Email.getText(),
                    soDienThoai.getText(),
                    getVai_Tro(),
                    dayNgaySinh.getDate(),
                    isGioi_Tinh().equals("Nữ"),
                    getStatus().equals("Hoạt động") ? 1 : 0));
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
