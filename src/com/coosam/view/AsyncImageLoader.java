package com.coosam.view;

import java.lang.ref.SoftReference;  
import java.net.URL;  
import java.util.HashMap;  
import java.util.Map;  
  
import android.graphics.drawable.Drawable;  
import android.os.Handler;  
import android.os.Message;  
  
//�������Ҫ������ʵ��ͼƬ���첽����  
public class AsyncImageLoader {  
    // ͼƬ�������  
    // ����ͼƬ��URL��ֵ��һ��SoftReference���󣬸ö���ָ��һ��Drawable����  
    private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();  
  
    // ʵ��ͼƬ���첽����  
    public Drawable loadDrawable(final String imageUrl,  
            final ImageCallback callback) {  
        // ��ѯ���棬�鿴��ǰ��Ҫ���ص�ͼƬ�Ƿ��Ѿ������ڻ��浱��  
        if (imageCache.containsKey(imageUrl)) {  
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);  
            if (softReference.get() != null) {  
                return softReference.get();  
            }  
        }  
  
        final Handler handler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                callback.imageLoaded((Drawable) msg.obj);  
            }  
        };  
        // �¿���һ���̣߳����߳����ڽ���ͼƬ������  
        new Thread() {  
            public void run() {  
                Drawable drawable = loadImageFromUrl(imageUrl);  
                // Ȼ���ͼƬ���뻺�浱��  
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));  
                Message message = handler.obtainMessage(0, drawable);  
                handler.sendMessage(message);  
            };  
        }.start();  
        return null;  
    }  
  
    // �÷������ڸ��ͼƬ��URL��������������ͼƬ  
    protected Drawable loadImageFromUrl(String imageUrl) {  
        try {  
            // ���ͼƬ��URL������ͼƬ�������һ��Drawable����  
            return Drawable.createFromStream(new URL(imageUrl).openStream(),  
                    "src");  
  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    // �ص��ӿ�  
    public interface ImageCallback {  
        public void imageLoaded(Drawable imageDrawable);  
    }  
}  