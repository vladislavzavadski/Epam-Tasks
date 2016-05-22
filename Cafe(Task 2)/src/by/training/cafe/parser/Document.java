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
    
    public Document(String rootXml){
        this.rootXmlText = rootXml;
    }
    
    public Element getDocumentElement(){
        Pattern pattern = Pattern.compile("<([a-z\\-]+).+</\\1>");
        Matcher matcher = pattern.matcher(rootXmlText);
        matcher.find();
        rootXmlText = matcher.group();
        return new Element(rootXmlText);
    }
    
    public ArrayList<String> getComments(){
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("<!--.+?-->");
        Matcher matcher = pattern.matcher(rootXmlText);
        while (matcher.find()) {            
            result.add(matcher.group());
        }
        return result;
    }
    
    public String getDocumentVersion(){
        return getDocumentProperty("version");
    }
    
    public String getDocumentEncoding(){
        return getDocumentProperty("encoding");
    }
    
    public Element getElementById(String id){
        Pattern pattern = Pattern.compile("<([a-z\\-]+)[^<]+id=\""+id+"\".+?</\\1>");
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
