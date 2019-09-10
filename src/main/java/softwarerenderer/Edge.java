package softwarerenderer;

public class Edge {

    private float x;
    private float xStep;
    private int yStart;
    private int yEnd;
    private float texCoordX;
    private float texCoordXStep;
    private float texCoordY;
    private float texCoordYStep;

    public float getX() {
        return x;
    }

    public int getYStart() {
        return yStart;
    }

    public int getYEnd() {
        return yEnd;
    }

    public float getTexCoordX() {
        return texCoordX;
    }

    public float getTexCoordY() {
        return texCoordY;
    }

    public Edge(TriangleData triangleData, Vertex minYVert, Vertex maxYVert, int minYVertIndex) {
        yStart = (int) Math.ceil(minYVert.getY());
        yEnd = (int) Math.ceil(maxYVert.getY());

        float yDist = maxYVert.getY() - minYVert.getY();
        float xDist = maxYVert.getX() - minYVert.getX();

        float yPrestep = yStart - minYVert.getY();
        xStep = (float) xDist / (float) yDist;
        x = minYVert.getX() + yPrestep * xStep;

        float xPrestep = x - minYVert.getX();

        texCoordX = triangleData.getTexCoordX(minYVertIndex) +
                triangleData.getTexCoordXXStep() * xPrestep +
                triangleData.getTexCoordXYStep() * yPrestep;
        texCoordXStep = triangleData.getTexCoordXYStep() + triangleData.getTexCoordXXStep() * xStep;

        texCoordY = triangleData.getTexCoordY(minYVertIndex) +
                triangleData.getTexCoordYXStep() * xPrestep +
                triangleData.getTexCoordYYStep() * yPrestep;
        texCoordYStep = triangleData.getTexCoordYYStep() + triangleData.getTexCoordYXStep() * xStep;
    }

    public void step() {
        x += xStep;

        texCoordX += texCoordXStep;
        texCoordY += texCoordYStep;
    }
}
