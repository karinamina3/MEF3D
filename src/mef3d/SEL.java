package mef3d;

import mef3d.classes.Condition;
import mef3d.classes.Element;
import mef3d.classes.Mesh;
import mef3d.classes.Node;
import mef3d.classes.enums.parameters;
import mef3d.classes.enums.sizes;

import java.util.*;

public class SEL {

    public static void showMatrix(ArrayList<ArrayList<Float>> K) {
        System.out.println(K.get(0).size());
        System.out.println(K.size());

        for (int i = 0; i < K.get(0).size(); i++) {
            System.out.print("[\t");
            for (int j = 0; j < K.size(); j++) {
                System.out.print(K.get(i).get(j) + "\t");
            }
            System.out.print("]\n");
        }
    }

    public static void showKs(ArrayList<ArrayList<ArrayList<Float>>> Ks) {
        for (int i = 0; i < Ks.size(); i++) {
            System.out.println("K del elemento " + (i + 1) + ":");
            showMatrix(Ks.get(i));
            System.out.println("*************************************");
        }
    }

    public static void showVector(ArrayList<Float> b) {
        System.out.print("[\t");
        for (Float aFloat : b) {
            System.out.print(aFloat + "\t");
        }
        System.out.print("]\n");
    }

    public static void showbs(ArrayList<ArrayList<Float>> bs) {
        for (int i = 0; i < bs.size(); i++) {
            System.out.println("b del elemento " + (i + 1) + ":");
            showVector(bs.get(i));
            System.out.print("*************************************\n");
        }
    }

    public static float calculateLocalD(int ind, Mesh m) {
        Node n1 = m.getNode(m.getElement(ind).getNode1() - 1);
        Node n2 = m.getNode(m.getElement(ind).getNode2() - 1);
        Node n3 = m.getNode(m.getElement(ind).getNode3() - 1);
        Node n4 = m.getNode(m.getElement(ind).getNode4() - 1);

        float a = n2.getX() - n1.getX();
        float  b = n2.getY() - n1.getY();
        float c = n2.getZ() - n1.getZ();
        float d = n3.getX() - n1.getX();
        float e = n3.getY() - n1.getY();
        float f = n3.getZ() - n1.getZ();
        float g = n4.getX() - n1.getX();
        float h = n4.getY() - n1.getY();
        float i = n4.getZ() - n1.getZ();
        //Se calcula el determinante de esta matriz utilizando
        //la Regla de Sarrus.

        return a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i;
    }

    public static float calculateLocalVolume(int ind, Mesh m) {
        //Se utiliza la siguiente fórmula:
        //      Dados los 4 puntos vértices del tetrahedro A, B, C, D.
        //      Nos anclamos en A y calculamos los 3 vectores:
        //              V1 = B - A
        //              V2 = C - A
        //              V3 = D - A
        //      Luego el volumen es:
        //              V = (1/6)*det(  [ V1' ; V2' ; V3' ]  )

        float V, a, b, c, d, e, f, g, h, i;
        Element el = m.getElement(ind);
        Node n1 = m.getNode(el.getNode1() - 1);
        Node n2 = m.getNode(el.getNode2() - 1);
        Node n3 = m.getNode(el.getNode3() - 1);
        Node n4 = m.getNode(el.getNode4() - 1);

        a = n2.getX() - n1.getX();
        b = n2.getY() - n1.getY();
        c = n2.getZ() - n1.getZ();
        d = n3.getX() - n1.getX();
        e = n3.getY() - n1.getY();
        f = n3.getZ() - n1.getZ();
        g = n4.getX() - n1.getX();
        h = n4.getY() - n1.getY();
        i = n4.getZ() - n1.getZ();
        //Para el determinante se usa la Regla de Sarrus.
        V = (1.0f / 6.0f) * (a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i);

        return V;
    }

    public static float ab_ij(float ai, float aj, float a1, float bi, float bj, float b1) {
        return (ai - a1) * (bj - b1) - (aj - a1) * (bi - b1);
    }

