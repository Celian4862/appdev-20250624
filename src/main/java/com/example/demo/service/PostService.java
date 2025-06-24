package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@Service
public class PostService {

    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findAll() {
        return "All posts";
    }

    public String findById(Long id) {
        return String.format("Photo: %d.jpeg", id);
    }

    public Map<String, Object> createPost(String postData) {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS posts (id BIGINT AUTO_INCREMENT PRIMARY KEY, text VARCHAR(255), likeCount INT)");
        jdbcTemplate.update("INSERT INTO posts (text, likeCount) VALUES (?, ?)", postData, 0);
        Map<String, Object> post = jdbcTemplate.queryForMap(
            "SELECT id, text, likeCount FROM posts ORDER BY id DESC LIMIT 1"
        );
        return post;
    }

    public Map<String, Object> createComment(Long id, String commentData) {
        // Check if the post exists using the recommended RowMapper approach
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM posts WHERE id = ?",
            (rs, rowNum) -> rs.getInt(1),
            id
        );
        if (count == null || count == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Post not found"
            );
        }
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS comments (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "postId BIGINT, " +
            "text VARCHAR(255), " +
            "CONSTRAINT fk_post FOREIGN KEY (postId) REFERENCES posts(id) ON DELETE CASCADE)"
        );
        jdbcTemplate.update("INSERT INTO comments (postId, text) VALUES (?, ?)", id, commentData);
        Map<String, Object> comment = jdbcTemplate.queryForMap(
            "SELECT id, postId, text FROM comments WHERE postId = ? ORDER BY id DESC LIMIT 1", id
        );
        return comment;
    }

    public Map<String, Object> likePost(Long id) {
        // Check if the post exists using the recommended RowMapper approach
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM posts WHERE id = ?",
            (rs, rowNum) -> rs.getInt(1),
            id
        );
        if (count == null || count == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Post not found"
            );
        }
        jdbcTemplate.update("UPDATE posts SET likeCount = likeCount + 1 WHERE id = ?", id);
        Map<String, Object> post = jdbcTemplate.queryForMap(
            "SELECT id, text, likeCount FROM posts WHERE id = ?", id
        );
        return post;
    }
}
