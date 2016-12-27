package com.examples.android.evento;

/**
 * Created by ankit on 27/12/16.
 */

import android.view.View;


public interface ItemInteractionListener<T> {
    void onItemClick(View v, T item);
    void onItemLongClick(View v, T item);
}