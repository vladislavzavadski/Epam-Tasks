/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.service;

import by.trepam.news.service.implementation.NewsService;

/**
 *
 * @author vladislav
 */
public class ServiceFactory {
    private static ServiceFactory serviceFactory = new ServiceFactory();
    
    public static ServiceFactory getInstance(){
        return serviceFactory;
    }
    
    
    
    private ServiceFactory(){}
    
    public IService getService(){
        return new NewsService();
    }
}
