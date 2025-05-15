package DAO;

import DTO.LoaiSanDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phucp
 */
public class LoaiSanDAO implements DAOinterface<LoaiSanDTO> {

    public static LoaiSanDAO getInstance() {
        return new LoaiSanDAO();
    }

    @Override
    public int insert(LoaiSanDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `loai_san`(ID, Ten_Loai, Mo_Ta, Status) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, p.getID());
            pst.setString(2, p.getTen_Loai());
            pst.setString(3, p.getMo_Ta());
            pst.setInt(4, p.getStatus());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(LoaiSanDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `loai_san` SET `Ten_Loai`=?,`Mo_Ta`=?,`Status`=? WHERE `ID`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p.getTen_Loai());
            pst.setString(2, p.getMo_Ta());
            pst.setInt(3, p.getStatus());
            pst.setInt(4, p.getID());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `loai_san` SET Status=0 WHERE `ID` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<LoaiSanDTO> selectAll() {
        ArrayList<LoaiSanDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM loai_san";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LoaiSanDTO cat = new LoaiSanDTO();
                cat.setID(rs.getInt("ID"));
                cat.setTen_Loai(rs.getString("Ten_Loai"));
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
    public LoaiSanDTO selectById(String p) {
        LoaiSanDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM loai_san WHERE ID=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenLoai = rs.getString("Ten_Loai");
                String moTa = rs.getString("Mo_Ta");
                int status = rs.getInt("Status");
                result = new LoaiSanDTO(ID, tenLoai, moTa, status);
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
            String sql = "SELECT COUNT(*) AS total FROM loai_san";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result + 1;
    }

    public boolean isNameUnique(String p, int ID) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM loai_san WHERE Ten_Loai=? AND ID<>?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            pst.setInt(2, ID);
            ResultSet rs = (ResultSet) pst.executeQuery();
            return rs.next();

        } catch (SQLException e) {
        }
        return false;
    }

    public ArrayList<LoaiSanDTO> selectAllStatus() {
        ArrayList<LoaiSanDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM loai_san WHERE status=1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LoaiSanDTO cat = new LoaiSanDTO();
                cat.setID(rs.getInt("ID"));
                cat.setTen_Loai(rs.getString("Ten_Loai"));
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

}
