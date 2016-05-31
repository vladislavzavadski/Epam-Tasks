/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.service;

import by.trepam.news.entity.Criteria;
import by.trepam.news.entity.News;
import by.trepam.news.service.exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author vladislav
 */
public interface IService {
    void saveNewNews(News news, String categoryName, String subCategoryName) throws ServiceException;
    List<News> findNews(Criteria criteria) throws ServiceException;
}
