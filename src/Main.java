import java.util.*;

import java.lang.*;

class Solution {

    private static char[] sChars;
    private static char[] sMatchedClosingChars;

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
        //convert  @param s to CharArray
        sChars = s.toCharArray();
        int length = sChars.length;
        //create a new CharArray to store matched closing braces
        sMatchedClosingChars = new char[length];
        boolean result = true;
        // check if the braces are even
        if (length % 2 == 0) {
            for (int x = 0; x < length; x++) {
                System.out.println(sChars[x]);
                boolean isClosingBrace = sChars[x] == ')' || sChars[x] == ']' || sChars[x] == '}';
                boolean isOpeningBrace = sChars[x] == '(' || sChars[x] == '[' || sChars[x] == '{';
//      if the first brace is a closing brace, return false because it is not closing anything
                if (isClosingBrace && x == 0) {
                    result = false;
                } else {

                    if (isOpeningBrace) {
                        if (result) {
                            result = isValidClosingBrace(length, x);
                        } else {
                            break;
                        }
//      if it is a closing brace, check if it matched an opening brace previously
                    } else if (isClosingBrace) {
                        if (sChars[x] == sMatchedClosingChars[x]) {
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
//  get the corresponding closing brace of the opening brace
        char closingChar = getClosingChar(sChars[x]);
        for (int i = x; i < length; i++) {
//  if this is the current opening brace is, check if it closes immediately
            if (i == x) {
                if (closingChar == sChars[i + 1]) {
//   if the closing brace matches with the opening,
//   add it to the matched @sMatchedClosingChars Array
                    sMatchedClosingChars[i + 1] = sChars[i + 1];
                    return true;
                } else {
//   if the first opening brace didn't close immediately, then
//   there must be  2 braces ( open and close) before it can validly be closed
                    i = i + 2;
                }
            } else {
                if (closingChar == sChars[i]) {
//   if the brace didn't math, then
//   there it must be closed first, thereby increasing the index
//   by 1( closing brace) before it can validly be closed
                    sMatchedClosingChars[i] = sChars[i];
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