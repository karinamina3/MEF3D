package mef3d.classes;

public class Condition extends mef3d.classes.Item {

    @Override
    public void setValues(int a, float b, float c, float d, int e, int f, int g, int h, int i, int j, int k, int l, int m, int n, float o) {
        super.setValues(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o);
        setNode1(e);
        setValue(i);
    }
}
