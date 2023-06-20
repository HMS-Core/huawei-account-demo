/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.huawei.hms.account;

import com.huawei.hms.account.idtoken.IdTokenUtil;

/**
 * IdTokenUtil use sample
 *
 * @date 2021-05-21
 */
public class IdTokenDemo {
    public static void main(String[] args) {
		/**
        * your idToken
        */
        String idToken = "eyJra****4NzYxNGY2YTE1MjQ2NGE2NTAyN2QyOTBjZWNlMjY3MzQxOTNlYzY2IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoiQUtvUTBadWYzM3BfUXB2X19KUUJpQSIsInN1YiI6Ik1ERk92WUUzNTZqdVFON3hpYWlheFFDaWJnYVdFYmljc0pnbk1RakhVWDJvMmsxNGxBIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuaHVhd2VpLmNvbSIsImdpdmVuX25hbWUiOiLkvZXnpaXmu6giLCJkaXNwbGF5X25hbWUiOiLlh4zpnITnp5HmioAiLCJub25jZSI6IjU1YjRmMzExLWE3M2YtNDFlMy05ZmY3LTBkMzdmNGZiNzY0MiIsInBpY3R1cmUiOiJodHRwczovL3VwZmlsZS1kcmNuLnBsYXRmb3JtLmhpY2xvdWQuY29tL0ZpbGVTZXJ2ZXIvaW1hZ2UvMTAwMC5ySW5DRUphZHFOSmNOMEVZZWYtQUhRLmYtVmhCMjE3elRtaWZQVGhOY2dwZ194ckZHUnNOYXVTTUhkWXhkWXpTT1IwRnZPUjY3aEFpZVZjaWhZWHRHTDB2NHFzYkV5bDFBT1VmTC1LUXdCM1NzZ1E1VTlkZGJ6MGs4bkRZTG5XX2ZRalVqUTA3Zy5qcGciLCJhdWQiOiIxMDQ0NTMwMzkiLCJhenAiOiIxMDQ0NTMwMzkiLCJuYW1lIjoi5L2V56Wl5ruoIiwiZXhwIjoxNjI0NDMzNTY4LCJpYXQiOjE2MjQ0Mjk5NjgsImVtYWlsIjoiaGV4aWFuZ2JpbjFAaHVhd2VpLmNvbSJ9.XNjWfmw9-lO3BBshu06-52A2rc6_Tpu7eusQ7zChACkZPFFhi5FBn77kkhnmM03I6TdaWDrSmSDwGflk4YNX01xmc35ae-NTZLRENRpd7cCcXZiKfIm_HltemjSYePq28hkNLU9-91x3dMTQUaJcxcJE37I93x5IVHgTRkAsj1GAP3UDZL3Dxv0Xf1-mzkvxCf4ykxyv90PCET49B2l2nFLPSapol82K07ZuqEtimoFCEqbjeLe3gc8dpHmbcBNkwhMgi4FLCjcY4dFh2HdPf_mI4pli4F0VnzdrACoN1WfYhO7PPiXoKslZymHUXa3sxK5WEIo139HTdkOeJacWkQ";
        
		/**
        * your app id
        */
		String appId = "104114183";
		
        String userInfos = IdTokenUtil.getUserInfosByIdToken(idToken, appId);
        if (userInfos == null) {
            //TODO id token is invalid, first,you should get a new id token,second, use getUserInfosByIdToken(idToken,appId) again.

        } else {
            //TODO do what you want after get user infos,for example,print log

            System.out.println(userInfos);
        }
    }
}
