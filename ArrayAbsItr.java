/**
* @author Austin Daigle
* @purpose: simple illustration of a dynamic array/vector (now with ADT
* support and iterators.)
* @version 1.2
* 
* The original methods for resize(), add(), set(), getLast(),
* getFirst(), elementAt(), DArray() have been modified to work
* with an abstract data type. 
* 
* Methods such as DArray(object) (copy constructor), isEqual, 
* remove(), iterator(), insert(), etc, have been added as new 
* features with compatabilty to the abstract data type class.
*  
*Edit History:
* Version 1.0: The whole program functions completely
* Version 1.1: Removed an unnecessary exception and fixed
* 	a few documentation comments.
* Version 1.2: Addded abstract Data type support, optimized a few methods
* 	added a copy constructor, a isEquals method, insert(), remove(), iterator()
* 	methods to the overall program.
*/ 

//////////////////////////////////////////////////////////
 //driver to test the dynamic array implementation
public class ArrayAbsItr{
    public static void main(String[] args) throws Exception{
    	//Run the given code to test out the drive class
        DArray<Integer> a = new DArray<Integer>(5);
        System.out.println(a.length());
        System.out.println(a.maxLength());
        //give it some values 
        a.set(0,5);
        a.set(1,4);
        a.set(2,3);
        a.set(3,2);
        a.set(4,1);
        System.out.println("Original content of a: " + a);
     
        //Illustration of iterator
        Iterator<Integer> itr = a.iterator();
        System.out.println("Iterator usage:");   
        while(itr.hasNext()){
          System.out.println(itr.next());
        }

        //create a new array from a
        DArray<Integer> b = new DArray<Integer>(a);
        System.out.println("Content of b: " + b);

        
        //testing equals method

        //check to see if the two arrays are equal      
        if(a.equals(b)){
          System.out.println("Array a is same as array b");
        }else{
          System.out.println("Array a is same as array b");
        }

        a.set(1,9);  //change the content of array a

        //print the content of both arrays
        System.out.println("Content of a: " + a);
        System.out.println("Content of b: " + b);
  	  
        //check to see if the two arrays are equal 
        if(a.equals(b)){
          System.out.println("Array a is same as array b");
        }else{
          System.out.println("Array a is NOT same as array b");
        }

    	
   }//end of main
} //end of class

/*
 * abstract based-class for Iterator
 */
abstract class Iterator<T>
{
    public abstract boolean hasNext();
    public abstract T next() throws Exception;
}

/**
 A specific iterator for dynamic array class 
*/
class DArrayAbsItr<T> extends Iterator<T>
{
	private DArray<T> storage; //points at the data structure
	private int curLoc; //points at the current location
 
	/**
	 * This method creates an abstract iterator object for the DArray
	 * object. 
	 * @param a This is the dynamic array object that is getting an 
	 * iterator object associated with it.
	 * @throws Throws an exception if the objected parsed is null.
	 */
	public DArrayAbsItr(DArray<T> a) throws Exception {
		//deal with invalid object and range errors here
		if(a == null) {
			throw new Exception("The object cannot be null to have an iterator.");
		}
		this.storage = a; //point at the same source
		this.curLoc = 0; //point at the first index
	}

	/**
	 * This method returns if the iterator has another object/value avaible.
	 * @return true if and only if the current location is in a valid 
	 * indexing range 
	 */
	public boolean hasNext(){
		//add in the difference of -1 to account for out of index errors
    return this.curLoc < this.storage.length(); 
	}

	/**
	 * This method returns the current index location of the 
	 * itorator and advances that location variable. 
	 * @return the element at the current index location and 
     * advance the current location to the next index slot
	 * @throws throws an exception then curLoc exceeds the length of storage
	 * object dynamic array thus resulting in an out-of-bounds error. 
	 */
	public T next() throws Exception
	{
		//Increment the variable curLoc by 1.
		curLoc++;
		/*
		 * return the the element at the current value of curLoc-1 held in the reference storage
		 * the -1 is added to statement of curLoc to account for out-of-bounds index errors.
		 */
		return this.storage.elementAt(curLoc-1);
	}
	
	/**
	 * This method resets the iterator cursor value to zero.
	 * This method is used to testing and debugging or to 
	 * reset the iterator object.
	 */
	public void reset()
	{
		//set curLoc to zero.
		curLoc = 0;
	}
}

