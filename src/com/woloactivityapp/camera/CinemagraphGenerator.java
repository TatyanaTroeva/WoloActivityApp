package com.woloactivityapp.camera;

import java.io.File;
import java.util.ArrayList;

import com.woloactivityapp.main.BottomActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class CinemagraphGenerator
{
	public static final int CINE_PORTRAIT_HEIGHT = 480;
	public static final int CINE_PORTRAIT_WIDTH = 360;
	public static final float DEFAULT_FPS = 20.0F;
	private static final int GIF_QUALITY = 100;
	public static final float MAX_FPS = 60.0F;
	public static final int MAX_TIME_FINAL_CINE_LARGE_HEAP = 4;
	public static final int MAX_TIME_FINAL_CINE_SMALL_HEAP = 2;
	public static final float MIN_FPS = 5.0F;
	private static CinemagraphGenerator instance = null;
//  private static boolean maskStarted = false;
	private Bitmap alphaMask = null;
//  private Stack<Bitmap> alphaMaskStack = new Stack();
	private Bitmap anchor;
//  private boolean animatedGifGenerated = false;
	private ArrayList<Bitmap> bitmapList;
//  private boolean cineGenerated = false;
//  private final GeneratedCinemagraphController cinemagraphController = GeneratedCinemagraphController.getInstance();
//  private CinemagraphStabilizer cinemagraphStabilizer = null;
	private final Context currentContext;
//  private FilterType currentFilterType = FilterType.RAW;
//  private LoopType currentLoopType = LoopType.LOOP_FORWARD_BACKWARD;
//  private FilterEngine filterEngine;
	private float fps = 20.0F;
	private boolean framesLoaded = false;
	private boolean framesSaved = false;
//  private boolean hasStabilizationFailed = false;
//  private boolean hasStabilizedCache = false;
//  private boolean interruptFilterBitmaps = false;
//  private boolean isApplyingFilter = false;
//  private boolean isFiltered = false;
//  private boolean isGeneratingCine = false;
//  private boolean isGeneratingFrames = false;
//  private boolean isStabilized = false;
//  private boolean isStabilizingFrames = false;
	private File originalVideoFile = null;
//  private FilterType pendingFilterType;
	protected int rotation = 0;
//  private boolean shouldShowMask = false;
//  private final File stabilizedVideo = initStabilizedVideoFile();
//  private CinemagraphStabilizer.Callback stabilizerCallback;
//  private long startedSavingOriginalBitmaps;
//  private boolean takenWithFrontCamera = false;

	private CinemagraphGenerator(Context context) {
		currentContext = context;
	}

//  private File animGifGenerate()
//  {
//    AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
//    localAnimatedGifEncoder.setFrameRate(getFps());
//    localAnimatedGifEncoder.setRepeat(0);
//    localAnimatedGifEncoder.setQuality(100);
//    int i = this.bitmapList.size();
//    if (getLoopType() != LoopType.LOOP_FORWARD_BACKWARD)
//      (-1 + i * 2);
//    float f;
//    if (this.bitmapList.size() >= 40)
//    {
//      f = (float)(0.3F * (40.0D / this.bitmapList.size()));
//      localAnimatedGifEncoder.setSize((int)(f * ((Bitmap)this.bitmapList.get(0)).getWidth()), (int)(f * ((Bitmap)this.bitmapList.get(0)).getHeight()));
//    }
//    while (true)
//    {
//      int j;
//      try
//      {
//        File localFile = new File(this.cinemagraphController.getCine().finalMovieGifFilePath());
//        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
//        long l1 = System.currentTimeMillis();
//        localAnimatedGifEncoder.start(localFileOutputStream);
//        Iterator localIterator;
//        if (getLoopType() != LoopType.LOOP_BACKWARD)
//        {
//          localIterator = this.bitmapList.iterator();
//          if (localIterator.hasNext());
//        }
//        else
//        {
//          if (getLoopType() != LoopType.LOOP_FORWARD)
//          {
//            j = -1 + this.bitmapList.size();
//            if (getLoopType() == LoopType.LOOP_FORWARD_BACKWARD)
//              break label330;
//            j--;
//            break label330;
//          }
//          localAnimatedGifEncoder.finish();
//          long l2 = System.currentTimeMillis();
//          (l2 - l1);
//          return localFile;
//          f = 0.5F - (0.5F - 0.3F) / 40.0F * this.bitmapList.size();
//          break;
//        }
//        localAnimatedGifEncoder.addFrame((Bitmap)localIterator.next());
//        continue;
//      }
//      catch (FileNotFoundException localFileNotFoundException)
//      {
//        AppUtils.logException(localFileNotFoundException);
//        localFileNotFoundException.printStackTrace();
//        return null;
//      }
//      int k;
//      label330: 
//      do
//      {
//        localAnimatedGifEncoder.addFrame((Bitmap)this.bitmapList.get(k));
//        k--;
//        continue;
//        k = j;
//      }
//      while (k >= 0);
//    }
//  }
//
//  private void blendFrames()
//  {
//    for (int i = 0; ; i++)
//    {
//      if (i >= this.bitmapList.size())
//        return;
//      new Canvas((Bitmap)this.bitmapList.get(i)).drawBitmap(this.anchor, 0.0F, 0.0F, new Paint());
//    }
//  }
//
//  private void cleanTempFiles()
//  {
//    this.cinemagraphController.cancelCinemagraphCreation();
//  }
//
//  private Bitmap filterBitmap(Bitmap paramBitmap, FilterType paramFilterType)
//  {
//    return this.filterEngine.applyFilterOnImage(paramBitmap);
//  }
//
//  private void filterBitmapList(FilterType paramFilterType, Callback paramCallback)
//  {
//    if ((this.bitmapList == null) || (this.bitmapList.size() == 0))
//      return;
//    this.currentFilterType = paramFilterType;
//    this.alphaMask = getAlphaMaskFromCurrentAnchor();
//    if (paramFilterType == FilterType.RAW)
//    {
//      restoreFromOriginal();
//      setAnchorFrame();
//      return;
//    }
//    restoreFromOriginal();
//    setupGLRenderer((Bitmap)this.bitmapList.get(0), paramFilterType);
//    for (int i = 0; ; i++)
//    {
//      if ((this.bitmapList == null) || (i >= this.bitmapList.size()));
//      do
//      {
//        this.filterEngine = null;
//        return;
//        if (this.bitmapList == null)
//          break;
//      }
//      while (this.interruptFilterBitmaps);
//      this.bitmapList.set(i, filterBitmap((Bitmap)this.bitmapList.get(i), paramFilterType));
//      if (i == 0)
//      {
//        setAnchorFrame();
//        paramCallback.anchorHasBeenProcessed();
//      }
//      paramCallback.onProgress(Integer.valueOf(i));
//    }
//  }
//
//  private void generateAnimatedGifAsync(Callback paramCallback)
//  {
//    new AsyncTask()
//    {
//      CinemagraphGenerator.Callback callback;
//
//      protected File doInBackground(Object[] paramAnonymousArrayOfObject)
//      {
//        this.callback = ((CinemagraphGenerator.Callback)paramAnonymousArrayOfObject[0]);
//        return CinemagraphGenerator.this.animGifGenerate();
//      }
//
//      protected void onPostExecute(File paramAnonymousFile)
//      {
//        CinemagraphGenerator.this.animatedGifGenerated = true;
//        this.callback.onComplete(true, paramAnonymousFile);
//      }
//    }
//    .execute(new Object[] { paramCallback });
//  }
//
//  private void generateFrames()
//  {
//    getFramesWithFFMPEG();
//    if (this.anchor == null)
//      setAnchorFrame();
//  }
//
//  private Bitmap getAlphaMaskFromCurrentAnchor()
//  {
//    Bitmap localBitmap1 = ((Bitmap)this.bitmapList.get(0)).extractAlpha();
//    Bitmap localBitmap2 = this.anchor.extractAlpha();
//    Paint localPaint = new Paint();
//    localPaint.setFilterBitmap(false);
//    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//    new Canvas(localBitmap1).drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint);
//    return localBitmap1;
//  }
//
//  private void getFramesWithFFMPEG()
//  {
//    if ((this.isStabilized) && (!this.hasStabilizationFailed));
//    for (File localFile = this.stabilizedVideo; ; localFile = this.originalVideoFile)
//    {
//      this.bitmapList = FfmpegUtils.getFrames(localFile, (int)(20.0F * getMaxTimeCine()));
//      return;
//    }
//  }
//
//  @Deprecated
//  private void getFramesWithMediaMetadataRetriever(String paramString)
//  {
//    File localFile = new File(paramString);
//    MediaMetadataRetriever localMediaMetadataRetriever = new MediaMetadataRetriever();
//    try
//    {
//      localMediaMetadataRetriever.setDataSource(localFile.getAbsolutePath());
//      l = 1000L * Long.parseLong(localMediaMetadataRetriever.extractMetadata(9)) / 40L;
//      i = 0;
//      if (this.bitmapList.size() >= 40)
//        return;
//    }
//    catch (IllegalArgumentException localIllegalArgumentException)
//    {
//      while (true)
//      {
//        long l;
//        int i;
//        localIllegalArgumentException.printStackTrace();
//        continue;
//        Bitmap localBitmap = localMediaMetadataRetriever.getFrameAtTime(l * i, 3);
//        if (localBitmap != null)
//        {
//          this.bitmapList.add(localBitmap);
//          AppUtils.log("FrameAdded!");
//        }
//        i++;
//      }
//    }
//  }

	public static CinemagraphGenerator getInstance() {
		if (instance == null)
			instance = new CinemagraphGenerator(BottomActivity.gInstance);
		return instance;
	}

	public static int getMaxTimeCine() {
		return 2;
	}

//  private static File initStabilizedVideoFile()
//  {
//    try
//    {
//      File localFile = new File(CineLocations.getFinalVideoCacheDirectory(), "stabilized.mp4");
//      return localFile;
//    }
//    catch (ExternalMediaException localExternalMediaException)
//    {
//      AppUtils.logException(localExternalMediaException);
//    }
//    return null;
//  }
//
//  private void onStabilized(Callback paramCallback)
//  {
//    paramCallback.onComplete(true, null);
//    this.isStabilizingFrames = false;
//    if (!this.hasStabilizationFailed)
//    {
//      this.isStabilized = true;
//      this.hasStabilizedCache = true;
//    }
//  }
//
//  private void reset()
//  {
//    this.animatedGifGenerated = false;
//    this.fps = 20.0F;
//    this.alphaMaskStack = new Stack();
//    this.currentFilterType = FilterType.RAW;
//    this.currentLoopType = LoopType.LOOP_FORWARD_BACKWARD;
//    maskStarted = false;
//    this.shouldShowMask = false;
//    this.isStabilized = false;
//    this.hasStabilizedCache = false;
//    this.hasStabilizationFailed = false;
//    this.originalVideoFile = null;
//    this.framesSaved = false;
//    this.cineGenerated = false;
//    this.bitmapList = null;
//    this.anchor = null;
//    this.framesLoaded = false;
//    this.alphaMask = null;
//    if (this.cinemagraphStabilizer != null)
//      this.cinemagraphStabilizer.pause();
//    this.cinemagraphStabilizer = null;
//  }
//
//  private void restoreFromOriginal()
//  {
//    if (!this.framesSaved)
//      return;
//    getFramesWithFFMPEG();
//  }
//
	private void setAnchorFrame() {
		if (bitmapList.get(0) != null)
			return;
		anchor = ((Bitmap) this.bitmapList.get(0)).copy(
				Bitmap.Config.ARGB_8888, true);
		anchor.setHasAlpha(true);
		if (alphaMask != null) {
			Paint localPaint = new Paint();
			localPaint.setFilterBitmap(false);
			localPaint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_OUT));
			new Canvas(this.anchor).drawBitmap(alphaMask, 0.0F, 0.0F,
					localPaint);
		}
	}

