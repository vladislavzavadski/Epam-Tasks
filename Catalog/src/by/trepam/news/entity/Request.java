/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vladislav
 */
public class Request {
    private String commandName;
    private Map<String, Object> params;

    public Request() {
        this.params = new HashMap<>();
    }
    
    public void setParameter(String name, Object value){
        params.put(name, value);
    }
    
    public Object getParamenter(String name){
        return params.get(name);
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
