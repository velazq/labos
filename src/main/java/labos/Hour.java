package labos;

public class Hour {

    public String subjectName;
    public boolean empty;
    private boolean visited;

    public Hour() {
        subjectName = "";
        empty = false;
        visited = false;
    }

    /*public Hour(String subjectName) {
        setSubjectName(subjectName);
    }*/

    public String getSubjectName() {
        return subjectName;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
        empty = false;
        visited = true;
    }

    public void setEmpty() {
        empty = true;
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }
}
