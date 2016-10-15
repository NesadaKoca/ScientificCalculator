package nesada.c4q.nyc.scientificcalculator;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

public class Scientific_Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific__calculator);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // in case to make this block of code work, you have to make changes in AndroidManifest for B Activity attributes
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){ // Checks the orientation of the screen if it's portrait
            startActivity(new Intent(Scientific_Calculator.this, Calculator.class)); // starts Main activity

          //  Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
