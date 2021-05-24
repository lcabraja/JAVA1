/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lcabraja
 */
public class ArticleTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Id", "Title", "Link", "Description", "Picture path", "Published date"};

    private List<Article> articles;

    public ArticleTableModel(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return articles.size();
    }

    @Override
    public int getColumnCount() {
        return Article.class.getDeclaredFields().length - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return articles.get(rowIndex).getId();
            case 1:
                return articles.get(rowIndex).getTitle();
            case 2:
                return articles.get(rowIndex).getLink();
            case 3:
                return articles.get(rowIndex).getDescription();
            case 4:
                return articles.get(rowIndex).getPicturePath();
            case 5:
                return articles.get(rowIndex).getPublishedDate().format(Article.DATE_FORMATTER);
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }

}
