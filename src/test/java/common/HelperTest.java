package common;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void getUrlSection() {
        String url = "http://localhost:8001/item1";
        Assert.assertTrue("item1".equals(Helper.getUrlSection(url)));

    }
}