package drsopa.com.dalt2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity {

    private ImageView imagen1;
    private EditText et1;
    private TextView textView;
    private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen1=(ImageView)findViewById(R.id.imageView);
        et1=(EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
    }

    public void tomarFoto(View v) {
        Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto = new File(getExternalFilesDir(null), et1.getText().toString());
        intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivity(intento1);


    }

    public void recuperarFoto(View v) {
        Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+et1.getText().toString());




        bitmap1.compress(Bitmap.CompressFormat.JPEG, 1, new ByteArrayOutputStream());





        imagen1.setImageBitmap(bitmap1);

        //imagen1 = (ImageView) findViewById(R.id.imageView1);

        imagen1 = (ImageView) findViewById(R.id.imageView);
       final Bitmap bmpResult = ((BitmapDrawable)imagen1.getDrawable()).getBitmap();
        imagen1.setOnTouchListener(new View.OnTouchListener() {


         //   imgv = (ImageView) findViewById(R.id.imageView1);
          //  Bitmap bmpResult = ((BitmapDrawable)imgv.getDrawable()).getBitmap();
           // imgv.setOnTouchListener(new View.OnTouchListener() {



            @Override
            public boolean onTouch(View view, MotionEvent event) {


                try{
                    // Obtener las coordenadas sobre el imageView
                    int x = (int)event.getX();
                    int y = (int) event.getY();

                    //Limitar x, y dentro del bitmap
                    if(x < 0){
                        x = 0;
                    }else if(x > bmpResult.getWidth()-1){
                        x = bmpResult.getWidth()-1;
                    }
                    if(y < 0){
                        y = 0;
                    }else if(y > bmpResult.getHeight()-1){
                        y = bmpResult.getHeight()-1;
                    }
                    // Obtener el codigo decimal del pixel
                    textView2.setText("x: "+x+"y:"+y);
                    int pixel = bmpResult.getPixel(x,y);
                    int rval = Color.red(pixel);
                    int gval = Color.green(pixel);
                    int bval = Color.blue(pixel);
                    int iColor = Color.argb(255, rval, gval, bval);


                    if(bval >= 150 && gval<50 && rval<50){
                        textView.setText("Azul" + "R:"+ rval + "V:" + gval + "A:"+bval);
                        textView.setBackgroundColor(iColor);
                    }
                    else if (gval >= 100 && bval<50 && rval<50) {
                        textView.setText("Verde" + "R:"+ rval + "V:" + gval + "A:"+bval);
                        textView.setBackgroundColor(iColor);}
                    else if (rval >= 125 && gval<50 && bval<50) {
                        textView.setText("Rojo"+ "R:"+ rval + "V:" + gval + "A:"+bval);
                        textView.setBackgroundColor(iColor);}
                    else{
                        textView.setText("Color no codificado" + "R:"+ rval + "V:" + gval + "A:"+ bval + "G" + iColor);
                        textView.setBackgroundColor(iColor);}





                    // Muestro el resultado en HEXA
                    System.out.println(String.format("RGB; #%06X   C:%.4f M:%.4f Y:%.4f K:%.4f", "(0xFFFFFF & iColor),cyan,magenta,yellow,black"));
                }catch(Exception e){
                    System.out.println("Error en pixel" + e.toString());
                }
                return true;
            }
        });
    }


}

