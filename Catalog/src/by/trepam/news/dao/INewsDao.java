/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.dao;

import by.trepam.news.dao.exceptions.DaoException;
import by.trepam.news.entity.Catalog;
import by.trepam.news.entity.News;

/**
 *
 * @author vladislav
 */
public interface INewsDao {
    void saveNews(Catalog catalog) throws DaoException;
    Catalog getCatalog() throws DaoException;
}
