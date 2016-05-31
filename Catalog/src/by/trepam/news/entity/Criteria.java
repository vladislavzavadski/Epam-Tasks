/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author vladislav
 */
public class Criteria {
    private String categoryName;
    private String subCategoryName;
    private Date dateOfIssue;
    private String newsName;

    public String getCategoryName() {
        return categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.categoryName);
        hash = 97 * hash + Objects.hashCode(this.subCategoryName);
        hash = 97 * hash + Objects.hashCode(this.dateOfIssue);
        hash = 97 * hash + Objects.hashCode(this.newsName);
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
        final Criteria other = (Criteria) obj;
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        if (!Objects.equals(this.subCategoryName, other.subCategoryName)) {
            return false;
        }
        if (!Objects.equals(this.newsName, other.newsName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfIssue, other.dateOfIssue)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Criteria{" + "categoryName=" + categoryName + ", subCategoryName=" + subCategoryName + ", dateOfIssue=" + dateOfIssue + ", newsName=" + newsName + '}';
    }
    
    
}
