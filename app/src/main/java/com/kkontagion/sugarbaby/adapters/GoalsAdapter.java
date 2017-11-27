package com.kkontagion.sugarbaby.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kkontagion.sugarbaby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkontagion on 028 28/11/2017.
 */

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Goal> goals;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader, tvSubtext;
        ImageView icon;

        public ViewHolder(View v) {
            super(v);
            tvHeader = v.findViewById(R.id.tv_todo);
            tvSubtext = v.findViewById(R.id.tv_details);
            icon = v.findViewById(R.id.img_icon);
        }
    }

    public GoalsAdapter(Context ctx) {
        this.ctx = ctx;
        goals = new ArrayList<>();
        goals.add(new Goal(R.mipmap.ic_shots, "Keep blood sugar levels below X for 1 week"));
        goals.add(new Goal(R.mipmap.ic_food, "Eat 3 types of fruits a day for 1 week"));
        goals.get(1).setDone(true);
        goals.add(new Goal(R.mipmap.ic_food, "Eat 2 servings of vegetables for every meal for 1 week"));
        goals.add(new Goal(R.mipmap.ic_heart, "Lose 1kg in 2 months", "Active from: 21 Nov 2017"));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goal item = goals.get(position);
        holder.tvHeader.setText(item.getText());
        holder.icon.setImageDrawable(ctx.getDrawable(item.getIconID()));

        if (item.getSubtext() != null) {
            holder.tvSubtext.setText(item.getSubtext());
            holder.tvSubtext.setVisibility(View.VISIBLE);
        }

        if (item.isDone())
            holder.icon.setForeground(ctx.getDrawable(R.mipmap.ic_check));
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return goals.size();
    }


    class Goal {
        private int iconID;
        private String text, subtext;

        private boolean done = false;

        public Goal(int iconID, String text) {
            this.iconID = iconID;
            this.text = text;
        }
        public Goal(int iconID, String text, String subtext) {
            this.iconID = iconID;
            this.text = text;
            this.subtext = subtext;
        }

        public int getIconID() {
            return iconID;
        }

        public String getText() {
            return text;
        }

        public String getSubtext() {
            return subtext;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
