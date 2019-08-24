package softwarerenderer;

public class Application {

    protected Display display;
    protected RenderContext frameBuffer;
    int fps;
    int targetFPS = 60;
    boolean running;

    public Application() {
        fps = 0;
    }

    public void start() {
        running = true;

        display = new Display(1200, 1000);
        frameBuffer = display.getFrameBuffer();

        init();

        long lastLoopTime = System.nanoTime();
        long optimalTime = 1000000000 / targetFPS;
        int fps = 0;
        long lastFpsTime = 0;

        long now;
        long updateLength;
        double delta;

        display.updateFPS(fps);

        while (running)
        {
            now = System.nanoTime();
            updateLength = now - lastLoopTime;
            lastLoopTime = now;
            delta = updateLength / ((double)optimalTime);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000)
            {
                display.updateFPS(fps);
                lastFpsTime = 0;
                fps = 0;
            }

            update(delta);
            draw(delta);
            display.swapBuffers();

            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + optimalTime)/1000000 );
            }
            catch(Exception e) {}
        }
    }

    public void init() {

    }

    public void update(double delta) {

    }

    public void draw(double delta) {

    }
}
