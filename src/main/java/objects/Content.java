package objects;

import common.Helper;
import common.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class Content {
    private String view;
    private String section;
    private String element;

    public Content(String view, String section,String element){
        this.view = view;
        this.section = section;
        this.element = element;
    }

    public String getContent() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("universe","content");
        parameters.put("view",view);
        parameters.put("section",section);
        parameters.put("element",element);
        String url = Helper.constructUrl("content",parameters);
        String result = "unable to get content: ";
        try {
            result = HttpClient.sendGet(url).getString("message");
        } catch (Exception e) {
           result = result + e.getMessage();
        }
        return result;
    }

    public String getMenuContent() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("universe","content");
        parameters.put("view",view);
        parameters.put("section",section);
        parameters.put("element",element);
        String url = Helper.constructUrl("content",parameters);
        String result = "unable to get content: ";
        try {
            result = HttpClient.sendGet(url + "&menu=true").getString("message");
        } catch (Exception e) {
            result = result + e.getMessage();
        }
        return result;
    }
}
