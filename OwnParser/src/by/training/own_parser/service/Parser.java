/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.training.own_parser.service;

import java.io.FileNotFoundException;

/**
 *
 * @author vladislav
 */
public interface Parser {
    void parse(String url) throws FileNotFoundException;
}
