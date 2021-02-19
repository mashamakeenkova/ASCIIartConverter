import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class AsciiConverter {

	public static final int MAX_SIZE = 100;
	BufferedImage img;
	BufferedImage imgScaled;
	PrintWriter printie;
	FileWriter writie;

	public AsciiConverter() {
		try {
			printie = new PrintWriter(writie = new FileWriter("OUTPUT FILE", true));
			// printie = new PrintWriter(System.out); //print to console
		} catch (IOException exc) { // change to IOException when working with file
			System.out.println(exc);
		}
	}

	public static void main(String[] args) throws IOException {
		AsciiConverter image = new AsciiConverter();
		clearFile(); 
		image.toGrayscale("INPUT FILE"); // file that is read
	}

	public void toGrayscale(String image) { // find grey scale value of a pixel and print appropriate character
		try {
			img = ImageIO.read(new File(image));
		} catch (IOException myException) {
			System.out.println(myException);
		}
		imgScaled = scaleImage(img);
		int h = imgScaled.getHeight();
		int w = imgScaled.getWidth();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Color col = new Color(imgScaled.getRGB(j, i));
				double red = col.getRed() * 0.299;
				double green = col.getGreen() * 0.587;
				double blue = col.getBlue() * 0.114;
				int grey = (int) ((int) red + green + blue);
				// Color greyCol = new Color(grey); //this would be the grey scale value
				print(asciify(grey));
			}
			try {
				printie.println("");
				printie.flush();
				writie.flush();
			} catch (Exception myExc) {
				System.out.println(myExc);
			}
		}
	}

	public BufferedImage scaleImage(BufferedImage image) { // scales image
		int h = image.getHeight();
		int w = image.getWidth();
		int newW = 0;
		int newH = 0;
		if (h > w) {
			newH = MAX_SIZE;
			double dH = (double) h;
			double temp = newH / dH;
			double wW = temp * w;
			newW = (int) wW;
		} else {
			newW = MAX_SIZE;
			double dW = (double) w;
			double temp = newW / dW;
			double hH = temp * h;
			newH = (int) hH;
		}
		BufferedImage newImg = new BufferedImage(newW, newH, Image.SCALE_DEFAULT);
		Graphics2D graphics2d = newImg.createGraphics();
		graphics2d.drawImage(image, 0, 0, newW, newH, null);
		graphics2d.dispose();
		return newImg;
	}

	public String asciify(int grey) { // reversed values for dark background
		String s = "";
		if (grey >= 240) {
			s = "@@";
		} else if (grey >= 210) {
			s = "##";
		} else if (grey >= 190) {
			s = "&&";
		} else if (grey >= 170) {
			s = "88";
		} else if (grey >= 120) {
			s = "^^";
		} else if (grey >= 110) {
			s = "++";
		} else if (grey >= 80) {
			s = "**";
		} else if (grey >= 60) {
			s = "..";
		} else {
			s = "  ";
		}
		return s;
	}

	public void print(String s) {
		try {
			printie.write(s);
			printie.flush();
			writie.flush();
		} catch (Exception myExc) {
			System.out.println(myExc);
		}

	}
	
	public static void clearFile() throws IOException {  //removes any content form output file 
		FileWriter writie2 = new FileWriter("OUTPUT FILE", false); 
		PrintWriter printie2 = new PrintWriter(writie2, false); 
		printie2.flush(); 
		printie2.close(); 
		writie2.close(); 
	}

}
