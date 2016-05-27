/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.own_parser.factory;

import by.training.own_parser.service.MyParser;
import by.training.own_parser.service.Parser;

/**
 *
 * @author vladislav
 */
public class ParsersFactory {
    
    private static ParsersFactory parsersFactory = new ParsersFactory();
    
    private ParsersFactory(){}
    
    public static ParsersFactory getInstance(){
        return parsersFactory;
    }
    
    public Parser getParser(){
        return new MyParser();
    }

}
