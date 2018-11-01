package info.pauek.songlist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends AppCompatActivity {

    private List<Song> songs;
    private RecyclerView song_list_view;
    private Adapter adapter;
    public static final int NEWSONG=0; //pasar 0 esta feo
    public static final int MODIFYSONG=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        songs = new ArrayList<>();
        songs.add(new Song("...And Justice For All","Metallica","1988"));

        adapter = new Adapter();

        song_list_view = findViewById(R.id.song_list_view);
        song_list_view.setLayoutManager(new LinearLayoutManager(this));
        song_list_view.setAdapter(adapter);
    }

    private void onClickSong(int position) {
        Toast.makeText(this, "Clicked '" + songs.get(position).getTitle() + "'", Toast.LENGTH_SHORT).show();
        String newPos = Integer.toString(position);
        Intent intent=new Intent(this,SongEditActivity.class);          //TODO 8
        intent.putExtra("song",songs.get(position).getTitle());
        intent.putExtra("band",songs.get(position).getBand());
        intent.putExtra("year",songs.get(position).getYear());
        intent.putExtra("position",newPos);
        startActivityForResult(intent,MODIFYSONG); //a dalt hem declarat una variable
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title_view;
        private TextView band_view;             //Todo 4
        private TextView year_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_view = itemView.findViewById(R.id.title_view);
            band_view= itemView.findViewById(R.id.band_view);       //Todo 4
            year_view=itemView.findViewById(R.id.year_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSong(getAdapterPosition());
                }
            });
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.song_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.title_view.setText(songs.get(i).getTitle());
            holder.band_view.setText((songs.get(i).getBand()));     //TODO 4
            holder.year_view.setText((songs.get(i).getYear()));
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {     //inflater pa'l menu
            getMenuInflater().inflate(R.menu.new_song,menu);
            return super.onCreateOptionsMenu(menu);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_song_menu:
                Toast.makeText(this,"New song",Toast.LENGTH_SHORT).show(); //TODO 2
                Intent intent=new Intent(this,SongEditActivity.class);          //TODO 3
                startActivityForResult(intent,NEWSONG); //a dalt hem declarat una variable
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override                                                                               //TODO 8
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NEWSONG:
                if (resultCode==RESULT_OK)
                {
                songs.add(new Song(data.getStringExtra("song"),data.getStringExtra("band"),data.getStringExtra("year")));
                adapter.notifyItemInserted(songs.size());
                }
                break;
            case MODIFYSONG:
                if (resultCode==RESULT_OK)
                {
                    int pos = Integer.valueOf(data.getStringExtra("position"));
                    songs.get(pos).setTitle(data.getStringExtra("song"));
                    songs.get(pos).setBand(data.getStringExtra("band"));
                    songs.get(pos).setYear(data.getStringExtra("year"));
                    adapter.notifyItemChanged(pos);
                }
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
        }

    }
}


