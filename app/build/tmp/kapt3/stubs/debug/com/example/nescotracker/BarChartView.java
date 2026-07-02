package com.example.nescotracker;

/**
 * মাসিক খরচের রিপোর্ট দেখানোর জন্য হালকা একটি বার-চার্ট।
 * কোনো external লাইব্রেরি ছাড়াই আঁকা হয়।
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u001e\u0010\u0014\u001a\u00020\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u0016\u001a\u00020\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/nescotracker/BarChartView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "barPaint", "Landroid/graphics/Paint;", "data", "", "Lcom/example/nescotracker/data/MonthlyUsage;", "isDark", "", "labelPaint", "valuePaint", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "setData", "newData", "darkMode", "app_debug"})
public final class BarChartView extends android.view.View {
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.nescotracker.data.MonthlyUsage> data;
    private boolean isDark = false;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint barPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint labelPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint valuePaint = null;
    
    @kotlin.jvm.JvmOverloads
    public BarChartView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.nescotracker.data.MonthlyUsage> newData, boolean darkMode) {
    }
    
    @java.lang.Override
    protected void onDraw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas) {
    }
    
    @kotlin.jvm.JvmOverloads
    public BarChartView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
}