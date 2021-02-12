package pjarosz.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine().trim().replace(" ", "");

        var stringInput = convertStringToList(input);

        var indexOfResult = new ArrayList<>();
        var priority = new ArrayList<>();
        String before = "0";
        String after = "0";

        for (int i = 0; i < (long) stringInput.size(); i++) {

            if (i > 0) {
                before = stringInput.get(i - 1);
            }
            if (i < (long) stringInput.size() - 1) {
                after = stringInput.get(i + 1);
            }

            switch (stringInput.get(i)) {
                case "/":
                    var result1 = Integer.parseInt(before) / Integer.parseInt(after);

                    int k = i + 2;
                    if (k > stringInput.size() - 1) {
                        priority.add(result1);
                        indexOfResult.add(i - 1);
                        break;
                    }

                    if (!stringInput.get(k).equals("/") && !stringInput.get(k).equals("*")) {
                        priority.add(result1);
                        indexOfResult.add(i - 1);
                    } else {
                        indexOfResult.add(i - 1);
                        for (k = i + 2; k < stringInput.size(); k += 2) {

                            if (stringInput.get(k).equals("/")) {
                                result1 = result1 / Integer.parseInt(stringInput.get(k + 1));
                                indexOfResult.add(k - 1);
                            }
                            if (stringInput.get(k).equals("*")) {
                                result1 = result1 * Integer.parseInt(stringInput.get(k + 1));
                                indexOfResult.add(k - 1);
                            }
                            if (!stringInput.get(k).equals("/") && !stringInput.get(k).equals("*")) {
                                break;
                            }
                        }
                        priority.add(result1);
                        i = k - 1;
                    }
                    break;
                case "*":
                    var result2 = Integer.parseInt(before) * Integer.parseInt(after);

                    int j = i + 2;
                    if (j > stringInput.size() - 1) {
                        priority.add(result2);
                        indexOfResult.add(i - 1);
                        break;
                    }

                    if (!stringInput.get(j).equals("/") && !stringInput.get(j).equals("*")) {
                        priority.add(result2);
                        indexOfResult.add(i - 1);
                    } else {
                        indexOfResult.add(i - 1);
                        for (j = i + 2; j < stringInput.size(); j += 2) {

                            if (stringInput.get(j).equals("/")) {
                                result2 = result2 / Integer.parseInt(stringInput.get(j + 1));
                                indexOfResult.add(j - 1);
                            }
                            if (stringInput.get(j).equals("*")) {
                                result2 = result2 * Integer.parseInt(stringInput.get(j + 1));
                                indexOfResult.add(j - 1);
                            }
                            if (!stringInput.get(j).equals("/") && !stringInput.get(j).equals("*")) {
                                break;
                            }
                        }
                        priority.add(result2);
                        i = j - 1;
                    }
                    break;
            }
        }
        int result = additionAndSubstration(stringInput, indexOfResult, priority, after);
        System.out.println(result);

    }

    private static int additionAndSubstration(List<String> stringInput, ArrayList<Object> indexOfResult, ArrayList<Object> priority, String after) {
        int result = 0;
        int g = 0;
        if (stringInput.get(0).equals("-") && (stringInput.get(2).equals("*") || stringInput.get(2).equals("/"))) {
            result = -Integer.parseInt(String.valueOf(priority.get(0)));
            priority.remove(0);
            g = 4;
        } else {
            if (stringInput.get(0).equals("-") && (stringInput.get(2).equals("-") || stringInput.get(2).equals("+"))) {
                result = -Integer.parseInt(stringInput.get(1));
                g = 2;
            } else {
                result = Integer.parseInt(stringInput.get(0));
            }
        }
        if (stringInput.get(1).equals("/") || stringInput.get(1).equals("*")) {
            g = 3;
            result = Integer.parseInt(String.valueOf(priority.get(0)));
            priority.remove(0);
        }

        for (int i = g; i < stringInput.size(); i++) {
            if (i < stringInput.size() - 1) {
                after = stringInput.get(i + 1);
            }

            switch (stringInput.get(i)) {
                case "+":
                    if (indexOfResult.contains(i + 1)) {
                        result += Integer.parseInt(String.valueOf(priority.get(0)));
                        priority.remove(0);
                        i += 3;
                        break;
                    }
                    result += Integer.parseInt(after);
                    break;
                case "-":
                    if (indexOfResult.contains(i + 1)) {
                        result -= Integer.parseInt(String.valueOf(priority.get(0)));
                        priority.remove(0);
                        i += 3;
                        break;
                    }
                    result -= Integer.parseInt(after);
                    break;
            }
        }
        return result;
    }

    private static List<String> convertStringToList(String input) {
        var stringInput = new ArrayList<String>();
        var indexOfFirstSign = -1;
        var startIteration = 0;

        if (input.charAt(0) == '-') {
            indexOfFirstSign = 0;
            startIteration = 1;
            stringInput.add(String.valueOf(input.charAt(0)));
        }

        for (int i = startIteration; i < input.length(); i++) {

            if (input.charAt(i) == '-' || input.charAt(i) == '+' ||
                    input.charAt(i) == '*' || input.charAt(i) == '/') {
                var indexOfNextSign = i;
                stringInput.add(input.substring(indexOfFirstSign + 1, indexOfNextSign));
                stringInput.add(String.valueOf(input.charAt(i)));
                indexOfFirstSign = indexOfNextSign;
                startIteration = indexOfFirstSign;
                i = startIteration;
            }
        }
        stringInput.add(input.substring(indexOfFirstSign + 1, input.length() - 1 + 1));

        return stringInput;


    }
}


