/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.model.Article;
import hr.algebra.parsers.rss.ArticleParser;
import hr.algebra.view.UploadPanel;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author doss
 */
public class Main {
    public static void main(String[] args) {
        try {
            List<Article> articles = ArticleParser.parse();
            articles.forEach(x -> System.out.println(x));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        UploadPanel up = new UploadPanel();
        up.setEnabled(true);
        
    }
}
