package dk.madslee.imageCapInsets;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.ImageView;
import dk.madslee.imageCapInsets.utils.NinePatchBitmapFactory;
import dk.madslee.imageCapInsets.utils.RCTImageLoaderListener;
import dk.madslee.imageCapInsets.utils.RCTImageLoaderTask;


public class RCTImageCapInsetView extends ImageView {
    private Rect mCapInsets;
    private String mUri;

    public RCTImageCapInsetView(Context context) {
        super(context);

        mCapInsets = new Rect();
    }

    public void setCapInsets(Rect insets) {
        mCapInsets = insets;
        reload();
    }

    public void setSource(String uri) {
        mUri = uri;
        reload();
    }

    public void reload() {
        final String key = mUri + "-" + mCapInsets.toShortString();
        final RCTImageCache cache = RCTImageCache.getInstance();

        if (cache.has(key)) {
            setBackground(cache.get(key));
            return;
        }

        RCTImageLoaderTask task = new RCTImageLoaderTask(mUri, getContext(), new RCTImageLoaderListener() {
            @Override
            public void onImageLoaded(Bitmap bitmap) {
                int ratio = Math.round(bitmap.getDensity() / 160);
                int top = mCapInsets.top * ratio;
                int right = bitmap.getWidth() - (mCapInsets.right * ratio);
                int bottom = bitmap.getHeight() - (mCapInsets.bottom * ratio);
                int left = mCapInsets.left * ratio;

                NinePatchDrawable ninePatchDrawable = NinePatchBitmapFactory.createNinePathWithCapInsets(getResources(), bitmap, top, left, bottom, right, null);
                setBackground(ninePatchDrawable);

                cache.put(key, ninePatchDrawable);
            }
        });

        task.execute();
    }

	
	/*
	 * HACK: the following methods fix the wrong dimensions of the ninepatch when the activity is hidden and shown again,
	 * and when the device is rotated. This hack is required because RN uses its own layout system that bypasses the android one.
	 * In order to update the bounds of the background drawable, we pretend that the frame of the view has been changed.
	 */
		
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);       		
		
		if (visibility == android.view.View.VISIBLE) {			
			fireFakeFrameChange();
        }
    }
	
	protected void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		fireFakeFrameChange();
    }
	
	private void fireFakeFrameChange() {		
		final int left = getLeft();
		final int top = getTop();
		final int right = getRight();
		final int bottom = getBottom();
				
		setFrame(left+1, top, right, bottom);
		setFrame(left, top, right, bottom);		
	}
}
