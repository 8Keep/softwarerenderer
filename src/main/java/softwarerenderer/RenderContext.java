package softwarerenderer;

public class RenderContext extends Buffer2d {

    public RenderContext(int width, int height) {
        super(width, height);
    }

    public void fillTriangle(Vertex v1, Vertex v2, Vertex v3) {

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
                minYVert.triangleAreaTimesTwo(maxYVert, midYVert) >= 0);
    }

    public void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness) {
        Edge topToBottom = new Edge(minYVert, maxYVert);
        Edge topToMiddle = new Edge(minYVert, midYVert);
        Edge middleToBottom = new Edge(midYVert, maxYVert);

        scanEdges(topToBottom, topToMiddle, handedness);
        scanEdges(topToBottom, middleToBottom, handedness);
    }

    private void scanEdges(Edge a, Edge b, boolean handedness) {

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
            drawScanLine(left, right, j);
            left.step();
            right.step();
        }
    }

    private void drawScanLine(Edge left, Edge right, int j) {
        int xMin = (int) Math.ceil(left.getX());
        int xMax = (int) Math.ceil(right.getX());

        for (int i = xMin; i < xMax; i++) {
            drawPixel(i, j, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF);

        }
    }
}
