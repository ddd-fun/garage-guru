package org.home.gg;


public class SpotLocation {

    private final String level;
    private final String place;

    public SpotLocation(String level, String place) {
        //TODO validate input
        this.level = level;
        this.place = place;
    }


    @Override
    public String toString() {
      return String.format("level=%s, place=%s", level, place);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpotLocation)) return false;

        SpotLocation location = (SpotLocation) o;

        if (!level.equals(location.level)) return false;
        if (!place.equals(location.place)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + place.hashCode();
      return result;
    }
}