class DArray<T>
{
	//constants
	private final double GROW_FACTOR = 0.5; // array size growing rate
	private final int DEFAULT_SIZE = 10; // default array constructor size
	
	//attributes
	private int size; //the array size that the user is aware of
	private T[] buffer; //the actual array
	
	/**
	 * Default constructor for creating dynamic arrays at the default size of 10.
	 */
	@SuppressWarnings("unchecked")
	public DArray()
	{
		//Set the field value to the constant value of 10.
		this.size = DEFAULT_SIZE;
		//Create an int variable to store the value from the size adjustment algorithm plus size. 
		int bufferSize = (int) Math.ceil(this.size + this.size * GROW_FACTOR);
		//Create the buffer[] with the default constant size of 10.
		this.buffer = (T[]) new Object[bufferSize];
	}
	
	/**
	 * This constructor creates an array with the capacity size of the
	 * int variable parsed into the constructor call plus the growth
	 * factor algorithm.
	 * @param size - the initial user requested size of the array
	 */
	@SuppressWarnings("unchecked")
	public DArray(int size) throws Exception
	{
		//Check to see if the size is below zero to throw an exception if needed.
		if(size < 0){
			throw new Exception("Not a valid range");
		}
		this.size = size; //the user's requested array size
		//Capacity is user's requested size plus equivalent grow portion
		int bufferSize = (int) Math.ceil(this.size + this.size * GROW_FACTOR);
		//create the actual array buffer
		this.buffer = (T[]) new Object[bufferSize];  
	}

	/**
	 * This method returns the length (used space on the buffer array).
     * @return the user's aware size of the array
     */
    public int length(){
    	return this.size;
    }
   
    /**
     * This method returns the max capacity (used space plus unused space
     * of the buffer array).
     * @return the max length/ the capacity of the dynamic array
     */
    public int maxLength(){
    	return this.buffer.length;
    }
   
    /**
     * This method returns the element at the value of the variable
     * index.
     * @param index - the location of the element in the array
     * @return the element at the given location/index
     * @exception throw an exception if the index is above or below the
     * size of the buffer array.
     */
    public T elementAt(int index) throws Exception
    {
    	//check to see if the parsed index is within bounds
    	if(index < 0 || index >= this.size){
    		throw new Exception("Index is out of bounds of array.");
    	}
    	return this.buffer[index];
   }
    
    /**
     * This method is a toString() method that returns all of the elements 
     * in the array.
     * @return: the content of the dynamic array as a string
     */   
    public String toString()
    {
    	String output = "[";
    	for(int i = 0; i < this.size; i++){
    		output += " " + this.buffer[i];
    	}
    	return output + " ]";
    }
    
	public void resize()
	{
		//Create an int variable to store the value from the size adjustment algorithm plus size. 
		int bufferSize = (int) Math.ceil(this.size + this.size * GROW_FACTOR);
		//Create an int variable store the value of the length of buffer and the size adjustment variable.
		int newSize = this.buffer.length + bufferSize;
		//Create a new int array to store all of the original data at the size of the new adjustment.
		@SuppressWarnings("unchecked")
		//Create a new generic array to resize the original array
		T[] resizedBuffer = (T[]) new Object[newSize];
		//Iterate the for loop for every item in the array buffer. 
		for(int i = 0; i < this.buffer.length; i++){
			/*
			 * update the element at i in the array resizedBuffer to the
			 * current value of buffer at the index at i. Basically copy all
			 * of the data from buffer to resizedBuffer. 
			 */
			resizedBuffer[i] = this.buffer[i];
		} 
		//Release the value of buffer, and update that to the value reference data of resizedBuffer.
		this.buffer = resizedBuffer;
	}
	
