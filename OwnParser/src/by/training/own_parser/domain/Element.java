/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.own_parser.domain;

import java.util.ArrayList;

/**
 *
 * @author vladislav
 */
public interface Element {
    Attribute getAttribute(String attrName);     
    ArrayList<Element> getElementsByTag(String tag);    
    String getTextContent();
}
