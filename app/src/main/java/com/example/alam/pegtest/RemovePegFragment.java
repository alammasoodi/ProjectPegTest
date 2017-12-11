package com.example.alam.pegtest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alam on 9/12/17.
 */

public class RemovePegFragment extends Fragment {
    View v;
    Button dragButton,holeButton;
    LinearLayout hole;
    int total,failure,success;
    TextView text;
    LinearLayout.LayoutParams lParams;
    private static final String Bt_TAG = " BT_DRAG";
    private static final String Hl_TAG = " HL_DRAG";
    int droppedPegs = 0;
    LinearLayout draggable_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_remove_peg, container, false);
        dragButton = (Button)v. findViewById(R.id.drag_button);
        holeButton = (Button) v.findViewById(R.id.hole_button);
        //hole = (LinearLayout) findViewById(R.id.hole);
        //dragButton.setTag(Bt_TAG);
        //hole.setTag(Hl_TAG);
        draggable_layout = (LinearLayout) v.findViewById(R.id.draggable_layout);
        draggable_layout.setOnDragListener(new View.OnDragListener() {
            boolean flag = false;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP: {
                        success = success +1;
                        flag = true;
                        return (true);
                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
                        droppedPegs = droppedPegs + 1;
                        final FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }

                    default:
                        break;

                }
                return true;
            }
        });
        dragButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
//                if(motionEvent.getPointerCount()==2){
//                    Toast.makeText(PlacePegActivity.this, " Two Fingers Tapped Once. Yeeeyy :)", Toast.LENGTH_SHORT).show();
//                    ClipData data = ClipData.newPlainText("", "");
//                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(dragButton);
//                    v.startDrag(data, shadow, null, 0);
//                    v.setVisibility(View.INVISIBLE);
//
//                }
                switch(action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Toast.makeText(getActivity(), " Two Fingers Tapped Once. Yeeeyy :)", Toast.LENGTH_SHORT).show();
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadow = new View.DragShadowBuilder(dragButton);
                        v.startDrag(data, shadow, null, 0);
                        v.setVisibility(View.INVISIBLE);
                        // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                        //mTwoFingersTapped = true;
                        break;

                }
                // TODO Auto-generated method stub

                return false;
            }
        });

        return v;
    }
    }
