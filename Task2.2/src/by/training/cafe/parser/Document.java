/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vladislav
 */
public class Document {
    private String rootXmlText;
    private Element rootElement;
    private ArrayList<String> comments;
    private String documentVersion;
    private String documentEncoding;
    
    public Document(String rootXml){  
        this.rootXmlText = rootXml;
        lookRootElement();
        comments = new ArrayList<>();
        lookComments();
        documentVersion = getDocumentProperty("version");
        documentEncoding = getDocumentProperty("encoding");
    }
    
    public Element getDocumentElement(){
        return rootElement;
    }
    
    private void lookRootElement(){    
        Pattern pattern = Pattern.compile("<([\\S]+).+</\\1>");
        Matcher matcher = pattern.matcher(rootXmlText);
        matcher.find();
        rootElement =  new Element(matcher.group());         
    }
    
    public ArrayList<String> getComments(){
        return comments;
    }
    
    private void lookComments(){
        Pattern pattern = Pattern.compile("<!--.+?-->");
        Matcher matcher = pattern.matcher(rootXmlText);
        while (matcher.find()) {            
            comments.add(matcher.group());
        }        
    }
    
    public String getDocumentVersion(){
        return documentVersion;
    }
    
    public String getDocumentEncoding(){
        return documentEncoding;
    }
    
    public Element getElementById(String id){
        Pattern pattern = Pattern.compile("<([\\S]+)[^<]+id=\""+id+"\".+?</\\1>");
        Matcher matcher = pattern.matcher(rootXmlText);
        matcher.find();
                
        return new Element(matcher.group());
    }
    
    private String getDocumentProperty(String property){
        Pattern pattern = Pattern.compile("<\\?.+?\\?>");
        Matcher matcher = pattern.matcher(rootXmlText);
        matcher.find();
        String temp = matcher.group();
        pattern = Pattern.compile(property+"=\\\".+?\\\"");
        matcher = pattern.matcher(rootXmlText);
        matcher.find();
        temp = matcher.group();
        return temp.split("=")[1].replaceAll("\"", "");
    }
}
