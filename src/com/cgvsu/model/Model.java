package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    protected ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    protected ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    protected ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    protected ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public ArrayList<Polygon> getPolygons() {
        return new ArrayList<>(this.polygons);
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return new ArrayList<>(this.textureVertices);
    }

    public ArrayList<Vector3f> getNormals() {
        return new ArrayList<>(normals);
    }

    public ArrayList<Vector3f> getVertices() {
        return new ArrayList<>(vertices);
    }
    public void addVertices(Vector3f v){
        this.vertices.add(v);
    }
    public void addTextureVertices(Vector2f v){
        this.textureVertices.add(v);
    }
    public void addNormals(Vector3f v){
        this.normals.add(v);
    }
    public void addPolygon(Polygon polygon){
        this.polygons.add(polygon);
    }
}
