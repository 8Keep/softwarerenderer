package softwarerenderer;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Display extends Canvas {

    final JFrame frame;
    final RenderContext frameBuffer;
    final BufferedImage displayImage;
    final BufferStrategy bufferStrategy;
    final byte[] displayBuffer;
    final Graphics graphics;
    String title;
    int fps;

    public Display(int width, int height) {

        height/=2;
        width/=2;
        frameBuffer = new RenderContext(width, height);
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        displayBuffer = ((DataBufferByte)displayImage.getRaster().getDataBuffer()).getData();

        Dimension size = new Dimension(width*2, height*2);
        setPreferredSize(size);
        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("");

        createBufferStrategy(1);
        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
        frame.setVisible(true);
    }

    public RenderContext getFrameBuffer() {
        return frameBuffer;
    }

    public void setTitle(String title) {
        this.title = title;
        updateTitle();
    }

    void updateTitle() {
        frame.setTitle(title + " FPS: " + fps);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
        updateTitle();
    }

    public void swapBuffers() {
        frameBuffer.toByteArray(displayBuffer);
        graphics.drawImage(displayImage, 0, 0, frameBuffer.width*2, frameBuffer.height*2, null);
        bufferStrategy.show();
    }
}
