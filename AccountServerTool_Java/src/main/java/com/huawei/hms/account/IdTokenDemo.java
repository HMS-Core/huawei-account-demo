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
        String idToken = "eyJraWQiOiIzOTJkOWZiOWI0NDU1NWM3MDJjNzZmZDIzODgxZjg2ODEyZjliZTcyMzkxYjViYzFiNDAyNjM1NTY4N2U2OWEzIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoiUHhkdVcyZHJ5amVZbjlpNjVtSXNNUSIsImF1ZCI6IjEwNDExNDE4MyIsInN1YiI6Ik1ERk92WUUzNTZqdVFON3hpYWlheFFDaWJnYVdFYmljc0pnbk1RakhVWDJvMmsxNGxBIiwiYXpwIjoiMTA0MTE0MTgzIiwiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5odWF3ZWkuY29tIiwiZXhwIjoxNjIxNTY4MzIxLCJkaXNwbGF5X25hbWUiOiLlh4zpnITnp5HmioAiLCJpYXQiOjE2MjE1NjQ3MjEsInBpY3R1cmUiOiJodHRwczovL3VwZmlsZS1kcmNuLnBsYXRmb3JtLmhpY2xvdWQuY29tL0ZpbGVTZXJ2ZXIvaW1hZ2UvMTAwMC5aVXlnR0xYTnVIV21WTnR6OUhzVmVBLlg4SXppRDRLZzQxUjlFSEtWemxfN0tRV3ppWS1BbkFuRlpOX2Q2UHZ2cGV2MmVhdjFIeXdVdk1JRktodEpMM1hub01kLThibXUtNnk1OTQ2WlppTi1rLXozdXgzMzlJMWZxQzRRS2VZSHp2R1pBdkxUUS5qcGcifQ.l_R87XK1RwJURsffWdHZomHnFiHA4gFDiHDwLjfGUowk5Dtgn4V5fglvrBqZ8s0QGWU_WElpTMIN0ryLO3VnAUHQgqV16WkMoNEgRstu_J4QVcPrWM1-AGDI_2eR8fedlnxn-7Mj1cQsP0fEc6nS1VYABFZ2er9nM1mCisYEg7r6gFixIv8JdaVIuwNQjhv7-_SzzxyLwNwvB12p6szFkhRlJuaiR3pxjIy1ac08Ver7vzPPwMJzzL-nb99kSCmdFVB55GN8NkfPcRzcK2yx97_0nSirAFDWmbA1nY8c1gCR37Y7YR65FrpLBKl6tERAJyl1Nt9-f2TK18swKWTnRA";
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
