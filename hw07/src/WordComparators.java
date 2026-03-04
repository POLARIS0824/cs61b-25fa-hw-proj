import java.util.Comparator;
import java.util.List;

public class WordComparators {

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        // TODO: Implement this.
        // Anonymous Inner Class
        return new Comparator<String>() {
            /**
             * Compares its two arguments for order.  Returns a negative integer,
             * zero, or a positive integer as the first argument is less than, equal
             * to, or greater than the second.<p>
             * <p>
             * The implementor must ensure that {@link Integer#signum
             * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
             * all {@code x} and {@code y}.  (This implies that {@code
             * compare(x, y)} must throw an exception if and only if {@code
             * compare(y, x)} throws an exception.)<p>
             * <p>
             * The implementor must also ensure that the relation is transitive:
             * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
             * {@code compare(x, z)>0}.<p>
             * <p>
             * Finally, the implementor must ensure that {@code compare(x,
             * y)==0} implies that {@code signum(compare(x,
             * z))==signum(compare(y, z))} for all {@code z}.
             *
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return a negative integer, zero, or a positive integer as the
             * first argument is less than, equal to, or greater than the
             * second.
             * @throws NullPointerException if an argument is null and this
             *                              comparator does not permit null arguments
             * @throws ClassCastException   if the arguments' types prevent them from
             *                              being compared by this comparator.
             * @apiNote It is generally the case, but <i>not</i> strictly required that
             * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
             * any comparator that violates this condition should clearly indicate
             * this fact.  The recommended language is "Note: this comparator
             * imposes orderings that are inconsistent with equals."
             */
            @Override
            public int compare(String o1, String o2) {
                int count1 = countList(o1, List.of('x'));
                int count2 = countList(o2, List.of('x'));

                return count1 - count2;
            }
        };
    }

//    private static int countX(String s) {
//        if (s == null) return 0;
//        int count = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == 'x') {
//                count++;
//            }
//        }
//        return count;
//    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        // TODO: Implement this.
        return new Comparator<String>() {
            /**
             * Compares its two arguments for order.  Returns a negative integer,
             * zero, or a positive integer as the first argument is less than, equal
             * to, or greater than the second.<p>
             * <p>
             * The implementor must ensure that {@link Integer#signum
             * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
             * all {@code x} and {@code y}.  (This implies that {@code
             * compare(x, y)} must throw an exception if and only if {@code
             * compare(y, x)} throws an exception.)<p>
             * <p>
             * The implementor must also ensure that the relation is transitive:
             * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
             * {@code compare(x, z)>0}.<p>
             * <p>
             * Finally, the implementor must ensure that {@code compare(x,
             * y)==0} implies that {@code signum(compare(x,
             * z))==signum(compare(y, z))} for all {@code z}.
             *
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return a negative integer, zero, or a positive integer as the
             * first argument is less than, equal to, or greater than the
             * second.
             * @throws NullPointerException if an argument is null and this
             *                              comparator does not permit null arguments
             * @throws ClassCastException   if the arguments' types prevent them from
             *                              being compared by this comparator.
             * @apiNote It is generally the case, but <i>not</i> strictly required that
             * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
             * any comparator that violates this condition should clearly indicate
             * this fact.  The recommended language is "Note: this comparator
             * imposes orderings that are inconsistent with equals."
             */
            @Override
            public int compare(String o1, String o2) {
                int count1 = countList(o1, List.of(c));
                int count2 = countList(o2, List.of(c));

                return count1 - count2;
            }
        };
    }

//    private static int countChar(String s, char c) {
//        if (s == null) return 0;
//        int count = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == c) {
//                count++;
//            }
//        }
//        return count;
//    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        // TODO: Implement this.
        return new Comparator<String>() {

            /**
             * Compares its two arguments for order.  Returns a negative integer,
             * zero, or a positive integer as the first argument is less than, equal
             * to, or greater than the second.<p>
             * <p>
             * The implementor must ensure that {@link Integer#signum
             * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
             * all {@code x} and {@code y}.  (This implies that {@code
             * compare(x, y)} must throw an exception if and only if {@code
             * compare(y, x)} throws an exception.)<p>
             * <p>
             * The implementor must also ensure that the relation is transitive:
             * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
             * {@code compare(x, z)>0}.<p>
             * <p>
             * Finally, the implementor must ensure that {@code compare(x,
             * y)==0} implies that {@code signum(compare(x,
             * z))==signum(compare(y, z))} for all {@code z}.
             *
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return a negative integer, zero, or a positive integer as the
             * first argument is less than, equal to, or greater than the
             * second.
             * @throws NullPointerException if an argument is null and this
             *                              comparator does not permit null arguments
             * @throws ClassCastException   if the arguments' types prevent them from
             *                              being compared by this comparator.
             * @apiNote It is generally the case, but <i>not</i> strictly required that
             * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
             * any comparator that violates this condition should clearly indicate
             * this fact.  The recommended language is "Note: this comparator
             * imposes orderings that are inconsistent with equals."
             */
            @Override
            public int compare(String o1, String o2) {
                int count1 = countList(o1, chars);
                int count2 = countList(o2, chars);

                return count1 - count2;
            }
        };
    }

    private static int countList(String s, List<Character> chars) {
        if (s == null) return 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (chars.contains(s.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}
