package gdgsearch.com.gdgfinals;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by skmishra on 9/9/2017.
 */
public class recyclerMainElecsAdapter extends RecyclerView.Adapter<recyclerMainElecsAdapter.mainHolder> {


    ArrayList<pollPOJO> mArray=new ArrayList<>();
    Context mconts;
    public void setData(ArrayList<pollPOJO> mA)
    {
        mArray=mA;
        notifyItemRangeChanged(0,mArray.size());
    }
    LayoutInflater maly;
    public recyclerMainElecsAdapter(Context conts) {
        mconts=conts;
        maly=LayoutInflater.from(conts);

    }

    @Override
    public mainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=maly.inflate(R.layout.poll_main,parent,false);
        mainHolder mha=new mainHolder(v);
        return mha;
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    @Override
    public void onBindViewHolder(mainHolder holder, int position) {

        pollPOJO mSing=mArray.get(position);
        Log.e("Image",mSing.getImage());
        Glide.with(mconts).load(mSing.getImage()).into(holder.image);
        FontsOverride mFonts=new FontsOverride();
        mFonts.setLatoBold(mconts,holder.title);
        mFonts.setLatoLight(mconts,holder.about);
        holder.title.setText(mSing.getName());
        holder.about.setText(mSing.getAbout());
    }

    class mainHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView about;
        ImageView image;
        RelativeLayout mrel;
        public mainHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.title);
            about=(TextView)itemView.findViewById(R.id.about);
            mrel=(RelativeLayout)itemView.findViewById(R.id.relMainPoll);

            mrel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pollPOJO msjc=mArray.get(getAdapterPosition());
                    Intent i=new Intent(mconts,poll.class);
                    i.putExtra("id",msjc.getId());
                    i.putExtra("name",msjc.getName());
                    i.putExtra("about",msjc.getAbout());
                    mconts.startActivity(i);

                }
            });

        }
    }
}
