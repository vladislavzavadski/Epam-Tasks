/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.dao;

import by.trepam.news.dao.implementation.NewsDaoImpl;

/**
 *
 * @author vladislav
 */
public class DaoFactory {
    private static DaoFactory daoFactory = new DaoFactory();
    
    private DaoFactory(){}
    
    public static DaoFactory getInstance(){
        return daoFactory;
    }
    
    public INewsDao getDao(){
        return new NewsDaoImpl();
    }
}
