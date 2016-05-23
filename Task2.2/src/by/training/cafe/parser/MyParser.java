/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafe.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author vladislav
 */
public class MyParser {
    
    private String xmlText="";
    private Document document;
    
    public void parse(String url) throws FileNotFoundException{
        File xmlFile = new File(url);
        Scanner scanner = new Scanner(xmlFile);
        while (scanner.hasNext()) {            
            xmlText+=scanner.nextLine();
        }
        scanner.close();
        document = new Document(this.xmlText);

    }
    
    public Document getDocument(){       
        return document;
    }
}
