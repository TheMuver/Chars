package com.muver.chars;

import java.util.*;

public class ReplaceEncoder {
    private final static char[][] CHARS = new char[][] {{'а', 'a'}, {'с', 'c'}, {'е', 'e'}, {'к', 'k'}, {'о', 'o'},
            {'р', 'p'}, {'г', 'r'}, {'х', 'x'}, {'у', 'y'}, {'А', 'A'}, {'В', 'B'}, {'С', 'C'}, {'Е', 'E'},
            {'Н', 'H'}, {'К', 'K'}, {'М', 'M'}, {'О', 'O'}, {'Р', 'P'}, {'Т', 'T'}, {'Х', 'X'}, {'У', 'Y'}};

    private static int check_usability(char ch) {
        for (int i = 0; i < CHARS.length; i += 1) {
            if (ch == CHARS[i][0] || ch == CHARS[i][1]) {
                return i;
            }
        }
        return -1;
    }

    private static ArrayDeque<Integer> to_2_sys(String mess) {
        ArrayDeque<Integer> n_sys = new ArrayDeque<>();
        final int N = 2;
        while (mess.length() > 0) {
            int t_sys = Integer.parseInt("1" + mess.substring(0, Math.min(9, mess.length())));
            while (t_sys > 0) {
                n_sys.addLast(t_sys % N);
                t_sys /= N;
            }
            mess = mess.substring(Math.min(9, mess.length()));
        }
        return n_sys;
    }

    private static String to_10_sys(ArrayDeque<Integer> n_sys) {
        String output = "";
        long t_sys = 0;
        int j = -1;
        final int length = n_sys.size();
        final int N = 2;
        for (int i = 0; i < length; i += 1) {
            j += 1;
            t_sys += n_sys.pop()*(Math.pow(N, j));
            if (Long.toString(t_sys).length() > 9) {
                output += Long.toString(t_sys).substring(1);
                t_sys = 0;
                j = -1;
            }
        }
        output += Long.toString(t_sys).substring(1);
        return output;
    }

    public static String encoding(String container, String mess) { // 0 - оставление, 1 - замена (текущая версия)
                                                            // или 0 - русская бука, 1 - английская буква?
        String output = "";
        ArrayDeque<Integer> nums = to_2_sys(mess);
        for (char ch:container.toCharArray()) {
            int i = check_usability(ch);
            if (i != -1) {
                if (nums.isEmpty()) {
                    output += CHARS[i][0];
                }
                else
                    output += CHARS[i][nums.poll()];
            }
            else
                output += ch;
        }
        return output;
    }

    public static String decoding(String container) { // TODO идея для проверки вместимости:
        String output = "";
        ArrayDeque<Integer> nums = new ArrayDeque<>();
        for (char ch: container.toCharArray()) {
            int i = check_usability(ch);
            if (i != -1) {
                for (int k = 0; k < CHARS[i].length; k += 1)
                    if (CHARS[i][k] == ch) {
                        nums.addLast(k);
                        break;
                    }
            }
        }
        output = to_10_sys(nums);
        return output;
    }

    public static String maxCapacity(String container) {
        ArrayDeque<Integer> nums = new ArrayDeque<>();
        for (char ch: container.toCharArray()) {
            int i = check_usability(ch);
            if (i != -1) {
                nums.add(1);
            }
        }
        return to_10_sys(nums);
    }
}
