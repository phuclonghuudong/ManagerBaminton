package BUS;

import DAO.LoaiSanPhamDAO;
import DTO.LoaiSanPhamDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author phucp
 */
public class LoaiSanPhamBUS {

    private final LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
    public ArrayList<LoaiSanPhamDTO> listLoaiSP = new ArrayList<>();

    public LoaiSanPhamBUS() {
        listLoaiSP = loaiSanPhamDAO.selectAll();
    }

    public ArrayList<LoaiSanPhamDTO> getAll() {
        return this.listLoaiSP;
    }

    public LoaiSanPhamDTO getByIndex(int index) {
        return this.listLoaiSP.get(index);
    }

    public int getIndexByMaDV(int ID) {
        int i = 0;
        int vitri = -1;
        while (i < this.listLoaiSP.size() && vitri == -1) {
            if (listLoaiSP.get(i).getID() == ID) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(LoaiSanPhamDTO kh) {
        boolean check = loaiSanPhamDAO.insert(kh) != 0;
        if (check) {
            this.listLoaiSP.add(kh);
        }
        return check;
    }

    public Boolean delete(LoaiSanPhamDTO kh) {
        boolean check = loaiSanPhamDAO.delete(Integer.toString(kh.getID())) != 0;
        if (check) {
            this.listLoaiSP.remove(getIndexByMaDV(kh.getID()));
        }
        return check;
    }

    public Boolean update(LoaiSanPhamDTO kh) {
        boolean check = loaiSanPhamDAO.update(kh) != 0;
        if (check) {
            this.listLoaiSP.set(getIndexByMaDV(kh.getID()), kh);
        }
        return check;
    }

    public String[] getArrTenLoai() {
        Set<String> vaiTroSet = new HashSet<>();
        for (LoaiSanPhamDTO kh : listLoaiSP) {
            vaiTroSet.add(kh.getTen_Loai());
        }
        return vaiTroSet.toArray(String[]::new);
    }

    public ArrayList<LoaiSanPhamDTO> search(String text, String type) {
        ArrayList<LoaiSanPhamDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (LoaiSanPhamDTO i : this.listLoaiSP) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text) || i.getTen_Loai().toLowerCase().contains(text) || i.getMo_Ta().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã loại" -> {
                for (LoaiSanPhamDTO i : this.listLoaiSP) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên loại" -> {
                for (LoaiSanPhamDTO i : this.listLoaiSP) {
                    if (i.getTen_Loai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mô tả" -> {
                for (LoaiSanPhamDTO i : this.listLoaiSP) {
                    if (i.getMo_Ta().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Trạng thái" -> {
                for (LoaiSanPhamDTO i : this.listLoaiSP) {
                    String trangThai = i.getStatus() == 1 ? "Hoạt động" : "Dừng";
                    if (trangThai.toLowerCase().contains(text.toLowerCase())) {
                        result.add(i);
                    }
                }
            }

        }

        return result;
    }
}
