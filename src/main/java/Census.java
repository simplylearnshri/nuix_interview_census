import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * Implement the two methods below. We expect this class to be stateless and thread safe.
 */
public class Census {
    /**
     * Number of cores in the current machine.
     */
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    /**
     * Output format expected by our tests.
     */
    public static final String OUTPUT_FORMAT = "%d:%d=%d"; // Position:Age=Total

    /**
     * Factory for iterators.
     */
    private final Function<String, Census.AgeInputIterator> iteratorFactory;

    /**
     * Creates a new Census calculator.
     *
     * @param iteratorFactory factory for the iterators.
     */
    public Census(Function<String, Census.AgeInputIterator> iteratorFactory) {
        this.iteratorFactory = iteratorFactory;
    }

    /**
     * Given one region name, call {@link #iteratorFactory} to get an iterator for this region and return
     * the 3 most common ages in the format specified by {@link #OUTPUT_FORMAT}.
     */
    public String[] top3Ages(String region) {

        AgeInputIterator iterator = iteratorFactory.apply(region);

        Top3Population result = new Top3Population();
        try {

            Region censusRegion = new Region();
            while (iterator.hasNext()) {

                Integer age = iterator.next();
                if (age >= 0) {
                    censusRegion.addAge(age);
                    result.addAge(age);
                }
            }
            iterator.close();
            result.getTop3Ages();


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result.getCensusReport();

    }

    /**
     * Given a list of region names, call {@link #iteratorFactory} to get an iterator for each region and return
     * the 3 most common ages across all regions in the format specified by {@link #OUTPUT_FORMAT}.
     * We expect you to make use of all cores in the machine, specified by {@link #CORES).
     */
    public String[] top3Ages(List<String> regionNames) {

        Top3Population result = new Top3Population();
        for (String regionName : regionNames) {
            AgeInputIterator iterator = iteratorFactory.apply(regionName);
            Region censusRegion = new Region();
            while (iterator.hasNext()) {

                Integer age = iterator.next();
                if (age >= 0) {
                    result.addAge(age);

                }
            }
            try {
                iterator.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        result.getTop3Ages();
        return result.getCensusReport();
    }


    /**
     * Implementations of this interface will return ages on call to {@link Iterator#next()}. They may open resources
     * when being instantiated created.
     */
    public interface AgeInputIterator extends Iterator<Integer>, Closeable {
    }
}
