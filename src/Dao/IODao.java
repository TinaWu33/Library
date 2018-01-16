package Dao;

import Entity.Log;
import Util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IODao extends DBConnect{
    public void borrow(String bookid, String readerid, String time, int borrowday){
        try {
            int i = 0;
            Connection conn = super.getConnection();
            PreparedStatement pst = null;
            String sql = "INSERT INTO iolog (bookid, readerid, service, borrowtime, borrowday, complete)VALUES(?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, bookid);
            pst.setString(2, readerid);
            pst.setInt(3, -1);
            pst.setString(4, time);
            pst.setInt(5, borrowday);
            pst.setInt(6, 0);
            i = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Log> getLogList(){
        try {
            ArrayList<Log> Loglist = new ArrayList<>();
            BookDao bdao = new BookDao();
            Connection conn = super.getConnection();
            String sql = "SELECT * FROM iolog";
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String service = null;
            String bookname = null;
            String complete = null;
            while (rs.next()) {
                Log log = new Log();
                bookname = bdao.QueryBookById(rs.getString("bookid")).getName();
                log.setBookid(rs.getString("bookid") + "  (" + bookname + ")");
                log.setReaderid(rs.getString("readerid"));
                service = (rs.getInt("service") == -1) ? "借出" : "归还";
                log.setService(service);
                log.setBorrowtime(rs.getString("borrowtime"));
                log.setBorrowday(rs.getInt("borrowday"));
                complete = (rs.getInt("complete") == 0) ? "未归还" : "已归还";
                log.setComplete(complete);
                Loglist.add(log);
            }
            return Loglist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
