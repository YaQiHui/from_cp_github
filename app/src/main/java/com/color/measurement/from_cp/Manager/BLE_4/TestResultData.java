package com.color.measurement.from_cp.Manager.BLE_4;

import com.color.measurement.from_cp.Utils.Math.clsPublic;

import static com.color.measurement.from_cp.Manager.BLE_4.TestResultData.ResultType.base;
import static com.color.measurement.from_cp.Manager.BLE_4.TestResultData.ResultType.black;
import static com.color.measurement.from_cp.Manager.BLE_4.TestResultData.ResultType.offTime;
import static com.color.measurement.from_cp.Manager.BLE_4.TestResultData.ResultType.white;

/**
 * Created by wpc on 2016/12/16.
 */

public class TestResultData {

    enum ResultType {
        base, black, white, power, MSKey, offTime
    }

    ResultType mResultType;

    byte[] native_data;
    float[] SCI;
    float[] SCE;

    TestResultData(byte[] ble_data) {
        native_data=ble_data;
        if (ble_data.length == 327) {
            mResultType = base;
            byte[] sci_bytes = new byte[144];
            byte[] sce_bytes = new byte[144];
            System.arraycopy(ble_data, 33, sci_bytes, 0, 144);
            System.arraycopy(ble_data, 177, sce_bytes, 0, 144);
            SCI = clsPublic.getFloat(sci_bytes);
            SCE = clsPublic.getFloat(sce_bytes);
        } else if (ble_data.length == 5) {
            if (ble_data[1] == 2) {
                mResultType = white;
            }else if(ble_data[1]==3){
                mResultType = black;
            }else if(ble_data[1]==6){
                mResultType=offTime;
            }
        }
    }

    public float[] getSCI() {
        return SCI;
    }

    public void setSCI(float[] SCI) {
        this.SCI = SCI;
    }

    public float[] getSCE() {
        return SCE;
    }

    public void setSCE(float[] SCE) {
        this.SCE = SCE;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SCI:");
        for (int i = 0; i < SCI.length; i++) {
            sb.append(String.valueOf(SCI[i]) + "  ");
        }
        sb.append("\n");
        sb.append("SCE:");
        for (int i = 0; i < SCE.length; i++) {
            sb.append(String.valueOf(SCE[i]) + "  ");
        }
        return sb.toString();
    }
}
