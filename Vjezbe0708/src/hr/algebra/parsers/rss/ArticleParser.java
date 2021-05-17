/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Article;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author doss
 */
public class ArticleParser {
    
    private ArticleParser() {}
    
    private static final String RSS_URL = "https://slobodnadalmacija.hr/feed";
    private static final String ATT_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    
    public static List<Article> parse() throws IOException, XMLStreamException {
        List<Article> articles = new ArrayList<Article>();
        
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            
            StartElement startElement = null; 
            Article article = null;
            Optional<TagType> tagType = Optional.empty();
            
            
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                
                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent()) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch(tagType.get()) {
                        case ITEM:
                            article = new Article();
                            articles.add(article);
                            break;
                        case TITLE:
                            if (article != null && !data.isEmpty()) {
                                article.setTitle(data);
                            }
                            break;
                        case LINK:
                            if (article != null && !data.isEmpty()) {
                                article.setLink(data);
                            }
                            break;
                        case DESCRIPTION:
                            if (article != null && !data.isEmpty()) {
                                article.setDescription(data);
                            }
                            break;
                        case ENCLOSURE:
                            break;
                        case PUB_DATE:
                            if (article != null && !data.isEmpty()) {
                                LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return articles;
    }
    
    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESCRIPTION("description"),
        ENCLOSURE("enclosure"),
        PUB_DATE("pubDate");
        
        private final String name;

        private TagType(String name) {
            this.name = name;
        }
        
        private static Optional<TagType> from(String name) { // "item", "description"
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
