package services;

import common.Helper;
import common.HttpClient;
import objects.Content;
import objects.Sections;
import objects.Template;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@EnableAutoConfiguration
@SpringBootApplication
@CrossOrigin
public class Body {

    private static final String HOME = System.getProperty("user.dir");
    private static final String SEPARATOR = File.separator;

    @RequestMapping("/")

    String home(HttpServletRequest request) throws IOException {

        String view = request.getParameter("view");

        Element rootElement = new Template(view).getElement();


        Elements ancors = rootElement.select("*[data-id='ancor']");

        for(Element ancor:ancors){
         //   Element toRemove = ancor.selectFirst("*[data-id='sub-" + view + "']");
            Element toRemove = ancor.selectFirst("*[data-id*='sub-']");
            Element row = ancor;

          //  Element subElement = new Template(view,"sub-" + view).getElement();
            String localView = toRemove.attr("data-id");
            String local = localView.replace("sub-","");
            Element subElement = new Template(local,localView).getElement();
            String type = subElement.attr("data-type");

            String[] sections = new Sections(local).getSections();

            for(String section : sections){
                Element elementToPopulate = new Template(type).getElement();
                elementToPopulate.attr("id",section);
                Elements elements = elementToPopulate.select("*[data-element]");
                Element column = subElement.clone();

                for(Element el:elements){

                    Content content = new Content(local,section,el.attr("data-element"));
                    String text = content.getContent();
                    String attribute = el.attr("data-attribute");
                    if(Helper.isThing(attribute)){
                        String prefix = Helper.getStringFromProperties(Helper
                                .getHomeDir(new String[]{"config","config.properties"}),"attribute." + attribute);
                        el.attr(attribute,prefix + "=" + text);
                    }else{
                        el.append(text);
                    }
                }
                column.appendChild(elementToPopulate);
                row.appendChild(column);
            }
            toRemove.remove();



        }

        Element sidebar = rootElement.selectFirst("*[data-id='sidebar']");
        if(sidebar!=null){
            String menu = "";
            try {
                String menuid = (view.equals("about")) ? "menu" : "side-" + view;

                menu = HttpClient.sendGet("http://localhost:8082/menu?view=" + menuid).getString("message");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sidebar.append(menu);
        }

        String root = rootElement.outerHtml().replaceAll("\"","'").replaceAll("\n","");

        return "{\"message\":\"" + root + "\"}";
    }

    @RequestMapping("/menu")

    String homeMenu(HttpServletRequest request) throws IOException {

        String view = request.getParameter("view");


        Element rootElement = new Template(view).getElement();
        Element toRemove = rootElement.selectFirst("*[data-id='sub-" + view + "']");
        Element row = rootElement.selectFirst("*[data-id='ancor']");
        Element subElement = new Template(view,"sub-" + view).getElement();
        String type = subElement.attr("data-type");
        Element sidebar = rootElement.selectFirst("*[data-id='sidebar']");
        if(sidebar!=null){
            String menu = "";
            try {
                String menuid = (view.equals("about")) ? "menu" : "side-" + view;

                menu = HttpClient.sendGet("http://localhost:8082?view=" + menuid).getString("message");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sidebar.append(menu);
        }

        String[] sections = new Sections(view).getSections();

        for(String section : sections){
            Element elementToPopulate = new Template(type).getElement();
            elementToPopulate.attr("id",section);
            Elements elements = elementToPopulate.select("*[data-element]");
            Element column = subElement.clone();

            for(Element el:elements){

                Content content = new Content(view,section,el.attr("data-element"));
                String text = content.getMenuContent();
                String attribute = el.attr("data-attribute");
                if(Helper.isThing(attribute)){
                    String prefix = Helper.getStringFromProperties(Helper
                            .getHomeDir(new String[]{"config","config.properties"}),"attribute." + attribute);
                    el.attr(attribute,prefix + "=" + text);
                }else{
                    el.append(text);
                }
            }
            column.appendChild(elementToPopulate);
            row.appendChild(column);
        }
        toRemove.remove();
        String root = rootElement.outerHtml().replaceAll("\"","'").replaceAll("\n","");


        return "{\"message\":\"" + root + "\"}";
    }




    public static void main(String[] arg){
        SpringApplication.run(Body.class,arg);
    }
}


