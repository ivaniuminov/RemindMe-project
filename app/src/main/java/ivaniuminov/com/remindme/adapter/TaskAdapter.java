package ivaniuminov.com.remindme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ivaniuminov.com.remindme.fragment.TaskFragment;
import ivaniuminov.com.remindme.model.Item;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    TaskFragment taskFragment;
    List<Item> items;

    public TaskAdapter(TaskFragment taskFragment){
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location){
        if (location >= 0 && location <= getItemCount() - 1){
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}
