package Model;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Memory{
    private LinkedList<WebInfo> websites;
    public Memory(){
        websites = new LinkedList<WebInfo>();
    }
    public void addWebsite(WebInfo website){
        websites.add(website);

    }
    public void deleteWebsite(WebInfo website){
        websites.remove(website);
    }
    public void addResult(int index, String result){
        websites.get(index).setResult(result);

    }
    public void deleteResult(int i){
        websites.get(i).setResult(null);

    }

    public LinkedList<WebInfo> getWebsites(){
        return websites;
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        WebInfo[] webInfos= websites.toArray(new WebInfo[websites.size()]);

        oos.writeObject(webInfos);


        oos.close();

    }

    public void exportResult(File file) throws IOException{
        PrintWriter writer = new PrintWriter(file, "UTF-8");

        for(WebInfo website : websites){
            writer.println(website.getWebsite()+","+website.getResult());
        }
        writer.close();
    }

    public void loadFromFile(File file) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        WebInfo[] webInfos= (WebInfo[])ois.readObject();
        websites.clear();
        websites.addAll(Arrays.asList(webInfos));

        ois.close();
    }


}
