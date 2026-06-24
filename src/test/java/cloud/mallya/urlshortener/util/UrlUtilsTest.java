package cloud.mallya.urlshortener.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UrlUtilsTest {

    @Autowired
    private UrlUtils urlUtils;

    @Test
    void test_idValid() {
        assertFalse(urlUtils.isValid("spring boot diaries"));
        assertFalse(urlUtils.isValid("httpspring boot diaries"));
        assertTrue(urlUtils.isValid("http://facebook.com"));
        assertTrue(urlUtils.isValid("https://facebook.com"));
        assertFalse(urlUtils.isValid("htt://"));
        assertFalse(urlUtils.isValid(null));
    }
}
