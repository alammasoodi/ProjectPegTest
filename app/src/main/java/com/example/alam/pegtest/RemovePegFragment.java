package com.example.alam.pegtest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
    int pegsRemoved = 0;
    LinearLayout draggable_layout;
    SharedPreferences pref;
    long initialDropTime = 0;
    long lastDropTime = 0;
    long timeTaken;
    SharedPreferences.Editor editor;
    ImageView arrow1,arrow2,arrow3;
    Animation animFadein1,animFadein2,animFadein3;
    boolean isFlag,firstDrop;
    CountDownTimer mCountDownTimer;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_remove_peg, container, false);
        pref = getActivity().getApplication().getSharedPreferences("PegPref", 0); // 0 - for private mode
        editor = pref.edit();
        dragButton = (Button)v. findViewById(R.id.drag_button);
        holeButton = (Button) v.findViewById(R.id.hole_button);
        arrow1 = (ImageView) v.findViewById(R.id.guide1);
        arrow2 = (ImageView) v.findViewById(R.id.guide2);
        arrow3 = (ImageView) v.findViewById(R.id.guide3);
        arrow1.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        arrow2.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        arrow3.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        animFadein1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
        animFadein2 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
        animFadein3 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
        arrow1.startAnimation(animFadein1);
        arrow2.startAnimation(animFadein2);
        arrow3.startAnimation(animFadein3);

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
                        if (!firstDrop) {
                            initialDropTime = System.currentTimeMillis();
                            firstDrop = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP: {
                        success = success +1;
                        pegsRemoved = pegsRemoved + 1;
                        success = success +1;
                        flag = true;
                        return (true);
                    }

                    case DragEvent.ACTION_DRAG_ENDED: {


                        final FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        if (pegsRemoved < 2) {
                            if (flag) {
                                mCountDownTimer = new CountDownTimer(500, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        dragButton.setVisibility(View.VISIBLE);
                                    }
                                };
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.start();

                            } else {
                                dragButton.setVisibility(View.VISIBLE);

                                Toast.makeText(getActivity(), "try again", Toast.LENGTH_SHORT).show();

                            }

                            //text.setText("Total Drops: " + total);


                        }
                        else {
                            lastDropTime = System.currentTimeMillis();
                            timeTaken  = lastDropTime - initialDropTime;
                           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                            alertDialogBuilder.setTitle("Time taken to put pegs behind the line");
                            alertDialogBuilder.setMessage(String.valueOf(timeTaken / 1000) + " seconds");

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(),ResultActivity.class);
                                    startActivity(intent);

                                }
                            });
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.show().getWindow();


                        }
                    }
                    return (true);
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
