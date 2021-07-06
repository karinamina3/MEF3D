package mef3d;

import java.util.*;

public class MathTools {

    public static void zeroes(ArrayList<ArrayList<Float>> M, int n, int m) {
        for (int i = 0; i < n; i++) {
            ArrayList<Float> row = new ArrayList<>(m);
            for (int j = 0; j < m; j++){
                row.add(0.0F);
            }
            M.add(row);
        }
    }

    public static void zeroesAux(ArrayList<ArrayList<Float>> M, int n) {
        for (int i = 0; i < n; i++) {
            ArrayList<Float> row = new ArrayList<Float>(n);
            for (int j = 0; j < n; j++) {
                row.add(0.0F);
            }
            M.add(row);
        }
    }

    public static void zeroes(ArrayList<Float> v, int n) {
        for (int i = 0; i < n; i++) {
            v.add(0.0F);
        }
    }

    public static void copyMatrix(ArrayList<ArrayList<Float>> A, ArrayList<ArrayList<Float>> copy) {
        zeroesAux(copy, A.size());
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < A.get(0).size(); j++) {
                copy.get(i).set(j, A.get(i).get(j));
            }
        }
    }

    public static float calculateMember(int i, int j, int r, ArrayList<ArrayList<Float>> A, ArrayList<ArrayList<Float>> B) {
        float member = 0F;
        for (int k = 0; k < r; k++) {
            member += A.get(i).get(k) * B.get(k).get(j);
        }
        return member;
    }

    public static ArrayList<ArrayList<Float>> productMatrixMatrix(ArrayList<ArrayList<Float>> A, ArrayList<ArrayList<Float>> B, int n, int r, int m) {
        ArrayList<ArrayList<Float>> R = new ArrayList<ArrayList<Float>>();

        zeroes(R, n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                R.get(i).set(j, calculateMember(i, j, r, new ArrayList<ArrayList<Float>>(A), new ArrayList<ArrayList<Float>>(B)));
            }
        }

        return new ArrayList<ArrayList<Float>>(R);
    }

    public static void productMatrixVector(ArrayList<ArrayList<Float>> A, ArrayList<Float> v, ArrayList<Float> R) {
        for (int f = 0; f < A.size(); f++) {
            float cell = 0.0F;
            for (int c = 0; c < v.size(); c++) {
                cell += A.get(f).get(c) * v.get(c);
            }
            R.set(f, R.get(f) + cell);
        }
    }

    public static void productRealMatrix(float real, ArrayList<ArrayList<Float>> M, ArrayList<ArrayList<Float>> R) {
        zeroesAux(R, M.size());
        for (int i = 0; i < M.size(); i++) {
            for (int j = 0; j < M.get(0).size(); j++) {
                R.get(i).set(j, real * M.get(i).get(j));
            }
        }
    }

    public static void getMinor(ArrayList<ArrayList<Float>> M, int i, int j) {
        //System.out.print("Calculando menor ("<<i+1<<","<<j+1<<")...\n");
        M.remove(i);
        int mSize = M.size();

        for (int index = 0; index < mSize; index++) {
            M.get(index).remove(j);
        }
    }

    public static float determinant(ArrayList<ArrayList<Float>> M) {
        if (M.size() == 1) {
            return M.get(0).get(0);
        } else {
            float det = 0.0F;
            for (int i = 0; i < M.get(0).size(); i++) {
                ArrayList<ArrayList<Float>> minor = new ArrayList<ArrayList<Float>>();
                copyMatrix(new ArrayList<ArrayList<Float>>(M), minor);
                getMinor(minor, 0, i);
                det += Math.pow(-1, i) * M.get(0).get(i) * determinant(new ArrayList<ArrayList<Float>>(minor));
            }
            return det;
        }
    }

    public static void cofactors(ArrayList<ArrayList<Float>> M, ArrayList<ArrayList<Float>> Cof) {
        zeroesAux(Cof, M.size());
        for (int i = 0; i < M.size(); i++) {
            for (int j = 0; j < M.get(0).size(); j++) {
                //System.out.print("Calculando cofactor ("<<i+1<<","<<j+1<<")...\n");
                ArrayList<ArrayList<Float>> minor = new ArrayList<ArrayList<Float>>();
                copyMatrix(new ArrayList<ArrayList<Float>>(M), minor);
                getMinor(minor, i, j);
                Cof.get(i).set(j, (float) (Math.pow(-1, i + j) * determinant(new ArrayList<ArrayList<Float>>(minor))));
            }
        }
    }

    public static void transpose(ArrayList<ArrayList<Float>> M, ArrayList<ArrayList<Float>> T) {
        zeroes(T, M.get(0).size(), M.size());
        for (int i = 0; i < M.size(); i++) {
            for (int j = 0; j < M.get(0).size(); j++) {
                T.get(j).set(i, M.get(i).get(j));
            }
        }
    }

    public static void inverseMatrix(ArrayList<ArrayList<Float>> M, ArrayList<ArrayList<Float>> Minv) {
        System.out.print("Iniciando calculo de inversa...\n");
        ArrayList<ArrayList<Float>> Cof = new ArrayList<ArrayList<Float>>();
        ArrayList<ArrayList<Float>> Adj = new ArrayList<ArrayList<Float>>();
        System.out.print("Calculo de determinante...\n");
        float det = determinant(new ArrayList<ArrayList<Float>>(M));
        if (det == 0F) {
            System.exit(1);
        }
        System.out.print("Iniciando calculo de cofactores...\n");
        cofactors(new ArrayList<ArrayList<Float>>(M), Cof);
        System.out.print("Calculo de adjunta...\n");
        transpose(new ArrayList<ArrayList<Float>>(Cof), Adj);
        System.out.print("Calculo de inversa...\n");
        productRealMatrix(1 / det, new ArrayList<ArrayList<Float>>(Adj), Minv);
    }

}
