package studentcompany.sportgest.daos;

/**
 * Created by arturh on 01/09/2009 at StackOverflow.
 * (http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java)
 */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (	this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }
}