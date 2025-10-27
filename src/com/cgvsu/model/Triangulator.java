package com.cgvsu.model;

import java.util.ArrayList;

// Для выполнения триангуляции модели
public class Triangulator {
    //Нужен алгоритм веера
    public TriangulateModel triangulate(Model model) {
        TriangulateModel triangulateModel = new TriangulateModel(model);
        //очищаем для новой триангуляции!!!
        triangulateModel.polygons.clear();
        for (Polygon orPolygon : model.polygons) {
            ArrayList<Polygon> triangles = triangulatePolygon(orPolygon);
            triangulateModel.polygons.addAll(triangles);
        }
        return triangulateModel;
    }


    //Не лучшая идея, кажется об этом говорили на лекции...
    //Слишком много if хотелось бы уменьшить, но как не знаю
    private ArrayList<Polygon> triangulatePolygon(Polygon polygon) {
        ArrayList<Polygon> triangles = new ArrayList<>();
        // в теории можно было бы и обойтись
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        ArrayList<Integer> textureIndices = polygon.getTextureVertexIndices();
        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        int vertexCount = vertexIndices.size();
        if (vertexCount < 3) {
            //not valid
            return triangles;
        }
        //если уже треугольник, то ничего не менеям
        if (vertexCount == 3) {
            triangles.add(polygon);
            return triangles;
        }


        boolean hasTexture = !textureIndices.isEmpty() && textureIndices.size() == vertexCount;
        boolean hasNormals = !normalIndices.isEmpty() && normalIndices.size() == vertexCount;

        for (int i = 1; i < vertexCount - 1; i++) {
            Polygon triangle = new Polygon();
            ArrayList<Integer> triangleVertexIndices = new ArrayList<>();
            ArrayList<Integer> triangleTextureIndices = new ArrayList<>();
            ArrayList<Integer> triangleNormalIndices = new ArrayList<>();

            //первая вершина всегда вершина 0
            triangleVertexIndices.add(vertexIndices.get(0));
            if (hasTexture) triangleTextureIndices.add(textureIndices.get(0));
            if (hasNormals) triangleNormalIndices.add(normalIndices.get(0));

            //вторая
            triangleVertexIndices.add(vertexIndices.get(i));
            if (hasTexture) triangleTextureIndices.add(textureIndices.get(i));
            if (hasNormals) triangleNormalIndices.add(normalIndices.get(i));

            //третья вершина
            triangleVertexIndices.add(vertexIndices.get(i + 1));
            if (hasTexture) triangleTextureIndices.add(textureIndices.get(i + 1));
            if (hasNormals) triangleNormalIndices.add(normalIndices.get(i + 1));

            triangle.setVertexIndices(triangleVertexIndices);
            if (hasTexture) triangle.setTextureVertexIndices(triangleTextureIndices);
            if (hasNormals) triangle.setNormalIndices(triangleNormalIndices);

            triangles.add(triangle);

        }
        return triangles;
    }

    public static boolean needsTriangulation(Model model) {
        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().size() > 3) {
                return true;
            }
        }
        return false;
    }


    //Статистика

    public static String getPolygonStatistics(Model model) {
        int triangleCount = 0;
        int quadCount = 0;
        int ngonCount = 0;
        for (Polygon polygon: model.polygons){
            int vertexCount= polygon.getVertexIndices().size();
            if (vertexCount==3){
                triangleCount++;
            }
            else if (vertexCount==4){quadCount++;}
            else  ngonCount++;
        }
        return String.format("Triangles: %d, Quads: %d, N-gons: %d", triangleCount, quadCount, ngonCount);
    }
}