package com.exemplo.field.customfieldsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public EditText phoneFieldMask;
    public EditText valueFieldCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        phoneFieldMask = (EditText)findViewById(R.id.phone);
        valueFieldCustom = (EditText) findViewById(R.id.value);

        //Treatment mask phone
        phoneFieldMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                phoneFieldMask.setText("");
            }
        });

        phoneFieldMask.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == true){
                    phoneFieldMask.setText("");
                }
            }
        });
        phoneFieldMask.addTextChangedListener(new TextWatcher() {

            private boolean flagDelete = false;
            private boolean flagDeleteTrace = false;
            private boolean flagSelectionPosition = false;
            private String auxString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().isEmpty()){
                    auxString = "";
                    flagDeleteTrace = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before == 0){
                    flagDelete = false;
                }else if(before == 1){
                    flagDelete = true;
                }
                //Log.d("Char",s.toString());
                //Log.d("Lenght",String.valueOf(s.toString().length()));
                //Log.d("Start", String.valueOf(start));
                //Log.d("Count", String.valueOf(count));
                //Log.d("Selection", String.valueOf(phoneFieldMask.getSelectionEnd()));
                if(phoneFieldMask.getSelectionEnd() < s.length()){
                    Log.d("Sub", String.valueOf(s.toString().substring(0,phoneFieldMask.getSelectionEnd())));
                    auxString = s.toString().substring(0,phoneFieldMask.getSelectionEnd());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(auxString.isEmpty()) {
                    if (flagDelete == false) {
                        if (s.toString().length() == 1) {
                            s.insert(0, "(");
                        } else if (s.toString().length() == 2) {
                            if (s.toString().charAt(s.toString().length() - 1) == '0') {
                                s.delete(1, 2);
                            }
                        } else if (s.toString().length() == 4) {
                            s.insert(3, ")");
                        } else if (s.toString().length() == 5) {
                            s.insert(4, " ");
                        } else if (s.toString().length() == 10) {
                            s.insert(9, "-");
                        } else if (s.toString().length() == 15) {
                            if (flagDeleteTrace == false) {
                                s.delete(9, 10);
                                flagDeleteTrace = true;
                                s.insert(10, "-");
                            }
                        } else if (s.toString().length() == 16) {
                            s.delete(15, 16);
                            flagDelete = true;
                        }
                    } else {
                        if (s.toString().length() == 14 && flagDeleteTrace == true) {
                            s.delete(10, 11);
                            flagDeleteTrace = false;
                            s.insert(9, "-");

                        }
                        flagDelete = false;
                    }
                }else{
                    String aux = auxString;
                    auxString = "";
                    s.delete(0,s.toString().length());
                    s.append(aux);
                    flagDeleteTrace = false;
                }
            }
        });



        //Treatment value field
        valueFieldCustom.setText("0,00");
        valueFieldCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueFieldCustom.setSelection(valueFieldCustom.getText().length());
            }
        });
        valueFieldCustom.setSelection(valueFieldCustom.getText().length());
        valueFieldCustom.addTextChangedListener(new TextWatcher() {

            boolean ignoreChange = false;
            int countAux;
            int mStart;
            int auxLenght;
            public String farmSizeAux = "";

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

                mStart = start;
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (!ignoreChange) {
                    countAux = count;
                    if(count > 0) {
                        farmSizeAux = farmSizeAux + s.toString().substring(start, start + 1);
                    }else{
                        if(farmSizeAux.length() - 1 > -1) {
                            farmSizeAux = farmSizeAux.substring(0, farmSizeAux.length() - 1);
                        }else{
                            farmSizeAux = "";
                        }
                    }


                    String string = s.toString();



                    string = string.replace(".", "");
                    string = string.replace(",", "");
                    string = string.replace(" ", "");
                    string = string.replace("0","");


                    if (farmSizeAux.length() == 0) {
                        string = "0,00";
                    }else if (farmSizeAux.length() == 1) {
                        string = "0,0" + farmSizeAux;
                    }else if (farmSizeAux.length() == 2) {
                        string = "0," + farmSizeAux;
                    }else if (farmSizeAux.length() > 2) {
                        string = farmSizeAux.substring(0, farmSizeAux.length() - 2) + "," + farmSizeAux.substring(farmSizeAux.length() - 2, farmSizeAux.length());
                    }
                    ignoreChange = true;
                    valueFieldCustom.setText(string);
                    valueFieldCustom.setSelection(valueFieldCustom.getText().length());
                    ignoreChange = false;
                }
            }
        });


    }
}
