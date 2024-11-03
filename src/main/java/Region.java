import java.util.*;
import java.util.stream.Collectors;

public class Region {
    private String region;
    private List<Integer> ages;


    private Map<Integer, Integer> ageFrequency = new HashMap<>();

    public Region(String region) {
        this.region = region;
    }

    public Region() {

    }

    public Map<Integer, Integer> getAgeFrequency() {
        return ageFrequency;
    }

    public void setAgeFrequency(Map<Integer, Integer> ageFrequency) {
        this.ageFrequency = ageFrequency;
    }
    public void addAge(int age) {
        ageFrequency.put(age, ageFrequency.getOrDefault(age, 0) + 1);
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
