package softwarerenderer;

public class TestApplication extends Application {

    Matrix4f projection;
    float rotCounter;

    Vertex minYVert;
    Vertex midYVert;
    Vertex maxYVert;

    Buffer2d texture;

    public TestApplication() {

    }

    @Override
    public void init() {

        display.setTitle("Software Renderer V0.1");

        texture = new Buffer2d(32, 32);
        for (int j = 0; j < texture.getHeight(); j++) {
            for (int i = 0; i < texture.getWidth(); i++) {
                texture.drawPixel(i, j,
                        (byte) (Math.random() * 255.0 + 0.5),
                        (byte) (Math.random() * 255.0 + 0.5),
                        (byte) (Math.random() * 255.0 + 0.5),
                        (byte) (Math.random() * 255.0 + 0.5));
            }
        }

        minYVert = new Vertex(new Vector4f(-1, -1, 0, 1),
                new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
        midYVert = new Vertex(new Vector4f(0, 1, 0, 1),
                new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
        maxYVert = new Vertex(new Vector4f(1, -1, 0, 1),
                new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));

        projection = new Matrix4f().initPerspective(
                (float) Math.toRadians(70.0f),
                (float) frameBuffer.getWidth() / (float) frameBuffer.getHeight(),
                0.1f,
                1000.0f);

        rotCounter = 0.0f;
    }

    @Override
    public void update(double delta) {

        frameBuffer.clear(0, 0, 0, 0);

        rotCounter += delta / 30;
        Matrix4f translation = new Matrix4f().initTranslation(0.0f, 0.0f, 3.0f);
        Matrix4f rotation = new Matrix4f().initRotation(rotCounter, rotCounter, rotCounter);
        Matrix4f transform = projection.mult(translation.mult(rotation));

        frameBuffer.fillTriangle(
                maxYVert.transform(transform),
                midYVert.transform(transform),
                minYVert.transform(transform),
                texture);
    }

    @Override
    public void draw(double delta) {

    }
}
