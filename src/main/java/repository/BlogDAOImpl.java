package repository;

import domain.Blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAOImpl extends DAOImplOracle implements BlogDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public BlogDAOImpl() {
        this.conn = getConnection();
    }

    @Override
    public int create(Blog blog) {
        int rows = 0;
        //INSERT INTO blog (title, content, filepath, blogger, created_date) values ('제목-1','블로그 내용-1','01.png','root@induk.ac.kr', '09.16(2021)');

        String sql = "insert into blog(title, content, filepath, blogger) values(?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, blog.getTitle());
            pstmt.setString(2, blog.getContent());
            pstmt.setString(3, blog.getFilepath());
            pstmt.setString(4, blog.getBlogger());
            //pstmt.setTimestamp(5, blog.getRegDateTime());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public Blog read(Blog blog) {
        Blog retBlog = null;

        String sql = "select * from blog where id='" + blog.getId() + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) { // 다음 record값을 접근
                // rs : record 집합, rs.getString(1) : 현재 레코드의 첫번재 필드 값
                retBlog = new Blog();
                retBlog.setId(rs.getInt(1)); // rs.getString("<field_name>"); 필드이름로도 가능
                retBlog.setTitle(rs.getString(2)); // 생성 시 필드 순서
                retBlog.setContent(rs.getString(3));
                retBlog.setFilepath(rs.getString(4));
                retBlog.setBlogger(rs.getString(5));
                //retBlog.setRegDateTime(rs.getTimestamp(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResources(conn, stmt, pstmt, rs);
        }
        return retBlog;
    }

    @Override
    public List<Blog> readList() {
        List<Blog> blogList = null;
        Blog retBlog = null;
        int i = 0;
        String sql = "select * from blog order by id desc";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            blogList = new ArrayList<Blog>();
            while (rs.next()) { // 다음 record값을 접근
                // rs : record 집합, rs.getString(1) : 현재 레코드의 첫번재 필드 값
                retBlog = new Blog();
                retBlog.setId(rs.getInt(1)); // rs.getString("<field_name>"); 필드이름로도 가능
                retBlog.setTitle(rs.getString(2)); // 생성 시 필드 순서
                retBlog.setContent(rs.getString(3));
                retBlog.setFilepath(rs.getString(4));
                retBlog.setBlogger(rs.getString(5));
                //retBlog.setRegDateTime(rs.getTimestamp(6));
                blogList.add(retBlog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResources(conn, stmt, pstmt, rs);
        }
        return blogList;
    }

    public int getNext() {
        String sql = "SELECT * FROM blog ORDER BY id DESC";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;//첫 번째 게시물인 경우
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; //데이터베이스 오류
    }

    public ArrayList<Blog> getList(int pageNumber) {
        String sql = "select * from blog where id < ? order by id desc";
        ArrayList<Blog> list = new ArrayList<Blog>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, getNext() - (pageNumber - 1) * 3);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getLong(1));
                blog.setTitle(rs.getString(2));
                blog.setContent(rs.getString(3));
                blog.setFilepath(rs.getString(4));
                blog.setBlogger(rs.getString(5));
                //blog.setRegDateTime(rs.getTimestamp(6));
                list.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean nextPage(int pageNumber) {
        String sql = "select * from ( select * from blog where id < ? order by id desc ) where rownum <= 3 ";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, getNext() - (pageNumber - 1) * 3);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int update(Blog blog) {
        int rows = 0;
        String sql = "update blog set title=?, content=?, filepath=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, blog.getTitle());
            pstmt.setString(2, blog.getContent());
            pstmt.setString(3, blog.getFilepath());
            //pstmt.setTimestamp(4, blog.getRegDateTime());
            pstmt.setLong(4, blog.getId());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public int delete(Blog blog) {
        int rows = 0;
        String sql = "delete from blog where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, blog.getId());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

}
