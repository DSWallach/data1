interface BST {
    int cardinality();
}
class Branch implements BST {
    int iden;
    BST left;
    BST right;

    Branch (BST left, int iden, BST right){
	this.left = left;
	this.iden = iden;
	this.right = right;
	}
    public String toString(){
	return "new Branch("
	    + this.left + ", "
	    + this.iden + ", "
	    + this.right + ")";
    }
}
class Leaf implements BST {
    Leaf(){};
    public String toString(){
	return "Leaf";
    }
    public int cardinality(BST t){
	int count;
	
	return count;
    }				 
}
class Data1 {
    public static void main (String args[]){
    BST leaf = new Leaf();
    BST b7 = new Branch (leaf, 7, leaf);
    BST b6 = new Branch (leaf, 6, leaf);
    BST b5 = new Branch (leaf, 5, leaf);
    BST b4 = new Branch (leaf, 4, leaf);
    BST b3 = new Branch (b6, 3, b7);
    BST b2 = new Branch (b4, 2, b5);
    BST b1 = new Branch (b2, 1, b3);

    System.out.println ("Hello " + b1.toString() + "How are you?");
    }
}

			    
