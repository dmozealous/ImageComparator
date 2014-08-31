package com.iphingers.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageComparator {
	
	BufferedImage img1 = null;
	BufferedImage img2 = null;
	int diffCount = 0;
	List<Pixel> pixels;
	
	public ImageComparator(BufferedImage img1, BufferedImage img2){
		this.img1 = img1;
		this.img2 = img2;
		
		pixels = new ArrayList<Pixel>();
	}

	public int getPixelDiffCount() {
		
		if(diffCount != 0){
			return diffCount;
		}
		
		for(int y = 0; y < img1.getHeight(); y++){
			
			for(int x = 0; x < img1.getWidth(); x++){
				
				if(img1.getRGB(x, y) != img2.getRGB(x, y)){
					Pixel pixel = new Pixel(x,y);
					pixels.add(pixel);
					diffCount++;
				}

			}
	
		}
		
		return diffCount;
	}

	public int getWidthDiff() {
		return img1.getWidth() - img2.getWidth();
	}

	public int getHeightDiff() {
		return img1.getHeight() - img2.getHeight();
	}
	
	public BufferedImage createCompositeImage(){
		
		if(getPixelDiffCount() == 0){
			return img1;
		}
		
		ColorModel cm = img1.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = img1.copyData(null);
		BufferedImage newImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		
		for(Pixel pixel : pixels){
			newImage.setRGB(pixel.getX(), pixel.getY(), 16711680);
		}
		
	    File outputfile = new File("Images/new.png");
	    try {
			ImageIO.write(newImage, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return newImage;
		
	}

	public int getPixelDiffPercent() {
		
		int dimension = img1.getHeight() * img1.getWidth();
		double diff = (double)getPixelDiffCount()/dimension*100;
		
		return (int)diff;
		
	}
			
	
}
