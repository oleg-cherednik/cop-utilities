package cop.icoman;

import java.io.DataInput;
import java.io.IOException;

public class ImageKey implements Comparable<ImageKey> {
	private int width; // size: 1, offs: 0x0 (0-255, 0=256 pixels)
	private int height; // size: 1, offs: 0x1 (0-255, 0=256 pixels)
	private int colors; // size: 1, offs: 0x2 (0=256 - truecolor)

	public ImageKey() {}

	public ImageKey(int width, int height, int colors) {
		this.width = width;
		this.height = height;
		this.colors = (colors == 0) ? 256 : colors;
	}

	public ImageKey(DataInput in) throws IOException {
		read(in);
	}

	public void read(DataInput in) throws IOException {
		width = fix(in.readUnsignedByte());
		height = fix(in.readUnsignedByte());
		colors = fix(in.readUnsignedByte());
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = fix(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = fix(height);
	}

	public int getColors() {
		return colors;
	}

	public void setColors(int colors) {
		this.colors = fix(colors);
	}

	/*
	 * Comparable
	 */

	public int compareTo(ImageKey obj) {
		if (obj == null)
			return 1;

		if (this.width != obj.width)
			return (this.width > obj.width) ? 1 : -1;
		if (this.height != obj.height)
			return (this.height > obj.height) ? 1 : -1;
		if (this.colors != obj.colors)
			return (this.colors > obj.colors) ? 1 : -1;

		return 0;
	}

	/*
	 * Object
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colors;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageKey other = (ImageKey)obj;
		if (colors != other.colors)
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return width + "x" + height + " " + ((colors == 256) ? "truecolor" : colors + "colors");
	}

	/*
	 * static
	 */

	private static int fix(int size) {
		return (size != 0) ? size : 256;
	}
}
