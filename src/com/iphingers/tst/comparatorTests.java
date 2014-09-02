package com.iphingers.tst;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import com.iphingers.image.ImageComparator;

/**
 * The Class comparatorTests.
 */
public class comparatorTests {
	
	/** The baseimage. */
	private final File BASEIMAGE = new File("Images/IMG_4776.png");
	
	/** The BASEIMAG e2. */
	private final File BASEIMAGE2 = new File("Images/IMG_4777.png");
	
	/** The baseimagetimediff. */
	private final File BASEIMAGETIMEDIFF = new File("Images/IMG_4778.png");
	
	/** The baseimagesmaller. */
	private final File BASEIMAGESMALLER = new File("Images/Smaller.png");
	
	/** The baseimagebigger. */
	private final File BASEIMAGEBIGGER = new File("Images/Bigger.png");
	
	/** The base image. */
	private BufferedImage baseImage;
	
	/** The base image2. */
	private BufferedImage baseImage2;
	
	/** The base image time diff. */
	private BufferedImage baseImageTimeDiff;
	
	/** The base image same. */
	private BufferedImage baseImageSame;
	
	/** The base image smaller. */
	private BufferedImage baseImageSmaller;
	
	/** The base image bigger. */
	private BufferedImage baseImageBigger;
	
	/** The img good. */
	private BufferedImage imgGood;	
	
	/** The goodfilecomp. */
	private File GOODFILECOMP = new File("Images/Good_COMP.png");
	
	/** The smallerfilecomp. */
	private File SMALLERFILECOMP = new File("Images/Smaller_COMP.png");
	
	/** The biggerfilecomp. */
	private File BIGGERFILECOMP = new File("Images/Bigger_COMP.png");
	
	/**
	 * Before test.
	 */
	@Before
	public void beforeTest(){
		
		try {
			
			baseImage = ImageIO.read(BASEIMAGE);
			baseImage2 = ImageIO.read(BASEIMAGE2);
			baseImageTimeDiff = ImageIO.read(BASEIMAGETIMEDIFF);
			baseImageSame = ImageIO.read(BASEIMAGE);		
			imgGood = ImageIO.read(GOODFILECOMP);
			baseImageSmaller = ImageIO.read(BASEIMAGESMALLER);
			baseImageBigger = ImageIO.read(BASEIMAGEBIGGER);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Test same.
	 */
	@Test
	public void testSame() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImageSame);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(0, comp.getPixelDiffCount());
	}

	/**
	 * Test diff same dimensions.
	 */
	@Test
	public void testDiffSameDimensions() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImage2);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(229721, comp.getPixelDiffCount());
		assertEquals(229721, comp.getPixelDiffCount());
	}
	
	/**
	 * Test diff same dimensions percent.
	 */
	@Test
	public void testDiffSameDimensionsPercent() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImage2);
		assertEquals(0, comp.getWidthDiff());
		assertEquals(0, comp.getHeightDiff());
		assertEquals(31, comp.getPixelDiffPercent(),1);
	}
	
	/**
	 * Test create composite.
	 */
	@Test
	public void testCreateComposite() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImageTimeDiff);
		BufferedImage composite = comp.createCompositeImage(GOODFILECOMP);
		ImageComparator compNew = new ImageComparator(baseImage, composite);
		assertEquals(comp.getPixelDiffCount(), compNew.getPixelDiffCount());

	}
	
	/**
	 * Test diff dimensions smaller.
	 */
	@Test
	public void testDiffDimensionsSmaller() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImageSmaller);
		assertEquals(100, comp.getWidthDiff());
		assertEquals(100, comp.getHeightDiff());
		assertEquals(10000, comp.getPixelDiffCount());
		comp.createCompositeImage(SMALLERFILECOMP);
	}
	
	/**
	 * Test diff dimensions bigger.
	 */
	@Test
	public void testDiffDimensionsBigger() {
		
		ImageComparator comp = new ImageComparator(baseImage, baseImageBigger);
		assertEquals(100, comp.getWidthDiff());
		assertEquals(100, comp.getHeightDiff());
		assertEquals(10000, comp.getPixelDiffCount());
		comp.createCompositeImage(BIGGERFILECOMP);
	}
}
