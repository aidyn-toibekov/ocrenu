package ocrenu.main.ocr;

import jandcode.dbm.test.*;
import net.sourceforge.tess4j.*;
import org.junit.*;

import java.io.*;

public class OcrTest extends DbmTestCase {

    @Test
    public void name() {
        File path = new File("E:\\temp");

        Tesseract tesseract = new Tesseract();


        for (File file : path.listFiles()) {
            System.out.println(file.getName());
            try {
                tesseract.setDatapath(dbm.getApp().getAppdir());
                String lane = tesseract.doOCR(file);
                System.out.println(lane);
            }catch (Exception e){

            }

        }
    }

    @Test
    public void path() {

        System.out.println(dbm.getApp().getAppdir());
    }
}