//  private void setupGLRenderer(Bitmap paramBitmap, FilterType paramFilterType)
//  {
//    this.filterEngine = FilterEngine.filterEngineWithProgram(FilterData.programWithResolution(paramFilterType, new Sizei(paramBitmap.getWidth(), paramBitmap.getHeight())));
//  }
//
//  public boolean areFramesReady()
//  {
//    return (this.framesLoaded) && (!this.isApplyingFilter) && (!this.isGeneratingFrames) && (!this.isStabilizingFrames) && (!this.isGeneratingCine);
//  }
//
//  public void cleanOnAbandon()
//  {
//    reset();
//    cleanTempFiles();
//  }
//
//  public void cleanOnShare()
//  {
//    reset();
//  }
//
//  public void filterFramesAsync(FilterType paramFilterType, Callback paramCallback)
//  {
//    if (this.isApplyingFilter)
//    {
//      this.interruptFilterBitmaps = true;
//      this.pendingFilterType = paramFilterType;
//      return;
//    }
//    this.isApplyingFilter = true;
//    new AsyncTask()
//    {
//      CinemagraphGenerator.Callback callback;
//
//      protected FilterType doInBackground(Object[] paramAnonymousArrayOfObject)
//      {
//        this.callback = ((CinemagraphGenerator.Callback)paramAnonymousArrayOfObject[1]);
//        CinemagraphGenerator.this.filterBitmapList((FilterType)paramAnonymousArrayOfObject[0], this.callback);
//        return (FilterType)paramAnonymousArrayOfObject[0];
//      }
//
//      protected void onPostExecute(FilterType paramAnonymousFilterType)
//      {
//        if (CinemagraphGenerator.this.interruptFilterBitmaps)
//        {
//          CinemagraphGenerator.this.isApplyingFilter = false;
//          CinemagraphGenerator.this.interruptFilterBitmaps = false;
//          CinemagraphGenerator.this.filterFramesAsync(CinemagraphGenerator.this.pendingFilterType, this.callback);
//          return;
//        }
//        this.callback.onComplete(true, null);
//        CinemagraphGenerator.this.isApplyingFilter = false;
//        if (paramAnonymousFilterType == FilterType.RAW)
//        {
//          CinemagraphGenerator.this.isFiltered = false;
//          return;
//        }
//        CinemagraphGenerator.this.isFiltered = true;
//      }
//    }
//    .execute(new Object[] { paramFilterType, paramCallback });
//  }
//
//  public void generateAnimatedGif(Callback paramCallback)
//  {
//    File localFile = new File(this.cinemagraphController.getCine().finalMovieGifFilePath());
//    if (!localFile.exists())
//    {
//      generateAnimatedGifAsync(paramCallback);
//      return;
//    }
//    paramCallback.onComplete(true, localFile);
//  }
//
//  public void generateCineAsync(Callback paramCallback)
//  {
//    if ((this.bitmapList != null) && (this.bitmapList.size() > 0));
//    for (boolean bool = true; ; bool = false)
//    {
//      CineAssert.assertTrue(bool);
//      new AsyncTask()
//      {
//        CinemagraphGenerator.Callback callback;
//
//        // ERROR //
//        protected File doInBackground(CinemagraphGenerator.Callback[] paramAnonymousArrayOfCallback)
//        {
//          // Byte code:
//          //   0: aload_0
//          //   1: aload_1
//          //   2: iconst_0
//          //   3: aaload
//          //   4: putfield 27	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:callback	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator$Callback;
//          //   7: aload_0
//          //   8: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   11: iconst_1
//          //   12: invokestatic 31	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$12	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;Z)V
//          //   15: aload_0
//          //   16: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   19: iconst_0
//          //   20: invokestatic 34	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$13	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;Z)V
//          //   23: aload_0
//          //   24: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   27: iconst_1
//          //   28: invokestatic 31	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$12	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;Z)V
//          //   31: aload_0
//          //   32: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   35: invokestatic 38	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$14	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;)Z
//          //   38: ifeq +10 -> 48
//          //   41: aload_0
//          //   42: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   45: invokestatic 41	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$15	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;)V
//          //   48: new 43	java/io/File
//          //   51: dup
//          //   52: invokestatic 49	com/cinemagram/utils/CineLocations:getFinalVideoCacheDirectory	()Ljava/io/File;
//          //   55: ldc 51
//          //   57: invokespecial 54	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
//          //   60: astore_2
//          //   61: aload_0
//          //   62: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   65: invokestatic 58	com/cinemagram/main/cineplayer/CinemagraphGenerator:access$16	(Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;)Ljava/util/ArrayList;
//          //   68: aload_2
//          //   69: aload_0
//          //   70: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   73: invokevirtual 62	com/cinemagram/main/cineplayer/CinemagraphGenerator:getFps	()F
//          //   76: aload_0
//          //   77: getfield 18	com/cinemagram/main/cineplayer/CinemagraphGenerator$4:this$0	Lcom/cinemagram/main/cineplayer/CinemagraphGenerator;
//          //   80: invokevirtual 66	com/cinemagram/main/cineplayer/CinemagraphGenerator:getLoopType	()Lcom/cinemagram/main/cineplayer/LoopType;
//          //   83: invokestatic 72	com/cinemagram/utils/FfmpegUtils:generateCine	(Ljava/util/ArrayList;Ljava/io/File;FLcom/cinemagram/main/cineplayer/LoopType;)Ljava/io/File;
//          //   86: pop
//          //   87: aload_2
//          //   88: areturn
//          //   89: astore_3
//          //   90: aload_3
//          //   91: invokevirtual 76	com/cinemagram/utils/ExternalMediaException:getLocalizedMessage	()Ljava/lang/String;
//          //   94: invokestatic 82	com/cinemagram/main/AppUtils:log	(Ljava/lang/String;)V
//          //   97: aload_3
//          //   98: invokestatic 86	com/cinemagram/main/AppUtils:logException	(Ljava/lang/Exception;)V
//          //   101: aconst_null
//          //   102: areturn
//          //   103: astore_3
//          //   104: goto -14 -> 90
//          //
//          // Exception table:
//          //   from	to	target	type
//          //   48	61	89	com/cinemagram/utils/ExternalMediaException
//          //   61	87	103	com/cinemagram/utils/ExternalMediaException
//        }
//
//        protected void onPostExecute(File paramAnonymousFile)
//        {
//          CinemagraphGenerator.this.isGeneratingCine = false;
//          CinemagraphGenerator.Callback localCallback = this.callback;
//          if (paramAnonymousFile != null);
//          for (boolean bool = true; ; bool = false)
//          {
//            localCallback.onComplete(bool, paramAnonymousFile);
//            CinemagraphGenerator.this.isGeneratingCine = false;
//            CinemagraphGenerator.this.cineGenerated = true;
//            return;
//          }
//        }
//      }
//      .execute(new Callback[] { paramCallback });
//      return;
//    }
//  }
//
//  public void generateFramesAsync(String paramString, int paramInt, Callback paramCallback)
//  {
//    if (this.originalVideoFile == null)
//      this.originalVideoFile = new File(paramString);
//    GeneratedCinemagraphController.getInstance().createCinemagraphWithTmpMovieFilePath(this.originalVideoFile);
//    if (paramInt == 1)
//      this.takenWithFrontCamera = true;
//    while (true)
//    {
//      new AsyncTask()
//      {
//        CinemagraphGenerator.Callback callback;
//
//        protected Void doInBackground(Object[] paramAnonymousArrayOfObject)
//        {
//          this.callback = ((CinemagraphGenerator.Callback)paramAnonymousArrayOfObject[0]);
//          CinemagraphGenerator.this.isGeneratingFrames = true;
//          CinemagraphGenerator.this.generateFrames();
//          return null;
//        }
//
//        protected void onPostExecute(Void paramAnonymousVoid)
//        {
//          this.callback.onComplete(true, null);
//          GeneratedCinemagraphController.getInstance().getCine().movieRotationDegrees = CinemagraphGenerator.this.rotation;
//          GeneratedCinemagraphController.getInstance().getCine().setDimensions(((Bitmap)CinemagraphGenerator.this.bitmapList.get(0)).getWidth(), ((Bitmap)CinemagraphGenerator.this.bitmapList.get(0)).getHeight());
//          CinemagraphGenerator.this.framesLoaded = true;
//          CinemagraphGenerator.this.isGeneratingFrames = false;
//          CinemagraphGenerator.this.startCinemagraphStabilizer();
//        }
//      }
//      .execute(new Object[] { paramCallback });
//      return;
//      if (paramInt == 0)
//        this.takenWithFrontCamera = false;
//      else
//        AppUtils.log("WTF type of camera is this?!?");
//    }
//  }
//
//  public Bitmap getAnchor()
//  {
//    return this.anchor;
//  }
//
	public ArrayList<Bitmap> getBitmapList()
	{
		return this.bitmapList;
	}