	//Resize method that is manual-triggered from the user or the set() method
	/**
	 * This method is the overloaded variant of resize, this method can be called
	 * by the user or automatically invoked by the set() method. This method resizes
	 * the buffer array to the value of newSize. 
	 * @param newSize This is an int value to that is used to resize the buffer array.
	 * @throws throws an exception if the resize new value is the same size of the 
	 * max length of the buffer.
	 */
	public void resize(int newSize) throws Exception {
		/*
		 * This if statement checks to see if the newSize variable is the same as the
		 * this.buffer.length variable to printout an error message if the user is 
		 * attempting to resize the buffer array to it current size. You cannot resize an
		 * object that is already at its current size, and since the user may not always 
		 * know the max size of the array before resizing it does not make since to crash the
		 * program with an exception.
		 */
		if(newSize == this.buffer.length) {
			System.out.println("Array cannot be resized to its already present max size.");
		}
		//If the newSize is larger then the max length value of buffer
		if(newSize < this.buffer.length){
			@SuppressWarnings("unchecked")
			//Create a new generic array to resize the original array
			T[] resizedBuffer = (T[]) new Object[newSize];
			//Iterate the for loop for the value of newSize
			for(int i = 0; i < newSize; i++){
				/*
				 * set the current index of resizedBuffer at i to the element of buffer
				 * at the value of i.
				 */
				resizedBuffer[i] = this.buffer[i];
			}
			//Release the data in buffer and set it to the data references of resizedBuffer.
			this.buffer = resizedBuffer;
			//Update the value of size to the adjusted smaller size
			this.size = newSize;
		}
		//If the value of newSize is less than the max size value of the array buffer.
		if(newSize > this.buffer.length){
			@SuppressWarnings("unchecked")
			//Create a new generic array to resize the original array
			T[] resizedBuffer = (T[]) new Object[newSize];
			//Iterate for the values at the buffer.length
			for(int i = 0; i < this.buffer.length; i++){
				//set the current value of resized buffer at i to the current value of buffer at i.
				resizedBuffer[i] = this.buffer[i];
			}
			//Release the data in buffer to the data referees of resizedBuffer.
			this.buffer = resizedBuffer;
		}
	}
	
	/**
	 * This method adds a new element to the buffer array's 
	 * "buffer" space, also the size field will be updated to
	 * reflect the entry of a new element into the array's "buffer"
	 * space.
	 * If the buffer array has run out of "buffer" space then
	 * call the resize method to resize the buffer array. 
	 * @param itemToInsert This is the element that is added to the array
	 */
	public void add(T itemToInsert) {
		/*
		 * If the value of size and max length are equal then resize
		 * the array, when this condition is met it means that the 
		 * array's "buffer" space is exceeded and it need to be resized
		 * to be able to store more entries. The maxLength() and maxLength()-1 are 
		 * the max size parameters that indicate that the given "buffer-space" memory
		 * has been exceeded and therefore should be adjusted.
		 */
		if(this.size == this.maxLength()-1 || this.size == this.maxLength()) {
			resize(); //automatically resize the buffer array.
		}
		this.size++; //update the value size to reflect the the addition of a new entry.
		//Insert the element itemToInsert to the index at the value of size in the array.
		this.buffer[this.size-1] = (T) itemToInsert;
	}
	
	
	
    /**
     * copy constructor - return a new instance of this class with the 
     * same content as the given DArray - make sure to copy the content over
     * @param from - a DArray object to be copied from
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public DArray(DArray a){
    	//set the value of buffer to the value of buffer a.
    	this.buffer = (T[]) a.buffer;
    	//set the value of size to the value of size a.
    	this.size = a.size;
    }
   
    /** 
     * This is an equals method. This method returns a true if all of the 
     * attributes and fields of the given DArray is the same of the content
     * in this array, otherwise it returns a false.
     * @return returns a true if both array are equal and false if not.
     * @param this is the object that is parsed into the method to be 
     * checked on if it is equal to another object or not.
     * @throws an exception can be through if the index parsed into 
     * elementAt() is out of bounds, however the method is designed to not
     * trigger this exception.
     */
    @SuppressWarnings("rawtypes")
	public boolean isEqual(DArray a) throws Exception{
    	//Check to see if the length() and maxLength() between a and this are not equal
    	if(a.size != this.size || a.length() != this.length()){
    		//return false since there is a difference in sizes for a and this object.
    		return false;
    	}
    	else {
    		/* Since it has been proven that length() and maxLength() are equal 
    		 * between a and this object, then loop through every object in a
    		 * and compare it at the same index at the index of this object.
    		 */
    		for(int i = 0; i < a.length(); i++){
    			/* compare the current element at a and this object,
    			 * if false then return false, if true then continue the
    			 * loop.
    			 */
    			if(a.elementAt(i) != this.elementAt(i)){
    				//if the element at i for a and this object is not equal then return false
    				return false;
    			}
    		}
    	}
    	//return true is the other inequality check are completed without returning a value.
    	return true;
    }
  
    /**
     * This method returns the first value of the buffer array.
     * @return the first element in the array.
     */
    public T getFirst(){
    	return this.buffer[0];
    }
    
