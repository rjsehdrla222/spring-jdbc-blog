package repository;

import domain.Member;

import java.sql.*;
import java.util.ArrayList;

public class MemberDAOImpl extends OracleDAO implements MemberDAO {
   Connection conn = null;
   Statement stmt = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;

   public MemberDAOImpl() {
      conn = getConnection();
   }
   @Override
   public int create(Member m) { // 회원 가입, insert문
      int ret = 0;
      conn = this.getConnection();
      String userName = m.getName();
      String userEmail = m.getEmail();
      String userAddress = m.getAddress();
      String userPhone = m.getPhone();
      String userPw = m.getPw();
      System.out.println("=============");
      System.out.println(userName);
      System.out.println(userEmail);
      System.out.println(userAddress);
      System.out.println(userPhone);
      System.out.println(userPw);
      System.out.println("=============");

      //String sql = "insert into member values("+userEmail+",'"+userPw+"','"+userName+"','"+userPhone+"','"+userAddress+"')";

      String sql = "insert into member(email, pw, name, phone, address) values(?, ?, ?, ?, ?)";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, m.getEmail());
         pstmt.setString(2, m.getPw());
         pstmt.setString(3, m.getName());
         pstmt.setString(4, m.getPhone());
         pstmt.setString(5, m.getAddress());
      } catch (SQLException e) {
         e.printStackTrace();
      }

      try {
         stmt = conn.createStatement();
         ret = pstmt.executeUpdate();
      } catch (SQLException e) {
// TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         this.closeResources(conn, stmt, pstmt, rs);
      }
      return ret;
   }

   @Override
   public Member read(Member m) { // 회원 로그인, 상세 정보, select문
      rs = null;
      Member retMember = null;
      String sql = "select * from member where email = '" + m.getEmail() + "'";
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if(rs.next()) {
            retMember = setRsMember(rs);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         this.closeResources(conn, stmt, pstmt, rs);
      }
      return retMember;
   }

   private Member setRsMember(ResultSet rs) throws SQLException { // rs : record 집합, re.getString(1) : 현재 레코드의 첫번째 필드 값
      Member retMember = new Member();
      retMember.setId(rs.getLong(1));
      retMember.setEmail(rs.getString(2));
      retMember.setPw(rs.getString(3));
      retMember.setName(rs.getString(4));
      retMember.setPhone(rs.getString(5));
      retMember.setAddress(rs.getString(6));
      return retMember;
   }

   @Override
   public ArrayList<Member> readList(Member m) { // 회원 목록 정보, select문, 1개 이상의 레코드들을 반환
      rs = null;
      ArrayList<Member> memberList = null;
      Member retMember = null;
      conn = this.getConnection();
      String sql = "select * from member";
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         memberList = new ArrayList<Member>();
         while(rs.next()) {
            retMember = setRsMember(rs);
            memberList.add(retMember);
         }
      } catch (SQLException e) { // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         this.closeResources(conn, stmt, pstmt, rs);
      }
      return memberList;
   }

   public ArrayList<Member> getMemberList()
   {
      ArrayList<Member> memberLit = new ArrayList<Member>();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Member member = null;

      try {
         StringBuffer query = new StringBuffer();
         query.append("SELECT * FROM member");

         conn = this.getConnection();
         pstmt = conn.prepareStatement(query.toString());
         rs = pstmt.executeQuery();

         while (rs.next())
         {
            member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setName(rs.getString("name"));
            member.setPhone(rs.getString("phone"));
            member.setAddress(rs.getString("address"));
            memberLit.add(member);
         }

         return memberLit;

      } catch (Exception sqle) {
         throw new RuntimeException(sqle.getMessage());
      } finally {
         // Connection, PreparedStatement를 닫는다.
         try{
            if ( pstmt != null ){ pstmt.close(); pstmt=null; }
            if ( conn != null ){ conn.close(); conn=null;    }
         }catch(Exception e){
            throw new RuntimeException(e.getMessage());
         }
      }
   }

   @Override
   public int update(Member m) { // 회원 정보 수정, update문
      int ret = 0;
      conn = this.getConnection();
      String sql = "update member set name=?, phone=?, address=? where email=? and pw=?";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, m.getName());
         pstmt.setString(2, m.getPhone());
         pstmt.setString(3, m.getAddress());
         pstmt.setString(4, m.getEmail());
         pstmt.setString(5, m.getPw());
         ret = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         this.closeResources(conn, stmt, pstmt, rs);
      }
      return ret;
   }
   @Override
   public int delete(Member m) { // 회원 탈퇴(정보 삭제), delete문
      int ret = 0;
      conn = this.getConnection();
      String sql = "delete from member where email=? and pw=?";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, m.getEmail());
         pstmt.setString(2, m.getPw());
         ret = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         this.closeResources(conn, stmt, pstmt, rs);
      }
      return ret;
   }
}