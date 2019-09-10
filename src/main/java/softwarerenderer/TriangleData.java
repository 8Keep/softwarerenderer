package softwarerenderer;

public class TriangleData {
    private float[] texCoordX;
    private float[] texCoordY;
    private float texCoordXXStep;
    private float texCoordXYStep;
    private float texCoordYXStep;
    private float texCoordYYStep;

    public float getTexCoordX(int loc) {
        return texCoordX[loc];
    }

    public float getTexCoordY(int loc) {
        return texCoordY[loc];
    }

    public float getTexCoordXXStep() {
        return texCoordXXStep;
    }

    public float getTexCoordXYStep() {
        return texCoordXYStep;
    }

    public float getTexCoordYXStep() {
        return texCoordYXStep;
    }

    public float getTexCoordYYStep() {
        return texCoordYYStep;
    }

    public TriangleData(Vertex minYVert, Vertex midYVert, Vertex maxYVert) {

        float oneOverdX = 1.0f /
                (((midYVert.getX() - maxYVert.getX()) *
                        (minYVert.getY() - maxYVert.getY())) -
                        ((minYVert.getX() - maxYVert.getX()) *
                                (midYVert.getY() - maxYVert.getY())));

        float oneOverdY = -oneOverdX;

        texCoordX = new float[3];
        texCoordY = new float[3];

        texCoordX[0] = minYVert.getTexCoords().x;
        texCoordX[1] = midYVert.getTexCoords().x;
        texCoordX[2] = maxYVert.getTexCoords().x;

        texCoordY[0] = minYVert.getTexCoords().y;
        texCoordY[1] = midYVert.getTexCoords().y;
        texCoordY[2] = maxYVert.getTexCoords().y;

        texCoordXXStep =
                (((texCoordX[1] - texCoordX[2]) *
                        (minYVert.getY() - maxYVert.getY())) -
                        ((texCoordX[0] - texCoordX[2]) *
                                (midYVert.getY() - maxYVert.getY()))) * oneOverdX;

        texCoordXYStep =
                (((texCoordX[1] - texCoordX[2]) *
                        (minYVert.getX() - maxYVert.getX())) -
                        ((texCoordX[0] - texCoordX[2]) *
                                (midYVert.getX() - maxYVert.getX()))) * oneOverdY;

        texCoordYXStep =
                (((texCoordY[1] - texCoordY[2]) *
                        (minYVert.getY() - maxYVert.getY())) -
                        ((texCoordY[0] - texCoordY[2]) *
                                (midYVert.getY() - maxYVert.getY()))) * oneOverdX;

        texCoordYYStep =
                (((texCoordY[1] - texCoordY[2]) *
                        (minYVert.getX() - maxYVert.getX())) -
                        ((texCoordY[0] - texCoordY[2]) *
                                (midYVert.getX() - maxYVert.getX()))) * oneOverdY;
    }
}