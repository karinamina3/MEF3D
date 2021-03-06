package mef3d;

import mef3d.classes.Mesh;
import mef3d.classes.enums.sizes;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // Si en los args no viene el nombre del archivo del .dat se sale del programa
        if (args.length == 0) {
            System.exit(0);
        }

        String filename = args[0];
        // inicializaciones variables requeridas
        ArrayList<ArrayList<ArrayList<Float>>> localKs = new ArrayList<ArrayList<ArrayList<Float>>>();
        ArrayList<ArrayList<Float>> localbs = new ArrayList<ArrayList<Float>>();
        ArrayList<ArrayList<Float>> K = new ArrayList<ArrayList<Float>>();
        ArrayList<Float> b = new ArrayList<Float>();
        ArrayList<Float> T = new ArrayList<Float>();

        System.out.print("IMPLEMENTACION DEL METODO DE LOS ELEMENTOS FINITOS EN 3D\n");
        System.out.print("\t- ALEXA SHMALEXA\n");
        System.out.print("\t- 3 DIMENSIONES\n");
        System.out.print("\t- FUNCIONES DE FORMA LINEALES\n");
        System.out.print("\t- PESOS DE GALERKIN\n");
        System.out.print("\t- ELEMENTOS TETRAHEDROS\n");
        System.out.print("*********************************************************************************\n\n");

        Mesh m = new Mesh();
        Tools.leerMallayCondiciones(m, filename); // leemos del archivo .dat todos los datos sobre condiciones, elementos.
        System.out.print("Datos obtenidos correctamente\n********************\n");

        SEL.crearSistemasLocales(m, localKs, localbs); // creamos sistema local

        SEL.showKs(new ArrayList<ArrayList<ArrayList<Float>>>(localKs));
        SEL.showbs(new ArrayList<ArrayList<Float>>(localbs));
        System.out.print("******************************\n");

        MathTools.zeroesAux(K, m.getSize(sizes.NODES));
        MathTools.zeroes(b, m.getSize(sizes.NODES));

        // proceso de ensamblaje
        SEL.ensamblaje(m, localKs, localbs, K, b);
        SEL.showMatrix(new ArrayList<ArrayList<Float>>(K));
        SEL.showVector(new ArrayList<Float>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        SEL.applyNeumann(m, b);
        SEL.showMatrix(new ArrayList<>(K));
        SEL.showVector(new ArrayList<>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        SEL.applyDirichlet(m, K, b);
        SEL.showMatrix(new ArrayList<>(K));
        SEL.showVector(new ArrayList<>(b));
        System.out.print("******************************\n");
        System.out.print(K.size());
        System.out.print(" - ");
        System.out.print(K.get(0).size());
        System.out.print("\n");
        System.out.print(b.size());
        System.out.print("\n");

        MathTools.zeroes(T, b.size());
        SEL.calculate(K, b, T);

        // resultados
        System.out.print("La respuesta es: \n");
        SEL.showVector(new ArrayList<Float>(T));

        Tools.writeResults(m, new ArrayList<Float>(T), filename);
    }

}
