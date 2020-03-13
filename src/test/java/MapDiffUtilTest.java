import java.util.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests MapDiffUtil
 */
public class MapDiffUtilTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Rule
    public TestName testName = new TestName();

    @Before
    public void logTestName() {
        log.info("Executing {}", testName.getMethodName());
    }

    @Test
    public void testEqual() {


        String one = "strikethrough price: $69.00";
        String two ="sale price: $50.99";

        String pattern = "([\\d.\\d]+)";
        List<String> prices = new ArrayList<>();
        List<String> prices2 = new ArrayList<>();
        prices.add(one);
        prices.add(two);
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        for (String line : prices) {
            Matcher m = r.matcher(line);
            if (m.find()) {
                //System.out.println("Found value: " + m.group(0));
                prices2.add(m.group(0));
            }
        }
        System.out.println(prices2);

        double priceOne = Double.parseDouble(prices2.get(0));
        double priceTwo = Double.parseDouble(prices2.get(1));

        if (priceOne > priceTwo){
            System.out.println("Price one is higher than price two");
        }
        else if(priceOne == priceTwo){
            System.out.println("Prices are equals");
        }
        else{
            System.out.println("Price two is higher than price one");
        }


        final Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("A", 1);
        map1.put("B", 2);

        final Map<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("B", 2);
        map2.put("A", 1);

        assertTrue("Maps should be equal", MapDiffUtil.validateEqual(
                map1, map2, "map1", "map2"));
    }

    @Test
    public void testSubset() {
        final Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("A", 1);

        final Map<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("B", 2);
        map2.put("A", 1);

        assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(
                map1, map2, "map1", "map2"));

    }

    @Test
    public void testSeparate() {
        final Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("A", 1);

        final Map<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("B", 2);

        assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(
                map1, map2, "map1", "map2"));
    }

    @Test
    public void testMismatches() {
        final Map<String, String> map1 = new HashMap<String, String>();

        List<String> out = new ArrayList<>();
        out.add("GREEN");
        out.add("GREEN");
        out.add("GREEN");
        Random r = new Random();
        for (int i = 0; i < 10; i++){
            int randomItem = r.nextInt(out.size());
            String randomElement = out.get(randomItem);
            map1.put("CR00"+i, randomElement);
        }

        TreeMap<String, String> sorted1 = new TreeMap<>();
        sorted1.putAll(map1);

        /*map1.put("CR001", "GREEN");
        map1.put("CR002", "YELLOW");
        map1.put("CR003", "YELLOW");
        map1.put("CR004", "RED");*/

        final Map<String, String> map2 = new HashMap<String, String>();

        List<String> out1 = new ArrayList<>();
        out1.add("GREEN");
        out1.add("YELLOW");
        out1.add("RED");

        for (int x = 0; x < 10; x++){
            int randomItem = r.nextInt(out1.size());
            String randomElement = out1.get(randomItem);
            map2.put("CR00"+x, randomElement);
        }

        TreeMap<String, String> sorted2 = new TreeMap<>();
        sorted2.putAll(map2);

        /*map2.put("CR001", "GREEN");
        map2.put("CR002", "GREEN");
        map2.put("CR003", "GREEN");
        map2.put("CR004", "YELLOW");*/

        assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(
                sorted1, sorted2, "map1", "map2"));

    }
}