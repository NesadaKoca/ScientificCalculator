package nesada.c4q.nyc.scientificcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Calculator extends AppCompatActivity {
    TextView tvFinish, tvStart;
    ArrayList<String> arrayList = new ArrayList<>();
    String input ="";
    String containerString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Configuration.OriConfiguration.ORIENTATION_LANDSCAPE){
//
//        }



        setContentView(R.layout.activity_calculator);
        tvFinish = (TextView) findViewById(R.id.tvFinish);
        tvStart = (TextView) findViewById(R.id.tvStart);

    }


    public void addValue(View v){

        Button button = (Button) v;

        input = (String) button.getText().toString(); // string input is equal with button name  eg. if we pres button 7,  input = 7

        if (!input.contains("+") && !input.contains("-") && !input.contains("*") && !input.contains("/") ){ // this checks if we did not press any operator button, if so then execute this code

            containerString = containerString + input;

            if(arrayList.size() > 0) {

                arrayList.remove(arrayList.size() - 1); // since in the block below we added an operator twice, now we gonna delete the last element
            }

            arrayList.add(containerString); // and here we add the number that it was clicked

        }else { // this checks if we did press any operator button, if so then execute this code

            if (arrayList.size() < 1){ // first, check if there is any number in our array, if not do nothing


            }else {

                arrayList.add(input); // if we press one operator, is gonna add twice in array
                arrayList.add(input);
                containerString = "";
            }
        }

        tvStart.setText(tvStart.getText().toString() + input); // this set the textView without parentheses of  the array in the display

        // textView2.setText(arrayList.toString());

    }

    public  void checkValue(View v){



        int result = 0;
        int arrSize = arrayList.size();


        //eg. array [2,+,3,*,4,-,3]   size = 7
        while (arrSize!=1){

            if (arrSize>3){

                if (arrayList.get(3).contains("*") || arrayList.get(3).contains("/")){

                    if (arrayList.get(3).contains("*")){result = Integer.parseInt(arrayList.get(2)) * Integer.parseInt(arrayList.get(4));}
                    if (arrayList.get(3).contains("/")){result = Integer.parseInt(arrayList.get(2)) / Integer.parseInt(arrayList.get(4));}

                    // calc = 12 ; array [2,+,3,*,4,-,3]
                    arrayList.remove(2); // [2,+,*,4,-,3]
                    arrayList.remove(2); // [2,+,4,-,3]
                    arrayList.remove(2); // [2,+,-,3]
                    arrayList.add(2, Integer.toString(result)); // [2,+,12,-,3]
                    arrSize = arrayList.size(); // size 5

                } else {
                    // [2,+,12,-,3]
                    if (arrayList.get(1).contains("+")){result = Integer.parseInt(arrayList.get(0)) + Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("-")){result = Integer.parseInt(arrayList.get(0)) - Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("*")){result = Integer.parseInt(arrayList.get(0)) * Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("/")){result = Integer.parseInt(arrayList.get(0)) / Integer.parseInt(arrayList.get(2));}
                    // calc = 14     array [2,+,12,-,3]
                    arrayList.remove(0);  // [+,12,-,3]
                    arrayList.remove(0);  // [12,-,3]
                    arrayList.remove(0);  // [-,3]
                    arrayList.add(0, Integer.toString(result));  // [14,-,3]
                    arrSize = arrayList.size(); // size 3

                }

            }else{
                // size <=3
                if (arrayList.get(1).contains("+")){result = Integer.parseInt(arrayList.get(0)) + Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("-")){result = Integer.parseInt(arrayList.get(0)) - Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("*")){result = Integer.parseInt(arrayList.get(0)) * Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("/")){result= Integer.parseInt(arrayList.get(0)) / Integer.parseInt(arrayList.get(2));}
                // calc = 11  array [14,-,3]
                arrayList.remove(0); // [-,3]
                arrayList.remove(0); // [3]
                arrayList.remove(0); // []
                arrayList.add(0, Integer.toString(result)); // [11]
                arrSize = arrayList.size(); // size 1

            }

        }
        if (result == 0){ // if
            tvFinish.setText(tvStart.getText().toString());
        }else {

            tvFinish.setText(tvStart.getText().toString());
            tvStart.setText(Integer.toString(result));
        }

    }


    public void clearValue(View v){

        containerString="";
        input = "";
        tvFinish.setText("");
        tvStart.setText("");
        arrayList.clear();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // in case to make this block of code work, you have to make changes in AndroidManifest for Main Activity attributes
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) { // Checks the orientation of the screen if it's landscape

            startActivity(new Intent(Calculator.this, Scientific_Calculator.class)); // starts B activity

           // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
    }


}
