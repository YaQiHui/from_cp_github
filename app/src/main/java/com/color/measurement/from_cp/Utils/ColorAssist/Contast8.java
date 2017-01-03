package com.color.measurement.from_cp.Utils.ColorAssist;

/**
 * Created by cimcenter on 2016/12/6.
 */

public class Contast8 {
    //视觉密度（K）
    public static double Density_V_Coefficient[] =
    {
        0,	1.322,	1.914,	2.447,	2.811,	3.09,	3.346,	3.582,	3.818,	4.041,	4.276,	4.513,	4.702,	4.825,	4.905,	4.957,	4.989,	5.0,	4.989,	4.956,	4.902,	4.827,	4.731,	4.593,	4.433,	4.238,	4.013,	3.749,	3.49,	3.188,	2.901,
    };
    /////////////////////////A密度(CMY = RGB)//////////////////////////////////////
    public static double DensityA_B_Coefficient[] =
    {
        0,	0,	3.602,	4.819,	5,	4.912,	4.62,	4.04,	2.989,	1.566,	0.165,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityA_G_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1.65,	3.822,	4.782,	5,	4.906,	4.644,	4.221,	3.609,	2.766,	1.579,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };

    public static double DensityA_R_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2.568,	4.638,	5,	4.871,	4.604,	4.286,	3.9,	3.551,	3.165,	2.776,	2.383,	1.97,	1.551,	1.141,	0.741,	0.341,	0,
    };
/////////////////////////T密度(CMY = RGB)//////////////////////////////////////
    public static double DensityT_B_Coefficient[] =
    {
        3.778,	4.23,	4.602,	4.778,	4.914,	4.973,	5,	4.987,	4.929,	4.813,	4.602,	4.255,	3.699,	2.301,	1.602,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityT_G_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	3,	3.699,	4.447,	4.833,	4.964,	5,	4.944,	4.82,	4.623,	4.342,	3.954,	3.398,	2.845,	1.954,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityT_R_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1.778,	2.653,	4.447,	5.0,	4.929,	4.74,	4.398,	4.0,	3.699,	3.176,	2.699,	2.477,	2.176,	1.699,	1,	0,	0,	0,	0,	0,
    };
/////////////////////////M密度(CMY = RGB)//////////////////////////////////////
    public static double DensityM_B_Coefficient[] =
    {
        0,	2.103,	4.111,	4.632,	4.871,	5.0,	4.955,	4.743,	4.343,	3.743,	2.99,	1.852,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityM_G_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	1.152,	2.207,	3.156,	3.804,	4.272,	4.626,	4.872,	5.0,	4.995,	4.818,	4.458,	3.915,	3.172,	2.239,	1.07,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityM_R_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2.109,	4.479,	5.0,	4.899,	4.578,	4.252,	3.875,	3.491,	3.099,
    };
/////////////////////////E密度(CMY = RGB)//////////////////////////////////////
    public static double DensityE_B_Coefficient[] =
    {
        4.114,	4.477,	4.778,	4.914,	5.0,	4.959,	4.881,	4.672,	4.255,	3.778,	2.903,	1.699,	1.0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityE_G_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	3.0,	3.699,	4.477,	4.833,	4.964,	5.0,	4.944,	4.82,	4.623,	4.342,	3.954,	3.398,	2.845,	1.954,	1,	0,	0,	0,	0,	0,	0,	0,	0,
    };
    public static double DensityE_R_Coefficient[] =
    {
        0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1.778,	2.653,	4.477,	5.0,	4.929,	4.74,	4.398,	4.0,	3.699,	3.176,	2.699,	2.477,	2.176,	1.699,
    };
}