//  public FilterType getCurrentFilterType()
//  {
//    return this.currentFilterType;
//  }
//
//  public float getFps()
//  {
//    return this.fps;
//  }
//
//  public LoopType getLoopType()
//  {
//    return this.currentLoopType;
//  }
//
//  public boolean hasMaskStarted()
//  {
//    return maskStarted;
//  }
//
//  public void pushAlphaMaskToStack()
//  {
//    this.alphaMaskStack.push(getAlphaMaskFromCurrentAnchor());
//  }
//
//  public void resetToOriginalUnstabilizedFilteredVideo(Callback paramCallback)
//  {
//    this.shouldShowMask = false;
//    maskStarted = false;
//    this.isStabilized = false;
//    this.anchor = ((Bitmap)this.bitmapList.get(0)).copy(Bitmap.Config.ARGB_8888, true);
//    this.anchor.setHasAlpha(true);
//    this.alphaMask = null;
//    this.alphaMaskStack = new Stack();
//    filterFramesAsync(this.currentFilterType, paramCallback);
//  }
//
//	public void saveOriginalFramesAsync(ArrayList<Bitmap> paramArrayList,
//			File paramFile, final Callback paramCallback) {
//		if (framesSaved) return;
//		
//		originalVideoFile = paramFile;
//		
//		GeneratedCinemagraphController.getInstance()
//				.createCinemagraphWithTmpMovieFilePath(this.originalVideoFile);
//		GeneratedCinemagraphController.getInstance().getCine().movieRotationDegrees = this.rotation;
//		GeneratedCinemagraphController
//				.getInstance()
//				.getCine()
//				.setDimensions(((Bitmap) this.bitmapList.get(0)).getWidth(),
//						((Bitmap) this.bitmapList.get(0)).getHeight());
//		ThreadPoolExecutor localThreadPoolExecutor = new ThreadPoolExecutor(1,
//				1, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue(1, true));
//		new AsyncTask() {
//			protected Void doInBackground(Void[] paramAnonymousArrayOfVoid) {
//				CinemagraphGenerator.this.startedSavingOriginalBitmaps = System
//						.currentTimeMillis();
//				FfmpegUtils.saveFrames(CinemagraphGenerator.getInstance()
//						.getBitmapList(),
//						CinemagraphGenerator.this.originalVideoFile);
//				return null;
//			}
//
//			protected void onPostExecute(Void paramAnonymousVoid) {
//				CinemagraphGenerator.this.framesSaved = true;
//				AppUtils.log("Time to save: "
//						+ (System.currentTimeMillis() - CinemagraphGenerator.this.startedSavingOriginalBitmaps));
//				paramCallback.onComplete(true,
//						CinemagraphGenerator.this.originalVideoFile);
//				CinemagraphGenerator.this.startCinemagraphStabilizer();
//			}
//		}.executeOnExecutor(localThreadPoolExecutor, new Void[0]);
//	}

	public void setBitmapList(ArrayList<Bitmap> paramArrayList)
	{
		bitmapList = paramArrayList;
		setAnchorFrame();
	    framesLoaded = true;
	}

	public void setFps(float paramFloat)
	{
		fps = paramFloat;
	}
