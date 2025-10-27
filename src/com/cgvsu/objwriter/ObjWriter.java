package com.cgvsu.objwriter;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ObjWriter {
    public static void write(Model model, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (var vertex : model.vertices) {
                writer.write(String.format("v %.6f %.6f %.6f", vertex.getX(), vertex.getY(), vertex.getZ()));

                writer.newLine();
            }
            //текстурные коорд
            for (var texVertex : model.textureVertices) {
                writer.write(String.format("vt %.6f %.6f", texVertex.getX(), texVertex.getY()));
                writer.newLine();
            }
            //нормали
            for (var normal : model.normals) {
                writer.write(String.format("vn %.6f %.6f %.6f", normal.getX(), normal.getY(), normal.getZ()));
                writer.newLine();
            }
            //полигоны
            for (Polygon polygon : model.polygons) {
                //вернусь допишу
                writer.write(faceTostring(polygon));
                writer.newLine();
            }


        }
    }
    //Я не уверен, слишком странно выглядит
    private static String faceTostring(Polygon polygon) {
        StringBuilder sb = new StringBuilder("f");
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        ArrayList<Integer> textureIndices = polygon.getTextureVertexIndices();
        ArrayList<Integer> normalIndices = polygon.getNormalIndices();

        boolean hasTexture = !textureIndices.isEmpty();
        boolean hasNormals = !normalIndices.isEmpty();

        for (int i = 0; i< vertexIndices.size(); i++){
            sb.append(" ");
            //начинает с единицы obj
            sb.append(vertexIndices.get(i)+1);
            if (hasTexture||hasNormals){
                sb.append("/");
                if (hasTexture){
                    sb.append(textureIndices.get(i)+1);

                }
                if (hasNormals){
                    sb.append("/").append(normalIndices.get(i)+1);
                }
            }
        }
        return sb.toString();
    }
}
