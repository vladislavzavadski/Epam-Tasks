/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe;

import by.training.cafe.beans.Food;
import java.util.ArrayList;
import javafx.util.Pair;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;




/**
 *
 * @author vladislav
 */
public class Cafe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        DOMParser parser = new DOMParser();
        parser.parse("Menu/menu.xml");
        Document document = parser.getDocument();
    
        Element root = document.getDocumentElement();
        ArrayList<Food> breakfast = getFoodList((Element)root.getElementsByTagName("breakfast").item(0));
        ArrayList<Food> hotAppetizers = getFoodList((Element)root.getElementsByTagName("hot-appetizers").item(0));
        ArrayList<Food> coldAppetizers = getFoodList((Element)root.getElementsByTagName("cold-appetizers").item(0));
        showMenu(breakfast, "Завтрак");
        showMenu(hotAppetizers, "Горячие закуски");
        showMenu(coldAppetizers, "Холодные закуски");
        
    }
    
    public static ArrayList<Food> getFoodList(Element tagElement){
        NodeList nodeList = tagElement.getElementsByTagName("food");
        ArrayList<Food> result = new ArrayList<>();
        for(int i=0; i<nodeList.getLength(); i++){
            result.add(getFood((Element)nodeList.item(i)));
        }
        return result;
    }
    
    public static Food getFood(Element element){
        Food food = new Food();
        food.setName(((Element)element.getElementsByTagName("name").item(0)).getTextContent());
        food.setPhoto(((Element)element.getElementsByTagName("photo").item(0)).getTextContent());
        food.setDescription(((Element)element.getElementsByTagName("description").item(0)).getTextContent());
        food.setId(element.getAttribute("id"));
        NodeList nodeList = element.getElementsByTagName("portion-size");
        for(int i=0; i<nodeList.getLength(); i++){
            food.getPortions().add(Integer.parseInt(((Element)nodeList.item(i)).getTextContent()));
        }
        NodeList nodeList1 = element.getElementsByTagName("price");
        for(int i=0; i<nodeList1.getLength(); i++){
            Pair<String, Integer> pair = getSizeCostPair((Element)nodeList1.item(i));
            food.getPrice().put(pair.getKey(), pair.getValue());
        }
        return food;
    }
    
    public static Pair<String, Integer> getSizeCostPair(Element element){
        Pair<String, Integer> pairSizeCost;
        pairSizeCost = new Pair<>(((Element)element.getElementsByTagName("portion-type").item(0)).getTextContent(), Integer.parseInt(((Element)element.getElementsByTagName("portion-cost").item(0)).getTextContent()));
        
        return pairSizeCost;
    }
    
    public static void showMenu(ArrayList<Food> menu, String foodType){
        System.out.println(foodType+":\n");
        menu.forEach(System.out::println);
    }
    
}
