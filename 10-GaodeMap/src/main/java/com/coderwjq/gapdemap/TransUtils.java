package com.coderwjq.gapdemap;

/**
 * @Created by coderwjq on 2017/5/11 14:41.
 * @Desc
 */

public class TransUtils {

    public static String trans(String input) {
        if (input.length() < 3) {
            return "0";
        } else {
            input = input.substring(0, input.length() - 2);

            int extra = input.length() % 3;

            StringBuilder sb = new StringBuilder();

            int j = 0;
            for (int i = 0; i < input.length(); i++) {
                if (i < extra) {
                    sb.append(input.charAt(i));
                } else {
                    if (j % 3 == 0) {
                        sb.append(",");
                    }
                    sb.append(input.charAt(i));
                    j++;
                }
            }
            return sb.toString();
        }
    }
}
