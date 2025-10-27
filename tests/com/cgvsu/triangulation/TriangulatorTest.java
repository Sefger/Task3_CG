package com.cgvsu.triangulation;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.TriangulateModel;
import com.cgvsu.model.Triangulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TriangulatorTest {

    @Test
    void testTriangulateQuad() {
        Model model = new Model();

        // Добавляем вершины квадрата
        model.vertices.add(new Vector3f(0, 0, 0));
        model.vertices.add(new Vector3f(1, 0, 0));
        model.vertices.add(new Vector3f(1, 1, 0));
        model.vertices.add(new Vector3f(0, 1, 0));

        // Создаем четырехугольник
        Polygon quad = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        vertexIndices.add(0);
        vertexIndices.add(1);
        vertexIndices.add(2);
        vertexIndices.add(3);
        quad.setVertexIndices(vertexIndices);

        model.polygons.add(quad);

        Triangulator triangulator = new Triangulator();
        TriangulateModel result = triangulator.triangulate(model);

        assertEquals(2, result.polygons.size());
        assertEquals(3, result.polygons.get(0).getVertexIndices().size());
        assertEquals(3, result.polygons.get(1).getVertexIndices().size());
        assertTrue(result.isFullyTriangulated());
    }

    @Test
    void testTriangulateTriangle() {
        Model model = new Model();

        model.vertices.add(new Vector3f(0, 0, 0));
        model.vertices.add(new Vector3f(1, 0, 0));
        model.vertices.add(new Vector3f(0, 1, 0));

        Polygon triangle = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        vertexIndices.add(0);
        vertexIndices.add(1);
        vertexIndices.add(2);
        triangle.setVertexIndices(vertexIndices);

        model.polygons.add(triangle);

        Triangulator triangulator = new Triangulator();
        TriangulateModel result = triangulator.triangulate(model);

        assertEquals(1, result.polygons.size());
        assertEquals(3, result.polygons.get(0).getVertexIndices().size());
    }

    @Test
    void testTriangulateWithTextureAndNormals() {
        Model model = new Model();

        // Вершины
        model.vertices.add(new Vector3f(0, 0, 0));
        model.vertices.add(new Vector3f(1, 0, 0));
        model.vertices.add(new Vector3f(1, 1, 0));
        model.vertices.add(new Vector3f(0, 1, 0));

        // Текстурные координаты
        model.textureVertices.add(new Vector2f(0, 0));
        model.textureVertices.add(new Vector2f(1, 0));
        model.textureVertices.add(new Vector2f(1, 1));
        model.textureVertices.add(new Vector2f(0, 1));

        // Нормали
        model.normals.add(new Vector3f(0, 0, 1));
        model.normals.add(new Vector3f(0, 0, 1));
        model.normals.add(new Vector3f(0, 0, 1));
        model.normals.add(new Vector3f(0, 0, 1));

        // Четырехугольник с текстурами и нормалями
        Polygon quad = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        ArrayList<Integer> textureIndices = new ArrayList<>();
        ArrayList<Integer> normalIndices = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            vertexIndices.add(i);
            textureIndices.add(i);
            normalIndices.add(i);
        }

        quad.setVertexIndices(vertexIndices);
        quad.setTextureVertexIndices(textureIndices);
        quad.setNormalIndices(normalIndices);

        model.polygons.add(quad);

        Triangulator triangulator = new Triangulator();
        TriangulateModel result = triangulator.triangulate(model);

        assertEquals(2, result.polygons.size());

        // Проверяем, что текстуры и нормали сохранились
        Polygon firstTriangle = result.polygons.get(0);
        assertEquals(3, firstTriangle.getTextureVertexIndices().size());
        assertEquals(3, firstTriangle.getNormalIndices().size());
    }

    @Test
    void testNeedsTriangulation() {
        Model model = new Model();

        // Добавляем треугольник
        Polygon triangle = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        vertexIndices.add(0);
        vertexIndices.add(1);
        vertexIndices.add(2);
        triangle.setVertexIndices(vertexIndices);
        model.polygons.add(triangle);

        assertFalse(Triangulator.needsTriangulation(model));

        // Добавляем четырехугольник
        Polygon quad = new Polygon();
        vertexIndices = new ArrayList<>();
        vertexIndices.add(0);
        vertexIndices.add(1);
        vertexIndices.add(2);
        vertexIndices.add(3);
        quad.setVertexIndices(vertexIndices);
        model.polygons.add(quad);

        assertTrue(Triangulator.needsTriangulation(model));
    }
}