/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vladislav
 */
@XmlRootElement(name = "sub-category")
public class SubCategory {
    
    private String subCategoryName;
    
    private List<News> news;

    public SubCategory(){
        news = new ArrayList<>();
    }
    @XmlAttribute(name = "name")
    public String getSubCategoryName() {
        return subCategoryName;
    }
    
    @XmlElement
    public List<News> getNews() {
        return news;
    }
    
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.subCategoryName);
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
        final SubCategory other = (SubCategory) obj;
        if (!Objects.equals(this.subCategoryName, other.subCategoryName)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "SubCategory{" + "subCategoryName=" + subCategoryName + ", news=" + news + '}';
    }
    
    
}
