# StrokedTextView

## 使用

### 代码方式

```kotlin
val v = findViewById<StrokedTextView>(R.id.stroked_text_view)
v.text = "ab克己复礼可升级老骥伏枥文件垃圾分类看cdef我人有的和"
v.textSize = 100f
v.solidTextColor = Color.RED
v.strokeTextColor = Color.GREEN
v.strokeWidth = 5f
v.gravity = Gravity.TOP or Gravity.END
```

### xml方式

```xml
<me.daemon.strokedtextview.StrokedTextView
    android:id="@+id/stroked_text_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:daemon_gravity="end|bottom"
    app:daemon_solid_text_color="#ff0000"
    app:daemon_stroke_text_color="@color/teal_200"
    app:daemon_stroke_width="2dp"
    app:daemon_text="福建阿胶离开就离开离开faflew家flaw了几分里发霉了么饭啦没离开v老妈老妈额外"
    app:daemon_text_size="20sp" />
```

#### 属性列表

```xml
<declare-styleable name="StrokedTextView">
    <!-- 文本 -->
    <attr name="daemon_text" format="string|reference" />
    <!-- 字体大小 -->
    <attr name="daemon_text_size" format="dimension" />
    <!-- 填充色 -->
    <attr name="daemon_solid_text_color" format="color" />
    <!-- 描边色 -->
    <attr name="daemon_stroke_text_color" format="color" />
    <!-- 描边宽度 -->
    <attr name="daemon_stroke_width" format="dimension" />
    
    <!-- 文本重心 -->
    <attr name="daemon_gravity">
        <flag name="top" value="0x30" />
        <flag name="bottom" value="0x50" />
        <flag name="left" value="0x03" />
        <flag name="right" value="0x05" />
        <flag name="center_vertical" value="0x10" />
        <flag name="center_horizontal" value="0x01" />
        <flag name="center" value="0x11" />
        <flag name="start" value="0x00800003" />
        <flag name="end" value="0x00800005" />
    </attr>
</declare-styleable>
```
