/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe.parser;

/**
 *
 * @author vladislav
 */
public class Attribute {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public Attribute(){}
    
    public Attribute(String name, String value){
        this.name = name;
        this.value = value;
    }
}
