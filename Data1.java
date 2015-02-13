import java.util.Random;
// A FiniteSet is either...
interface FiniteSet {
    // cardinality : FiniteSet -> int
    int cardinality();
    // isEmptyHuh : FiniteSet -> boolean
    boolean isEmptyHuh();
    // member : FiniteSet int -> boolean
    boolean member(int elt);
    // add : FiniteSet int -> FiniteSet
    FiniteSet add(int elt);
    // remove : FiniteSet int -> FiniteSet
    FiniteSet remove(int elt);
    // union : FiniteSet FiniteSet -> FiniteSet
    FiniteSet union(FiniteSet t);
    // inter : FiniteSet FiniteSet -> FiniteSet
    FiniteSet inter(FiniteSet t);
    // diff : FiniteSet FiniteSet -> FiniteSet
    FiniteSet diff(FiniteSet t);
    // equal : FiniteSet FiniteSet -> boolean
    boolean equal(FiniteSet t);
    // subset : FiniteSet FiniteSet -> boolean
    boolean subset(FiniteSet t);
}
// - an empty set: empty
class Empty implements FiniteSet {
    Empty (){}
    public String toString(){
	return ("e");
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
    public FiniteSet add(int elt){
        return new Branch (this, elt, this);
    }
    public FiniteSet remove(int elt){
	return this;
    }
    public FiniteSet union(FiniteSet t){
	return t;
    }
    public FiniteSet inter(FiniteSet t){
	return new Empty();
    }
    public FiniteSet diff(FiniteSet t){
	return t;
    }
    public boolean equal(FiniteSet t){
	return t.isEmptyHuh();
    }
    public boolean subset(FiniteSet t){
	return t.isEmptyHuh();
    }
}
// - a Branch: (FiniteSet, int, FiniteSet)
class Branch implements FiniteSet {
    int iden;
    FiniteSet left;
    FiniteSet right;

    Branch (FiniteSet left, int iden, FiniteSet right){
	this.left = left;
	this.iden = iden;
	this.right = right;
    }
    public String toString() {
        return "(" +
            this.left + "," +
            this.iden + "," +
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
    public FiniteSet add(int elt){
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
    public FiniteSet remove(int elt){
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
    public FiniteSet union(FiniteSet t){
	FiniteSet newTree = t.union(this.left).union(this.right).add(this.iden);
	return newTree;
    }
    public FiniteSet inter(FiniteSet t){
	FiniteSet newTree;
	if (!t.member(this.iden)){
	    newTree = this.left.union(this.right);
	    newTree.inter(t);
	} else {
	    newTree = new Branch (this.left.inter(t),
				  this.iden,
				  this.right.inter(t));
	}
	return newTree;
    }
    public FiniteSet diff(FiniteSet t){
	return this.remove(this.iden).diff(t.remove(this.iden));
    }
    public boolean equal(FiniteSet t){
	return (t.subset(this) && this.subset(t));
    }
    public boolean subset(FiniteSet t){
	if(!t.member(this.iden)){
	    return false;
        } else if (this.left.isEmptyHuh() && this.right.isEmptyHuh()) {
	    return true;
	} else {
	    return this.remove(this.iden).subset(t);
	}
    }
}
class Data1 {
    public static FiniteSet randomFiniteSet(FiniteSet t, int numAdd, int rangeAdd){
	FiniteSet rFiniteSet = t;
	for (int i=0; i<numAdd;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(rangeAdd);
	    while (rFiniteSet.member(randInt)){
		newRandom = new Random();
		randInt = newRandom.nextInt(rangeAdd);
	    }
	    rFiniteSet = rFiniteSet.add(randInt);
	}
	return rFiniteSet;
    }
    public static String memberTest(FiniteSet t , int numTest, int rangeTest){
        int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    Random newRandomY = new Random();
	    Random newRandomX = new Random();
	    int y = newRandomY.nextInt(rangeTest);
	    int x = newRandomX.nextInt(rangeTest);
	    FiniteSet set = randomFiniteSet(t, numTest, rangeTest);
	    if (x < (rangeTest/2)){
		y = x;
	    } else {
		set = set.add(y);
	    }
	    if (set.add(x).member(y)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed+" tests passed. " + failed+" tests failed.");
    }
    public static String unionTest(FiniteSet t , int numTest, int rangeTest){
        int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    Random newRandom = new Random();
	    int x = newRandom.nextInt(rangeTest);
	    FiniteSet setA = randomFiniteSet(t, (numTest/2), rangeTest);
	    FiniteSet setB = randomFiniteSet(t, (numTest/2), rangeTest);
	    if (x>(rangeTest/2)){
		setA = setA.add(x);
	    } else {
		setB = setB.add(x);
	    }
	    if (setA.union(setB).member(x)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed+" tests passed. " + failed+" tests failed.");
    }
    public static void main (String args[]){
	//PREMADE TEST SETS 
	FiniteSet empty = new Empty();
	FiniteSet b0 = new Branch (empty, 0, empty);
	FiniteSet b1 = new Branch (empty, 1, empty);
	FiniteSet b3 = new Branch (empty, 3, empty);
	FiniteSet b5 = new Branch (empty, 5, empty);
	FiniteSet b8 = new Branch (empty, 8, empty);
	FiniteSet b7 = new Branch (b5, 7, b8);
	FiniteSet b6 = new Branch (b5, 6, b8);
	FiniteSet b2 = new Branch (b1, 2, b3);
	FiniteSet b2b = new Branch (b0, 2, b3);
	FiniteSet b4 = new Branch (b2, 4, b6);
	FiniteSet b4b = new Branch (b2b, 4, b7);
	
	//TESTS
	System.out.println ("RANDOMLY GENERATED TESTS");
	System.out.println (memberTest(empty, 50, 10000));
	System.out.println (unionTest(empty, 50, 10000));		    
	System.out.println ();
	System.out.println ("PREMADE TESTS");
	System.out.println (empty.cardinality()+ " Should be 0");
	System.out.println (b4.cardinality()+ " Should be 7");
	System.out.println (empty.isEmptyHuh() + " Should be true.");
	System.out.println (b1.isEmptyHuh() + " Should be false");
	System.out.println (b3.member(5) + " Should be false.");
	System.out.println (b4.member(6) + " Should be true");
        System.out.println ("Should contain {1,2,3,4,5,6,7,8}. Contains "+b4.add(7));
	System.out.println ("Should contain {1,2,3,5,6,8}. Contains "+b4.remove(4));
	System.out.println ("Should contain {1,2,3,5,6,8}. Contains "+b2.union(b6));
        System.out.println ("Should contain {2,3,4,5,8}.Contains "+b4b.inter(b4));
        System.out.println ("Should contain {2,3,4,5,8}. Contains "+b4.inter(b4b));
	System.out.println ("Should contain {0,1,7}. Contains "+b4.diff(b4b));
        System.out.println ("Should contain {0,1,7}. Contains "+b4b.diff(b4));
        System.out.println (b4.equal(b4)+ " Should be true");
        System.out.println (b4.equal(b4b)+ " Should be false");
	System.out.println (b2.subset(b4)+ " Should be true");
	System.out.println (b4.subset(b2)+ " Should be false");
    }
}



			    
