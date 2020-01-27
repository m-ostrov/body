package objects;

import common.Helper;
import common.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class Sections {
    private String id;
    public Sections(String id){
        this.id = id;
    }

    public String[] getSections(){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("view",id);
        parameters.put("universe","list-sections");
        String url = Helper.constructUrl("list-sections",parameters);
        String[] result = new String[0];
        try {
            result = HttpClient.sendGet(url).getString("message").split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
