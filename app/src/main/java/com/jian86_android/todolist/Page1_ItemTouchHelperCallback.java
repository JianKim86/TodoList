package com.jian86_android.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class Page1_ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final  ItemTuchHelpListener listener;
    public Page1_ItemTouchHelperCallback(ItemTuchHelpListener listener) {
        this.listener = listener;
    }

    //움직이는 방향에 따라
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof CheckListAdapter.VHfooter) return makeMovementFlags(0,0);

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);


    }//getMovementFlags

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        return listener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direnction) {


        if(viewHolder.getAdapterPosition()>0) listener.onItemRemove(viewHolder.getAdapterPosition());
    }

    public interface ItemTuchHelpListener{

        boolean onItemMove(int fromPosition, int toPosition);
        void onItemRemove(int position);
    }


}//Page1_ItemTouchHelperCallback
