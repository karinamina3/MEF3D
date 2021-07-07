package mef3d.classes;

public class Element extends mef3d.classes.Item {
    @Override
    public void setValues(int a, float b, float c, float d, int e, int f, int g, int h, int i, int j, int k, int l, int m, int n, float o) {
        super.setValues(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o);
        setId(a);
        setNode1(e);
        setNode2(f);
        setNode3(g);
        setNode4(h);
        setNode5(i);
        setNode6(j);
        setNode7(k);
        setNode8(l);
        setNode9(m);
        setNode10(n);

    }
}
