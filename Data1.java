import java.util.Random;
// A BST is either...
interface BST {
    // randomBST : int -> BST
    BST randomBST(int numAdd, int rangeAdd);
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
    // subset : BST BST -> boolean
    boolean subset(BST t);
}
// - an empty set: empty
class Empty implements BST {
    Empty (){}
    public String toString(){
	return ("e");
    }
    public BST randomBST(int numAdd, int rangeAdd){
	BST rBST = this;
	for (int i=0; i<numAdd;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(rangeAdd);
	    while (rBST.member(randInt)){
		newRandom = new Random();
		randInt = newRandom.nextInt(rangeAdd);
	    }
	    rBST = rBST.add(randInt);
	}
	return rBST;
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
	return t;
    }
    public boolean equal(BST t){
	return t.isEmptyHuh();
    }
    public boolean subset(BST t){
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
            this.left + "," +
            this.iden + "," +
            this.right + ")";
    }
    public BST randomBST(int numAdd, int rangeAdd){
	BST rBST = this;
	for (int i=0; i<numAdd;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(rangeAdd);
	    while (rBST.member(randInt)){
		newRandom = new Random();
		randInt = newRandom.nextInt(rangeAdd);
	    }
	    rBST = rBST.add(randInt);
	}
	return rBST;
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
	BST newTree;
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
    public BST diff(BST t){
	return this.remove(this.iden).diff(t.remove(this.iden));
    }
    public boolean equal(BST t){
	return (t.subset(this) && this.subset(t));
    }
    public boolean subset(BST t){
	if(!t.member(this.iden)){
	    return false;
        } else if (this.left.isEmptyHuh() && this.right.isEmptyHuh()) {
	    return true;
	} else {
	    return this.remove(this.iden).subset(t);
	}
    }
}
interface Test {
    String cardinalityTest(int numTests);
    String memberTest(int numTests);
    String addTest(int numTest);
    String removeTest(int numTest);
    String unionTest(int numTests);
    String interTest(int numTests);
    String diffTest(int numTests);
    String equalTest(int numTests);
    String subsetTest(int numTests);
    
}
class Tester implements Test{
    Tester(){};
    public String cardinalityTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBST = new Empty();
	    testBST = testBST.randomBST(i, 25);
	    if (testBST.cardinality()==i){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String memberTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBST = new Empty();
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(100);
	    testBST = testBST.randomBST(i, 25).add(randInt);
	    if (testBST.member(randInt)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String addTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(100);	    
	    BST testBST = new Empty();
	    testBST = testBST.randomBST(i,50).add(randInt);	    
	    if (testBST.member(randInt)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String unionTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(100);	    
	    BST testBST = new Empty();
	    BST testBSTb = new Empty();
	    testBST = testBST.randomBST(i,50);
	    testBSTb = testBSTb.add(randInt);	    
	    if (testBSTb.union(testBST).equal(testBST.add(randInt))){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String removeTest(int numTest){
	int passed = 0;
	int failed = 0;
	BST empty = new Empty();
	for (int i=0;i<numTest;i++){
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(100);
	    BST testBST = new Branch (empty, i, empty);
	    testBST = testBST.randomBST(i,50).remove(i);
	    if (testBST.member(i)){
	        failed++;
	    } else {
		passed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String interTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBSTsubset = new Empty();
	    testBSTsubset = testBSTsubset.randomBST(i, 25);
	    BST testBST = testBSTsubset.randomBST((i/2),50);
	    if (testBSTsubset.inter(testBST).equal(testBSTsubset)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
        public String diffTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBST = new Empty();
	    testBST = testBST.randomBST(i, 25);
	    BST testBSTb = new Empty();
	    testBST = testBSTb.randomBST((i/2),50);
	    BST testBSTc = testBST.union(testBSTb);
	    if (testBST.diff(testBSTc).equal(testBSTb)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String equalTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBST = new Empty();
	    Random newRandom = new Random();
	    int randInt = newRandom.nextInt(100);
	    testBST = testBST.randomBST(i, 25);
	    BST testBSTb = testBST;
	    if (testBSTb.equal(testBST)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
    public String subsetTest(int numTest){
	int passed = 0;
	int failed = 0;
	for (int i=0;i<numTest;i++){
	    BST testBSTsubset = new Empty();
	    testBSTsubset = testBSTsubset.randomBST(i, 25);
	    BST testBST = testBSTsubset.randomBST((i/2),50);
	    if (testBSTsubset.subset(testBST)){
		passed++;
	    } else {
		failed++;
	    }
	}
	return (passed + " tests passed. "+ failed +" tests failed.");
    }
}
class Data1 {
    public static void main (String args[]){
	//Premade Sets
	BST empty = new Empty();
	BST b0 = new Branch (empty, 0, empty);
	BST b1 = new Branch (empty, 1, empty);
	BST b3 = new Branch (empty, 3, empty);
	BST b5 = new Branch (empty, 5, empty);
	BST b8 = new Branch (empty, 8, empty);
	BST b7 = new Branch (b5, 7, b8);
	BST b6 = new Branch (b5, 6, b8);
	BST b6c = new Branch (empty, 6, b8);
	BST b5c = new Branch (empty, 5, b6c);
	BST b2 = new Branch (b1, 2, b3);
	BST b2b = new Branch (b0, 2, b3);
	BST b4 = new Branch (b2, 4, b6);
	BST b4b = new Branch (b2b, 4, b7);
	BST b4c = new Branch (b2, 4, b6c);

	//Randomly Generated Sets
	BST r0 = empty.randomBST(20, 100);
	BST r1 = r0;
	BST r2 = r0.add(120);
	BST r3 = b2.randomBST(5, 100);
	BST r4 = b2.randomBST(5, 100);
	
	//TEST
	Test Test = new Tester();
	System.out.println ("PREMADE TESTS");
	System.out.println (r0.equal(r1)+ " Should be true");
	System.out.println (r0.equal(r2)+ " Should be false");
	System.out.println (r3.inter(r4)+ " Should contain at least {1,2,3}");
	System.out.println (r3.diff(r4)+ " Should not contain {1,2,3}");
	System.out.println (empty.cardinality()+ " Should be 0");
	System.out.println (b4.cardinality()+ " Should be 7");
	System.out.println (empty.isEmptyHuh() + " Should be true.");
	System.out.println (b1.isEmptyHuh() + " Should be false");
	System.out.println (b3.member(5) + " Should be false.");
	System.out.println (b4.member(6) + " Should be true");
        System.out.println (b4.add(7)+ " Should contain {1,2,3,4,5,6,7,8}");
	System.out.println (b4.remove(4)+ " Should contain {1,2,3,5,6,8}");
	System.out.println (b2.union(b6)+ " Should contain {1,2,3,5,6,8}");
        System.out.println (b4b.inter(b4)+ " Should contain {2,3,4,5,8}");
        System.out.println (b4.inter(b4b)+ " Should contain {2,3,4,5,8}");
	System.out.println (b4.diff(b4b)+ " Should contain {0,1,7}");
        System.out.println (b4b.diff(b4)+ " Should contain {0,1,7}");
        System.out.println (b4.equal(b4)+ " Should be true");
        System.out.println (b4.equal(b4b)+ " Should be false");
        System.out.println (b4.equal(b4c)+ " Should be false");
	System.out.println (b2.subset(b4)+ " Should be true");
	System.out.println (b4.subset(b2)+ " Should be false");
	System.out.println ();
	System.out.println ("RANDOM TESTS");
	System.out.println (Test.cardinalityTest(20));
	System.out.println (Test.memberTest(20));
	System.out.println (Test.addTest(20));
	System.out.println (Test.removeTest(20));
	System.out.println (Test.unionTest(20));
	System.out.println (Test.interTest(20));
	System.out.println (Test.diffTest(20));
	System.out.println (Test.equalTest(20));
	System.out.println (Test.subsetTest(20));
    }
}


			    
