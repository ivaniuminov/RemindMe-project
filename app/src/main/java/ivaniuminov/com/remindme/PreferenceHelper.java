package ivaniuminov.com.remindme;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static final String SPLASH_IS_VISIBLE = "splash";
    private Context context;
    private SharedPreferences sharedPreferences;
    private static PreferenceHelper instance;

    private PreferenceHelper(){}

    public static PreferenceHelper getInstance(){
        if (instance == null)
            instance = new PreferenceHelper();
        return instance;
    }

    public void init(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void setBoolValue(String key, boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolValue(String key){
        return sharedPreferences.getBoolean(key, false);
    }

}
