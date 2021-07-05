import classes.Item;
import classes.enums.indicators;
import classes.enums.modes;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Tools {

    public static void obtenerDatos(FileInputStream file, int nlines, int n, int mode, ArrayList<Item> item_list) {
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

}

