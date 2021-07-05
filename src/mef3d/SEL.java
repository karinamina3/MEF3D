package mef3d;

import java.util.*;

public class SEL {

    public static void showMatrix(Matrix K) {
        // TODO: Matrix
        for (int i = 0; i < K.get(0).size(); i++) {
            System.out.print("[\t");
            for (int j = 0; j < K.size(); j++) {
                System.out.print(K.get(i).get(j) + "\t");
            }
            System.out.print("]\n");
        }
    }

    public static void showKs(vector<Matrix> Ks) {
        // TODO: Matrix, ¿vector?
        for (int i = 0; i < Ks.size(); i++) {
            System.out.println("K del elemento " + (i + 1) + ":");
            showMatrix(Ks.get(i));
            System.out.println("*************************************");
        }
    }

    public static void showVector(Vector b) {
        // TODO: Vector
        System.out.print("[\t");
        for (int i = 0; i < b.size(); i++) {
            System.out.print(b.get(i) + "\t");
        }
        System.out.print("]\n");
    }

    public static void showbs(vector<Vector> bs) {
        // TODO: Vector, ¿vector?
        for (int i = 0; i < bs.size(); i++) {
            System.out.println("b del elemento " + (i + 1) + ":");
            showVector(bs.geti));
            System.out.print("*************************************\n");
        }
    }

    float calculateLocalD(int ind, mesh m) {
        float D, a, b, c, d, e, f, g, h, i;

        element el = m.getElement(ind);

        node n1 = m.getNode(el.getNode1() - 1);
        node n2 = m.getNode(el.getNode2() - 1);
        node n3 = m.getNode(el.getNode3() - 1);
        node n4 = m.getNode(el.getNode4() - 1);

        a = n2.getX() - n1.getX();
        b = n2.getY() - n1.getY();
        c = n2.getZ() - n1.getZ();
        d = n3.getX() - n1.getX();
        e = n3.getY() - n1.getY();
        f = n3.getZ() - n1.getZ();
        g = n4.getX() - n1.getX();
        h = n4.getY() - n1.getY();
        i = n4.getZ() - n1.getZ();
        //Se calcula el determinante de esta matriz utilizando
        //la Regla de Sarrus.
        D = a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i;

        return D;
    }

    float calculateLocalVolume(int ind, mesh m) {
        //Se utiliza la siguiente fórmula:
        //      Dados los 4 puntos vértices del tetrahedro A, B, C, D.
        //      Nos anclamos en A y calculamos los 3 vectores:
        //              V1 = B - A
        //              V2 = C - A
        //              V3 = D - A
        //      Luego el volumen es:
        //              V = (1/6)*det(  [ V1' ; V2' ; V3' ]  )

        float V, a, b, c, d, e, f, g, h, i;
        element el = m.getElement(ind);
        node n1 = m.getNode(el.getNode1() - 1);
        node n2 = m.getNode(el.getNode2() - 1);
        node n3 = m.getNode(el.getNode3() - 1);
        node n4 = m.getNode(el.getNode4() - 1);

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
        V = (1.0 / 6.0) * (a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i);

        return V;
    }

    float ab_ij(float ai, float aj, float a1, float bi, float bj, float b1) {
        return (ai - a1) * (bj - b1) - (aj - a1) * (bi - b1);
    }

    public static void calculateLocalA(int i, Matrix &A, mesh m) {
        element e = m.getElement(i);
        node n1 = m.getNode(e.getNode1() - 1);
        node n2 = m.getNode(e.getNode2() - 1);
        node n3 = m.getNode(e.getNode3() - 1);
        node n4 = m.getNode(e.getNode4() - 1);

        A.get0).get0) =ab_ij(n3.getY(), n4.getY(), n1.getY(), n3.getZ(), n4.getZ(), n1.getZ());
        A.get0).get1) =ab_ij(n4.getY(), n2.getY(), n1.getY(), n4.getZ(), n2.getZ(), n1.getZ());
        A.get0).get2) =ab_ij(n2.getY(), n3.getY(), n1.getY(), n2.getZ(), n3.getZ(), n1.getZ());
        A.get1).get0) =ab_ij(n4.getX(), n3.getX(), n1.getX(), n4.getZ(), n3.getZ(), n1.getZ());
        A.get1).get1) =ab_ij(n2.getX(), n4.getX(), n1.getX(), n2.getZ(), n4.getZ(), n1.getZ());
        A.get1).get2) =ab_ij(n3.getX(), n2.getX(), n1.getX(), n3.getZ(), n2.getZ(), n1.getZ());
        A.get2).get0) =ab_ij(n3.getX(), n4.getX(), n1.getX(), n3.getY(), n4.getY(), n1.getY());
        A.get2).get1) =ab_ij(n4.getX(), n2.getX(), n1.getX(), n4.getY(), n2.getY(), n1.getY());
        A.get2).get2) =ab_ij(n2.getX(), n3.getX(), n1.getX(), n2.getY(), n3.getY(), n1.getY());
    }

    public static void calculateB(Matrix B) {
        // TODO: Matrix
        B.get(0).get(0) = -1;
        B.get(0).get(1) = 1;
        B.get(0).get(2) = 0;
        B.get(0).get(3) = 0;

        B.get(1).get(0) = -1;
        B.get(1).get(1) = 0;
        B.get(1).get(2) = 1;
        B.get(1).get(3) = 0;

        B.get(2).get(0) = -1;
        B.get(2).get(1) = 0;
        B.get(2).get(2) = 0;
        B.get(2).get(3) = 1;
    }

    public static Matrix createLocalK(int element, mesh m) {
        // TODO: Matrix, mesh
        // K = (k*Ve/D^2)Bt*At*A*B := K_4x4
        float D, Ve, k = m.getParameter(THERMAL_CONDUCTIVITY);
        Matrix K, A, B, Bt, At;

        D = calculateLocalD(element, m);
        Ve = calculateLocalVolume(element, m);

        MathTools.zeroes(A, 3);
        MathTools.zeroes(B, 3, 4);
        calculateLocalA(element, A, m);
        calculateB(B);
        MathTools.transpose(A, At);
        MathTools.transpose(B, Bt);

        MathTools.productRealMatrix(k * Ve / (D * D), MathTools.productMatrixMatrix(Bt, MathTools.productMatrixMatrix(At, MathTools.productMatrixMatrix(A, B, 3, 3, 4), 3, 3, 4), 4, 3, 4), K);

        return K;
    }

    float calculateLocalJ(int ind, mesh m) {
        float J, a, b, c, d, e, f, g, h, i;

        // TODO: mesh, element, node
        element el = m.getElement(ind);

        node n1 = m.getNode(el.getNode1() - 1);
        node n2 = m.getNode(el.getNode2() - 1);
        node n3 = m.getNode(el.getNode3() - 1);
        node n4 = m.getNode(el.getNode4() - 1);

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

    Vector createLocalb(int element, mesh &m) {
        Vector b;

        float Q = m.getParameter(HEAT_SOURCE), J, b_i;
        J = calculateLocalJ(element, m);

        b_i = Q * J / 24.0;
        b.push_back(b_i);
        b.push_back(b_i);
        b.push_back(b_i);
        b.push_back(b_i);

        return b;
    }

    public static void crearSistemasLocales(mesh &m, vector<Matrix> &localKs, vector<Vector> &localbs) {
        for (int i = 0; i < m.getSize(ELEMENTS); i++) {
            localKs.push_back(createLocalK(i, m));
            localbs.push_back(createLocalb(i, m));
        }
    }

    public static void assemblyK(element e, Matrix localK, Matrix &K) {
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;
        int index4 = e.getNode4() - 1;

        K.getindex1).getindex1) +=localK.get0).get0);
        K.getindex1).getindex2) +=localK.get0).get1);
        K.getindex1).getindex3) +=localK.get0).get2);
        K.getindex1).getindex4) +=localK.get0).get3);
        K.getindex2).getindex1) +=localK.get1).get0);
        K.getindex2).getindex2) +=localK.get1).get1);
        K.getindex2).getindex3) +=localK.get1).get2);
        K.getindex2).getindex4) +=localK.get1).get3);
        K.getindex3).getindex1) +=localK.get2).get0);
        K.getindex3).getindex2) +=localK.get2).get1);
        K.getindex3).getindex3) +=localK.get2).get2);
        K.getindex3).getindex4) +=localK.get2).get3);
        K.getindex4).getindex1) +=localK.get3).get0);
        K.getindex4).getindex2) +=localK.get3).get1);
        K.getindex4).getindex3) +=localK.get3).get2);
        K.getindex4).getindex4) +=localK.get3).get3);
    }

    public static void assemblyb(element e, Vector localb, Vector &b) {
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;
        int index4 = e.getNode4() - 1;

        b.getindex1) +=localb.get0);
        b.getindex2) +=localb.get1);
        b.getindex3) +=localb.get2);
        b.getindex4) +=localb.get3);
    }

    public static void ensamblaje(mesh &m, vector<Matrix> &localKs, vector<Vector> &localbs, Matrix &K, Vector &b) {
        for (int i = 0; i < m.getSize(ELEMENTS); i++) {
            element e = m.getElement(i);
            assemblyK(e, localKs.geti), K);
            assemblyb(e, localbs.geti), b);
        }
    }

    public static void applyNeumann(mesh &m, Vector &b) {
        for (int i = 0; i < m.getSize(NEUMANN); i++) {
            condition c = m.getCondition(i, NEUMANN);
            b.getc.getNode1() - 1) +=c.getValue();
        }
    }

    public static void applyDirichlet(mesh &m, Matrix &K, Vector &b) {
        for (int i = 0; i < m.getSize(DIRICHLET); i++) {
            condition c = m.getCondition(i, DIRICHLET);
            int index = c.getNode1() - 1;

            K.erase(K.begin() + index);
            b.erase(b.begin() + index);

            for (int row = 0; row < K.size(); row++) {
                float cell = K.getrow).getindex);
                K.getrow).erase(K.getrow).begin() + index);
                b.getrow) +=-1 * c.getValue() * cell;
            }
        }
    }

    public static void calculate(Matrix K, Vector b, Vector T) {
        System.out.println("Iniciando calculo de respuesta...");
        // TODO: Matrix
        Matrix Kinv;
        System.out.println("Calculo de inversa...");
        MathTools.inverseMatrix(K, Kinv);
        System.out.println("Calculo de respuesta...");
        MathTools.productMatrixVector(Kinv, b, T);
    }
}
