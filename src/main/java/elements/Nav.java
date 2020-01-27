package elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class Nav extends Tag {
    public Nav() {
        super("nav");
        populate();
    }

    private void populate() {
        this.addAttribute("class","navbar navbar-style");
    }



    public void addMenu(String[] menu){
        try {
            populateFromTemplate(System.getProperty("user.dir") + File.separator + "template" + File.separator + "nav.txt");
            Document nav = Jsoup.parse(code);
            Element ul = nav.selectFirst("ul");
            Element el = ul.selectFirst("li");
            int i = 1;
            for(String item:menu){
                Element temp = el.clone();
                temp.attr("id","item" + i);
                Element a = temp.selectFirst("a");
                a.attr("href","/" + "item" + i);
                a.append(item);
                ul.appendChild(temp);
                i++;
            }
            el.remove();
            code = nav.selectFirst("nav").outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
