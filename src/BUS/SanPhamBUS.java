package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import java.util.ArrayList;

/**
 *
 * @author phucp
 */
public class SanPhamBUS {

    private final SanPhamDAO spDAO = new SanPhamDAO();
    public ArrayList<SanPhamDTO> listSP = new ArrayList<>();

    public SanPhamBUS() {
        listSP = spDAO.selectAll();
    }

    public ArrayList<SanPhamDTO> getAll() {
        return this.listSP;
    }

    public ArrayList<SanPhamDTO> getAllLoaiSP() {
        return spDAO.selectSanPhamJoinLoaiSP();
    }

    public SanPhamDTO getByIndex(int index) {
        return this.listSP.get(index);
    }

    public int getIndexByMaDV(int ID) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSP.size() && vitri == -1) {
            if (listSP.get(i).getID() == ID) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(SanPhamDTO kh) {
        boolean check = spDAO.insert(kh) != 0;
        if (check) {
            this.listSP.add(kh);
        }
        return check;
    }

    public Boolean delete(SanPhamDTO kh) {
        boolean check = spDAO.delete(Integer.toString(kh.getID())) != 0;
        if (check) {
            this.listSP.remove(getIndexByMaDV(kh.getID()));
        }
        return check;
    }

    public Boolean update(SanPhamDTO kh) {
        boolean check = spDAO.update(kh) != 0;
        if (check) {
            this.listSP.set(getIndexByMaDV(kh.getID()), kh);
        }
        return check;
    }

    public ArrayList<SanPhamDTO> search(String text, String type) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> data = spDAO.selectSanPhamJoinLoaiSP();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (SanPhamDTO i : data) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text) || i.getTen_San_Pham().toLowerCase().contains(text) || i.getMo_Ta().toLowerCase().contains(text) || i.getTen_Loai_SP().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã SP" -> {
                for (SanPhamDTO i : data) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên sản phẩm" -> {
                for (SanPhamDTO i : data) {
                    if (i.getTen_San_Pham().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên loại " -> {
                for (SanPhamDTO i : data) {
                    if (i.getTen_Loai_SP().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Giá SP" -> {
                for (SanPhamDTO i : data) {
                    if (Double.toString(i.getGia_Ban()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mô tả" -> {
                for (SanPhamDTO i : data) {
                    if (i.getMo_Ta().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Trạng thái" -> {
                for (SanPhamDTO i : data) {
                    String trangThai = i.getStatus() == 1 ? "Hoạt động" : i.getStatus() == 2 ? "Hết hàng" : "Dừng";
                    if (trangThai.toLowerCase().contains(text.toLowerCase())) {
                        result.add(i);
                    }
                }
            }

        }

        return result;
    }
}
