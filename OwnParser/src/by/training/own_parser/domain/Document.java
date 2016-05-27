/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.own_parser.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author vladislav
 */
public class Document {
    private Element rootElement;
    private ArrayList<String> comments;
    private Map<String, Element> elementByIdMap;
    private String documentVersion;
    private String documentEncoding;
        
    public Document(){
        comments = new ArrayList<>();
        elementByIdMap = new HashMap<String, Element>();
    }
    
    public void addElementById(String id, Element element){
        elementByIdMap.put(id, element);
    }

    @Override
    public String toString() {
        return "Document{" + "rootElement=" + rootElement + ", comments=" + comments + ", elementByIdMap=" + elementByIdMap + ", documentVersion=" + documentVersion + ", documentEncoding=" + documentEncoding + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.rootElement);
        hash = 79 * hash + Objects.hashCode(this.comments);
        hash = 79 * hash + Objects.hashCode(this.elementByIdMap);
        hash = 79 * hash + Objects.hashCode(this.documentVersion);
        hash = 79 * hash + Objects.hashCode(this.documentEncoding);
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
        final Document other = (Document) obj;
        if (!Objects.equals(this.documentVersion, other.documentVersion)) {
            return false;
        }
        if (!Objects.equals(this.documentEncoding, other.documentEncoding)) {
            return false;
        }
        if (!Objects.equals(this.rootElement, other.rootElement)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.elementByIdMap, other.elementByIdMap)) {
            return false;
        }
        return true;
    }

    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public void setDocumentEncoding(String documentEncoding) {
        this.documentEncoding = documentEncoding;
    }
    
    public void addComment(String comment){
        comments.add(comment);
    }
      
    public Element getDocumentElement(){
        return rootElement;
    }
        
    public ArrayList<String> getComments(){
        return comments;
    }
    
    
    public String getDocumentVersion(){
        return documentVersion;
    }
    
    public String getDocumentEncoding(){
        return documentEncoding;
    }
    
    public Element getElementById(String id){
               
        return elementByIdMap.get(id);
    }
    
}
