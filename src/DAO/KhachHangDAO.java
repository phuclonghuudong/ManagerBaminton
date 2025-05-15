package DAO;

import DTO.KhachHangDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import config.JDBCUtil;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author phucp
 */
public class KhachHangDAO implements DAOinterface<KhachHangDTO> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int insert(KhachHangDTO p) {

        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO khach_hang (ID, Ten_Nguoi_Dung, Email, Ngay_Sinh, So_Dien_Thoai, Mat_Khau, Vai_Tro, Gioi_Tinh, Status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, p.getID());
            pst.setString(2, p.getTen_Nguoi_Dung());
            pst.setString(3, p.getEmail());
            pst.setDate(4, new Date(p.getNgay_Sinh().getTime()));
            pst.setString(5, p.getSo_Dien_Thoai());
            pst.setString(6, p.getMat_Khau());
            pst.setString(7, p.getVai_Tro());
            pst.setBoolean(8, p.isGioi_Tinh());
            pst.setInt(9, p.getStatus());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(KhachHangDTO p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `khach_hang` SET `Ten_Nguoi_Dung`=?,`Email`=?,`Ngay_Sinh`=?,`So_Dien_Thoai`=?,`Vai_Tro`=?,`Gioi_Tinh`=?,`Status`=? WHERE `ID`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p.getTen_Nguoi_Dung());
            pst.setString(2, p.getEmail());
            pst.setDate(3, new Date(p.getNgay_Sinh().getTime()));
            pst.setString(4, p.getSo_Dien_Thoai());
            pst.setString(5, p.getVai_Tro());
            pst.setBoolean(6, p.isGioi_Tinh());
            pst.setInt(7, p.getStatus());
            pst.setInt(8, p.getID());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String p) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE  `khach_hang` SET Status=0 WHERE `ID` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setID(rs.getInt("ID"));
                kh.setTen_Nguoi_Dung(rs.getString("Ten_Nguoi_Dung"));
                kh.setEmail(rs.getString("Email"));
                kh.setSo_Dien_Thoai(rs.getString("So_Dien_Thoai"));
                kh.setNgay_Sinh(rs.getDate("Ngay_Sinh"));
                kh.setGioi_Tinh(rs.getBoolean("Gioi_Tinh"));
                kh.setMat_Khau(rs.getString("Mat_Khau"));
                kh.setVai_Tro(rs.getString("Vai_Tro"));
                kh.setNgay_Tao(rs.getDate("Ngay_Tao"));
                kh.setNgay_Cap_Nhat(rs.getDate("Ngay_Cap_Nhat"));
                kh.setStatus(rs.getInt("Status"));
                list.add(kh);
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
        }

        return list;
    }

    public KhachHangDTO selectById(String p) {
        KhachHangDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE ID=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenNguoiDung = rs.getString("Ten_Nguoi_Dung");
                String email = rs.getString("Email");
                String soDienThoai = rs.getString("So_Dien_Thoai");
                String vaiTro = rs.getString("Vai_Tro");
                Date ngaySinh = rs.getDate("Ngay_Sinh");
                boolean gioiTinh = rs.getBoolean("Gioi_Tinh");
                int status = rs.getInt("Status");
                result = new KhachHangDTO(ID, tenNguoiDung, email, soDienThoai, vaiTro, ngaySinh, gioiTinh, status);
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
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quan_lY_san_cau_long' AND TABLE_NAME = 'khach_hang'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean selectByEmail(String p) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE Email=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();
            return rs.next();

        } catch (SQLException e) {
        }
        return false;
    }

    public boolean selectByPhone(String p) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE So_Dien_Thoai=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            ResultSet rs = (ResultSet) pst.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhachHangDTO getUserByEmailAndPassword(String email, String hashedPassword) {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE Email = ? AND Mat_Khau = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                KhachHangDTO user = new KhachHangDTO();
                user.setID(rs.getInt("ID"));
                user.setTen_Nguoi_Dung(rs.getString("Ten_Nguoi_Dung"));
                user.setEmail(rs.getString("Email"));
                user.setNgay_Sinh(rs.getDate("Ngay_Sinh"));
                user.setSo_Dien_Thoai(rs.getString("So_Dien_Thoai"));
                user.setMat_Khau(rs.getString("Mat_Khau"));
                user.setVai_Tro(rs.getString("Vai_Tro"));
                user.setGioi_Tinh(rs.getBoolean("Gioi_Tinh"));
                user.setStatus(rs.getInt("Status"));
                return user;
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailUnique(String p, int ID) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE Email=? AND ID<>?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            pst.setInt(2, ID);
            ResultSet rs = (ResultSet) pst.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneNumberUnique(String p, int ID) {
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khach_hang WHERE So_Dien_Thoai=? AND ID<> ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, p);
            pst.setInt(2, ID);
            ResultSet rs = (ResultSet) pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
