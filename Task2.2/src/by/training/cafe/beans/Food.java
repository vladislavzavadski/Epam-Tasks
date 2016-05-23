/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe.beans;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author vladislav
 */
public class Food {
    private String id;
    private String name;
    private String description;
    private String photo;
    private ArrayList<Integer> portions;
    private Map<String, Integer> price;
    private String kitchen;

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setPortions(ArrayList<Integer> portions) {
        this.portions = portions;
    }

    public ArrayList<Integer> getPortions() {
        return portions;
    }
    
    
    public Food(){
        price = new TreeMap<>();
        portions = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPrice(Map<String, Integer> price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public Map<String, Integer> getPrice() {
        return price;
    }
    
    public String getStringPrice(){
        String result = "Цены: \n";
        for(Map.Entry<String, Integer> pair:price.entrySet()){
            result+="Вариант: "+pair.getKey()+" Цена: "+pair.getValue()+"\n";
        }
        return result;
    }
    
    public String getStringPortions(){
        String result="";
        for(Integer item:portions){
            result+=item+"/";
        }
        
        return result+"\n";
    }

}
