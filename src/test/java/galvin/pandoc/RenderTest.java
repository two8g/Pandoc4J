/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galvin.pandoc;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author galvint
 */
public class RenderTest {
    private static final String HEADING = "<h1>Lorem ipsum dolor sit amet</h1>\n\n";
    private static final String GREEKING = "Lorem ipsum dolor sit amet, consectetur "
            + "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore "
            + "magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco "
            + "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor "
            + "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla "
            + "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa "
            + "qui officia deserunt mollit anim id est laborum.\n\n";

    List<KeyValue<String, String>> values = new ArrayList<>();

    @Before
    public void setLaTeXVariable() {
        //values.add(new KeyValue<>("documentclass", "article"));
        values.add(new KeyValue<>("mainfont", "SimSun"));
        values.add(new KeyValue<>("papersize", "A4"));
        values.add(new KeyValue<>("fontsize", "10.5pt"));
        values.add(new KeyValue<>("title", "2016年中考真题化学（陕西卷）"));
        values.add(new KeyValue<>("dxh", "导学号:80690081"));
        values.add(new KeyValue<>("mathfont", "Times New Roman"));
        values.add(new KeyValue<>("geometry", "top=1.28cm, bottom=5.08cm, left=3.18cm, right=3.18cm"));
        values.add(new KeyValue<>("linestretch", "1.5"));
    }

    public RenderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFilesToFile() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        File output = new File("target/test-classes/file-to-file.html");

        Options options = new Options();
        options.setFrom(Format.markdown);
        options.setTo(Format.html5);

        Pandoc pandoc = new Pandoc(new File("/usr/local/bin/pandoc"));
        pandoc.render(options, source, output);

