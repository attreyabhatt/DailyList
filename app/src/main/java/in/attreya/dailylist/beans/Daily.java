package in.attreya.dailylist.beans;

import io.realm.RealmObject;


public class Daily extends RealmObject {
    public Daily(String what,boolean completed) {
        this.what = what;
        this.completed = completed;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }



    String what;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    boolean completed;

    public Daily() {
    }
}
