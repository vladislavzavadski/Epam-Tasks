/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.view;

import by.trepam.news.entity.Criteria;
import by.trepam.news.entity.News;
import by.trepam.news.entity.Request;
import by.trepam.news.entity.Response;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author vladislav
 */
public class View{
 
    private List<Request> requests;
        

    public Request getUserAction(){
        
        if(requests.isEmpty()){
            return null;
        }
        return requests.remove(0);
    }
    
    public View(){
        requests = new ArrayList<>();
        initRequests();
    }

    public void printAnswer(Response response){
        
        printAnswer(response.getParameter("message").toString());
        if(response.isStatus()){            
            if(response.getParameter("newsList")!=null){
                showResults((List<News>)response.getParameter("newsList"));
            }
        }
        else{
            printAnswer(response.getParameter("errorMessage").toString());
        }
    }
    
    private void showResults(List<News> newsList){
        newsList.forEach(news->{
            System.out.println("1. Name: "+news.getName());
            System.out.println("2. Issue date: "+new SimpleDateFormat("yyyy-MM-dd").format(news.getIssueDate()));
            System.out.println("3. Authors: "+news.getAuthors());
            System.out.println("4. Body: "+news.getBody());
            System.out.println("--------------------------------------");
        });
    }

    public void printAnswer(String message){
        System.out.println(message);
    }
    
    private void initRequests(){
        Request request = new Request();
        request.setCommandName("SAVE_NEW_NEWS");
        request.setParameter("category", "film");
        request.setParameter("subCategory", "poem");
        News news = new News();
        List<String> authors = new ArrayList<String>();
        authors.add("qwe");
        authors.add("qwe1");
        authors.add("qwe2");
        news.setAuthors(authors);
        news.setIssueDate(new Date());
        news.setName("Bla-bla-bla");
        news.setBody("Body");
        request.setParameter("entity", news);  
        requests.add(request);
        
        request = new Request();
        request.setCommandName("SAVE_NEW_NEWS");
        request.setParameter("category", "disk");
        request.setParameter("subCategory", "music");
        news = new News();
        authors = new ArrayList<String>();
        authors.add("Vladislav");
        authors.add("Zavadski");
        authors.add("qweeqw");
        news.setAuthors(authors);
        news.setIssueDate(new Date());
        news.setName("Выпьем за любовь");
        news.setBody("Выпьем за любовьь");
        request.setParameter("entity", news);     
        requests.add(request);
        
        request = new Request();
        request.setCommandName("FIND_NEWS");
        Criteria criteria = new Criteria();
     //   criteria.setCategoryName("film");
        criteria.setSubCategoryName("music");
   //     criteria.setNewsName("Bla-bla-bla");
        request.setParameter("entity", criteria); 
        requests.add(request);
        
    }

}
