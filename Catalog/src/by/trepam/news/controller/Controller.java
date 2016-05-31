/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.controller;

import by.trepam.news.entity.Request;
import by.trepam.news.entity.Response;

/**
 *
 * @author vladislav
 */
public class Controller {
    public Response processAction(Request request){
        CommandHelper commandHelper = new CommandHelper();
        Command command = commandHelper.getCommand(CommandName.valueOf(request.getCommandName()));
        return command.execute(request);
    }
}
