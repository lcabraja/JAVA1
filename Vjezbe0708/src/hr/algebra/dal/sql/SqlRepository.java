/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Article; 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author dnlbe
 */
public class SqlRepository implements Repository {

    private static final String ID_ARTICLE = "IDArticle";
    private static final String TITLE = "Title";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISHED_DATE = "PublishedDate";

    private static final String CREATE_ARTICLE = "{ CALL createArticle (?,?,?,?,?,?) }";
    private static final String UPDATE_ARTICLE = "{ CALL updateArticle (?,?,?,?,?,?) }";
    private static final String DELETE_ARTICLE = "{ CALL deleteArticle (?) }";
    private static final String SELECT_ARTICLE = "{ CALL selectArticle (?) }";
    private static final String SELECT_ARTICLES = "{ CALL selectArticles }";

    @Override
    public int createArticle(Article article) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getLink());
            stmt.setString(3, article.getDescription());
            stmt.setString(4, article.getPicturePath());
            stmt.setString(5, article.getPublishedDate().format(Article.DATE_FORMATTER));

            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(6);
        }
    }

    @Override
    public void createArticles(List<Article> articles) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {
            for (Article article : articles) {
                stmt.setString(1, article.getTitle());
                stmt.setString(2, article.getLink());
                stmt.setString(3, article.getDescription());
                stmt.setString(4, article.getPicturePath());
                stmt.setString(5, article.getPublishedDate().format(Article.DATE_FORMATTER));

                stmt.registerOutParameter(6, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateArticle(int id, Article data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ARTICLE)) {
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getLink());
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getPicturePath());
            stmt.setString(5, data.getPublishedDate().format(Article.DATE_FORMATTER));
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ARTICLE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Article> selectArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ARTICLE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Article(
                            rs.getInt(ID_ARTICLE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Article.DATE_FORMATTER)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Article> selectArticles() throws Exception {
        List<Article> articles = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ARTICLES)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(new Article(
                            rs.getInt(ID_ARTICLE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Article.DATE_FORMATTER)));
                }
            }
        }
        return articles;
    }

}
