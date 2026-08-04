class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        List<Integer> result=new ArrayList<>();
        HashSet<Integer> set=new HashSet<>();

        for(int num :nums){
            set.add(num);
        }
        int smallest =nums[0];
        int largest =nums[0];

        for(int num : nums){
            if(num<smallest){
                smallest =num;
            }
            if(num >largest){
                largest =num;
            }
            
        }
        for(int i=smallest;i<= largest;i++){
            if(!set.contains(i)){
                result.add(i);
            }
        }
        return result;
    }
}
