/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.service.implementation;

import by.trepam.news.dao.DaoFactory;
import by.trepam.news.dao.INewsDao;
import by.trepam.news.dao.exceptions.DaoException;
import by.trepam.news.entity.Catalog;
import by.trepam.news.entity.Category;
import by.trepam.news.entity.Criteria;
import by.trepam.news.entity.News;
import by.trepam.news.entity.SubCategory;
import by.trepam.news.service.IService;
import by.trepam.news.service.exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vladislav
 */
public class NewsService implements IService{

    @Override
    public void saveNewNews(News news, String categoryName, String subCategoryName) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        INewsDao newsDao = daoFactory.getDao();
        try{
            Catalog catalog = newsDao.getCatalog();
            
            Category category = new Category();
            category.setName(categoryName);
            
            if(!catalog.getCategories().contains(category)){
                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryName(subCategoryName);
                subCategory.getNews().add(news);
                category.getSubcategories().add(subCategory);
                catalog.getCategories().add(category);
            }
            else{
                for(Category category1:catalog.getCategories()){
                    if(category1.getName().equals(categoryName)){
                        SubCategory subCategory = new SubCategory();
                        subCategory.setSubCategoryName(subCategoryName);
                        if(!category1.getSubcategories().contains(subCategory)){
                            subCategory.getNews().add(news);
                            category1.getSubcategories().add(subCategory);

                        }
                        else{
                            for(SubCategory subCategory1:category1.getSubcategories()){
                                if(subCategory1.getSubCategoryName().equals(subCategoryName)){
                                    subCategory1.getNews().add(news);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }

            
            newsDao.saveNews(catalog);
        }
        catch(DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
        
    }

    @Override
    public List<News> findNews(Criteria criteria) throws ServiceException {
        List<News> result;
        try {
            result = new ArrayList<>();
            DaoFactory daoFactory = DaoFactory.getInstance();
            INewsDao iNewsDao = daoFactory.getDao();
            Catalog catalog = iNewsDao.getCatalog();
            for(Category category:catalog.getCategories()){
                if(category.getName().equals(criteria.getCategoryName())||criteria.getCategoryName()==null){
                    for(SubCategory subCategory:category.getSubcategories()){
                        if(subCategory.getSubCategoryName().equals(criteria.getSubCategoryName())||criteria.getSubCategoryName()==null){
                            for(News news:subCategory.getNews()){
                                if((news.getName().equals(criteria.getNewsName())||criteria.getNewsName()==null)&&(news.getIssueDate().equals(criteria.getDateOfIssue())||criteria.getDateOfIssue()==null)){
                                    result.add(news);
                                }
                            }
                        }
                    }
                }
            }
            return result;
        } catch (DaoException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
        
    }
    
}
