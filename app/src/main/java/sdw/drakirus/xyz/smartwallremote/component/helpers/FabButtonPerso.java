package sdw.drakirus.xyz.smartwallremote.component.helpers;

/**
 * Created by drakirus (p.champion) on 19/02/18.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import mbanje.kurt.fabbutton.AnimationUtils;
import mbanje.kurt.fabbutton.CircleImageView;
import mbanje.kurt.fabbutton.CircleImageView.OnFabViewListener;
import mbanje.kurt.fabbutton.FabButton;
import mbanje.kurt.fabbutton.ProgressRingView;
import mbanje.kurt.fabbutton.R.drawable;
import mbanje.kurt.fabbutton.R.id;
import mbanje.kurt.fabbutton.R.layout;
import mbanje.kurt.fabbutton.R.styleable;
import mbanje.kurt.fabbutton.ViewGroupUtils;

public class FabButtonPerso extends FrameLayout implements OnFabViewListener {
    public CircleImageView circle;
    public ProgressRingView ring;
    private float ringWidthRatio = 0.14F;
    private boolean indeterminate;
    private boolean autostartanim;
    public int endBitmapResource;
    public boolean showEndBitmap;
    private boolean hideProgressOnComplete;

    public FabButtonPerso(Context context) {
        super(context);
        this.init(context, (AttributeSet)null, 0);
    }

    public FabButtonPerso(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public FabButtonPerso(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    protected void init(Context context, AttributeSet attrs, int defStyle) {
        View v = View.inflate(context, layout.widget_fab_button, this);
        this.setClipChildren(false);
        this.circle = (CircleImageView)v.findViewById(id.fabbutton_circle);
        this.ring = (ProgressRingView)v.findViewById(id.fabbutton_ring);
        this.circle.setFabViewListener(this);
        this.ring.setFabViewListener(this);
        int color = -16777216;
        int progressColor = -16777216;
        int animDuration = 4000;
        int icon = -1;
        float maxProgress = 0.0F;
        float progress = 0.0F;
        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, styleable.CircleImageView);
            color = a.getColor(styleable.CircleImageView_android_color, -16777216);
            progressColor = a.getColor(styleable.CircleImageView_fbb_progressColor, -16777216);
            progress = a.getFloat(styleable.CircleImageView_android_progress, 0.0F);
            maxProgress = a.getFloat(styleable.CircleImageView_android_max, 100.0F);
            this.indeterminate = a.getBoolean(styleable.CircleImageView_android_indeterminate, false);
            this.autostartanim = a.getBoolean(styleable.CircleImageView_fbb_autoStart, true);
            animDuration = a.getInteger(styleable.CircleImageView_android_indeterminateDuration, animDuration);
            icon = a.getResourceId(styleable.CircleImageView_android_src, icon);
            this.ringWidthRatio = a.getFloat(styleable.CircleImageView_fbb_progressWidthRatio, this.ringWidthRatio);
            this.endBitmapResource = a.getResourceId(styleable.CircleImageView_fbb_endBitmap, drawable.ic_fab_complete);
            this.showEndBitmap = a.getBoolean(styleable.CircleImageView_fbb_showEndBitmap, false);
            this.hideProgressOnComplete = a.getBoolean(styleable.CircleImageView_fbb_hideProgressOnComplete, false);
            this.circle.setShowShadow(a.getBoolean(styleable.CircleImageView_fbb_showShadow, true));
            a.recycle();
        }

        this.circle.setColor(color);
        this.circle.setShowEndBitmap(this.showEndBitmap);
        this.circle.setRingWidthRatio(this.ringWidthRatio);
        this.ring.setProgressColor(progressColor);
        this.ring.setProgress(progress);
        this.ring.setMaxProgress(maxProgress);
        this.ring.setAutostartanim(this.autostartanim);
        this.ring.setAnimDuration(animDuration);
        this.ring.setRingWidthRatio(this.ringWidthRatio);
        this.ring.setIndeterminate(this.indeterminate);
        if(icon != -1) {
            this.circle.setIcon(icon, this.endBitmapResource);
        }

    }

    public void setShadow(boolean showShadow) {
        this.circle.setShowShadow(showShadow);
    }

    public void setIcon(int resource, int endIconResource) {
        this.circle.setIcon(resource, endIconResource);
    }

    public void showShadow(boolean show) {
        this.circle.setShowShadow(show);
        this.invalidate();
    }

    public void setColor(int color) {
        this.circle.setColor(color);
    }

    public void setIcon(Drawable icon, Drawable endIcon) {
        this.circle.setIcon(icon, endIcon);
    }

    public void resetIcon() {
        this.circle.resetIcon();
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        this.ring.setIndeterminate(indeterminate);
    }

    public void setOnClickListener(OnClickListener listener) {
        super.setOnClickListener(listener);
        this.ring.setOnClickListener(listener);
        this.circle.setOnClickListener(listener);
    }

    public void showProgress(boolean show) {
        this.circle.showRing(show);
    }

    public void hideProgressOnComplete(boolean hide) {
        this.hideProgressOnComplete = hide;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.circle.setEnabled(enabled);
        this.ring.setEnabled(enabled);
    }

    public void setProgress(float progress) {
        this.ring.setProgress(progress);
    }

    public void onProgressVisibilityChanged(boolean visible) {
        if(visible) {
            this.ring.setVisibility(View.VISIBLE);
            this.ring.startAnimation();
        } else {
            this.ring.stopAnimation(true);
            this.ring.setVisibility(View.GONE);
        }

    }

    public void onProgressCompleted() {
        this.circle.showCompleted(this.showEndBitmap, this.hideProgressOnComplete);
        if(this.hideProgressOnComplete) {
            this.ring.setVisibility(View.GONE);
        }

    }

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FabButton> {
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;
        private Rect mTmpRect;
        private boolean mIsAnimatingOut;
        private float mTranslationY;

        public Behavior() {
        }

        public boolean layoutDependsOn(CoordinatorLayout parent, FabButton child, View dependency) {
            return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof SnackbarLayout;
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, FabButton child, View dependency) {
            if(dependency instanceof SnackbarLayout) {
                this.updateFabTranslationForSnackbar(parent, child, dependency);
            } else if(dependency instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout)dependency;
                if(this.mTmpRect == null) {
                    this.mTmpRect = new Rect();
                }

                Rect rect = this.mTmpRect;
                ViewGroupUtils.getDescendantRect(parent, dependency, rect);
                if(rect.bottom <= this.getMinimumHeightForVisibleOverlappingContent(appBarLayout)) {
                    if(!this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
                        this.animateOut(child);
                    }
                } else if(child.getVisibility() != View.VISIBLE) {
                    this.animateIn(child);
                }
            }

            return false;
        }

        final int getMinimumHeightForVisibleOverlappingContent(AppBarLayout bar) {
            int topInset = 0;
            int minHeight = ViewCompat.getMinimumHeight(bar);
            if(minHeight != 0) {
                return minHeight * 2 + topInset;
            } else {
                int childCount = bar.getChildCount();
                return childCount >= 1?ViewCompat.getMinimumHeight(bar.getChildAt(childCount - 1)) * 2 + topInset:0;
            }
        }

        private void updateFabTranslationForSnackbar(CoordinatorLayout parent, FabButton fab, View snackbar) {
            float translationY = this.getFabTranslationYForSnackbar(parent, fab);
            if(translationY != this.mTranslationY) {
                ViewCompat.animate(fab).cancel();
                if(Math.abs(translationY - this.mTranslationY) == (float)snackbar.getHeight()) {
                    ViewCompat.animate(fab).translationY(translationY).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener((ViewPropertyAnimatorListener)null);
                } else {
                    ViewCompat.setTranslationY(fab, translationY);
                }

                this.mTranslationY = translationY;
            }

        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout parent, FabButton fab) {
            float minOffset = 0.0F;
            List<View> dependencies = parent.getDependencies(fab);
            int i = 0;

            for(int z = dependencies.size(); i < z; ++i) {
                View view = (View)dependencies.get(i);
                if(view instanceof SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                    minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - (float)view.getHeight());
                }
            }

            return minOffset;
        }

        private void animateIn(FabButton button) {
            button.setVisibility(View.VISIBLE);
            ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener((ViewPropertyAnimatorListener)null).start();
        }

        private void animateOut(final FabButton button) {
            ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                public void onAnimationStart(View view) {
                    Behavior.this.mIsAnimatingOut = true;
                }

                public void onAnimationCancel(View view) {
                    Behavior.this.mIsAnimatingOut = false;
                }

                public void onAnimationEnd(View view) {
                    Behavior.this.mIsAnimatingOut = false;
                    view.setVisibility(View.VISIBLE);
                }
            }).start();

        }

        static {
            SNACKBAR_BEHAVIOR_ENABLED = VERSION.SDK_INT >= 11;
        }
    }
}