    public static void calculateLocalA(int i, ArrayList<ArrayList<Float>> A, Mesh m) {
        Element e = m.getElement(i);
        Node n1 = m.getNode(e.getNode1() - 1);
        Node n2 = m.getNode(e.getNode2() - 1);
        Node n3 = m.getNode(e.getNode3() - 1);
        Node n4 = m.getNode(e.getNode4() - 1);

        A.get(0).set(0, ab_ij(n3.getY(), n4.getY(), n1.getY(), n3.getZ(), n4.getZ(), n1.getZ()));
        A.get(0).set(1, ab_ij(n4.getY(), n2.getY(), n1.getY(), n4.getZ(), n2.getZ(), n1.getZ()));
        A.get(0).set(2, ab_ij(n2.getY(), n3.getY(), n1.getY(), n2.getZ(), n3.getZ(), n1.getZ()));
        A.get(1).set(0, ab_ij(n4.getX(), n3.getX(), n1.getX(), n4.getZ(), n3.getZ(), n1.getZ()));
        A.get(1).set(1, ab_ij(n2.getX(), n4.getX(), n1.getX(), n2.getZ(), n4.getZ(), n1.getZ()));
        A.get(1).set(2, ab_ij(n3.getX(), n2.getX(), n1.getX(), n3.getZ(), n2.getZ(), n1.getZ()));
        A.get(2).set(0, ab_ij(n3.getX(), n4.getX(), n1.getX(), n3.getY(), n4.getY(), n1.getY()));
        A.get(2).set(1, ab_ij(n4.getX(), n2.getX(), n1.getX(), n4.getY(), n2.getY(), n1.getY()));
        A.get(2).set(2, ab_ij(n2.getX(), n3.getX(), n1.getX(), n2.getY(), n3.getY(), n1.getY()));
    }

    public static void calculateB(ArrayList<ArrayList<Float>> B) {
        B.get(0).set(0, -1.0f);
        B.get(0).set(1, 1.0f);
        B.get(0).set(2, 0.0f);
        B.get(0).set(3, 0.0f);

        B.get(1).set(0, -1.0f);
        B.get(1).set(1, 0.0f);
        B.get(1).set(2, 1.0f);
        B.get(1).set(3, 0.0f);

        B.get(2).set(0, -1.0f);
        B.get(2).set(1, 0.0f);
        B.get(2).set(2, 0.0f);
        B.get(2).set(3, 1.0f);
    }

    public static ArrayList<ArrayList<Float>> createLocalK(int Element, Mesh m) {
        // K = (k*Ve/D^2)Bt*At*A*B := K_4x4
        float D, Ve, k = m.getParameter(parameters.THERMAL_CONDUCTIVITY);
        ArrayList<ArrayList<Float>> K, A, B, Bt, At;

        K = new ArrayList<>();
        A = new ArrayList<>();
        B = new ArrayList<>();
        Bt = new ArrayList<>();
        At = new ArrayList<>();

        D = calculateLocalD(Element, m);
        Ve = calculateLocalVolume(Element, m);

        MathTools.zeroesAux(A, 3);
        MathTools.zeroes(B, 3, 4);
        calculateLocalA(Element, A, m);
        calculateB(B);
        MathTools.transpose(A, At);
        MathTools.transpose(B, Bt);

        MathTools.productRealMatrix(k * Ve / (D * D), MathTools.productMatrixMatrix(Bt, MathTools.productMatrixMatrix(At, MathTools.productMatrixMatrix(A, B, 3, 3, 4), 3, 3, 4), 4, 3, 4), K);

        return K;
    }

    public static float calculateLocalJ(int ind, Mesh m) {
        float J, a, b, c, d, e, f, g, h, i;

        Element el = m.getElement(ind);

        Node n1 = m.getNode(el.getNode1() - 1);
        Node n2 = m.getNode(el.getNode2() - 1);
        Node n3 = m.getNode(el.getNode3() - 1);
        Node n4 = m.getNode(el.getNode4() - 1);

        a = n2.getX() - n1.getX();
        b = n3.getX() - n1.getX();
        c = n4.getX() - n1.getX();
        d = n2.getY() - n1.getY();
        e = n3.getY() - n1.getY();
        f = n4.getY() - n1.getY();
        g = n2.getZ() - n1.getZ();
        h = n3.getZ() - n1.getZ();
        i = n4.getZ() - n1.getZ();
        //Se calcula el determinante de esta matriz utilizando
        //la Regla de Sarrus.
        J = a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i;

        return J;
    }

    public static ArrayList<Float> createLocalb(int element, Mesh m) {
        ArrayList<Float> b = new ArrayList<>();

        float Q = m.getParameter(parameters.HEAT_SOURCE), J, b_i;
        J = calculateLocalJ(element, m);

        b_i = Q * J / 24.0f;
        b.add(b_i);
        b.add(b_i);
        b.add(b_i);
        b.add(b_i);

        return b;
    }

