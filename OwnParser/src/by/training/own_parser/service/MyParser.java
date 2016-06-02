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
    private DocumentImpl document;
    
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
        final String ROOT_TAG_REG_EXP = "<([\\S]+).+</\\1>";
        document = new DocumentImpl();
        Matcher matcher = getMatcher(ROOT_TAG_REG_EXP, xmlText);
        matcher.find();
        document.setRootElement(lookRootElement(matcher.group()));
        lookComments();
        document.setDocumentEncoding(getDocumentProperty("encoding"));
        document.setDocumentEncoding(getDocumentProperty("version"));
    }

    private String lookTextContent(String xmlText){
        final String TEXT_WITHIN_TAG_REG_EXP = ">.+<";
        Matcher matcher = getMatcher(TEXT_WITHIN_TAG_REG_EXP, xmlText);
        matcher.find();
        return matcher.group().replaceAll("[><]", "");       
    }  
    
    private void lookAttributes(ElementImpl element, String xmlText){
        final String TAG_START_REG_EXP = "<.+?>";
        final String ATTRIBUTE_VALUE_PAIR_REG_EXP = "\\S+?=\\\".+?\\\"";
        Matcher matcher = getMatcher(TAG_START_REG_EXP, xmlText);
        matcher.find();
        matcher = getMatcher(ATTRIBUTE_VALUE_PAIR_REG_EXP, matcher.group());
        while(matcher.find()){
            String tmp = matcher.group();
            element.addAttribute(new Attribute(tmp.split("=")[0], tmp.split("=")[1].replaceAll("\"", "")));
        }        
    }    
     
    
    private ElementImpl lookRootElement(String xmlText){   
        final String ELEMENT_REG_EXP = "<([\\S]+).+?</\\1>";
        ElementImpl element = new ElementImpl();
        Matcher matcher = getMatcher(ELEMENT_REG_EXP, xmlText.substring(1));
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
        final String COMMENT_REG_EXP = "<!--.+?-->";
        Matcher matcher = getMatcher(COMMENT_REG_EXP, xmlText);
        while (matcher.find()) {            
            document.addComment(matcher.group());
        }        
    }

    private String getDocumentProperty(String property){
        final String DOCUMENT_HEADER_REG_EXP = "<\\?.+?\\?>";
        final String DOCUMENT_PROPERTY_REG_EXP = "=\\\".+?\\\"";
        Matcher matcher = getMatcher(DOCUMENT_HEADER_REG_EXP, xmlText);
        matcher.find();
        matcher = getMatcher(property+DOCUMENT_PROPERTY_REG_EXP, xmlText);
        matcher.find();
        return matcher.group().split("=")[1].replaceAll("\"", "");
    }

    private Matcher getMatcher(String regExp, String srcString){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(srcString);
        return matcher;
    }
    
    @Override
    public Document getDocument(){       
        return (Document)document;
    }
}
