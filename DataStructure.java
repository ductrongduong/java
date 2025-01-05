public class DataStructure {

}

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;
  TreeNode() {}
  TreeNode(int val) { this.val = val; }
  TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
  }
}

class AVLTree {
    class Node {
        int key, height, size;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1;
            size = 1;
        }
    }

    private Node root;

    // Get the height of a node
    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int size(Node n) {
        return n == null ? 0 : n.size;
    }

    // Get the balance factor of a node
    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // Right rotate
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        y.size = size(y.left) + size(y.right) + 1;
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    // Left rotate
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        x.size = size(x.left) + size(x.right) + 1;
        y.size = size(y.left) + size(y.right) + 1;

        return y;
    }

    public Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else {
            node.right = insert(node.right, key);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;

        int balance = getBalance(node);

        // Perform rotations if necessary
        // Left Left Case
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    public Node deleteNode(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteNode(root.right, temp.key);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        root.size = size(root.left) + size(root.right) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void delete(int key) {
        root = deleteNode(root, key);
    }

    public int getNumKeyBiggerOrEquals(Node node, int key) {
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

    public int getNumKeySmaller(Node node, int key) {
        if (node == null) {
            return 0;
        }

        if (node.key >= key) {
            return getNumKeySmaller(node.left, key);
        } else {
            return size(node.left) + 1 + getNumKeySmaller(node.right, key);
        }
    }

    public int getNumKeyBiggerOrEquals(int key) {
        return getNumKeyBiggerOrEquals(root, key);
    }

    public int getNumKeySmaller(int key) {
        return getNumKeySmaller(root, key);
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