package project1;

import java.util.Arrays;

public class ResizableArrayBag<T> implements BagInterface<T>
{
   private T[] bag;
   private int currentSize;
   private boolean integrityOK;
   private final int MAX_CAPACITY = 10000;
   private static final int DEFAULT_CAPACITY = 25;
   /**
   * Creates a bag with a default capacity of 25.
   */
   public ResizableArrayBag()
   {
      this(DEFAULT_CAPACITY);
   } //end default constructor
   
   /**
   * Creates a bag with a specified intial capacity.
   * @param initialCapcity the desired integer capacity.
   */
   public ResizableArrayBag(int initialCapacity)
   {
      if(initialCapacity <= MAX_CAPACITY)
      {
         @SuppressWarnings("unchecked")
         T[] tempBag = (T[])new Object[initialCapacity]; // Unchecked cast
         bag = tempBag;
         currentSize = 0;
         integrityOK = true;
      }
      else
      {
         throw new IllegalStateException("Attempted to create a bag whose capacity exceeds " +
                                         "allowed maximum of " + MAX_CAPACITY);
      }
   } //end constructor
   /**
   * Creates a bag with all items in this bag with another one.
   * @param otherBag the other bag which will be combined with this one.
   * @return a bag containing all the items of the two bags.
   */
   @Override
   public BagInterface<T> union(BagInterface<T> otherBag)
   {
      T[] thisArray = this.toArray();
      T[] otherArray = otherBag.toArray();
      ResizableArrayBag<T> result = new ResizableArrayBag<T>(thisArray.length + otherArray.length);
      for(int i = 0; i < thisArray.length; i++)
      {
         result.add(thisArray[i]);
      }
      for(int i = 0; i < otherArray.length; i++)
      {
         result.add(otherArray[i]);
      }
      return result;
   }
   /**
   * Creates a bag with all the matching items in this bag and another one.
   * @param otherBag the other bag which will be combined with this one.
   * @return a bag containing all the items that were in both bags.
   */
   @Override
   public BagInterface<T> intersection(BagInterface<T> otherBag)
   {
      T[] thisArray = this.toArray();
      T[] otherArray = otherBag.toArray();
      boolean[] used = new boolean[otherArray.length];
      ResizableArrayBag<T> result = new ResizableArrayBag<T>();
      for(int i = 0; i < thisArray.length; i++)
      {
         for(int j = 0; j < otherArray.length; j++)
         {
            if(!used[j] & thisArray[i].equals(otherArray[j]))
            {
               result.add(thisArray[i]);
               used[j] = true;
               break;
            }
         }
      }
      return result;
   }
   /**
   * Creates a bag that has all the elements in this bag which are not in the second.
   * @param otherBag the other bag which will be combined with this one.
   * @return a bag containing the items that were in this bag but not otherBag.
   */
   @Override
   public BagInterface<T> difference(BagInterface<T> otherBag)
   {
      T[] thisArray = this.toArray();
      T[] otherArray = otherBag.toArray();
      boolean[] used = new boolean[otherArray.length];
      ResizableArrayBag<T> result = new ResizableArrayBag<T>();
      for(T item : thisArray)
      {
         result.add(item);
      }
      for(int i = 0; i < thisArray.length; i++)
      {
         for(int j = 0; j < otherArray.length; j++)
         {
            if(!used[j] & thisArray[i].equals(otherArray[j]))
            {
               result.remove(thisArray[i]);
               used[j] = true;
               break;
            }
         }
      }
      return result;
   }
   /**
   * Doubles the size of the bag array.
   */
   private void doubleCapacity()
   {
      int newLength = 2*bag.length;
      resize(newLength);
   } 
   /**
   * Resizes the bag array to a new length.
   * @param newsize the desired length of the bag array.
   */
   private void resize(int newSize)
   {
      T[] newBag = Arrays.copyOf(bag, newSize);
      bag = newBag;
      checkCapacity(bag.length);
   }
   /**
   * Ensures the capacity does not exceed the maximum allowed capacity.
   * @param capacity the capacity to check.
   */
   private void checkCapacity(int capacity)
   {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempted to create a bag whose capacity exceeds " +
                                         "allowed maximum of " + MAX_CAPACITY);
   }
   /**
   * @return the current number of items in the bag.
   */
   @Override
   public int getCurrentSize()
   {
      return currentSize;
   }
   /**
   * @return true if the bag is empty. False otherwise.
   */
   @Override
   public boolean isEmpty()
   {
      return currentSize == 0;
   }
   /**
   * Adds a new item to the bag.
   * @param newEntry item of generic type to be added.
   * @return true if addition was successful. False otherwise.
   */
   @Override
   public boolean add(T newEntry)
   {
      checkIntegrity();
      if(currentSize >= bag.length)
         doubleCapacity();
      bag[currentSize] = newEntry;
      currentSize++;
      return true;
   }
   /**
   * Removes an item from the bag.
   * @return the item that was removed.
   *         Null if removal was unsuccessful.
   */
   @Override
   public T remove()
   {
      checkIntegrity();
      T result = removeEntry(currentSize - 1);
      return result;
   }
   /**
   * Removes a specific item from the bag.
   * @param entry item to be removed.
   * @return true if removal was successful. False otherwise.
   */
   @Override
   public boolean remove(T entry)
   {
      checkIntegrity();
      int index = getIndexOf(entry);
      T result = removeEntry(index);
      return entry.equals(result);
   }
   /**
   * Removes all items from the bag.
   */
   @Override
   public void clear()
   {
      while(!isEmpty())
         remove();
   }
   /**
   * Indicates the frequency of an item.
   * @param entry item to look for.
   * @return number of times the item appears in the bag.
   */
   @Override
   public int getFrequencyOf(T entry)
   {
      checkIntegrity();
      int frequency = 0;
      for(T item:bag)
      {
         if(entry.equals(item))
            frequency++;
      }
      return frequency;
   }
   /**
   * Determines if the bag contains a certain item.
   * @param entry item to look for.
   * @return true if the bag contains the item. False otherwise.
   */
   @Override
   public boolean contains(T entry)
   {
      checkIntegrity();
      return getIndexOf(entry) >= 0;
   }
   /**
   * Puts the contents of the bag into a new array and returns it.
   * @return array containing the contents of the bag.
   */
   @Override
   public T[] toArray()
   {
      @SuppressWarnings("unchecked")
      T[] result = (T[]) new Object[currentSize];
      for(int i = 0; i < currentSize; i++)
         result[i] = bag[i];
      return result;
   }
   /**
   * Finds the index of an item in the bag.
   * @param entry item to look for.
   * @return the index of the item. -1 if the item was not found.
   */
   private int getIndexOf(T entry)
   {
      int location = -1;
      boolean found = false;
      int index = 0;
      while(!found && index < currentSize)
      {
         if(entry.equals(bag[index]))
         {
            found = true;
            location = index;
         }
         index++;
      }
      return location;
   }
   /**
   * Removes an item at a certain index from the bag.
   * @param index index of the item to be removed.
   * @return object that was removed. Null if removal was unsuccessful.
   */
   private T removeEntry(int index)
   {
      T objToBeRemoved = null;
      if(!isEmpty() && index >= 0)
      {
         objToBeRemoved = bag[index];
         bag[index] = bag[currentSize - 1];
         bag[currentSize - 1] = null;
         currentSize--;
      }
      return objToBeRemoved;
   }
   /**
   * Ensures the bag is not corrupt. If it is, it throws a SecurityException.
   */
   private void checkIntegrity()
   {
      if(!integrityOK)
      {
         throw new SecurityException("ArrayBag object is corrupt.");
      }
   }
}
