package softwarerenderer;

import java.util.Vector;

public class Vertex {
    Vector4f pos;
    Vector4f color;
    Vector4f texCoords;

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public Vector4f getPosition() {
        return pos;
    }

    public Vector4f getColor() {
        return color;
    }

    public Vector4f getTexCoords() {
        return texCoords;
    }

    public Vertex(float x, float y, float z) {
        pos = new Vector4f(x, y, z, 1);
    }

    public Vertex(Vector4f pos, Vector4f texCoords) {
        this.pos = pos;
        this.texCoords = texCoords;
    }

    public Vertex(Vector4f pos, Vector4f color, Vector4f texCoords) {
        this.pos = pos;
        this.color = color;
        this.texCoords = texCoords;
    }

    public Vertex transform(Matrix4f matrix) {
        return new Vertex(matrix.transform(pos), color, texCoords);
    }

    public Vertex perspectiveDivide() {
        return new Vertex(new Vector4f(pos.x / pos.w,
                pos.y / pos.w,
                pos.z / pos.w,
                pos.w),
                color,
                texCoords);

    }

    public float triangleAreaTimesTwo(Vertex b, Vertex c) {
        float x1 = b.getX() - pos.x;
        float y1 = b.getY() - pos.y;

        float x2 = c.getX() - pos.x;
        float y2 = c.getY() - pos.y;

        return (x1 * y2) - (x2 * y1);
    }
}