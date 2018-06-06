package ocrenu.main.model.ocr;

import jandcode.dbm.*;
import jandcode.dbm.data.*;
import jandcode.lang.*;
import net.sourceforge.tess4j.*;
import ocrenu.utils.dbfilestorage.*;

import javax.imageio.*;
import java.awt.image.*;

public class Resolver {

    Model model;

    public Resolver(Model model) {
        this.model = model;
    }

    public void resolve(DataRecord record) {
        long fileId = record.getValueLong("fileStorage");

        if(fileId==0){
            return;
        }

        DbFileStorageService fstorage = model.service(DbFileStorageService.class);

        BufferedImage img = null;
        try {
            img = ImageIO.read(fstorage.getFile(fileId).getFile());
            Normallizzer normallizzer = new Normallizzer(img);
            BufferedImage nImg = normallizzer.normollize();
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(model.getApp().getAppdir());
            String lane = tesseract.doOCR(nImg).replaceAll("'","").replaceAll("|","");
            for (Lang lang : model.getDblangService().getLangs()) {
                record.setValue("description_"+lang.getName(),lane);
            }
        } catch (Exception e) {
            record.setValue("description",e.getMessage());
        }
    }
}
