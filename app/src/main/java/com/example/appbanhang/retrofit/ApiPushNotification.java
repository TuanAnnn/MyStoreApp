package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.NotiResponse;
import com.example.appbanhang.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    // Yêu cầu
    /*<Gửi đi>:
        {
          "to":"euLhA17fSAy0R-RpRa_nyu:APA91bG59V2etGCs5znQsGOsj5vpmkHeTO4b_ZqkrCkpyclW0syTJF57m7hC2Pvb_JiyOq58KNd66ueFUjk1ljKdZyUEMf8Gg-hFFN7wlwMbBdvC23guyRpkDBSZgQBivWTJDCp3GK2y",
          "notification":{
           "title":"thong bao json",
            "body":"noi dung phan body"
          }
        }
    (Trả về):
             {
        "multicast_id": 5183409066947442000,
        "success": 1,
        "failure": 0,
        "canonical_ids": 0,
        "results": [
          {
        "message_id": "0:1652521470068319%2f1c08512f1c0851"
        }
        ],
        }
     */
    @Headers(
            {
                    "content-type:application/json",
                    "authorization: key=AAAAfHpSVXs:APA91bFpCP-MUCdi9npNzqBtwOh_zljR2oN2yN5HUN5tcAy5FKrQ994NSddpXu8N1F4IRB2GtA9SbJVxB7pTeQe80QjsbnOgUZBGprL-7c8m4bLhdcIfySCW_N4NWZxvzN1NitNBdBF1"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNotification(@Body NotiSendData data);
}
