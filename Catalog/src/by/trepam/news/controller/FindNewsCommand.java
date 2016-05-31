/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.controller;

import by.trepam.news.entity.Criteria;
import by.trepam.news.entity.News;
import by.trepam.news.entity.Request;
import by.trepam.news.entity.Response;
import by.trepam.news.service.IService;
import by.trepam.news.service.ServiceFactory;
import by.trepam.news.service.exceptions.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladislav
 */
public class FindNewsCommand implements Command{

    @Override
    public Response execute(Request request) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        IService iService = serviceFactory.getService();
        Response response = new Response();
        try {
            List<News> result = iService.findNews((Criteria)request.getParamenter("entity"));
            response.setStatus(true);
            response.setParamenter("message", "News was successfully found");
            response.setParamenter("newsList", result);
        } catch (ServiceException ex) {
            Logger.getLogger(FindNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(false);
            response.setParamenter("message", "Something went wrong(");
            response.setParamenter("errorMessage", ex.getMessage());
        }
        return response;
    }
    
}
