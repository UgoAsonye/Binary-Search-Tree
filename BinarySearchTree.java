public class BinarySearchTree<E extends Comparable<E>> implements SimpleSSet<E> {
    private int size = 0;
    private Node<E> root;

    public BinarySearchTree() {

        size = 0;
    }

    class Node<E> {
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        E data;

        public Node(E data, Node<E> parent) { //constructor
            this.data = data;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }
    }


    @Override
    public int size() {
//        return 0;
        return this.size;
    }

    @Override
    public boolean isEmpty() {
//        return false;
        return this.root == null;
    }

    @Override
    public boolean add(E x) {
////        return false;
//        Node<E> n = this.root; //first value we want for n
//        while (n != null) {
//            E y = n.data; //the data in n
//            if (x.equals(y)) {
//                return false; //if y already in set, then return false
//            }
//            n = n.left; //we should look to the left if we don't find what we're looking for
//        }
//        if (right == null) {  //if nothing to th right, add a node to the right
//            this.right = new Node<E>(x);
////            right = right.right;
//        } else { //if not,
//            parent.right = new Node<E>(x);
////            this.right = right.right;
        if (this.root == null) {
            this.root = new Node<E>(x, null);
            this.size++;
            return true;
        }
        Node<E> n = this.root;

        while (n != null) {
            if (x.compareTo(n.data) == 0) {
                return false;
            } else if (x.compareTo(n.data) < 0) {
                if (n.left == null) {
                    n.left = new Node<E>(x, n);
                    size++;
                    return true;
                }
                n = n.left;
            } else if (x.compareTo(n.data) > 0) {
                if (n.right == null) {
                    n.right = new Node<E>(x, n);
                    size++;
                    return true;
                }
                n = n.right;
            }
        }
        System.out.println("Reached end of loop in add, shouldn't happen. Uh oh!");
        return true;
    }

    @Override
    public E remove(E x) {
        //3 situations: either remove a bottom one, remove one with a child (it's child takes its place),if there's 2 kids: replace with largest element in left subtree or smallest in right subtree (maybe do rightmost from left node. Copy value and remove the node.

        Node<E> m = this.root;
        //--------------
        if (this.isEmpty()) {
            return null;
        }
        while (m != null) {
//            E y = p.data;
            if (x.equals(m.data)) {
                if (m.left == null && m.right == null) {//removing a node without children
                    if (m.parent.left == m)
                        m.parent.left = null;
                    else {
                        m.parent.right = null;
                    }
                }
                if (m.left == null && m.right != null) { //removing a node with a child to the right
                    if(m == m.parent.left) {
                        m.parent.left = m.right; //this sets the child node of what's being removed to take place of removed node
                        m.right.parent = m.parent; //this sets the parent of the replacement to be the parent of the replacement
                    }
                    else {
                        m.parent.right = m.right; //this sets the child node of what's being removed to take place of removed node
                        m.right.parent = m.parent; //this sets the parent of the replacement to be the parent of the replacement
                    }
                }
                if (m.left != null && m.right == null) { //removing a node with a child to the left
                    if(m == m.parent.left) {
                        m.parent.left = m.left;
                        m.left.parent = m.parent;
                    }
                    else{
                        m.parent.right = m.left;
                        m.left.parent = m.parent;
                    }
                }
                if (m.left != null && m.right != null) { //removing a node with 2 kids
                    if (m.parent.left == m) { //if the node to be removed is to the left of its parent
                        m.parent.left = m.right; //sets child node of what will be removed to take place of removed node
                        m.right.parent = m.parent; //sets the parent of the replacement node to be the parent of the removed node
                        Node<E> n = m.right; //create a new general node (for the loop) Imagine it being to the right of m. After the loop, n is the leftmost node that's right of m
                        while (n.left != null) { //while the general node's left isn't null...
                            n = n.left; //look at the next node to the left
                        }
                        n.left = m.left; //move removed node's left children to leftmost spot in removed node's right subtree
                        m.left.parent = n; //update parent

                    } else { //if m.parent.right==m
                        m.parent.right = m.left;
                        m.left.parent = m.parent;
                        Node<E> o = m.left;
                        while (o.right != null) {
                            o = o.right;
                        }
                        o.right = m.right;
                        m.right.parent = o;
                    }
                }
                this.size--;
                return m.data;


            } else if (x.compareTo(m.data) < 0) { //we look to the left

                m = m.left;
            } else if (x.compareTo(m.data) > 0) {

                m = m.right;
            }

        }
        return null;
    }


    @Override
    public E find(E x) {
        Node<E> n = this.root; //making a node, n that is the root of the tree
        while (n != null) { //while the node we're looking at isn't null...
            if (x.compareTo(n.data) < 0) { //we look to the left
                if (n.left == null)
                    return n.data;
                n = n.left; //set the node to the left as th new node we're looking at

            } else if (x.compareTo(n.data) > 0) { //if not looking to left, look to the right
                if (n.right == null) { //if nothing is to the right
                    Node<E> m = n.parent; //makes a new node as parent of n
                    while (m != null) {
                        if (x.compareTo(m.data) < 0) //if it's less than what you're looking at, return it
                            return m.data;
                        if (x.compareTo(m.data) > 0) //if it's grater than what you're looking at, look at the parent
                            m = m.parent;
                    }
                    return null;
                }
                n = n.right; //set node to right as new node we're looking at
            } else //if neither condition (is equal)
                return n.data; //return data stored in n

        }
        return null; //we return null because that means the node we're looking for isn't inthe tree. It's Null.
    }


    @Override
    public E findMin() {
        if (this.isEmpty()) {
            return null;
        }
        Node<E> n = this.root;
        while (n.left != null) { //aws long as n has a left
            n = n.left; //we're now looking at what's to the left of n
        }
        return n.data;
    }

    @Override
    public E findMax() {
        if (this.isEmpty()) {
            return null;
        }
        Node<E> n = this.root;
        while (n.right != null) {
            n = n.right;
        }
        return n.data;
    }

    public int height() {
        return nodeHeight(this.root);
    }

    public int nodeHeight(Node root) {
        if (root == null) { //base case which ends recursion
            return -1;
        }
        return Math.max(nodeHeight(root.left), nodeHeight(root.right)) + 1; //gives you max or left or right, whatever is bigger

    }
}
