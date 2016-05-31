/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.start;

import by.trepam.news.controller.Controller;
import by.trepam.news.entity.Request;
import by.trepam.news.entity.Response;
import by.trepam.news.view.View;

import javax.xml.bind.JAXBException;


/**
 *
 * @author vladislav
 */
public class Main {
    public static void main(String ...n) throws JAXBException {
        View view = new View();
        Controller controller = new Controller();
        Request request;
        while((request = view.getUserAction())!=null){
            Response response = controller.processAction(request);
            view.printAnswer(response);
        }
    }
}
