package cop.icoman;

import java.util.Comparator;

/**
 * @author Oleg Cherednik
 * @since 03.07.2013
 */
public final class OffsetComparator implements Comparator<IconImageHeader> {
    private static final OffsetComparator INSTANCE = new OffsetComparator();

    public static OffsetComparator getInstance() {
        return INSTANCE;
    }

    private OffsetComparator() {
    }

    // ========== Comparator ==========

    public int compare(IconImageHeader obj1, IconImageHeader obj2) {
        if (obj1 != null)
            return obj2 != null ? Integer.compare(obj1.getOffs(), obj2.getOffs()) : 1;
        return obj2 != null ? -1 : 0;
    }
}
