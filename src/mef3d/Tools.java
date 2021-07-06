package mef3d;

import classes.Item;
import mef3d.classes.Condition;
import mef3d.classes.Element;
import mef3d.classes.Mesh;
import mef3d.classes.Node;
import mef3d.classes.enums.indicators;
import mef3d.classes.enums.lines;
import mef3d.classes.enums.modes;
import mef3d.classes.enums.sizes;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Tools {

    public static void obtenerDatosCoordenadas(Scanner sc, int n, ArrayList<Node> node_list) {
        for (int i = 0; i < n; i++){
            int e;
            float r, rr, rrr;

            e = sc.nextInt();
            r = sc.nextFloat();
            rr = sc.nextFloat();
            rrr = sc.nextFloat();

            node_list.get(i).setValues(
                    e,
                    r,
                    rr,
                    rrr,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING
            );
        }
    }

    public static void obtenerDatosElementos(Scanner sc, int n, ArrayList<Element> element_list) {
        for (int i = 0; i < n; i++){
            int e1, e2, e3, e4, e5;

            e1 = sc.nextInt();
            e2 = sc.nextInt();
            e3 = sc.nextInt();
            e4 = sc.nextInt();
            e5 = sc.nextInt();

            element_list.get(i).setValues(
                    e1,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    e2,
                    e3,
                    e4,
                    e5,
                    indicators.NOTHING
            );
        }
    }
    public static void obtenerDatosCondiciones(Scanner sc, int n, ArrayList<Condition> condition_list) {
        for (int i = 0; i < n; i++){
            int e0;
            float r0;

            e0 = sc.nextInt();
            r0 = sc.nextFloat();

            condition_list.get(i).setValues(
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    e0,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    r0
            );
        }
    }

    public static void correctConditions(int n, ArrayList<Condition> list, ArrayList<Integer> indices){
        for (int i = 0; i < n; i++)
            indices.set(i, list.get(i).getNode1());

        for (int i = 0; i < n-1; i++){
            int pivot = list.get(i).getNode1();
            for (int j = i; j < n; j++){
                // Si la condición actual corresponde a un nodo posterior al nodo eliminado por
                // aplicar la condición anterior, se debe actualizar su posición.
                if (list.get(j).getNode1() > pivot) list.get(j).setNode1(list.get(j).getNode1() - 1);
            }
        }
    }

    public static String addExtension(String filename, String extension){
        return filename + extension;
    }

    public static void leerMallayCondiciones(Mesh m, String filename) throws IOException {
        String inputFileName = addExtension(filename, ".dat");

        float k, Q;

        int nnodes, neltos, ndirich, nneu;

        File archivo = new File(inputFileName);
        Scanner sc = new Scanner(archivo);

        k = sc.nextFloat();
        Q = sc.nextFloat();

        nnodes = sc.nextInt();
        neltos = sc.nextInt();
        ndirich = sc.nextInt();
        nneu = sc.nextInt();

        m.setParameters(k, Q);
        m.setSizes(nnodes,neltos,ndirich,nneu);
        m.createData();

        obtenerDatosCoordenadas(sc, nnodes, m.getNodes());
        obtenerDatosElementos(sc,neltos, m.getElements());
        obtenerDatosCondiciones(sc,ndirich, m.getDirichlet());
        obtenerDatosCondiciones(sc,nneu, m.getNeumann());

        sc.close();
        //Se corrigen los índices en base a las filas que serán eliminadas
        //luego de aplicar las condiciones de Dirichlet
        correctConditions(ndirich,m.getDirichlet(),m.getDirichletIndices());
    }

    public static boolean findIndex(int v, int s, ArrayList<Integer> arr){
        for (int i = 0; i < s; i++){
            if (arr.get(i) == v) return true;
        }

        return false;
    }

    public static void writeResults(Mesh m, ArrayList<Float> T, String filename) throws IOException {
        String outputFileName = addExtension(filename, ".post.res");

        ArrayList<Integer> dirich_indices = m.getDirichletIndices();
        ArrayList<Condition> dirich = m.getDirichlet();

        FileWriter file = new FileWriter(outputFileName);
        BufferedWriter out = new BufferedWriter(file);

        out.write("GiD Post Results File 1.0\n");
        out.write("Result \"Temperature\" \"Load Case 1\" 1 Scalar OnNodes\nComponentNames \"T\"\nValues\n");

        int Tpos = 0;
        int Dpos = 0;
        int n = m.getSize(sizes.NODES);
        int nd = m.getSize(sizes.DIRICHLET);

        for (int i = 0; i < n; i++){
            if (findIndex(i+1, nd, dirich_indices)){
                out.write("" + i+1 + " " + dirich.get(Dpos).getValue() + "\n");
                Dpos++;
            } else {
                out.write("" + i+1 + " " + T.get(Tpos) + "\n");
                Tpos++;
            }
        }

        out.write("End values");
        file.close();
    }
}

