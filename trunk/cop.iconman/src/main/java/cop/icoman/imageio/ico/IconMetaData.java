package cop.icoman.imageio.ico;

import nl.ikarus.nxt.priv.imageio.icoreader.lib.ICOMetaDataFormat;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 01.09.2013
 */
public final class IconMetaData extends IIOMetadata {
	private static final String NAME_KEY = "key";
	private static final String NAME_VALUE = "value";
	private static final String NAME_NODE = "KeywordValuePair";

	private static final boolean STANDARD_METADATA_FORMAT_SUPPORTED = false;
	private static final String NATIVE_METADATA_FORMAT_NAME = IconMetaDataFormat.NAME;
	private static final String NATIVE_METADATA_FORMAT_CLASS_NAME = IconMetaDataFormat.class.getName();

	private final List<String> keys = new ArrayList<>();
	private final List<String> values = new ArrayList<>();

	public IconMetaData() {
		super(STANDARD_METADATA_FORMAT_SUPPORTED, NATIVE_METADATA_FORMAT_NAME, NATIVE_METADATA_FORMAT_CLASS_NAME, null,
				null);
	}

	void put(String key, String value) {
		keys.add(key);
		values.add(value);
	}

	// ========== IIOMetadata ==========

	public IIOMetadataFormat getMetadataFormat(String formatName) {
		if (!formatName.equals(NATIVE_METADATA_FORMAT_NAME))
			throw new IllegalArgumentException("Bad format name!");
		return ICOMetaDataFormat.getDefaultInstance();
	}

	public Node getAsTree(String formatName) {
		if (!formatName.equals(NATIVE_METADATA_FORMAT_NAME))
			throw new IllegalArgumentException("Bad format name!");

		IIOMetadataNode root = new IIOMetadataNode(NATIVE_METADATA_FORMAT_NAME);
		Iterator<String> itValue = values.iterator();

		for (String key : keys) {
			IIOMetadataNode node = new IIOMetadataNode(NAME_NODE);
			node.setAttribute(NAME_KEY, key);
			node.setAttribute(NAME_VALUE, itValue.next());
			root.appendChild(node);
		}

		return root;
	}

	public boolean isReadOnly() {
		return true;
	}

	public void reset() {
		keys.clear();
		values.clear();
	}

	public void mergeTree(String formatName, Node root) throws IIOInvalidTreeException {
		if (!formatName.equals(NATIVE_METADATA_FORMAT_NAME))
			throw new IllegalArgumentException("Bad format name!");

		Node node = root;

		if (!node.getNodeName().equals(NATIVE_METADATA_FORMAT_NAME))
			throw new IIOInvalidTreeException("Root must be '" + NATIVE_METADATA_FORMAT_NAME + '\'', node);

		node = node.getFirstChild();

		while (node != null) {
			if (!node.getNodeName().equals(NAME_NODE))
				throw new IIOInvalidTreeException("Node name not KeywordValuePair!", node);

			NamedNodeMap attributes = node.getAttributes();
			Node key = attributes.getNamedItem(NAME_KEY);
			Node value = attributes.getNamedItem(NAME_VALUE);

			if (key == null || value == null)
				throw new IIOInvalidTreeException("Keyword or value missing!", node);

			keys.add(key.getNodeValue());
			values.add(value.getNodeValue());

			node = node.getNextSibling();
		}
	}
}