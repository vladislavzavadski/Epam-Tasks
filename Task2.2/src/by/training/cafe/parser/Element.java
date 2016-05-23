/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vladislav
 */
public class Element {
    private Map<String, Attribute> attributes;
    private String xmlText;
    private String textContent;
    private Map<String, ArrayList<Element>> elements;
    
    public Element(String xmlText){
        attributes = new HashMap<>();
        elements = new HashMap<>();
        this.xmlText = xmlText;
        lookAttributes();
        lookTextContent();
        lookElements();
    }
    
    private void lookElements(){
        String substring = xmlText.substring(1);
        Pattern pattern = Pattern.compile("<([\\S]+).+?</\\1>");
        Matcher matcher = pattern.matcher(substring);
        
        while(matcher.find()){
            if(elements.containsKey(matcher.group(1))){
                
            }
            else{
                elements.put(matcher.group(1), new ArrayList<>());
            }  
            elements.get(matcher.group(1)).add(new Element(matcher.group()));
        }
    }
    
    private void lookTextContent(){
        Pattern pattern = Pattern.compile(">.+<");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        textContent =  matcher.group().replaceAll("[><]", "");       
    }
    
    public Attribute getAttribute(String attrName){
        return attributes.get(attrName);
    }
    
    private void lookAttributes(){
        Pattern pattern = Pattern.compile("<.+?>");
        Matcher matcher = pattern.matcher(xmlText);//\s
        matcher.find();
        String temp = matcher.group();
        pattern = Pattern.compile("\\S+?=\\\".+?\\\"");
        matcher = pattern.matcher(temp);
        while(matcher.find()){
            String tmp = matcher.group();
            attributes.put(tmp.split("=")[0], new Attribute(tmp.split("=")[0], tmp.split("=")[1].replaceAll("\"", "")));
        }        
    }
    
    public ArrayList<Element> getElementsByTag(String tag){
        return elements.get(tag);
    }
    
    public String getTextContent(){
        return textContent;
    }
}
