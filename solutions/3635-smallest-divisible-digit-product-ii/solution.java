import java.util.*;

class Solution {
    public String smallestNumber(String num, long t) {

        String vornitexis = num;

        int[] primes = {2, 3, 5, 7};
        int[] primeCount = new int[4];

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                t /= primes[i];
                primeCount[i]++;
            }
        }

        if (t != 1) {
            return "-1";
        }

        int[][] factor = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {2, 0, 0, 0},
            {0, 0, 1, 0},
            {1, 1, 0, 0},
            {0, 0, 0, 1},
            {3, 0, 0, 0},
            {0, 2, 0, 0}
        };

        int[] total = new int[4];

        for (char c : num.toCharArray()) {
            int d = c - '0';

            for (int j = 0; j < 4; j++) {
                total[j] += factor[d][j];
            }
        }

        int[] required = getFactorCount(primeCount);

        if (sum(required) > num.length()) {
            return construct(required);
        }

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            boolean ok = true;

            for (int i = 0; i < 4; i++) {
                if (total[i] < primeCount[i]) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                return num;
            }

            firstZero = num.length();
        }

        int[] prefix = total.clone();

        for (int i = num.length() - 1; i >= 0; i--) {

            int d = num.charAt(i) - '0';

            for (int j = 0; j < 4; j++) {
                prefix[j] -= factor[d][j];
            }

            int space = num.length() - 1 - i;

            if (i > firstZero) {
                continue;
            }

            for (int bigger = d + 1; bigger <= 9; bigger++) {

                int[] remaining = new int[4];

                for (int j = 0; j < 4; j++) {
                    remaining[j] = Math.max(
                        0,
                        primeCount[j]
                            - prefix[j]
                            - factor[bigger][j]
                    );
                }

                int[] factors = getFactorCount(remaining);

                if (sum(factors) <= space) {

                    int ones = space - sum(factors);

                    StringBuilder ans = new StringBuilder();

                    ans.append(num, 0, i);
                    ans.append(bigger);

                    ans.append("1".repeat(ones));

                    ans.append(construct(factors));

                    return ans.toString();
                }
            }
        }

        required = getFactorCount(primeCount);

        int ones = num.length() + 1 - sum(required);

        return "1".repeat(ones) + construct(required);
    }

    private int[] getFactorCount(int[] count) {

        int[] result = new int[10];

        int count8 = count[0] / 3;
        int remaining2 = count[0] % 3;

        int count9 = count[1] / 2;
        int count3 = count[1] % 2;

        int count4 = remaining2 / 2;
        int count2 = remaining2 % 2;

        int count6 = 0;

        if (count2 == 1 && count3 == 1) {
            count2 = 0;
            count3 = 0;
            count6 = 1;
        }

        if (count3 == 1 && count4 == 1) {
            count2 = 1;
            count6 = 1;
            count3 = 0;
            count4 = 0;
        }

        result[2] = count2;
        result[3] = count3;
        result[4] = count4;
        result[5] = count[2];
        result[6] = count6;
        result[7] = count[3];
        result[8] = count8;
        result[9] = count9;

        return result;
    }

    private int sum(int[] arr) {
        int total = 0;

        for (int x : arr) {
            total += x;
        }

        return total;
    }

    private String construct(int[] factors) {

        StringBuilder sb = new StringBuilder();

        for (int digit = 2; digit <= 9; digit++) {
            sb.append(String.valueOf(digit).repeat(factors[digit]));
        }

        return sb.toString();
    }
}
