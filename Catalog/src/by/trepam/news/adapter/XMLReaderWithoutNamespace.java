/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.adapter;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

/**
 *
 * @author vladislav
 */
public class XMLReaderWithoutNamespace extends StreamReaderDelegate {
    public XMLReaderWithoutNamespace(XMLStreamReader reader) {
      super(reader);
    }
    @Override
    public String getAttributeNamespace(int arg0) {
      return "";
    }
    @Override
    public String getNamespaceURI() {
      return "";
    }
}
