import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<List<String>> list = readValues();
        System.out.println(list.get(0));
        List<Integer> valueList = new ArrayList<Integer>();
        List<Integer> weightList = new ArrayList<Integer>();
        for (int i = 1; i < list.size(); i++) {
            valueList.add(Integer.parseInt(list.get(i).get(4)));
            weightList.add(Integer.parseInt(list.get(i).get(5)));
        }
        int[] values = new int[valueList.size()];
        int[] weights = new int[weightList.size()];
        for (int i = 0; i < valueList.size(); i++) {
            values[i] = valueList.get(i);
            weights[i] = weightList.get(i);
        }
        List<List<String>> list1 = readSequential();
        List<ArrayList<Double>> sequential_data = new ArrayList<ArrayList<Double>>();
        for (int i = 1; i < list1.size(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 1; j < list1.get(0).size(); j++) {
                row.add(Double.parseDouble(list1.get(i).get(j)));
            }
            sequential_data.add(row);
        }
        System.out.println(sequential_data.get(0).get(1));
        knapsack(1800000, weights, values, sequential_data);
    }

    public static List<List<String>> readValues() throws IOException {
        try {
            List<List<String>> data = new ArrayList<>();//list of lists to store data
            String file = "C:\\Users\\Ata\\IdeaProjects\\Project343\\src\\term_project_value_data.csv";//file path
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Reading until we run out of lines
            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));//splitting lines
                data.add(lineData);
                line = br.readLine();
            }

            //printing the fetched data
            for (List<String> list : data) {
                for (String str : list)
                    System.out.print(str + " ");
                System.out.println();
            }
            br.close();
            return data;
        } catch (Exception e) {
            System.out.print(e);
            List<List<String>> data = new ArrayList<>();//list of lists to store data
            return data;
        }

    }

    public static List<List<String>> readSequential() throws IOException {
        try {
            List<List<String>> data = new ArrayList<>();//list of lists to store data
            String file = "C:\\Users\\Ata\\IdeaProjects\\Project343\\src\\term_project_sequential_data.csv";//file path
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Reading until we run out of lines
            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));//splitting lines
                data.add(lineData);
                line = br.readLine();
            }
            //printing the fetched data
            for (List<String> list : data) {
                for (String str : list)
                    System.out.print(str + " ");
                System.out.println();
            }
            br.close();
            return data;
        } catch (Exception e) {
            //   System.out.print(e);
            List<List<String>> data = new ArrayList<>();//list of lists to store data
            return data;
        }

    }

    public static void knapsack(int capacity, int[] weights, int[] values, List<ArrayList<Double>> sequential_data) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        boolean[][] isSelected = new boolean[n + 1][capacity + 1];

        // Build the table in bottom-up manner
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                    isSelected[i][j] = false;
                } else if (weights[i - 1] <= j) {
                    int val1 = values[i - 1] + dp[i - 1][j - weights[i - 1]];
                    int val2 = dp[i - 1][j];
                    if (val1 > val2) {
                        dp[i][j] = val1;
                        isSelected[i][j] = true;
                    } else {
                        dp[i][j] = val2;
                        isSelected[i][j] = false;
                    }
                } else {
                    dp[i][j] = dp[i - 1][j];
                    isSelected[i][j] = false;
                }
            }
        }
        // Find the included items
        List<Integer> items = new ArrayList<>();
        int i = n;
        int j = capacity;
        while (i > 0 && j > 0) {
            if (isSelected[i][j]) {
                items.add(i - 1);
                j -= weights[i - 1];
            }
            i--;
        }

        // Reverse the list to get the items in the correct order
        System.out.print("The included items are: ");
        for (int k = items.size() - 1; k >= 0; k--) {
            System.out.print(items.get(k) + " ");
        }
        System.out.println();

        // Decrease the solution by 0.02 times the remaining capacity
        double solution = dp[n][capacity] - 0.02 * ((capacity / (double) Arrays.stream(weights).sum()));
        System.out.println("Objective value: " + (int) solution);
        //part 2
        List<Double> tracklistValues = new ArrayList<>();
        List<Integer> from = new ArrayList<>();
        List<Integer> to = new ArrayList<>();
        for (int o = 0; o < items.size(); o++) {
            for (int p = 0; p < items.size(); p++) {
                if (p != o) {
                    tracklistValues.add(sequential_data.get(items.get(o)).get(items.get(p)));
                    from.add(items.get(o));
                    to.add(items.get(p));
                }
            }
        }
        System.out.println("All possible combinations :");
        for (int o = 0; o < tracklistValues.size(); o++) {
            System.out.print(tracklistValues.get(o) + " ");
        }
        System.out.println();
        System.out.println("From : ");
        for (int o = 0; o < from.size(); o++) {
            System.out.print(from.get(o) + " ");
        }
        System.out.println();
        System.out.println("To : ");
        for (int o = 0; o < to.size(); o++) {
            System.out.print(to.get(o) + " ");
        }
        List<Double> trackListDesc = new ArrayList<>();
        for (int o = 0; o < tracklistValues.size(); o++) {
            trackListDesc.add(tracklistValues.get(o));
        }
        Collections.sort(trackListDesc);
        Collections.reverse(trackListDesc);
        System.out.println();
        System.out.println("New sorted array :");
        for (int o = 0; o < trackListDesc.size(); o++) {
            System.out.print(trackListDesc.get(o) + " ");
        }
        System.out.println();
        // System.out.println("Optimal Tracklist is : ");
        List<Integer> optimalTrackList = new ArrayList<>();
        optimalTrackList.add(from.get(tracklistValues.indexOf(trackListDesc.get(0))));
        optimalTrackList.add(to.get(tracklistValues.indexOf(trackListDesc.get(0))));
        for (int m = 1; m < tracklistValues.size(); m++) {
            if ((from.get(tracklistValues.indexOf(trackListDesc.get(m))) == optimalTrackList.get(optimalTrackList.size() - 1)) && !optimalTrackList.contains(to.get(tracklistValues.indexOf(trackListDesc.get(m))))) {
                //optimalTrackList.add(from.get(tracklistValues.indexOf(trackListDesc.get(m))));
                optimalTrackList.add(to.get(tracklistValues.indexOf(trackListDesc.get(m))));
            }
        }
        List<Double> totalValues = new ArrayList<>();
        List<Integer> TrackList = new ArrayList<>();
        double totalValue=0;
        double value=0;
        for (int s = 0; s < 10000000; s++) {
            totalValue=0;
            value=0;
            for (int g = 0; g < items.size() - 1; g++) {
                value += sequential_data.get(items.get(g)).get(items.get(g + 1));
            }
            if (value > totalValue && s == 0) {
                totalValue = value;
                for (int z = 0; z < items.size(); z++) {
                    TrackList.add(items.get(z));
                }
            }
            if (value > totalValue && s != 0) {
                totalValue = value;
                for (int q = TrackList.size() - 1; q >= 0; q--) {
                    TrackList.remove(items.get(q));
                }
                for (int k = 0; k < items.size(); k++) {
                    TrackList.add(items.get(k));
                }
            }
            totalValues.add(totalValue);
            Collections.shuffle(items);
        }
        Collections.sort(totalValues);
        Collections.reverse(totalValues);
        System.out.println("Tracklist: ");
        for (int h = 0; h < TrackList.size(); h++) {
            System.out.print(TrackList.get(h) + " ");
        }
        System.out.println();
        System.out.println("Value of tracklist is : " +totalValue);
    }
    }
