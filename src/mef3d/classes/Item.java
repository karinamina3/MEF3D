package classes;

public class Item {

    protected int id;
    protected float x;
    protected float y;
    protected float z;
    protected int node1;
    protected int node2;
    protected int node3;
    protected int node4;
    protected float value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getNode1() {
        return node1;
    }

    public void setNode1(int node1) {
        this.node1 = node1;
    }

    public int getNode2() {
        return node2;
    }

    public void setNode2(int node2) {
        this.node2 = node2;
    }

    public int getNode3() {
        return node3;
    }

    public void setNode3(int node3) {
        this.node3 = node3;
    }

    public int getNode4() {
        return node4;
    }

    public void setNode4(int node4) {
        this.node4 = node4;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    // TODO: equivalent of virtual functions in Java
    // virtual void setValues(int a,float b,float c,float d,int e,int f,int g, int h, float i)=0;

}
