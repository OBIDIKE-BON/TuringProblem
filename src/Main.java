import java.util.*;

import java.lang.*;

class Solution {

    private static char[] sChars;
    private static char[] sMatchedChars;

    /**
     * @param ops - List of operations@return int Sum of scores after
     *            performing all operations
     */
    public static int calPoints(String[] ops) {
        int result = Integer.MIN_VALUE;

        if (ops.length >= 1 && ops.length <= 1000) {

            result = 0;

            ArrayList<Integer> scores = new ArrayList<>();

            int scoreCount = 0;
            int temp;
            for (String op : ops) {
                try {
                    temp = Integer.parseInt(op);
                    if (temp >= (-3 * 104) && temp <= (3 * 104)) {
                        scores.add(temp);
                        scoreCount++;
                        result = result + temp;
                    }
                } catch (Exception e) {

                    switch (op) {
                        case "+": {
                            if (scoreCount >= 2) {
                                temp = scores.get(scores.size() - 1)
                                        + scores.get(scores.size() - 2);
                                scores.add(temp);
                                result = result + temp;
                                scoreCount++;
                            }
                            break;
                        }
                        case "D": {
                            if (scoreCount >= 1) {
                                temp = scores.get(scores.size() - 1) * 2;
                                scoreCount++;
                                scores.add(temp);
                                result += temp;
                            }
                        }
                        break;

                        case "C": {
                            if (scoreCount >= 1) {
                                temp = scores.get(scores.size() - 1);
                                scores.remove(scores.size() - 1);
                                result -= temp;
                                scoreCount--;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }


    public static boolean isValid(String s) {
        sChars = s.toCharArray();
        int length = sChars.length;
        sMatchedChars = new char[length];
        boolean result = true;
        if (length % 2 == 0) {
            for (int x = 0; x < length; x++) {
                System.out.println(sChars[x]);
                boolean isClosingBrace = sChars[x] == ')' || sChars[x] == ']' || sChars[x] == '}';
                boolean isOpeningBrace = sChars[x] == '(' || sChars[x] == '[' || sChars[x] == '{';

                if (isClosingBrace && x == 0) {
                    result = false;
                } else {
                    if (isOpeningBrace) {
                        if (result) {
                            result = isValidClosingBrace(length, x);
                        } else {
                            break;
                        }
                    } else if (isClosingBrace) {
                        if (sChars[x] == sMatchedChars[x]) {
                            result = true;
                        } else {
                            result = false;
                            break;
                        }
                    }
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    private static boolean isValidClosingBrace(int length, int x) {

        char closingChar = getClosingChar(sChars[x]);
        for (int i = x; i < length; i++) {
            if (i == x) {
                if (closingChar == sChars[i + 1]) {
                    sMatchedChars[i + 1] = sChars[i + 1];
                    return true;
                } else {
                    i = i + 2;
                }
            } else {
                if (closingChar == sChars[i]) {
                    sMatchedChars[i] = sChars[i];
                    return true;
                } else {
                    i += 1;
                }
            }
        }
        return false;
    }

    private static char getClosingChar(char aChar) {
        switch (aChar) {
            case '(':
                return ')';
            case '[':
                return ']';
            default:
                return '}';
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String op = sc.nextLine();
        String[] ops = op.split(" ");

        System.out.println(Solution.calPoints(ops));

        String s = sc.nextLine();
        if (Solution.isValid(s)) {
            System.out.println("valid");
        } else {
            System.out.println("invalid");
        }
    }
}