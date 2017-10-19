package com.example.debarghya.text;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private VisionServiceClient visionServiceClient = new VisionServiceRestClient("fc7e312f3066422da841fb90e7af3708");

    Button button;
    ImageView imageView;
    Bitmap j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.imageView);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

    }

    public void savemefromtrouble(){
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setBackgroundColor(80000000);
        imageView.setImageBitmap(j);
        Imagetotext(j);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        j=(Bitmap)data.getExtras().get("data");

        savemefromtrouble();
    }

    public void Imagetotext(Bitmap k) {


        Bitmap mBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.image);
        //ImageView imageView=(ImageView)findViewById(R.id.imageView);
        //imageView.setImageBitmap(mBitmap);

        //Bitmap bitmap = ((BitmapDrawable)isaveme.getDrawable()).getBitmap();
        Bitmap bitmap=k;

        Button btnProcess=(Button)findViewById(R.id.btn_process);

        //convert bitmap to string

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        final ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<InputStream,String,String> recognizeTextTask=new AsyncTask<InputStream, String, String>() {
                    ProgressDialog mDialog=new ProgressDialog(MainActivity.this);
                    @Override
                    protected String doInBackground(InputStream... params) {
                        try{
                            publishProgress("Recognizing...");
                            OCR ocr=visionServiceClient.recognizeText(params[0], LanguageCodes.English,true);
                            String result=new Gson().toJson(ocr);
                            return  result;
                        }catch (Exception ex){
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        mDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        mDialog.dismiss();
                        OCR ocr=new Gson().fromJson(s,OCR.class);

                        final EditText editText=(EditText)findViewById(R.id.editText);
                        StringBuilder stringBuilder=new StringBuilder();

                        for (Region region:ocr.regions){
                            for (Line line:region.lines)
                            {
                                for(Word word:line.words)
                                    stringBuilder.append(word.text+" ");
                                stringBuilder.append("\n");
                            }
                            stringBuilder.append("\n\n");
                        }
                        String string=stringBuilder.toString();
                        editText.setText(string);

                        Button ask=(Button)findViewById(R.id.button2);
                        ask.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String extract=editText.getText().toString();
                                dothemajorlifting(extract);
                            }
                        });


                        //txtDescription.setText(string);


                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                        mDialog.setMessage(values[0]);
                    }
                };

                recognizeTextTask.execute(inputStream);
            }
        });

    }
    public void dothemajorlifting(String xx){



       goToUrl("https://heroku-myfirstapp.herokuapp.com/?user="+xx);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}//main activity
