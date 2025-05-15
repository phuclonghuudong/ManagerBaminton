package DAO;

import DTO.SanPhamDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author phucp
 */
public class SanPhamDAO implements DAOinterface<SanPhamDTO> {

    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }

    @Override
    public int insert(SanPhamDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `san_pham`(ID, Ten_San_Pham, Loai_San_Pham, Gia_Ban, So_Luong, Mo_Ta, Status) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, p.getID());
            pst.setString(2, p.getTen_San_Pham());
            pst.setInt(3, p.getLoai_San_Pham());
            pst.setDouble(4, p.getGia_Ban());
            pst.setInt(5, p.getSo_Luong());
            pst.setString(6, p.getMo_Ta());
            pst.setInt(7, p.getStatus());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(SanPhamDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `san_pham` SET `Ten_San_Pham`=?, `Loai_San_pham`=?, `Gia_Ban`=?, `So_Luong`=?, `Mo_Ta`=?,`Status`=? WHERE `ID`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p.getTen_San_Pham());
            pst.setInt(2, p.getLoai_San_Pham());
            pst.setDouble(3, p.getGia_Ban());
            pst.setInt(4, p.getSo_Luong());
            pst.setString(5, p.getMo_Ta());
            pst.setInt(6, p.getStatus());
            pst.setInt(7, p.getID());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `san_pham` SET Status=0 WHERE `ID` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM san_pham";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamDTO cat = new SanPhamDTO();
                cat.setID(rs.getInt("ID"));
                cat.setTen_San_Pham(rs.getString("Ten_San_Pham"));
                cat.setLoai_San_Pham(rs.getInt("Loai_San_Pham"));
                cat.setGia_Ban(rs.getDouble("Gia_Ban"));
                cat.setSo_Luong(rs.getInt("So_Luong"));
                cat.setMo_Ta(rs.getString("Mo_Ta"));
                cat.setNgay_Tao(rs.getDate("Ngay_Tao"));
                cat.setNgay_Cap_Nhat(rs.getDate("Ngay_Cap_Nhat"));
                cat.setStatus(rs.getInt("Status"));
                list.add(cat);
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
        }
        return list;
    }

    @Override
    public SanPhamDTO selectById(String p) {
        SanPhamDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM san_pham WHERE ID=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenSP = rs.getString("Ten_San_Pham");
                int loaiSP = rs.getInt("Loai_San_Pham");
                double giaBan = rs.getDouble("Gia_San");
                int soLuong = rs.getInt("Loai_San_Pham");
                String moTa = rs.getString("Mo_Ta");
                int status = rs.getInt("Status");

                result = new SanPhamDTO(ID, tenSP, loaiSP, giaBan, soLuong, moTa, status);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM san_pham";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result + 1;
    }

    public ArrayList<SanPhamDTO> selectSanPhamJoinLoaiSP() {
        ArrayList<SanPhamDTO> result = new ArrayList<SanPhamDTO>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT s.ID as ID, s.Ten_San_Pham as Ten_San_Pham, s.Gia_Ban AS Gia_Ban, s.Mo_Ta as Mo_Ta, s.Status as Status, s.So_Luong as So_Luong, ls.Ten_Loai as Ten_Loai, s.Loai_San_pham as Loai_San_Pham"
                    + " FROM san_pham AS s JOIN loai_san_pham AS ls on ls.ID=s.Loai_San_Pham";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenSan = rs.getString("Ten_San_Pham");
                int loaiSan = rs.getInt("Loai_San_Pham");
                double giaSan = rs.getDouble("Gia_Ban");
                int soLuong = rs.getInt("So_Luong");
                String moTa = rs.getString("Mo_Ta");
                int status = rs.getInt("Status");
                String tenLoai = rs.getString("Ten_Loai");

                SanPhamDTO san = new SanPhamDTO(ID, tenSan, loaiSan, giaSan, soLuong, moTa, status);
                san.setTen_Loai_SP(tenLoai);

                result.add(san);
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    public boolean isNameUnique(String p, int ID) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM san_pham WHERE Ten_San_Pham=? AND ID<>?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            pst.setInt(2, ID);
            ResultSet rs = (ResultSet) pst.executeQuery();
            return rs.next();

        } catch (SQLException e) {
        }
        return false;
    }
}
