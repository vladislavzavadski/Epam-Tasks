/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.cafesax;

import by.training.cafesax.beans.Food;
import by.training.cafesax.handlers.MenuSaxHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author vladislav
 */
public class CafeSAX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        MenuSaxHandler handler = new MenuSaxHandler();
        reader.setContentHandler(handler);
        reader.parse(new InputSource("Menu/menu.xml"));

        reader.setFeature("http://xml.org/sax/features/validation", true);
   
        reader.setFeature("http://xml.org/sax/features/namespaces", true);

        reader.setFeature("http://xml.org/sax/features/string-interning",true);

        reader.setFeature("http://apache.org/xml/features/validation/schema",false);
        
        Map<String, ArrayList<Food>> menu = handler.getMenu();
        
        for(Map.Entry foodType:menu.entrySet()){
            System.out.println(foodType.getKey());
            ArrayList<Food> foods = (ArrayList<Food>)foodType.getValue();
            for(Food item: foods){
                System.out.println("1. Фото "+item.getPhoto()+"\n2. Название "+item.getName()+"\n3. Описание "+
                        item.getDescription()+"\n3. Кухня "+item.getKitchen()+"\n4. Порции: "+item.getStringPortions()+item.getStringPrice());
            }
        }
    
    }
}
