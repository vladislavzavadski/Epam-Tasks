/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.dao.implementation;

import by.trepam.news.dao.INewsDao;
import by.trepam.news.dao.exceptions.DaoException;
import by.trepam.news.entity.Catalog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author vladislav
 */
public class NewsDaoImpl implements INewsDao{
    private static final String pathToFile = "resourse/News.xml";
    @Override
    public void saveNews(Catalog catalog) throws DaoException {
        JAXBContext context;
        
        try {
            FileOutputStream xmlFile = new FileOutputStream(pathToFile);
            context = JAXBContext.newInstance(Catalog.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.training.by/2016/CatalogSchema News.xsd");
            m.marshal(catalog, xmlFile);
            xmlFile.close();
        } catch (JAXBException ex) {
            throw new DaoException(ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            throw new DaoException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
        
    }

    @Override
    public Catalog getCatalog() throws DaoException {
        try {
            JAXBContext context;
            FileInputStream is = new FileInputStream(pathToFile);
            Catalog catalog = null;
            
            context = JAXBContext.newInstance(Catalog.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            catalog = (Catalog)unmarshaller.unmarshal(is);
            is.close();
            return catalog;
        } catch (JAXBException ex) {
           throw new DaoException(ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            throw new DaoException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new DaoException(ex.getMessage(), ex);
        } 
    }
    
}
