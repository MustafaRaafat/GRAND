package com.mustafa.grand.ui;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mustafa.grand.databinding.FullScreenImageBinding;
import com.squareup.picasso.Picasso;

public class FullScreenImage extends Fragment implements View.OnTouchListener {
    FullScreenImageBinding binding;

//    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FullScreenImageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String photoUrl = FullScreenImageArgs.fromBundle(getArguments()).getPhotoUrl();
        Picasso.get()
                .load(photoUrl)
                .into(binding.fullScreenImage);
        binding.fullScreenImage.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView myview = (ImageView) view;
        myview.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;
        dumpEvent(motionEvent);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                matrix.set(myview.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(motionEvent.getX(), motionEvent.getY());
//                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
//                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(motionEvent);
//                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, motionEvent);
                    mode = ZOOM;
//                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(motionEvent.getX() - start.x, motionEvent.getY() - start.y); // create the transformation in the matrix  of points
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(motionEvent);
//                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        myview.setImageMatrix(matrix); // display the transformation on screen

        return true;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
//        Log.d("Touch Events ---------", sb.toString());

    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /* --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}