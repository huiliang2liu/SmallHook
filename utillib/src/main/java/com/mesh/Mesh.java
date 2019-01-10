package com.mesh;
public abstract class Mesh {
	protected int width      = 40;
    protected int height     = 40;
    protected int mBmpWidth   = -1;  
    protected int mBmpHeight  = -1;  
    protected final float[] mVerts;  
      
    public Mesh(int width, int height)  
    {
        this.width  = width;
        this.height = height;
        mVerts  = new float[(this.width + 1) * (this.height + 1) * 2];
    }  
      
    public float[] getVertices()  
    {  
        return mVerts;  
    }  
      
    public int getWidth()  
    {  
        return width;
    }  
      
    public int getHeight()  
    {  
        return height;
    }  
      
    public static void setXY(float[] array, int index, float x, float y)  
    {  
        array[index*2 + 0] = x;  
        array[index*2 + 1] = y;  
    }  
      
    public void setBitmapSize(int w, int h)  
    {  
        mBmpWidth  = w;  
        mBmpHeight = h;  
    }  
      
    public abstract void buildPaths(float endX, float endY);  
      
    public abstract void buildMeshes(int index);  
  
    public void buildMeshes(float w, float h)  
    {  
        int index = 0;  
          
        for (int y = 0; y <= height; ++y)
        {  
            float fy = y * h / height;
            for (int x = 0; x <= width; ++x)
            {  
                float fx = x * w / width;
                  
                setXY(mVerts, index, fx, fy);  
                  
                index += 1;  
            }  
        }  
    }  
}
