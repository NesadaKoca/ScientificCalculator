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
    String lastNumberEntered = "";
    String lastArrElement = "";
    Boolean equal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvFinish = (TextView) findViewById(R.id.tvFinish);
        tvStart = (TextView) findViewById(R.id.tvStart);

    }


    public void addValue(View v){
        if (equal){
            clearValue();
        }


        Button button = (Button) v;

        input = (String) button.getText().toString(); // string input is equal with button name  eg. if we pres button 7,  input = 7

        if (!input.contains("+") && !input.contains("-") && !input.contains("*") && !input.contains("/") && !input.contains("(") && !input.contains(")") ){ // this checks if we did not press any operator button, if so then execute this code

            containerString = containerString + input;
            lastNumberEntered = containerString; // this we need to use only in the backspace method
            if(arrayList.size() > 0) {

                arrayList.remove(arrayList.size() - 1); // since in the block below we added an operator twice, now we gonna delete the last element
            }

            arrayList.add(containerString); // and here we add the number that was clicked

        }else { // this checks if we did press any operator button, if so then execute this code

            if (arrayList.size() < 1){ // first, check is we have any number in our array, if not do nothing

                if(input.contains("(")){
                    arrayList.add(input);
                    arrayList.add(input);
                }

            } else {

                if(input.contains("(")) {
                    arrayList.remove(arrayList.size() - 1);
                    arrayList.add(input);
                    arrayList.add(input);

                }else if(input.contains(")")){
                    arrayList.add(input);

                }else {
                    // this block of code checks if we already entered any operator before, if so, replace the operator before with the new one
                    lastArrElement = arrayList.get(arrayList.size()-1);

                    if (lastArrElement.contains("+") || lastArrElement.contains("-")|| lastArrElement.contains("*") || lastArrElement.contains("/") ) { // this checks if we did not press any operator button, if so then execute this code
                        arrayList.remove(arrayList.size() - 1);
                        arrayList.remove(arrayList.size() - 1);
                        arrayList.add(input);
                        arrayList.add(input);
                    }else{
                        arrayList.add(input); // if we press one operator, is gonna add twice in array
                        arrayList.add(input);
                        containerString = "";
                    }





                }

            }
        }
        // this block of code checks if we already entered any operator before, if so, replace the operator before with the new one
        if (lastArrElement.contains("+") || lastArrElement.contains("-")|| lastArrElement.contains("*") || lastArrElement.contains("/") ) { // this checks if we did not press any operator button, if so then execute this code

            String backspace = tvStart.getText().toString();
            backspace = backspace.substring(0, backspace.length()-1);
            tvStart.setText(backspace + input);
            lastArrElement = "";

        }else {
            tvStart.setText(tvStart.getText().toString() + input); // this set the textView in the display

            // textView2.setText(arrayList.toString());
        }
    }



    public float checkTempArray(ArrayList arr){

        ArrayList<String> a = new ArrayList<>();

        a = arr;

        float result = 0;
        int arrSize = a.size();


        //eg. array [2,+,3,*,4,-,3]   size = 7
        while (arrSize!=1){

            if (arrSize>3){

                if (a.get(3).contains("*") || a.get(3).contains("/")){

                    if (a.get(3).contains("*")){result = Float.parseFloat(a.get(2)) * Float.parseFloat(a.get(4));}
                    if (a.get(3).contains("/")){result = Float.parseFloat(a.get(2)) / Float.parseFloat(a.get(4));}

                    // calc = 12 ; array [2,+,3,*,4,-,3]
                    a.remove(2); // [2,+,*,4,-,3]
                    a.remove(2); // [2,+,4,-,3]
                    a.remove(2); // [2,+,-,3]
                    a.add(2, Float.toString(result)); // [2,+,12,-,3]
                    arrSize = a.size(); // size 5

                } else {
                    // [2,+,12,-,3]
                    if (a.get(1).contains("+")){result = Float.parseFloat(a.get(0)) + Float.parseFloat(a.get(2));}
                    if (a.get(1).contains("-")){result = Float.parseFloat(a.get(0)) - Float.parseFloat(a.get(2));}
                    if (a.get(1).contains("*")){result = Float.parseFloat(a.get(0)) * Float.parseFloat(a.get(2));}
                    if (a.get(1).contains("/")){result = Float.parseFloat(a.get(0)) / Float.parseFloat(a.get(2));}
                    // calc = 14     array [2,+,12,-,3]
                    a.remove(0);  // [+,12,-,3]
                    a.remove(0);  // [12,-,3]
                    a.remove(0);  // [-,3]
                    a.add(0, Float.toString(result));  // [14,-,3]
                    arrSize = a.size(); // size 3

                }


            }else{
                // size <=3
                if (a.get(1).contains("+")){result = Float.parseFloat(a.get(0)) + Float.parseFloat(a.get(2));}
                if (a.get(1).contains("-")){result = Float.parseFloat(a.get(0)) - Float.parseFloat(a.get(2));}
                if (a.get(1).contains("*")){result = Float.parseFloat(a.get(0)) * Float.parseFloat(a.get(2));}
                if (a.get(1).contains("/")){result= Float.parseFloat(a.get(0)) / Float.parseFloat(a.get(2));}
                // calc = 11  array [14,-,3]
                a.remove(0); // [-,3]
                a.remove(0); // [3]
                a.remove(0); // []
                a.add(0, Float.toString(result)); // [11]
                arrSize = a.size(); // size 1

            }


        }


        return  result;
    }


    public void checkParentheses ( View v){

        try {
            equal = true;

            if (arrayList.size() < 3){ // this means if the user did not clicked any button, or if he/she clicked just one or two do nothing, because
                // at least he/she has to click 3 buttons, two numbers and one operator
                tvStart.setText(Integer.toString(arrayList.size()));
            } else {

                int arrSize = arrayList.size();
                int count = 0;
                boolean parenthesisOpen = false;

                for (int i = 0; i < arrSize; i++) {// check from 0 to end of arr


                    if (arrayList.get(i).contains(")")) { // found ")"

                        for (int b = i; b >= 0; b--) { // check from index of ")" until the index of the "("
                            count++; // count the number of elements between two parenthesis
                            if (arrayList.get(b).contains("(")) { // found "(" also

                                ArrayList<String> tempArr = new ArrayList<>();

                                for (int c = 0; c < count; c++) {// check from 0 to the end of distance between the two parenthesis


                                    tempArr.add(arrayList.get(b)); //add elements to our new array

                                    arrayList.remove(b); // delete the elements from the old array
                                    arrSize--;

                                }

                                tempArr.remove(0); // we are deleting the parentheses, here is the first one
                                tempArr.remove(tempArr.size() - 1); // here is the last one
                                //now we have our new array, we need to call the method checkValue to give us the result as return and then we add the result to our old array arr.add()

                                float checkTempArr = checkTempArray(tempArr);
                                arrayList.add(b, Float.toString(checkTempArr));

                                arrSize++;

                                parenthesisOpen = true; // found
                                count = 0;
                            }

                            if (parenthesisOpen) {//if the second bracket is found than break the for loop, don't search anymore
                                parenthesisOpen = false; // return again to false for the next couple of brackets we gonna search
                                break;

                            }

                        }

                    }

                }
                float result = 0;

                if (arrayList.size() == 1) { // if user enter just one input than return the input
                    result = Float.parseFloat(arrayList.get(0));
                } else {
                    result = checkTempArray(arrayList);
                }

                // this block of code check if the result end with .0, if so than delete it. exp if user enter 3+2 is gonna be equal to 5.0, after this block is executed the result is gonna 5
                if ((""+result).length() > 2) {
                    String afterDot = "" + (""+result).charAt((""+result).length()-2) + (""+result).charAt((""+result).length()-1);

                    if(afterDot.equals(".0")){

                        String cut = (""+result).substring(0,(""+result).length()-1);
                        cut = cut.substring(0,cut.length()-1);

                        tvFinish.setText(tvStart.getText().toString());
                        tvStart.setText(cut);


                    }else{
                        tvFinish.setText(tvStart.getText().toString());
                        tvStart.setText(Float.toString(result));

                    }

                }else {
                    tvFinish.setText(tvStart.getText().toString());
                    tvStart.setText(Float.toString(result));
                }


            }
        } catch (ArithmeticException e) {
            tvStart.setText("Error! Don't divide a number by zero!");
            tvStart.setTextSize(30);

        }catch(Exception e){
            tvStart.setText("Wrong format!");
        }

    }



    public void backspace (View v){
        if (!input.contains("+") && !input.contains("-") && !input.contains("*") && !input.contains("/") && !input.contains("(") ) { // this checks if we did not press any operator button, if so then execute this code

            String backspace = tvStart.getText().toString();
            backspace = backspace.substring(0, backspace.length()-1);
            tvStart.setText(backspace);
            containerString = "";
        }else{


        }


    }



    public void clearValue(View v){

        containerString="";
        input = "";
        tvFinish.setText("");
        tvStart.setText("");
        arrayList.clear();
        tvStart.setTextSize(42);
        equal = false;

    }

    public void clearValue(){

        containerString="";
        input = "";
        tvFinish.setText("");
        tvStart.setText("");
        arrayList.clear();
        tvStart.setTextSize(42);
        equal = false;

    }





    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // in case to make this block of code work, you have to make changes in AndroidManifest for Main Activity attributes
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) { // Checks the orientation of the screen if it's landscape

            startActivity(new Intent(Calculator.this, Scientific_Calculator.class)); // starts B activity


        }
    }


}
