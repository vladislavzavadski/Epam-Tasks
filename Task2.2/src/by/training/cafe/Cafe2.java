/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe;

import by.training.cafe.beans.Food;
import by.training.cafe.parser.Document;
import by.training.cafe.parser.Element;
import by.training.cafe.parser.MyParser;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author vladislav
 */
public class Cafe2 {
    public static void main(String[] args) throws FileNotFoundException {
        MyParser parser = new MyParser();
        parser.parse("Menu/menu.xml");
        Document document = parser.getDocument();
        Element element = document.getDocumentElement();
        
        ArrayList<Element> breakfast = element.getElementsByTag("breakfast");
        ArrayList<Element> hotAppetizers = element.getElementsByTag("hot-appetizers");
        ArrayList<Element> coldAppetizers = element.getElementsByTag("cold-appetizers");
        
        showMenu(getFoodList(breakfast.get(0)), "Завтрак");
        showMenu(getFoodList(hotAppetizers.get(0)), "Горячие закуски");
        showMenu(getFoodList(coldAppetizers.get(0)), "Холодные закуски");
        
    }
    
    public static ArrayList<Food> getFoodList(Element tagElement){
    ArrayList<Element> nodeList = tagElement.getElementsByTag("food");
    ArrayList<Food> result = new ArrayList<>();
    for(int i=0; i<nodeList.size(); i++){
        result.add(getFood((Element)nodeList.get(i)));
    }
    return result;
    }
    
    public static Food getFood(Element element){
        Food food = new Food();
        food.setName(((Element)element.getElementsByTag("name").get(0)).getTextContent());
        food.setPhoto(((Element)element.getElementsByTag("photo").get(0)).getTextContent());
        food.setDescription(((Element)element.getElementsByTag("description").get(0)).getTextContent());
        food.setKitchen(((Element)element.getElementsByTag("kitchen").get(0)).getTextContent());
        food.setId(element.getAttribute("id").getValue());
        ArrayList<Element> nodeList = element.getElementsByTag("portion-size");
        for(int i=0; i<nodeList.size(); i++){
            food.getPortions().add(Integer.parseInt(((Element)nodeList.get(i)).getTextContent()));
        }
        ArrayList<Element> nodeList1 = element.getElementsByTag("price");
        for(int i=0; i<nodeList1.size(); i++){
            Pair<String, Integer> pair = getSizeCostPair((Element)nodeList1.get(i));
            food.getPrice().put(pair.getKey(), pair.getValue());
        }
        return food;
    }
    
    public static Pair<String, Integer> getSizeCostPair(Element element){
        Pair<String, Integer> pairSizeCost;
        pairSizeCost = new Pair<>(((Element)element.getElementsByTag("portion-type").get(0)).getTextContent(), Integer.parseInt(((Element)element.getElementsByTag("portion-cost").get(0)).getTextContent()));
        
        return pairSizeCost;
    }
    
    public static void showMenu(ArrayList<Food> menu, String foodType){
        System.out.println(foodType+":\n");
        for(Food item:menu){
            System.out.println("1. Фото "+item.getPhoto()+"\n2. Название "+item.getName()+"\n3. Описание "+
                    item.getDescription()+"\n3. Кухня "+item.getKitchen()+"\n4. Порции: "+item.getStringPortions()+item.getStringPrice());
        }
    }
}
