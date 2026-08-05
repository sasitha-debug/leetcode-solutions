class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        // Graph: method -> list of methods it invokes
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] invocation : invocations) {
            int a = invocation[0];
            int b = invocation[1];

            graph.computeIfAbsent(a, x -> new ArrayList<>()).add(b);
        }

        // Find all suspicious methods using BFS
        Set<Integer> suspicious = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(k);
        suspicious.add(k);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            List<Integer> nextMethods = graph.getOrDefault(
                current,
                new ArrayList<>()
            );

            for (int next : nextMethods) {

                if (!suspicious.contains(next)) {
                    suspicious.add(next);
                    queue.offer(next);
                }
            }
        }

        // Check whether an outside method invokes a suspicious method
        for (int[] invocation : invocations) {
            int caller = invocation[0];
            int called = invocation[1];

            if (!suspicious.contains(caller)
                    && suspicious.contains(called)) {

                // Cannot remove suspicious methods
                List<Integer> answer = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    answer.add(i);
                }

                return answer;
            }
        }

        // Remove suspicious methods
        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious.contains(i)) {
                answer.add(i);
            }
        }

        return answer;
    }
}