# TouchButton

一个类似抖音 APP 拍摄按钮效果的控件

### 效果图预览

![效果图](https://wx4.sinaimg.cn/large/5f90ffefgy1g7u1lwmyzjg20f40qoqv6.gif)

### 用法

```xml
<net.angrycode.library.TouchButton
        android:id="@+id/touch_btn"
        android:layout_width="85dp"
        android:layout_height="85dp"
        android:layout_centerInParent="true"
        app:tb_anim_padding="14dp"
        app:tb_color="@color/colorAccent"
        app:tb_text="Hold" />
```

### 自定义属性

```xml
<declare-styleable name="TouchButton">
        <attr name="tb_text" format="string|reference" />
        <attr name="tb_text_color" format="color|reference" />
        <attr name="tb_color" format="color|reference" />
        <attr name="tb_anim_padding" format="dimension|reference" />
    </declare-styleable>
```

### 属性说明

|       属性        |   描述   |    默认值    |
| :-------------: | :----: | :-------: |
| tb_anim_padding |  动画边距  |   10dp    |
|    tb_color     |  按钮背景  |  #FF4081  |
|     tb_text     |  按钮文本  |   Hold    |
|  tb_text_color  | 按钮文本颜色 | #ffffffff |

### 微信公众号

终身开发者（AngryCode）









