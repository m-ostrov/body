package objects;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTest {

    @Test
    void getContent() {
        Content content = new Content("about","greeting","header");
        String privet = content.getContent();
        Assert.assertTrue("Привет! was expected and found: " + privet,privet.equals("Привет!") );
    }
}