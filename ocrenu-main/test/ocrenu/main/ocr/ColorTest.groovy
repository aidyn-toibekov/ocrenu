package ocrenu.main.ocr

import ocrenu.main.model.ocr.*
import org.joda.time.*
import org.junit.*

import javax.imageio.*
import java.awt.*
import java.awt.image.*

class ColorTest {

    @Test
    public void name() {
        BufferedImage img = ImageIO.read(new File("E:\\temp\\ment.png"));

        Normallizzer normallizzer = new Normallizzer(img);
        println("ment")
        Image nImg = normallizzer.normollize();

        File rFile = new File("E:\\temp\\ment_" + DateTime.now().toString("yyyyMMddHHss") + ".png");
        ImageIO.write(nImg, "png", rFile);
        img = ImageIO.read(new File("E:\\temp\\num.png"));
        normallizzer = new Normallizzer(img);

        println("num")
        nImg = normallizzer.normollize();
        rFile = new File("E:\\temp\\num_" + DateTime.now().toString("yyyyMMddHHss") + ".png");
        ImageIO.write(nImg, "png", rFile);

    }
}
