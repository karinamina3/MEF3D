package mef3d.classes;

import mef3d.classes.enums.parameters;
import mef3d.classes.enums.sizes;

import java.util.ArrayList;

public class Mesh {

    float parameter [] = new float[6] ;
    int size [] = new int[4];

    ArrayList<Node> node_list;
    ArrayList<Element> element_list;
    ArrayList<Integer> indices_dirich;
    ArrayList<Condition> dirichlet_list;
    ArrayList<Condition> neumann_list;

    public void setParameters(float k, float Q){
        parameter[parameters.THERMAL_CONDUCTIVITY] = k;
        parameter[parameters.HEAT_SOURCE] = Q;
    }

    public void setSizes(int nnodes, int neltos,int ndirich,int nneu) {
        size[sizes.NODES] = nnodes;
        size[sizes.ELEMENTS] = neltos;
        size[sizes.DIRICHLET] = ndirich;
        size[sizes.NEUMANN] = nneu;
    }

    public int getSize(int s){
        return size[s];
    }

    public float getParameter(int p){
        return parameter[p];
    }

    public void createData(){
        node_list = new ArrayList<>(size[sizes.NODES]);
        element_list = new ArrayList<>(size[sizes.ELEMENTS]);
        indices_dirich = new ArrayList<>(size[sizes.DIRICHLET]);
        dirichlet_list = new ArrayList<>(size[sizes.DIRICHLET]);
        neumann_list = new ArrayList<>(size[sizes.NEUMANN]);
    }

    public ArrayList<Node> getNodes(){
        return node_list;
    }

    public ArrayList<Element> getElements(){
        return element_list;
    }

    public ArrayList<Integer> getDirichletIndices(){
        return indices_dirich;
    }

    public ArrayList<Condition> getDirichlet(){
        return dirichlet_list;
    }

    public ArrayList<Condition> getNeumann(){
        return neumann_list;
    }

    public Node getNode(int i){
        return node_list.get(i);
    }

    public Element getElement(int i){
        return element_list.get(i);
    }

    public Condition getCondition(int i, int type){
        if (type == sizes.DIRICHLET) return dirichlet_list.get(i);
        else return neumann_list.get(i);
    }
}
