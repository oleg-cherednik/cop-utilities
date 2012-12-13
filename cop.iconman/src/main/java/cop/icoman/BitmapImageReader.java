package cop.icoman;

import javax.imageio.ImageReader;

public abstract class BitmapImageReader// extends ImageReader
{
//	protected BitmapImageReader(ImageReaderSpi originatingProvider) {
//		super(originatingProvider);
//	}
//
//
//
//
//	  /**
//	   * Returns the height in pixels of the given image within the input source.
//	   *
//	   * @param imageIndex the index of the image to be queried.
//	   * @return the height of the image, as an <code>int</code>.
//	   * @throws IOException if an error occurs reading the height information
//	   *   from the input source.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public int getHeight(int imageIndex) throws IOException {
//	    return getIcoFile().getEntry(imageIndex).getHeight();
//	  }
//
//	  /**
//	   * Returns an <code>IIOMetadata</code> object containing metadata associated
//	   * with the given image, or <code>null</code> if the reader does not support
//	   * reading metadata, is set to ignore metadata, or if no metadata is
//	   * available.
//	   *
//	   * example:
//	   * <pre>
//	     IIOMetadata meta = reader.getImageMetadata(imageNr);
//	     IIOMetadataNode n = (IIOMetadataNode)meta.getAsTree(meta.getNativeMetadataFormatName());
//	       if (n.hasChildNodes()) {
//	           org.w3c.dom.NodeList nl = n.getChildNodes();
//	           for (int childNr=0;childNr<nl.getLength();childNr++) {
//	              IIOMetadataNode child =(IIOMetadataNode) nl.item(childNr);
//	              String key = child.getAttribute("keyword");
//	              if (key != null && key.equals("bpp")) {
//	                bpp = child.getAttribute("value");
//	                break;
//	               }
//	            }
//	       }
//	    </pre>
//	   the available keywords are:<br />
//	   width, height, colorCount, bitCount, bpp, reserved, planes <br />
//	   note that bitCount & bpp are the same.<br />
//	   <br />
//	   *
//	   * @param imageIndex the index of the image whose metadata is to be
//	   *   retrieved.
//	   * @return an <code>IIOMetadata</code> object, or <code>null</code>.
//	   * @throws IOException if an error occurs during reading.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
//	    try {
//	      ICOFile file = getIcoFile();
//	      IconEntry e = file.getEntry(imageIndex);
//	      ICOMetaData meta = new ICOMetaData();
//	      meta.put("width",  Integer.toString(e.getWidth()));
//	      meta.put("height", Integer.toString(e.getHeight()));
//	      meta.put("colorCount", Integer.toString(e.getColorCount()));
//	      meta.put("bitCount", Integer.toString(e.getBitCount()));
//	      meta.put("bpp", Integer.toString(e.getBitCount()));
//	      meta.put("reserved", Integer.toString(e.getReserved()));
//	      meta.put("planes", Integer.toString(e.getPlanes()));
//	      return meta;
//	    } catch(Exception ex) {
//	      throw new IIOException("Exception reading metadata",ex);
//	    }
//	  }
//
//	  /**
//	   * Returns an <code>Iterator</code> containing possible image types to which
//	   * the given image may be decoded, in the form of
//	   * <code>ImageTypeSpecifiers</code>s.
//	   *
//	   * @param imageIndex the index of the image to be <code>retrieved</code>.
//	   * @return an <code>Iterator</code> containing at least one
//	   *   <code>ImageTypeSpecifier</code> representing suggested image types for
//	   *   decoding the current given image.
//	   * @throws IOException if an error occurs reading the format information
//	   *   from the input source.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public Iterator getImageTypes(int imageIndex) throws IOException {
//	    return new Iterator() {
//	      boolean hasN = true;
//	      public boolean hasNext() {
//	        return hasN;
//	      }
//
//	      public Object next() {
//	        if (!hasN) {
//	          throw new NoSuchElementException();
//	        }
//	        hasN = false;
//	        return ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB);
//	      }
//
//	      public void remove() {
//	        throw new UnsupportedOperationException();
//	      }
//	    };
//
////	    if (true) throw new IOException("getImageTypes(...) is not yet implemented");
//	    //  return null;
//	  }
//
//	  /**
//	   * Returns the number of images, not including thumbnails, available from the
//	   * current input source.
//	   *
//	   * @param allowSearch if <code>true</code>, the true number of images will
//	   *   be returned even if a search is required. If <code>false</code>, the
//	   *   reader may return <code>-1</code> without performing the search.
//	   * @return the number of images, as an <code>int</code>, or <code>-1</code>
//	   *   if <code>allowSearch</code> is <code>false</code> and a search would be
//	   *   required.
//	   * @throws IOException if an error occurs reading the information from the
//	   *   input source.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public int getNumImages(boolean allowSearch) throws IOException {
//	    if (_cachedIcoFile == null && !allowSearch)
//	      return -1;
//	    return getIcoFile().getEntryCount();
//	  }
//
//	  /**
//	   * Returns an <code>IIOMetadata</code> object representing the metadata
//	   * associated with the input source as a whole (i.e., not associated with any
//	   * particular image), or <code>null</code> if the reader does not support
//	   * reading metadata, is set to ignore metadata, or if no metadata is
//	   * available.
//	   *
//	   * @return an <code>IIOMetadata</code> object, or <code>null</code>.
//	   * @throws IOException if an error occurs during reading.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public IIOMetadata getStreamMetadata() throws IOException {
//	    return null;
//	  }
//
//	  /**
//	   * Returns the width in pixels of the given image within the input source.
//	   *
//	   * @param imageIndex the index of the image to be queried.
//	   * @return the width of the image, as an <code>int</code>.
//	   * @throws IOException if an error occurs reading the width information from
//	   *   the input source.
//	   * @todo Implement this javax.imageio.ImageReader method
//	   */
//	  @Override
//    public int getWidth(int imageIndex) throws IOException {
//	    return getIcoFile().getEntry(imageIndex).getWidth();
//	  }
//	  public final static String PROPERTY_NAME_PREFIX = "nl.ikarus.nxt.priv.imageio.icoreader.";
//	  /**
//	   * Reads the image indexed by <code>imageIndex</code> and returns it as a
//	   * complete <code>BufferedImage</code>, using a supplied
//	   * <code>ImageReadParam</code>.
//	   *
//	   * The BufferedImage that is returned is of the type:  BufferedImage.TYPE_INT_ARGB
//	   * and all transparent pixels in the source ico will be transparent in the buffered image
//	   *
//	   * @param imageIndex the index of the image to be retrieved.
//	   * @param param an <code>ImageReadParam</code> used to control the reading
//	   *   process, or <code>null</code>.
//	   * @return the desired portion of the image as a <code>BufferedImage</code>.
//	   * @throws IOException if an error occurs during reading.
//	   */
//	  @Override
//    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
//	    ICOFile file = getIcoFile();
//	    boolean autoSelect =  Boolean.valueOf(System.getProperty(PROPERTY_NAME_PREFIX+"autoselect.icon",Boolean.toString(false)));
//	    if (autoSelect || imageIndex < 0) {
//	      return selectBestImage();
//	    }
//	    /*
//	        boolean autoSelect =  Boolean.valueOf(System.getProperty("nl.ikarus.nxt.priv.imageio.icoreader.autoselect.icon",Boolean.toString(true)));
//	        if (autoSelect || imageIndex < 0) {
//	          //select highest resolution
//	          IconEntry bestBPP = null;
//	          IconEntry bestSize = null;
//	          for (Iterator<IconEntry> it = file.getEntryIterator();it.hasNext();) {
//
//	          }
//	        } else {
//	     */
//
//
//
////	          }
//	    //IconEntry e = file.getEntryIterator().next();
//	    IconEntry e = file.getEntry(imageIndex);
//	    return getImageFromEntry(e);
//	  }
//	  private BufferedImage selectBestImage() throws IOException {
//	    ICOFile file = getIcoFile();
//	    if (file.getEntryCount() == 0) return null;
//	    int w,h;
//	    w = h = Integer.MAX_VALUE;
//	    try {
//	      int ww = Integer.parseInt(System.getProperty(PROPERTY_NAME_PREFIX + "autoselect.width", Integer.toString(w)));
//	      int hh = Integer.parseInt(System.getProperty(PROPERTY_NAME_PREFIX + "autoselect.height", Integer.toString( h)));
//	      w = ww;
//	      h = hh;
//	    } catch(Exception ex) {
//	    }
//	    IconEntry best = file.getEntry(0);
//	    int best_w =best.getWidth();
//	    int best_h = best.getHeight();
//	    int bpp  = best.getBitCount();
//	    for(int i=1;i<file.getEntryCount();i++) {
//	      IconEntry cur = file.getEntry(i);
//	      if (cur.getBitCount() > bpp) {
//	        best=cur;
//	        bpp=best.getBitCount();
//	        best_w =best.getWidth();
//	        best_h = best.getHeight();
//	      } else if (cur.getBitCount() == bpp) {
//	        int d1 = Math.abs(w - best_w) + Math.abs(h - best_h);
//	        int d2 = Math.abs(w - cur.getWidth()) + Math.abs(h - cur.getHeight());
//	        if (d2 < d1) {
//	          best = cur;
//	          //current is closer to the specified with/height
//	          bpp=best.getBitCount();
//	          best_w =best.getWidth();
//	          best_h = best.getHeight();
//	        }
//	      }//else skip
//	    }
//	    if (DEBUG) System.out.println("selected best fitting image: "+best.getWidth() + "x" + best.getHeight()+"x"+best.getBitCount());
//	    return getImageFromEntry(best);
//	  }
//	private BufferedImage getImageFromEntry(IconEntry e) throws IOException {
//	  BufferedImage im = null;
//	  try {
//	    Bitmap b = e.getBitmap();
//	    if (b == null)
//	      return null;
//	    im = b.getImage();
//	  } catch (Exception ex) {
//	    System.err.println(ex.getMessage() + " -- I'll try to convert the icon to BMP and read it with the BMP reader");
//	    im = null;
//	  }
//	  if (im == null) {
//	    Bitmap b = e.getImageIoBitmap();
//	    im = b.getImage();
//	  }
//	  return im;
//	}
//	  private ImageInputStream getMyReader() throws IOException {
//	    Object input = getInput();
//	    if (input instanceof ImageInputStream)
//	      return (ImageInputStream) input;
//	    return ImageIO.createImageInputStream(input);
//	  }
//
//	  private ICOFile _cachedIcoFile = null;
//	  private ICOFile getIcoFile() throws IOException {
//	    if (_cachedIcoFile != null)
//	      return _cachedIcoFile;
//	    Object input = getMyReader();
//	    if (input instanceof ImageInputStream) {
//	      ImageInputStream in = (ImageInputStream) input;
//	      /*      ByteArrayOutputStream bout = new ByteArrayOutputStream(10240);
//	            byte[] buff = new byte[4096];
//	            int len;
//	            while ( (len = in.read(buff)) != -1) {
//	       bout.write(buff, 0, len);
//	            }
//	            buff = bout.toByteArray();
//	            bout = null;
//
//	            ICOFile file = new ICOFile(buff);
//	       */
//
//	      byte[] buff = new byte[4];
//	      in.mark();
//	      in.readFully(buff);
//	      in.reset();
//	//check header
//	      boolean res = (buff[0] == 0x00 && buff[1] == 0x00 && buff[2] == 0x01 && buff[3] == 0x00);
//	      if (!res) {
//	        System.err.println("ICOReader: Incorrect header -- this should have been detected by the ICOReaderSpi -- did anyone tamper with the inputstream (for example the WBMPImageReader sometimes does that)? make sure you have a fresh imageinputstream before calling the read method!.....");
//	      }
//	      ICOFile file = new ICOFile(in);
//	      _cachedIcoFile = file;
//	      return file;
//	    } else {
//	      if (input == null)
//	        throw new IOException("This class supports only an ImageInputStream as input, found: null");
//	      else
//	        throw new IOException("This class supports only an ImageInputStream as input, found: " + input.getClass().getName());
//	    }
//	  }
//	}

	
}
