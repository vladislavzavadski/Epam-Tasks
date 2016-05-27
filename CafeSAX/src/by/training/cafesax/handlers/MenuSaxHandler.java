/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafesax.handlers;

import by.training.cafesax.beans.Food;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author vladislav
 */
public class MenuSaxHandler extends DefaultHandler{
    
    private Map<String, ArrayList<Food>> menu = new HashMap<>();
    private Food food;
    private String currentFoodType;
    private String portionType;
    private int cost;
    private StringBuilder text;
    
    public Map<String, ArrayList<Food>> getMenu(){
        return menu;
    }
    
    @Override
    public void startDocument(){
        System.out.println("Parsing was started");
    }
    
    @Override
    public void endDocument(){
        System.out.println("Parsing was finished");
    }
    @Override
    
    public void characters(char[] buffer, int start, int length) {
        text.append(buffer, start, length);
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        text = new StringBuilder();
        
        if(qName.equals("menu")){
            return;
        }
        
        if(qName.equals("food")){
            food = new Food();
            food.setId(attributes.getValue("id"));
        }
        
        else if(qName.equals("breakfast")||qName.equals("hot-appetizers")||qName.equals("cold-appetizers")){
            currentFoodType = qName;
            menu.put(qName, new ArrayList<>());
        }
        
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if(qName.equals("photo")){
            food.setPhoto(text.toString());
        }
        
        if(qName.equals("name")){
            food.setName(text.toString());
        }
        
        if(qName.equals("kitchen")){
            food.setKitchen(text.toString());
        }
        
        if(qName.equals("description")){
            food.setDescription(text.toString());
        }
        
        if(qName.equals("portion-size")){
            food.addPortion(Integer.parseInt(text.toString()));
        }
        
        if(qName.equals("portion-type")){
            portionType = text.toString();
        }
        
        if(qName.equals("portion-cost")){
            cost = Integer.parseInt(text.toString());
        }
        
        if(qName.equals("price")){
            food.addPrice(portionType, cost);
        }
        if(qName.equals("food")){
            menu.get(currentFoodType).add(food);
            food = null;
        }
        
        
        
    }
    
    
}
