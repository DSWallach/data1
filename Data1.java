interface BST {
    String toString();
    int cardinality();
    boolean isBranch();
    boolean isEmptyHuh();
    boolean member(int elt);
    BST add(int elt);
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
	String newString = ("" + this.iden);
	return newString;
    }
    public boolean isBranch(){
	return true;
    }
    public int cardinality(){
	int count = 0;
	if (this.left.isBranch()){
	    count = count + this.left.cardinality();
	}
	if (this.right.isBranch()){
	    count = count + this.right.cardinality();
	}
	count = count + 1;
	return count;
    }
    public boolean isEmptyHuh(){
	return false;
    }
    public boolean member(int elt){
	boolean memberHuh = false;
	if (elt == this.iden){
	    memberHuh = true;
	}
	if (this.left.isBranch() && memberHuh == false){
	    memberHuh = this.left.member(elt);
	    }
	if (this.right.isBranch() && memberHuh == false){
	    memberHuh = this.right.member(elt);
	}
	return memberHuh;
    }
      public BST add(int elt){
	BST empty = new Empty();
	BST temp = new Branch (empty, elt, empty);
      	if (this.left.isEmptyHuh()){
	    BST newSet = new Branch (temp, this.iden, this.right);
	}
	else if (this.right.isEmptyHuh()){
	    BST newSet = new Branch (this.left, this.iden, temp);
	}
	else{
	    this.left.add(elt);
	    }
	return newSet;
    }
}
class Empty implements BST {
    Empty (){}
    public String toString(){
	String newString = ("empty");
	return newString;
    }
    public int cardinality(){
	return 0;
    }
    public boolean isBranch(){
	return false;
    }
    public boolean isEmptyHuh(){
	return true;
    }
    public boolean member(int elt){
	return false;
    }
    public BST add(int elt){
	BST empty = new Empty();
        BST newSet = new Branch (empty, elt, empty);
	return newSet;
	}
}
class Data1 {
    public static void main (String args[]){
	BST empty = new Empty();
	BST notEmpty = empty.add(6);
	BST b7 = new Branch (empty, 7, empty);
	BST b6 = new Branch (empty, 6, empty);
	BST b5 = new Branch (empty, 5, empty);
	BST b4 = new Branch (empty, 4, empty);
	BST b3 = new Branch (b6, 3, b7);
	BST b2 = new Branch (b4, 2, b5);
	BST b1 = new Branch (b2, 1, b3);
	//TESTS
	System.out.println ("The cardinalty of this set is: " + empty.cardinality());
	System.out.println ("The cardinalty of this set is: " + b1.cardinality());
	System.out.println ("This set is empty is a " + empty.isEmptyHuh() + " statement");
	System.out.println ("This set is empty is a " + b1.isEmptyHuh() + " statement");
	System.out.println ("6 is a member of this set is a " + empty.member(6) + " statement");
	System.out.println ("6 is a member of this set is a " + b1.member(6) + " statement");
	System.out.println ("A set that was once " + empty.toString()
			    + " now contains a " + notEmpty.toString());
    }
}


			    
