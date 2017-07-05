FlexibleSearchBar
=================

Introduction
------------
可以伸缩的搜索栏，模仿 [华为应用市场](http://app.hicloud.com/) 。

Screenshot
----------
![screenshot](/screenshot/20170701150542.gif)

Blog
----------
[《可以伸缩的搜索栏，模仿华为应用市场》](http://yuqirong.me/2017/07/03/%E5%8F%AF%E4%BB%A5%E4%BC%B8%E7%BC%A9%E7%9A%84%E6%90%9C%E7%B4%A2%E6%A0%8F%EF%BC%8C%E6%A8%A1%E4%BB%BF%E5%8D%8E%E4%B8%BA%E5%BA%94%E7%94%A8%E5%B8%82%E5%9C%BA/)

Usage
-----
1. 在布局中加入 `SearchBarView`：

	``` xml
	<com.yuqirong.searchbar.SearchBarView
	    android:id="@+id/searchbarview"
	    android:layout_width="match_parent"
	    android:layout_height="40dp"
	    android:layout_margin="8dp"
	    app:search_bar_hint_text="@string/string_search_text" />
	```

2. 在代码中使用它，`startOpen()` 为打开搜索栏；反之，`startClose()` 为关闭搜索栏：

	``` java
	SearchBarView searchbarview = (SearchBarView) findViewById(R.id.searchbarview);
	searchbarview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // enter search activity
        }
    });

	searchbarview.startOpen(); // 打开搜索栏
	...
	searchbarview.startClose(); // 关闭搜索栏
	```

Custom Attributes
-----------------
| 属性名          | 格式        | 描述 |
| ------------- |:-------------:| -----------:|
| search_bar_color | color\|reference | 搜索栏背景色 |
| search_bar_position | enum | 搜索栏的位置（左或右） |
| search_bar_status | enum | 搜索栏的状态（打开或关闭） |
| search_bar_duration | integer | 搜索栏打开或关闭的动画时间 |
| search_bar_hint_text | string\|reference | 搜索栏的提示文本 |
| search_bar_hint_text_color | color\|reference | 提示文本的颜色 |
| search_bar_hint_text_size | dimension\|reference | 提示文本的字体大小 |
| search_bar_icon | reference | 搜索栏的图标 |

Contact Me
----------
* Email : <yqr271228943@gmail.com>
* Weibo : [@活得好像一条狗](http://weibo.com/yyyuqirong)

License
-------
	Copyright (c) 2017 yuqirong 
	
	Licensed under the Apache License, Version 2.0 (the "License”);
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.