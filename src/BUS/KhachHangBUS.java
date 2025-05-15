package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import helper.ValidationUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author phucp
 */
public class KhachHangBUS {

    private final KhachHangDAO khDAO = new KhachHangDAO();
    public ArrayList<KhachHangDTO> listKhachHang = new ArrayList<>();

    public KhachHangBUS() {
        listKhachHang = khDAO.selectAll();
    }

    public ArrayList<KhachHangDTO> getAll() {
        return this.listKhachHang;
    }

    public KhachHangDTO getByIndex(int index) {
        return this.listKhachHang.get(index);
    }

    public String registerUser(KhachHangDTO user) throws ParseException {
        if (user.getTen_Nguoi_Dung().isEmpty() || user.getEmail().isEmpty() || user.getMat_Khau().isEmpty() || user.getSo_Dien_Thoai().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin bắt buộc.";
        }

        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            return "Email không đúng định dạng.";
        }
        if (!ValidationUtil.isValidPhoneNumber(user.getSo_Dien_Thoai())) {
            return "Số điện thoại không đúng định dạng.";
        }

        if (KhachHangDAO.getInstance().selectByEmail(user.getEmail())) {
            return "Email đã tồn tại.";
        }
        if (KhachHangDAO.getInstance().selectByPhone(user.getSo_Dien_Thoai())) {
            return "Số điện thoại đã tồn tại.";
        }

        String hashedPassword = ValidationUtil.hashPassword(user.getMat_Khau());
        user.setMat_Khau(hashedPassword);

        user.setVai_Tro("USER");
        user.setStatus(1); // Đang hoạt động

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date defaultDate = sdf.parse("01/01/2000");
        user.setNgay_Sinh(defaultDate);

        int inserted = KhachHangDAO.getInstance().insert(user);
        return inserted > 0 ? "Đăng ký thành công!" : "Đăng ký thất bại!";
    }

    public String loginUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin bắt buộc.";
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return "Email không hợp lệ - không đúng định dạng.";
        }

        if (!KhachHangDAO.getInstance().selectByEmail(email)) {
            return "Email không tồn tại.";
        }

        String hashedPassword = ValidationUtil.hashPassword(password);

        KhachHangDTO user = KhachHangDAO.getInstance().getUserByEmailAndPassword(email, hashedPassword);
        if (user == null) {
            return "Sai email hoặc mật khẩu.";
        }

        if (user.getStatus() == 0) {
            return "Tài khoản của bạn đã bị khóa.";
        }
        return "Đăng nhập thành công! Xin chào " + user.getTen_Nguoi_Dung();
    }

    public int getIndexByMaDV(int makhachhang) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKhachHang.size() && vitri == -1) {
            if (listKhachHang.get(i).getID() == makhachhang) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(KhachHangDTO kh) {
        boolean check = khDAO.insert(kh) != 0;
        if (check) {
            this.listKhachHang.add(kh);
        }
        return check;
    }

    public Boolean delete(KhachHangDTO kh) {
        boolean check = khDAO.delete(Integer.toString(kh.getID())) != 0;
        if (check) {
            this.listKhachHang.remove(getIndexByMaDV(kh.getID()));
        }
        return check;
    }

    public Boolean update(KhachHangDTO kh) {
        boolean check = khDAO.update(kh) != 0;
        if (check) {
            this.listKhachHang.set(getIndexByMaDV(kh.getID()), kh);
        }
        return check;
    }

    public String getTenKhachHang(int makh) {
        String name = "";
        for (KhachHangDTO khachHangDTO : listKhachHang) {
            if (khachHangDTO.getID() == makh) {
                name = khachHangDTO.getTen_Nguoi_Dung();
            }
        }
        return name;
    }

    public String[] getArrTenKhachHang() {
        int size = listKhachHang.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listKhachHang.get(i).getTen_Nguoi_Dung();
        }
        return result;
    }

    public String[] getArrVaiTro() {
        Set<String> vaiTroSet = new HashSet<>();
        for (KhachHangDTO kh : listKhachHang) {
            vaiTroSet.add(kh.getVai_Tro());
        }
        return vaiTroSet.toArray(new String[0]);
//        int size = listKhachHang.size();
//        String[] result = new String[size];
//        for (int i = 0; i < size; i++) {
//            result[i] = listKhachHang.get(i).getVai_Tro();
//        }
//        return result;
    }

    public KhachHangDTO selectKh(int makh) {
        return khDAO.selectById(makh + "");
    }

    public ArrayList<KhachHangDTO> search(String text, String type) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text) || i.getTen_Nguoi_Dung().toLowerCase().contains(text) || i.getEmail().toLowerCase().contains(text) || i.getSo_Dien_Thoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã khách hàng" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên khách hàng" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getTen_Nguoi_Dung().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Email" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getEmail().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getSo_Dien_Thoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Vai trò" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getVai_Tro().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Trạng thái" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    String trangThai = i.getStatus() == 1 ? "Hoạt động" : "Dừng";
                    if (trangThai.toLowerCase().contains(text.toLowerCase())) {
                        result.add(i);
                    }
                }
            }
            case "Giới tính" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    String gioiTinh = i.isGioi_Tinh() ? "Nữ" : "Nam";
                    if (gioiTinh.toLowerCase().contains(text.toLowerCase())) {
                        result.add(i);
                    }
                }
            }

        }

        return result;
    }
}
