package manhua.easou.com.mymovetest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DanmuContainerView extends ViewGroup {
    private static final int ROW = 3;
    private List<Danmu> list;
    private int currentDanmuNumber = 0;
    private int screenWith;
    private int RowPadding = 30;
    private int[] speend = {5, 4, 5};
    private SparseArray<View> cacheViews=new SparseArray<View>();
    int cacheNumber=1;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    View lastDanmuView = getChildAt(getChildCount() - 1);
                    for (int i = 0; i < DanmuContainerView.this.getChildCount(); i++) {
                        View view = DanmuContainerView.this.getChildAt(i);
                        int seed = speend[(Integer) view.getTag() % ROW];
                        // if(view.getX()+view.getWidth() >= 0)
                        System.out.println("i======" + i + "view.getLeft====" + view.getLeft() + "view.getRight====" + view.getRight() + "getChildCount()===" + getChildCount());
                        System.out.println("i======" + i + "速度是===" + seed);
                        view.offsetLeftAndRight(seed);
                        if (view.getLeft() >= screenWith) {
                            //cacheViews.put(cacheNumber,view);
                           // cacheNumber++;
                            DanmuContainerView.this.removeView(view);

                        }
                        boolean needAddView = false;
                        if (currentDanmuNumber >= ROW) {
                            System.out.println("view.getTag()======" + view.getTag() + "currentDanmuNumber===" + currentDanmuNumber);
                            if (((int) view.getTag()) == (currentDanmuNumber - ROW)) {
                                if (view != null) {
                                    if (view.getLeft() > RowPadding) {
                                        needAddView = true;
                                    }
                                }
                            }
                        } else {
                            needAddView = true;

                        }
                        if (lastDanmuView != null && lastDanmuView.getRight() > RowPadding && needAddView) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    handler.sendEmptyMessage(2);
                                }
                            }).start();

                        }
                    }
                    handler.sendEmptyMessageDelayed(1, 30);
                    break;
                case 2:
                    addNextDanmu(currentDanmuNumber + 1);
                    break;
                default:
                    break;
            }

        }
    };

    public DanmuContainerView(Context context) {
        this(context, null, 0);
    }

    public DanmuContainerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmuContainerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void startDanmu(List<Danmu> list) {
        this.list = list;
        handler.sendEmptyMessage(1);
        addNextDanmu(1);
    }


    public void addNextDanmu(int danmuNumber) {
        currentDanmuNumber = danmuNumber;
        View convertView=null;
        if (currentDanmuNumber <= list.size()) {
           // if(cacheViews.size()==0) {
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.danmu_item, null);
//            }else{
//                convertView=cacheViews.get(cacheViews.size()-1);
//            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_text);
            textView.setText(list.get(currentDanmuNumber - 1).content);
            convertView.setTag(currentDanmuNumber - 1);
            super.addView(convertView);
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWith = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("onMeasure==", "screenWith====" + screenWith + "height=====" + height);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View children = getChildAt(i);
            measureChild(children, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("changed==" + changed + "onLayout====", "l==" + l + "t====" + t + "r====" + r + "b====" + b);
        if (changed)
            return;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View children = getChildAt(i);
            // 把宽高拿到，宽高都是包含ItemDecorate的尺寸
            int width = children.getMeasuredWidth();
            int height = children.getMeasuredHeight();

            int rowNuber = (Integer) (children.getTag()) % ROW;
            Log.e("i===" + i + "onLayout===========", "children.getLeft" + children.getLeft() + "width=====" + width);
            if (children.getRight() > RowPadding) {
                children.layout(l + children.getLeft(), rowNuber * height, l + width + children.getLeft(), height + rowNuber * height);
            } else {
                //2 如果是0,相当于没有子view，子View不会显示,所以给2
                children.layout(-width, rowNuber * height, 2, height + rowNuber * height);
            }
        }

    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
