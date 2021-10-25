package domain;

import java.sql.Timestamp;

public class Blog {
    private long id; // 블로그 아이디 (식별자)
    private String title; // 블로그 제목
    private String content; // 블로그 내용
    private String filepath; // 블로그 첨부 이미지
    private String blogger; // 블로그 작성자
    private Timestamp regDateTime; // 블로그 작성일 : 날짜와 시간

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getBlogger() {
        return blogger;
    }
    public void setBlogger(String blogger) {
        this.blogger = blogger;
    }
    public Timestamp getRegDateTime() {
        return regDateTime;
    }
    public void setRegDateTime(Timestamp regDateTime) {
        this.regDateTime = regDateTime;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blogger == null) ? 0 : blogger.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Blog other = (Blog) obj;
        if (blogger == null) {
            if (other.blogger != null)
                return false;
        } else if (!blogger.equals(other.blogger))
            return false;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Blog [ title=" + title + ", content=" + content + ", filepath=" + filepath + ", blogger="
                + blogger + ", regDateTime=" + regDateTime + "]";
    }
}
