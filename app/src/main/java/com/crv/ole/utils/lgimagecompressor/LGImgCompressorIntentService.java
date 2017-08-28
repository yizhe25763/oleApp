package com.crv.ole.utils.lgimagecompressor;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


/**
 * 图片压缩服务
 */
public class LGImgCompressorIntentService extends IntentService {
    private final String TAG = LGImgCompressorIntentService.class.getSimpleName();

    private static final String ACTION_COMPRESS = "gui.com.lgimagecompressor.action.COMPRESS";

    private ArrayList<LGImgCompressor.CompressResult> compressResults = new ArrayList<>();//存储压缩任务的返回结果

    public LGImgCompressorIntentService() {
        super("LGImgCompressorIntentService");
        setIntentRedelivery(false);//避免出异常后service重新启动
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(Constanse.ACTION_COMPRESS_BROADCAST);
        intent.putExtra(Constanse.KEY_COMPRESS_FLAG, Constanse.FLAG_BEGAIIN);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(Constanse.ACTION_COMPRESS_BROADCAST);
        intent.putExtra(Constanse.KEY_COMPRESS_FLAG, Constanse.FLAG_END);
        intent.putParcelableArrayListExtra(Constanse.KEY_COMPRESS_RESULT, compressResults);
        sendBroadcast(intent);
        compressResults.clear();
    }

    public static void startActionCompress(Context context, CompressServiceParam param) {
        Intent intent = new Intent(context, LGImgCompressorIntentService.class);
        intent.setAction(ACTION_COMPRESS);
        intent.putExtra(Constanse.COMPRESS_PARAM, param);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_COMPRESS.equals(action)) {
                final CompressServiceParam param1 = intent.getParcelableExtra(Constanse.COMPRESS_PARAM);
                handleActionCompress(param1);
            }
        }
    }

    private void handleActionCompress(CompressServiceParam param) {
        int outwidth = param.getOutWidth();
        int outHieight = param.getOutHeight();
        int maxFileSize = param.getMaxFileSize();
        String srcImageUri = param.getSrcImageUri();
        int tag = param.getTag();
        LGImgCompressor.CompressResult compressResult = new LGImgCompressor.CompressResult();
        String outPutPath = null;
        try {
            outPutPath = LGImgCompressor.getInstance(this).compressImage(srcImageUri, outwidth, outHieight, maxFileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        compressResult.setSrcPath(srcImageUri);
        compressResult.setOutPath(outPutPath);
        compressResult.setTag(tag);
        if (outPutPath == null) {
            compressResult.setStatus(LGImgCompressor.CompressResult.RESULT_ERROR);
        }
        compressResults.add(compressResult);
    }
}
