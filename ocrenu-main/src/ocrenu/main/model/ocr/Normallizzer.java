package ocrenu.main.model.ocr;

import java.awt.*;
import java.awt.image.*;

public class Normallizzer {

    BufferedImage buf_img;

    BufferedImage resultImg;

    int width;
    int height;

    int percent = 22;


    public Normallizzer(BufferedImage imgFile) throws Exception {
        this.buf_img = imgFile;
        width = buf_img.getWidth();
        height = buf_img.getHeight();
    }

    public BufferedImage normollize() {
        resultImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int cnt = 0;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                totalR += getColor(col, row).getRed();
                totalG += getColor(col, row).getGreen();
                totalB += getColor(col, row).getBlue();
                cnt++;
            }
        }

        double d = ((totalR + totalG + totalB) / cnt) / 3;

        if (d > 170) {
            lightImage();
        } else {
            darkImage();
        }


        return resultImg;
    }

    private void lightImage() {
        int minW, maxW, minH, maxH = 0;

        minW = width / percent;
        maxW = width - width / percent;

        minH = height / percent;
        maxH = height - height / percent;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (col < minW || col > maxW || row < minH || row > maxH) {
                    setColor(col, row, new Color(255, 255, 255));
                } else {
                    setColor(col, row, getColor(col, row));
                }
            }
        }
    }

    private void darkImage() {
        int minW, maxW, minH, maxH = 0;

        minW = width / percent;
        maxW = width - width / percent;

        minH = height / percent;
        maxH = height - height / percent;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (col < minW || col > maxW || row < minH || row > maxH) {
                    setColor(col, row, new Color(255, 255, 255));
                } else {
                    setColor(col, row, generateColorForDark(col, row));
                }
            }
        }
    }

    private Color generateColorForDark(int w, int h) {
        int minW = 0;
        int maxW = 0;
        int minH = 0;
        int maxH = 0;

        int step = 5;

        if ((w - step) > 0) {
            minW = w - step;
        }
        if ((w + step) < width) {
            maxW = w + step;
        }

        if ((h - step) > 0) {
            minH = h - step;
        }
        if ((h + step) < height) {
            maxH = h + step;
        }

        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int cnt = 0;

        for (int col = minW; col < maxW; col++) {
            for (int row = minH; row < maxH; row++) {
                totalR += getColor(col, row).getRed();
                totalG += getColor(col, row).getGreen();
                totalB += getColor(col, row).getBlue();
                cnt++;
            }
        }

        if (cnt == 0) {
            return new Color(255, 255, 255);
        } else {
            double d = (totalR + totalG + totalB) / cnt;
            if (d < 600) {
                return new Color(255, 255, 255);
            } else {
                return new Color(0, 0, 0);
            }
        }
    }

    private Color getColor(int col, int row) {
        Color color = new Color(buf_img.getRGB(col, row));
        return color;
    }

    private void setColor(int col, int row, Color color) {
        resultImg.setRGB(col, row, color.getRGB());
    }
}
