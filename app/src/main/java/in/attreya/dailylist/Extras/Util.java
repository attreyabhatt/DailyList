package in.attreya.dailylist.Extras;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class Util{

        public static boolean moreThanJellyBean() {
            return Build.VERSION.SDK_INT > 15;
        }

        public static void setBackground(View view, Drawable drawable) {
            if (moreThanJellyBean()) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
}
