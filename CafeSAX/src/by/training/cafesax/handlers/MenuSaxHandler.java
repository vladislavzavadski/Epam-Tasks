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
 
        switch(qName){
            case "photo":{
                food.setPhoto(text.toString());
                break;
            }
            case "name":{
                food.setName(text.toString());
                break;
            }
            case "kitchen":{
                food.setKitchen(text.toString());
                break;
            }
            case "description":{
                food.setDescription(text.toString());
                break;
            }
            case "portion-size":{
                food.addPortion(Integer.parseInt(text.toString()));
                break;
            }
            case "portion-type":{
                portionType = text.toString();
                break;
            }
            case "portion-cost":{
                cost = Integer.parseInt(text.toString());
                break;
            }
            case "price":{
                food.addPrice(portionType, cost);
                break;
            }
            case "food":{
                menu.get(currentFoodType).add(food);
                food = null;
                break;
            }
        }
        
        
        
    }
    
    
}
