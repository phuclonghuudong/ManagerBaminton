package BUS;

import DAO.SanDAO;
import DTO.SanDTO;
import java.util.ArrayList;

/**
 *
 * @author phucp
 */
public class SanBUS {

    private final SanDAO sanDAO = new SanDAO();
    public ArrayList<SanDTO> listSan = new ArrayList<>();

    public SanBUS() {
        listSan = sanDAO.selectAll();
    }

    public ArrayList<SanDTO> getAll() {
        return this.listSan;
    }

    public ArrayList<SanDTO> getAllLoaiSan() {
        return sanDAO.selectSanJoinLoaiSan();
    }

    public SanDTO getByIndex(int index) {
        return this.listSan.get(index);
    }

    public int getIndexByMaDV(int ID) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSan.size() && vitri == -1) {
            if (listSan.get(i).getID() == ID) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(SanDTO kh) {
        boolean check = sanDAO.insert(kh) != 0;
        if (check) {
            this.listSan.add(kh);
        }
        return check;
    }

    public Boolean delete(SanDTO kh) {
        boolean check = sanDAO.delete(Integer.toString(kh.getID())) != 0;
        if (check) {
            this.listSan.remove(getIndexByMaDV(kh.getID()));
        }
        return check;
    }

    public Boolean update(SanDTO kh) {
        boolean check = sanDAO.update(kh) != 0;
        if (check) {
            this.listSan.set(getIndexByMaDV(kh.getID()), kh);
        }
        return check;
    }

    public ArrayList<SanDTO> search(String text, String type) {
        ArrayList<SanDTO> result = new ArrayList<>();
        ArrayList<SanDTO> data = sanDAO.selectSanJoinLoaiSan();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (SanDTO i : data) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text) || i.getTen_San().toLowerCase().contains(text) || i.getMo_Ta().toLowerCase().contains(text) || i.getTen_Loai_San().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã sân" -> {
                for (SanDTO i : data) {
                    if (Integer.toString(i.getID()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên sân" -> {
                for (SanDTO i : data) {
                    if (i.getTen_San().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên loại sân" -> {
                for (SanDTO i : data) {
                    if (i.getTen_Loai_San().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Giá sân" -> {
                for (SanDTO i : data) {
                    if (Double.toString(i.getGia_San()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mô tả" -> {
                for (SanDTO i : data) {
                    if (i.getMo_Ta().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Trạng thái" -> {
                for (SanDTO i : data) {
                    String trangThai = i.getStatus() == 1 ? "Hoạt động" : i.getStatus() == 2 ? "Đang bảo trì" : "Dừng";
                    if (trangThai.toLowerCase().contains(text.toLowerCase())) {
                        result.add(i);
                    }
                }
            }

        }

        return result;
    }
}