    public static void crearSistemasLocales(Mesh m, ArrayList<ArrayList<ArrayList<Float>>> localKs, ArrayList<ArrayList<Float>> localbs) {
        System.out.println(m.getSize(sizes.ELEMENTS));

        for (int i = 0; i < m.getSize(sizes.ELEMENTS); i++) {
            localKs.add(createLocalK(i, m));
            localbs.add(createLocalb(i, m));
        }
        System.out.println("Local KS antes" + localKs);
    }

    public static void assemblyK(Element e, ArrayList<ArrayList<Float>> localK, ArrayList<ArrayList<Float>> K) {
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;
        int index4 = e.getNode4() - 1;

        K.get(index1).set(index1, K.get(index1).get(index1) + localK.get(0).get(0));
        K.get(index1).set(index2, K.get(index1).get(index2) + localK.get(0).get(1));
        K.get(index1).set(index3, K.get(index1).get(index3) + localK.get(0).get(2));
        K.get(index1).set(index4, K.get(index1).get(index4) + localK.get(0).get(3));
        K.get(index2).set(index1, K.get(index2).get(index1) + localK.get(1).get(0));
        K.get(index2).set(index2, K.get(index2).get(index2) + localK.get(1).get(1));
        K.get(index2).set(index3, K.get(index2).get(index3) + localK.get(1).get(2));
        K.get(index2).set(index4, K.get(index2).get(index4) + localK.get(1).get(3));
        K.get(index3).set(index1, K.get(index3).get(index1) + localK.get(2).get(0));
        K.get(index3).set(index2, K.get(index3).get(index2) + localK.get(2).get(1));
        K.get(index3).set(index3, K.get(index3).get(index3) + localK.get(2).get(2));
        K.get(index3).set(index4, K.get(index3).get(index4) + localK.get(2).get(3));
        K.get(index4).set(index1, K.get(index4).get(index1) + localK.get(3).get(0));
        K.get(index4).set(index2, K.get(index4).get(index2) + localK.get(3).get(1));
        K.get(index4).set(index3, K.get(index4).get(index3) + localK.get(3).get(2));
        K.get(index4).set(index4, K.get(index4).get(index4) + localK.get(3).get(3));
    }

    public static void assemblyb(Element e, ArrayList<Float> localb, ArrayList<Float> b) {
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;
        int index4 = e.getNode4() - 1;

        b.set(index1, b.get(index1) + localb.get(0));
        b.set(index2, b.get(index2) + localb.get(1));
        b.set(index3, b.get(index3) + localb.get(2));
        b.set(index4, b.get(index4) + localb.get(3));
    }

    public static void ensamblaje(Mesh m, ArrayList<ArrayList<ArrayList<Float>>> localKs, ArrayList<ArrayList<Float>> localbs, ArrayList<ArrayList<Float>> K, ArrayList<Float> b) {
        for (int i = 0; i < m.getSize(sizes.ELEMENTS); i++) {
            Element e = m.getElement(i);
            assemblyK(e, localKs.get(i), K);
            assemblyb(e, localbs.get(i), b);
        }
    }

    public static void applyNeumann(Mesh m, ArrayList<Float> b) {
        for (int i = 0; i < m.getSize(sizes.NEUMANN); i++) {
            Condition c = m.getCondition(i, sizes.NEUMANN);
            b.set(c.getNode1() - 1, b.get(c.getNode1() - 1) + c.getValue());
        }
    }

    public static void applyDirichlet(Mesh m, ArrayList<ArrayList<Float>> K, ArrayList<Float> b) {
        for (int i = 0; i < m.getSize(sizes.DIRICHLET); i++) {
            Condition c = m.getCondition(i, sizes.DIRICHLET);
            int index = c.getNode1() - 1;

            K.remove(index);
            b.remove(index);

            for (int row = 0; row < K.size(); row++) {
                float cell = K.get(row).get(index);
                K.get(row).remove(index);
                b.set(row, b.get(row) + (-1 * c.getValue() * cell));
            }
        }
    }

    public static void calculate(ArrayList<ArrayList<Float>> K, ArrayList<Float> b, ArrayList<Float> T) {
        System.out.println("Iniciando calculo de respuesta...");
        ArrayList<ArrayList<Float>> Kinv = new ArrayList<>();
        System.out.println("Calculo de inversa...");
        MathTools.inverseMatrix(K, Kinv);
        System.out.println("Calculo de respuesta...");
        MathTools.productMatrixVector(Kinv, b, T);
    }
}
