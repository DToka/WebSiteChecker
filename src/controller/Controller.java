package controller;

import model.Memory;
import model.WebInfo;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    Memory memory = new Memory();
    public LinkedList<WebInfo> getWebsites(){
        return memory.getWebsites();
    }

    public void addWebsite(String websiteName){
        WebInfo website = new WebInfo(websiteName, null);
        memory.addWebsite(website);
    }
    public void setResult(int index, String result){
        memory.addResult(index, result);
    }

    public void removeWebsite(WebInfo website){
        memory.deleteWebsite(website);
    }

    public void saveToFile(File file) throws IOException {
        memory.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException,ClassNotFoundException{
        memory.loadFromFile(file);
    }

    public void exportResult(File file) throws IOException{
        memory.exportResult(file);
    }




}
