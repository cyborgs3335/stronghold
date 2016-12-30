package org.macy.tile;

import java.util.Random;

public class TiledImage {

  private final float f1;
  private final float d1;
  private final int n1;
  private final float f2;
  private final float d2;
  private final int n2;
  private final int n1tile;
  private final int n2tile;
  private float[][][][] tiledData; // [tile2, tile1, index, x|y|z]

  public TiledImage(float f1, float d1, int n1, float f2, float d2, int n2, int n1tile, int n2tile) {
    this.f1 = f1;
    this.d1 = d1;
    this.n1 = n1;
    this.f2 = f2;
    this.d2 = d2;
    this.n2 = n2;
    this.n1tile = n1tile;
    this.n2tile = n2tile;
    tiledData = new float[n2tile][n1tile][][];
  }

  public void setData(float[][] data) {
    if (n1tile == 1 && n2tile == 1) {
      tiledData[0][0] = data;
      return;
    }
    // Determine how many data points are in each tile
    int[][] tileCount = new int[n2tile][n1tile];
    for (int i = 0; i < data.length; i++) {
      int[] i1i2 = getTileIndex(data[i][0], data[i][1]);
      tileCount[i1i2[1]][i1i2[0]] += 1;
    }
    // Allocate memory for each tile
    for (int i2tile = 0; i2tile < n2tile; i2tile++) {
      for (int i1tile = 0; i1tile < n1tile; i1tile++) {
        tiledData[i2tile][i1tile] = new float[tileCount[i2tile][i1tile]][];
        tileCount[i2tile][i1tile] = 0;
      }
    }
    // Copy data into tiles
    for (int i = 0; i < data.length; i++) {
      int[] i1i2 = getTileIndex(data[i][0], data[i][1]);
      int index = tileCount[i1i2[1]][i1i2[0]];
      tiledData[i1i2[1]][i1i2[0]][index] = data[i];
      tileCount[i1i2[1]][i1i2[0]] += 1;
    }
  }

  private int[] getTileIndex(float x1, float x2) {
    int[] i1i2 = new int[2];
    float x1index = (x1 - f1) / d1;
    float x2index = (x2 - f2) / d2;
    i1i2[0] = Math.min(Math.max((int) x1index / n1tile, 0), n1tile - 1);
    i1i2[1] = Math.min(Math.max((int) x2index / n2tile, 0), n2tile - 1);
    return i1i2;
  }

  public float[] findNearestPoint(float x1, float x2) {
    // TODO check distance between nearest point in tile containing x1 and x2 to
    // determine whether to check adjacent tiles for potentially nearer points
    // (when x1,x2 is near an edge or when the data is sparse
    int[] i1i2 = getTileIndex(x1, x2);
    System.out
        .println("points in tile i2 " + i1i2[1] + " i1 " + i1i2[0] + " npts " + tiledData[i1i2[1]][i1i2[0]].length);
    return findNearestPointInTile(tiledData[i1i2[1]][i1i2[0]], x1, x2);
  }

  private float[] findNearestPointInTile(float[][] data, float x1, float x2) {
    float[] xy = new float[2];
    float minDist = Float.MAX_VALUE;
    for (int i = 0; i < data.length; i++) {
      float dx = data[i][0] - x1;
      float dy = data[i][1] - x2;
      float dist = dx * dx + dy * dy;
      if (dist < minDist) {
        xy[0] = data[i][0];
        xy[1] = data[i][1];
        minDist = dist;
      }
    }
    return xy;
  }

  public float[][] createRandomData(int length, long seed) {
    float[][] data = new float[length][3];
    float range1 = (n1 - 1) * d1;
    float range2 = (n2 - 1) * d2;
    Random r = new Random(seed);
    for (int l = 0; l < length; l++) {
      data[l][0] = r.nextFloat() * range1 + f1;
      data[l][1] = r.nextFloat() * range2 + f2;
      data[l][2] = r.nextFloat();
    }
    return data;
  }

  public static void main(String[] args) {
    int n1 = 5000;
    int n2 = 5000;
    TiledImage ti = new TiledImage(0, 1, n1, 0, 1, n2, 1, 1);
    float[][] data = ti.createRandomData(n1 * n2, 123456789);
    ti.setData(data);
    Random r = new Random(0);
    int index = r.nextInt(data.length);
    float x = data[index][0];
    float y = data[index][1];
    long t0 = System.currentTimeMillis();
    float[] xy = ti.findNearestPoint(x, y);
    long t1 = System.currentTimeMillis();
    System.out.println("Actual x,y: " + x + "," + y);
    System.out.println("Nearest x,y: " + xy[0] + "," + xy[1]);
    System.out.println("Found in " + (t1 - t0) + " msec");
  }

}
