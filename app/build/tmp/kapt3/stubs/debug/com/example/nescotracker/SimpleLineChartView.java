package com.example.nescotracker;

/**
 * খুব হালকা একটি লাইন-চার্ট ভিউ, ব্যালেন্স হিস্ট্রি দেখানোর জন্য।
 * কোনো external লাইব্রেরি লাগে না, তাই build.gradle/repositories নিয়ে
 * ঝামেলায় পড়তে হবে না।
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0014\u0010\u0016\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/nescotracker/SimpleLineChartView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "dotPaint", "Landroid/graphics/Paint;", "fillPaint", "gridPaint", "labelPaint", "linePaint", "records", "", "Lcom/example/nescotracker/data/BalanceRecord;", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "setData", "data", "app_debug"})
public final class SimpleLineChartView extends android.view.View {
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.nescotracker.data.BalanceRecord> records;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint linePaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint fillPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint dotPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint gridPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint labelPaint = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormat = null;
    
    @kotlin.jvm.JvmOverloads
    public SimpleLineChartView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.nescotracker.data.BalanceRecord> data) {
    }
    
    @java.lang.Override
    protected void onDraw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas) {
    }
    
    @kotlin.jvm.JvmOverloads
    public SimpleLineChartView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
}