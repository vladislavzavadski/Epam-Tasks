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
public class Element {
    private Map<String, Attribute> attributes;

    private String textContent;
    private Map<String, ArrayList<Element>> elements;
    
    
    public Element(){
        attributes = new HashMap<>();
        elements = new HashMap<>();
    }
    

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    
    public void addAttribute(Attribute attribute){
        attributes.put(attribute.getName(), attribute);
    }
    
    public void addElement(String parentName, Element element){
        if(!elements.containsKey(parentName)){
            elements.put(parentName, new ArrayList<>());
        }
        
        elements.get(parentName).add(element);
    }
        
    public Attribute getAttribute(String attrName){
        return attributes.get(attrName);
    }
      
    public ArrayList<Element> getElementsByTag(String tag){
        return elements.get(tag);
    }
    
    public String getTextContent(){
        return textContent;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.attributes);
        hash = 17 * hash + Objects.hashCode(this.textContent);
        hash = 17 * hash + Objects.hashCode(this.elements);
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
        final Element other = (Element) obj;
        if (!Objects.equals(this.textContent, other.textContent)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (!Objects.equals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Element{" + "attributes=" + attributes + ", textContent=" + textContent + ", elements=" + elements + '}';
    }
    
    
}
