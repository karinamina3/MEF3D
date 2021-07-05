import classes.Item;
import classes.enums.indicators;
import classes.enums.modes;
import classes.enums.lines;
import classes.enums.sizes;

import java.io.*;
import java.util.ArrayList;

public class Tools {

    public static void obtenerDatos(File file, int nlines, int n, int mode, ArrayList<Item> item_list) {
        String line;

        // TODO: research equivalent '>>' operator

        // file >> line;
        // if(nlines==DOUBLELINE) file >> line;

        for (int i = 0; i < n; i++) {
            switch (mode) {
                case modes.INT_FLOAT:
                    int e0;
                    float r0;
                    // file >> e0 >> r0
                    item_list.get(i).setValues(
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
                    break;
                case modes.INT_FLOAT_FLOAT_FLOAT:
                    int e;
                    float r, rr, rrr;
                    // file >> e >> r >> rr >> rrr;
                    item_list.get(i).setValues(
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
                    break;
                case modes.INT_INT_INT_INT_INT:
                    int e1, e2, e3, e4, e5;
                    // file >> e1 >> e2 >> e3 >> e4 >> e5;
                    item_list.get(i).setValues(
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
                    break;
            }
        }
    }

    public static void correctConditions(int n, Condition [] list, int [] indices){
        for (int i = 0; i < n; i++)
            indices[i] = list[i].getNode1();

        for (int i = 0; i < n-1; i++){
            int pivot = list[i].getNode1();
            for (int j = i; j < n; j++){
                // Si la condición actual corresponde a un nodo posterior al nodo eliminado por
                // aplicar la condición anterior, se debe actualizar su posición.
                if (list[j].getNode1() > pivot) list[j].setNode1(list[j].getNode1() - 1);
            }
        }
    }

    public static void addExtension(String newfilename, String filename, String extension){
        int ori_length = newfilename.length();
        int ext_length = extension.length();

        StringBuilder newFileName = new StringBuilder(newfilename);
        int i;

        for (i = 0; i < ori_length; i++){
            newFileName.setCharAt(i, filename.charAt(i));

        for (i = 0; i < ext_length; i++)
            newFileName.setCharAt(ori_length + i, extension.charAt(i));

        newFileName.setCharAt(ori_length + i, '\0') ;
        }
    }

    public static void leerMallayCondiciones(Mesh m, String filename) throws IOException {
        String inputFileName = "";

        float k, Q;

        int nnodes, neltos, ndirich, nneu;

        addExtension(inputFileName, filename, ".dat");

        FileReader file = new FileReader(inputFileName);

        // file >> k >> Q;
        //cout << "k y Q: "<<k<<" y "<<Q<<"\n";
        // file >> nnodes >> neltos >> ndirich >> nneu;
        //cout << "sizes: "<<nnodes<<" y "<<neltos<<" y "<<ndirich<<" y "<<nneu<<"\n";

        m.setParameters(k, Q);
        m.setSizes(nnodes,neltos,ndirich,nneu);
        m.createData();

        obtenerDatos(file,lines.SINGLELINE,nnodes,modes.INT_FLOAT_FLOAT_FLOAT,m.getNodes());
        obtenerDatos(file,lines.DOUBLELINE,neltos,modes.INT_INT_INT_INT_INT,m.getElements());
        obtenerDatos(file,lines.DOUBLELINE,ndirich,modes.INT_FLOAT,m.getDirichlet());
        obtenerDatos(file,lines.DOUBLELINE,nneu,modes.INT_FLOAT,m.getNeumann());

        file.close();
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

    public static void writeResults(Mesh m, Vector T, String filename) throws IOException {
        String outputFileName = "";

        ArrayList<Integer> dirich_indices = m.getDirichletIndices();
        Condition dirich = m.getDirichlet();

        addExtension(outputFileName, filename, ".post.res");

        FileReader file = new FileReader(outputFileName);

        // file << "GiD Post Results File 1.0\n";
        // file << "Result \"Temperature\" \"Load Case 1\" 1 Scalar OnNodes\nComponentNames \"T\"\nValues\n";

        int Tpos = 0;
        int Dpos = 0;
        int n = m.getSize(sizes.NODES);
        int nd = m.getSize(sizes.DIRICHLET);

        for (int i = 0; i < n; i++){
            if (findIndex(i+1, nd, dirich_indices)){
                // file << i+1 << " " << dirich[Dpos].getValue() << "\n";
                Dpos++;
            } else {
                // file << i+1 << " " << T.at(Tpos) << "\n";
                Tpos++;
            }
        }

        // file << "End values\n";

        file.close();
    }
}

