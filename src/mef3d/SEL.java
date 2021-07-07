package mef3d;

import mef3d.classes.Condition;
import mef3d.classes.Element;
import mef3d.classes.Mesh;
import mef3d.classes.Node;
import mef3d.classes.enums.parameters;
import mef3d.classes.enums.sizes;

import java.lang.reflect.Array;
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

    //declaraciones de c1

    public static float createC1 (int ind, Mesh m){
        Element el = m.getElement(ind);
        el= m.getElement(ind);
        Node n1 = m.getNode(el.getNode1()-1);
        Node n2 = m.getNode(el.getNode2()-1);

        return (float) ((1f)/Math.pow((n2.getX()-n1.getX()),2));

    }
    public static float createC2 (int ind, Mesh m){
        Element el = new Element();
        el = m.getElement(ind);
        System.out.println(el);

        Node n1 = m.getNode(el.getNode1()-1);
        Node n2 = m.getNode(el.getNode2()-1);
        Node n8 = m.getNode(el.getNode8()-1);


        return ((1f)/(n2.getX() - n1.getX())) *((4*n1.getX())+(4*n2.getX())-(8*n8.getX()));

    }

    public static void calculateLocalU(float c1, float c2, ArrayList<ArrayList<Float>> U){

        float A,B,C,D,E,F,G,H,I,J,K;


        A= (float) ((float) ((float) ((float) (-(1f)/(192*Math.pow(c2,2)))*Math.pow(4*c1-c2,4))-((1f)/(24*c2))*Math.pow(4*c1-c2,3))-
                ((1f)/(3840*Math.pow(c2,3)))*Math.pow(4*c1-c2,5)+((1f)/(3840*Math.pow(c2,3)))*Math.pow(4*c1-3*c2,5));

        B= (float) ((float) ((float) ((float) (-(1f)/(192*Math.pow(c2,2)))*Math.pow(4*c1+c2,4))+((1f)/(24*c2))*Math.pow(4*c1+c2,3))+
                ((1f)/(3840*Math.pow(c2,3)))*Math.pow(4*c1+c2,5)-((1f)/(3840*Math.pow(c2,3)))*Math.pow(4*c1-3*c2,5));

        C= (float) ((4/15)*Math.pow(c2,2));

        D= (float) ((float) ((float) ((float) ((float) ((float) ((float) ((float) ((float) ((1f)/(192*Math.pow(c2,2)))*Math.pow(4*c2-c1,4))-((1f)/(3840*Math.pow(c2,3)))*Math.pow(4*c2-c1,5))+
                ((1f)/(7680*Math.pow(c2,3)))*Math.pow(4*c2+8*c1,5)-((7f)/(7680*Math.pow(c2,3)))*Math.pow(4*c2-8*c1,5))+ ((1f)/(768*Math.pow(c2,3)))*Math.pow(-8*c1,5))-
                (c1)/(96*Math.pow(c2,3)))*Math.pow((4*c2)-8*c1,4))+((2*c1-1)/192*Math.pow(c2,3)))*Math.pow(-8*c1,4));

        E = (float) ((float) ((8/3)*Math.pow(c1,2))+ (1/30)*Math.pow(c2,2));

        F= (float) (((2/3)*(c1*c2)) - (1/30)*Math.pow(c2,2));

        G = (float) ((-16/3)*(Math.pow(c1,2)) - (4/3)*(c1*c2) - (2/15)*Math.pow(c2,2)) ;

        H = (float) (((2/3)*(c1*c2)) + (1/30)*Math.pow(c2,2));

        I =  (float) ((float) (-(16/3)*Math.pow(c1,2))-(2/3)*Math.pow(c2,2));

        J = (float) (- (2/15)*Math.pow(c2,2)) ;

        K =  -(4/3)*(c1*c2);

        U.get(0).set(0, A);
        U.get(0).set(1, E);
        U.get(0).set(2, 0f);
        U.get(0).set(3, 0f);
        U.get(0).set(4, -F);
        U.get(0).set(5, 0f);
        U.get(0).set(6, -F);
        U.get(0).set(7, G);
        U.get(0).set(8, F);
        U.get(0).set(9, F);

        U.get(1).set(0, E);
        U.get(1).set(1, B);
        U.get(1).set(2, 0f);
        U.get(1).set(3, 0f);
        U.get(1).set(4, -H);
        U.get(1).set(5, 0f);
        U.get(1).set(6, -H);
        U.get(1).set(7, I);
        U.get(1).set(8, H);
        U.get(1).set(9, H);

        U.get(2).set(0, 0f);
        U.get(2).set(1, 0f);
        U.get(2).set(2, 0f);
        U.get(2).set(3, 0f);
        U.get(2).set(4, 0f);
        U.get(2).set(5, 0f);
        U.get(2).set(6, 0f);
        U.get(2).set(7, 0f);
        U.get(2).set(8, 0f);
        U.get(2).set(9, 0f);

        U.get(3).set(0, 0f);
        U.get(3).set(1, 0f);
        U.get(3).set(2, 0f);
        U.get(3).set(3, 0f);
        U.get(3).set(4, 0f);
        U.get(3).set(5, 0f);
        U.get(3).set(6, 0f);
        U.get(3).set(7, 0f);
        U.get(3).set(8, 0f);
        U.get(3).set(9, 0f);

        U.get(4).set(0, -F);
        U.get(4).set(1, -H);
        U.get(4).set(2, 0f);
        U.get(4).set(3, 0f);
        U.get(4).set(4, C);
        U.get(4).set(5, 0f);
        U.get(4).set(6, J);
        U.get(4).set(7, -K);
        U.get(4).set(8, -C);
        U.get(4).set(9, -J);

        U.get(5).set(0, 0f);
        U.get(5).set(1, 0f);
        U.get(5).set(2, 0f);
        U.get(5).set(3, 0f);
        U.get(5).set(4, 0f);
        U.get(5).set(5, 0f);
        U.get(5).set(6, 0f);
        U.get(5).set(7, 0f);
        U.get(5).set(8, 0f);
        U.get(5).set(9, 0f);

        U.get(6).set(0, -F);
        U.get(6).set(1, -H);
        U.get(6).set(2, 0f);
        U.get(6).set(3, 0f);
        U.get(6).set(4, J);
        U.get(6).set(5, 0f);
        U.get(6).set(6, C);
        U.get(6).set(7, -K);
        U.get(6).set(8, -J);
        U.get(6).set(9, -C);

        U.get(7).set(0, G);
        U.get(7).set(1, I);
        U.get(7).set(2, 0f);
        U.get(7).set(3, 0f);
        U.get(7).set(4, -K);
        U.get(7).set(5, 0f);
        U.get(7).set(6, -K);
        U.get(7).set(7, D);
        U.get(7).set(8, K);
        U.get(7).set(9, K);

        U.get(8).set(0, F);
        U.get(8).set(1, H);
        U.get(8).set(2, 0f);
        U.get(8).set(3, 0f);
        U.get(8).set(4, -C);
        U.get(8).set(5, 0f);
        U.get(8).set(6, -J);
        U.get(8).set(7, K);
        U.get(8).set(8, C);
        U.get(8).set(9, J);

        U.get(9).set(0, F);
        U.get(9).set(1, H);
        U.get(9).set(2, 0f);
        U.get(9).set(3, 0f);
        U.get(9).set(4, -J);
        U.get(9).set(5, 0f);
        U.get(9).set(6, -C);
        U.get(9).set(7, K);
        U.get(9).set(8, J);
        U.get(9).set(9, C);
    }

    private static void calculatelocalT(ArrayList<ArrayList<Float>> MatrixT){
        MatrixT.get(0).set(0, 59f);
        MatrixT.get(1).set(0, -1f);
        MatrixT.get(2).set(0, -1f);
        MatrixT.get(3).set(0, -1f);
        MatrixT.get(4).set(0, 4f);
        MatrixT.get(5).set(0, 4f);
        MatrixT.get(6).set(0, 4f);
        MatrixT.get(7).set(0, 4f);
        MatrixT.get(8).set(0, 4f);
        MatrixT.get(9).set(0, 4f);
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

        ArrayList<ArrayList<Float>> K;

        ArrayList<ArrayList<Float>> MatrixU = new ArrayList<>();

        MathTools.zeroesAux(MatrixU, 10);

        calculateLocalU(createC1(Element, m), createC2(Element, m), MatrixU);

        float J = calculateLocalJ(Element, m);

        K = new ArrayList<>();

        MathTools.zeroesAux(K, 30);

        for(int i = 0; i < 30;i++){
            for(int j = 0; j < 30; j++){
                K.get(i).set(j, (183f * J * MatrixU.get(i).get(j)));
                K.get(10+i).set(j+10, (183f*J*MatrixU.get(i).get(j)));
                K.get(20+i).set(j+20, (183f*J*MatrixU.get(i).get(j)));
            }
        }

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
        ArrayList<ArrayList<Float>> MatrixT = new ArrayList<>();

        MathTools.zeroes(MatrixT, 30, 1);

        MatrixT.get(0).set(0, 59f);
        MatrixT.get(1).set(0, -1f);
        MatrixT.get(2).set(0, -1f);
        MatrixT.get(3).set(0, -1f);
        MatrixT.get(4).set(0, 4f);
        MatrixT.get(5).set(0, 4f);
        MatrixT.get(6).set(0, 4f);
        MatrixT.get(7).set(0, 4f);
        MatrixT.get(8).set(0, 4f);
        MatrixT.get(9).set(0, 4f);
        
        float J = calculateLocalJ(element, m);
        ArrayList<ArrayList<Float>> MatrixMA = new ArrayList<>();
        MathTools.zeroes(MatrixMA, 30, 1);

        ArrayList<Float> F = new ArrayList<>();

        F.add(0, 50f);
        F.add(0, 0f);
        F.add(0, 49f);

        for (int i =0; i<30;i++){
            MatrixMA.get(i).set(0, (J/120f*MatrixT.get(i).get(0)));
            MatrixMA.get(10+i).set(1, (J/120f*MatrixT.get(i).get(0)));
            MatrixMA.get(20+i).set(2, (J/120f*MatrixT.get(i).get(0)));
        }

        ArrayList<Float> b = new ArrayList<>();
        MathTools.zeroes(b, 30);

        MathTools.productMatrixVector(MatrixMA, F, b);

        return b;
    }

    public static void crearSistemasLocales(Mesh m, ArrayList<ArrayList<ArrayList<Float>>> localKs, ArrayList<ArrayList<Float>> localbs) {

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

    public static void calculate(ArrayList<ArrayList<Float>> K, ArrayList<Float> b, ArrayList<Float> MatrixT) {
        System.out.println("Iniciando calculo de respuesta...");
        ArrayList<ArrayList<Float>> Kinv = new ArrayList<>();
        System.out.println("Calculo de inversa...");
        MathTools.inverseMatrix(K, Kinv);
        System.out.println("Calculo de respuesta...");
        MathTools.productMatrixVector(Kinv, b, MatrixT);
    }
}
