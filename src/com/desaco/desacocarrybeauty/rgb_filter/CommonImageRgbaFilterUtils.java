package com.desaco.desacocarrybeauty.rgb_filter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * 圖片濾鏡- https://www.oschina.net/question/231733_44154
 * @author desaco
 * 同样注意图片的大小，数组大小不能超过虚拟机规定值。
 * 对像素点进行处理.
 *
 */
public class CommonImageRgbaFilterUtils {

	/** 
     * 怀旧效果(相对之前做了优化快一倍) ,算法如下：
     * R = 0.393r+0.769g+0.189b
     * G = 0.349r+0.686g+0.168b
     * B = 0.272r+0.534g+0.131b
     * @param bmp 
     * @return 
     */  
    private Bitmap oldRemeber(Bitmap bmp)  
    {  
        // 速度测试  
        long start = System.currentTimeMillis();  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
        int pixColor = 0;  
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 0; i < height; i++)  
        {  
            for (int k = 0; k < width; k++)  
            {  
                pixColor = pixels[width * i + k];  
                pixR = Color.red(pixColor);  
                pixG = Color.green(pixColor);  
                pixB = Color.blue(pixColor);  
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);  
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);  
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);  
                int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);  
                pixels[width * i + k] = newColor;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  
    
    /**
     * 模糊效果
     * @param bmp
     * @return
     */ 
    private Bitmap blurImage(Bitmap bmp) 
    { 
        int width = bmp.getWidth(); 
        int height = bmp.getHeight(); 
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565); 
         
        int pixColor = 0; 
         
        int newR = 0; 
        int newG = 0; 
        int newB = 0; 
         
        int newColor = 0; 
         
        int[][] colors = new int[9][3]; 
        for (int i = 1, length = width - 1; i < length; i++) 
        { 
            for (int k = 1, len = height - 1; k < len; k++) 
            { 
                for (int m = 0; m < 9; m++) 
                { 
                    int s = 0; 
                    int p = 0; 
                    switch(m) 
                    { 
                    case 0: 
                        s = i - 1; 
                        p = k - 1; 
                        break; 
                    case 1: 
                        s = i; 
                        p = k - 1; 
                        break; 
                    case 2: 
                        s = i + 1; 
                        p = k - 1; 
                        break; 
                    case 3: 
                        s = i + 1; 
                        p = k; 
                        break; 
                    case 4: 
                        s = i + 1; 
                        p = k + 1; 
                        break; 
                    case 5: 
                        s = i; 
                        p = k + 1; 
                        break; 
                    case 6: 
                        s = i - 1; 
                        p = k + 1; 
                        break; 
                    case 7: 
                        s = i - 1; 
                        p = k; 
                        break; 
                    case 8: 
                        s = i; 
                        p = k; 
                    } 
                    pixColor = bmp.getPixel(s, p); 
                    colors[m][0] = Color.red(pixColor); 
                    colors[m][1] = Color.green(pixColor); 
                    colors[m][2] = Color.blue(pixColor); 
                } 
                 
                for (int m = 0; m < 9; m++) 
                { 
                    newR += colors[m][0]; 
                    newG += colors[m][1]; 
                    newB += colors[m][2]; 
                } 
                 
                newR = (int) (newR / 9F); 
                newG = (int) (newG / 9F); 
                newB = (int) (newB / 9F); 
                 
                newR = Math.min(255, Math.max(0, newR)); 
                newG = Math.min(255, Math.max(0, newG)); 
                newB = Math.min(255, Math.max(0, newB)); 
                 
                newColor = Color.argb(255, newR, newG, newB); 
                bitmap.setPixel(i, k, newColor); 
                 
                newR = 0; 
                newG = 0; 
                newB = 0; 
            } 
        } 
         
        return bitmap; 
    } 
     
    /**
     * 柔化效果(高斯模糊)(优化后比上面快三倍)
     * @param bmp
     * @return
     */ 
    private Bitmap blurImageAmeliorate(Bitmap bmp) 
    { 
        long start = System.currentTimeMillis(); 
        // 高斯矩阵 
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 }; 
         
        int width = bmp.getWidth(); 
        int height = bmp.getHeight(); 
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565); 
         
        int pixR = 0; 
        int pixG = 0; 
        int pixB = 0; 
         
        int pixColor = 0; 
         
        int newR = 0; 
        int newG = 0; 
        int newB = 0; 
         
        int delta = 16; // 值越小图片会越亮，越大则越暗 
         
        int idx = 0; 
        int[] pixels = new int[width * height]; 
        bmp.getPixels(pixels, 0, width, 0, 0, width, height); 
        for (int i = 1, length = height - 1; i < length; i++) 
        { 
            for (int k = 1, len = width - 1; k < len; k++) 
            { 
                idx = 0; 
                for (int m = -1; m <= 1; m++) 
                { 
                    for (int n = -1; n <= 1; n++) 
                    { 
                        pixColor = pixels[(i + m) * width + k + n]; 
                        pixR = Color.red(pixColor); 
                        pixG = Color.green(pixColor); 
                        pixB = Color.blue(pixColor); 
                         
                        newR = newR + (int) (pixR * gauss[idx]); 
                        newG = newG + (int) (pixG * gauss[idx]); 
                        newB = newB + (int) (pixB * gauss[idx]); 
                        idx++; 
                    } 
                } 
                 
                newR /= delta; 
                newG /= delta; 
                newB /= delta; 
                 
                newR = Math.min(255, Math.max(0, newR)); 
                newG = Math.min(255, Math.max(0, newG)); 
                newB = Math.min(255, Math.max(0, newB)); 
                 
                pixels[i * width + k] = Color.argb(255, newR, newG, newB); 
                 
                newR = 0; 
                newG = 0; 
                newB = 0; 
            } 
        } 
         
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height); 
        long end = System.currentTimeMillis(); 
        Log.d("may", "used time="+(end - start)); 
        return bitmap; 
    } 
    
    /** 
     * 图片锐化（拉普拉斯变换） 
     * @param bmp 
     * @return 
     */  
    private Bitmap sharpenImageAmeliorate(Bitmap bmp)  
    {  
        long start = System.currentTimeMillis();  
        // 拉普拉斯矩阵  
        int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int idx = 0;  
        float alpha = 0.3F;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + n) * width + k + m];  
                        pixR = Color.red(pixColor);  
                        pixG = Color.green(pixColor);  
                        pixB = Color.blue(pixColor);  
                          
                        newR = newR + (int) (pixR * laplacian[idx] * alpha);  
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);  
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);  
                        idx++;  
                    }  
                }  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  
    
    
    
}
