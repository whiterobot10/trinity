package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Segment {

	private static BufferedImage placeHolderImage = new BufferedImage(1, 1, 1);
	private static Graphics2D g2 = placeHolderImage.createGraphics();

	private BufferedImage[] image;
	private Twin[] offsets;

	public final float degreesPerRot;

	public Segment(BufferedImage image, int rotations_per_quarter) {
		this.image = new BufferedImage[rotations_per_quarter];
		this.image[0] = image;
		offsets = new Twin[rotations_per_quarter];
		for (int i = 0; i < offsets.length; i++) {
			this.offsets[i] = Twin.zero;
		}
		degreesPerRot = 90.0f / rotations_per_quarter;
	}

//	public Segment(BufferedImage image, int rotations_per_quarter, Twin offset) {
//		this(image, rotations_per_quarter);
//		for (int i = 0; i < offsets.length; i++) {
//			this.offsets[i] = offset;
//		}
//	}

	public BufferedImage getImage() {
		return image[0];
	}



	public void setRotatedImage(int rotation, BufferedImage image, Twin offset) {
		this.image[rotation % this.image.length] = image;
		offsets[rotation] = offset;
	}

	public BufferedImage getImage(int rotation) {
		rotation %= image.length;
		// int subquarters = rotation % image.length;
		// int quarters = rotation/image.length;

		BufferedImage finalimage = image[rotation];

		if (finalimage == null) {
			finalimage = image[0];
			image[rotation] = image[0];
		}

//		if(quarters!=0) {
//			finalimage = new BufferedImage(finalimage.getColorModel(),finalimage.copyData(null),finalimage.getColorModel().isAlphaPremultiplied() ,null );
//			AffineTransformOp tf = new AffineTransformOp(AffineTransform.getQuadrantRotateInstance(quarters, finalimage.getWidth()*0.5+offset.x, finalimage.getHeight()*0.5+offset.y), AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//			finalimage = tf.filter(finalimage, null);
//		}

		return finalimage;

	}

	public void Draw(Graphics2D g, Twin core_pos, Twin offset, Twin scale, int rotation) {
		if (offset == null) {
			offset = new Twin(0, 0);
		}

		if (scale == null) {
			scale = new Twin(1, 1);
		}

//		if (mirrorX) {
//			offset = offset.move(offset.x * -2, 0);
//		}

//		int maxX = image.getWidth() + image.getHeight();
//		int maxY = image.getWidth() + image.getHeight();
//
//		int xCore = (image.getWidth() + image.getHeight()) / 2 - offset.ix();
//		int yCore = (image.getHeight() + image.getWidth()) / 2 - offset.iy();

		// AffineTransform rotate = new AffineTransform();
		AffineTransform translate = new AffineTransform();

		// int rotate_width = image.getWidth() + image.getHeight() +
		// Math.abs(center.ix() + center.iy()) * 2;
		// int rotate_height = image.getWidth() + image.getHeight() +
		// Math.abs(center.ix() + center.iy()) * 2;

		// if (rotate_width % 2 == 1) {
		// rotate_width++;
		// }
		// if (rotate_height % 2 == 1) {
		// rotate_height++;
		// }

		// int rotate_offset_x = rotate_width - image.getWidth() + center.ix();
		// int rotate_offset_y = rotate_height - image.getHeight() + center.iy();

		// rotate.rotate(Math.toRadians(rotation), rotate_width * 0.5, rotate_height *
		// 0.5);

		placeHolderImage = getImage(rotation);

		if (placeHolderImage == null) {
			System.out.println("foo");
		}

//		if(this.offset!=null&&!this.offset.equals(Twin.zero)) {
//			AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotation*degreesPerRot));
//		Point2D.Float foo = new Point2D.Float(this.offset.x, this.offset.y);
//			tx.transform(foo, foo);
//		translate.translate((int)(foo.getX()*scale.x), (int)(foo.getY()*scale.y));
//		}

		translate.translate(
				(int) (core_pos.ix() + offset.ix() * scale.ix() + ((placeHolderImage.getWidth()) * scale.ix()) / -2),
				(int) (core_pos.iy() + offset.iy() * scale.iy() + ((placeHolderImage.getHeight()) * scale.iy()) / -2));

//		if(scale.x<0) {
//		translate.translate(placeHolderImage.getWidth(), 0);
//		}
//		if(scale.y<0) {
//		translate.translate(0, placeHolderImage.getHeight());
//		}
//		g.setColor(Color.red);
//		g.fillRect(
//				(int) (translate.getTranslateX() + ((placeHolderImage.getWidth() + this.offset.ix()) * scale.ix()) / 2),
//				(int) (translate.getTranslateY()
//						+ ((placeHolderImage.getHeight() + this.offset.iy()) * scale.iy()) / 2),
//				3, 3);

		// System.out.println(Math.toRadians((rotation / image.length) * 90));

		translate.scale(scale.x, scale.y);


		 translate.rotate(Math.toRadians((rotation / image.length) * 90),
				 (placeHolderImage.getWidth()/2), (placeHolderImage.getHeight()/2));

		translate.translate(offsets[rotation % offsets.length].ix(), offsets[rotation % offsets.length].iy());

		// g2 = placeHolderImage.createGraphics();
		// g2.setColor(new Color(0, 0, 255, 32));
		// g2.fillRect(0, 0, foo, foo);
		// g2.drawRect(0, 0, rotate_width - 1, rotate_height - 1);

		// rotate.translate((rotate_offset_x - 0) * 0.5, (rotate_offset_y - 0) * 0.5);

		// translate.translate((int) (rotate_width * -0.5), (int) (rotate_height *
		// -0.5));

		// translate.rotate(Math.toRadians(rotation));

		// translate.translate(center.x, center.y);

		// translate.rotate(Math.toRadians(-rotation));

//		AffineTransformOp op = new AffineTransformOp(rotate, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//		op.filter(image, placeHolderImage);

//		if (mirrorX) {
//			translate.translate(rotate_width, 0);
//			translate.scale(-1, 1);
//		}

		// translate.translate(-foo/2, -foo/2);

		g.drawImage(placeHolderImage, translate, null);
		g.setColor(Color.green);
		// g.fillRect(core_pos.ix() + offset.ix(), core_pos.iy() + offset.iy(), 1, 1);
		// g.fillRect(core_pos.ix() + offset.ix()+(rotate_width/2), core_pos.iy() +
		// offset.iy()+(rotate_height/2), 1, 1);
		// g.fillRect(core_pos.ix() + offset.ix()+(int)rotate.getTranslateX(),
		// core_pos.iy() + offset.iy()+(int)rotate.getTranslateY(), 1, 1);

//		if (mirrorX) {
//			g.drawImage(placeHolderImage, core_pos.ix() + offset.ix() +placeHolderImage.getWidth()/2,
//					core_pos.iy() + offset.iy() -placeHolderImage.getHeight()/2, -placeHolderImage.getWidth(), placeHolderImage.getHeight(), null);
//			
//		} else {
//			g.drawImage(placeHolderImage, core_pos.ix() + offset.ix() -placeHolderImage.getWidth()/2,
//					core_pos.iy() + offset.iy() - placeHolderImage.getHeight()/2, null);
//			//g.drawRect(core_pos.ix() + offset.ix() - center.ix()-placeHolderImage.getWidth()/2, core_pos.iy() + offset.iy() - center.iy()-placeHolderImage.getHeight()/2, 1, 1);
//		}

		g2.dispose();

	}

}
