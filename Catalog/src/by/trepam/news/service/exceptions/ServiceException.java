/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.service.exceptions;

/**
 *
 * @author vladislav
 */
public class ServiceException extends Exception{
    public ServiceException(String message){
        super(message);
    }
    
    public ServiceException(String message, Exception ex){
        super(message, ex);
    }
}
