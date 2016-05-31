/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import by.trepam.news.adapter.DateAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author vladislav
 */


@XmlRootElement(name = "news")
@XmlType(name="news", propOrder = {"name", "authors", "issueDate", "body"})

public class News {
    
    

    private String name;
    
    private List<String> authors;

    private Date issueDate;
    
    private String body;

    public News(){
        authors = new ArrayList<>();
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    @XmlElement
    public String getName() {
        return name;
    }
    @XmlElement(name="author")
    public List<String> getAuthors() {
        return authors;
    }
    @XmlElement(name="issue-date")
    @XmlJavaTypeAdapter(value = DateAdapter.class, type = Date.class)
    public Date getIssueDate() {
         return issueDate;
    }
    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.authors);
        hash = 11 * hash + Objects.hashCode(this.issueDate);
        hash = 11 * hash + Objects.hashCode(this.body);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final News other = (News) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.issueDate, other.issueDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "News{" + "name=" + name + ", authors=" + authors + ", issueDate=" + issueDate + ", body=" + body + '}';
    }
    
    
}
