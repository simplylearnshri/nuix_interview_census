import java.util.*;
import java.util.stream.Collectors;

public class Top3Population {
    private Map<Integer, List<Map<Integer, Integer>>> resultTop3 = new HashMap<>();

    private Map<Integer, Integer> ageFrequency = new HashMap<>();

    public void addAge(int age) {
        ageFrequency.put(age, ageFrequency.getOrDefault(age, 0) + 1);
    }

    public void getTop3Ages() {

        List<Integer> topCounts =
                ageFrequency.values().stream()
                        .sorted(Comparator.reverseOrder())
                        .distinct()
                        .limit(3)
                        .collect(Collectors.toList());


        for (Map.Entry<Integer, Integer> entry : ageFrequency.entrySet()) {

            int position = topCounts.indexOf(entry.getValue()) + 1;
            if (position <= 3 && position > 0) {
                resultTop3.computeIfAbsent(position, k -> new ArrayList<>())
                        .add(Collections.singletonMap(entry.getKey(), entry.getValue()));
            }
        }


    }



    public String[] getCensusReport(){

        List<String> result = new ArrayList<>();
        resultTop3.forEach((pos, list) -> {

            list.forEach(map -> map.forEach((age, count) ->  result.add( String.format(Census.OUTPUT_FORMAT, pos, age, count))));
        });
        return result.toArray(new String[0]);
    }
}
