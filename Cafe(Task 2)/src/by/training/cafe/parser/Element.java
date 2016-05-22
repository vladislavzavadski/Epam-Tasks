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
    private Map<String, String> attributes;
    private String xmlText;
    
    public Element(String xmlText){
        attributes = new HashMap<>();
        this.xmlText = xmlText;
        Pattern pattern = Pattern.compile("<.+?>");
        Matcher matcher = pattern.matcher(xmlText);//\s
        matcher.find();
        String temp = matcher.group();
        pattern = Pattern.compile("\\S+?=\\\".+?\\\"");
        matcher = pattern.matcher(temp);
        while(matcher.find()){
            String tmp = matcher.group();
            attributes.put(tmp.split("=")[0], tmp.split("=")[1].replaceAll("\"", ""));
        }
    }
    
    public String getAttribute(String attrName){
        return attributes.get(attrName);
    }
    
    public ArrayList<Element> getElementsByTag(String tag){
        ArrayList<Element> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("<"+tag+".+?</"+tag+">");
        Matcher matcher = pattern.matcher(this.xmlText);
        while(matcher.find()){
            result.add(new Element(matcher.group()));
        }
        return result;
    }
    
    public String getTextContent(){
        Pattern pattern = Pattern.compile(">.+<");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        return matcher.group().replaceAll("[><]", "");
    }
}
