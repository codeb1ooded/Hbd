package com.example.megha.hbd;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.q42.android.scrollingimageview.ScrollingImageView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity  implements MediaPlayer.OnErrorListener{

    BrokenView brokenView;
    BrokenTouchListener listener;
    ImageView view;
    PageAdapter mAdapter;
    RecyclerView mRecyclerView;
    Intent music;
    MediaPlayer mPlayer;
    private int length = 0;
    Shimmer shimmer;
    ShimmerTextView myShimmerTextView;

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brokenView = BrokenView.add2Window(this);
        listener = new BrokenTouchListener.Builder(brokenView).build();
        view = (ImageView) findViewById(R.id.backgroundImage);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setOnTouchListener(listener);

        myShimmerTextView = (ShimmerTextView) findViewById(R.id.break_image_instruction);
        shimmer = new Shimmer();
        shimmer.start(myShimmerTextView);

        brokenView.setCallback(new BrokenCallback() {

            @Override
            public void onStart(View v) {
            }

            @Override
            public void onCancel(View v) {
            }

            @Override
            public void onCancelEnd(View v) {
                shimmer = new Shimmer();
                myShimmerTextView.setText("Long press to break the window");
                shimmer.start(myShimmerTextView);
            }

            @Override
            public void onRestart(View v) {}

            @Override
            public void onFalling(View v) {
                shimmer.cancel();
                myShimmerTextView.setText("");
            }

            @Override
            public void onFallingEnd(View v) {
            }
        });

        ScrollingImageView scrollingBackground = (ScrollingImageView) findViewById(R.id.scrolling_background);
        scrollingBackground.start();

        ViewPager pager = (ViewPager) findViewById(R.id.containerViewPager);
        mAdapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        pager.setPageTransformer(true, new RotateUpTransformer());
        TransformerItem transformerItem = new TransformerItem(CubeOutTransformer.class);
        try {
            pager.setPageTransformer(true, transformerItem.clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayer.start();
        mPlayer.setOnErrorListener(this);
        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra) {
                onError(mPlayer, what, extra);
                return true;
            }
        });

    }

    @Override
    public void onPause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String EXTRA_POSITION = "EXTRA_POSITION";


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int position = getArguments().getInt(EXTRA_POSITION);
            if(position == 1){
                TextView quote = (TextView) inflater.inflate(R.layout.fragment1, container, false);
                quote.setBackgroundColor(0xFFAA66CC);
                return quote;
            }
            else if(position == 2){
                View self = inflater.inflate(R.layout.fragment3, container, false);
                self.setBackgroundColor(0xFF99CC00);
                return self;
            }
            int data[];
            int color;
            switch (position){
                case 3:
                    color = 0xFFca133c;
                    data = new int[]
                            {R.drawable.divya, R.drawable.divya2, R.drawable.divya3, R.drawable.divya4,
                            R.drawable.divya5, R.drawable.divya6, R.drawable.divya7, R.drawable.divya8, R.drawable.divya9};
                    break;
                case 4:
                    color = 0xFFFFBB33;
                    data = new int[]
                            {R.drawable.divya9, R.drawable.divya8, R.drawable.divya7, R.drawable.divya6,
                            R.drawable.divya5, R.drawable.divya4, R.drawable.divya3, R.drawable.divya2, R.drawable.divya};
                    break;
                case 5:
                    color = 0xFF381fd9;
                    data = new int[]
                            {R.drawable.divya, R.drawable.divya3, R.drawable.divya5, R.drawable.divya7,
                            R.drawable.divya9, R.drawable.divya2, R.drawable.divya4, R.drawable.divya6, R.drawable.divya8};
                    break;
                case 6:
                    color = 0xFFe90bda;
                    data = new int[]
                            {R.drawable.divya2, R.drawable.divya4, R.drawable.divya6, R.drawable.divya8,
                            R.drawable.divya, R.drawable.divya3, R.drawable.divya5, R.drawable.divya7, R.drawable.divya9};
                    break;
                case 7:
                    color = 0xFF35cb1e;
                    data = new int[]
                            {R.drawable.divya8, R.drawable.divya6, R.drawable.divya4, R.drawable.divya2,
                            R.drawable.divya9, R.drawable.divya7, R.drawable.divya5, R.drawable.divya3, R.drawable.divya};
                    break;
                default:
                    color = 0xFFe9800b;
                    data = new int[]
                            {R.drawable.divya3, R.drawable.divya6, R.drawable.divya9, R.drawable.divya,
                            R.drawable.divya4, R.drawable.divya7, R.drawable.divya2, R.drawable.divya5, R.drawable.divya8};
                    break;
            }
            View recyclerView = inflater.inflate(R.layout.fragment4, container, false);
            recyclerView.setBackgroundColor(color);
            PhotoAdapter adapter = new PhotoAdapter(getContext(), data);
            RecyclerView mRecyclerView = (RecyclerView) recyclerView.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                }
            });
            mRecyclerView.setAdapter(adapter);
            return recyclerView;
        }

    }

    private static final class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.EXTRA_POSITION, position + 1);

            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 8;
        }

    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends ViewPager.PageTransformer> clazz;

        public TransformerItem(Class<? extends ViewPager.PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
