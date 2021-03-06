package by.training.cafe.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.photo);
        hash = 29 * hash + Objects.hashCode(this.portions);
        hash = 29 * hash + Objects.hashCode(this.price);
        hash = 29 * hash + Objects.hashCode(this.kitchen);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Food other = (Food) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Food{" + "id=" + id + ", name=" + name + ", description=" + description + ", photo=" + photo + ", portions=" + portions + ", price=" + price + ", kitchen=" + kitchen + '}';
    }


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
    
    public void addPortion(int portion){
        this.portions.add(portion);
    }
    
    public void addPrice(String type, int cost){
        price.put(type, cost);
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
    
  /*  public String getStringPrice(){
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
    }*/

}
