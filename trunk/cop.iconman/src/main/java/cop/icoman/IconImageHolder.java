package cop.icoman;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class IconImageHolder
{
	private IconTypeEnum type;
	private final Map<ImageKey, BitmapImage> images = new TreeMap<ImageKey, BitmapImage>();

	public void read(DataInput in, int imageCount, IconTypeEnum type) throws IOException
	{
		this.type = type;
		images.clear();

		BitmapImage image;

		for(int i = 0; i < imageCount; i++)
		{
			image = type.createBitmapImage(in);
			images.put(image.getImageKey(), image);
		}
	}

	public BitmapImage getImage(ImageKey key)
	{
		return images.get(key);
	}

	public BitmapImage[] getImages()
	{
		return images.values().toArray(new BitmapImage[images.size()]);
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
