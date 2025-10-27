package com.cgvsu.model;

import java.util.ArrayList;

public class TriangulateModel extends  Model{
    public TriangulateModel(){
        //СУПЕР!!!
        super();
    }
    //на всякий случай вдруг кто захочет
    public TriangulateModel(Model model){
        this.vertices = new ArrayList<>(model.vertices);
        this.textureVertices = new ArrayList<>(model.textureVertices);
        this.normals = new ArrayList<>(model.normals);
        this.polygons = new ArrayList<>(model.polygons);
    }

    //кол-во треуг в модели
    /*
    Хотя наверно это плохая идея, ведь класс уже сразу должен протриангулировать, надо будет уточнить!!!
     */
    public int getTriangleCount(){
        int count = 0;
        for (Polygon polygon: polygons){
            if (polygon.getVertexIndices().size()==3){
                count++;
            }
        }
        return count;
    }
    public boolean isFullyTriangulated(){
        //можно было бы использовать getTriangleCount, но это долго
        for (Polygon polygon: polygons){
            if (polygon.getVertexIndices().size()!=3){
                return false;
            }
        }
        return true;
    }

    //Провервка валидности триангулированной модели

    public boolean isValid(){
        for (Polygon polygon: polygons){
            if (polygon.getVertexIndices().size()!=3) return false;

            for (int vertexIndex: polygon.getVertexIndices()){
                if (vertexIndex<0 || vertexIndex>=vertices.size()) return false;
            }

            if (!polygon.getVertexIndices().isEmpty()){
                for (int texIndex: polygon.getTextureVertexIndices()){
                    if (texIndex<0 ||texIndex>= textureVertices.size()) return false;
                }
            }
            if (!polygon.getNormalIndices().isEmpty()){
                for (int normalIndex: polygon.getNormalIndices()){
                    if (normalIndex<0 || normalIndex >=normals.size()) return false;
                }
            }

        }
        return true;
    }
}
