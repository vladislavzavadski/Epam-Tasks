/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author vladislav
 */
public class Response {
    private boolean status;
    private Map<String, Object> params;
    
    public Response(){
        params = new HashMap<>();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void setParamenter(String name, Object value){
        params.put(name, value);
    }
    
    public Object getParameter(String name){
        return params.get(name);
    }

    @Override
    public String toString() {
        return "Response{" + "status=" + status + ", params=" + params + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.status ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.params);
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
        final Response other = (Response) obj;
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.params, other.params)) {
            return false;
        }
        return true;
    }
            
}
