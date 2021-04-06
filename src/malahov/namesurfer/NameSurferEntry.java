package malahov.namesurfer;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.Arrays;
import java.util.Objects;

public class NameSurferEntry implements NameSurferConstants {

    private final String name;
    private final int[] namePopularity = new int[NDECADES];

    /* Constructor: NameSurferEntry(line) */

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        String[] lineElements = line.split(" ");

        name = lineElements[0];

        for (int i = 1; i < lineElements.length; i++) {
            if(Integer.parseInt(lineElements[i]) == 0){
                namePopularity[i - 1] = 1001;
            }else{
                namePopularity[i - 1] = Integer.parseInt(lineElements[i]);
            }
        }
    }

    /* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameSurferEntry entry = (NameSurferEntry) o;
        return Objects.equals(name, entry.name) && Arrays.equals(namePopularity, entry.namePopularity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(namePopularity);
        return result;
    }

    /* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        // 0-1900,1-1910,2-1920,3-1930,4-1940,5-1950,6-1960,7-1970,8-1980,9-1990,10-2000,11-2010.
        return namePopularity[decade];
    }

    /* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        String str = name + " " + Arrays.toString(namePopularity);
        return str.replaceAll(",","");
    }
}

