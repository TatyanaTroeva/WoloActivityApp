package com.woloactivityapp.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.woloactivityapp.main.R;

public class CustomTextView extends TextView {
	/** An <code>LruCache</code> for previously loaded typefaces. */
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(2);

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Get our custom attributes
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.TypefaceView, 0, 0);
		try {
			String typefaceName = a
					.getString(R.styleable.TypefaceView_typeface);
			if (!isInEditMode() && !TextUtils.isEmpty(typefaceName)) {
				Typeface typeface = sTypefaceCache.get(typefaceName);
				if (typeface == null) {
					typeface = Typeface.createFromAsset(context.getAssets(),
							String.format("fonts/%s.ttf", typefaceName));
					// Cache the Typeface object
					sTypefaceCache.put(typefaceName, typeface);
				}
				setTypeface(typeface);
			}
		} finally {
			a.recycle();
		}
	}
}
