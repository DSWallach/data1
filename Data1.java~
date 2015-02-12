// A BST is either...
interface BST {
    // cardinality : BST -> int
    int cardinality();
    // isEmptyHuh : BST -> boolean
    boolean isEmptyHuh();
    // member : BST int -> boolean
    boolean member(int elt);
    // add : BST int -> BST
    BST add(int elt);
    // remove : BST int -> BST
    BST remove(int elt);
    // union : BST BST -> BST
    BST union(BST t);
    // inter : BST BST -> BST
    BST inter(BST t);
    // diff : BST BST -> BST
    BST diff(BST t);
    // equal : BST BST -> boolean
    boolean equal(BST t);
}
// - an empty set: empty
class Empty implements BST {
    Empty (){}
    public String toString(){
	return ("empty");
    }
    public int cardinality(){
	return 0;
    }
    public boolean isEmptyHuh(){
	return true;
    }
    public boolean member(int elt){
	return false;
    }
    public BST add(int elt){
        return new Branch (this, elt, this);
    }
    public BST remove(int elt){
	return this;
    }
    public BST union(BST t){
	return t;
    }
    public BST inter(BST t){
	return new Empty();
    }
    public BST diff(BST t){
	return this;
    }
    public boolean equal(BST t){
	return t.isEmptyHuh();
    }
}
// - a Branch: (BST, int, BST)
class Branch implements BST {
    int iden;
    BST left;
    BST right;

    Branch (BST left, int iden, BST right){
	this.left = left;
	this.iden = iden;
	this.right = right;
    }
    public String toString() {
        return "(" +
            this.left + ", " +
            this.iden + ", " +
            this.right + ")";
    }
    public int cardinality(){
	return 1 + this.left.cardinality() + this.right.cardinality();
    }
    public boolean isEmptyHuh(){
	return false;
    }
    public boolean member(int elt){
	if (elt == this.iden){
	    return true;
	} else {
	    return (this.left.member(elt) || this.right.member(elt));
	}
    }
    public BST add(int elt){
	if (elt == this.iden){
	    return this;
	} else if (elt < this.iden){
	    return new Branch (this.left.add(elt),
			       this.iden,
			       this.right);
	} else{
	    return new Branch (this.left,
			       this.iden,
			       this.right.add(elt));
	}
    }
    public BST remove(int elt){
	if (elt == this.iden){
	    return this.left.union(this.right);
	} else if (elt < this.iden){
	    return new Branch (this.left.remove(elt),
			       this.iden,
			       this.right);
	} else {
	    return new Branch (this.left,
			       this.iden,
			       this.right.remove(elt));
	}			       	   
    }
    public BST union(BST t){
	BST newTree = t.union(this.left).union(this.right).add(this.iden);
	return newTree;
    }
    public BST inter(BST t){
	BST newTree = t;
	if (!t.member(this.iden)){
	    newTree.remove(this.iden);
	} 
	this.left.inter(newTree);
	this.right.inter(newTree);
	return newTree;	    
    }
    public BST diff(BST t){
	BST newTree = t;
	if (t.member(this.iden)){
	    newTree.remove(this.iden);
	}
	else {
	    this.left.diff(newTree);
	    this.right.diff(newTree);
	}
	return newTree;	
    }
    public boolean equal(BST t){
	//Temperary return so the rest of the code will run
	return true;//(t.member(this.iden) &&
    }	
}
class Data1 {
    public static void main (String args[]){
	    //for testing purposes
	BST empty = new Empty();
	BST b1 = new Branch (empty, 1, empty);
	BST b3 = new Branch (empty, 3, empty);
	BST b5 = new Branch (empty, 5, empty);
	BST b8 = new Branch (empty, 8, empty);
	BST b6 = new Branch (b5, 6, b8);
	BST b2 = new Branch (b1, 2, b3);
	BST b4 = new Branch (b2, 4, b6);
	
	//TESTS
	// System.out.println ("The cardinalty of this set is: " + empty.cardinality());
	// System.out.println ("The cardinalty of this set is: " + b4.cardinality());
	// System.out.println ("This set is empty is: " + empty.isEmptyHuh() + ". It should be: true.");
	// System.out.println ("This set is empty is: " + b1.isEmptyHuh() + ". It should be: false");
	// System.out.println ("6 is a member of this set is: " + b3.member(5) + ". It should be: false.");
	// System.out.println ("6 is a member of this set is: " + b4.member(6) + ". It should be: true");
	// System.out.println (b4.add(7).toString()+ " Should contain: {1,2,3,4,5,6,7,8}");
	System.out.println (b4.remove(4).toString()+ " Should contain: {1,2,3,6,5,8}");
	System.out.println (b2.union(b6).toString()+ " Should contain: {1,2,3,5,6,8}");
	System.out.println (b4.inter(b6).toString()+ " Should contain: {5,6,8}");
	System.out.println (b6.inter(b4).toString()+ " Should contain: {5,6,8}");
	System.out.println (b4.diff(b6).toString()+ " Should contain: {1,2,3,4}");
    }
}


			    
