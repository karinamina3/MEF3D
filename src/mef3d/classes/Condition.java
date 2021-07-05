package mef3d.classes;

public class Condition extends classes.Item {

    @Override
    public void setValues(int a, float b, float c, float d, int e, int f, int g, int h, float i) {
        super.setValues(a, b, c, d, e, f, g, h, i);
        setNode1(e);
        setValue(i);
    }
}