//
//  public void setLoopType(LoopType paramLoopType)
//  {
//    this.currentLoopType = paramLoopType;
//  }
//
//  public void setMaskHasStarted(boolean paramBoolean)
//  {
//    maskStarted = paramBoolean;
//  }
//
	public void setRotation(int paramInt)
	{
		rotation = paramInt;
	}
//
//  public void setShouldShowMask(boolean paramBoolean)
//  {
//    this.shouldShowMask = paramBoolean;
//  }
//
//  public boolean shouldShowMask()
//  {
//    return this.shouldShowMask;
//  }
//
//  public void stabilizeFramesAsync(Callback paramCallback)
//  {
//    new AsyncTask()
//    {
//      CinemagraphGenerator.Callback callback;
//
//      protected Boolean doInBackground(Object[] paramAnonymousArrayOfObject)
//      {
//        this.callback = ((CinemagraphGenerator.Callback)paramAnonymousArrayOfObject[0]);
//        CinemagraphGenerator.this.isStabilizingFrames = true;
//        int i = 0;
//        while (true)
//        {
//          if ((CinemagraphGenerator.this.hasStabilizedCache) || (CinemagraphGenerator.this.hasStabilizationFailed))
//          {
//            CinemagraphGenerator.this.isStabilized = true;
//            CinemagraphGenerator.this.filterBitmapList(CinemagraphGenerator.this.currentFilterType, this.callback);
//            return Boolean.valueOf(true);
//          }
//          try
//          {
//            Thread.sleep(20L);
//            if (i == CinemagraphGenerator.this.cinemagraphStabilizer.getNumStabilized())
//              continue;
//            i = CinemagraphGenerator.this.cinemagraphStabilizer.getNumStabilized();
//            Integer[] arrayOfInteger = new Integer[1];
//            arrayOfInteger[0] = Integer.valueOf(i);
//            publishProgress(arrayOfInteger);
//          }
//          catch (InterruptedException localInterruptedException)
//          {
//            while (true)
//              localInterruptedException.printStackTrace();
//          }
//        }
//      }
//
//      protected void onPostExecute(Boolean paramAnonymousBoolean)
//      {
//        CinemagraphGenerator.this.onStabilized(this.callback);
//      }
//
//      protected void onProgressUpdate(Integer[] paramAnonymousArrayOfInteger)
//      {
//        this.callback.onProgress(paramAnonymousArrayOfInteger[0]);
//      }
//    }
//    .execute(new Object[] { paramCallback });
//  }
//
//  public void startCinemagraphStabilizer()
//  {
//    if (this.cinemagraphStabilizer == null)
//    {
//      this.cinemagraphStabilizer = new CinemagraphStabilizer(this.bitmapList, this.originalVideoFile, this.stabilizedVideo);
//      this.stabilizerCallback = new CinemagraphStabilizer.Callback()
//      {
//        public void onFailure()
//        {
//          CinemagraphGenerator.this.hasStabilizedCache = false;
//          CinemagraphGenerator.this.hasStabilizationFailed = true;
//        }
//
//        public void onFinish()
//        {
//          CinemagraphGenerator.this.hasStabilizedCache = true;
//        }
//
//        public void onPause()
//        {
//        }
//      };
//      this.cinemagraphStabilizer.start(this.stabilizerCallback);
//    }
//  }
//
//  public void undoDrawingMask(Callback paramCallback)
//  {
//    if (this.alphaMaskStack.size() == 0)
//    {
//      paramCallback.onComplete(false, null);
//      return;
//    }
//    this.alphaMaskStack.pop();
//    if (this.alphaMaskStack.size() == 0)
//    {
//      this.alphaMask = null;
//      resetToOriginalUnstabilizedFilteredVideo(paramCallback);
//      return;
//    }
//    this.alphaMask = ((Bitmap)this.alphaMaskStack.peek());
//    setAnchorFrame();
//    paramCallback.onComplete(true, null);
//  }
//
//  public void updateAlphaMaskAndSetAnchor()
//  {
//    this.alphaMask = getAlphaMaskFromCurrentAnchor();
//    setAnchorFrame();
//  }
//
	public static abstract interface Callback
	{
		public abstract void anchorHasBeenProcessed();

		public abstract void onComplete(boolean paramBoolean, File paramFile);

		public abstract void onProgress(Integer paramInteger);
	}
}