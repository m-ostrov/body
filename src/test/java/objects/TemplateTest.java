package objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {

    @Test
    void getElement() {
        Template template = new Template("about");
        Element element = template.getElement();
        Assert.assertTrue("Element should not be null",element!=null);
        Template template1 = new Template("about","sub-about");
        Element element1 = template1.getElement();
        Assert.assertTrue("Sub-element should not be null", element1!=null);
    }
}