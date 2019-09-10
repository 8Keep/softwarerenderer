package softwarerenderer;

import java.util.Arrays;

public class Buffer2d {

    public static final int A_MASK = 255 << 24;
    public static final int R_MASK = 255 << 16;
    public static final int G_MASK = 255 << 8;
    public static final int B_MASK = 255;

    final int width;
    final int height;
    final int pixels[];

    public Buffer2d(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public int getPixel(int x, int y) {
        return pixels[(y * getWidth()) + x];
    }

    public void setPixel(int x, int y, int pixel) {
        pixels[(y * getWidth()) + x] = pixel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void clear(int a, int r, int g, int b) {
        Arrays.fill(pixels, componentsToInt(a, r, g, b));
    }

    public int componentsToInt(int a, int r, int g, int b) {
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    public void copyPixel(Buffer2d src, int srcX, int srcY, int destX, int destY)
    {
        setPixel(destX, destY, src.getPixel(srcX, srcY));
    }

    public void drawPixel(int x, int y, int a, int r, int g, int b) {
        int index = (y * getWidth()) + x;
        if (index < 0 || index >= pixels.length)
            return;
        pixels[(y * getWidth()) + x] = componentsToInt(a, r, g, b);
    }

    public void toByteArray(byte[] dest) {
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            dest[i * 3] = (byte) (pixels[i] & B_MASK);
            dest[i * 3 + 1] = (byte) ((pixels[i] & G_MASK) >> 8);
            dest[i * 3 + 2] = (byte) ((pixels[i] & R_MASK) >> 16);
        }
    }
}
