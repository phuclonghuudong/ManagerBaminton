package DAO;

import DTO.SanDTO;
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
public class SanDAO implements DAOinterface<SanDTO> {

    public static SanDAO getInstance() {
        return new SanDAO();
    }

    @Override
    public int insert(SanDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `san`(ID, Ten_San, Loai_San, Gia_San , Mo_Ta, Status) "
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, p.getID());
            pst.setString(2, p.getTen_San());
            pst.setInt(3, p.getLoai_San());
            pst.setDouble(4, p.getGia_San());
            pst.setString(5, p.getMo_Ta());
            pst.setInt(6, p.getStatus());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(SanDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `san` SET `Ten_San`=?, `Loai_San`=?, `Gia_San`=?, `Mo_Ta`=?,`Status`=? WHERE `ID`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p.getTen_San());
            pst.setInt(2, p.getLoai_San());
            pst.setDouble(3, p.getGia_San());
            pst.setString(4, p.getMo_Ta());
            pst.setInt(5, p.getStatus());
            pst.setInt(6, p.getID());

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
            String sql = "UPDATE `san` SET Status=0 WHERE `ID` = ?";
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
    public ArrayList<SanDTO> selectAll() {
        ArrayList<SanDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM san";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanDTO cat = new SanDTO();
                cat.setID(rs.getInt("ID"));
                cat.setTen_San(rs.getString("Ten_San"));
                cat.setLoai_San(rs.getInt("Loai_San"));
                cat.setGia_San(rs.getDouble("Gia_San"));
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
    public SanDTO selectById(String p) {
        SanDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM san WHERE ID=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenSan = rs.getString("Ten_San");
                int loaiSan = rs.getInt("Loai_San");
                double giaSan = rs.getDouble("Gia_San");
                String moTa = rs.getString("Mo_Ta");
                int status = rs.getInt("Status");

                result = new SanDTO(ID, tenSan, loaiSan, giaSan, moTa, status);
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
            String sql = "SELECT COUNT(*) AS total FROM san";
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

    public ArrayList<SanDTO> selectSanJoinLoaiSan() {
        ArrayList<SanDTO> result = new ArrayList<SanDTO>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT s.ID as ID, s.Ten_San as Ten_San, s.Gia_san AS Gia_San, s.Mo_Ta as Mo_Ta, s.Status as Status, ls.Ten_Loai as Ten_Loai_San, s.Loai_San as Loai_San"
                    + " FROM san AS s JOIN loai_san AS ls on ls.ID=s.Loai_San";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenSan = rs.getString("Ten_San");
                int loaiSan = rs.getInt("Loai_San");
                double giaSan = rs.getDouble("Gia_San");
                String moTa = rs.getString("Mo_Ta");
                int status = rs.getInt("Status");
                String tenLoai = rs.getString("Ten_Loai_San");

                SanDTO san = new SanDTO(ID, tenSan, loaiSan, giaSan, moTa, status);
                san.setTen_Loai_San(tenLoai);

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
            String sql = "SELECT * FROM san WHERE Ten_San=? AND ID<>?";
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
