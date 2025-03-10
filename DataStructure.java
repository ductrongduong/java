import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataStructure {

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class BIT {
    int[] bit;

    public BIT(int size) {
        bit = new int[size + 1];
    }

    public void update(int i, int diff) {
        i++;
        while (i < bit.length) {
            bit[i] += diff;
            i += i & -i;
        }
    }

    public int getSum(int i) {
        i++;
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }

        return sum;
    }
}

class AVLTree {
    class Node {
        int key, height, size;
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
            this.size = 1;
        }

        void calculateHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        void calculateSize() {
            this.size = size(this.left) + size(this.right) + 1;
        }

        void setLeft(Node node) {
            this.left = node;
            calculateHeight();
            calculateSize();
        }

        void setRight(Node node) {
            this.right = node;
            calculateHeight();
            calculateSize();
        }
    }

    Node root;

    int height(Node node) {
        return node == null ? 0 : node.height;
    }

    int size(Node node) {
        return node == null ? 0 : node.size;
    }

    Node rotateLeft(Node node) {
        Node currRoot = node.left;
        node.setLeft(node.left.right);
        currRoot.setRight(node);
        return currRoot;
    }

    Node rotateRight(Node node) {
        Node currRoot = node.right;
        node.setRight(node.right.left);
        currRoot.setLeft(node);
        return currRoot;
    }

    Node balance(Node node) throws Exception {
        int balanceValue = height(node.left) - height(node.right);
        if (balanceValue >= -1 && balanceValue <= 1) {
            return node;
        }

        if (balanceValue == -2) {
            int balanceRightValue = height(node.right.right) - height(node.right.left);
            if (balanceRightValue == -1) {
                node.setRight(rotateLeft(node.right));
                return rotateRight(node);
            } else if (balanceRightValue > -1 && balanceRightValue < 2) {
                return rotateRight(node);
            } else {
                throw new Exception("balanceRightValue Value Error: " + balanceRightValue);
            }
        } else if (balanceValue == 2) {
            int balanceLeftValue = height(node.left.left) - height(node.left.right);
            if (balanceLeftValue == -1) {
                node.setLeft(rotateRight(node.left));
                return rotateLeft(node);
            } else if (balanceLeftValue > -1 && balanceLeftValue < 2) {
                return rotateLeft(node);
            } else {
                throw new Exception("balanceLeftValue Value Error: " + balanceLeftValue);
            }
        } else {
            throw new Exception("balanceValue Error: " + balanceValue);
        }
    }

    Node insert(Node node, int key) throws Exception {
        if (node == null) {
            return new Node(key);
        }

        if (key >= node.key) {
            node.setRight(insert(node.right, key));
        } else {
            node.setLeft(insert(node.left, key));
        }

        return balance(node);
    }

    void insert(int key) throws Exception {
        root = insert(this.root, key);
    }

    int getMinimumKey(Node node) {
        if (node.left == null) {
            return node.key;
        }
        return getMinimumKey(node.left);
    }

    Node deleteMinimun(Node node) throws Exception {
        if (node.left == null) {
            return node.right;
        }

        node.setLeft(deleteMinimun(node.left));
        return balance(node);
    }

    Node delete(Node node, int key) throws Exception {
        //we will code here
        if (node == null) {
            return null;
        }

        if (key > node.key) {
            node.setRight(delete(node.right, key));
            return balance(node);
        } else if (key < node.key) {
            node.setLeft(delete(node.left, key));
            return balance(node);
        }

        //if node.key == key
        if (node.right == null) {
            return node.left;
        } else if (node.right.left == null) {
            node.right.setLeft(node.left);
            return balance(node.right);
        }

        node.key = getMinimumKey(node.right);
        node.setRight(deleteMinimun(node.right));
        return balance(node);
    }

    void delete(int key) throws Exception {
        root = delete(root, key);
    }

    int getNumKeyBiggerOrEquals(Node node, int key) {
        //we will code here
        if (node == null) {
            return 0;
        }

        if (node.key == key) {
            return size(node.right) + 1;
        } else if (key > node.key) {
            return getNumKeyBiggerOrEquals(node.right, key);
        } else {
            return getNumKeyBiggerOrEquals(node.left, key) + size(node.right) + 1;
        }
    }

    public int getNumKeyBiggerOrEquals(int key) {
        return getNumKeyBiggerOrEquals(root, key);
    }
}

class QuickSelect {
    private int[] partition(int[] nums, int left, int right) {
        Random rand = new Random();
        int pivotIndex = left + rand.nextInt(right - left + 1); // Random pivot
        int pivotValue = nums[pivotIndex];
        // Move pivot to the end
        swap(nums, pivotIndex, right);
        int lt = left, gt = right, i = left;

        while (i <= gt) {
            if (nums[i] < pivotValue) {
                swap(nums, lt++, i++);
            } else if (nums[i] > pivotValue) {
                swap(nums, i, gt--);
            } else {
                i++;
            }
        }

        return new int[]{lt - 1, gt + 1};
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class UnionFind {
    int[] parent, rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // Path compression
        return parent[x];
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    List<Integer> primeFactors(int num) {
        List<Integer> factors = new ArrayList<>();
        for (int d = 2; d * d <= num; d++) {
            if (num % d == 0) {
                factors.add(d);
                while (num % d == 0) num /= d;
            }
        }
        if (num > 1) factors.add(num); // If num is a prime > 1, add it
        return factors;
    }
}