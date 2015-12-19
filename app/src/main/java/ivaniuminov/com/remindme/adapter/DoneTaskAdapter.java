package ivaniuminov.com.remindme.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ivaniuminov.com.remindme.R;
import ivaniuminov.com.remindme.Utils;
import ivaniuminov.com.remindme.fragment.DoneTaskFragment;
import ivaniuminov.com.remindme.fragment.TaskFragment;
import ivaniuminov.com.remindme.model.Item;
import ivaniuminov.com.remindme.model.ModelTask;


public class DoneTaskAdapter extends TaskAdapter {

    public DoneTaskAdapter(DoneTaskFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);
        TextView title = (TextView) view.findViewById(R.id.tvTaskTitle);
        TextView date = (TextView) view.findViewById(R.id.tvTaskDate);
        CircleImageView priority = (CircleImageView) view.findViewById(R.id.cvTaskPriority);

        return new TaskViewHolder(view, title, date, priority);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            final ModelTask task = (ModelTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);
            itemView.setBackgroundColor(resources.getColor(R.color.gray_200));
            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
            taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.ic_check_circle_white_48dp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                        }
                    }, 1000);
                    return true;
                }
            });

            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setStatus(ModelTask.STATUS_CURRENT);
                    getTaskFragment().activity.dbHelper.update().status(task.getTimeStamp(), ModelTask.STATUS_CURRENT);

                    itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
                    taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority,
                            "rotationY", 180, 0f);
                    taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (task.getStatus() != ModelTask.STATUS_DONE) {

                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                        "translationX", 0f, -itemView.getWidth());

                                ObjectAnimator translationBack = ObjectAnimator.ofFloat(itemView,
                                        "translationX", -itemView.getWidth(), 0f);

                                translationX.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(task);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationBack);
                                translationSet.start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    flipIn.start();
                }
            });
        }
    }
}
