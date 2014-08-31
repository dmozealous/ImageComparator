package com.iphingers.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * The Class ImageComparator.
 */
public class ImageComparator {
	
	/** The img1. */
	BufferedImage img1 = null;
	
	/** The img2. */
	BufferedImage img2 = null;
	
	/** The diff count. */
	int diffCount = 0;
	
	/** The list of pixels that differ between images. */
	List<Pixel> pixels;
	
	/**
	 * Instantiates a new image comparator.
	 *
	 * @param img1 the img1
	 * @param img2 the img2
	 */
	public ImageComparator(BufferedImage img1, BufferedImage img2){
		this.img1 = img1;
		this.img2 = img2;
		
		pixels = new ArrayList<Pixel>();
	}

	/**
	 * Gets the number of pixels that are different between the two images.
	 *
	 * @return the pixel diff count
	 */
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

	/**
	 * Gets the difference between the width of the images
	 *
	 * @return the width diff
	 */
	public int getWidthDiff() {
		return img1.getWidth() - img2.getWidth();
	}

	/**
	 * Gets the difference between the height of the images.
	 *
	 * @return the height diff
	 */
	public int getHeightDiff() {
		return img1.getHeight() - img2.getHeight();
	}
	
	/**
	 * Creates a composite image of the two images.
	 *
	 * @return the buffered image
	 */
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

	/**
	 * Gets the pixel diff percent.
	 *
	 * @return the pixel diff percent
	 */
	public int getPixelDiffPercent() {
		
		int dimension = img1.getHeight() * img1.getWidth();
		double diff = (double)getPixelDiffCount()/dimension*100;
		
		return (int)diff;
		
	}
			
	
}
