package usth.edu.dropboxclient;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;




import com.google.android.exoplayer2.SimpleExoPlayer;


import com.google.android.exoplayer2.ui.PlayerView;

public class myviewholder extends RecyclerView.ViewHolder {
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;

    public myviewholder(@NonNull View itemView) {
        super(itemView);
    }
    public void setExoPlayer(Application application,String name,String Videourl){
        TextView textView = itemView.findViewById(R.id.v_title);
        playerView = itemView.findViewById(R.id.exo_playview);

        textView.setText(name);


    }
}


