package DTO;

import java.util.Date;

public class SanCauLongDTO {

    private int ID;
    private String Ten_San;
    private int Loai_San;
    private String Mo_Ta;
    private Date Ngay_Tao;
    private Date Ngay_Cap_Nhat;
    private int Status;

    public SanCauLongDTO(int ID, String Ten_San, int Loai_San, String Mo_Ta, Date Ngay_Tao, Date Ngay_Cap_Nhat, int Status) {
        this.ID = ID;
        this.Ten_San = Ten_San;
        this.Loai_San = Loai_San;
        this.Mo_Ta = Mo_Ta;
        this.Ngay_Tao = Ngay_Tao;
        this.Ngay_Cap_Nhat = Ngay_Cap_Nhat;
        this.Status = Status;
    }

    public SanCauLongDTO() {
    }

    @Override
    public String toString() {
        return "SanCauLongDTO{" + "ID=" + ID + ", Ten_San=" + Ten_San + ", Loai_San=" + Loai_San + ", Mo_Ta=" + Mo_Ta + ", Ngay_Tao=" + Ngay_Tao + ", Ngay_Cap_Nhat=" + Ngay_Cap_Nhat + ", Status=" + Status + '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTen_San() {
        return Ten_San;
    }

    public void setTen_San(String Ten_San) {
        this.Ten_San = Ten_San;
    }

    public int getLoai_San() {
        return Loai_San;
    }

    public void setLoai_San(int Loai_San) {
        this.Loai_San = Loai_San;
    }

    public String getMo_Ta() {
        return Mo_Ta;
    }

    public void setMo_Ta(String Mo_Ta) {
        this.Mo_Ta = Mo_Ta;
    }

    public Date getNgay_Tao() {
        return Ngay_Tao;
    }

    public void setNgay_Tao(Date Ngay_Tao) {
        this.Ngay_Tao = Ngay_Tao;
    }

    public Date getNgay_Cap_Nhat() {
        return Ngay_Cap_Nhat;
    }

    public void setNgay_Cap_Nhat(Date Ngay_Cap_Nhat) {
        this.Ngay_Cap_Nhat = Ngay_Cap_Nhat;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

}
