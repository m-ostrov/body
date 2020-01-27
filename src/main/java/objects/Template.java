package objects;

import common.Helper;
import common.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class Template{

    private String id;
    private String sub;
    public Template(String id){
        this.id = id;
    }

    public Template(String id, String sub){
        this.id = id;
        this.sub = sub;
    }

    public Element getElement(){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("universe","template");
        parameters.put("id",id);
        Document document = null;
        Element element = null;
        String finId = id;
        if(sub!=null){
            parameters.put("sub",sub);
            finId = sub;
        }
        String url = Helper.constructUrl("template",parameters);
        String result = "Unable to find template";
        try {
            result = HttpClient.sendGet(url).getString("message");
            String res = result
                    .replaceAll("0101quote0101","\\\"")
                    ;
            document = Jsoup.parse(res
                     );
            element = document.selectFirst("*[data-id='" + finId + "']");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }
}
