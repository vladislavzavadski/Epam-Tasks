/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.own_parser.service;

import by.training.own_parser.domain.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vladislav
 */
public class MyParser implements Parser{
    
    private String xmlText="";
    private Document document;
    
    @Override
    public void parse(String url) throws FileNotFoundException{
        File xmlFile = new File(url);
        Scanner scanner = new Scanner(xmlFile);
        while (scanner.hasNext()) {            
            xmlText+=scanner.nextLine();
        }
        scanner.close();
        createDocument();
    }
    
    private void createDocument(){
        document = new Document();
        Pattern pattern = Pattern.compile("<([\\S]+).+</\\1>");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        document.setRootElement(lookRootElement(matcher.group()));
        lookComments();
        document.setDocumentEncoding(getDocumentProperty("encoding"));
        document.setDocumentEncoding(getDocumentProperty("version"));
    }

    private String lookTextContent(String xmlText){
        Pattern pattern = Pattern.compile(">.+<");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        return matcher.group().replaceAll("[><]", "");       
    }  
    
    private void lookAttributes(Element element, String xmlText){
        Pattern pattern = Pattern.compile("<.+?>");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        String temp = matcher.group();
        pattern = Pattern.compile("\\S+?=\\\".+?\\\"");
        matcher = pattern.matcher(temp);
        while(matcher.find()){
            String tmp = matcher.group();
            element.addAttribute(new Attribute(tmp.split("=")[0], tmp.split("=")[1].replaceAll("\"", "")));
        }        
    }    
     
    
    private Element lookRootElement(String xmlText){   
        Element element = new Element();
        Pattern pattern = Pattern.compile("<([\\S]+).+?</\\1>");
        Matcher matcher = pattern.matcher(xmlText.substring(1));
        while(matcher.find()){
            element.addElement(matcher.group(1), lookRootElement(matcher.group()));                  
        }
        
        element.setTextContent(lookTextContent(xmlText));
        lookAttributes(element, xmlText);
        if(element.getAttribute("id")!=null){
            document.addElementById(element.getAttribute("id").getValue(), element);
        }
        return element;       
    }
    
    private void lookComments(){
        Pattern pattern = Pattern.compile("<!--.+?-->");
        Matcher matcher = pattern.matcher(xmlText);
        while (matcher.find()) {            
            document.addComment(matcher.group());
        }        
    }

    private String getDocumentProperty(String property){
        Pattern pattern = Pattern.compile("<\\?.+?\\?>");
        Matcher matcher = pattern.matcher(xmlText);
        matcher.find();
        String temp = matcher.group();
        pattern = Pattern.compile(property+"=\\\".+?\\\"");
        matcher = pattern.matcher(xmlText);
        matcher.find();
        temp = matcher.group();
        return temp.split("=")[1].replaceAll("\"", "");
    }    
    
    @Override
    public Document getDocument(){       
        return document;
    }
}
