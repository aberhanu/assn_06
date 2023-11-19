package assn06;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;
    
    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    /**
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */
     private AVLTree<T> rotateLeft() {
         // You should implement left rotation and then use this 
         // method as needed when fixing imbalances.
    	 // TODO
         AVLTree<T> old_root = this;
         AVLTree<T> new_root = _right;

         old_root._right = _right._left;
         int old_root_right_size = 0;
         int old_root_left_size = 0;
         int old_root_right_height = 0;
         int old_root_left_height = 0;

         if (old_root._right != null) {
             old_root_right_size = old_root._right.size();
             old_root_right_height = old_root._right.height();
         }
         if(old_root._left != null){
             old_root_left_size = old_root._left.size();
             old_root_left_height = old_root._left.height();
         }

         old_root._size = 1 + old_root_right_size + old_root_left_size;
         old_root._height = (Math.max(old_root_right_height, old_root_left_height)) + 1;


         new_root._left = old_root;
         int new_root_right_size = 0;
         int new_root_right_height = 0;

         if (new_root._right != null) {
             new_root_right_size = new_root._right.size();
             new_root_right_height = new_root._right.height();
         }


         new_root._size = 1 + new_root_right_size + new_root._left.size();
         new_root._height = (Math.max(new_root_right_height, new_root._left.height())) + 1;

         return new_root;
     }
    
    /**
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */
     private AVLTree<T> rotateRight() {
         // You should implement right rotation and then use this 
         // method as needed when fixing imbalances.
    	 // TODO
         AVLTree<T> old_root = this;
         AVLTree<T> new_root = _left;

         old_root._left = _left._right;
         int old_root_right_size = 0;
         int old_root_left_size = 0;
         int old_root_right_height = 0;
         int old_root_left_height = 0;

         if (old_root._right != null) {
             old_root_right_size = old_root._right.size();
             old_root_right_height = old_root._right.height();
         }
         if(old_root._left != null){
             old_root_left_size = old_root._left.size();
             old_root_left_height = old_root._left.height();
         }
         old_root._size = 1 + old_root_right_size + old_root_left_size;
         old_root._height = (Math.max(old_root_right_height, old_root_left_height)) + 1;

         new_root._right = old_root;
         int new_root_left_size = 0;
         int new_root_left_height = 0;

         if(new_root._left != null){
             new_root_left_size = new_root._left.size();
             new_root_left_height = new_root._left.height();
         }


         new_root._size = 1 + new_root._right.size() + new_root_left_size;
         new_root._height = (Math.max(new_root._right.height(), new_root_left_height)) + 1;

         return new_root;
     }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public SelfBalancingBST<T> insert(T element) {
    	// TODO
        AVLTree<T> result = this;
        if (this.isEmpty()) {
            _value = element;
            _size = 1;
            _height = 1;
        } else {
            if((element.compareTo(_value) < 0) && (_left != null )){
                _left = (AVLTree<T>) _left.insert(element);
            } else if (((element.compareTo(_value) >= 0)) && (_right != null)) {
                _right = (AVLTree<T>) _right.insert(element);
            } else if ((element.compareTo(_value) < 0) && ((_left == null))) {
                AVLTree<T> insertion = new AVLTree<T>();
                insertion._value = element;
                insertion._height = 1;
                insertion._size = 1;
                _left = insertion;

            } else if (((element.compareTo(_value) >= 0)) && ((_right == null))) {
                AVLTree<T> insertion = new AVLTree<T>();
                insertion._value = element;
                insertion._height = 1;
                insertion._size = 1;
                _right = insertion;
            }

            _size += 1;
            if (_size > (Math.pow(2,_height) - 1)) {
                _height += 1;
            }


            int left_height = 0;
            int right_height = 0;
            if (_left != null) {
                left_height = _left.height();
            }
            if (_right != null){
                right_height = _right.height();
            }

            if((Math.abs(left_height-right_height)) <= 1){
                return this;
            }

            AVLTree<T> left_child = _left;
            AVLTree<T> right_child = _right;

            int left_child_left_height = 0;
            int left_child_right_height = 0;
            int right_child_left_height = 0;
            int right_child_right_height = 0;

            if(left_child != null) {
                if(left_child._left != null){
                    left_child_left_height = left_child._left.height();
                }

                if(left_child._right != null){
                    left_child_right_height = left_child._right.height();
                }

            }
            if(right_child != null){

                if(right_child._left != null){
                    right_child_left_height = right_child._left.height();
                }
                if(right_child._right != null){
                    right_child_right_height = right_child._right.height();
                }
            }

            if(left_height - right_height > 1){
                AVLTree<T> smthn = new AVLTree<T>();
                smthn = this;
                if(left_child_left_height - left_child_right_height >= 1){
                    smthn = rotateRight();
                } else {
                    _left = _left.rotateLeft();
                    smthn = rotateRight();
                }
                return smthn;


            } else {
                AVLTree<T> smthn = new AVLTree<T>();
                smthn = this;
                if (right_child_right_height - right_child_left_height >= 1) {
                    smthn = rotateLeft();
                } else {
                    _right = _right.rotateRight();
                    smthn = rotateLeft();
                }
                return smthn;
            }
        }
        return this;
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
    	// TODO


        return null;
    }

    @Override
    public T findMin() {
         if (isEmpty()) {
             throw new RuntimeException("Illegal operation on empty tree");
         }
         if (_left.isEmpty()) {
             return _value;
         } else {
             return _left.findMin();
         }
    }

    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        if (_right.isEmpty()) {
            return _value;
        } else {
            return _right.findMax();
        }
    }

    @Override
    public boolean contains(T element) {
    	// TODO

        return false;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }
         return _right;
    }

}
