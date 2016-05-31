/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vladislav
 */

@XmlRootElement(name="catalog")

public class Catalog {
    
    private List<Category> categories;

    @XmlElement(name="category")
    public List<Category> getCategories(){
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public Catalog(){
        categories = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.categories);
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
        final Catalog other = (Catalog) obj;
        if (!Objects.equals(this.categories, other.categories)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Catalog{" + "categories=" + categories + '}';
    }
    
    
}
