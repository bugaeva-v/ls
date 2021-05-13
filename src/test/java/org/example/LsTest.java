package org.example;


import org.junit.Test;
import org.junit.Assert;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LsTest {

    private static void assertFileContent( String name, String expectedContent) {
        try {
            List<String> lines;
            lines = Files.readAllLines(Paths.get(name), StandardCharsets.UTF_8);
            StringBuilder content = new StringBuilder();
            for (String line: lines) {
                content.append(line).append("\n");
            }
            Assert.assertEquals(expectedContent, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void l() {
        Assert.assertTrue(true);
    }

    @Test
    public void h() {
        Assert.assertTrue(true);
    }

    @Test
    public void none() {
        Assert.assertTrue(true);
    }
}
