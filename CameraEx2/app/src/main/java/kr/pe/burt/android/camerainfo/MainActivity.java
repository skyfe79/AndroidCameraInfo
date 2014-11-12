package kr.pe.burt.android.camerainfo;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity {

    TextView mCameraInfoTextView = null;
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraInfoTextView = (TextView)findViewById(R.id.mCameraInfoTextView);
        mCameraInfoTextView.setText(getCameraInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    private String getCameraInfo() {


        //: 카메라를 열지 않고 알 수 있는 정보 모음
        line("- 카메라를 열지 않고 알 수 있는 정보 모음 -");
        int numberOfCameras = Camera.getNumberOfCameras();
        line("카메라 갯수 : " + numberOfCameras); br();

        if(numberOfCameras == 0) {
            return sb.toString();
        }


        for(int i=0; i<numberOfCameras; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            line("= [ " + i + " 번 카메라 ] =========================");
            //sb.append("셔터음 무음 가능 : " + cameraInfo.canDisableShutterSound + "\n");
            line("카메라가 바라보는 방향 : " + (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK ? "CAMERA_FACING_BACK" : "CAMERA_FACING_FRONT"));
            line("카메라 orientation : " + cameraInfo.orientation); br();
        }

        //: 카메라를 열어야 알 수 있는 정보
        br();
        line("- 카메라를 열어야 알 수 있는 정보 모음 -"); br();
        for(int i=0; i<numberOfCameras; i++) {
            sb.append("= [ " + i + " 번 카메라 ] ========================="); br();
            Camera camera = Camera.open(i);
            Camera.Parameters parameters = camera.getParameters();
            line("Antibanding : " + parameters.getAntibanding());
            line("AutoExposureLock : " + parameters.getAutoExposureLock());
            line("AutoWhiteBalanceLock : " + parameters.getAutoWhiteBalanceLock());
            line("ColorEffect : " + parameters.getColorEffect());
            line("ExposureCompensation : " + parameters.getExposureCompensation());
            line("ExposureCompensationStep : " + parameters.getExposureCompensationStep());
            line("FlashMode : " + parameters.getFlashMode());
            line("FocalLength : " + parameters.getFocalLength());

            int maxNumFocusArea = parameters.getMaxNumFocusAreas();
            line("MaxNumFocusAreas : " + parameters.getMaxNumFocusAreas());

//            Caused by: java.lang.NumberFormatException: Invalid int: " 0"
//            if(maxNumFocusArea > 0) {
//                for (Camera.Area area : parameters.getFocusAreas()) {
//                    line(cameraAreaToString(area));
//                }
//            }

            float[] focusDistance = new float[3];
            parameters.getFocusDistances(focusDistance);
            line("FocusDistances[FOCUS_DISTANCE_NEAR_INDEX] : " + focusDistance[Camera.Parameters.FOCUS_DISTANCE_NEAR_INDEX]);
            line("FocusDistances[FOCUS_DISTANCE_FAR_INDEX] : " + focusDistance[Camera.Parameters.FOCUS_DISTANCE_FAR_INDEX]);
            line("FocusDistances[FOCUS_DISTANCE_OPTIMAL_INDEX] : " + focusDistance[Camera.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX]);
            line("FocusMode : " + parameters.getFocusMode());
            line("HorizontalViewAngle : " + parameters.getHorizontalViewAngle());
            line("JpegQuality : " + parameters.getJpegQuality());
            line("JpegThumbnailQuality : " + parameters.getJpegThumbnailQuality());
            line("JpegThumbnailSize : " + cameraSizeToString(parameters.getJpegThumbnailSize()));
            line("MaxExposureCompensation : " + parameters.getMaxExposureCompensation());
            line("MaxNumDetectedFaces : " + parameters.getMaxNumDetectedFaces());
            line("MaxNumMeteringAreas : " + parameters.getMaxNumMeteringAreas());
            line("MaxZoom : " + parameters.getMaxZoom());

//            Caused by: java.lang.NumberFormatException: Invalid int: " 0"
//            if(parameters.getMaxNumMeteringAreas() > 0) {
//                for(Camera.Area area : parameters.getMeteringAreas()) {
//                    line("MeteringArea : " + cameraAreaToString(area));
//                }
//            }

            line("MinExposureCompensation : " + parameters.getMinExposureCompensation());
            line("PictureFormat : " + pictureFormatToString(parameters.getPictureFormat()));
            line("PictureSize : " + cameraSizeToString(parameters.getPictureSize()));
            line("PreferredPreviewSizeForVideo : " + cameraSizeToString(parameters.getPreferredPreviewSizeForVideo()));
            line("PreviewFormat : " + pictureFormatToString(parameters.getPreviewFormat()));

            int[] previewFpsRange = new int[2];
            parameters.getPreviewFpsRange(previewFpsRange);
            line("PreviewFpsRange[PREVIEW_FPS_MIN_INDEX] : " + previewFpsRange[Camera.Parameters.PREVIEW_FPS_MIN_INDEX]);
            line("PreviewFpsRange[PREVIEW_FPS_MAX_INDEX] : " + previewFpsRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
            line("[DEPRECATED] PreviewFrameRate : " + parameters.getPreviewFrameRate());
            line("PreviewSize : " +  cameraSizeToString(parameters.getPreviewSize()));
            line("SceneMode : " + parameters.getSceneMode());
            List<String> supportedAntibanding = parameters.getSupportedAntibanding();
            if(supportedAntibanding != null) {
                for(String antibanding : supportedAntibanding) {
                    line("Supported Antibanding : " + antibanding);
                }
            }

            List<String> supportedColorEffects = parameters.getSupportedColorEffects();
            if(supportedColorEffects != null) {
                for(String colorEffect : supportedColorEffects) {
                    line("Supported ColorEffect : " + colorEffect);
                }
            }

            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if(supportedFlashModes != null) {
                for(String flashMode : supportedFlashModes) {
                    line("Supported FlashMode : " + flashMode);
                }
            }

            List<Camera.Size> supportedJpegThumbnailSizes = parameters.getSupportedJpegThumbnailSizes();
            if(supportedJpegThumbnailSizes != null) {
                for(Camera.Size size : supportedJpegThumbnailSizes) {
                    line("Supported JpegThumbnailSize : " + cameraSizeToString(size));
                }
            }

            List<Integer> supportedPictureFormat = parameters.getSupportedPictureFormats();
            if(supportedPictureFormat != null) {
                for(int pictureFormat : supportedPictureFormat) {
                    line("Supported PictureFormat : " + pictureFormatToString(pictureFormat));
                }
            }

            List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
            if(supportedPictureSizes != null) {
                for(Camera.Size pictureSize : supportedPictureSizes) {
                    line("Supported Picture Size : " + cameraSizeToString(pictureSize));
                }
            }

            List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
            if(supportedPreviewFormats != null) {
                for(int previewFormat : supportedPreviewFormats) {
                    line("Supported Preview Format : " + pictureFormatToString(previewFormat));
                }
            }

            List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
            if(supportedPreviewFpsRange != null) {
                for(int[] fpsRange : supportedPreviewFpsRange) {
                    line("Supported Preview FPS RANGE[MIN, MAX] : [" + fpsRange[Camera.Parameters.PREVIEW_FPS_MIN_INDEX] + ", " + fpsRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX] + "]");
                }
            }

            List<Integer> supportedPreviewFrameRate = parameters.getSupportedPreviewFrameRates();
            if(supportedPreviewFrameRate != null) {
                for(int frameRate : supportedPreviewFrameRate) {
                    line("Supported Preview Frame Rate : " + frameRate);
                }
            }

            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            if(supportedPreviewSizes != null) {
                for(Camera.Size size : supportedPreviewSizes) {
                    line("Supported Preview Size : " + cameraSizeToString(size));
                }
            }

            List<String> supportedSceneModes = parameters.getSupportedSceneModes();
            if(supportedSceneModes != null) {
                for(String mode : supportedSceneModes) {
                    line("Supported Scene mode : " + mode);
                }
            }

            List<Camera.Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
            if(supportedVideoSizes != null) {
                for(Camera.Size size : supportedVideoSizes) {
                    line("Supported Video Size : " + cameraSizeToString(size));
                }
            }

            List<String> supportedWhiteBalance = parameters.getSupportedWhiteBalance();
            if(supportedWhiteBalance != supportedWhiteBalance) {
                for(String whiteBalance : supportedWhiteBalance) {
                    line("Supported WhiteBalance : " + whiteBalance);
                }
            }

            line("VerticalViewAngle : " + parameters.getVerticalViewAngle());
            line("VideoStabilization : " + parameters.getVideoStabilization());
            line("WhiteBalance : " + parameters.getWhiteBalance());
            line("Zoom" + parameters.getZoom());

            List<Integer> zoomRatios = parameters.getZoomRatios();
            if(zoomRatios != null) {
                for(int zoomRatio : zoomRatios) {
                    line("ZoomRatios : " + zoomRatio);
                }
            }

            line("AutoExposureLockSupported : " + parameters.isAutoExposureLockSupported());
            line("AutoWhiteBalanceLockSupported : " + parameters.isAutoWhiteBalanceLockSupported());
            line("SmoothZoomSupported : " + parameters.isSmoothZoomSupported());
            line("VideoSnapshotSupported : " + parameters.isVideoSnapshotSupported());
            line("VideoStabilizationSupported : " + parameters.isVideoStabilizationSupported());
            line("ZoomSupported : " + parameters.isZoomSupported());

            line("Parameter's flatten String : " + parameters.flatten());

            // 다른 카메라에 연결하려면 무조건 기존 카메라 연결은 닫아야 한다
            camera.release();

            br();
        }



        return sb.toString();
    }

    @SuppressWarnings("deprecation")
    private String cameraAreaToString(Camera.Area area) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("area.rect : " + area.rect.flattenToString() + "\n");
        stringBuffer.append("area.weight : " + area.weight + "\n");
        return stringBuffer.toString();
    }

    private String cameraSizeToString(Camera.Size size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" { " + size.width + "," + size.height + " } ");
        return stringBuilder.toString();
    }

    private String pictureFormatToString(int pictureFormat) {
        switch (pictureFormat) {
            case ImageFormat.JPEG:
                return "JPEG";
            case ImageFormat.NV16:
                return "NV16";
            case ImageFormat.NV21:
                return "NV21";
            case ImageFormat.RAW10:
                return "RAW10";
            case ImageFormat.RAW_SENSOR:
                return "RAW_SENSOR";
            case ImageFormat.RGB_565:
                return "RGB_565";
            case ImageFormat.UNKNOWN:
                return "UNKNOWN";
            case ImageFormat.YUV_420_888:
                return "YUV_420_888";
            case ImageFormat.YUY2:
                return "YUY2";
            case ImageFormat.YV12:
                return "YV12";
            default:
                return "There is no picture format";
        }
    }
    private void line(String line) {
        sb.append(line); br();
    }

    private void br() {
        sb.append("\n");
    }
}
