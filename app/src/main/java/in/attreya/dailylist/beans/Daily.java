package in.attreya.dailylist.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Daily extends RealmObject {
    public Daily(String what,long now,boolean completed) {
        this.what = what;
        this.completed = completed;
        this.now = now;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }



    String what;

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
    @PrimaryKey
    long now;

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