        String expectedHtml = FileUtils.readFileToString(expected);
        String actualHtml = FileUtils.readFileToString(output);
        assertEquals("Generated HTML did not match expected", expectedHtml, actualHtml);
    }

    @Test
    public void testStringToFile() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        File output = new File("target/test-classes/string-to-file.html");

        String expectedHtml = FileUtils.readFileToString(expected);
        String sourceText = FileUtils.readFileToString(source);

        Options options = new Options();
        options.setFrom(Format.markdown);
        options.setTo(Format.html5);

        Pandoc pandoc = new Pandoc(new File("/usr/local/bin/pandoc"));
        pandoc.render(options, sourceText, output);

        String actualHtml = pandoc.render(options, source);
        assertEquals("Generated HTML did not match expected", expectedHtml, actualHtml);
    }

    @Test
    public void testFilesToString() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");

        Options options = new Options();
        options.setFrom(Format.markdown);
        options.setTo(Format.html5);

        Pandoc pandoc = new Pandoc(new File("/usr/local/bin/pandoc"));
        String actualHtml = pandoc.render(options, source);
        String expectedHtml = FileUtils.readFileToString(expected);
        assertEquals("Generated HTML did not match expected", expectedHtml, actualHtml);
    }

    @Test
    public void testStringToString() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");

        String expectedHtml = FileUtils.readFileToString(expected);
        String sourceText = FileUtils.readFileToString(source);

        Options options = new Options();
        options.setFrom(Format.markdown);
        options.setTo(Format.html5);

        Pandoc pandoc = new Pandoc(new File("/usr/local/bin/pandoc"));
        String actualHtml = pandoc.render(options, sourceText);

        assertEquals("Generated HTML did not match expected", expectedHtml, actualHtml);
    }

    @Test
    public void largeFileTest() throws Exception {
        int size = HEADING.length() * 25 + GREEKING.length() * 2500;
        StringBuilder text = new StringBuilder(size);
        for (int i = 0; i < 25; i++) {
            text.append(HEADING);
            for (int j = 0; j < 100; j++) {
                text.append(GREEKING);
            }
        }

        Options options = new Options();
        options.setFrom(Format.markdown);
        options.setTo(Format.html5);
        options.setStandalone(Boolean.TRUE);

        Pandoc pandoc = new Pandoc(new File("/usr/local/bin/pandoc"));
        String actualHtml = pandoc.render(options, text.toString());
        System.out.println(actualHtml);
    }

    @Test
    public void testFile2Docx() {
        File source = new File("target/test-classes/test.txt");
        File output = new File("target/test-classes/file-to-docx.docx");

        Options options = new Options();
        options.setSmart(Boolean.TRUE);
        options.setFrom(Format.html);
        options.setTo(Format.docx);

        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFile2DocxWithTemplate() {
        File source = new File("target/test-classes/test.txt");
        File template = new File("target/test-classes/template.docx");
        File output = new File("target/test-classes/file-to-docx-template.docx");

        Options options = new Options();
        options.setSmart(Boolean.TRUE);
        options.setFrom(Format.html);
        options.setTo(Format.docx);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);
        //设置docx模板
        //options.setReferenceDOCX(template);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFile2DocxWithTemplate80690081() {
        File source = new File("target/test-classes/80690081.html");
        File template = new File("target/test-classes/docx.docx");
        File output = new File("target/test-classes/80690081.docx");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.docx);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);
        //设置docx模板
        options.setReferenceDOCX(template);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtml2PDF() {
        File source = new File("target/test-classes/80690081.html");
        File output = new File("target/test-classes/80690081.pdf");
        File template = new File("target/test-classes/pagestyle.tex");
        //File template = new File("target/test-classes/pm-template.tex");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.pdf);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);
        options.setLatexEngine(new File("/usr/bin/xelatex"));

        options.setVariables(values);
        //options.setTemplate(template);
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File texoutput = new File("target/test-classes/80690081.tex");
        options.setTo(Format.latex);
        try {
            pandoc.render(options, source, texoutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtml2Docx() {
        File source = new File("target/test-classes/80690081.html");
        File output = new File("target/test-classes/80690081.tex");
        File template = new File("target/test-classes/pagestyle.tex");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.latex);
        options.setSmart(Boolean.TRUE);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);
        options.setLatexEngine(new File("/usr/bin/xelatex"));

        options.setVariables(values);
        options.setTemplate(template);
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File docxoutput = new File("target/test-classes/80690081-tex.docx");
        Options options1 = new Options();
        options1.setFrom(Format.latex);
        options1.setTo(Format.docx);
        extensionList.remove(0);
        extensionList.add(Extension.auto_identifiers);
        options1.setExtensions(extensionList);
        File referenceDocx = new File("target/test-classes/paper.docx");
        options1.setReferenceDOCX(referenceDocx);
        try {
            pandoc.render(options1, output, docxoutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDocx2pdf() {
        File source = new File("target/test-classes/cbc37fc98ee14df082619f46e6367195.docx");
        File output = new File("target/test-classes/80690081-docx.pdf");
        File template = new File("target/test-classes/pagestyle.tex");
        //File template = new File("target/test-classes/pm-template.tex");

        Options options = new Options();
        options.setFrom(Format.docx);
        options.setTo(Format.pdf);
        options.setSmart(Boolean.TRUE);
        options.setLatexEngine(new File("/usr/bin/xelatex"));

        List<KeyValue<String, String>> values = new ArrayList<>();
        options.setVariables(values);
        options.setTemplate(template);
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File texoutput = new File("target/test-classes/80690081-docx.tex");
        options.setTo(Format.latex);
        try {
            pandoc.render(options, source, texoutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtml2LaTeX() {
        File source = new File("target/test-classes/80690081.html");
        File output = new File("target/test-classes/80690081.tex");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.latex);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtml2md() {
        File source = new File("target/test-classes/80690081.html");
        File output = new File("target/test-classes/80690081.md");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.markdown_phpextra);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        options.setExtensions(extensionList);
        try {
            options.setCss(new URL("https://gist.github.com/killercup/5917178#file-pandoc-css"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void md2docx() {
        File source = new File("target/test-classes/80690081.md");
        File template = new File("target/test-classes/template.docx");
        File output = new File("target/test-classes/80690081-md.docx");

        Options options = new Options();
        options.setTo(Format.docx);
        //设置docx模板
        options.setReferenceDOCX(template);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tex2docx() {
        File source = new File("target/test-classes/80690081.tex");
        File template = new File("target/test-classes/template.docx");
        File output = new File("target/test-classes/80690081-tex.docx");

        Options options = new Options();
        options.setFrom(Format.latex);
        options.setTo(Format.docx);
        //设置docx模板
        options.setReferenceDOCX(template);

        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tex2pdf() {
        File source = new File("target/test-classes/test1.tex");
        File output = new File("target/test-classes/test1.pdf");

        Options options = new Options();
        options.setFrom(Format.latex);
        options.setTo(Format.pdf);
        options.setLatexEngine(new File("/usr/bin/xelatex"));
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void html2odt() {
        File source = new File("target/test-classes/80690081.html");
        File output = new File("target/test-classes/80690081.odt");

        Options options = new Options();
        options.setFrom(Format.html);
        options.setTo(Format.odt);
        options.setSmart(Boolean.TRUE);
        List<Extension> extensionList = new ArrayList<>();
        extensionList.add(Extension.tex_math_dollars);
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void odt2docx() {
        File source = new File("target/test-classes/80690081.odt");
        File output = new File("target/test-classes/80690081.docx");

        Options options = new Options();
        options.setFrom(Format.odt);
        options.setTo(Format.docx);
        Pandoc pandoc = new Pandoc(new File("/usr/bin/pandoc"));
        try {
            pandoc.render(options, source, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
