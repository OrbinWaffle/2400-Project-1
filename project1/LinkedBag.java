package project1;

public class LinkedBag<T> implements BagInterface<T>
{
   private Node<T> firstNode;
   private int numberOfEntries;
   /**
   * Creates an empty linked bag.
   */
   public LinkedBag()
   {
      firstNode = null;
      numberOfEntries = 0;
   } //end default constructor
   @Override
   /**
   * Creates a bag with all items in this bag with another one.
   * @param otherBag the other bag which will be combined with this one.
   * @return a bag containing all the items of the two bags.
   */
   public BagInterface<T> union(BagInterface<T> otherBag)
   {
      T[] thisArray = this.toArray();
      T[] otherArray = otherBag.toArray();
      LinkedBag<T> result = new LinkedBag<T>();
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
      LinkedBag<T> result = new LinkedBag<T>();
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
      LinkedBag<T> result = new LinkedBag<T>();
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
   * @return the current number of items in the bag.
   */
   @Override
   public int getCurrentSize()
   {
      return numberOfEntries;
   }
   /**
   * @return true if the bag is empty. False otherwise.
   */
   @Override
   public boolean isEmpty()
   {
      return firstNode == null;
   }
   /**
   * Adds a new item to the bag.
   * @param newEntry item of generic type to be added.
   * @return true if addition was successful. False otherwise.
   */
   @Override
   public boolean add(T newEntry)
   {
      Node<T> newNode = new Node<T>(newEntry);
      newNode.setNext(firstNode);
      firstNode = newNode;
      numberOfEntries++;
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
      T result = null;
      if (firstNode != null)
      {
         result = firstNode.getData();
         firstNode = firstNode.getNext();
         numberOfEntries--;
      }
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
      boolean result = false;
      Node<T> foundNode = getReferenceTo(entry);
      if(foundNode != null)
      {
         foundNode.setData(firstNode.getData());
         firstNode = firstNode.getNext();
         numberOfEntries--;
         result = true;
      }
      return result;
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
      int frequency = 0;
      Node<T> currentNode = firstNode;
      while(currentNode != null)
      {
         if(entry.equals(currentNode.getData()))
            frequency++;
         currentNode = currentNode.getNext();
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
      boolean found = false;
      Node<T> currentNode = firstNode;
      while(!found & currentNode != null)
      {
         if(entry.equals(currentNode.getData()))
            found = true;
         else
            currentNode = currentNode.getNext();
      }
      return found;
   }
   /**
   * Puts the contents of the bag into a new array and returns it.
   * @return array containing the contents of the bag.
   */
   @Override
   public T[] toArray()
   {
      Node<T> currentNode = firstNode;
      int counter = 0;
      @SuppressWarnings("unchecked")
      T[] result = (T[]) new Object[numberOfEntries];
      while(currentNode != null)
      {
         result[counter] = currentNode.getData();
         currentNode = currentNode.getNext();
         counter++;
      }
      return result;
   }
   /**
   * Finds the node that contains specified data.
   * @param entry the data to find.
   * @return the node that contains the data in entry.
   */
   private Node<T> getReferenceTo(T entry)
   {
      boolean found = false;
      Node<T> currentNode = firstNode;
      while(!found & currentNode != null)
      {
         if(entry.equals(currentNode.getData()))
            found = true;
         else
            currentNode = currentNode.getNext();
      }
      return currentNode;
   }
}
class Node<T>
{
   private T data;
   private Node<T> next;
   public Node(T newData)
   {
      data = newData;
   } // end constructor
   
   /**
   * @return the data in the node.
   */
   public T getData()
   {
      return data;
   }

   /**
   * @param newData the data to put in the node.
   */
   public void setData(T newData)
   {
      data = newData;
   }
   
   /**
   * @return the next node in the chain.
   */
   public Node<T> getNext()
   {
      return next;
   }
   
   /**
   * @param newNext the node to be set as the next node in the chain.
   */
   public void setNext(Node<T> newNext)
   {
      next = newNext;
   }
}
