package info.pauek.songlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SongEditActivity extends AppCompatActivity {

    private EditText song;      //TODO 5
    private EditText band;
    private EditText year;
    private int pos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_edit);
        song=findViewById(R.id.edit_title);
        band=findViewById(R.id.edit_band);
        year=findViewById(R.id.edit_year);
        Calendar calendar=new GregorianCalendar();
        int years=calendar.get(Calendar.YEAR);           //TODO 6
        String yearString=Integer.toString(years);
        year.setText(yearString);


        Intent intent = getIntent();
        song.setText(intent.getStringExtra("song"));
        band.setText(intent.getStringExtra("band"));
        if(intent.getStringExtra("year") != null){
            year.setText(intent.getStringExtra("year"));
        }
        if(intent.getStringExtra("position") != null){
            String position = intent.getStringExtra("position");
            pos = Integer.valueOf(position);
        }

    }

    public void minus (View view){                                  //TODO 5
        int resta=Integer.valueOf(year.getText().toString());
        resta--;
        String restastring=Integer.toString(resta);
        year.setText(restastring);
    }

    public void plus (View view){
        int suma=Integer.valueOf(year.getText().toString());
        suma++;
        String sumastring=Integer.toString(suma);
        year.setText(sumastring);

    }

    public void saveclick(View view){
        String newPos = Integer.toString(pos);
        Intent data=new Intent();
        data.putExtra("song",song.getText().toString());            //TODO 7
        data.putExtra("band",band.getText().toString());
        data.putExtra("year",year.getText().toString());
        data.putExtra("position", newPos);
        setResult(RESULT_OK,data);
        finish();
    }

}

