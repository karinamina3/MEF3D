package mef3d;

import mef3d.classes.Item;
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

        for (int i = 0; i < n; i++) {
            Node node = new Node();
            int e;
            float r, rr, rrr;

            e = sc.nextInt();
            r = sc.nextFloat();
            rr = sc.nextFloat();
            rrr = sc.nextFloat();

            node.setValues(e,
                    r,
                    rr,
                    rrr,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING
                    );

            node_list.add(node);
        }
    }

    public static void obtenerDatosElementos(Scanner sc, int n, ArrayList<Element> element_list) {
        for (int i = 0; i < n; i++) {
            Element el = new Element();
            int eId, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;

            eId = sc.nextInt();
            e1 = sc.nextInt();
            e2 = sc.nextInt();
            e3 = sc.nextInt();
            e4 = sc.nextInt();
//            e5 = sc.nextInt();
//            e6 = sc.nextInt();
//            e7 = sc.nextInt();
//            e8 = sc.nextInt();
//            e9 = sc.nextInt();
//            e10 = sc.nextInt();

            el.setValues(
                    eId,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    e1,
                    e2,
                    e3,
                    e4,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING
            );

            element_list.add(el);
        }
    }

    public static void obtenerDatosCondiciones(Scanner sc, int n, ArrayList<Condition> condition_list) {
        for (int i = 0; i < n; i++) {
            Condition cond = new Condition();
            int e0;
            float r0;

            e0 = sc.nextInt();
            r0 = sc.nextFloat();

            cond.setValues(
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    e0,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    indicators.NOTHING,
                    r0
            );

            condition_list.add(cond);
        }
    }

    public static void correctConditions(int n, ArrayList<Condition> list, ArrayList<Integer> indices) {
        for (int i = 0; i < n; i++)
            indices.add(list.get(i).getNode1());

        for (int i = 0; i < n - 1; i++) {
            int pivot = list.get(i).getNode1();
            for (int j = i; j < n; j++) {
                // Si la condición actual corresponde a un nodo posterior al nodo eliminado por
                // aplicar la condición anterior, se debe actualizar su posición.
                if (list.get(j).getNode1() > pivot) list.get(j).setNode1(list.get(j).getNode1() - 1);
            }
        }
    }

    public static String addExtension(String filename, String extension) {
        return filename + extension;
    }

    public static void leerMallayCondiciones(Mesh m, String filename) throws IOException {
        String inputFileName = addExtension(filename, ".dat");

        float Ei, xF, yF, zF;

        int nnodes, neltos, nneu;

        int nDx, nDy, nDz, ndirich;

        File archivo = new File(inputFileName);
        Scanner sc = new Scanner(archivo);

        Ei = sc.nextFloat();
        xF = sc.nextFloat();
        yF = sc.nextFloat();
        zF = sc.nextFloat();

        float[] listF = new float[3];
        listF[0] = xF;
        listF[1] = yF;
        listF[2] = zF;

        nnodes = sc.nextInt();
        neltos = sc.nextInt();

        nDx = sc.nextInt();
        nDy = sc.nextInt();
        nDz = sc.nextInt();

        ndirich = nDx + nDy + nDz;

        nneu = sc.nextInt();

        m.setParameters(Ei, listF);
        m.setSizes(nnodes, neltos, ndirich, nneu);
        m.createData();

        sc.next();

        obtenerDatosCoordenadas(sc, nnodes, m.getNodes());

        sc.next();
        sc.nextLine();
        sc.next();

        obtenerDatosElementos(sc, neltos, m.getElements());

        sc.next();
        sc.nextLine();
        sc.next();

        obtenerDatosCondiciones(sc, nDx, m.getDirichlet());

        sc.next();
        sc.nextLine();
        sc.next();

        obtenerDatosCondiciones(sc, nDy, m.getDirichlet());

        sc.next();
        sc.nextLine();
        sc.next();

        obtenerDatosCondiciones(sc, nDz, m.getDirichlet());

        sc.next();
        sc.nextLine();
        sc.next();

        obtenerDatosCondiciones(sc, nneu, m.getNeumann());

        sc.close();
        //Se corrigen los índices en base a las filas que serán eliminadas
        //luego de aplicar las condiciones de Dirichlet
        correctConditions(ndirich, m.getDirichlet(), m.getDirichletIndices());
    }

    public static boolean findIndex(int v, int s, ArrayList<Integer> arr) {
        for (int i = 0; i < s; i++) {
            if (arr.get(i) == v) return true;
        }

        return false;
    }

    public static void writeResults(Mesh m, ArrayList<Float> T, String filename) throws IOException {
        String outputFileName = addExtension(filename, ".post.res");

        ArrayList<Integer> dirich_indices = m.getDirichletIndices();
        ArrayList<Condition> dirich = m.getDirichlet();

        FileWriter file = new FileWriter(outputFileName);

        file.write("GiD Post Results File 1.0\n");
        file.write("Result \"Temperature\" \"Load Case 1\" 1 Scalar OnNodes\nComponentNames \"T\"\nValues\n");

        int Tpos = 0;
        int Dpos = 0;
        int n = m.getSize(sizes.NODES);
        int nd = m.getSize(sizes.DIRICHLET);

        for (int i = 0; i < n; i++) {
            if (findIndex(i + 1, nd, dirich_indices)) {
                file.write("" + i + 1 + " " + dirich.get(Dpos).getValue() + "\n");
                Dpos++;
            } else {
                file.write("" + i + 1 + " " + T.get(Tpos) + "\n");
                Tpos++;
            }
        }

        file.write("End values");
        file.close();
    }
}

