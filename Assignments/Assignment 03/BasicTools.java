/**
 * This interface specifies a few key behaviors for every DynamicArray
 * object.
 */
public interface BasicTools {

    /**
     * Determines if this DynamicArray intersects the specified DynamicArray (other).
     * 
     * @param other DynamicArray to determine if it intersects with this object
     * @return true if two DynamicArrays have a common entry; false otherwise.
     */
    public boolean intersects(DynamicArray other) {
        int i = 0; // set up a counter
        boolean result = false
        while (i < this.position) {
            if (other.contains(this.data[i])) {
                result = true;
            }
            i++;
        }
        return result;
    }
}
