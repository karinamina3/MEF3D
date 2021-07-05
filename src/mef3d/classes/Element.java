package mef3d.classes;

public class Element extends classes.Item {
    @Override
    public void setValues(int a, float b, float c, float d, int e, int f, int g, int h, float i) {
        super.setValues(a, b, c, d, e, f, g, h, i);
        setId(a);
        setNode1(e);
        setNode2(f);
        setNode3(g);
        setNode4(h);
    }
}
