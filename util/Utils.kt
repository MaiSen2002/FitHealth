package com.bloodpressure.bloodtracker.bptracker.util

import android.content.Context
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugarInfo
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRateStatus
import com.bloodpressure.bloodtracker.bptracker.domain.InfoData
import com.bloodpressure.bloodtracker.bptracker.domain.SettingDataModel
import com.bloodpressure.bloodtracker.bptracker.ui.language.LanguageModel
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.FeaturesModel

class Utils {
    companion object {

        const val KEY_HEART_RATE = "heart_rate"
        const val KEY_BLOOD_PRESSURE = "blood_pressure"
        const val KEY_BLOOD_SUGAR = "blood_sugar"
        const val KEY_WEIGHT_BMI = "weight_bmi"
        const val KEY_TYPE = "type"
        const val KEY_FROM_SPLASH = "from_splash"
        const val KEY_TRACKER = "tracker"
        const val KEY_DATA = "data"
        const val KEY_EDIT = "edit"
        const val KEY_FROM_HISTORY = "from_history"
        const val KEY_START_INFO = "info"
        const val IS_CALCULATED = "is_calculated"
        const val EDIT_BLOOD_PRESSURE = "edit_blood_pressure"
        const val EDIT_BLOOD_SUGAR = "edit_blood_sugar"
        const val EDIT_HEART_RATE = "edit_heart_rate"


        fun getListItemLanguage(context: Context): ArrayList<LanguageModel> {
            val item = arrayListOf<LanguageModel>()
            item.add(
                LanguageModel(
                    context.getString(R.string.vietnam),
                    code = "vn",
                    R.drawable.ic_flag_vi,
                    false
                )
            )
            item.add(
                LanguageModel(
                    context.getString(R.string.french),
                    code = "fr",
                    R.drawable.c2,
                    false
                )
            )

            item.add(
                LanguageModel(
                    context.getString(R.string.portuguese),
                    code = "pt",
                    R.drawable.c3,
                    false
                )
            )

            item.add(
                LanguageModel(
                    context.getString(R.string.spanish),
                    code = "es",
                    R.drawable.c4,
                    false
                )
            )

            item.add(                        //"en", "hi", "es", "fr", "de", "su", "pt"
                LanguageModel(
                    context.getString(R.string.india),
                    code = "hi",
                    R.drawable.c5,
                    false
                )
            )
            return item
        }

        fun getSrc(icon: Int): Int {
            return when (icon) {
                0 -> R.drawable.ic_normal_bp
                1 -> R.drawable.ic_elevated_bp
                2 -> R.drawable.ic_high_bp1
                3 -> R.drawable.ic_high_bp2
                else -> R.drawable.ic_dangerously_bp
            }
        }

        fun getListFeatures(context: Context): ArrayList<FeaturesModel> {
            val item = arrayListOf<FeaturesModel>()
            item.add(
                FeaturesModel(
                    context.getString(R.string.blood_pressure),
                    R.drawable.ic_blood_pressure_
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.blood_sugar),
                    R.drawable.ic_blood_sugar_
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.heart_rate),
                    R.drawable.ic_heart_rate
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.weight_bmi),
                    R.drawable.ic_weight_bmi_
                )
            )
            return item
        }

        fun getListFeaturesHasAds(context: Context): ArrayList<FeaturesModel> {
            val item = arrayListOf<FeaturesModel>()
            item.add(
                FeaturesModel(
                    context.getString(R.string.blood_pressure),
                    R.drawable.ic_blood_pressure_
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.blood_sugar),
                    R.drawable.ic_blood_sugar_
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.blood_sugar),
                    R.drawable.ic_blood_sugar_,
                    0
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.heart_rate),
                    R.drawable.ic_heart_rate
                )
            )
            item.add(
                FeaturesModel(
                    context.getString(R.string.weight_bmi),
                    R.drawable.ic_weight_bmi_
                )
            )
            return item
        }

        fun getBloodPressureInfo(context: Context): List<BPInfoDataModel> {
            val data = arrayListOf<BPInfoDataModel>()
            data.add(
                BPInfoDataModel(
                    "< 120",
                    "< 60",
                    "#00C57E",
                    context.getString(R.string.normal_blood_pressure),
                    context.getString(R.string.and),
                    0,
                    0
                )
            )

            data.add(
                BPInfoDataModel(
                    "120 - 129",
                    "60 - 79",
                    "#E9D841",
                    context.getString(R.string.elevated_blood_pressure),
                    context.getString(R.string.and),
                    1, 1
                )
            )

            data.add(
                BPInfoDataModel(
                    "130 - 139",
                    "80 - 89",
                    "#FEC415",
                    context.getString(R.string.high_blood_pressure_1),
                    context.getString(R.string.or),
                    2,
                    2
                )
            )

            data.add(
                BPInfoDataModel(
                    "140 - 180",
                    "90 - 120",
                    "#FA9C0F",
                    context.getString(R.string.high_blood_pressure_2),
                    context.getString(R.string.or),
                    3,
                    3
                )
            )

            data.add(
                BPInfoDataModel(
                    "> 180",
                    "> 120",
                    "#FB5555",
                    context.getString(R.string.dangerously_high_blood_pressure),
                    context.getString(R.string.and_or),
                    4, 4
                )
            )
            return data
        }

        fun getBloodSugarInfo(context: Context): List<BloodSugarInfo> {
            val data = arrayListOf<BloodSugarInfo>()
            data.add(BloodSugarInfo(context.getString(R.string.low), "#41ACE9", "< 4.0"))
            data.add(BloodSugarInfo(context.getString(R.string.normal), "#00C57E", "4.0 - 5.5"))
            data.add(
                BloodSugarInfo(
                    context.getString(R.string.pre_diabetes),
                    "#E9D841",
                    "5.5 - 7.0"
                )
            )
            data.add(BloodSugarInfo(context.getString(R.string.diabetes), "#FB5555", ">= 7.0"))
            return data
        }

        fun getMeasured(context: Context): List<Pair<String, Int>> {
            val data = arrayListOf<Pair<String, Int>>()
            data.add(Pair(context.getString(R.string.breakfast), 0))
            data.add(Pair(context.getString(R.string.before_breakfast), 1))
            data.add(Pair(context.getString(R.string.after_breakfast), 1))

            data.add(Pair(context.getString(R.string.lunch), 0))
            data.add(Pair(context.getString(R.string.before_lunch), 1))
            data.add(Pair(context.getString(R.string.after_lunch), 1))

            data.add(Pair(context.getString(R.string.dinner), 0))
            data.add(Pair(context.getString(R.string.before_dinner), 1))
            data.add(Pair(context.getString(R.string.after_dinner), 1))
            return data
        }

        fun getNameMeasured(context: Context, position: Int): String {
            return when (position) {
                1 -> context.getString(R.string.before_breakfast)
                2 -> context.getString(R.string.after_breakfast)
                4 -> context.getString(R.string.before_lunch)
                5 -> context.getString(R.string.after_lunch)
                7 -> context.getString(R.string.before_dinner)
                8 -> context.getString(R.string.after_dinner)
                else -> ""
            }
        }

        fun getHeartRateInfo(context: Context): List<HeartRate> {
            val item = arrayListOf<HeartRate>()
            item.add(
                HeartRate(
                    0,
                    0,
                    HeartRateStatus.LOW.ordinal,
                    "< 60",
                    "#41ACE9",
                    context.getString(R.string.resting_heart_rate_low),
                    R.drawable.ic_low_heart_rate
                )
            )
            item.add(
                HeartRate(
                    0,
                    0,
                    HeartRateStatus.NORMAL.ordinal,
                    "60 - 100",
                    "#00C57E",
                    context.getString(R.string.resting_heart_rate_normal),
                    R.drawable.ic_normal_heart_rate
                )
            )
            item.add(
                HeartRate(
                    0,
                    0,
                    HeartRateStatus.DIABETES.ordinal,
                    "> 100",
                    "#FB5555",
                    context.getString(R.string.resting_heart_rate_diabetes),
                    R.drawable.ic_diabetes_heart_rate
                )
            )
            return item
        }

        fun getStatusHeartRate(context: Context, position: Int): String {
            return when (position) {
                HeartRateStatus.LOW.ordinal -> context.getString(R.string.low)
                HeartRateStatus.NORMAL.ordinal -> context.getString(R.string.normal)
                HeartRateStatus.DIABETES.ordinal -> context.getString(R.string.diabetes)
                else -> ""
            }
        }

        fun getBmiInfo(context: Context): List<BmiData> {
            val item = arrayListOf<BmiData>()
            item.add(
                BmiData(
                    bmi = 5.0,
                    status = context.getString(R.string.very_severely_underweight),
                    description = "<= 15.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 16.4,
                    status = context.getString(R.string.severely_underweight),
                    description = "16.0 - 16.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 18.0,
                    status = context.getString(R.string.underweight),
                    description = "17.0 - 18.4"
                )
            )
            item.add(
                BmiData(
                    bmi = 20.0,
                    status = context.getString(R.string.normal),
                    description = "18.5 - 24.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 27.0,
                    status = context.getString(R.string.overweight),
                    description = "25.0 - 29.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 33.0,
                    status = context.getString(R.string.obese_class_1),
                    description = "30.0 - 34.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 37.0,
                    status = context.getString(R.string.obese_class_2),
                    description = "35.0 - 39.9"
                )
            )
            item.add(
                BmiData(
                    bmi = 45.0,
                    status = context.getString(R.string.obese_class_3),
                    description = ">= 40.0"
                )
            )
            return item
        }

        fun getInfo(context: Context): List<InfoData> {
            val item = arrayListOf<InfoData>()
            item.add(
                InfoData(
                    context.getString(R.string.about_blood_pressure),
                    R.drawable.ic_info_blood_pressure,
                    "#0AB678"
                )
            )
            item.add(
                InfoData(
                    context.getString(R.string.about_blood_sugar),
                    R.drawable.ic_info_sugar,
                    "#8296FF"
                )
            )
            item.add(
                InfoData(
                    context.getString(R.string.about_heart_rate),
                    R.drawable.ic_info_heart_rate,
                    "#FDE400"
                )
            )
            item.add(
                InfoData(
                    context.getString(R.string.about_weight_bmi),
                    R.drawable.ic_info_weight_bmi,
                    "#F7B11E"
                )
            )
            return item
        }

        fun getItemSettings(context: Context): List<SettingDataModel> {
            val item = arrayListOf<SettingDataModel>()
            item.add(
                SettingDataModel(
                    R.drawable.ic_language,
                    context.getString(R.string.language_option)
                )
            )
            item.add(
                SettingDataModel(
                    R.drawable.ic_share,
                    context.getString(R.string.share_with_friends)
                )
            )
            item.add(
                SettingDataModel(
                    R.drawable.ic_rate_us,
                    context.getString(R.string.rate_us)
                )
            )
            return item
        }


        fun getStatusFromSugarConcentration(context: Context, sugarConcentration: Double): String {
            var status = ""
            if (sugarConcentration < 4.0) {
                status = context.getString(R.string.low)
            } else if (sugarConcentration in 4.0..5.5) {
                status = context.getString(R.string.normal)
            } else if (sugarConcentration in 5.6..7.0) {
                status = context.getString(R.string.pre_diabetes)
            } else if (sugarConcentration > 7.0) {
                status = context.getString(R.string.diabetes)
            }
            return status
        }


        fun getStatusBP(context: Context, status: String): String {
            return when (status) {
                context.getString(R.string.normal_blood_pressure) -> context.getString(
                    R.string.normal_blood_pressure
                )

                context.getString(R.string.elevated_blood_pressure) -> context.getString(
                    R.string.elevated_blood_pressure
                )

                context.getString(R.string.high_blood_pressure_1) -> context.getString(
                    R.string.high_blood_pressure_1
                )

                context.getString(R.string.high_blood_pressure_2) -> context.getString(
                    R.string.high_blood_pressure_2
                )

                else -> {
                    context.getString(R.string.dangerously_high_blood_pressure)
                }
            }
        }

        fun getStatusBmi(context: Context, status: String): String {
            return when (status) {
                context.getString(R.string.obese_class_3) -> context.getString(
                    R.string.obese_class_3
                )

                context.getString(R.string.very_severely_underweight) -> context.getString(
                    R.string.very_severely_underweight
                )

                context.getString(R.string.severely_underweight) -> context.getString(
                    R.string.severely_underweight
                )

                context.getString(R.string.underweight) -> context.getString(
                    R.string.underweight
                )

                context.getString(R.string.overweight) -> context.getString(
                    R.string.overweight
                )

                context.getString(R.string.obese_class_1) -> context.getString(
                    R.string.obese_class_1
                )

                context.getString(R.string.obese_class_2) -> context.getString(
                    R.string.obese_class_2
                )

                else -> {
                    context.getString(R.string.normal)
                }
            }
        }
    }
}