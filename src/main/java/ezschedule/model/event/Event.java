package ezschedule.model.event;

/**
 * Represents an Event in the scheduler.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

    private final Name name;
    private final Date date;
    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Date date, Time startTime, Time endTime) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getCompletedStatus() {
        return date.isPastDate()
                ? "Event completed"
                : date.isFutureDate()
                ? ""
                : endTime.isPastTime()
                ? "Event completed"
                : "";
    }

    /**
     * Returns true if both events have the same name.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime());
    }

    /**
     * Returns true if otherEvent's date and time overlaps this event.
     */
    public boolean isEventOverlap(Event otherEvent) {
        return isEqualDate(otherEvent) && isTimeOverlap(otherEvent);
    }

    private boolean isEqualDate(Event otherEvent) {
        return this.getDate().equals(otherEvent.getDate());
    }

    private boolean isTimeOverlap(Event otherEvent) {
        return isStartTimeOverlap(otherEvent) || isEndTimeOverlap(otherEvent)
                || isTimeInBetween(otherEvent) || isTimeIsEqual(otherEvent);
    }

    private boolean isStartTimeOverlap(Event otherEvent) {
        return otherEvent.getStartTime().isBefore(this.getStartTime())
                && otherEvent.getEndTime().isAfter(this.getStartTime());
    }

    private boolean isEndTimeOverlap(Event otherEvent) {
        return otherEvent.getStartTime().isBefore(this.getEndTime())
                && otherEvent.getEndTime().isAfter(this.getEndTime());
    }

    private boolean isTimeInBetween(Event otherEvent) {
        return otherEvent.getStartTime().isAfter(this.getStartTime())
                && otherEvent.getEndTime().isBefore(this.getEndTime());
    }

    private boolean isTimeIsEqual(Event otherEvent) {
        return otherEvent.getStartTime().equals(this.getStartTime())
                && otherEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int compareTo(Event otherEvent) {
        if (otherEvent == this) {
            return 0;
        }

        int result = getDate().compareTo(otherEvent.getDate());
        if (result != 0) {
            return result;
        }

        return getStartTime().compareTo(otherEvent.getStartTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getName())
                .append("\nDate: ")
                .append(getDate())
                .append("\nStart Time: ")
                .append(getStartTime())
                .append("\nEnd Time: ")
                .append(getEndTime());
        return sb.toString();
    }
}
