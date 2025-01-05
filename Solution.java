
class Solution {


    public int numTeams(int[] rs) {
        AVLTree tree1 = new AVLTree();
        AVLTree tree2 = new AVLTree();
        for (int r : rs) {
            tree2.insert(r);
        }

        int ans = 0;
        for (int i = 0; i < rs.length; i++) {
            tree2.delete(rs[i]);
            int l1 = tree1.getNumKeyBiggerOrEquals(rs[i]);
            int l2 = tree2.getNumKeyBiggerOrEquals(rs[i]);
            ans += l1 * (rs.length - i - 1 - l2) + (i - l1) * l2;
            tree1.insert(rs[i]);
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numTeams(new int[]{1, 2, 3, 4}));
    }

}
