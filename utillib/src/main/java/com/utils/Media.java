package com.utils;

import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceView;

import java.io.File;

/**
 * com.video.util
 * 2019/1/17 17:55
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Media {
    private static final String TAG = "Media";
    private MediaRecorder mediaRecorder;
    private boolean isRecording;
    private MediaListener mListener;

    public void startVoice(String parentPath) {
        if (isRecording)
            return;
        Log.e(TAG, "开始录音");
        isRecording = true;
        try {
            File file = new File(parentPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(file, System.currentTimeMillis() + ".amr");
            mediaRecorder = new MediaRecorder();
            // 设置音频录入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置录制音频的输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // 设置音频的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置录制音频文件输出文件路径
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    Log.e(TAG, "录音失败");
                    if (mListener != null)
                        mListener.failure();
                }
            });
            // 准备、开始
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            Log.e(TAG, "录音失败");
            stop();
        }
    }


    /**
     * 2019/1/17 18:37
     * annotation：录制视频
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void startVideo(String parentPath, SurfaceView surfaceView) {
        if (isRecording)
            return;
        Log.e(TAG, "开始录制视频");
        isRecording = true;
        try {
            File file = new File(parentPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(file, System.currentTimeMillis() + ".mp4");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.reset();
            // 设置音频录入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置视频图像的录入源
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // 设置录入媒体的输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 设置音频的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            // 设置视频的编码格式
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            // 设置视频的采样率，每秒4帧
            mediaRecorder.setVideoFrameRate(4);
            // 设置录制视频文件的输出路径
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            // 设置捕获视频图像的预览界面
            if (surfaceView != null)
                mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    // 发生错误，停止录制
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    Log.e(TAG, "录制视频失败");
                    if (mListener != null)
                        mListener.failure();
                }
            });
            // 准备、开始
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 2019/1/17 18:30
     * annotation：停止录制
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void stop() {
        Log.e(TAG, "停止录制");
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        }
    }

    /**
     * 2019/1/17 18:14
     * annotation：录制回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public static interface MediaListener {


        /**
         * 2019/1/17 18:15
         * annotation：录制失败
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        void failure();
    }
}
