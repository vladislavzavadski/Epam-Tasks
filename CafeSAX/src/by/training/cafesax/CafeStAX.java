/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafesax;

import by.training.cafesax.beans.Food;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author vladislav
 */
public class CafeStAX {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xMLInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream("Menu/menu.xml");
        XMLStreamReader reader = xMLInputFactory.createXMLStreamReader(inputStream);
        
        Map<String, ArrayList<Food>> menu = process(reader);
        
        for(Map.Entry foodType:menu.entrySet()){
            System.out.println(foodType.getKey());
            ArrayList<Food> foods = (ArrayList<Food>)foodType.getValue();
            for(Food item: foods){
                System.out.println("1. Фото "+item.getPhoto()+"\n2. Название "+item.getName()+"\n3. Описание "+
                        item.getDescription()+"\n3. Кухня "+item.getKitchen()+"\n4. Порции: "+item.getStringPortions()+item.getStringPrice());
            }
        }        
    }
    
    public static Map<String, ArrayList<Food>> process(XMLStreamReader reader) throws XMLStreamException{
        Map<String, ArrayList<Food>> menu = new HashMap<>();
        Food food = null;
        String currentFoodType = null;
        String localName = null;
        String portionType = null;
        int cost = 0;   
        boolean tmp = reader.hasNext();
        while (tmp) {            
            int type = reader.next();
            switch(type){
                case XMLStreamConstants.START_ELEMENT:{
                    localName = reader.getLocalName();
                    if(localName.equals("breakfast")||localName.equals("cold-appetizers")||localName.equals("hot-appetizers")){
                        currentFoodType = localName;
                        menu.put(currentFoodType, new ArrayList<>());
                    }
                    
                    if(localName.equals("food")){
                        food = new Food();
                        food.setId(reader.getAttributeValue(null, "id"));
                    }
                    break;
                }
                
                case XMLStreamConstants.CHARACTERS:{
                    String content = reader.getText().trim();
                    if(content.equals("")){
                        break;
                    }
                    switch(localName){
                        case "photo":{
                            food.setPhoto(content);
                            break;
                        }
                        case "name":{
                            food.setName(content);
                            break;
                        }
                        case "kitchen":{
                            food.setKitchen(content);
                            break;
                        }
                        case "description":{
                            food.setDescription(content);
                            break;
                        }
                        case "portion-size":{
                            food.addPortion(Integer.parseInt(content));
                            break;
                        }
                        case "portion-type":{
                            portionType = content;
                            break;
                        }
                        case "portion-cost":{
                            cost = Integer.parseInt(content);
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

                    break;
                }
                
                case XMLStreamConstants.END_ELEMENT:{
                    if(reader.getLocalName().equals("food")){
                        menu.get(currentFoodType).add(food);
                        //food = null;
                    }
                    break;
                }
            }
            tmp = reader.hasNext();
        }
        return menu;
    }
}
