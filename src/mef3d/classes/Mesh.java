package mef3d.classes;

import mef3d.classes.enums.parameters;
import mef3d.classes.enums.sizes;

import java.util.ArrayList;

public class Mesh {

    float parameter [] = new float[2] ;
    int size [] = new int[4];

    ArrayList<Node> node_list;
    ArrayList<Element> element_list;
    ArrayList<Integer> indices_dirich;
    ArrayList<Condition> dirichlet_list;
    ArrayList<Condition> neumann_list;

    public void setParameters(float k, float Q){
        parameter[Integer.parseInt(parameters.THERMAL_CONDUCTIVITY.toString())] = k;
        parameter[Integer.parseInt(parameters.HEAT_SOURCE.toString())] = Q;
    }

    public void setSizes(int nnodes, int neltos,int ndirich,int nneu) {
        size[Integer.parseInt(sizes.NODES.toString())] = nnodes;
        size[Integer.parseInt(sizes.ELEMENTS.toString())] = neltos;
        size[Integer.parseInt(sizes.DIRICHLET.toString())] = ndirich;
        size[Integer.parseInt(sizes.NEUMANN.toString())] = nneu;
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
        if (type == Integer.parseInt(sizes.DIRICHLET.toString())) return dirichlet_list.get(i);
        else return neumann_list.get(i);
    }
}
