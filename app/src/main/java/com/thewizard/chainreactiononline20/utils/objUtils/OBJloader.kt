package com.thewizard.chainreactiononline20.utils.objUtils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Vector

class OBJloader(context: Context, res: Int) {

    val numFaces: Int
    val normals: FloatArray
    val textureCoordinates: FloatArray
    val positions: FloatArray


    init {
        val vertices: Vector<Float> = Vector()
        val normals: Vector<Float> = Vector()
        val textures: Vector<Float> = Vector()
        val faces: Vector<String> = Vector()

        try {

            val inputStream = context.resources.openRawResource(res)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String
            while (reader.ready()) {
                line = reader.readLine() ?: break
                val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                when (parts[0]) {
                    "v" -> {
                        vertices.add(parts[1].toFloat())
                        vertices.add(parts[2].toFloat())
                        vertices.add(parts[3].toFloat())
                    }

                    "vt" -> {
                        textures.add(parts[1].toFloat())
                        textures.add(parts[2].toFloat())
                    }

                    "vn" -> {
                        normals.add(parts[1].toFloat())
                        normals.add(parts[2].toFloat())
                        normals.add(parts[3].toFloat())
                    }

                    "f" -> {
                        faces.add(parts[1])
                        faces.add(parts[2])
                        faces.add(parts[3])
                    }
                }
            }
        } catch (e: IOException) {
            System.out.println(e.message)
        }

        numFaces = faces.size
        this.normals = FloatArray(numFaces * 3)
        textureCoordinates = FloatArray(numFaces * 2)
        positions = FloatArray(numFaces * 3)
        var positionIndex = 0
        var normalIndex = 0
        var textureIndex = 0

        val recordTextures = !faces.get(0).contains("//")
        for (face in faces) {
            val parts = face.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            var index = 3 * (parts[0].toShort() - 1)
            positions[positionIndex++] = vertices.get(index++)
            positions[positionIndex++] = vertices.get(index++)
            positions[positionIndex++] = vertices.get(index)

            if (recordTextures) {
                index = 2 * (parts[1].toShort() - 1)
                textureCoordinates[normalIndex++] = textures.get(index++)
                textureCoordinates[normalIndex++] = 1 - textures.get(index)
            }

            index = 3 * (parts[2].toShort() - 1)
            this.normals[textureIndex++] = normals.get(index++)
            this.normals[textureIndex++] = normals.get(index++)
            this.normals[textureIndex++] = normals.get(index)
        }
    }
}