    /**
     * This method returns the last value of the buffer array.
     * @return the last element in the array
     */
    public T getLast(){
    	return this.buffer[this.size];
    }
    
	/**
	 * This method sets an element into the given index.
	 * 
	 * If the index is within the array max length threshold,
	 * then, update the element at the index of the array.
	 * 
	 * If the index exceeds the max size of the array buffer,
	 * then, call the method resize() to automatically resize
	 * the array to the size of the exceeded given index and
	 * update the last value of that array to the element given.
	 * @param index this is the int value that of the element that will be updated.
	 * @param value This is the element that is going to be updating the array.
	 * @throws Exception Throws an error if the index value of below zero.
	 */
	public void set(int index, T value) throws Exception {
		if(index < 0) { //if the value of index is below zero then throw exception
			throw new Exception("Index out of bound for selected entry");
		}
		/*
		 * If the value of index exceeds the max size of buffer then resize
		 * the buffer array to the max value of the field index, then update
		 * the last element (the value of index) of buffer to the field itemToInsert.
		 */
		if(index >= this.buffer.length) {
			resize(index); //resize the array buffer to the size of the value of index.
			this.buffer[index-1] = value; //Update the element at index to itemToInsert.
			this.size = (index-1); //update the value of size to the index of the inserted element.****
		}
		// Update the element at index to itemToInsert.
		else {
			this.buffer[index] = value; //Update the element at index to itemToInsert.
		}
	}
	
	/**
	 * This method removes the given element at the index 
	 * of the dynamic array object, once the object has been
	 * deleted, the rest of the array is shifted to correct the space.
	 * @param index This is the value that points to the index to be removed.
	 * @throws Throws an exception if the index is out of bounds.
	 */
	@SuppressWarnings("unchecked")
	public void remove(int index) throws Exception {
		//Throw an exception if the index is out of range.
		if(index < 0 || index >= this.size){
			throw new Exception("Index Out Of Bounds.");
		}
		//create an array to allow for the processing of the delection
		T[] editedBuffer = (T[]) new Object[this.buffer.length];
		/* For every element below the index of the object to deleted.
		 * These two for loop basically "skip" about the element to be deleted
		 * 
		 */
		for(int i = 0; i < index; i++){
			//set the current value of editedBuffer to buffer at the value of i.
			editedBuffer[i] = buffer[i];
		}
		//For every element above the object to deleted.
		for(int i = index+1; i < this.size; i++){
			/*
			 * Set the current value minus one of editedBuffer to the current
			 * value of buffer at i. 
			 */
			editedBuffer[i-1] = buffer[i];
		}
		//Update the variable size minus 1 to reflect the deletion.
		size--;
		//set the all of the data refferences of buffer to editedBuffer.
		buffer = editedBuffer;
	}
	
	/**
	 * This method insert a value at the given index and readjusted the
	 * rest of the data upwards to fit around it.
	 * @param index	The value in which the object/value should be inserted at
	 * @param value the object/value to be inserted.
	 * @throws Throws an exception if the value of index of out of bounds.
	 */
	public void insert(int index, T value) throws Exception {
		if(index < 0 || index >= this.size){
			throw new Exception("Index Out Of Bounds.");
		}
		//create an array to allow for the processing of the insertion
		@SuppressWarnings("unchecked")
		T[] editedBuffer = (T[]) new Object[this.buffer.length+1];
		//For every element prior to the value being inserted.
		for(int i = 0; i < index; i++){
			//set the element at i in editedBuffer to the current value of i at buffer
			editedBuffer[i] = buffer[i];
		}
		//insert value from parsed method call
		editedBuffer[index] = value;
		//For every element after the value being inserted.
		for(int i = index; i < this.size; i++){
			/*
			 * set the element at i plus 1 in editedBuffr to the
			 * current value of i at the buffer. The plus one offset is
			 * to account for the insertion.
			 */
			editedBuffer[i+1] = buffer[i]; 
		}
		//Update the size to reflect the insertion
		size++;
		//set the value of buffer to the refference of editedBuffer.
		buffer = editedBuffer;
	}
    
    /**
     * This method returns an iterator for traversal
     * @throws Throws exception in the case of incomptability.
     */
	public Iterator<T> iterator() throws Exception  {
    	return new DArrayAbsItr<T>(this);
    }
}//end of DArray class
