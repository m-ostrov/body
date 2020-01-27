package elements;

import common.Helper;
import common.HttpClient;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.autoconfigure.http.HttpProperties;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tag {
    private String type;
    private List<Attribute> attributes;
    public String code;
    public String id;
    public String[] types;
    public Document document;

    private static final String HOME = System.getProperty("user.dir");


    public Tag(String type, List<Attribute> attributes){
        attributes = new ArrayList<>();
    }

    public Tag(){

        attributes = new ArrayList<>();
    }

    public Tag(String type){
        this.type = type;
        attributes = new ArrayList<>();
    }

    public String getTagString(){
        if(code == null)
        code =  "<" + type + getAttributes() + "></" + type + ">";
        return code;
    }

    private String getAttributes() {
        String result = "";
        for(Attribute a: attributes){
            result = result + a.getAttributeString() + " ";
        }
        return result;
    }

    public void addAttribute(String key, String value){
        attributes.add(new Attribute(key,value));
    }

    public void populateFromTemplate(String path) throws IOException {
        code = FileUtils.readFileToString(new File(path),"UTF-8");
        document = Jsoup.parse(code);
    }

    public void populateFromTemplate(String path,String id,String[] types) throws IOException {
        code = FileUtils.readFileToString(new File(path),"UTF-8");
        this.id = id;
        this.types = types;
        document = Jsoup.parse(code);
        populateForm();
    }

    private void populateForm(){
        Element element = document.selectFirst("*[data-id='" + type + "']");
        Document childDocument = null;
        String child = "";
        try {
            child = FileUtils.readFileToString(new File(Helper.getHomeDir(new String[]{"template",id + ".txt"})),"UTF-8");
            childDocument = Jsoup.parse(child);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String el : types){

            try {
                String text = getInnerText(el);
                Element elementToPopulate = childDocument.selectFirst("*[data-element='" + el + "']");
                String att = elementToPopulate.attr("data-attribute");
                if(att.isEmpty()){
                    elementToPopulate.append(text);
                }else{
                    element.attr(att,getAttribute(text));
                }

                element.appendChild(elementToPopulate);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        code = element.outerHtml();
        document = Jsoup.parse(code);
    }

    private String getAttribute(String txt) {
        return "http://localhost:3001/image?id=" + txt;
    }

    private String getInnerText(String datatype) throws IOException {
        String result = "";
        String uri = "http://localhost:8084/content?type=" + id + "&element=" + datatype + "&id=" + type;
        try {
            result = ((JSONObject)HttpClient.sendGet(uri)).getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
