package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Segment {

	private static BufferedImage placeHolderImage = new BufferedImage(1, 1, 1);
	private static Graphics2D g2 = placeHolderImage.createGraphics();

	private BufferedImage image;
	private Twin center;

	public Segment(BufferedImage image, Twin center) {
		this.image = image;
		this.center = center;
		this.center = new Twin(image.getWidth() / 2, image.getHeight() / 2);
	}

	public Segment(BufferedImage image) {
		this(image, new Twin(image.getWidth() / 2, image.getHeight() / 2));
	}

	public BufferedImage getImage() {
		return image;
	}

	public Twin getCenter() {
		return center;
	}

	public void Draw(Graphics2D g, Twin core_pos, Twin offset, boolean mirrorX, double rotation) {
		if (offset == null) {
			offset=new Twin(0,0);
		}

		if (mirrorX) {
			offset = offset.move(offset.x * -2, 0);
		}

		int maxX = image.getWidth() + image.getHeight();
		int maxY = image.getWidth() + image.getHeight();

		int xCore = (image.getWidth() + image.getHeight()) / 2 - offset.ix();
		int yCore = (image.getHeight() + image.getWidth()) / 2 - offset.iy();

		// placeHolderImage = new BufferedImage(image.getWidth()+image.getHeight(),
		// image.getWidth()+image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		// g2=placeHolderImage.createGraphics();

//		g2.setColor(Color.BLUE);
//		g2.fillRect(0, 0, baz.getWidth(), baz.getHeight());

//		AffineTransform foo = g.getTransform();
//		
//		g2.setBackground(new Color(255, 255, 255, 0));
//		g2.setColor(Color.blue);
//		g2.clearRect(0, 0, maxX, maxY);
//		g2.fillRect(0, 0, maxX, maxY);
//		g2.setColor(Color.black);
//		g2.drawRect((maxX/2), (maxY/2), 1, 1);
//		g2.rotate(Math.toRadians(rotation), (maxX-(image.getWidth()/1)), (maxY-(image.getHeight()/1)));
//		if(mirrorX) {
//			g2.drawImage(image, (maxX-image.getWidth())/2+(image.getWidth())+center.ix(), (maxY-image.getHeight())/2+center.iy(), -image.getWidth(), image.getHeight(), null);
//		} else {
//		
//		g2.drawImage(image, (maxX-image.getWidth())/1+center.ix(), (maxY-image.getHeight())/1+center.iy(), image.getWidth(), image.getHeight(), null);
//		}
//		g.drawImage(placeHolderImage.getSubimage(0, 0, maxX, maxY), core_pos.ix()-xCore, core_pos.iy()-yCore, null);
//		g2.setTransform(foo);
		AffineTransform tx = new AffineTransform();
//		tx.translate(core_pos.ix() + offset.ix()-center.ix(),
//				core_pos.iy() + offset.iy()-center.iy());
		tx.rotate(Math.toRadians(rotation), center.ix() + image.getHeight() * 0.5,
				center.iy() + image.getWidth() * 0.5);

		// placeHolderImage = op.filter(image, null);
//		g2.setBackground(new Color(255, 255, 255, 0));
//		g2.clearRect(0, 0, 100, 100);
//		g2.drawImage(image, tx, null);
//		g.translate(core_pos.ix() + offset.ix()-center.ix(), core_pos.iy() + offset.iy()-center.iy());
//		g.draw(op.getBounds2D(image));
//		g.drawRect(0, 0, 1, 1);
//		g.translate((core_pos.ix() + offset.ix()-center.ix())*-1, (core_pos.iy() + offset.iy()-center.iy())*-1);
		int foo = image.getHeight() + image.getWidth();
		placeHolderImage = new BufferedImage(foo, foo, image.getType());

		tx.translate(image.getHeight() * 0.5, image.getWidth() * 0.5);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		op.filter(image, placeHolderImage);
		

		if (mirrorX) {
			g.drawImage(placeHolderImage, core_pos.ix() + offset.ix() +placeHolderImage.getWidth()/2,
					core_pos.iy() + offset.iy() -placeHolderImage.getHeight()/2, -placeHolderImage.getWidth(), placeHolderImage.getHeight(), null);
			
		} else {
			g.drawImage(placeHolderImage, core_pos.ix() + offset.ix() -placeHolderImage.getWidth()/2,
					core_pos.iy() + offset.iy() - placeHolderImage.getHeight()/2, null);
			//g.drawRect(core_pos.ix() + offset.ix() - center.ix()-placeHolderImage.getWidth()/2, core_pos.iy() + offset.iy() - center.iy()-placeHolderImage.getHeight()/2, 1, 1);

			
		}

		// g2.dispose();

//		AffineTransform foo = g.getTransform();
//		g.rotate(Math.toRadians(rotation), xCore/2,
//				yCore/2);
//		
//		
//		
//		AffineTransformOp foo2 = new AffineTransformOp(foo, null);
//		BufferedImage bar = foo2.filter(image, null);
//		System.out.println();
//		System.out.println("pos "+xCore+" "+yCore);
//		System.out.println("offset "+((xCore/2)*2-xCore)+" "+((yCore/2)*2-yCore));
//		
//		System.out.println(((((bar.getWidth()/4)*2)-(bar.getWidth())/2)+0.5)+" "+((((bar.getHeight()/4)*2)-(bar.getHeight())/2)+0.5));
//		
//		if((xCore/2)*2!=xCore){
//			//if((image.getWidth()/4)*4==image.getWidth()) {
//				g.translate((((bar.getWidth()/4)*2)-(bar.getWidth())/2)+0.5 ,0);
//				System.out.println("x+ "+((xCore/2)*2-xCore)/2.0);
//				
////			} else {
////				g.translate(-0.5, 0);
////				System.out.println("x-0.5");
////				System.out.println((xCore/2)*2-xCore);
////			}
//			
//		}
//		
//		
//		if((yCore/2)*2!=yCore){
//			//if((image.getHeight()/4)*4==image.getHeight()) {
//				g.translate(0, (((bar.getHeight()/4)*2)-(bar.getHeight())/2)+0.5);
//				System.out.println("y+ "+((yCore/2)*2-yCore)/2.0);
//				
////			} else {
////				g.translate(0, -0.5);
////				System.out.println("y-0.5");
////				System.out.println((yCore/2)*2-yCore);
////			}
//		}
//		g.setPaint(null);
//		g.setColor(Color.RED);
//		g.fillRect((core_pos.offset(offset).ix() - (image.getWidth() / 2))/2,
//				(core_pos.offset(offset).iy() - (image.getHeight() / 2))/2, image.getWidth()/2, image.getHeight()/2);
////		g.setPaint(new TexturePaint(image, new Rectangle((core_pos.offset(offset).ix() - (image.getWidth() / 2))/2%image.getWidth(),(core_pos.offset(offset).iy() - (image.getHeight() / 2))/2%image.getHeight(),image.getWidth()/2,image.getHeight()/2)));
////		g.fillRect((core_pos.offset(offset).ix() - (image.getWidth() / 2))/2,
////				(core_pos.offset(offset).iy() - (image.getHeight() / 2))/2, image.getWidth()/2, image.getHeight()/2);
//		g.drawImage(image, (core_pos.offset(offset).ix() - (image.getWidth() / 2))/2,
//				(core_pos.offset(offset).iy() - (image.getHeight() / 2))/2, image.getWidth()/2, image.getHeight()/2, null);
//		
//		g.setTransform(foo);

//		if (mirrorX) {
//			offset = new Twin(-offset.x, offset.y);
//
//			AffineTransform old = g.getTransform();
//			g.rotate(Math.toRadians(rotation), center.ix() + core_pos.offset(offset).ix() - image.getWidth(),
//					center.iy() + core_pos.offset(offset).iy() - image.getHeight());
//
//			g.drawImage(image, core_pos.offset(offset).ix() + (image.getWidth() / 2),
//					core_pos.offset(offset).iy() - (image.getHeight() / 2), -image.getWidth(), image.getHeight(), null);
//
//			g.setTransform(old);
//			
//		} else {
//			AffineTransform old = g.getTransform();
//			g.rotate(Math.toRadians(rotation), center.ix() + core_pos.offset(offset).ix() - image.getWidth(),
//					center.iy() + core_pos.offset(offset).iy() - image.getHeight());
//
//			g.drawImage(image, core_pos.offset(offset).ix() - (image.getWidth() / 2),
//					core_pos.offset(offset).iy() - (image.getHeight() / 2), image.getWidth(), image.getHeight(), null);
//
//			g.setTransform(old);
//		}
	}

}
