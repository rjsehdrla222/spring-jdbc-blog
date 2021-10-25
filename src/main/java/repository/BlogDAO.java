package repository;

import domain.Blog;

import java.util.List;

public interface BlogDAO {
    int create(Blog blog);
    Blog read(Blog blog);
    List<Blog> readList();
    int update(Blog blog);
    int delete(Blog blog);
}
