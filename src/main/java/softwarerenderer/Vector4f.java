package softwarerenderer;

public class Vector4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f() {
        x = y = z = w = 0;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public float lengthSquared() {
        return (float) (x * x + y * y + z * z + w * w);
    }

    public float max() {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }

    public float dot(Vector4f r) {
        return x * r.x + y * r.y + z * r.z + w * r.w;
    }

    public Vector4f cross(Vector4f r) {
        float x_ = y * r.z - z * r.y;
        float y_ = z * r.x - x * r.z;
        float z_ = x * r.y - y * r.x;

        return new Vector4f(x_, y_, z_, 0);
    }

    public Vector4f normalized() {
        float length = length();

        return new Vector4f(x / length, y / length, z / length, w / length);
    }

    public Vector4f rotate(Vector4f axis, float angle) {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);

        return this.cross(axis.mult(sinAngle)).add(           //Rotation on local X
                (this.mult(cosAngle)).add(                     //Rotation on local Z
                        axis.mult(this.dot(axis.mult(1 - cosAngle))))); //Rotation on local Y
    }

    public Vector4f lerp(Vector4f dest, float lerpFactor) {
        return dest.subtract(this).mult(lerpFactor).add(this);
    }

    public Vector4f add(Vector4f r) {
        return new Vector4f(x + r.x, y + r.y, z + r.z, w + r.w);
    }

    public Vector4f add(float r) {
        return new Vector4f(x + r, y + r, z + r, w + r);
    }

    public Vector4f subtract(Vector4f r) {
        return new Vector4f(x - r.x, y - r.y, z - r.z, w - r.w);
    }

    public Vector4f subtract(float r) {
        return new Vector4f(x - r, y - r, z - r, w - r);
    }

    public Vector4f mult(Vector4f r) {
        return new Vector4f(x * r.x, y * r.y, z * r.z, w * r.w);
    }

    public Vector4f mult(float r) {
        return new Vector4f(x * r, y * r, z * r, w * r);
    }

    public Vector4f divide(Vector4f r) {
        return new Vector4f(x / r.x, y / r.y, z / r.z, w / r.w);
    }

    public Vector4f divide(float r) {
        return new Vector4f(x / r, y / r, z / r, w / r);
    }

    public Vector4f abs() {
        return new Vector4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public boolean equals(Vector4f r) {
        return x == r.x && y == r.y && z == r.z && w == r.w;
    }
}