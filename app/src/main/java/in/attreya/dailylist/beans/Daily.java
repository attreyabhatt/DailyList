package in.attreya.dailylist.beans;

import io.realm.RealmObject;


public class Daily extends RealmObject {
    public Daily(String what) {
        this.what = what;

    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }



    String what;


    public Daily() {
    }
}
