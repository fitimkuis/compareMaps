import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import static com.google.common.collect.MapDifference.ValueDifference;

/**
 * Map comparison with detailed log messages
 */
public class MapDiffUtil {

    private static final Logger log =
            LoggerFactory.getLogger(MapDiffUtil.class);

    public static <K, V> boolean validateEqual(
            Map<K, V> map1, Map<K, V> map2,
            String map1Name, String map2Name) {

        final MapDifference<K, V> diff = Maps.difference(map1, map2);

        if (diff.areEqual()) {
            log.info("Maps '{}' and '{}' contain exactly the same "
                    + "name/value pairs", map1Name, map2Name);
            return true;

        } else {
            logKeys(diff.entriesOnlyOnLeft(), map1Name, map2Name);
            logKeys(diff.entriesOnlyOnRight(), map2Name, map1Name);
            logEntries(diff.entriesDiffering(), map1Name, map2Name);
            return false;
        }
    }

    private static <K, V> void logKeys(
            Map<K, V> mapSubset, String n1, String n2) {
        if (not(mapSubset.isEmpty())) {
            log.error("Keys found in {} but not in {}: {}",
                    n1, n2, mapSubset.keySet());
        }
    }

    private static <K, V> void logEntries(
            Map<K, ValueDifference<V>> differing,
            String n1, String n2) {
        if (not(differing.isEmpty())) {
            log.error("Differing values found {key={}-value,{}-value}: {}",
                    n1, n2, differing);
        }
    }

    private static boolean not(boolean b) {
        return !b;
    }
}