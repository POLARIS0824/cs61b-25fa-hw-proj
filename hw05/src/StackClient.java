public class StackClient {
    /**
     * Returns a new stack with the same elements as s, but in reverse order.
     * The original stack s is not modified.
     */
    public static Stack flipped(Stack s) {
        int n = s.size();
        int[] values = new int[n];
        
        // Pop all elements from s and store them
        // values[0] = top, values[n-1] = bottom
        // e.g., values = [100, 10, 1] for s with bottom=1, top=100
        for (int i = 0; i < n; i++) {
            values[i] = s.pop();
        }
        
        // Restore s: push in reverse order (bottom first, then up to top)
        for (int i = n - 1; i >= 0; i--) {
            s.push(values[i]);  // push 1, then 10, then 100
        }
        
        // Build result: push in original order (top of s first)
        // This makes the original top become the new bottom
        Stack result = new Stack();
        for (int i = 0; i < n; i++) {
            result.push(values[i]);  // push 100, then 10, then 1
        }
        // Now result from top to bottom is: 1, 10, 100
        
        return result;
    }
}
