package com.iphingers.tst;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import com.iphingers.image.ImageComparator;

public class comparatorTests {
	
	private final String IMAGE1 = "Images/IMG_4776.png";
	private final String IMAGE2 = "Images/IMG_4777.png";
	private final String IMAGE3 = "Images/IMG_4778.png";
	private final String IMAGE1SAME = "Images/IMG_4776.png";
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage imgSame;
	
	@Before
	public void beforeTest(){
		
		try {
			
			img1 = ImageIO.read(new File(IMAGE1));
			img2 = ImageIO.read(new File(IMAGE2));
			img3 = ImageIO.read(new File(IMAGE3));
			imgSame = ImageIO.read(new File(IMAGE1SAME));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testSame() {
		
		ImageComparator comp = new ImageComparator(img1, imgSame);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(0, comp.getPixelDiffCount());
	}

	@Test
	public void testDiffSameDimensions() {
		
		ImageComparator comp = new ImageComparator(img1, img2);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(229721, comp.getPixelDiffCount());
		assertEquals(229721, comp.getPixelDiffCount());
	}
	
	@Test
	public void testDiffSameDimensionsPercent() {
		
		ImageComparator comp = new ImageComparator(img1, img2);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(31, comp.getPixelDiffPercent(),1);
	}
	
	@Test
	public void testCreateComposite() {
		
		ImageComparator comp = new ImageComparator(img1, img3);
		BufferedImage composite = comp.createCompositeImage();
		ImageComparator compNew = new ImageComparator(img1, composite);
		assertEquals(comp.getPixelDiffCount(), compNew.getPixelDiffCount());

	}
}
