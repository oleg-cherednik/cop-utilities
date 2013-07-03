package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class IconImageHolder
{
	private BitmapType type;
	private final Map<ImageKey, IconImageHeader> images = new TreeMap<ImageKey, IconImageHeader>();

	public void read(DataInput in, int imageCount, BitmapType type) throws IOException, IconManagerException {
		this.type = type;
		images.clear();

		IconImageHeader image;

		for(int i = 0; i < imageCount; i++)
		{
			image = type.createImageHeader(i, in);
			images.put(image.getImageKey(), image);
		}
	}

	public IconImageHeader getImage(ImageKey key)
	{
		return images.get(key);
	}

	public IconImageHeader[] getImages()
	{
		return images.values().toArray(new IconImageHeader[images.size()]);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return type + ":" + images.size();
	}
}
