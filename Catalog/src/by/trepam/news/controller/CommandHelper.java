/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.trepam.news.controller;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vladislav
 */
public class CommandHelper {
    private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
    
    public CommandHelper(){
        commands.put(CommandName.SAVE_NEW_NEWS, new SaveNewsCommand());
        commands.put(CommandName.FIND_NEWS, new FindNewsCommand());
    }
    
    public Command getCommand(CommandName commandName){
        return commands.get(commandName);
    }
}
