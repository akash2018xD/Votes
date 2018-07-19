package gdgsearch.com.gdgfinals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by skmishra on 9/9/2017.
 */
public class adapterSelections extends RecyclerView.Adapter<adapterSelections.HolderSelect>
{
    LayoutInflater mlay;
    Context context;
    int positionSelected=-1;
    ArrayList<dataDers> mPrivacy=new ArrayList<>();
   public int option;


    public adapterSelections(Context mConts) {
        context=mConts;
        mlay=LayoutInflater.from(context);

    }
    public void setData(ArrayList<dataDers> mArray)
    {
        mPrivacy=mArray;
        notifyItemRangeChanged(0,mArray.size());
    }

    @Override
    public HolderSelect onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=mlay.inflate(R.layout.options_custom,parent,false);
        HolderSelect mHolder=new HolderSelect(v);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(HolderSelect holder, int position) {


        Glide.with(context).load(R.drawable.ic_radio_button_unchecked_white_24dp).into(holder.selected);
        if(position==positionSelected)
        {
            Glide.with(context).load(R.drawable.ic_check_circle_black_24dp).into(holder.selected);

        }

        holder.knowMore.setVisibility(View.GONE);
        FontsOverride mfonrs=new FontsOverride();
        mfonrs.setLatoLight(context,holder.title);
        holder.title.setText(mPrivacy.get(position).name);
        if(!mPrivacy.get(position).getImage().equals("")) {
            holder.knowMore.setVisibility(View.VISIBLE);
            Glide.with(context).load(mPrivacy.get(position).getImage()).into(holder.icon);
        }
        }

    @Override
    public int getItemCount() {
        return mPrivacy.size();
    }

    class HolderSelect extends RecyclerView.ViewHolder
    {

        ImageView selected;
        TextView title;
        ImageView icon;
        TextView knowMore;

        RelativeLayout relHeadl;
        public HolderSelect(View itemView) {
            super(itemView);
            selected=(ImageView)itemView.findViewById(R.id.iconVista);
            title=(TextView)itemView.findViewById(R.id.nameOpt);
            icon=(ImageView)itemView.findViewById(R.id.option);
            knowMore=(TextView)itemView.findViewById(R.id.knowMore);
            relHeadl=(RelativeLayout)itemView.findViewById(R.id.relHeadl);
            relHeadl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(positionSelected);

                    positionSelected=getAdapterPosition();
                    Glide.with(context).load(R.drawable.ic_check_circle_black_24dp).into(selected);
                    selected.setColorFilter(Color.WHITE);
                    privacyClick(positionSelected);




                }
            });

            knowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogKnowMore md=new dialogKnowMore(context,mPrivacy.get(getAdapterPosition()).getId());
                    md.show();
                }
            });
        }
    }
    public void privacyClick(int which)
    {


        option=which;
    }
}