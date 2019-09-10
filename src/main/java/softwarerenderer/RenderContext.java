package softwarerenderer;

public class RenderContext extends Buffer2d {

    public RenderContext(int width, int height) {
        super(width, height);
    }

    public void fillTriangle(Vertex v1, Vertex v2, Vertex v3, Buffer2d texture) {

        Matrix4f screenspaceTransform =
                new Matrix4f().initScreenSpaceTransform(getWidth() / 2, getHeight() / 2);

        Vertex minYVert = v1.transform(screenspaceTransform).perspectiveDivide();
        Vertex midYVert = v2.transform(screenspaceTransform).perspectiveDivide();
        Vertex maxYVert = v3.transform(screenspaceTransform).perspectiveDivide();

        if (maxYVert.getY() < midYVert.getY()) {
            Vertex tmp = maxYVert;
            maxYVert = midYVert;
            midYVert = tmp;
        }
        if (midYVert.getY() < minYVert.getY()) {
            Vertex tmp = midYVert;
            midYVert = minYVert;
            minYVert = tmp;
        }
        if (maxYVert.getY() < midYVert.getY()) {
            Vertex tmp = maxYVert;
            maxYVert = midYVert;
            midYVert = tmp;
        }

        float area = minYVert.triangleAreaTimesTwo(maxYVert, midYVert);
        int handedness = area >= 0 ? 1 : 0;


        scanTriangle(minYVert, midYVert, maxYVert,
                minYVert.triangleAreaTimesTwo(maxYVert, midYVert) >= 0,
                texture);
    }

    public void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness, Buffer2d texture) {
        TriangleData triangleData = new TriangleData(minYVert, midYVert, maxYVert);
        Edge topToBottom = new Edge(triangleData, minYVert, maxYVert, 0);
        Edge topToMiddle = new Edge(triangleData, minYVert, midYVert, 0);
        Edge middleToBottom = new Edge(triangleData, midYVert, maxYVert, 1);

        scanEdges(topToBottom, topToMiddle, handedness, texture);
        scanEdges(topToBottom, middleToBottom, handedness, texture);
    }

    private void scanEdges(Edge a, Edge b, boolean handedness, Buffer2d texture) {

        Edge left = a;
        Edge right = b;
        if (handedness) {
            Edge temp = left;
            left = right;
            right = temp;
        }

        int yStart = b.getYStart();
        int yEnd = b.getYEnd();
        for (int j = yStart; j < yEnd; j++) {
            drawScanLine(left, right, j, texture);
            left.step();
            right.step();
        }
    }

    private void drawScanLine(Edge left, Edge right, int j, Buffer2d texture) {
        int xMin = (int) Math.ceil(left.getX());
        int xMax = (int) Math.ceil(right.getX());

        float xPrestep = xMin - left.getX();

        float xDist = right.getX() - left.getX();
        float texCoordXXStep = (right.getTexCoordX() - left.getTexCoordX())/xDist;
        float texCoordYXStep = (right.getTexCoordY() - left.getTexCoordY())/xDist;
        float oneOverZXStep = (right.getOneOverZ() - left.getOneOverZ())/xDist;

        float texCoordX = left.getTexCoordX() + texCoordXXStep * xPrestep;
        float texCoordY = left.getTexCoordY() + texCoordYXStep * xPrestep;
        float oneOverZ = left.getOneOverZ() + oneOverZXStep * xPrestep;

        for (int i = xMin; i < xMax; i++) {

            float z = 1.0f/oneOverZ;
            int srcX = (int)((texCoordX * z) * (float)(texture.getWidth() - 1) + 0.5f);
            int srcY = (int)((texCoordY * z) * (float)(texture.getHeight() - 1) + 0.5f);

            copyPixel(texture, srcX, srcY, i, j);
            oneOverZ += oneOverZXStep;
            texCoordX += texCoordXXStep;
            texCoordY += texCoordYXStep;
        }
    }
}
