package softwarerenderer;

public class TestApplication extends Application {

    Matrix4f projection;
    float rotCounter;

    Vertex minYVert;
    Vertex midYVert;
    Vertex maxYVert;

    public TestApplication() {

    }

    @Override
    public void init() {

        display.setTitle("Software Renderer V0.1");

        minYVert = new Vertex(-1, -1, 0);
        midYVert = new Vertex(0, 1, 0);
        maxYVert = new Vertex(1 , -1, 0);
        projection = new Matrix4f().initPerspective(
                                            (float)Math.toRadians(70.0f),
                                  (float)frameBuffer.getWidth() / (float)frameBuffer.getHeight(),
                                      0.1f,
                                       1000.0f);

        rotCounter = 0.0f;
    }

    @Override
    public void update(double delta) {

        //frameBuffer.fillTriangle(minYVert, maxYVert, midYVert);
        frameBuffer.clear(0, 0, 0, 0);

        rotCounter += delta / 30;
        Matrix4f translation = new Matrix4f().initTranslation(0.0f, 0.0f, 3.0f);
        Matrix4f rotation = new Matrix4f().initRotation(0.0f, rotCounter, 0.0f);
        Matrix4f transform = projection.mult(translation.mult(rotation));

        frameBuffer.fillTriangle(
                maxYVert.transform(transform),
                midYVert.transform(transform), minYVert.transform(transform));
    }

    @Override
    public void draw(double delta) {

    }
}
