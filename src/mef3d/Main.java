package mef3d;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String filename;

        ArrayList<ArrayList<ArrayList<Float>>> localKs = new ArrayList<ArrayList<ArrayList<Float>>>();
        ArrayList<ArrayList<Float>> localbs = new ArrayList<ArrayList<Float>>();
        ArrayList<ArrayList<Float>> K = new ArrayList<ArrayList<Float>>();
        ArrayList<Float> b = new ArrayList<Float>();
        ArrayList<Float> T = new ArrayList<Float>();

        System.out.print("IMPLEMENTACION DEL METODO DE LOS ELEMENTOS FINITOS\n");
        System.out.print("\t- TRANSFERENCIA DE CALOR\n");
        System.out.print("\t- 3 DIMENSIONES\n");
        System.out.print("\t- FUNCIONES DE FORMA LINEALES\n");
        System.out.print("\t- PESOS DE GALERKIN\n");
        System.out.print("\t- ELEMENTOS TETRAHEDROS\n");
        System.out.print("*********************************************************************************\n\n");

        mesh m = new mesh();
        leerMallayCondiciones(m, tempRef_filename);
        filename = tempRef_filename.argValue;
        System.out.print("Datos obtenidos correctamente\n********************\n");

        crearSistemasLocales(m, localKs, localbs);
        showKs(new ArrayList<ArrayList<ArrayList<Float>>>(localKs));
        showbs(new ArrayList<ArrayList<Float>>(localbs));
        System.out.print("******************************\n");

        zeroes(K, m.getSize(sizes.NODES.getValue()));
        zeroes(b, m.getSize(sizes.NODES.getValue()));
        ensamblaje(m, localKs, localbs, K, b);
        showMatrix(new ArrayList<ArrayList<Float>>(K));
        showVector(new ArrayList<Float>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        applyNeumann(m, b);
        showMatrix(new ArrayList<ArrayList<Float>>(K));
        showVector(new ArrayList<Float>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        applyDirichlet(m, K, b);
        showMatrix(new ArrayList<ArrayList<Float>>(K));
        showVector(new ArrayList<Float>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        zeroes(T, b.size());
        calculate(K, b, T);

        System.out.print("La respuesta es: \n");
        showVector(new ArrayList<Float>(T));

        writeResults(new mesh(m), new ArrayList<Float>(T), filename);

    }

}
