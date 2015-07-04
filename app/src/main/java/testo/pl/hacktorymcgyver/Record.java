package testo.pl.hacktorymcgyver;

/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class Record {
    public String name;
    public String value;

    public Record(String _name, String _value) {
        name = _name;
        value = _value;
    }

    public int compareTo(Record fruite2) {
        Float thisValue = Float.valueOf(this.value);
        Float secondValue = Float.valueOf(fruite2.value);
        if (thisValue > secondValue)
            return -1;
        else return 1;

    }
}
