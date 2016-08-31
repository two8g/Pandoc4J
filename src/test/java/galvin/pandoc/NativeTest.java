package galvin.pandoc;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by two8g on 16-8-30.
 */
public class NativeTest {
    @Test
    public void nativeTable2Docx(){
        File source = new File("/home/two8g/Develop/IdeaProjects/Pandoc4J/src/test/resources/tables.native");
        File out = new File("/home/two8g/Develop/IdeaProjects/Pandoc4J/src/test/resources/tables.docx");
        File template = new File("/home/two8g/Develop/IdeaProjects/Pandoc4J/src/test/resources/tables-ok.docx");
        Options options = new Options();
        options.setFrom(Format.format_native);
        options.setTo(Format.docx);
        options.setReferenceDOCX(template);
        try {
            new Pandoc(new File("/usr/bin/pandoc")).render(options, source,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
